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

    public boolean discoveryExecutorGetData(DiscoveryBean discoveryBean)
    {
        List<Map<String, Object>> list;

        List<DiscoveryBean> discoveryBeanList;

        try
        {

            discoveryBeanList = new ArrayList<>();

            list = discoveryDao.discoveryDaoGetData();

            if (!list.isEmpty())
            {
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

                return true;
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return false;
    }

    public boolean discoveryExecutorGetUsernameData(DiscoveryBean discoveryBean)
    {
        DiscoveryDao discoveryDao;

        String userName = null;

        try
        {
          //discoveryBean.setUsername(discoveryDao.discoveryGetUsernameDaoData(discoveryBean.getId()));

            discoveryDao = new DiscoveryDao();

            userName = discoveryDao.discoveryDaoGetUsernameData(discoveryBean.getId());

            if (!userName.isEmpty())
            {
                if(discoveryBean.setUsername(userName))
                {
                    return true;
                }
            }
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

        return false;
    }

    public boolean discoveryExecutorInsertInDatabase(DiscoveryBean discoveryBean)
    {
        List<String> list;

        try
        {

            list = discoveryDao.discoveryDaoCheckRedundantData(discoveryBean.getIP(), discoveryBean.getType());

            if (!list.isEmpty())
            {
                discoveryBean.setStatus(discoveryBean.getIP()+" and "+discoveryBean.getType()+" already in Discovery");

                return true;
            }
            else
            {
                if(discoveryDao.discoveryDaoInsertData(discoveryBean.getName(), discoveryBean.getIP(), discoveryBean.getType(), discoveryBean.getUsername(), discoveryBean.getPassword()))
                {
                    discoveryBean.setStatus(discoveryBean.getIP()+" and "+discoveryBean.getType()+" added to Discovery");

                    return true;
                }
            }
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

        return false;
    }

    public boolean discoveryExecutorUpdate(DiscoveryBean discoveryBean)
    {

        try
        {
            if(discoveryDao.discoveryDaoUpdateData(discoveryBean.getName(), discoveryBean.getIP(), discoveryBean.getUsername(), discoveryBean.getPassword(), discoveryBean.getId()))
            {
                return true;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return false;
    }

    public boolean discoveryExecutorDelete(DiscoveryBean discoveryBean)
    {

        try
        {
            if(discoveryDao.discoveryDaoDeleteData(discoveryBean.getId()))
            {
                return true;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return false;
    }
}
