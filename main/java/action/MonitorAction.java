package action;

import bean.MonitorBean;
import com.opensymphony.xwork2.ModelDriven;
import executor.MonitorExecutor;

/**
 * Created by smit on 20/3/22.
 */

public class MonitorAction implements ModelDriven
{

    private MonitorBean monitorBean = new MonitorBean();

    public String provision()
    {

        try
        {

            MonitorExecutor monitorExecutor = new MonitorExecutor();

            if(monitorExecutor.deviceProvision(monitorBean))
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

    public String monitorActionGetAllData()
    {
        try
        {
            MonitorExecutor monitorExecutor = new MonitorExecutor();

            if(monitorExecutor.monitorExecutorGetAllData(monitorBean))
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

    public String monitorActionDelete()
    {
        try
        {
            MonitorExecutor monitorExecutor = new MonitorExecutor();

            monitorExecutor.monitorExecutorDelete(monitorBean);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return "success";
    }

    public String monitorActionGetChartData()
    {

        try
        {
            MonitorExecutor monitorExecutor = new MonitorExecutor();

            if(monitorExecutor.monitorExecutorGetChartsData(monitorBean))
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
        return monitorBean;
    }
}
