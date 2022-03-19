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
    DiscoveryDao discoveryDao = new DiscoveryDao();

    public boolean discoveryShowData(DiscoveryBean discoveryBean)
    {

        List<Map<String, Object>> list;

        List<DiscoveryBean> discoveryBeanList = new ArrayList<>();

        list = discoveryDao.discoveryShowData();

        try
        {

            for (int i = 0; i < list.size(); i++)
            {
                DiscoveryBean discovery=new DiscoveryBean();

               discovery.setName((String) list.get(i).get("Name"));
               discovery.setIP((String) list.get(i).get("IP"));
               discovery.setType((String) list.get(i).get("Type"));

                /*discoveryBean.setName((String) mapData.get("Name"));

                discoveryBean.setIP((String) mapData.get("IP"));

                discoveryBean.setType((String) mapData.get("Type"));*/

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

    public boolean discoveryInsertInDatabase(DiscoveryBean discoveryBean)
    {

        try
        {
            discoveryDao.discovery(discoveryBean.getName(), discoveryBean.getIP(), discoveryBean.getType(), discoveryBean.getUsername(), discoveryBean.getPassword());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return true;
    }

    public boolean discoveryUpdateInDatabase(DiscoveryBean discoveryBean)
    {

        try
        {
            discoveryDao.discoveryUpdateData(discoveryBean.getName(), discoveryBean.getIP());
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
            discoveryDao.discoveryDeleteData(discoveryBean.getName());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return true;
    }
}
