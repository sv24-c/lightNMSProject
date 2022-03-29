package executor;

import bean.MonitorBean;
import dao.MonitorDao;
import helper.MonitorHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by smit on 24/3/22.
 */
public class PollingExecutor
{

    MonitorDao monitorDao = new MonitorDao();

    private boolean pingresult;

    public boolean pingPolling()
    {

        try
        {
            ExecutorService executorService = Executors.newFixedThreadPool(2);

            MonitorHelper monitorHelper = new MonitorHelper();

            MonitorBean monitorBean = new MonitorBean();

            MonitorDao monitorDao = new MonitorDao();

            String ip = null, type = null;

            String command = null;

            String username = null;

            String password = null;

            int id=0;

            List<String> pingcommands = new ArrayList<>();

            List<Map<String, Object>> list;

            list = monitorDao.monitorShowAllData();

            for (int i = 0; i < list.size(); i++)
            {

                id   = (int) list.get(i).get("Id");

                ip   = (String) list.get(i).get("IP");

                type = (String) list.get(i).get("Type");

                username = (String) list.get(i).get("Username");

                password = (String) list.get(i).get("Password");

            }


            String finalType = type;
            String finalIp = ip;
            String finalIp1 = ip;

            String finalUsername = username;
            String finalPassword = password;

            executorService.execute(new Runnable(){

                @Override
                public void run()
                {
                    if (finalType.equals("Ping"))
                    {
                        pingcommands.add("ping -c 5 " + finalIp);

                        pingresult = monitorHelper.ping(pingcommands);

                    }
                    else if (finalType.equals("SSH"))
                    {
                        pingresult = monitorHelper.ping(pingcommands);

                        if (pingresult)
                        {
                            monitorHelper.ssh(finalUsername, finalPassword, finalIp1, command);
                        }
                    }

                }
            });

            executorService.shutdown();

            try
            {
                if (!executorService.awaitTermination(800, TimeUnit.MILLISECONDS))
                {
                    executorService.shutdownNow();
                }

            }
            catch (InterruptedException e)
            {
                executorService.shutdownNow();
            }


        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

        return false;
    }
}
