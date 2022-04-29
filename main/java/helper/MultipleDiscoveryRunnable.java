package helper;

/**
 * Created by smit on 7/4/22.
 */

public class MultipleDiscoveryRunnable implements Runnable
{
    int blockingQueueTakenId = 0;

    private ServerWebSocket serverWebSocket = new ServerWebSocket();

    private static final Logger _logger = new Logger();

    MultipleDiscoveryRunnable(int id)
    {
        this.blockingQueueTakenId = id;
    }

    @Override
    public void run()
    {
        try
        {
            String returnResult = MultipleDiscovery.multipleDiscoveryPingSSH(blockingQueueTakenId);

            if(returnResult !=null)
            {
                serverWebSocket.sendMessage(returnResult);
            }
        }
        catch (Exception exception)
        {
            _logger.error("MultipleDiscoveryRunnable run method having error. ", exception);
        }
    }
}
