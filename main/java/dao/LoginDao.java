package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by smit on 10/3/22.
 */

public class LoginDao //extends ActionSupport
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


    public List logIn(String userName, String password)
    {
        ResultSet resultSet;

        Connection con = null;

        PreparedStatement preparedStatement = null;

        List<Map<String, Object>> list = new ArrayList<>();

        try
        {
            con = makeConnection();

            preparedStatement = con.prepareStatement("SELECT UserName , Password FROM Login where UserName=? AND Password=?");

            preparedStatement.setString(1, userName);

            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
            {
                Map<String, Object> map = new LinkedHashMap<>();

                map.put("UserName", resultSet.getString(1));

                map.put("Password", resultSet.getString(2));

                list.add(map);

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