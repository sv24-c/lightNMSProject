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

    public String monitor()
    {

        try
        {

            MonitorExecutor monitorExecutor = new MonitorExecutor();

            if(monitorExecutor.monitorPing(monitorBean))
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

    public String showAllData()
    {
        try
        {
            MonitorExecutor monitorExecutor = new MonitorExecutor();

            if(monitorExecutor.monitorShowAllData(monitorBean))
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

    public String monitorDelete()
    {
        try
        {
            MonitorExecutor monitorExecutor = new MonitorExecutor();

            monitorExecutor.monitorDeleteInDatabase(monitorBean);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return "success";
    }

    public String monitorShowInChart()
    {

        try
        {
            MonitorExecutor monitorExecutor = new MonitorExecutor();

            if(monitorExecutor.monitorShowAllDataInCharts(monitorBean))
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
