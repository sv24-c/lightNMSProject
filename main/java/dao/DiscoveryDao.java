package dao;

import org.apache.commons.codec.binary.Base64;

import java.sql.*;
import java.util.*;

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

    private Connection makeConnection()
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

    private void closeConnection(PreparedStatement preparedStatement, Connection connection)
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

    public boolean discoveryDaoInsertData(String name, String ip, String type, String username, String password)
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

                preparedStatementOfInsert.executeUpdate();

                return true;
            }
        }

        catch (Exception e)
        {
            System.out.println(e.getMessage());

            System.out.println(Arrays.toString(e.getStackTrace()));

        }

        finally
        {
             closeConnection(preparedStatementOfInsert, con);
        }

        return false;
    }

    public List discoveryDaoGetData()
    {
        ResultSet resultSet;

        Connection con = null;

        PreparedStatement preparedStatement = null;

        List<Map<String, Object>> list = null;

        try
        {
            con = makeConnection();

            list = new ArrayList<>();

            if ( con != null)
            {

                preparedStatement = con.prepareStatement("SELECT Id, Name, IP, Type FROM Discovery");

                resultSet = preparedStatement.executeQuery();

                while (resultSet.next())
                {

                    Map<String, Object> map = new LinkedHashMap<String, Object>();

                    map.put("Id", resultSet.getInt(1));

                    map.put("Name", resultSet.getString(2));

                    map.put("IP", resultSet.getString(3));

                    map.put("Type", resultSet.getString(4));

                    list.add(map);

                }
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

    public List discoveryDaoCheckRedundantData(String ip, String type)
    {
        ResultSet resultSet;

        Connection con = null;

        PreparedStatement preparedStatement = null;

        List<String> list = null;

        try
        {
            con = makeConnection();

            list = new ArrayList<>();

            if ( con != null)
            {

                preparedStatement = con.prepareStatement("SELECT IP, Type FROM Discovery WHERE IP =? AND TYPE = ?");

                preparedStatement.setString(1, ip);

                preparedStatement.setString(2, type);

                resultSet = preparedStatement.executeQuery();

                while (resultSet.next())
                {

                    list.add(resultSet.getString(1));

                    list.add(resultSet.getString(2));

                }
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

    public String discoveryDaoGetUsernameData(int id)
    {
        String userName=null;

        ResultSet resultSet;

        Connection con = null;

        PreparedStatement preparedStatement = null;

        try
        {
            con = makeConnection();

            if ( con != null)
            {

                preparedStatement = con.prepareStatement("SELECT Username FROM Discovery WHERE Id=?");

                preparedStatement.setInt(1, id);

                resultSet = preparedStatement.executeQuery();

                while (resultSet.next())
                {

                    userName = resultSet.getString(1);

                }
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

        return userName;
    }

    public List discoveryDaoGetUsernamePasswordData(int id)
    {
        String userName=null;

        String password=null;

        ResultSet resultSet;

        Connection con = null;

        PreparedStatement preparedStatement = null;

        List<String> list = null;

        try
        {
            con = makeConnection();

            list = new ArrayList<>();

            if ( con != null)
            {

                preparedStatement = con.prepareStatement("SELECT Username, Password FROM Discovery WHERE Id=?");

                preparedStatement.setInt(1, id);

                resultSet = preparedStatement.executeQuery();

                while (resultSet.next())
                {

                    userName = resultSet.getString(1);

                    list.add(userName);

                    password = resultSet.getString(2);

                    list.add(password);
                }

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

    public boolean discoveryDaoUpdateData(String name, String ip, String username, String password, int id)
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

                preparedStatementOfUpdate.executeUpdate();

                return true;
            }

            else
            {
                return false;
            }
        }

        catch (Exception e)
        {
            System.out.println(e.getMessage());

            System.out.println(Arrays.toString(e.getStackTrace()));

        }

        finally
        {
            closeConnection(preparedStatementOfUpdate, con);
        }

        return false;
    }

    public boolean discoveryDaoDeleteData(int id)
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

                preparedStatementOfDelete.executeUpdate();

                return true;
            }

            else
            {
                return false;
            }
        }

        catch (Exception e)
        {
            System.out.println(e.getMessage());

            System.out.println(Arrays.toString(e.getStackTrace()));

        }

        finally
        {
            closeConnection(preparedStatementOfDelete, con);
        }

        return false;
    }
}
