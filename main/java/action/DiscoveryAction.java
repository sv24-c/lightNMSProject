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

    public String discoveryActionGetData()
    {

        try
        {
            DiscoveryExecutor discoveryExecutor= new DiscoveryExecutor();

            if(discoveryExecutor.discoveryExecutorGetData(discoveryBean))
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

    public String discoveryActionGetUsername()
    {

        try
        {
            DiscoveryExecutor discoveryExecutor= new DiscoveryExecutor();

            if(discoveryExecutor.discoveryExecutorGetUsernameData(discoveryBean))
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

    public String discoveryActionInsert()
    {
        try
        {
            DiscoveryExecutor discoveryExecutor = new DiscoveryExecutor();

            if(discoveryExecutor.discoveryExecutorInsertInDatabase(discoveryBean))
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

    public String discoveryActionUpdate()
    {
        try
        {
            DiscoveryExecutor discoveryExecutor= new DiscoveryExecutor();

            if(discoveryExecutor.discoveryExecutorUpdate(discoveryBean))
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

    public String discoveryActionDelete()
    {
        try
        {
            DiscoveryExecutor discoveryExecutor= new DiscoveryExecutor();

            if(discoveryExecutor.discoveryExecutorDelete(discoveryBean))
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
    public Object getModel()
    {
        return discoveryBean;
    }
}
