package helper;

import bean.MonitorBean;
import dao.MonitorDao;
import dao.PollingDao;

import java.util.ArrayList;
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

    String ip = null;

    String type = null;

    String username = null;

    String password = null;

    int id = 0;

    ExecutorService executorService = Executors.newFixedThreadPool(8);

    /*public PollingRunnable(String ip) {
    }*/

    public PollingRunnable() {

    }

    /*public PollingRunnable(String username, String password, String ip, int id, String type) {
        this.username = username;
        this.password = password;
        this.ip = ip;
        this.id = id;
        this.type = type;
    }
*/
    public void pollingRunnableMethod()
    {
        try
        {

            List<Map<String, Object>> list = null;

            List<String> stringList = null;

            MonitorDao monitorDao = new MonitorDao();

            PollingDao pollingDao = new PollingDao();

            list = monitorDao.monitorShowAllData();



            for (int i = 0; i < list.size(); i++)
            {

                id = (int) list.get(i).get("Id");

                ip   = (String) list.get(i).get("IP");

                type = (String) list.get(i).get("Type");

                if (type.equals("SSH"))
                {

                    stringList = pollingDao.getUsernamePasswordDaoData(id);

                    username = stringList.get(0);

                    password = stringList.get(1);
                }

                executorService.execute(new PollingPingSSH(this.username, this.password, this.ip, this.id, this.type));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        finally
        {

            executorService.shutdown();

            /*try
            {
                if (!executorService.awaitTermination(800, TimeUnit.MILLISECONDS))
                {
                    executorService.shutdownNow();
                }
            }
            catch (InterruptedException e)
            {
                executorService.shutdownNow();
            }*/
        }
    }

}
