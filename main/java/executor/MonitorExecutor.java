package executor;

import bean.MonitorBean;
import bean.PollingBean;
import dao.MonitorDao;
import dao.PollingDao;
import helper.MonitorHelper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by smit on 20/3/22.
 */
public class MonitorExecutor
{

    MonitorDao monitorDao = new MonitorDao();

    public boolean monitorPing(MonitorBean monitorBean)
    {

        try
        {

            MonitorHelper monitorHelper = new MonitorHelper();

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

            pingresult = monitorHelper.ping(pingcommands);

            if(pingresult && type.equals("Ping") )
            {

                monitorDao.monitorInsertInDataBase(monitorBean.getId(), name, ip, type);

                return true;

                /*if(monitorDao.monitorCheckRedundantIdData(monitorBean.getId()) == monitorBean.getId())
                {
                    return true;
                }
                else
                {
                    monitorDao.monitorInsertInDataBase(monitorBean.getId(), name, ip, type);

                    return true;
                }*/
            }

            else if(pingresult && type.equals("SSH"))
            {

                if (monitorHelper.ssh(username, password, ip, command))
                {

                    monitorDao.monitorInsertInDataBase(monitorBean.getId(), name, ip, type);

                    return true;

                    /*if (monitorDao.monitorCheckRedundantIdData(monitorBean.getId()) == monitorBean.getId())
                    {
                        return true;
                    }
                    else
                    {
                        monitorDao.monitorInsertInDataBase(monitorBean.getId(), name, ip, type);

                        return true;
                    }*/
                }

            }

            else
            {
                System.out.println("Make sure type is appropriate");

                return false;
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return false;
    }

    public boolean monitorShowAllData(MonitorBean monitorBean)
    {
        try
        {

            List<Map<String, Object>> list;

            List<MonitorBean> monitorBeanList = new ArrayList<>();

            list = monitorDao.monitorShowAllData();

            for (int i = 0; i < list.size(); i++)
            {
                MonitorBean monitorBeanShow = new MonitorBean();

                monitorBeanShow.setId((Integer) list.get(i).get("Id"));

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

    public boolean monitorShowAllDataInTable(PollingBean pollingBean)
    {
        try
        {
            List<Object> matrixList = new ArrayList<>();

            List<Object> pingShowResultList = new ArrayList<>();

            List<Object> sshShowResultList = new ArrayList<>();

            PollingDao pollingDao = new PollingDao();

            MonitorBean monitorBean = new MonitorBean();

            System.out.println(monitorBean.getType());
            System.out.println(pollingBean.getType());

            if (monitorBean.getType().equals("Ping"))
            {
                pingShowResultList = pollingDao.getPollingPingData(pollingBean.getId());

                for (int i = 0; i < pingShowResultList.size(); i++)
                {

                    PollingBean pollingBeanPingMatrix = new PollingBean();

                    pollingBeanPingMatrix.setSendPacket((Integer) pingShowResultList.get(0));
                    pollingBeanPingMatrix.setReceivePacket((Integer) pingShowResultList.get(1));
                    pollingBeanPingMatrix.setPacketLoss((Integer) pingShowResultList.get(2));
                    pollingBeanPingMatrix.setRtt((Float) pingShowResultList.get(3));

                }

                return true;
            }

            else if (pollingBean.getType().equals("Ping"))
            {

                sshShowResultList = pollingDao.getPollingSSHData(pollingBean.getId());

                for (int i = 0; i < sshShowResultList.size(); i++)
                {

                    PollingBean pollingBeanSSHMatrix = new PollingBean();

                    pollingBeanSSHMatrix.setCpu((Float) sshShowResultList.get(0));
                    pollingBeanSSHMatrix.setMemory((Float) sshShowResultList.get(1));
                    pollingBeanSSHMatrix.setDisk((Float) sshShowResultList.get(2));
                }

                return true;
            }

            else
            {
                System.out.println("Not able to get Data");

                return false;
            }


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return false;
    }

}
