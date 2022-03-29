package action;

import bean.DiscoveryBean;
import com.opensymphony.xwork2.ModelDriven;
import executor.DiscoveryExecutor;

/**
 * Created by smit on 12/3/22.
 */
public class DiscoveryAction implements ModelDriven
{

    DiscoveryBean discoveryBean = new DiscoveryBean();

    public String discovery()
    {

        try
        {
            DiscoveryExecutor discoveryExecutor= new DiscoveryExecutor();

            if(discoveryExecutor.discoveryShowData(discoveryBean))
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

    public String discoveryGetUsername()
    {

        try
        {
            DiscoveryExecutor discoveryExecutor= new DiscoveryExecutor();

            if(discoveryExecutor.discoveryGetUsernameData(discoveryBean))
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

    public String discoveryInsert()
    {
        try
        {
            DiscoveryExecutor discoveryExecutor = new DiscoveryExecutor();

            if(discoveryExecutor.discoveryInsertInDatabase(discoveryBean))
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

    public String discoveryUpdate()
    {
        try
        {
            DiscoveryExecutor discoveryExecutor= new DiscoveryExecutor();

            if(discoveryExecutor.discoveryUpdateInDatabase(discoveryBean))
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

    public String discoveryDelete()
    {
        try
        {
            DiscoveryExecutor discoveryExecutor= new DiscoveryExecutor();

            discoveryExecutor.discoveryDeleteInDatabase(discoveryBean);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return "success";
    }

    @Override
    public Object getModel()
    {
        return discoveryBean;
    }
}
