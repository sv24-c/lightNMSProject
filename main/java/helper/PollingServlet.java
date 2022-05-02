package helper;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Created by smit on 28/3/22.
 */
public class PollingServlet extends HttpServlet
{

    private static Logger _logger = new Logger();

    @Override
    public void init() throws ServletException
    {
        try
        {
            ConnectionPool.createConnection();
        }
        catch (Exception exception)
        {
            _logger.error("PollingServlet init method in create connection having error. ", exception);
        }

        try
        {
            JobDetail jobDetail = JobBuilder.newJob(PollingHelper.class).build();

            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("SimpleTrigger")
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(120).repeatForever())
                    .build();

            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            scheduler.start();

            scheduler.scheduleJob(jobDetail, trigger);
        }
        catch (Exception exception)
        {
            _logger.error("PollingServlet init method in polling job scheduling having error. ", exception);
        }

        try
        {
            Thread consumerThread = new Thread(new TakeDiscoveryRunnable());

            consumerThread.setName("Discovery Runnable thread");

            consumerThread.start();
        }
        catch (Exception exception)
        {
            _logger.error("PollingServlet init method in starting thread having error. ", exception);
        }
    }
}
