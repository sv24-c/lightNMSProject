package helper;

import com.jcraft.jsch.*;
import org.apache.commons.codec.binary.Base64;
import org.joda.time.DateTime;

import java.io.*;
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

    public String ssh(String username, String password, String ip)
    {
        Base64 base64 = new Base64();

        String decryptedPassword = new String(base64.decode(password.getBytes()));

        String responseString = "";

        String resultString = null;

        BufferedWriter bufferedWriter = null;

        BufferedReader bufferedReader = null;

        Session session = null;

        Channel channel= null;

        ChannelExec channelExec = null;

        DateTime dateTime = new DateTime();

        try
        {
            session = new JSch().getSession(username, ip, 22);

            if (session != null)
            {
                session.setPassword(decryptedPassword);

                session.setConfig("StrictHostKeyChecking", "no");

                session.connect(10*1000);

                if (session.isConnected())
                {
                    channel =  session.openChannel("shell");

                    if (channel != null)
                    {

                    System.out.println("Start Time: "+dateTime.toLocalTime());


                        channel.connect(10*1000);

                        if (channel.isConnected())
                        {
                            bufferedWriter = new BufferedWriter(new OutputStreamWriter(channel.getOutputStream()));

                            bufferedReader = new BufferedReader(new InputStreamReader(channel.getInputStream()));

                            bufferedWriter.write("uname\nexit\n");

                            bufferedWriter.flush();

                            while ((responseString = bufferedReader.readLine()) != null)
                            {
                                resultString += responseString;
                            }

                            System.out.println("Start Time: "+dateTime.toLocalTime());

                            if (resultString != null && resultString.trim().contains("Linux"))
                            {
                                return "Linux";
                            }
                            else
                            {
                                return "SSH Failed to this "+ip+" Device Type must be Linux";
                            }
                        }
                        else
                        {
                            return "Channel is not connected";
                        }
                    }
                    else
                    {
                        return "Channel is not Open";
                    }
                }
                else
                {
                    return "Wrong Username or Password for SSH";
                }
            }
            else
            {
                return "Session is null";
            }
        }
        catch (Exception exception)
        {
            _logger.error("MonitorHelper ssh method having error. ", exception);
        }
        finally
        {
            try
            {
                if (channel!= null)
                {
                    channel.disconnect();
                }
            }
            catch (Exception exception)
            {
                _logger.error("Multiple Discovery time channel is not closed", exception);
            }

            try
            {
                if (session != null)
                {
                    session.disconnect();
                }
            }
            catch (Exception exception)
            {
                _logger.error("Multiple Discovery time Session is not closed", exception);
            }

            try
            {
                if (bufferedReader != null)
                {
                    bufferedReader.close();
                }
            }
            catch (Exception exception)
            {
                _logger.error("Multiple Discovery time Buffered reader is not closed ", exception);
            }

            try
            {
                if (bufferedWriter != null)
                {
                    bufferedWriter.close();
                }
            }
            catch (Exception exception)
            {
                _logger.error("Multiple Discovery time Buffered writer is not closed ", exception);
            }
        }
        return "Wrong Username or Password for SSH";
    }
}
