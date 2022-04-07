package executor;

import bean.DiscoveryBean;
import bean.MonitorBean;
import dao.DiscoveryDao;
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

    private MonitorDao monitorDao = new MonitorDao();

    public boolean deviceProvision(MonitorBean monitorBean)
    {

        try 
        {

            /*MonitorHelper monitorHelper = new MonitorHelper();

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
*/


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return false;
    }

    public boolean monitorExecutorGetAllData(MonitorBean monitorBean)
    {
        try
        {

            List<Map<String, Object>> list;

            List<MonitorBean> monitorBeanList = new ArrayList<>();

            list = monitorDao.monitorDaoGetAllData();

            for (int i = 0; i < list.size(); i++)
            {
                MonitorBean monitorBeanData = new MonitorBean();

                monitorBeanData.setId((Integer) list.get(i).get("Id"));

                monitorBeanData.setName(String.valueOf(list.get(i).get("Name")));

                monitorBeanData.setIp((String) list.get(i).get("IP"));

                monitorBeanData.setType((String) list.get(i).get("Type"));

                monitorBeanData.setAvailability((String) list.get(i).get("Availability"));

                monitorBeanList.add(monitorBeanData);
            }

            monitorBean.setMonitorBeanList(monitorBeanList);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return true;
    }

    public boolean monitorExecutorDelete(MonitorBean monitorBean)
    {
        try
        {
            if(monitorDao.monitorDaoDeleteData(monitorBean.getId()))
            {
                return true;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return false;
    }

    public boolean monitorExecutorGetChartsData(MonitorBean monitorBean)
    {
        try
        {

            List<Object> pingShowResultList;

            List<Object> sshShowResultList;

            List<Integer> pingStatusList;

            List<Integer> sshShowStatusList;

            Map<Timestamp, Float> map;

            Map<Timestamp, Float> mapofRtt;

            PollingDao pollingDao = new PollingDao();

            if (monitorBean.getType().equals("Ping"))
            {
                pingShowResultList = pollingDao.pollingDaoGetPingData(monitorBean.getId());

                if (!pingShowResultList.isEmpty())
                {
                    monitorBean.setMatrixList(pingShowResultList);
                }

                pingStatusList = (pollingDao.pollingDaoGetPingAvailabilityData(monitorBean.getId()));

                if (!pingStatusList.isEmpty())
                {
                    monitorBean.setPingStatusList(pingStatusList);
                }

                mapofRtt = pollingDao.pollingDaoGetPingRttData(monitorBean.getId());

                if (!mapofRtt.isEmpty())
                {
                    monitorBean.setRttMap(mapofRtt);
                }

                return true;
            }

            else if (monitorBean.getType().equals("SSH"))
            {

                sshShowResultList = pollingDao.pollingDaoGetSSHData(monitorBean.getId());

                if (!sshShowResultList.isEmpty())
                {
                    monitorBean.setSshMatrixList(sshShowResultList);
                }

                sshShowStatusList = (pollingDao.pollingDaoGetSSHAvailabilityData(monitorBean.getId()));

                if (!sshShowStatusList.isEmpty())
                {
                    monitorBean.setSshStatusList(sshShowStatusList);
                }

                map = pollingDao.pollingDaoGetSSHCpuData(monitorBean.getId());

                if (!map.isEmpty())
                {
                    monitorBean.setCpuMap(map);
                }


                return true;
            }

            else
            {
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
