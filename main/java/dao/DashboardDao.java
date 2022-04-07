package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by smit on 31/3/22.
 */
public class DashboardDao
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

            System.out.println("Connection Established..");

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

                System.out.println("Connection Closed");
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public List dashboardShowData()
    {
        ResultSet resultSet;

        Connection con = null;

        PreparedStatement preparedStatement = null;

        List<Integer> list = new ArrayList<Integer>();

        int upCount = 0;

        int downCount = 0;

        int unknownCount = 0;

        try
        {
            con = makeConnection();

            if ( con != null)
            {

                preparedStatement = con.prepareStatement("SELECT SUM(Availability ='Up') , SUM(Availability='Down'), SUM(Availability='unknown') from Monitor;");

                System.out.println("Prepared statement created successfully");

                resultSet = preparedStatement.executeQuery();

                while (resultSet.next())
                {

                    upCount = resultSet.getInt(1);

                    downCount = resultSet.getInt(2);

                    unknownCount = resultSet.getInt(3);

                    list.add(upCount);

                    list.add(downCount);

                    list.add(unknownCount);

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
}
