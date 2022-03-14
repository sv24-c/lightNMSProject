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

        Map<String, Object> mapData = null;

        try
        {

            for (int i = 0; i < list.size(); i++)
            {
                mapData = list.get(i);

                discoveryBean.setName((String) mapData.get("Name"));

                discoveryBean.setIP((String) mapData.get("IP"));

                discoveryBean.setType((String) mapData.get("Type"));

                discoveryBeanList.add(discoveryBean);

            }

            discoveryBean.setDiscoveryBeanList(discoveryBeanList);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return true;
    }
}
