package action;

import bean.DiscoveryBean;
import com.opensymphony.xwork2.ModelDriven;
import executor.DiscoveryExecutor;

/**
 * Created by smit on 12/3/22.
 */
public class DiscoveryAction implements ModelDriven
{

    DiscoveryExecutor discoveryExecutor = new DiscoveryExecutor();

    DiscoveryBean discoveryBean = new DiscoveryBean();

    public String discovery()
    {

        try
        {

            discoveryExecutor.discoveryShowData(discoveryBean);
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

        return "success";
    }

    @Override
    public Object getModel() {
        return discoveryBean;
    }
}
