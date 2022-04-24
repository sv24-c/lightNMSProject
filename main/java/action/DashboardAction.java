package action;

import bean.DashboardBean;
import com.opensymphony.xwork2.ModelDriven;
import executor.DashboardExecutor;
import helper.Logger;

/**
 * Created by smit on 31/3/22.
 */
public class DashboardAction implements ModelDriven
{
    DashboardBean dashboardBean = new DashboardBean();

    private final Logger _logger = new Logger();

    public String dashboardData()//dashboardFetchData()
    {
        try
        {
            DashboardExecutor dashboardExecutor = new DashboardExecutor();

            if(dashboardExecutor.dashboardShowData(dashboardBean))
            {
                return "success";
            }
            else
            {
                return "failure";
            }
        }
        catch (Exception exception)
        {
            _logger.error("DashboardAction dashboardActionFetchData method having error", exception);
        }

        return "failure";
    }

    @Override
    public Object getModel()
    {
        return dashboardBean;
    }
}
