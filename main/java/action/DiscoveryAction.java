package action;

import bean.DiscoveryBean;
import com.opensymphony.xwork2.ModelDriven;
import executor.DiscoveryExecutor;
import helper.Logger;

/**
 * Created by smit on 12/3/22.
 */
public class DiscoveryAction implements ModelDriven
{

    DiscoveryBean discoveryBean = new DiscoveryBean();

    private static final Logger _logger = new Logger();

    public String discoveryFetchData()
    {

        try
        {
            DiscoveryExecutor discoveryExecutor= new DiscoveryExecutor();

            if(discoveryExecutor.discoveryFetchData(discoveryBean))
            {
                return "success";
            }
        }
        catch (Exception exception)
        {
            _logger.error("DiscoveryActionFetchData discoveryActionFetchData method having error. ", exception);
        }

        return "failure";
    }

    public String discoveryFetchUsername()
    {
        try
        {
            DiscoveryExecutor discoveryExecutor= new DiscoveryExecutor();

            if(discoveryExecutor.discoveryFetchUsername(discoveryBean))
            {
                return "success";
            }
        }
        catch (Exception exception)
        {
            _logger.error("DiscoveryActionFetchUsernameData discoveryActionFetchUsernameData method having error. ", exception);
        }

        return "failure";
    }

    public String discoveryInsert()
    {
        try
        {
            DiscoveryExecutor discoveryExecutor = new DiscoveryExecutor();

            if(discoveryExecutor.discoveryInsert(discoveryBean))
            {

                return "success";
            }
        }
        catch (Exception exception)
        {
            _logger.error("DiscoveryActionInsert discoveryActionInsertInDatabase method having error. ", exception);
        }

        return "failure";
    }

    public String discoveryUpdate()
    {
        try
        {
            DiscoveryExecutor discoveryExecutor= new DiscoveryExecutor();

            if(discoveryExecutor.discoveryUpdate(discoveryBean))
            {
                return "success";
            }
        }
        catch (Exception exception)
        {
            _logger.error("DiscoveryAction discoveryUpdate method having error. ", exception);
        }

        return "failure";
    }

    public String discoveryDelete()
    {
        try
        {
            DiscoveryExecutor discoveryExecutor= new DiscoveryExecutor();

            if(discoveryExecutor.discoveryDelete(discoveryBean))
            {
                return "success";
            }
        }
        catch (Exception exception)
        {
            _logger.error("DiscoveryAction discoveryDelete method having error. ", exception);

        }
        return "failure";
    }

    @Override
    public Object getModel()
    {
        return discoveryBean;
    }
}
