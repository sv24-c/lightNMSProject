package dao;
import helper.ConnectionPool;
import helper.Logger;

import java.sql.*;
/**
 * Created by smit on 10/3/22.
 */

public class LoginDao
{
    Connection con = null;

    PreparedStatement preparedStatement = null;

    ResultSet resultSet = null;

    private static final Logger _logger = new Logger();

    public boolean logIn(String userName, String password)
    {
        boolean verifyStatus = false;

        try
        {
            con = ConnectionPool.getConnection();

            preparedStatement = con.prepareStatement("SELECT UserName , Password FROM Login where UserName LIKE BINARY ? AND Password LIKE BINARY ? ");

            preparedStatement.setString(1, userName);

            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next())
            {
                verifyStatus = true;
            }
        }
        catch (SQLException exception)
        {
            _logger.error("Credential is not valid!", exception);
        }
        finally
        {
            ConnectionPool.closePreparedStatement(preparedStatement);

            ConnectionPool.releaseConnection(con);
        }

        return verifyStatus;
    }
}