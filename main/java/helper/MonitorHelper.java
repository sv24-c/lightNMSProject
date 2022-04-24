package helper;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import org.apache.commons.codec.binary.Base64;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by smit on 22/3/22.
 */

public class MonitorHelper
{

    private static final Logger _logger = new Logger();

    public boolean ping(List<String> pinglist)
    {
        StringBuilder packetdata = new StringBuilder();

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
                    packetdata.append(result);
                }

                if (packetdata.length() > 0)
                {
                    if((Integer.parseInt(packetdata.substring((packetdata.indexOf(" received,")  - 1), packetdata.indexOf(" received,"))) == CommonConstant.RECEVIEDPACKET))
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
        catch(Exception exception)
        {
            _logger.error("MonitorHelper ping method having error. ", exception);
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
            catch (Exception exception)
            {
                _logger.error("MonitorHelper in ping method reader close having error. ", exception);
            }

            try
            {
                if (process != null)
                {
                    process.destroy();
                }
            }
            catch (Exception exception)
            {
                _logger.error("MonitorHelper in ping method process close having error. ", exception);
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
            session = new JSch().getSession(username, ip, CommonConstant.SSHPORT);

            session.setPassword(decryptedPassword);

            session.setConfig("StrictHostKeyChecking", "no");

            session.connect(10*1000);

            channel = (ChannelExec) session.openChannel("exec");

            channel.setCommand("uname");

            ByteArrayOutputStream responseStream = new ByteArrayOutputStream();

            channel.setOutputStream(responseStream);

            channel.connect(10*1000);

            String responseString = new String(responseStream.toByteArray());

            if (responseString.trim().equals("Linux"))
            {
                return true;
            }
        }
        catch (Exception exception)
        {
            _logger.error("MonitorHelper ssh method having error. ", exception);
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
