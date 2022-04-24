package dao;

import helper.ConnectionPool;
import helper.Logger;
import org.apache.commons.codec.binary.Base64;

import java.sql.*;
import java.util.*;

/**
 * Created by smit on 12/3/22.
 */
public class DiscoveryDao
{
    private static final Logger _logger = new Logger();

    private boolean verifyStatus = false;

    public boolean discoveryInsert(String name, String ip, String type, String username, String encodedPassword)
    {
        Connection con = null;

        PreparedStatement preparedStatement = null;

        try
        {
            con = ConnectionPool.getConnection();

            if ( con != null)
            {
                preparedStatement = con.prepareStatement("INSERT INTO Discovery (Name, IP, Type, Username, Password) VALUES(?,?,?,?,?)");

                preparedStatement.setString(1, name);

                preparedStatement.setString(2, ip);

                preparedStatement.setString(3, type);

                preparedStatement.setString(4, username);

                preparedStatement.setString(5, encodedPassword);

                preparedStatement.executeUpdate();

                return true;
            }
        }

        catch (Exception exception)
        {
            _logger.error("DiscoveryDao discoveryInsert method having error. ", exception);

        }

        finally
        {
            ConnectionPool.closePreparedStatement(preparedStatement);

            ConnectionPool.releaseConnection(con);

        }

        return false;
    }

    public List<Map<String, Object>> discoveryFetchData()
    {
        Connection con = null;

        PreparedStatement preparedStatement = null;

        ResultSet resultSet = null;

        List<Map<String, Object>> discoveryFetchDataList = null;

        try
        {
            con = ConnectionPool.getConnection();

            discoveryFetchDataList = new ArrayList<>();

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

                    discoveryFetchDataList.add(map);
                }
            }
        }
        catch (SQLException exception)
        {
            _logger.error("DiscoveryDao discoveryFetchData method having error. ", exception);
        }
        finally
        {
            ConnectionPool.closePreparedStatement(preparedStatement);

            ConnectionPool.releaseConnection(con);
        }
        return discoveryFetchDataList;
    }

    public boolean discoveryCheckDuplicateData(String ip, String type)
    {
        Connection con = null;

        PreparedStatement preparedStatement = null;

        ResultSet resultSet = null;

        try
        {
            con = ConnectionPool.getConnection();

            if ( con != null)
            {

                preparedStatement = con.prepareStatement("SELECT IP, Type FROM Discovery WHERE IP =? AND TYPE = ?");

                preparedStatement.setString(1, ip);

                preparedStatement.setString(2, type);

                resultSet = preparedStatement.executeQuery();

                while (resultSet.next())
                {
                    verifyStatus = true;

                }
            }
        }

        catch (SQLException exception)
        {

            _logger.error("DiscoveryDao discoveryCheckDuplicateData method having error. ", exception);

        }

        finally
        {
            ConnectionPool.closePreparedStatement(preparedStatement);

            ConnectionPool.releaseConnection(con);

        }

        return verifyStatus;
    }

    public String discoveryFetchUsername(int id)
    {
        Connection con = null;

        PreparedStatement preparedStatement = null;

        ResultSet resultSet = null;

        String username=null;

        try
        {

            con = ConnectionPool.getConnection();

            if ( con != null)
            {

                preparedStatement = con.prepareStatement("SELECT Username FROM Discovery WHERE Id=?");

                preparedStatement.setInt(1, id);

                resultSet = preparedStatement.executeQuery();

                while (resultSet.next())
                {

                    username = resultSet.getString(1);

                }
            }
        }

        catch (SQLException exception)
        {

            _logger.error("DiscoveryDao discoveryFetchUsername method having error. ", exception);

        }

        finally
        {
            ConnectionPool.closePreparedStatement(preparedStatement);

            ConnectionPool.releaseConnection(con);

        }

        return username;
    }

    public List<String> discoveryFetchUsernamePassword(int id)
    {
        Connection con = null;

        PreparedStatement preparedStatement = null;

        ResultSet resultSet = null;

        List<String> list = null;

        try
        {

            con = ConnectionPool.getConnection();

            list = new ArrayList<>();

            if ( con != null)
            {

                preparedStatement = con.prepareStatement("SELECT Username, Password FROM Discovery WHERE Id=?");

                preparedStatement.setInt(1, id);

                resultSet = preparedStatement.executeQuery();

                while (resultSet.next())
                {
                    list.add(resultSet.getString(1));

                    list.add(resultSet.getString(2));
                }

            }
        }

        catch (SQLException exception)
        {

            _logger.error("DiscoveryDao discoveryFetchUsernamePassword method having error. ", exception);


        }

        finally
        {
            ConnectionPool.closePreparedStatement(preparedStatement);

            ConnectionPool.releaseConnection(con);

        }

        return list;
    }

    public boolean discoveryUpdate(String name, String ip, String username, String password, int id)
    {
        Connection con = null;

        PreparedStatement preparedStatement = null;

        try
        {
            con = ConnectionPool.getConnection();
            
            if ( con != null)
            {
                Base64 base64 = new Base64();

                String encodedPassword = new String(base64.encode(password.getBytes()));

                preparedStatement = con.prepareStatement("UPDATE Discovery SET Name=? , IP=? , Username=? , Password=? where Id=?");

                preparedStatement.setString(1, name);

                preparedStatement.setString(2, ip);

                preparedStatement.setString(3, username);

                preparedStatement.setString(4, encodedPassword);

                preparedStatement.setInt(5, id);

                preparedStatement.executeUpdate();

                verifyStatus = true;

            }
        }

        catch (Exception exception)
        {
            _logger.error("DiscoveryDao discoveryUpdate method having error. ", exception);

        }

        finally
        {
            ConnectionPool.closePreparedStatement(preparedStatement);

            ConnectionPool.releaseConnection(con);

        }

        return verifyStatus;
    }

    public boolean discoveryDelete(int id)
    {

        Connection con = null;

        PreparedStatement preparedStatement = null;

        try
        {
            con = ConnectionPool.getConnection();

            if ( con != null)
            {
                preparedStatement = con.prepareStatement("DELETE FROM Discovery WHERE Id = ?");

                preparedStatement.setInt(1, id);

                preparedStatement.executeUpdate();

                return true;
            }

            else
            {
                return false;
            }
        }

        catch (Exception exception)
        {
            _logger.error("DiscoveryDao discoveryDelete method having error. ", exception);


        }

        finally
        {
            ConnectionPool.closePreparedStatement(preparedStatement);

            ConnectionPool.releaseConnection(con);

        }

        return false;
    }
}
