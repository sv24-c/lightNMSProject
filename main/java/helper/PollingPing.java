package helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by smit on 25/3/22.
 */
public class PollingPing  extends PollingRunnable implements Runnable
{

    List<Object> pingResultList = new ArrayList<>();

    List<String> pinglist = new ArrayList<>();

   /* PollingPing(String ip) {
        super(ip);
    }*/

    @Override
    public void run() {

        //ping(super.ip);
    }



   /* public List ping(String ip)
    {

        String packetdata = "";

        String s = "";

        int packetLoss =0;

        int packetTransmitted;

        int packetReceive;

        float rtt;

        ProcessBuilder processBuilder;

        Process process = null;

        BufferedReader reader = null;

        BufferedReader error = null;

        pinglist.add("ping");
        pinglist.add("-c");
        pinglist.add("5");
        pinglist.add(ip);


        try
        {

            if(!pinglist.isEmpty())
            {

                processBuilder = new ProcessBuilder(pinglist);

                process = processBuilder.start();

                reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

                error = new BufferedReader(new InputStreamReader(process.getErrorStream()));

                System.out.println("Result for Command ping...");

                while ((s = reader.readLine()) != null)
                {
                    packetdata+=s;

                    System.out.println(s);

                }

                while ((s = error.readLine()) != null)
                {
                    System.out.println("Error for Command ping."+s);
                }

                if((Integer.parseInt(packetdata.substring((packetdata.indexOf(" received,")  - 1), packetdata.indexOf(" received,"))) == 5))
                {
                    packetReceive = Integer.parseInt(packetdata.substring((packetdata.indexOf(" received,")  - 1), packetdata.indexOf(" received,")));
                    packetTransmitted = Integer.parseInt(packetdata.substring((packetdata.indexOf(" packets transmitted")  - 1), packetdata.indexOf(" packets transmitted")));
                    packetLoss = 100 - ((Integer.parseInt(packetdata.substring((packetdata.indexOf(" received,")  - 1), packetdata.indexOf(" received,"))) / 5) *100) ;
                    rtt = Float.parseFloat(packetdata.substring((packetdata.indexOf(" ms")-5), packetdata.indexOf(" ms")));

                    pingResultList.add(packetTransmitted);
                    pingResultList.add(packetReceive);
                    pingResultList.add(packetLoss);
                    pingResultList.add(rtt);
                }
                else
                {
                    System.out.println("Host Unreachable.");
                }

            }

            else
            {
                System.out.println("list should be empty first");
            }
        }

        catch(Exception e)
        {
            e.printStackTrace();
        }

        finally
        {
            try
            {
                reader.close();

                error.close();

                process.destroy();

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }

        return pingResultList;
    }*/
}
