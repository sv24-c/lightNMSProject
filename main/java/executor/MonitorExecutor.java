package executor;

import bean.MonitorBean;
import dao.Database;
import dao.MonitorDao;
import dao.PollingDao;
import helper.Logger;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by smit on 20/3/22.
 */
public class MonitorExecutor
{
    private MonitorDao monitorDao = new MonitorDao();

    private Logger _logger = new Logger();

    private Database database = new Database();

    private ArrayList<Object> data = null;

    public boolean monitorFetchAllData(MonitorBean monitorBean)
    {
        try
        {
            data = new ArrayList<>();

            List<HashMap<String, Object>> list;

            List<MonitorBean> monitorBeanList = new ArrayList<>();

            //list = monitorDao.monitorFetchAllData();

            list = database.fireSelectQuery("SELECT Id, Name, IP, Type, Availability FROM Monitor", data);

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
        catch (Exception exception)
        {
            _logger.error("MonitorExecutor monitorFetchAllData method having error. ", exception);

        }

        return true;
    }

    public boolean monitorDelete(MonitorBean monitorBean)
    {
        try
        {
            data = new ArrayList<>();

            data.add(monitorBean.getId());

            /*if(monitorDao.monitorDelete(monitorBean.getId()))
            {
                return true;
            }*/

            if(database.fireExecuteUpdate("DELETE FROM Monitor WHERE Id = ?" , data) >=1)
            {
                return true;
            }
        }
        catch (Exception exception)
        {
            _logger.error("MonitorExecutor monitorDelete method having error. ", exception);
        }

        return false;
    }

    public boolean monitorFetchChartData(MonitorBean monitorBean)
    {
        try
        {

            List<HashMap<String, Object>> pingShowResultList;

            List<HashMap<String, Object>> sshShowResultList;

            List<HashMap<String, Object>> pingStatusList;

            List<HashMap<String, Object>> sshShowStatusList;

            List<HashMap<String, Object>> ipForChart;

            List<HashMap<String, Object>> hashMapList;

            List<HashMap<String, Object>> hashMapCpuPollingTimeList;

            Map<Timestamp, Float> map;

            HashMap<Timestamp, Float> mapofRtt;

            PollingDao pollingDao = new PollingDao();

            //ipForChart = monitorDao.monitorFetchIp(monitorBean.getId());

            data = new ArrayList<>();

            data.add(monitorBean.getId());

            ipForChart = (database.fireSelectQuery("SELECT IP FROM Monitor where Id = ?",data));

            if (!ipForChart.isEmpty())
            {
                monitorBean.setIpForChart((String) ipForChart.get(0).get("IP"));
            }

            if (monitorBean.getType().equals("Ping"))
            {
                data = new ArrayList<>();

                data.add(monitorBean.getId());

                //pingShowResultList = pollingDao.pollingFetchPingData(monitorBean.getId());

                pingShowResultList = database.fireSelectQuery("select SendPacket, ReceivePacket, PacketLoss, RTT, PollingTime FROM PingPolling p1 WHERE Id=? and PollingTime = (select max(PollingTime) from PingPolling p2 where p1.Id = p2.Id)" , data);

                if (!pingShowResultList.isEmpty())
                {
                    monitorBean.setMatrixList(pingShowResultList);
                }

                data = new ArrayList<>();

                data.add(monitorBean.getId());

                //pingStatusList = (pollingDao.pollingPingFetchAvailabilityData(monitorBean.getId()));

                pingStatusList = database.fireSelectQuery("SELECT SUM(Status='Up') , SUM(Status='Down') from PingPolling where Id = ? and PollingTime > now() - interval 1 day;", data);

                if (!pingStatusList.isEmpty())
                {
                    monitorBean.setPingStatusList(pingStatusList);
                }

                data = new ArrayList<>();

                data.add(monitorBean.getId());

                //mapofRtt = pollingDao.pollingPingFetchRttData(monitorBean.getId());

                hashMapList = database.fireSelectQuery("SELECT RTT, PollingTime FROM PingPolling where Id=? ORDER BY PollingTime DESC LIMIT 10;" , data);

                if (!hashMapList.isEmpty())
                {
                    monitorBean.setRttMap(hashMapList);
                }

                return true;
            }

            else if (monitorBean.getType().equals("SSH"))
            {
                data = new ArrayList<>();

                data.add(monitorBean.getId());

                //sshShowResultList = pollingDao.pollingFetchSSHData(monitorBean.getId());

                sshShowResultList = database.fireSelectQuery("select CPU, Memory, Disk, SwapMemory, PollingTime  FROM SSHPolling s1 WHERE Id=? and PollingTime = (select max(PollingTime) from SSHPolling s2 where s1.Id = s2.Id)" , data);

                if (!sshShowResultList.isEmpty())
                {
                    monitorBean.setSshMatrixList(sshShowResultList);
                }

                data = new ArrayList<>();

                data.add(monitorBean.getId());

                //sshShowStatusList = (pollingDao.pollingSSHFetchAvailabilityData(monitorBean.getId()));

                sshShowStatusList = database.fireSelectQuery("SELECT SUM(Status='Up') , SUM(Status='Down') from SSHPolling where Id = ? and PollingTime > now() - interval 1 day;" , data);

                if (!sshShowStatusList.isEmpty())
                {
                    monitorBean.setSshStatusList(sshShowStatusList);
                }

                data = new ArrayList<>();

                data.add(monitorBean.getId());

                //map = pollingDao.pollingSSHFetchCpuData(monitorBean.getId());

                hashMapCpuPollingTimeList = database.fireSelectQuery("SELECT CPU, PollingTime FROM SSHPolling where Id=? ORDER BY PollingTime DESC LIMIT 10;" , data);

                if (!hashMapCpuPollingTimeList.isEmpty())
                {
                    monitorBean.setCpuMap(hashMapCpuPollingTimeList);
                }

                return true;
            }
            else
            {
                return false;
            }
        }
        catch (Exception exception)
        {
            _logger.error("MonitorDao monitorFetchChartData method having error. ", exception);
        }

        return false;
    }

}
