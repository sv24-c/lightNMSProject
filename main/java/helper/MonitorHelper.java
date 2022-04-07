package helper;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import org.apache.commons.codec.binary.Base64;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by smit on 22/3/22.
 */

public class MonitorHelper
{

    public boolean ping(List<String> pinglist)
    {

        String packetdata = "";

        String result = "";

        ProcessBuilder processBuilder;

        Process process = null;

        BufferedReader reader = null;

        try
        {

            if(!pinglist.isEmpty())
            {

                processBuilder = new ProcessBuilder(pinglist);

                process = processBuilder.start();

                reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

                while ((result = reader.readLine()) != null)
                {
                    packetdata+=result;

                }

                if (!packetdata.isEmpty())
                {
                    if((Integer.parseInt(packetdata.substring((packetdata.indexOf(" received,")  - 1), packetdata.indexOf(" received,"))) == 5))
                    {
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
            }

            else
            {
                return false;
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
                if (reader != null)
                {
                    reader.close();
                }

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            try
            {

                if (process != null)
                {
                    process.destroy();
                }

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }


        }
        return false;
    }

    public boolean ssh(String username, String password, String ip)
    {
        Base64 base64 = new Base64();

        String decryptedPassword = new String(base64.decode(password.getBytes()));

        com.jcraft.jsch.Session session = null;

        ChannelExec channel= null;

        try
        {

            session = new JSch().getSession(username, ip, 22);

            session.setPassword(decryptedPassword);

            session.setConfig("StrictHostKeyChecking", "no");

            session.setTimeout(10000);

            session.connect();

            channel = (ChannelExec) session.openChannel("exec");

          //  channel.setCommand("uname");
            //ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
            //channel.setOutputStream(responseStream);

            channel.connect(10000);

           // String responseString = new String(responseStream.toByteArray());
           // System.out.println(responseString);

            if (channel.isConnected() && session.isConnected())
            {
                return true;
            }

            /*if (responseString.equals("Linux") && channel.isConnected() && session.isConnected())
            {
                return true;
            }*/

        }

        catch (Exception e)
        {
            e.printStackTrace();
        }

        finally
        {
            if (channel!= null)
            {
                channel.disconnect();
            }

            if (session != null)
            {
                session.disconnect();
            }
        }

        return false;
    }
}
