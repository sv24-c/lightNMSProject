package dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Date;

/**
 * Created by smit on 27/3/22.
 */
public class PollingDao
{
    static
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");

        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public Connection makeConnection()
    {
        Connection con = null;

        try
        {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lightNMS?characterEncoding=utf8", "root", "Mind@123");
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return con;
    }

    public void closeConnection(PreparedStatement preparedStatement, Connection connection)
    {
        try
        {

            if (preparedStatement != null || !preparedStatement.isClosed())
            {
                preparedStatement.close();
            }

            if (connection != null || !connection.isClosed())
            {
                connection.close();
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void pollingDaoPingInsertData(int id, String ip, int sendPacket, int receivePacket, int packetLoss, float rtt, Timestamp time, String status)
    {
        Connection con = null;

        PreparedStatement preparedStatementOfInsert = null;

        try
        {
            con = makeConnection();

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
                System.out.println("Connection not established...");
            }

        }

        catch (Exception e)
        {
            System.out.println(e.getMessage());

            System.out.println(e.getStackTrace());

        }

        finally
        {
            closeConnection(preparedStatementOfInsert, con);
        }
    }

    public void pollingDaoSSHInsertData(int id, String ip, double cpu, double memory, float disk, Timestamp time, String status, float swapMemory)
    {
        Connection con = null;

        PreparedStatement preparedStatementOfInsert = null;

        try
        {
            con = makeConnection();

            con.setAutoCommit(false);

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
                System.out.println("Connection not established...");
            }

            con.setAutoCommit(true);

        }

        catch (Exception e)
        {
            System.out.println(e.getMessage());

            System.out.println(e.getStackTrace());

        }

        finally
        {
            closeConnection(preparedStatementOfInsert, con);
        }
    }

    public List pollingDaoGetUsernamePassword(int id)
    {

        String uname=null;

        String pass = null;

        List<String> stringList = null;

        ResultSet resultSet;

        Connection con = null;

        PreparedStatement preparedStatement = null;

        try
        {
            con = makeConnection();

            stringList = new ArrayList<>();

            if ( con != null)
            {

                preparedStatement = con.prepareStatement("SELECT Username, Password FROM Discovery WHERE Id=?");

                preparedStatement.setInt(1, id);

                resultSet = preparedStatement.executeQuery();

                while (resultSet.next())
                {

                    uname = resultSet.getString(1);

                    pass = resultSet.getString(2);

                    stringList.add(uname);

                    stringList.add(pass);

                }

            }

            else
            {
                System.out.println("Connection is not established");
            }

        }

        catch (SQLException e)
        {

            System.out.println("SQL State: "+ e.getSQLState());

            System.out.println("Error Code "+ e.getErrorCode());

            System.out.println(e.getMessage());

        }

        finally
        {
            closeConnection(preparedStatement, con);
        }

        return stringList;
    }

    public List pollingDaoGetSSHAvailabilityData(int id)
    {
        List<Integer> list = null;

        int sshUpCount = 0;

        int sshDownCount = 0;

        ResultSet resultSet;

        Connection con = null;

        PreparedStatement preparedStatement = null;

        try
        {
            con = makeConnection();

            list = new ArrayList<>();

            if ( con != null)
            {

                preparedStatement = con.prepareStatement("SELECT SUM(Status='Up') , SUM(Status='Down') from SSHPolling where Id = ? and PollingTime > now() - interval 1 day;");

                preparedStatement.setInt(1, id);

                resultSet = preparedStatement.executeQuery();

                while (resultSet.next())
                {

                    sshUpCount = resultSet.getInt(1);

                    sshDownCount = resultSet.getInt(2);

                }

                list.add(sshUpCount);

                list.add(sshDownCount);

            }

            else
            {
                System.out.println("Connection is not established");
            }

        }

        catch (SQLException e)
        {

            System.out.println("SQL State: "+ e.getSQLState());

            System.out.println("Error Code "+ e.getErrorCode());

            System.out.println(e.getMessage());

        }

        finally
        {
            closeConnection(preparedStatement, con);
        }

        return list;
    }

    public List pollingDaoGetPingAvailabilityData(int id)
    {
        List<Integer> list = null;

        int pingUpCount = 0;

        int pingDownCount = 0;

        ResultSet resultSet;

        Connection con = null;

        PreparedStatement preparedStatement = null;

        try
        {
            con = makeConnection();

            list = new ArrayList<>();

            if ( con != null)
            {

                preparedStatement = con.prepareStatement("SELECT SUM(Status='Up') , SUM(Status='Down') from PingPolling where Id = ? and PollingTime > now() - interval 1 day;");

                preparedStatement.setInt(1, id);

                resultSet = preparedStatement.executeQuery();

                while (resultSet.next())
                {

                    pingUpCount = resultSet.getInt(1);

                    pingDownCount = resultSet.getInt(2);

                }

                list.add(pingUpCount);

                list.add(pingDownCount);

            }

            else
            {
                System.out.println("Connection is not established");
            }

        }

        catch (SQLException e)
        {

            System.out.println("SQL State: "+ e.getSQLState());

            System.out.println("Error Code "+ e.getErrorCode());

            System.out.println(e.getMessage());

        }

        finally
        {
            closeConnection(preparedStatement, con);
        }

        return list;
    }

    public List pollingDaoGetPingData(int id)
    {
        List<Object> list = null;

        int sendPacket = 0;

        int receivepacket = 0;

        int packetLoss = 0;

        float rtt = 0;

        Timestamp timestamp = null;

        ResultSet resultSet;

        Connection con = null;

        PreparedStatement preparedStatement = null;

        try
        {
            con = makeConnection();

            list = new ArrayList<>();

            if ( con != null)
            {

                preparedStatement = con.prepareStatement("select SendPacket, ReceivePacket, PacketLoss, RTT, PollingTime FROM PingPolling p1 WHERE Id=? and PollingTime = (select max(PollingTime) from PingPolling p2 where p1.Id = p2.Id)");

                preparedStatement.setInt(1, id);

                resultSet = preparedStatement.executeQuery();

                while (resultSet.next())
                {

                    sendPacket = resultSet.getInt(1);

                    receivepacket = resultSet.getInt(2);

                    packetLoss = resultSet.getInt(3);

                    rtt = resultSet.getFloat(4);

                    timestamp = resultSet.getTimestamp(5);

                    list.add(sendPacket);

                    list.add(receivepacket);

                    list.add(packetLoss);

                    list.add(rtt);

                    list.add(timestamp);
                }
            }

            else
            {
                System.out.println("Connection is not established");
            }

        }

        catch (SQLException e)
        {

            System.out.println("SQL State: "+ e.getSQLState());

            System.out.println("Error Code "+ e.getErrorCode());

            System.out.println(e.getMessage());

        }

        finally
        {
            closeConnection(preparedStatement, con);
        }

        return list;
    }

    public List pollingDaoGetSSHData(int id)
    {
        List<Object> list = null;

        float cpu = 0;

        float memory = 0;

        float swapMemory = 0;

        float disk = 0;

        Timestamp sshTimestamp = null;

        ResultSet resultSet;

        Connection con = null;

        PreparedStatement preparedStatement = null;

        try
        {
            con = makeConnection();

            list = new ArrayList<>();

            if ( con != null)
            {

                preparedStatement = con.prepareStatement("select CPU, Memory, Disk, SwapMemory, PollingTime  FROM SSHPolling s1 WHERE Id=? and PollingTime = (select max(PollingTime) from SSHPolling s2 where s1.Id = s2.Id)");

                preparedStatement.setInt(1, id);

                resultSet = preparedStatement.executeQuery();

                while (resultSet.next())
                {

                    cpu = resultSet.getFloat(1);

                    memory = resultSet.getFloat(2);

                    disk = resultSet.getFloat(3);

                    swapMemory  =resultSet.getFloat(4);

                    sshTimestamp = resultSet.getTimestamp(5);

                }

                list.add(cpu);
                list.add(memory);
                list.add(disk);
                list.add(swapMemory);
                list.add(sshTimestamp);
            }

            else
            {
                System.out.println("Connection is not established");
            }

        }

        catch (SQLException e)
        {

            System.out.println("SQL State: "+ e.getSQLState());

            System.out.println("Error Code "+ e.getErrorCode());

            System.out.println(e.getMessage());

        }

        finally
        {
            closeConnection(preparedStatement, con);
        }

        return list;
    }

    public Map pollingDaoGetPingRttData(int id)
    {
        Map<Timestamp, Float> mapofRtt = null;

        float rtt = 0;

        Timestamp sshTimestamp = null;

        ResultSet resultSet;

        Connection con = null;

        PreparedStatement preparedStatement = null;

        try
        {
            con = makeConnection();

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
                System.out.println("Connection is not established");
            }

        }

        catch (SQLException e)
        {

            System.out.println("SQL State: "+ e.getSQLState());

            System.out.println("Error Code "+ e.getErrorCode());

            System.out.println(e.getMessage());

        }

        finally
        {
            closeConnection(preparedStatement, con);
        }

        return mapofRtt;
    }

    public Map pollingDaoGetSSHCpuData(int id)
    {
        Map<Timestamp, Float> map = null;

        float cpu = 0;

        Timestamp sshTimestamp = null;

        ResultSet resultSet;

        Connection con = null;

        PreparedStatement preparedStatement = null;

        try
        {
            con = makeConnection();

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
                System.out.println("Connection is not established");
            }

        }

        catch (SQLException e)
        {

            System.out.println("SQL State: "+ e.getSQLState());

            System.out.println("Error Code "+ e.getErrorCode());

            System.out.println(e.getMessage());

        }

        finally
        {
            closeConnection(preparedStatement, con);
        }

        return map;
    }
}
