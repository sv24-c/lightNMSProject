package helper;

import dao.Database;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by smit on 7/4/22.
 */
public class MultipleDiscovery
{
    private static LinkedBlockingQueue<Integer> linkedBlockingQueue = new LinkedBlockingQueue<>(10);

    static Database database = new Database();

    static ArrayList<Object> data = null;

    private static final Logger _logger = new Logger();

   public static boolean multipleDiscoveryAddInQueue(int id)
   {
       try
       {
           linkedBlockingQueue.add(id);

           return true;
       }
       catch (Exception exception)
       {
           _logger.error("MultipleDiscovery multipleDiscoveryAddInQueue method having error. ", exception);

       }
       return false;
   }

   public static int multipleDiscoveryTakeFromQueue()
   {
       int takeId = 0;

       try
       {
          takeId =  linkedBlockingQueue.take();
       }
       catch (InterruptedException exception)
       {
           _logger.error("MultipleDiscovery multipleDiscoveryTakeFromQueue method having error. ", exception);
       }
       return takeId;
   }

    public static String multipleDiscoveryPingSSH(int blockingQueueTakenId)
    {
        try
        {
            MonitorHelper monitorHelper = new MonitorHelper();

            String name = null;

            String ip = null;

            String type = null;

            String username = null;

            String password = null;

            String availability = "unknown";

            boolean pingresult;

            PollingPingSSH pollingPingSSH = new PollingPingSSH();

            List<String> command = new ArrayList<>();

            List<String> pingcommands = new ArrayList<>();

            List<HashMap<String, Object>> usernamePasswordList;

            List<HashMap<String, Object>> provisionDataList;

            data = new ArrayList<>();

            data.add(blockingQueueTakenId);

            provisionDataList = database.fireSelectQuery("SELECT Name, IP, Type FROM Discovery where Id = ?", data);

            if(!provisionDataList.isEmpty())
            {
                name = (String) provisionDataList.get(0).get("Name");

                ip = (String) provisionDataList.get(0).get("IP");

                type = (String) provisionDataList.get(0).get("Type");
            }

            if (blockingQueueTakenId != 0 )
            {
                data = new ArrayList<>();

                data.add(ip);

                data.add(type);

                if(!database.fireSelectQuery("SELECT Id FROM Monitor WHERE IP = ? AND Type = ?", data).isEmpty())
                {
                    return ip+" already in the Monitor Grid";
                }
                else
                {
                    pingcommands.add("ping");

                    pingcommands.add("-c");

                    pingcommands.add("5");

                    pingcommands.add(ip);

                    pingresult = monitorHelper.ping(pingcommands);

                    if (pingresult)
                    {
                        if (type != null)
                        {
                            switch (type)
                            {
                                case "Ping":

                                    data = new ArrayList<>();

                                    data.add(blockingQueueTakenId);

                                    data.add(name);

                                    data.add(ip);

                                    data.add(type);

                                    data.add(availability);

                                    database.fireExecuteUpdate("INSERT INTO Monitor (Id, Name, IP, Type, Availability) VALUES(?,?,?,?,?)" , data);

                                    return "Ping "+ ip + " Provision Done.";


                                case "SSH":

                                    data = new ArrayList<>();

                                    data.add(blockingQueueTakenId);

                                    usernamePasswordList = database.fireSelectQuery("SELECT Username, Password FROM Credential WHERE Id=?" , data);

                                    if (!usernamePasswordList.isEmpty())
                                    {
                                        username = (String) usernamePasswordList.get(0).get("Username");

                                        password = (String) usernamePasswordList.get(0).get("Password");
                                    }

                                    //String returnSSHResult = monitorHelper.ssh(username, password, ip);

                                    command.add("uname\nexit\n");

                                    String returnSSHResult = pollingPingSSH.ssh(username, password, ip, command);

                                    if (returnSSHResult.contains("Linux"))
                                    {
                                        data = new ArrayList<>();

                                        data.add(blockingQueueTakenId);

                                        data.add(name);

                                        data.add(ip);

                                        data.add(type);

                                        data.add(availability);

                                        database.fireExecuteUpdate("INSERT INTO Monitor (Id, Name, IP, Type, Availability) VALUES(?,?,?,?,?)" , data);

                                        return "SSH "+ ip + " Provision Done.";
                                    }
                                    else
                                    {
                                        return returnSSHResult;
                                    }

                                default:

                                    return "Device Type Would be Ping or SSH only";
                            }
                        }
                    }
                    else
                    {
                        return "Provision Failed "+ip;
                    }
                }
            }
        }
        catch (Exception exception)
        {
            _logger.error("MultipleDiscovery multipleDiscoveryPingSSH method having error. ", exception);

        }
        return null;
    }
}
