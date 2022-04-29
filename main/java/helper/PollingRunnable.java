package helper;

import dao.Database;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by smit on 25/3/22.
 */

public class PollingRunnable
{
    private static Logger _logger = new Logger();

    private Database database = new Database();

    ArrayList<Object> data = null;

    ExecutorService executorService = Executors.newFixedThreadPool(2*Runtime.getRuntime().availableProcessors());

    public PollingRunnable()
    {

    }

    public void pollingRunnableMethod()
    {
        try
        {

            String ip = null;

            String type = null;

            String username = null;

            String password = null;

            int id = 0;

            List<HashMap<String, Object>> list = null;

            List<HashMap<String, Object>> usernamePasswordList = null;

            data = new ArrayList<>();

            list = database.fireSelectQuery("SELECT Id, Name, IP, Type, Availability FROM Monitor", data);

            for (int i = 0; i < list.size(); i++)
            {
                id = (int) list.get(i).get("Id");

                ip   = (String) list.get(i).get("IP");

                type = (String) list.get(i).get("Type");
                
                if (type.equals("SSH"))
                {
                    data = new ArrayList<>();

                    data.add(id);

                    usernamePasswordList = database.fireSelectQuery("SELECT Username, Password FROM Credential WHERE Id=?", data);

                    username = (String) usernamePasswordList.get(0).get("Username");

                    password = (String) usernamePasswordList.get(0).get("Password");
                }
                executorService.execute(new PollingPingSSH(username, password, ip, id, type));
            }
        }
        catch(Exception exception)
        {
            _logger.error("PollingRunnable pollingRunnable method having error. ", exception);
        }
    }
}
