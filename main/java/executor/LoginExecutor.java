package executor;

import dao.Database;
import helper.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by smit on 23/4/22.
 */
public class LoginExecutor
{
    List<HashMap<String, Object>> loginListHashMap = null;

    Database database = new Database();

    private static final Logger _logger = new Logger();

    private ArrayList<Object> data = null;

    public boolean logIn(String userName, String password)
    {
        boolean verifyStatus = false;

        try
        {
            data = new ArrayList<>();

            data.add(userName);

            data.add(password);

            loginListHashMap = database.fireSelectQuery("SELECT UserName , Password FROM Login where UserName LIKE BINARY ? AND Password LIKE BINARY ? ", data);

            if (loginListHashMap != null && !loginListHashMap.isEmpty())
            {
                verifyStatus = true;
            }
        }
        catch (Exception exception)
        {
            _logger.error("Credential is not valid!", exception);
        }
        return verifyStatus;
    }


}
