package helper;

import dao.MonitorDao;
import dao.PollingDao;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by smit on 25/3/22.
 */
public class PollingRunnable
{
 ExecutorService executorService = Executors.newFixedThreadPool(8);

    public PollingRunnable() {

    }

    public void pollingRunnableMethod()
    {
        try
        {

            String ip = null;

            String type = null;

            String username = null;

            String password = null;

            int id = 0;

            List<Map<String, Object>> list = null;

            List<String> stringList = null;

            MonitorDao monitorDao = new MonitorDao();

            PollingDao pollingDao = new PollingDao();

            list = monitorDao.monitorDaoGetAllData();

            for (int i = 0; i < list.size(); i++)
            {

                id = (int) list.get(i).get("Id");

                ip   = (String) list.get(i).get("IP");

                type = (String) list.get(i).get("Type");

                System.out.println("Hi "+ip);

                if (type.equals("SSH"))
                {

                    stringList = pollingDao.pollingDaoGetUsernamePassword(id);

                    username = stringList.get(0);

                    password = stringList.get(1);
                }

                executorService.execute(new PollingPingSSH(username, password, ip, id, type));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        finally
        {

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
                e.printStackTrace();
            }
        }
    }

}
