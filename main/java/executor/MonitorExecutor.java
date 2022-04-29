package executor;

import bean.MonitorBean;
import com.sun.jmx.remote.internal.ArrayQueue;
import dao.Database;
import helper.Logger;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by smit on 20/3/22.
 */
public class MonitorExecutor
{
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
            HashMap<String, Object> pingResultHashMap;

            HashMap<String, Object> pingStatusHashMap;

            HashMap<Float, Timestamp> pingRTTPerPollingTimeHashMap = new LinkedHashMap<>();

            HashMap<String, Object> sshMatrixResultHashMap;

            HashMap<String, Object> sshStatusHashMap;

            List<HashMap<String, Object>> pingShowResultList;

            List<HashMap<String, Object>> sshShowResultList;

            List<HashMap<String, Object>> pingStatusList;

            List<HashMap<String, Object>> sshShowStatusList;

            List<HashMap<String, Object>> ipForChart;

            List<HashMap<String, Object>> hashMapList;

            List<HashMap<String, Object>> hashMapCpuPollingTimeList;

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

                pingShowResultList = database.fireSelectQuery("select SendPacket, ReceivePacket, PacketLoss, RTT, PollingTime FROM PingPolling p1 WHERE Id=? and PollingTime = (select max(PollingTime) from PingPolling p2 where p1.Id = p2.Id)" , data);

                if (pingShowResultList !=null && !pingShowResultList.isEmpty())
                {
                    pingResultHashMap = pingShowResultList.get(0);

                    monitorBean.setPingMatrixHashMap(pingResultHashMap);
                }

                data = new ArrayList<>();

                data.add(monitorBean.getId());

                pingStatusList = database.fireSelectQuery("SELECT SUM(Status='Up') , SUM(Status='Down') from PingPolling where Id = ? and PollingTime > now() - interval 1 day;", data);

                if (pingShowResultList != null && !pingStatusList.isEmpty())
                {
                    pingStatusHashMap = pingStatusList.get(0);

                    monitorBean.setPingStatusHashMap(pingStatusHashMap);
                }

                data = new ArrayList<>();

                data.add(monitorBean.getId());

                hashMapList = database.fireSelectQuery("SELECT RTT, PollingTime FROM PingPolling where Id=? ORDER BY PollingTime DESC LIMIT 10;" , data);

                /*if (hashMapList !=null && !hashMapList.isEmpty())
                {
                    for (int i = 0; i < hashMapList.size() ; i++)
                    {
                        pingRTTPerPollingTimeHashMap.put((Float) hashMapList.get(i).get("RTT"), (Timestamp) hashMapList.get(i).get("PollingTime"));
                    }

                    monitorBean.setPingRTTPerPollingTimeHashMap(pingRTTPerPollingTimeHashMap);
                }*/

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

                sshShowResultList = database.fireSelectQuery("select CPU, Memory, Disk, SwapMemory, PollingTime  FROM SSHPolling s1 WHERE Id=? and PollingTime = (select max(PollingTime) from SSHPolling s2 where s1.Id = s2.Id)" , data);

                if (sshShowResultList != null && !sshShowResultList.isEmpty())
                {
                    sshMatrixResultHashMap = sshShowResultList.get(0);

                    monitorBean.setSshMatrixHashMap(sshMatrixResultHashMap);
                }

                data = new ArrayList<>();

                data.add(monitorBean.getId());

                sshShowStatusList = database.fireSelectQuery("SELECT SUM(Status='Up') , SUM(Status='Down') from SSHPolling where Id = ? and PollingTime > now() - interval 1 day;" , data);

                if (sshShowStatusList!=null && !sshShowStatusList.isEmpty())
                {
                    sshStatusHashMap = sshShowStatusList.get(0);

                    monitorBean.setSshStatusHashMap(sshStatusHashMap);
                }

                data = new ArrayList<>();

                data.add(monitorBean.getId());

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
            _logger.error("MonitorExecutor monitorFetchChartData method having error. ", exception);
        }

        return false;
    }
}
