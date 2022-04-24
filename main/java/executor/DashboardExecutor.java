package executor;

import bean.DashboardBean;
import dao.DashboardDao;
import dao.Database;
import helper.Logger;

import java.util.*;

/**
 * Created by smit on 31/3/22.
 */
public class DashboardExecutor
{
    DashboardDao dashboardDao = null;

    private Database database = new Database();

    private List<Integer> dashboardList = null;

    private List<HashMap<String, Object>> topFiveCpuData = new ArrayList<>();

    private List<HashMap<String, Object>> dashboardMatrixData;

    HashMap<String, Object> hashMap = null;

    private ArrayList<Object> data = null;

    private Logger _logger = new Logger();

    public boolean dashboardShowData(DashboardBean dashboardBean)
    {
        ArrayList<Object> dashboardData = new ArrayList<>();

        try
        {
            dashboardDao = new DashboardDao();

            data = new ArrayList<>();

            dashboardMatrixData = new ArrayList<>();

            dashboardList = new ArrayList<>();

            //dashboardList = dashboardDao.dashboardShowData();

            dashboardMatrixData = database.fireSelectQuery("SELECT count(*), Availability from Monitor group by Availability;", data);

            //topFiveCpuData  = dashboardDao.dashboardFetchTopFiveCPUUtilizationData();

            topFiveCpuData  = database.fireSelectQuery("select max(CPU) as CPU, IP from SSHPolling where PollingTime > now() - interval 1 hour group by IP  order by CPU desc limit 5", data);

            if (dashboardMatrixData != null && !dashboardMatrixData.isEmpty())
            {
                dashboardBean.setDashboardList(dashboardMatrixData);

                if (!topFiveCpuData.isEmpty())
                {
                    dashboardBean.setTopFiveCpuData(topFiveCpuData);

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
