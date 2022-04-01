package action;

import bean.DashboardBean;
import com.opensymphony.xwork2.ModelDriven;
import executor.DashboardExecutor;

/**
 * Created by smit on 31/3/22.
 */
public class DashboardAction implements ModelDriven
{
    DashboardBean dashboardBean = new DashboardBean();

    public String dashboardData()
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

        catch (Exception e)
        {
            e.printStackTrace();
        }

        return "failure";
    }

    @Override
    public Object getModel() {
        return dashboardBean;
    }
}
