package action;

import bean.MonitorBean;
import executor.MonitorExecutor;

/**
 * Created by smit on 20/3/22.
 */

public class MonitorAction
{
    MonitorExecutor monitorExecutor;

    MonitorBean monitorBean = new MonitorBean();

    public String monitor()
    {

        try
        {
            new MonitorExecutor().monitorPing();

        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

        return "success";
    }

}
