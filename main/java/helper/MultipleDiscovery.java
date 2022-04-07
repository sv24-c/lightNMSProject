package helper;

import bean.MonitorBean;
import dao.DiscoveryDao;
import dao.MonitorDao;

import javax.management.monitor.Monitor;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by smit on 7/4/22.
 */
public class MultipleDiscovery
{

    MonitorDao monitorDao = new MonitorDao();

    public boolean multipleDiscovery(MonitorBean monitorBean)
    {

        try
        {
            LinkedBlockingQueue<MonitorBean> linkedBlockingQueue = new LinkedBlockingQueue<>();

            MonitorHelper monitorHelper = new MonitorHelper();

            String name = null;

            String ip = null;

            String type = null;

            String username = null;

            String password = null;

            boolean pingresult;

            List<String> pingcommands = new ArrayList<>();

            List<String> usernamePasswordList;

            List<String> provisionDataList;

            provisionDataList = monitorDao.monitorDaoGetDataForProvision(monitorBean.getId());

            if(!provisionDataList.isEmpty())
            {

                name = provisionDataList.get(0);

                ip = provisionDataList.get(1);

                type = provisionDataList.get(2);
            }

            if(monitorDao.monitorDaoGetData(monitorBean.getId()))
            {
                monitorBean.setStatus(ip+" already added to Monitor Grid");

                return true;
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

                    if (type.equals("Ping"))
                    {

                        monitorBean.setStatus(ip+" Provision Done.");

                        monitorDao.monitorDaoInsert(monitorBean.getId(), name, ip, type);

                        return true;

                    }

                    else if (type.equals("SSH"))
                    {

                        DiscoveryDao discoveryDao = new DiscoveryDao();

                        usernamePasswordList = discoveryDao.discoveryDaoGetUsernamePasswordData(monitorBean.getId());

                        if (!usernamePasswordList.isEmpty())
                        {
                            username = usernamePasswordList.get(1);

                            password = usernamePasswordList.get(2);
                        }

                        if (monitorHelper.ssh(username, password, ip))
                        {

                            monitorBean.setStatus(ip+" Provision Done.");

                            monitorDao.monitorDaoInsert(monitorBean.getId(), name, ip, type);

                            return true;
                        }
                        else
                        {
                            monitorBean.setStatus("SSH Failed to this " + ip);

                            return false;
                        }

                    }
                    else
                    {
                        monitorBean.setStatus("Device Type Would be Ping or SSH only");

                        return false;
                    }

                }
                else
                {
                    monitorBean.setStatus("Provision Failed "+ip);
                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }
}
