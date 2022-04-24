package helper;

import org.quartz.*;

/**
 * Created by smit on 24/3/22.
 */
public class PollingHelper implements Job
{
    private static Logger _logger = new Logger();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException
    {
        try
        {
            new PollingRunnable().pollingRunnableMethod();
        }
        catch (Exception exception)
        {
            _logger.error("PollingHelper execute method having error. ", exception);
        }
    }
}