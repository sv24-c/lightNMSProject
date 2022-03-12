package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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


    public void discovery()
    {
        Connection con = null;

        PreparedStatement preparedStatementOfInsert = null;

        try
        {
            con = makeConnection();

            if ( con != null)
            {
                preparedStatementOfInsert = con.prepareStatement("INSERT INTO Discovery VALUES(?,?,?)");

                preparedStatementOfInsert.setInt(1, 333);

                preparedStatementOfInsert.setString(2, "xyzxyz");

                preparedStatementOfInsert.setInt(3, 33);

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
    }
}
