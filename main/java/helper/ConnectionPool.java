package helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by smit on 12/4/22.
 */
public class ConnectionPool
{
    static LinkedBlockingQueue<Connection> linkedBlockingQueue = null;

    private static Logger _logger = new Logger();

    public static void createConnection()
    {
        try
        {
            Connection connection = null;

            linkedBlockingQueue = new LinkedBlockingQueue<>(15);

            Class.forName("com.mysql.cj.jdbc.Driver");

            for (int i = 0; i < 15 ; i++)
            {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/lightNMS?characterEncoding=utf8", "root", "Mind@123");

                if (connection !=null)
                {
                    linkedBlockingQueue.put(connection);
                }
                else
                {
                    _logger.info("Connection pool class has null connection");
                }
            }

           /* if (linkedBlockingQueue.size() != CommonConstant.CONNECTION)
            {
                _logger.info("You have left "+(CommonConstant.CONNECTION - linkedBlockingQueue.size()) + " connections to make ou");
            }*/
        }
        catch (Exception exception)
        {
            _logger.error("ConnectionPool createConnection method having error. ", exception);
        }
    }

    public static Connection getConnection()
    {
        Connection connection = null;
        
        try
        {
            connection = linkedBlockingQueue.take();
        }
        catch (Exception exception)
        {
            _logger.error("ConnectionPool getConnection method having error. ", exception);
        }

        return connection;
    }

    public static void releaseConnection(Connection connection)
    {
        try
        {
            linkedBlockingQueue.put(connection);
        }
        catch (Exception exception)
        {
            _logger.error("ConnectionPool releaseConnection method having error. ", exception);
        }
    }

    public static void closePreparedStatement(PreparedStatement preparedStatement)
    {
        try
        {
            if (preparedStatement != null && !preparedStatement.isClosed())
            {
                preparedStatement.close();
            }
        }
        catch (Exception exception)
        {
            _logger.error("ConnectionPool closePreparedStatement method having error. ", exception);
        }
    }
}
