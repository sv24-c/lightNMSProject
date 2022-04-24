package action;

import bean.MonitorBean;
import com.opensymphony.xwork2.ModelDriven;
import executor.MonitorExecutor;
import helper.Logger;
import helper.MultipleDiscovery;

/**
 * Created by smit on 20/3/22.
 */

public class MonitorAction implements ModelDriven
{

    private MonitorBean monitorBean = new MonitorBean();

    private static final Logger _logger = new Logger();

    public String provision()
    {
        try
        {
            if(MultipleDiscovery.multipleDiscoveryAddInQueue(monitorBean.getId()))
            {
                monitorBean.setStatus("Added to queue");

                return "success";
            }

            else
            {
                monitorBean.setStatus("Not Added to queue");

                return "failure";
            }

        }

        catch (Exception exception)
        {
            _logger.error("MonitorAction provision method having error. ", exception);

        }

        return "failure";
    }

    public String monitorFetchAllData()
    {
        try
        {
            MonitorExecutor monitorExecutor = new MonitorExecutor();

            if(monitorExecutor.monitorFetchAllData(monitorBean))
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
            _logger.error("MonitorAction provision method having error. ", exception);

        }

        return "failure";
    }

    public String monitorDelete()
    {
        try
        {
            MonitorExecutor monitorExecutor = new MonitorExecutor();

            if(monitorExecutor.monitorDelete(monitorBean))
            {
                return "success";
            }
        }
        catch (Exception exception)
        {
            _logger.error("MonitorAction monitorDelete method having error. ", exception);
        }

        return "failure";
    }

    public String monitorFetchChartData()
    {

        try
        {
            MonitorExecutor monitorExecutor = new MonitorExecutor();

            if(monitorExecutor.monitorFetchChartData(monitorBean))
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
            _logger.error("MonitorAction monitorFetchChartData method having error. ", exception);
        }

        return "failure";
    }

    @Override
    public Object getModel() {
        return monitorBean;
    }
}
