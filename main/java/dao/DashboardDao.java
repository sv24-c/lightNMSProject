package dao;

import helper.ConnectionPool;
import helper.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by smit on 31/3/22.
 */
public class DashboardDao
{
    private Logger _logger = new Logger();

    public List<Integer> dashboardShowData()//dashboardFetchData()
    {
        ResultSet resultSet;

        Connection con = null;

        int upCount = 0;

        int downCount = 0;

        int unknownCount = 0;

        PreparedStatement preparedStatement = null;

        List<Integer> list = null;

        try
        {
            list = new ArrayList<>();

            con = ConnectionPool.getConnection();

            if ( con != null)
            {

                preparedStatement = con.prepareStatement("SELECT SUM(Availability ='Up') , SUM(Availability='Down'), SUM(Availability='unknown') from Monitor");

                resultSet = preparedStatement.executeQuery();

                while (resultSet.next())
                {
                    upCount = resultSet.getInt(1);

                    downCount = resultSet.getInt(2);

                    unknownCount = resultSet.getInt(3);

                    list.add(upCount);

                    list.add(downCount);

                    list.add(unknownCount);

                }
            }

            else
            {
                System.out.println("Connection is not established");
            }

        }

        catch (SQLException exception)
        {

            exception.printStackTrace();

            _logger.error("DashboardDao dashboardDaoFetchData method having error", exception);

        }

        finally
        {
            ConnectionPool.closePreparedStatement(preparedStatement);

            ConnectionPool.releaseConnection(con);

        }

        return list;
    }

    public Map<String, Float> dashboardFetchTopFiveCPUUtilizationData()
    {
        ResultSet resultSet;

        Connection con = null;

        PreparedStatement preparedStatement = null;

        Map<String, Float> topFiveCpuData = null;

        try
        {
            topFiveCpuData = new LinkedHashMap<>();

            con = ConnectionPool.getConnection();

            if ( con != null)
            {
                preparedStatement = con.prepareStatement("select max(CPU) as CPU, IP from SSHPolling where PollingTime > now() - interval 1 hour group by IP  order by CPU desc limit 5");

                resultSet = preparedStatement.executeQuery();

                while (resultSet.next())
                {
                   topFiveCpuData.put(resultSet.getString(2), resultSet.getFloat(1));
                }
            }

            else
            {
                System.out.println("Connection is not established");
            }

        }

        catch (SQLException exception)
        {
            exception.printStackTrace();

            _logger.error("DashboardDao dashboardFetchTopFiveCPUUtilizationData method having error", exception);
        }

        finally
        {
            ConnectionPool.closePreparedStatement(preparedStatement);

            ConnectionPool.releaseConnection(con);

        }

        return topFiveCpuData;
    }

}
