package executor;

import dao.LoginDao;
import java.util.List;
import java.util.Map;

/**
 * Created by smit on 11/3/22.
 */
public class LoginExecutor
{


    private LoginDao loginDao = new LoginDao();

    public boolean login(String userName, String password)
    {

        List<Map<String, Object>> list;

        list = loginDao.logIn(userName, password);

        //Map<String, Object> mapData = list.get(0);

        Map<String, Object> mapData = null;

        String uname = null;

        String pass = null;

        try
        {
            if (list !=null && list.size() != 0)
            {
                mapData = list.get(0);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        try
        {
            if (mapData != null)
            {
                uname = String.valueOf(mapData.get("UserName"));
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        try
        {
            if (mapData != null)
            {
                pass = String.valueOf(mapData.get("Password"));
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        try
        {

            if (uname != null)
            {
                return uname.equals(userName) && pass.equals(password);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }
}
