package dao;

import helper.ConnectionPool;
import helper.Logger;

import java.sql.*;
import java.util.*;

/**
 * Created by smit on 22/3/22.
 */
public class MonitorDao
{

    private static Logger _logger = new Logger();

    public boolean monitorCheckDuplicateData(String ip, String type)
    {
        ResultSet resultSet = null;

        Connection con = null;

        PreparedStatement preparedStatement = null;

        try
        {
            con = ConnectionPool.getConnection();

            if ( con != null)
            {

                preparedStatement = con.prepareStatement("SELECT Id FROM Monitor WHERE IP = ? AND Type = ?");

                preparedStatement.setString(1, ip);

                preparedStatement.setString(2, type);

                resultSet = preparedStatement.executeQuery();

                if (resultSet.next())
                {
                    return true;
                }

            }
        }

        catch (SQLException exception)
        {

            _logger.error("MonitorDao monitorCheckDuplicateData method having error. ", exception);


        }

        finally
        {
            ConnectionPool.closePreparedStatement(preparedStatement);

            ConnectionPool.releaseConnection(con);

        }

        return false;
    }

    public String monitorInsert(int id, String name, String ip, String type)
    {
        String availability;

        Connection con = null;

        PreparedStatement preparedStatement = null;

        try
        {
            con = ConnectionPool.getConnection();

            availability = "unknown";

            if ( con != null)
            {
                preparedStatement = con.prepareStatement("INSERT INTO Monitor (Id, Name, IP, Type, Availability) VALUES(?,?,?,?,?)");

                preparedStatement.setInt(1, id);

                preparedStatement.setString(2, name);

                preparedStatement.setString(3, ip);

                preparedStatement.setString(4, type);

                preparedStatement.setString(5, availability);

                preparedStatement.executeUpdate();

            }

        }

        catch (Exception exception)
        {
            _logger.error("MonitorDao monitorInsert method having error. ", exception);


        }

        finally
        {
            ConnectionPool.closePreparedStatement(preparedStatement);

            ConnectionPool.releaseConnection(con);
        }

        return null;
    }

    public List<Map<String, Object>> monitorFetchAllData()
    {
        ResultSet resultSet = null;

        Connection con = null;

        PreparedStatement preparedStatement = null;

        List<Map<String, Object>> list = null;

        try
        {
            con = ConnectionPool.getConnection();

            list = new ArrayList<>();

            if ( con != null)
            {

                preparedStatement = con.prepareStatement("SELECT Id, Name, IP, Type, Availability FROM Monitor");

                resultSet = preparedStatement.executeQuery();

                while (resultSet.next())
                {

                    Map<String, Object> map = new LinkedHashMap<String, Object>();

                    map.put("Id", resultSet.getInt(1));

                    map.put("Name", resultSet.getString(2));

                    map.put("IP", resultSet.getString(3));

                    map.put("Type", resultSet.getString(4));

                    map.put("Availability", resultSet.getString(5));

                    list.add(map);

                }
            }
        }

        catch (Exception exception)
        {

            _logger.error("MonitorDao monitorFetchAllData method having error. ", exception);

        }

        finally
        {
            ConnectionPool.closePreparedStatement(preparedStatement);

            ConnectionPool.releaseConnection(con);

        }

        return list;
    }

    public boolean monitorDelete(int id)
    {
        Connection con = null;

        PreparedStatement preparedStatement = null;

        try
        {
            con = ConnectionPool.getConnection();

            if ( con != null)
            {
                preparedStatement = con.prepareStatement("DELETE FROM Monitor WHERE Id = ?");

                preparedStatement.setInt(1, id);

                preparedStatement.executeUpdate();

                return true;
            }
            else
            {
                return false;
            }
        }

        catch (Exception exception)
        {
            _logger.error("MonitorDao monitorDelete method having error. ", exception);
        }

        finally
        {
            ConnectionPool.closePreparedStatement(preparedStatement);

            ConnectionPool.releaseConnection(con);
        }

        return false;
    }

    public void monitorAvailabilityUpdate(String availability, int id)
    {
        Connection con = null;

        PreparedStatement preparedStatementOfUpdate = null;

        try
        {
            //con = makeConnection();

            con = ConnectionPool.getConnection();

            if ( con != null)
            {
                preparedStatementOfUpdate = con.prepareStatement("UPDATE Monitor SET Availability=? where Id=?");

                preparedStatementOfUpdate.setString(1, availability);

                preparedStatementOfUpdate.setInt(2, id);

                preparedStatementOfUpdate.executeUpdate();

            }
        }

        catch (Exception exception)
        {

            _logger.error("MonitorDao monitorAvailabilityUpdate method having error. ", exception);

        }

        finally
        {

            ConnectionPool.closePreparedStatement(preparedStatementOfUpdate);

            ConnectionPool.releaseConnection(con);

        }

    }

    public List<String> monitorFetchDataForProvision(int id)
    {
        ResultSet resultSet = null;

        Connection con = null;

        PreparedStatement preparedStatement = null;

        List<String> dataList = null;

        try
        {
            con = ConnectionPool.getConnection();

            dataList = new ArrayList<>();

            if ( con != null)
            {
                preparedStatement = con.prepareStatement("SELECT Name, IP, Type, Username, Password FROM Discovery where Id = ?");

                preparedStatement.setInt(1, id);

                resultSet = preparedStatement.executeQuery();

                while (resultSet.next())
                {
                    dataList.add(resultSet.getString(1));

                    dataList.add(resultSet.getString(2));

                    dataList.add(resultSet.getString(3));

                    dataList.add(resultSet.getString(4));

                    dataList.add(resultSet.getString(5));
                }
            }
        }

        catch (SQLException exception)
        {
            _logger.error("MonitorDao monitorFetchDataForProvision method having error. ", exception);

        }

        finally
        {
            ConnectionPool.closePreparedStatement(preparedStatement);

            ConnectionPool.releaseConnection(con);
        }

        return dataList;
    }

    public String monitorFetchIp(int id)
    {
        ResultSet resultSet = null;

        Connection con = null;

        PreparedStatement preparedStatement = null;

        String ipForChart = null;

        try
        {
           con = ConnectionPool.getConnection();

            if ( con != null)
            {

                preparedStatement = con.prepareStatement("SELECT IP FROM Monitor where Id = ?");

                preparedStatement.setInt(1, id);

                resultSet = preparedStatement.executeQuery();

                while (resultSet.next())
                {
                    ipForChart = resultSet.getString(1);
                }
            }
        }

        catch (SQLException exception)
        {

            _logger.error("MonitorDao monitorFetchIp method having error. ", exception);

        }

        finally
        {
            ConnectionPool.closePreparedStatement(preparedStatement);

            ConnectionPool.releaseConnection(con);
        }

        return ipForChart;
    }
}


