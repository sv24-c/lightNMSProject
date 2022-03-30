package executor;

import bean.DiscoveryBean;
import dao.DiscoveryDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by smit on 12/3/22.
 */
public class DiscoveryExecutor
{
    private DiscoveryDao discoveryDao = new DiscoveryDao();

    public boolean discoveryShowData(DiscoveryBean discoveryBean)
    {

        try
        {

            List<Map<String, Object>> list;

            List<DiscoveryBean> discoveryBeanList = new ArrayList<>();

            list = discoveryDao.discoveryShowData();

            for (int i = 0; i < list.size(); i++)
            {
                DiscoveryBean discovery = new DiscoveryBean();

               discovery.setId((Integer) list.get(i).get("Id"));

               discovery.setName((String) list.get(i).get("Name"));

               discovery.setIP((String) list.get(i).get("IP"));

               discovery.setType((String) list.get(i).get("Type"));

               discoveryBeanList.add(discovery);

            }

            discoveryBean.setDiscoveryBeanList(discoveryBeanList);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return true;
    }

    public boolean discoveryGetUsernameData(DiscoveryBean discoveryBean)
    {
        DiscoveryDao discoveryDao = new DiscoveryDao();

        try
        {

          discoveryBean.setUsername(discoveryDao.discoveryGetUsernameDaoData(discoveryBean.getId()));
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

        return true;
    }

    public boolean discoveryInsertInDatabase(DiscoveryBean discoveryBean)
    {

        try
        {

            List<Map<String, Object>> list;

            new DiscoveryDao().discovery(discoveryBean.getName(), discoveryBean.getIP(), discoveryBean.getType(), discoveryBean.getUsername(), discoveryBean.getPassword());

            return true;

            /*list = discoveryDao.discoveryCheckRedundantData(discoveryBean.getIP(), discoveryBean.getType());

            if (!list.isEmpty())
            {
                new DiscoveryDao().discovery(discoveryBean.getName(), discoveryBean.getIP(), discoveryBean.getType(), discoveryBean.getUsername(), discoveryBean.getPassword());

                return true;
            }
            else
            {
                return false;
            }*/
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

        return false;
    }

    public boolean discoveryUpdateInDatabase(DiscoveryBean discoveryBean)
    {

        try
        {
            new DiscoveryDao().discoveryUpdateData(discoveryBean.getName(), discoveryBean.getIP(), discoveryBean.getUsername(), discoveryBean.getPassword(), discoveryBean.getId());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return true;
    }

    public boolean discoveryDeleteInDatabase(DiscoveryBean discoveryBean)
    {

        try
        {
            new DiscoveryDao().discoveryDeleteData(discoveryBean.getId());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return true;
    }
}
