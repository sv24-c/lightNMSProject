package executor;

import bean.DashboardBean;
import dao.DashboardDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smit on 31/3/22.
 */
public class DashboardExecutor
{
    public boolean dashboardShowData(DashboardBean dashboardBean)
    {

        DashboardDao dashboardDao = new DashboardDao();

        try
        {
            if (!dashboardDao.dashboardShowData().isEmpty())
            {
                /*dashboardBean.setUpCount((Integer) dashboardDao.dashboardShowData().get(0));

                dashboardBean.setDownCount((Integer) dashboardDao.dashboardShowData().get(1));

                dashboardBean.setUnknownCount((Integer) dashboardDao.dashboardShowData().get(2));
                */

                dashboardBean.setDashboardList(dashboardDao.dashboardShowData());

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
