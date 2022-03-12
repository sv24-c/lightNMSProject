package executor;

import dao.LoginDao;
import action.LoginAction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by smit on 11/3/22.
 */
public class LoginExecutor
{

    LoginDao loginDao = new LoginDao();

    public boolean login(String userName, String password)
    {

        List<Map<String, Object>> list = new ArrayList<>();

        list = loginDao.logIn(userName, password);

        //Map<String, Object> mapData = list.get(0);

        Map<String, Object> mapData = null;

        try
        {
            if (list !=null && list.size() != 0)
            {
                mapData = list.get(0);
            }

            String uname = String.valueOf(mapData.get("UserName"));

            String pass = String.valueOf(mapData.get("Password"));

            if(uname.equals(userName) && pass.equals(password))
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
