package helper;

import org.quartz.*;

/**
 * Created by smit on 24/3/22.
 */
public class PollingHelper implements Job
{

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException
    {

        new PollingRunnable().pollingRunnableMethod();
    }
}