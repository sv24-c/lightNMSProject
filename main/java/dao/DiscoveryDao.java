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


    public String discovery(String Name, String IP, String Type, String username, String password)
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
                preparedStatementOfInsert = con.prepareStatement("INSERT  INTO Discovery VALUES(?,?,?,?,?)");

                preparedStatementOfInsert.setString(1, Name);

                preparedStatementOfInsert.setString(2, IP);

                preparedStatementOfInsert.setString(3, Type);

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

            preparedStatement = con.prepareStatement("SELECT * FROM Discovery");

            System.out.println("Prepared statement created successfully");

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
            {
                Map<String, Object> map = new LinkedHashMap<String, Object>();

                System.out.println("New LinkedHashMap has created ");

                map.put("Name", resultSet.getString(1));

                map.put("IP", resultSet.getString(2));

                map.put("Type", resultSet.getString(3));

                list.add(map);

                System.out.println("Map added to list.");

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

    public String discoveryUpdateData(String Name, String IP)
    {
        Connection con = null;

        PreparedStatement preparedStatementOfInsert = null;

        try
        {
            con = makeConnection();

            if ( con != null)
            {
                preparedStatementOfInsert = con.prepareStatement("UPDATE Discovery SET Name=?, IP=?)");

                preparedStatementOfInsert.setString(1, Name);

                preparedStatementOfInsert.setString(2, IP);

                int result = preparedStatementOfInsert.executeUpdate();

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
            closeConnection(preparedStatementOfInsert, con);
        }

        return null;
    }

    public String discoveryDeleteData(String Name)
    {
        Connection con = null;

        PreparedStatement preparedStatementOfInsert = null;

        try
        {
            con = makeConnection();

            if ( con != null)
            {
                preparedStatementOfInsert = con.prepareStatement("DELETE FROM Discovery WHERE Name=?)");

                preparedStatementOfInsert.setString(1, Name);

                int result = preparedStatementOfInsert.executeUpdate();

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
            closeConnection(preparedStatementOfInsert, con);
        }

        return null;
    }
}
