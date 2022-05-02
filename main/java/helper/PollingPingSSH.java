package helper;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import dao.Database;
import org.apache.commons.codec.binary.Base64;
import java.io.*;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by smit on 25/3/22.
 */
public class PollingPingSSH implements Runnable
{
    private static Logger _logger = new Logger();

    static final DecimalFormat FORMAT = new DecimalFormat("##");

    private Database database = new Database();

     ArrayList<Object> data = null;

    private List<Object> pingResultList = new ArrayList<>();

    private List<String> pinglist = new ArrayList<>();

    private List<String> commandList = new ArrayList<>();

    private List<Object> pingExecuteResult = new ArrayList<>();

    private String availability = "Up";

    private String availabilityStatus = "Down";

    private int id;

    private String username;

    private String password;

    private String ip;

    private String type;

    private double cpu;

    private double usedMemory;

    private float swapUsedMemory;

    private float disk;

    public PollingPingSSH(String username, String password, String ip, int id, String type)
    {
        this.username = username;

        this.password = password;

        this.ip = ip;

        this.id = id;

        this.type = type;
    }

    @Override
    public void run()
    {

        LocalDateTime now = LocalDateTime.now();

        Timestamp timestamp = Timestamp.valueOf(now);

        try
        {
            pingExecuteResult = ping(ip);

            if (pingResultList !=null && !pingResultList.isEmpty())
            {
                if (type.equals("Ping"))
                {
                    int packetSend = (int) pingExecuteResult.get(0);

                    int packetReceive = (int) pingExecuteResult.get(1);

                    int packetLoss = (int) pingExecuteResult.get(2);

                    float rtt = (float) pingExecuteResult.get(3);

                    if (packetLoss <= CommonConstant.MINIMUMPACKETLOSS)
                    {
                        data = new ArrayList<>();

                        data.add(availability);

                        data.add(id);

                        database.fireExecuteUpdate("UPDATE Monitor SET Availability=? where Id=?", data);

                        data = new ArrayList<>();

                        data.add(id);

                        data.add(ip);

                        data.add(packetSend);

                        data.add(packetReceive);

                        data.add(packetLoss);

                        data.add(rtt);

                        data.add(timestamp);

                        data.add(availability);

                        database.fireExecuteUpdate("INSERT INTO PingPolling (Id, IP, SendPacket, ReceivePacket, PacketLoss, RTT, PollingTime, Status) VALUES(?,?,?,?,?,?,?,?)", data);

                    }
                    else
                    {
                        data = new ArrayList<>();

                        data.add(availabilityStatus);

                        data.add(id);

                        database.fireExecuteUpdate("UPDATE Monitor SET Availability=? where Id=?",data);

                        data = new ArrayList<>();

                        data.add(id);

                        data.add(ip);

                        data.add(packetSend);

                        data.add(packetReceive);

                        data.add(packetLoss);

                        data.add(rtt);

                        data.add(timestamp);

                        data.add(availabilityStatus);

                        database.fireExecuteUpdate("INSERT INTO PingPolling (Id, IP, SendPacket, ReceivePacket, PacketLoss, RTT, PollingTime, Status) VALUES(?,?,?,?,?,?,?,?)" , data);

                        _logger.info("Polling time it might be packet loss or less packet sent.");
                    }
                }
                else if(type.equals("SSH"))
                {
                    int packetLoss = (int) pingExecuteResult.get(2);

                    if (packetLoss <= 20)
                    {
                        commandList.add("mpstat | grep all\n");

                        commandList.add("free -m | grep Mem | awk '{print $2,$3}'\n");

                        commandList.add("df -ht ext4 | grep / | awk '{print $5}'\n");

                        commandList.add("free -m | grep Swap | awk '{print $2,$3}'\n");

                        commandList.add("exit\n");

                        String result = ssh(username, password, ip, commandList);

                        if(!result.isEmpty())
                        {
                            result = result.substring(result.lastIndexOf("mpstat | grep all"));

                            String manipulateCpu = result.substring(result.lastIndexOf("mpstat | grep all")+"mpstat | grep all".length() , result.indexOf(username+"@"));

                            String[] cpuResult = manipulateCpu.split(" ");

                            cpu = 100 - (Double.parseDouble(cpuResult[cpuResult.length -1]));

                            result = result.substring(result.lastIndexOf("free -m | grep Mem | awk '{print $2,$3}'"));

                            String memory = result.substring(result.lastIndexOf("free -m | grep Mem | awk '{print $2,$3}'")+"free -m | grep Mem | awk '{print $2,$3}'".length() , result.indexOf(username+"@"));

                            String[] memoryArray = memory.split(" ");

                            usedMemory = Double.parseDouble(FORMAT.format((Double.parseDouble(memoryArray[1]) /  Double.parseDouble(memoryArray[0]) ) * 100));

                            result = result.substring(result.lastIndexOf("df -ht ext4 | grep / | awk '{print $5}'"));

                            disk = Float.parseFloat(result.substring(result.lastIndexOf("df -ht ext4 | grep / | awk '{print $5}'")+"df -ht ext4 | grep / | awk '{print $5}'".length() , result.indexOf("%")));

                            result = result.substring(result.lastIndexOf("free -m | grep Swap | awk '{print $2,$3}'"));

                            String swapMemory = result.substring(result.lastIndexOf("free -m | grep Swap | awk '{print $2,$3}'")+"free -m | grep Swap | awk '{print $2,$3}'".length() , result.indexOf(username+"@"));

                            String[] swapMemoryArray = swapMemory.split(" ");

                            float swapusedmemoryfloat = ((Float.parseFloat(swapMemoryArray[1]) / Float.parseFloat(swapMemoryArray[0])) * 100);

                            swapUsedMemory = Float.parseFloat(FORMAT.format(swapusedmemoryfloat));

                            //swapUsedMemory = Float.parseFloat(result.substring(result.lastIndexOf("free -m | grep Swap | awk '{print $2,$3}'")+"free -m | grep Swap | awk '{print $2,$3}'".length() , result.indexOf(username+"@")));

                            data = new ArrayList<>();

                            data.add(id);

                            data.add(ip);

                            data.add(cpu);

                            data.add(usedMemory);

                            data.add(disk);

                            data.add(timestamp);

                            data.add(availability);

                            data.add(swapUsedMemory);

                            database.fireExecuteUpdate("INSERT INTO SSHPolling (Id, IP, CPU, Memory, Disk, PollingTime, Status, SwapMemory) VALUES(?,?,?,?,?,?,?,?)" , data);

                            data = new ArrayList<>();

                            data.add(availability);

                            data.add(id);

                            database.fireExecuteUpdate("UPDATE Monitor SET Availability=? where Id=?",data);
                        }
                    }
                    else
                    {
                        data = new ArrayList<>();

                        data.add(availabilityStatus);

                        data.add(id);

                        database.fireExecuteUpdate("UPDATE Monitor SET Availability=? where Id=?",data);

                        cpu = 0;

                        usedMemory = 0;

                        swapUsedMemory = 0;

                        disk = 0;

                        data = new ArrayList<>();

                        data.add(id);

                        data.add(ip);

                        data.add(cpu);

                        data.add(usedMemory);

                        data.add(disk);

                        data.add(timestamp);

                        data.add(availabilityStatus);

                        data.add(swapUsedMemory);

                        database.fireExecuteUpdate("INSERT INTO SSHPolling (Id, IP, CPU, Memory, Disk, PollingTime, Status, SwapMemory) VALUES(?,?,?,?,?,?,?,?)" , data);
                    }
                }
                else
                {
                    _logger.info("PollingPingSSH Device Type Would be Ping or SSH Only");
                }
            }
            else
            {
                _logger.info("PollingPingSSH pingResult is empty or null");
            }
        }
        catch (Exception exception)
        {
            _logger.error("PollingPingSSH run method having error. ", exception);
        }
    }

    public List<Object> ping(String ip)
    {

        StringBuilder packetdata = new StringBuilder();

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
                    packetdata.append(result);
                }

                if (packetdata.length() > 0)
                {
                    if((Integer.parseInt(packetdata.substring((packetdata.indexOf(" received,")  - 1), packetdata.indexOf(" received,"))) >=1))
                    {
                        packetSend = Integer.parseInt(packetdata.substring((packetdata.indexOf(" packets transmitted")  - 1), packetdata.indexOf(" packets transmitted")));

                        packetReceive = Integer.parseInt(packetdata.substring((packetdata.indexOf(" received,")  - 1), packetdata.indexOf(" received,")));

                        packetLoss = (int) (100 - ((Float.parseFloat(packetdata.substring((packetdata.indexOf(" received,")  - 1), packetdata.indexOf(" received,"))) / 5.0) *100));

                        rtt = Float.parseFloat((packetdata.substring((packetdata.indexOf("rtt ")+23), packetdata.indexOf("rtt ")+28)));

                        System.out.println(rtt);

                        pingResultList.add(packetSend);

                        pingResultList.add(packetReceive);

                        pingResultList.add(packetLoss);

                        pingResultList.add(rtt);
                    }

                    else if((Integer.parseInt(packetdata.substring((packetdata.indexOf(" received,")  - 1), packetdata.indexOf(" received,"))) == 0))
                    {
                        packetSend = Integer.parseInt(packetdata.substring((packetdata.indexOf(" packets transmitted")  - 1), packetdata.indexOf(" packets transmitted")));

                        packetReceive = Integer.parseInt(packetdata.substring((packetdata.indexOf(" received,")  - 1), packetdata.indexOf(" received,")));

                        packetLoss = CommonConstant.PACKETLOSS;

                        rtt = -1;

                        pingResultList.add(packetSend);

                        pingResultList.add(packetReceive);

                        pingResultList.add(packetLoss);

                        pingResultList.add(rtt);
                    }
                    else
                    {
                        _logger.info("Getting Stuff in Ping at Polling Time");
                    }
                }
            }
            else
            {
                _logger.info("Something went Wrong in Ping at Polling Time");
            }
        }

        catch(Exception exception)
        {
            _logger.error("PollingPingSSH ping method having error. ", exception);
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
            catch (Exception exception)
            {
                _logger.error("PollingPingSSH in ping method reader close having error. ", exception);
            }

            try
            {
                if (process != null)
                {
                    process.destroy();
                }
            }
            catch (Exception exception)
            {
                _logger.error("PollingPingSSH in ping method process close having error. ", exception);
            }
        }

        return pingResultList;
    }

    public String ssh(String username, String password, String ip, List<String> commandList)
    {
        Base64 base64 = new Base64();

        String decryptedPassword = new String(base64.decode(password.getBytes()));

        JSch jSch = new JSch();

        Session session = null;

        Channel channel= null;

        String resultTemp = "";

        String resultString = null;

        BufferedWriter bufferedWriter = null;

        BufferedReader bufferedReader = null;
        
        try
        {
            session = jSch.getSession(username, ip, CommonConstant.SSHPORT);

            session.setPassword(decryptedPassword);

            session.setConfig("StrictHostKeyChecking", "no");

            session.connect(10*1000);
            
            channel = (ChannelShell) session.openChannel("shell");

            channel.connect(10*1000);
            
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
            }
        }
        catch (Exception exception)
        {
            _logger.error("PollingPingSSH ssh method having error. ", exception);
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
            catch (Exception exception)
            {
                _logger.error("PollingPingSSH in ssh method channel close having error. ", exception);
            }

            try
            {
                if (session != null)
                {
                    session.disconnect();
                }
            }
            catch (Exception exception)
            {
                _logger.error("PollingPingSSH in ssh method session close having error. ", exception);
            }

            try
            {
                if (bufferedWriter != null)
                {
                    bufferedWriter.close();
                }
            }
            catch (Exception exception)
            {
                _logger.error("PollingPingSSH in ssh method bufferWriter close having error. ", exception);
            }

            try
            {
                if (bufferedReader != null)
                {
                    bufferedReader.close();
                }
            }
            catch (Exception exception)
            {
                _logger.error("PollingPingSSH in ssh method bufferReader close having error. ", exception);
            }
        }
        return resultString;
    }
}
