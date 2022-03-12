package executor;

import dao.DiscoveryDao;

/**
 * Created by smit on 12/3/22.
 */
public class DiscoveryExecutor
{
    DiscoveryDao discoveryDao = new DiscoveryDao();

    public boolean discovery()
    {


        try
        {


            if(true)
            {
                return true;
            }

            else
            {
                return false;
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }
}
