package dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

            System.out.println("Connection Established..");

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

                System.out.println("Connection Closed..");
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public String pollingPingInsertInDataBase(int id, String ip, int sendPacket, int receivePacket, int packetLoss, float rtt, Timestamp time)
    {
        Connection con = null;

        PreparedStatement preparedStatementOfInsert = null;

        try
        {
            con = makeConnection();

            if ( con != null)
            {
                preparedStatementOfInsert = con.prepareStatement("INSERT INTO PingPolling (Id, IP, SendPacket, ReceivePacket, PacketLoss, RTT, PollingTime) VALUES(?,?,?,?,?,?,?)");

                preparedStatementOfInsert.setInt(1, id);

                preparedStatementOfInsert.setString(2, ip);

                preparedStatementOfInsert.setInt(3, sendPacket);

                preparedStatementOfInsert.setInt(4, receivePacket);

                preparedStatementOfInsert.setInt(5, packetLoss);

                preparedStatementOfInsert.setFloat(6, rtt);

                preparedStatementOfInsert.setTimestamp(7, (time));

                int result = preparedStatementOfInsert.executeUpdate();

                System.out.println(result + " record inserted successfully");

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

        return null;
    }

    public String pollingSSHInsertInDataBase(int id, String ip, double cpu, double memory, float disk, Timestamp time)
    {
        Connection con = null;

        PreparedStatement preparedStatementOfInsert = null;

        try
        {
            con = makeConnection();

            if ( con != null)
            {
                preparedStatementOfInsert = con.prepareStatement("INSERT INTO SSHPolling (Id, IP, CPU, Memory, Disk, PollingTime) VALUES(?,?,?,?,?,?)");

                preparedStatementOfInsert.setInt(1, id);

                preparedStatementOfInsert.setString(2, ip);

                preparedStatementOfInsert.setDouble(3, cpu);

                preparedStatementOfInsert.setDouble(4, memory);

                preparedStatementOfInsert.setFloat(5, disk);

                preparedStatementOfInsert.setTimestamp(6, time);

                int result = preparedStatementOfInsert.executeUpdate();

                System.out.println(result + " record inserted successfully");

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

        return null;
    }

    public List getUsernamePasswordDaoData(int id)
    {

        String uname=null;

        String pass = null;

        List<String> stringList = new ArrayList<>();

        ResultSet resultSet;

        Connection con = null;

        PreparedStatement preparedStatement = null;

        try
        {
            con = makeConnection();

            if ( con != null) {

                preparedStatement = con.prepareStatement("SELECT Username, Password FROM Discovery WHERE Id=?");

                System.out.println("Prepared statement created successfully");

                preparedStatement.setInt(1, id);

                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {

                    uname = resultSet.getString(1);

                    pass = resultSet.getString(2);

                }

                stringList.add(uname);

                stringList.add(pass);
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

    public List getPollingPingData(int id)
    {
        List<Object> list = new ArrayList<>();

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

            if ( con != null) {

                preparedStatement = con.prepareStatement("select SendPacket, ReceivePacket, PacketLoss, RTT, PollingTime FROM PingPolling p1 WHERE Id=? and PollingTime = (select max(PollingTime) from PingPolling p2 where p1.Id = p2.Id)");

                System.out.println("Prepared statement created successfully");

                preparedStatement.setInt(1, id);

                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {

                    sendPacket = resultSet.getInt(1);

                    receivepacket = resultSet.getInt(2);

                    packetLoss = resultSet.getInt(3);

                    rtt = resultSet.getInt(4);

                    timestamp = resultSet.getTimestamp(5);

                }


                list.add(sendPacket);
                list.add(receivepacket);
                list.add(packetLoss);
                list.add(rtt);
                list.add(timestamp);
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

    public List getPollingSSHData(int id)
    {
        List<Object> list = new ArrayList<>();

        float cpu = 0;

        float memory = 0;

        float disk = 0;

        Timestamp sshTimestamp = null;

        ResultSet resultSet;

        Connection con = null;

        PreparedStatement preparedStatement = null;

        try
        {
            con = makeConnection();

            if ( con != null) {

                preparedStatement = con.prepareStatement("select CPU, Memory, Disk, PollingTime FROM PingPolling p1 WHERE Id=? and PollingTime = (select max(PollingTime) from PingPolling p2 where p1.Id = p2.Id)");

                System.out.println("Prepared statement created successfully");

                preparedStatement.setInt(1, id);

                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {

                    cpu = resultSet.getFloat(1);

                    memory = resultSet.getFloat(2);

                    disk = resultSet.getFloat(3);

                    sshTimestamp = resultSet.getTimestamp(4);

                }


                list.add(cpu);
                list.add(memory);
                list.add(disk);
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
}
