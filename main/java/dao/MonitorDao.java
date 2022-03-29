package dao;

import org.apache.commons.codec.binary.Base64;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by smit on 22/3/22.
 */
public class MonitorDao
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

    public List monitorShowData(int id)
    {
        ResultSet resultSet;

        Connection con = null;

        PreparedStatement preparedStatement = null;

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        try
        {
            con = makeConnection();

            if ( con != null)
            {

                preparedStatement = con.prepareStatement("SELECT Name, IP, Type, Username, Password FROM Discovery where Id = ?");

                System.out.println("Prepared statement created successfully");

                preparedStatement.setInt(1, id);

                resultSet = preparedStatement.executeQuery();

                 while (resultSet.next())
                {

                    Map<String, Object> map = new LinkedHashMap<String, Object>();

                    System.out.println("New LinkedHashMap has created ");

                    map.put("Name", resultSet.getString(1));

                    map.put("IP", resultSet.getString(2));

                    map.put("Type", resultSet.getString(3));

                    map.put("Username", resultSet.getString(4));

                    map.put("Password", resultSet.getString(5));

                    list.add(map);

                    System.out.println("Map added to list.");

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

    public String monitorInsertInDataBase(int id, String name, String ip, String type)
    {
        Connection con = null;

        PreparedStatement preparedStatementOfInsert = null;

        String availability = "unknown";

        try
        {
            con = makeConnection();

            if ( con != null)
            {
                preparedStatementOfInsert = con.prepareStatement("INSERT INTO Monitor (Id, Name, IP, Type, Availability) VALUES(?,?,?,?,?)");

                preparedStatementOfInsert.setInt(1, id);

                preparedStatementOfInsert.setString(2, name);

                preparedStatementOfInsert.setString(3, ip);

                preparedStatementOfInsert.setString(4, type);

                preparedStatementOfInsert.setString(5, availability);

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

    public List monitorShowAllData()
    {
        ResultSet resultSet;

        Connection con = null;

        PreparedStatement preparedStatement = null;

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        try
        {
            con = makeConnection();

            if ( con != null) {

                preparedStatement = con.prepareStatement("SELECT Id, Name, IP, Type, Availability FROM Monitor");

                System.out.println("Prepared statement created successfully");

                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {

                    Map<String, Object> map = new LinkedHashMap<String, Object>();

                    System.out.println("New LinkedHashMap has created ");

                    map.put("Id", resultSet.getInt(1));

                    map.put("Name", resultSet.getString(2));

                    map.put("IP", resultSet.getString(3));

                    map.put("Type", resultSet.getString(4));

                    map.put("Availability", resultSet.getString(5));

                    list.add(map);

                    System.out.println("Map added to list.");

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

    public String  monitorDeleteData(int id)
    {
        Connection con = null;

        PreparedStatement preparedStatementOfDelete = null;

        try
        {
            con = makeConnection();

            if ( con != null)
            {
                preparedStatementOfDelete = con.prepareStatement("DELETE FROM Monitor WHERE Id = ?");

                preparedStatementOfDelete.setInt(1, id);

                int result = preparedStatementOfDelete.executeUpdate();

                System.out.println(result + " record Deleted successfully");

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
            closeConnection(preparedStatementOfDelete, con);
        }

        return null;
    }

    public String monitorAvailabilityUpdate(String availability, int id)
    {
        Connection con = null;

        PreparedStatement preparedStatementOfUpdate = null;

        try
        {
            con = makeConnection();

            if ( con != null)
            {

                preparedStatementOfUpdate = con.prepareStatement("UPDATE Monitor SET Availability=? where Id=?");

                preparedStatementOfUpdate.setString(1, availability);

                preparedStatementOfUpdate.setInt(2, id);

                int result = preparedStatementOfUpdate.executeUpdate();

                System.out.println(result + " record Updated successfully");

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
            closeConnection(preparedStatementOfUpdate, con);
        }

        return null;
    }

    public int monitorCheckRedundantIdData(int id)
    {
        ResultSet resultSet;

        Connection con = null;

        PreparedStatement preparedStatement = null;

        int resultid = 0;

        try
        {
            con = makeConnection();

            if ( con != null)
            {

                preparedStatement = con.prepareStatement("SELECT Id FROM Monitor where Id = ?");

                System.out.println("Prepared statement created successfully");

                preparedStatement.setInt(1, id);

                resultSet = preparedStatement.executeQuery();

                while (resultSet.next())
                {
                    resultSet.getInt(id);

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

        return resultid;
    }
}


