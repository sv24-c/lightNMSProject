package helper;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.sun.org.apache.bcel.internal.generic.FLOAD;
import org.apache.commons.codec.binary.Base64;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by smit on 22/3/22.
 */

public class MonitorHelper
{


    public boolean ping(List<String> pinglist)
    {

        String packetdata = "";

        String s = "";

        ProcessBuilder processBuilder;

        Process process = null;

        BufferedReader reader = null;

        BufferedReader error = null;

        try
        {

            if(!pinglist.isEmpty())
            {

                processBuilder = new ProcessBuilder(pinglist);

                process = processBuilder.start();

                reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

                error = new BufferedReader(new InputStreamReader(process.getErrorStream()));

                System.out.println("Result for Command ping.");

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
                    System.out.println(Integer.parseInt(packetdata.substring((packetdata.indexOf(" received,")  - 1), packetdata.indexOf(" received,"))));
                    System.out.println(Integer.parseInt(packetdata.substring((packetdata.indexOf(" packets transmitted")  - 1), packetdata.indexOf(" packets transmitted"))));
                    System.out.println(Float.parseFloat(packetdata.substring((packetdata.indexOf(" ms")-5), packetdata.indexOf(" ms"))));

                    return true;
                }
                else
                {
                    return false;
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
                reader.close();

                error.close();

                process.destroy();

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }
        return false;
    }

    public boolean ssh(String username, String password, String ip, String command)
    {
        Base64 base64 = new Base64();

        String decryptedPassword = new String(base64.decode(password.getBytes()));

        com.jcraft.jsch.Session session = null;

        ChannelExec channel= null;

        try
        {

            if(session==null && channel == null)
            {
                session = new JSch().getSession(username, ip, 22);

                session.setPassword(decryptedPassword);

                session.setConfig("StrictHostKeyChecking", "no");

                session.setTimeout(15000);

                session.connect();

                System.out.println("SSH Session connection done.");

                channel = (ChannelExec) session.openChannel("exec");

                channel.setCommand(command);

                System.out.println("Channel: "+channel);

                ByteArrayOutputStream resultStream = new ByteArrayOutputStream();

                System.out.println("ResultStream: "+resultStream);

                channel.setOutputStream(resultStream);

                System.out.println("Channel: "+channel);

                channel.connect();

                System.out.println("Channel connection done.");

                while (channel.isConnected())
                {
                    Thread.sleep(150);
                }

                String resultString = new String(resultStream.toByteArray());

                System.out.println("ResultStream: "+resultStream);

                System.out.println("ResultStream.toByteArray() : "+resultStream.toByteArray());

                System.out.println("ResultString: "+resultString);

                if (channel.isConnected() && session.isConnected() && !resultStream.equals(null))
                {
                    return true;
                }

            }

            else
            {
                System.out.println("Session or Channel is already connected.");
            }
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

                System.out.println("Channel disconnected.");
            }

            if (session != null)
            {
                session.disconnect();

                System.out.println("SSH connection disconnected.");
            }

        }
        return false;
    }
}
