package action;

import bean.MonitorBean;
import com.opensymphony.xwork2.ModelDriven;
import executor.MonitorExecutor;
import helper.MonitorHelper;

import java.io.StringReader;

/**
 * Created by smit on 20/3/22.
 */

public class MonitorAction implements ModelDriven
{

    MonitorBean monitorBean = new MonitorBean();

    MonitorExecutor monitorExecutor = new MonitorExecutor();

    public String monitor()
    {

        try
        {
            if(new MonitorExecutor().monitorPing(monitorBean))
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
            monitorExecutor.monitorDeleteInDatabase(monitorBean);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return "success";
    }

    @Override
    public Object getModel() {
        return monitorBean;
    }
}
