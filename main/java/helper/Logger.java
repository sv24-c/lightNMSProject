package helper;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * Created by smit on 14/4/22.
 */
public class Logger
{
    private String dateFormat;

    private String PATH = System.getProperty("user.dir");

    private File infoFile = new File(PATH + "/log");

    private DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern("dd-MMMM-yyyy");

    private static final Logger _logger = new Logger();

    public void info(String message)
    {
        BufferedWriter writer = null;

        try
        {
            dateFormat = getDateFormat();

            infoFile.mkdir();

            writer = new BufferedWriter(new FileWriter(infoFile + "/" + dateFormat + "-info.log", true));

                writer.write(message + "\n");
        }
        catch (Exception e)
        {
            e.printStackTrace();

            _logger.error("failed to dump message of info...", e);
        }
        finally
        {
            try
            {
                if (writer != null)
                {
                    writer.close();
                }
            }
            catch (Exception ignored)
            {
                _logger.info("writer not closed!");
            }
        }
    }

    public void error(String message, Throwable throwable)
    {
        BufferedWriter writer = null;

        try
        {
            dateFormat = getDateFormat();

            infoFile.mkdir();

            writer = new BufferedWriter(new FileWriter(infoFile + "/" + dateFormat + "-error.log", true));

            writer.write(message + "\n");

            StackTraceElement[] stackTrace = throwable.getStackTrace();

            for (int i = 0; i < stackTrace.length; i++)
            {
                System.out.println("Index " + i + " of stack trace" + " array contains = " + stackTrace[i].toString());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();

            _logger.error("failed to dump message of info...", e);
        }
        finally
        {
            try
            {
                if (writer != null)
                {
                    writer.close();
                }
            }
            catch (Exception ignored)
            {
                _logger.info("writer not closed!");
            }
        }
    }

    private String getDateFormat()
    {
        DateTime dateTime = DateTime.now();

        return dateTimeFormat.print(dateTime);
    }
}
