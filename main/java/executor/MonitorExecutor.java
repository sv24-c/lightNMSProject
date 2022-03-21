package executor;

import bean.DiscoveryBean;
import bean.MonitorBean;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by smit on 20/3/22.
 */
public class MonitorExecutor
{
    private static MonitorExecutor monitorExecutor = new MonitorExecutor();

    public static boolean monitorPing()
    {

        try
        {
            MonitorBean monitorBean = new MonitorBean();

            List<String> pingcommands = new ArrayList<String>();

            pingcommands.add("ping");
            pingcommands.add("-c");
            pingcommands.add("5");
            pingcommands.add(monitorBean.getIp());

            monitorExecutor.ping(pingcommands);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return true;
    }

    public void ping(List<String> pinglist)
    {
        try
        {

            String s = null;

            ProcessBuilder processBuilder = new ProcessBuilder(pinglist);

            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            while ((s = reader.readLine()) !=null)
            {
                System.out.println(s);
            }

            System.out.println("Result for Command ping.");

            while ((s = error.readLine()) !=null)
            {
                System.out.println(s);
            }

            System.out.println("Error for Command ping.");
        }

        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
