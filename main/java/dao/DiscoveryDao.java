package dao;

import org.apache.commons.codec.binary.Base64;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by smit on 12/3/22.
 */
public class DiscoveryDao
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

                System.out.println("Connection Closed....");
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    public String discovery(String name, String ip, String type, String username, String password)
    {
        Connection con = null;

        PreparedStatement preparedStatementOfInsert = null;

        try
        {
            con = makeConnection();

            Base64 base64 = new Base64();

            String encodedPassword = new String(base64.encode(password.getBytes()));

            if ( con != null)
            {
                preparedStatementOfInsert = con.prepareStatement("INSERT INTO Discovery (Name, IP, Type, Username, Password) VALUES(?,?,?,?,?)");

                preparedStatementOfInsert.setString(1, name);

                preparedStatementOfInsert.setString(2, ip);

                preparedStatementOfInsert.setString(3, type);

                preparedStatementOfInsert.setString(4, username);

                preparedStatementOfInsert.setString(5, encodedPassword);

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

    public List discoveryShowData()
    {
        ResultSet resultSet;

        Connection con = null;

        PreparedStatement preparedStatement = null;

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        try
        {
            con = makeConnection();

            if ( con != null) {

                preparedStatement = con.prepareStatement("SELECT Id, Name, IP, Type FROM Discovery");

                System.out.println("Prepared statement created successfully");

                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {

                    Map<String, Object> map = new LinkedHashMap<String, Object>();

                    System.out.println("New LinkedHashMap has created ");

                    map.put("Id", resultSet.getInt(1));

                    map.put("Name", resultSet.getString(2));

                    map.put("IP", resultSet.getString(3));

                    map.put("Type", resultSet.getString(4));

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

    public String discoveryGetUsernameDaoData(int id)
    {
        String uname="";

        ResultSet resultSet;

        Connection con = null;

        PreparedStatement preparedStatement = null;

        try
        {
            con = makeConnection();

            if ( con != null) {

                preparedStatement = con.prepareStatement("SELECT Username FROM Discovery WHERE Id=?");

                System.out.println("Prepared statement created successfully");

                preparedStatement.setInt(1, id);

                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {

                    uname = resultSet.getString(1);

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

        return uname;
    }

    public String discoveryUpdateData(String name, String ip, String username, String password, int id)
    {
        Connection con = null;

        PreparedStatement preparedStatementOfUpdate = null;

        try
        {
            con = makeConnection();

            if ( con != null)
            {
                Base64 base64 = new Base64();

                String encodedPassword = new String(base64.encode(password.getBytes()));

                preparedStatementOfUpdate = con.prepareStatement("UPDATE Discovery SET Name=? , IP=? , Username=? , Password=? where Id=?");

                preparedStatementOfUpdate.setString(1, name);

                preparedStatementOfUpdate.setString(2, ip);

                preparedStatementOfUpdate.setString(3, username);

                preparedStatementOfUpdate.setString(4, encodedPassword);

                preparedStatementOfUpdate.setInt(5, id);

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

    public String discoveryDeleteData(int id)
    {
        Connection con = null;

        PreparedStatement preparedStatementOfDelete = null;

        try
        {
            con = makeConnection();

            if ( con != null)
            {
                preparedStatementOfDelete = con.prepareStatement("DELETE FROM Discovery WHERE Id = ?");

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
}
