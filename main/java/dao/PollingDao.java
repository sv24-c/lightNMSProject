package dao;

import helper.ConnectionPool;
import helper.Logger;

import java.sql.*;
import java.util.*;

/**
 * Created by smit on 27/3/22.
 */
public class PollingDao
{

    private static Logger _logger = new Logger();

    public void pollingPingInsert(int id, String ip, int sendPacket, int receivePacket, int packetLoss, float rtt, Timestamp time, String status)
    {
        Connection con = null;

        PreparedStatement preparedStatementOfInsert = null;

        try
        {
            con = ConnectionPool.getConnection();

            if ( con != null)
            {
                preparedStatementOfInsert = con.prepareStatement("INSERT INTO PingPolling (Id, IP, SendPacket, ReceivePacket, PacketLoss, RTT, PollingTime, Status) VALUES(?,?,?,?,?,?,?,?)");

                preparedStatementOfInsert.setInt(1, id);

                preparedStatementOfInsert.setString(2, ip);

                preparedStatementOfInsert.setInt(3, sendPacket);

                preparedStatementOfInsert.setInt(4, receivePacket);

                preparedStatementOfInsert.setInt(5, packetLoss);

                preparedStatementOfInsert.setFloat(6, rtt);

                preparedStatementOfInsert.setTimestamp(7, time);

                preparedStatementOfInsert.setString(8, status);

                preparedStatementOfInsert.executeUpdate();
            }
            else
            {
                _logger.info("Connection not established PollingDao pollingDaoPingInsert method...");
            }
        }

        catch (Exception exception)
        {

            _logger.error("PollingDao pollingPingInsert method having error. ", exception);

        }

        finally
        {
            ConnectionPool.closePreparedStatement(preparedStatementOfInsert);

            ConnectionPool.releaseConnection(con);

        }
    }

    public void pollingSSHInsert(int id, String ip, double cpu, double memory, float disk, Timestamp time, String status, float swapMemory)
    {
        Connection con = null;

        PreparedStatement preparedStatementOfInsert = null;

        try
        {
            con = ConnectionPool.getConnection();

            if ( con != null)
            {
                preparedStatementOfInsert = con.prepareStatement("INSERT INTO SSHPolling (Id, IP, CPU, Memory, Disk, PollingTime, Status, SwapMemory) VALUES(?,?,?,?,?,?,?,?)");

                preparedStatementOfInsert.setInt(1, id);

                preparedStatementOfInsert.setString(2, ip);

                preparedStatementOfInsert.setDouble(3, cpu);

                preparedStatementOfInsert.setDouble(4, memory);

                preparedStatementOfInsert.setFloat(5, disk);

                preparedStatementOfInsert.setTimestamp(6, time);

                preparedStatementOfInsert.setString(7, status);

                preparedStatementOfInsert.setFloat(8, swapMemory);

                preparedStatementOfInsert.executeUpdate();

            }

            else
            {
                _logger.info("Connection not established PollingDao pollingDaoSSHInsert method...");
            }

        }

        catch (Exception exception)
        {
            _logger.error("PollingDao pollingSSHInsert method having error. ", exception);
        }

        finally
        {
            ConnectionPool.closePreparedStatement(preparedStatementOfInsert);

            ConnectionPool.releaseConnection(con);

        }
    }

    public List<String> pollingFetchUsernamePassword(int id)
    {

        List<String> stringList = null;

        ResultSet resultSet;

        Connection con = null;

        PreparedStatement preparedStatement = null;

        try
        {
            con = ConnectionPool.getConnection();

            stringList = new ArrayList<>();

            if ( con != null)
            {

                preparedStatement = con.prepareStatement("SELECT Username, Password FROM Discovery WHERE Id=?");

                preparedStatement.setInt(1, id);

                resultSet = preparedStatement.executeQuery();

                while (resultSet.next())
                {
                    stringList.add(resultSet.getString(1));

                    stringList.add(resultSet.getString(2));

                }

            }

            else
            {
                _logger.info("Connection not established PollingDao pollingFetchUsernamePassword method...");
            }

        }

        catch (SQLException exception)
        {

            _logger.error("PollingDao pollingFetchUsernamePassword method having error. ", exception);

        }

        finally
        {
            ConnectionPool.closePreparedStatement(preparedStatement);

            ConnectionPool.releaseConnection(con);

        }

        return stringList;
    }

    public List<Integer> pollingSSHFetchAvailabilityData(int id)
    {
        List<Integer> list = null;

        ResultSet resultSet;

        Connection con = null;

        PreparedStatement preparedStatement = null;

        try
        {
            con = ConnectionPool.getConnection();

            list = new ArrayList<>();

            if ( con != null)
            {

                preparedStatement = con.prepareStatement("SELECT SUM(Status='Up') , SUM(Status='Down') from SSHPolling where Id = ? and PollingTime > now() - interval 1 day;");

                preparedStatement.setInt(1, id);

                resultSet = preparedStatement.executeQuery();

                while (resultSet.next())
                {
                    list.add( resultSet.getInt(1));

                    list.add(resultSet.getInt(2));
                }
            }

            else
            {
                _logger.info("Connection not established PollingDao pollingSSHFetchAvailabilityData method...");
            }

        }

        catch (SQLException exception)
        {

            _logger.error("PollingDao pollingSSHFetchAvailabilityData method having error. ", exception);

        }

        finally
        {
            ConnectionPool.closePreparedStatement(preparedStatement);

            ConnectionPool.releaseConnection(con);

        }

        return list;
    }

    public List<Integer> pollingPingFetchAvailabilityData(int id)
    {
        List<Integer> list = null;

        ResultSet resultSet;

        Connection con = null;

        PreparedStatement preparedStatement = null;

        try
        {

            con = ConnectionPool.getConnection();

            list = new ArrayList<>();

            if ( con != null)
            {

                preparedStatement = con.prepareStatement("SELECT SUM(Status='Up') , SUM(Status='Down') from PingPolling where Id = ? and PollingTime > now() - interval 1 day;");

                preparedStatement.setInt(1, id);

                resultSet = preparedStatement.executeQuery();

                while (resultSet.next())
                {
                    list.add(resultSet.getInt(1));

                    list.add(resultSet.getInt(2));
                }
            }

            else
            {
                _logger.info("Connection not established PollingDao pollingPingFetchAvailabilityData method...");
            }

        }

        catch (SQLException exception)
        {
            _logger.error("PollingDao pollingPingFetchAvailabilityData method having error. ", exception);
        }

        finally
        {

            ConnectionPool.closePreparedStatement(preparedStatement);

            ConnectionPool.releaseConnection(con);

        }

        return list;
    }

    public List<Object> pollingFetchPingData(int id)
    {
        List<Object> list = null;

        ResultSet resultSet;

        Connection con = null;

        PreparedStatement preparedStatement = null;

        try
        {
            con = ConnectionPool.getConnection();

            list = new ArrayList<>();

            if ( con != null)
            {

                preparedStatement = con.prepareStatement("select SendPacket, ReceivePacket, PacketLoss, RTT, PollingTime FROM PingPolling p1 WHERE Id=? and PollingTime = (select max(PollingTime) from PingPolling p2 where p1.Id = p2.Id)");

                preparedStatement.setInt(1, id);

                resultSet = preparedStatement.executeQuery();

                while (resultSet.next())
                {

                    list.add(resultSet.getInt(1));

                    list.add(resultSet.getInt(2));

                    list.add(resultSet.getInt(3));

                    list.add(resultSet.getFloat(4));

                    list.add(resultSet.getTimestamp(5));
                }
            }

            else
            {
                _logger.info("Connection not established PollingDao pollingFetchPingData method...");
            }

        }

        catch (SQLException exception)
        {

            _logger.error("PollingDao pollingFetchPingData method having error. ", exception);

        }

        finally
        {

            ConnectionPool.closePreparedStatement(preparedStatement);

            ConnectionPool.releaseConnection(con);

        }

        return list;
    }

    public List<Object> pollingFetchSSHData(int id)
    {
        List<Object> list = null;

        ResultSet resultSet;

        Connection con = null;

        PreparedStatement preparedStatement = null;

        try
        {
            con = ConnectionPool.getConnection();

            list = new ArrayList<>();

            if ( con != null)
            {

                preparedStatement = con.prepareStatement("select CPU, Memory, Disk, SwapMemory, PollingTime  FROM SSHPolling s1 WHERE Id=? and PollingTime = (select max(PollingTime) from SSHPolling s2 where s1.Id = s2.Id)");

                preparedStatement.setInt(1, id);

                resultSet = preparedStatement.executeQuery();

                while (resultSet.next())
                {
                    list.add(resultSet.getFloat(1));

                    list.add(resultSet.getFloat(2));

                    list.add(resultSet.getFloat(3));

                    list.add(resultSet.getFloat(4));

                    list.add(resultSet.getTimestamp(5));
                }
            }

            else
            {
                _logger.info("Connection not established PollingDao pollingFetchSSHData method...");
            }

        }
        catch (SQLException exception)
        {
            _logger.error("PollingDao pollingFetchSSHData method having error. ", exception);
        }
        finally
        {
            ConnectionPool.closePreparedStatement(preparedStatement);

            ConnectionPool.releaseConnection(con);

        }

        return list;
    }

    public Map<Timestamp, Float> pollingPingFetchRttData(int id)
    {
        Map<Timestamp, Float> mapofRtt = null;

        float rtt = 0;

        Timestamp sshTimestamp = null;

        ResultSet resultSet;

        Connection con = null;

        PreparedStatement preparedStatement = null;

        try
        {
            con = ConnectionPool.getConnection();

            mapofRtt = new LinkedHashMap<>();

            if ( con != null)
            {
                preparedStatement = con.prepareStatement("SELECT RTT, PollingTime FROM PingPolling where Id=? ORDER BY PollingTime DESC LIMIT 10;");

                preparedStatement.setInt(1, id);

                resultSet = preparedStatement.executeQuery();

                while (resultSet.next())
                {
                    rtt = resultSet.getFloat(1);

                    sshTimestamp = resultSet.getTimestamp(2);

                    mapofRtt.put(sshTimestamp, rtt);
                }
            }
            else
            {
                _logger.info("Connection not established PollingDao pollingPingFetchRttData method...");
            }
        }
        catch (SQLException exception)
        {
            _logger.error("PollingDao pollingFetchRttData method having error. ", exception);
        }
        finally
        {
            ConnectionPool.closePreparedStatement(preparedStatement);

            ConnectionPool.releaseConnection(con);

        }

        return mapofRtt;
    }

    public Map<Timestamp, Float> pollingSSHFetchCpuData(int id)
    {
        Map<Timestamp, Float> map = null;

        float cpu = 0;

        Timestamp sshTimestamp = null;

        ResultSet resultSet;

        Connection con = null;

        PreparedStatement preparedStatement = null;

        try
        {
            con = ConnectionPool.getConnection();

            map = new LinkedHashMap<>();

            if ( con != null)
            {

                preparedStatement = con.prepareStatement("SELECT CPU, PollingTime FROM SSHPolling where Id=? ORDER BY PollingTime DESC LIMIT 10;");

                preparedStatement.setInt(1, id);

                resultSet = preparedStatement.executeQuery();

                while (resultSet.next())
                {

                    cpu = resultSet.getFloat(1);

                    sshTimestamp = resultSet.getTimestamp(2);

                    map.put(sshTimestamp, cpu);
                }
            }
            else
            {
                _logger.info("Connection not established PollingDao pollingSSHFetchCpuData method...");
            }
        }
        catch (SQLException exception)
        {
            _logger.error("PollingDao pollingSSHFetchCpuData method having error. ", exception);
        }
        finally
        {
            ConnectionPool.closePreparedStatement(preparedStatement);

            ConnectionPool.releaseConnection(con);
        }

        return map;
    }
}
