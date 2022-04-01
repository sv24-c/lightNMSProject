package helper;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import dao.MonitorDao;
import dao.PollingDao;
import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by smit on 25/3/22.
 */
public class PollingPingSSH implements Runnable
{
    List<Object> pingResultList = new ArrayList<>();

    List<String> pinglist = new ArrayList<>();

    List<Object> sshResultList = new ArrayList<>();

//    List<String>command;

    List<String> commandList = new ArrayList<>();

    List<Object> pingExecuteResult = new ArrayList<>();

    List<Object> sshExecuteResult = new ArrayList<>();

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

    /*public PollingPingSSH(String username, String password, String ip, int id) {
        super();
    }*/

    public PollingPingSSH(String username, String password, String ip, int id, String type) {
        //super(username, password, ip, id);

        this.username = username;
        this.password = password;
        this.ip = ip;
        this.id = id;
        this.type = type;
    }

    @Override
    public void run() {

        //pingExecuteResult = ping(ip);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(now);

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

                pollingDao.pollingPingInsertInDataBase(id, ip, packetSend, packetReceive, packetLoss, rtt, timestamp, availability);

            }
            else if (packetLoss ==100)
            {
                String availabilityStatus = "Down";

                monitorDao.monitorAvailabilityUpdate(availabilityStatus, id);

                pollingDao.pollingPingInsertInDataBase(id, ip, packetSend, packetReceive, packetLoss, rtt, timestamp, availabilityStatus);

            }


            System.out.println("Add to PingPolling table");

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

                //commandList.add("uptime");

                sshExecuteResult = ssh(username, password, ip, commandList);

                for (int i = 0;i<sshExecuteResult.size();i++)
                {
                    System.out.println(sshExecuteResult.get(i));

                }

                if(!sshExecuteResult.isEmpty())
                {
                    //sshExecuteResult = ssh(username, password, ip, commandList);

                    String cp = (String) sshExecuteResult.get(6);

                    String[] cpuResult = cp.split(" ");

                    cpu = 100 - (Double.parseDouble(cpuResult[cpuResult.length -1]));

                    String memo = (String) sshExecuteResult.get(8);

                    usedMemory = Double.parseDouble(memo);
                        /*String[] memoryResult = memo.split(" ");
                        double usedMemory = Double.parseDouble(memoryResult[memoryResult.length - 2]);*/

                    String dis = (String) sshExecuteResult.get(10);

                        //float disk = Float.parseFloat(dis);
                    disk = Float.parseFloat(dis.substring(dis.indexOf(0)+1, dis.indexOf("%")));

                    swapfreeMemory = Float.parseFloat(String.valueOf(sshExecuteResult.get(12)));

                    pollingDao.pollingSSHInsertInDataBase(id, ip, cpu, usedMemory, disk, timestamp, availability, swapfreeMemory);

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

                pollingDao.pollingSSHInsertInDataBase(id, ip, cpu, usedMemory, disk, timestamp, availabilityStatus, swapfreeMemory);

                System.out.println("ping ssh is not done");
            }
            else
            {
                System.out.println("Unable to ping");
            }
        }

        else
        {

            System.out.println("Make sure type should be appropriate");
        }

        }

    public List ping(String ip)
    {

        String packetdata = "";

        String s = "";

        int packetLoss =0;

        int packetSend;

        int packetReceive;

        float rtt;

        ProcessBuilder processBuilder;

        Process process = null;

        BufferedReader reader = null;

        BufferedReader error = null;

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

                error = new BufferedReader(new InputStreamReader(process.getErrorStream()));

                System.out.println("Result for Command ping..");

                while ((s = reader.readLine()) != null)
                {
                    packetdata+=s;

                    System.out.println(s);

                }

                while ((s = error.readLine()) != null)
                {
                    System.out.println("Error for Command ping."+s);
                }

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
                    System.out.println("Host Unreachable.");
                }

            }

            else
            {
                System.out.println("list should be empty first");
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
                reader.close();

                error.close();

                process.destroy();

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }

        return pingResultList;
    }

    public List ssh(String username, String password, String ip, List<String> commandList)
    {
        Base64 base64 = new Base64();

        String decryptedPassword = new String(base64.decode(password.getBytes()));

        JSch jSch = new JSch();

        com.jcraft.jsch.Session session = null;

        Channel channel= null;



        try
        {
            //commandList = new ArrayList<>();

                String result = "";

                session = jSch.getSession(username, ip, 22);

                session.setPassword(decryptedPassword);

                session.setConfig("StrictHostKeyChecking", "no");

//                session.setTimeout(15000);

                session.connect(10000);

                System.out.println("SSH Session connection done.");

                channel = (ChannelShell) session.openChannel("shell");

                channel.connect(10000);

            System.out.println("Channel result: "+channel.isConnected()+session.isConnected());

            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(channel.getOutputStream()));

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(channel.getInputStream()));

            for (int i = 0; i< commandList.size(); i++)
                {
                   //channel.setCommand(commandList.get(i));

                    String command = commandList.get(i);

                    bufferedWriter.write(command);

                }
            bufferedWriter.flush();

            String resultTemp = "";

            while ((resultTemp = bufferedReader.readLine()) != null)
            {
                System.out.println(resultTemp);

                sshResultList.add(resultTemp);

            }

        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

        finally
        {
            if (channel!= null)
            {
                channel.disconnect();

                System.out.println("Channel disconnected.");
            }

            if (session != null)
            {
                session.disconnect();

                System.out.println("SSH connection disconnected.");
            }

        }
        return sshResultList;
    }

}
