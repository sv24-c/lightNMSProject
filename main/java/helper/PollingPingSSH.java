package helper;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import dao.MonitorDao;
import dao.PollingDao;
import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by smit on 25/3/22.
 */
public class PollingPingSSH implements Runnable
{
    List<Object> pingResultList = new ArrayList<>();

    List<String> pinglist = new ArrayList<>();

    List<String> commandList = new ArrayList<>();

    List<Object> pingExecuteResult = new ArrayList<>();

    PollingDao pollingDao = new PollingDao();

    MonitorDao monitorDao = new MonitorDao();

    String availability = "Up";

    int id;
    String username;
    String password;
    String ip;
    String type;

    double cpu;
    double usedMemory;
    float swapfreeMemory;
    float disk;

    public PollingPingSSH(String username, String password, String ip, int id, String type)
    {
        this.username = username;

        this.password = password;

        this.ip = ip;

        this.id = id;

        this.type = type;

        System.out.println("Hey "+this.ip);
    }

    @Override
    public void run() {

        System.out.println("Hello "+this.ip);

        LocalDateTime now = LocalDateTime.now();

        Timestamp timestamp = Timestamp.valueOf(now);

        try
        {

            if (type.equals("Ping"))
            {
                pingExecuteResult = ping(ip);

                int packetSend = (int) pingExecuteResult.get(0);

                int packetReceive = (int) pingExecuteResult.get(1);

                int packetLoss = (int) pingExecuteResult.get(2);

                float rtt = (float) pingExecuteResult.get(3);


                if (packetLoss <= 20)
                {
                    monitorDao.monitorAvailabilityUpdate(availability, id);

                    pollingDao.pollingDaoPingInsertData(id, ip, packetSend, packetReceive, packetLoss, rtt, timestamp, availability);

                }
                else if (packetLoss ==100)
                {
                    String availabilityStatus = "Down";

                    monitorDao.monitorAvailabilityUpdate(availabilityStatus, id);

                    pollingDao.pollingDaoPingInsertData(id, ip, packetSend, packetReceive, packetLoss, rtt, timestamp, availabilityStatus);

                }

            }

            else if(type.equals("SSH"))
            {
                pingExecuteResult = ping(ip);

                int packetLoss = (int) pingExecuteResult.get(2);

                if (packetLoss <= 20)
                {

                    commandList.add("mpstat | grep all\n");

                    commandList.add("free -m | grep Mem | awk '{print $3}'\n");

                    commandList.add("df -ht ext4 | grep / | awk '{print $5}'\n");

                    commandList.add("free -m | grep Swap | awk '{print $4}'\n");

                    commandList.add("exit\n");

                    String result = ssh(username, password, ip, commandList);

                    if(!result.isEmpty())
                    {

                        result = result.substring(result.lastIndexOf("mpstat | grep all"));

                        String manipulateCpu = result.substring(result.lastIndexOf("mpstat | grep all")+"mpstat | grep all".length() , result.indexOf(username+"@"));

                        String[] cpuResult = manipulateCpu.split(" ");

                        cpu = 100 - (Double.parseDouble(cpuResult[cpuResult.length -1]));

                        result = result.substring(result.lastIndexOf("free -m | grep Mem | awk '{print $3}'"));

                        String memory = result.substring(result.lastIndexOf("free -m | grep Mem | awk '{print $3}'")+"free -m | grep Mem | awk '{print $3}'".length() , result.indexOf(username+"@"));

                        usedMemory = Double.parseDouble(memory);

                        result = result.substring(result.lastIndexOf("df -ht ext4 | grep / | awk '{print $5}'"));

                        disk = Float.parseFloat(result.substring(result.lastIndexOf("df -ht ext4 | grep / | awk '{print $5}'")+"df -ht ext4 | grep / | awk '{print $5}'".length() , result.indexOf("%"+username+"@")));

                        result = result.substring(result.lastIndexOf("free -m | grep Swap | awk '{print $4}'"));

                        swapfreeMemory = Float.parseFloat(result.substring(result.lastIndexOf("free -m | grep Swap | awk '{print $4}'")+"free -m | grep Swap | awk '{print $4}'".length() , result.indexOf(username+"@")));

                        pollingDao.pollingDaoSSHInsertData(id, ip, cpu, usedMemory, disk, timestamp, availability, swapfreeMemory);

                        monitorDao.monitorAvailabilityUpdate(availability, id);

                    }

                }
                else if (packetLoss == 100)
                {
                    String availabilityStatus = "Down";

                    monitorDao.monitorAvailabilityUpdate(availabilityStatus, id);

                    cpu = 0;

                    usedMemory = 0;

                    swapfreeMemory = 0;

                    disk = 0;

                    pollingDao.pollingDaoSSHInsertData(id, ip, cpu, usedMemory, disk, timestamp, availabilityStatus, swapfreeMemory);

                }
                else
                {
                    System.out.println("Host Unreachable");
                }
            }

            else
            {
                System.out.println("Device Type Would be Ping or SSH Only");
            }
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public List ping(String ip)
    {

        String packetdata = "";

        String result = "";

        int packetLoss =0;

        int packetSend;

        int packetReceive;

        float rtt;

        ProcessBuilder processBuilder;

        Process process = null;

        BufferedReader reader = null;

        try
        {
            pinglist.add("ping");

            pinglist.add("-c");

            pinglist.add("5");

            pinglist.add(ip);

            if(!pinglist.isEmpty())
            {

                processBuilder = new ProcessBuilder(pinglist);

                process = processBuilder.start();

                reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

                while ((result = reader.readLine()) != null)
                {
                    packetdata+=result;
                    
                }

                if (!packetdata.isEmpty())
                {
                    if((Integer.parseInt(packetdata.substring((packetdata.indexOf(" received,")  - 1), packetdata.indexOf(" received,"))) >=1))
                    {

                        packetSend = Integer.parseInt(packetdata.substring((packetdata.indexOf(" packets transmitted")  - 1), packetdata.indexOf(" packets transmitted")));

                        packetReceive = Integer.parseInt(packetdata.substring((packetdata.indexOf(" received,")  - 1), packetdata.indexOf(" received,")));

                        packetLoss = 100 - ((Integer.parseInt(packetdata.substring((packetdata.indexOf(" received,")  - 1), packetdata.indexOf(" received,"))) / 5) *100) ;

                        rtt = Float.parseFloat(packetdata.substring((packetdata.indexOf("rtt ")+23), packetdata.indexOf("rtt ")+28));

                        pingResultList.add(packetSend);

                        pingResultList.add(packetReceive);

                        pingResultList.add(packetLoss);

                        pingResultList.add(rtt);

                    }

                    else if((Integer.parseInt(packetdata.substring((packetdata.indexOf(" received,")  - 1), packetdata.indexOf(" received,"))) == 0))
                    {
                        packetSend = Integer.parseInt(packetdata.substring((packetdata.indexOf(" packets transmitted")  - 1), packetdata.indexOf(" packets transmitted")));

                        packetReceive = Integer.parseInt(packetdata.substring((packetdata.indexOf(" received,")  - 1), packetdata.indexOf(" received,")));

                        packetLoss = 100;

                        rtt = -1;

                        pingResultList.add(packetSend);

                        pingResultList.add(packetReceive);

                        pingResultList.add(packetLoss);

                        pingResultList.add(rtt);

                    }
                    else
                    {
                        System.out.println("Host Unreachable");
                    }

                }

            }

            else
            {
                System.out.println("Something went Wrong.");
            }
        }

        catch(Exception e)
        {
            e.printStackTrace();
        }

        finally
        {
            try
            {
                if (reader != null)
                {
                    reader.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            try
            {
                if (process != null)
                {
                    process.destroy();
                }
            }

            catch (Exception e)
            {
                e.printStackTrace();
            }

        }

        return pingResultList;
    }

    public String ssh(String username, String password, String ip, List<String> commandList)
    {
        Base64 base64 = new Base64();

        String decryptedPassword = new String(base64.decode(password.getBytes()));

        JSch jSch = new JSch();

        com.jcraft.jsch.Session session = null;

        Channel channel= null;

        String resultTemp = "";

        String resultString = null;

        BufferedWriter bufferedWriter = null;

        BufferedReader bufferedReader = null;
        
        try
        {
            session = jSch.getSession(username, ip, 22);

            session.setPassword(decryptedPassword);

            session.setConfig("StrictHostKeyChecking", "no");

            session.connect();
            
            channel = (ChannelShell) session.openChannel("shell");

            channel.connect();
            
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(channel.getOutputStream()));

            bufferedReader = new BufferedReader(new InputStreamReader(channel.getInputStream()));

            for (int i = 0; i< commandList.size(); i++)
            {
                String command = commandList.get(i);

                bufferedWriter.write(command);
            }

            bufferedWriter.flush();

            while ((resultTemp = bufferedReader.readLine()) != null)
            {
                resultString+=resultTemp;
                //sshResultList.add(resultTemp);
            }

        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

        finally
        {
            try
            {

                if (channel!= null)
                {
                    channel.disconnect();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            try
            {
                if (session != null)
                {
                    session.disconnect();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            try
            {
                if (bufferedWriter != null)
                {
                    bufferedWriter.close();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            try
            {
                if (bufferedReader != null)
                {
                    bufferedReader.close();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }

        return resultString;
    }

}
