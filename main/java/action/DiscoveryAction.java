package action;

import bean.DiscoveryBean;
import com.opensymphony.xwork2.ModelDriven;
import executor.DiscoveryExecutor;

/**
 * Created by smit on 12/3/22.
 */
public class DiscoveryAction implements ModelDriven
{

    DiscoveryExecutor discoveryExecutor= new DiscoveryExecutor();

    DiscoveryBean discoveryBean = new DiscoveryBean();

    public String discovery()
    {
        DiscoveryExecutor discoveryExecutor = new DiscoveryExecutor();

        try
        {
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
        DiscoveryExecutor discoveryExecutor = new DiscoveryExecutor();

        try
        {
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
            if(new DiscoveryExecutor().discoveryInsertInDatabase(discoveryBean))
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
