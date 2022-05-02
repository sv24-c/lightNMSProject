package executor;

import bean.DiscoveryBean;
import dao.Database;
import helper.Logger;
import helper.MonitorHelper;
import org.apache.commons.codec.binary.Base64;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by smit on 12/3/22.
 */
public class DiscoveryExecutor
{
    private Database database = new Database();

    private static final Logger _logger = new Logger();

    ArrayList<Object> data = null;

    private Base64 base64 = new Base64();

    static int id =0;

    public boolean discoveryFetchData(DiscoveryBean discoveryBean)
    {
        List<HashMap<String, Object>> discoveryFetchDataList;

        List<DiscoveryBean> discoveryBeanList;

        try
        {
            data = new ArrayList<>();

            discoveryBeanList = new ArrayList<>();

            discoveryFetchDataList = database.fireSelectQuery("SELECT Id, Name, IP, Type FROM Discovery", data);

            if (discoveryFetchDataList != null && !discoveryFetchDataList.isEmpty())
            {
                for (int i = 0; i < discoveryFetchDataList.size(); i++)
                {
                    DiscoveryBean discovery = new DiscoveryBean();

                    discovery.setId((Integer) discoveryFetchDataList.get(i).get("Id"));

                    discovery.setName((String) discoveryFetchDataList.get(i).get("Name"));

                    discovery.setIP((String) discoveryFetchDataList.get(i).get("IP"));

                    discovery.setType((String) discoveryFetchDataList.get(i).get("Type"));

                    discoveryBeanList.add(discovery);
                }

                discoveryBean.setDiscoveryBeanList(discoveryBeanList);

                return true;
            }
        }
        catch (Exception exception)
        {
            _logger.error("DiscoveryExecutionFetchData discoveryExecutionFetchData method having error. ", exception);
        }

        return false;
    }

    public boolean discoveryFetchUsername(DiscoveryBean discoveryBean)
    {
        List<HashMap<String, Object>> username = null;

        List<HashMap<String, Object>> fetchData = null;

        try
        {
            data = new ArrayList<>();

            id = discoveryBean.getId();

            data.add(discoveryBean.getId());

            fetchData = database.fireSelectQuery("SELECT Name, IP, Type FROM Discovery WHERE Id=?", data);

            if (fetchData != null && !fetchData.isEmpty())
            {
                discoveryBean.setName((String) fetchData.get(0).get("Name"));

                discoveryBean.setIP((String) fetchData.get(0).get("IP"));

                discoveryBean.setType((String) fetchData.get(0).get("Type"));

                if (fetchData.get(0).get("Type").equals("SSH"))
                {
                    username = database.fireSelectQuery("SELECT Username FROM Credential WHERE Id=?" , data);

                    if (username!=null && !username.isEmpty())
                    {
                        if(discoveryBean.setUsername((String) username.get(0).get("Username")))
                        {
                            return true;
                        }
                    }
                    else
                    {
                        _logger.info("Username fetch data at edit time is null.");
                    }
                }
            }
        }
        catch (Exception exception)
        {
            _logger.error("DiscoveryExecutorFetchUsernameData discoveryExecutorFetchUsernameData method having error. ", exception);
        }

        return false;
    }

    public boolean discoveryInsert(DiscoveryBean discoveryBean)
    {
        try
        {
            data = new ArrayList<>();

            data.add(discoveryBean.getIP());

            data.add(discoveryBean.getType());

            if (!database.fireSelectQuery("SELECT IP, Type FROM Discovery WHERE IP =? AND TYPE = ?", data).isEmpty())
            {
                discoveryBean.setStatus(discoveryBean.getIP()+" and "+discoveryBean.getType()+" already in Discovery");

                return true;
            }
            else
            {
                String encodedPassword = new String(base64.encode(discoveryBean.getPassword().getBytes()));

                if (discoveryBean.getType().equals("SSH") && discoveryBean.getUsername()!=null && discoveryBean.getPassword()!=null)
                {
                    String returnSSHResult = new MonitorHelper().ssh(discoveryBean.getUsername(), encodedPassword, discoveryBean.getIP());

                    if (returnSSHResult.equals("Linux"))
                    {
                        data = new ArrayList<>();

                        data.add(discoveryBean.getName());

                        data.add(discoveryBean.getIP());

                        data.add(discoveryBean.getType());

                        database.fireExecuteUpdate("INSERT INTO Discovery (Name, IP, Type) VALUES(?,?,?)" , data);

                        data = new ArrayList<>();

                        data.add(discoveryBean.getIP());

                        data.add(discoveryBean.getType());

                        List<HashMap<String, Object>> id = database.fireSelectQuery("SELECT Id from Discovery where IP = ? AND Type = ? " , data);

                        data = new ArrayList<>();

                        data.add(id.get(0).get("Id"));

                        data.add(discoveryBean.getUsername());

                        data.add(encodedPassword);


                        if(database.fireExecuteUpdate("INSERT INTO Credential (Id, Username, Password) VALUES(?,?,?)" , data) >=1)
                        {
                            discoveryBean.setStatus(discoveryBean.getIP()+" of "+discoveryBean.getType()+" type added to Discovery");

                            return true;
                        }
                    }
                    else
                    {
                        discoveryBean.setStatus(returnSSHResult);
                    }
                }
                else
                {
                    data = new ArrayList<>();

                    data.add(discoveryBean.getName());

                    data.add(discoveryBean.getIP());

                    data.add(discoveryBean.getType());

                    database.fireExecuteUpdate("INSERT INTO Discovery (Name, IP, Type) VALUES(?,?,?)" , data);

                    discoveryBean.setStatus(discoveryBean.getIP()+" of "+discoveryBean.getType()+" type added to Discovery");
                }

                    return true;
            }
        }
        catch (Exception exception)
        {
            _logger.error("DiscoveryExecutor discoveryExecutorInsertInDatabase method having error. ", exception);
        }

        return false;
    }

    public boolean discoveryUpdate(DiscoveryBean discoveryBean)
    {
        try
        {
            data = new ArrayList<>();

            String encodedPassword = new String(base64.encode(discoveryBean.getPassword().getBytes()));

            data.add(discoveryBean.getName());

            data.add(discoveryBean.getIP());

            data.add(id);

            if(database.fireExecuteUpdate("UPDATE Discovery SET Name=? , IP=? where Id=? ", data) >=1)
            {
                if (discoveryBean.getType().equals("SSH"))
                {
                    data = new ArrayList<>();

                    data.add(discoveryBean.getUsername());

                    data.add(encodedPassword);

                    data.add(id);

                    if (database.fireExecuteUpdate("UPDATE Credential SET Username=? , Password=? where Id=? ", data) >= 1)
                    {
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
                return true;
            }
        }
        catch (Exception exception)
        {
            _logger.error("DiscoveryExecutor discoveryUpdate method having error. ", exception);
        }

        return false;
    }

    public boolean discoveryDelete(DiscoveryBean discoveryBean)
    {

        try
        {
            data = new ArrayList<>();

            data.add(discoveryBean.getId());

            if(database.fireExecuteUpdate("DELETE FROM Discovery WHERE Id = ?" , data) >=1)
            {
                return true;
            }
        }
        catch (Exception exception)
        {
            _logger.error("DiscoveryExecutor discoveryDelete method having error. ", exception);
        }

        return false;
    }
}
