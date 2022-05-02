package helper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by smit on 7/4/22.
 */
public class TakeDiscoveryRunnable implements Runnable
{

    private static final Logger _logger = new Logger();

    @Override
    public void run()
    {
        try
        {
            ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

            while (true)
            {
                int id = MultipleDiscovery.multipleDiscoveryTakeFromQueue();

                service.execute(new MultipleDiscoveryRunnable(id));
            }
        }
        catch (Exception exception)
        {
            _logger.error("TakeDiscoveryRunnable run method having error.", exception);
        }
    }
}
