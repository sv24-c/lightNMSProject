package dao;

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

    public boolean monitorDaoGetData(int id)
    {
        ResultSet resultSet;

        Connection con = null;

        PreparedStatement preparedStatement = null;

        int checkId=0;

        try
        {
            con = makeConnection();

            if ( con != null)
            {

                preparedStatement = con.prepareStatement("SELECT Id FROM Monitor where Id = ?");

                preparedStatement.setInt(1, id);

                resultSet = preparedStatement.executeQuery();

                while (resultSet.next())
                {

                   checkId = resultSet.getInt(1);

                }

                if (checkId == id)
                {
                    return true;
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

        return false;
    }

    public String monitorDaoInsert(int id, String name, String ip, String type)
    {
        Connection con = null;

        PreparedStatement preparedStatementOfInsert = null;

        String availability;

        try
        {
            con = makeConnection();

            availability = "unknown";

            if ( con != null)
            {
                preparedStatementOfInsert = con.prepareStatement("INSERT INTO Monitor (Id, Name, IP, Type, Availability) VALUES(?,?,?,?,?)");

                preparedStatementOfInsert.setInt(1, id);

                preparedStatementOfInsert.setString(2, name);

                preparedStatementOfInsert.setString(3, ip);

                preparedStatementOfInsert.setString(4, type);

                preparedStatementOfInsert.setString(5, availability);

                preparedStatementOfInsert.executeUpdate();

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

    public List monitorDaoGetAllData()
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

    public boolean monitorDaoDeleteData(int id)
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

            System.out.println(e.getStackTrace());

        }

        finally
        {
            closeConnection(preparedStatementOfDelete, con);
        }

        return false;
    }

    public void monitorAvailabilityUpdate(String availability, int id)
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

                preparedStatementOfUpdate.executeUpdate();

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

    }

    public List<String> monitorDaoGetDataForProvision(int id)
    {
        ResultSet resultSet;

        Connection con = null;

        PreparedStatement preparedStatement = null;

        int resultid = 0;

        List<String> dataList = null;

        try
        {
            con = makeConnection();

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

        return dataList;
    }
}


