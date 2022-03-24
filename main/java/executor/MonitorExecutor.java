package executor;

import bean.MonitorBean;
import dao.MonitorDao;
import helper.MonitorHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by smit on 20/3/22.
 */
public class MonitorExecutor
{

    MonitorHelper monitorHelper = new MonitorHelper();

    MonitorDao monitorDao = new MonitorDao();

    public boolean monitorPing(MonitorBean monitorBean)
    {

        try
        {

            String name = null;
            String ip = null;
            String type = null;
            String username = null;
            String password = null;
            String command = "free";
            boolean pingresult;

            List<String> pingcommands = new ArrayList<>();

            List<Map<String, Object>> list;

            list = monitorDao.monitorShowData(monitorBean.getId());

            for (int i = 0; i < list.size(); i++)
            {

                name = (String) list.get(i).get("Name");

                ip   = (String) list.get(i).get("IP");

                type = (String) list.get(i).get("Type");

                username = (String) list.get(i).get("Username");

                password = (String) list.get(i).get("Password");
            }

            pingcommands.add("ping");
            pingcommands.add("-c");
            pingcommands.add("5");  
            pingcommands.add(ip);

            pingresult = new MonitorHelper().ping(pingcommands);

            if(pingresult && type.equals("Ping"))
            {
                monitorDao.monitorInsertInDataBase(monitorBean.getId(), name, ip, type);
            }

            else if(pingresult && type.equals("SSH"))
            {
                if(new MonitorHelper().ssh(username, password, ip, command))
                {
                    monitorDao.monitorInsertInDataBase(monitorBean.getId(), name, ip, type);
                }
                System.out.println("Do ping first then also ssh");
            }

            else
            {
                System.out.println("Make sure type is appropriate");
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return true;
    }

    public boolean monitorShowAllData(MonitorBean monitorBean)
    {
        try
        {

            MonitorDao monitorDao = new MonitorDao();

            List<Map<String, Object>> list;

            List<MonitorBean> monitorBeanList = new ArrayList<>();

            list = monitorDao.monitorShowAllData();

            for (int i = 0; i < list.size(); i++)
            {
                MonitorBean monitorBeanShow = new MonitorBean();

                monitorBeanShow.setId(String.valueOf(list.get(i).get("Id")));

                monitorBeanShow.setName(String.valueOf(list.get(i).get("Name")));

                monitorBeanShow.setIp((String) list.get(i).get("IP"));

                monitorBeanShow.setType((String) list.get(i).get("Type"));

                monitorBeanShow.setAvailability((String) list.get(i).get("Availability"));

                monitorBeanList.add(monitorBeanShow);

            }

            monitorBean.setMonitorBeanList(monitorBeanList);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return true;
    }

    public boolean monitorDeleteInDatabase(MonitorBean monitorBean)
    {
        try
        {
            monitorDao.monitorDeleteData(monitorBean.getId());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return true;
    }

}
