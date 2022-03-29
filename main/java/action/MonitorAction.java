package action;

import bean.MonitorBean;
import bean.PollingBean;
import com.opensymphony.xwork2.ModelDriven;
import dao.PollingDao;
import executor.MonitorExecutor;
import helper.MonitorHelper;

import java.io.StringReader;

/**
 * Created by smit on 20/3/22.
 */

public class MonitorAction implements ModelDriven
{

    MonitorBean monitorBean = new MonitorBean();

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

    public String monitorShowInTable()
    {

        try
        {
            MonitorExecutor monitorExecutor = new MonitorExecutor();

            PollingBean pollingBean = new PollingBean();

            if(monitorExecutor.monitorShowAllDataInTable(pollingBean))
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
