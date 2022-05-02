package executor;

import bean.DashboardBean;
import dao.Database;
import helper.Logger;

import java.util.*;

/**
 * Created by smit on 31/3/22.
 */
public class DashboardExecutor
{
    private Database database = new Database();

    private Logger _logger = new Logger();

    public boolean dashboardShowData(DashboardBean dashboardBean)
    {
        try
        {
            ArrayList<Object> data = new ArrayList<>();

            HashMap<String, Long> hashMap = new HashMap<>();

            HashMap<String, Float> topFiveCpuHashMap = new HashMap<>();

            List<HashMap<String, Object>> dashboardMatrixData = new ArrayList<>();

            List<HashMap<String, Object>> topFiveCpuData = null;

            List<HashMap<String, Object>> topFiveMemoryData = null;

            dashboardMatrixData = database.fireSelectQuery("SELECT count(*), Availability from Monitor group by Availability;", data);

            topFiveCpuData = database.fireSelectQuery("select max(CPU) as CPU, IP from SSHPolling where PollingTime > now() - interval 1 hour group by IP  order by CPU desc limit 5", data);

            topFiveMemoryData = database.fireSelectQuery("select max(Memory) as Memory, IP from SSHPolling where PollingTime > now() - interval 1 hour group by IP  order by Memory desc limit 5", data);

            if (dashboardMatrixData != null && !dashboardMatrixData.isEmpty())
            {
                for (HashMap<String, Object> foreachDashboardMatrixData : dashboardMatrixData) 
                {
                    hashMap.put((String) foreachDashboardMatrixData.get("Availability"), (Long) foreachDashboardMatrixData.get("count(*)"));

                    dashboardBean.setHashMap(hashMap);
                }
                
                if (topFiveCpuData !=null && !topFiveCpuData.isEmpty())
                {
                    for (HashMap<String, Object> foreachTopFiveCpu: topFiveCpuData) {

                        topFiveCpuHashMap.put((String) foreachTopFiveCpu.get("IP"), (Float)foreachTopFiveCpu.get("CPU"));

                        dashboardBean.setTopFiveCpuHashMap(topFiveCpuHashMap);
                    }

                    return true;
                }

                return true;
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();

            _logger.error("DashboardExecutor dashboardExecutorFetchData method having error", exception);
        }

        return false;
    }
}
