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

    @Override
    public void init() throws ServletException
    {

        try
        {

            System.out.println("Server started....");

            JobDetail jobDetail = JobBuilder.newJob(PollingHelper.class).build();

            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("SimpleTrigger")
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(60).repeatForever())
                    .build();

            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            scheduler.start();
            scheduler.scheduleJob(jobDetail, trigger);


        }
        catch (SchedulerException e)
        {
            e.printStackTrace();
        }
    }
}
