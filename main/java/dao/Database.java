package dao;

import helper.ConnectionPool;
import helper.Logger;

import java.sql.*;
import java.util.*;

/**
 * Created by smit on 22/4/22.
 */
public class Database
{
    private static final Logger _logger = new Logger();

    private Connection connection = null;

    public List<HashMap<String, Object>> fireSelectQuery(String query, ArrayList<Object> data)
    {
        List<HashMap<String, Object>> resultOfHashMapList = null;

        PreparedStatement preparedStatement = null;

        ResultSet resultSet = null;

        ResultSetMetaData resultSetMetaData = null;

        int columnCount = 0;

        try
        {
            connection = ConnectionPool.getConnection();

            resultOfHashMapList = new ArrayList<>();

            if (connection != null)
            {
                preparedStatement = connection.prepareStatement(query);

                if (data!=null && !data.isEmpty())
                {
                    for (int iterator = 0; iterator<data.size();iterator++)
                    {
                        preparedStatement.setObject(iterator + 1 , data.get(iterator));
                    }
                }

                resultSet = preparedStatement.executeQuery();

                if (resultSet != null)
                {
                    resultSetMetaData = resultSet.getMetaData();

                    columnCount = resultSetMetaData.getColumnCount();

                    while (resultSet.next())
                    {
                        HashMap<String, Object> hashMap = new HashMap<>();

                        for (int iterator = 1; iterator <= columnCount; iterator++)
                        {
                            hashMap.put(resultSetMetaData.getColumnName(iterator), resultSet.getObject(iterator));
                        }
                        resultOfHashMapList.add(hashMap);
                    }
                }
                else
                {
                    _logger.info("Database class fireSelectQuery method has empty resultSet");
                }
            }
            else
            {
                _logger.info("Database class fireSelectQuery method has null connection");
            }
        }
        catch (Exception exception)
        {
            _logger.error("Database class fireSelectQuery method having error. ", exception);
        }
        finally
        {
            try
            {
                if (preparedStatement != null && !preparedStatement.isClosed())
                {
                    preparedStatement.close();
                }
            }
            catch (Exception exception)
            {
                _logger.error("Database class preparedStatement is not close. ", exception);
            }
            try
            {
                ConnectionPool.releaseConnection(connection);
            }
            catch (Exception exception)
            {
                _logger.error("Database class connection is not close. ", exception);
            }
        }
        return resultOfHashMapList;
    }

    public int fireExecuteUpdate(String query, ArrayList<Object> data)
    {
        PreparedStatement preparedStatement = null;

        int affectedRow = 0;

        try
        {
            connection = ConnectionPool.getConnection();

            if (connection != null)
            {
                preparedStatement = connection.prepareStatement(query);

                if (data!=null && !data.isEmpty())
                {
                    for (int iterator = 0; iterator<data.size();iterator++)
                    {
                        preparedStatement.setObject(iterator + 1 , data.get(iterator));
                    }
                    affectedRow = preparedStatement.executeUpdate();
                }
                else
                {
                    _logger.info("Database class fireExecuteUpdate method has empty data");
                }
            }
            else
            {
                _logger.info("Database class fireExecuteUpdate method has null connection");
            }
        }
        catch (Exception exception)
        {
            _logger.error("Database class fireSelectQuery method having error. ", exception);
        }
        finally
        {
            try
            {
                if (preparedStatement != null && !preparedStatement.isClosed())
                {
                    preparedStatement.close();
                }
            }
            catch (Exception exception)
            {
                _logger.error("Database class preparedStatement is not close. ", exception);
            }
            try
            {
                ConnectionPool.releaseConnection(connection);
            }
            catch (Exception exception)
            {
                _logger.error("Database class connection is not close. ", exception);
            }
        }
        return affectedRow;
    }
}
