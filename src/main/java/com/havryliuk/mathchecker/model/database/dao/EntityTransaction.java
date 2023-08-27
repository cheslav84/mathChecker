package com.havryliuk.mathchecker.model.database.dao;

import com.havryliuk.mathchecker.model.database.connection.ConnectionManager;
import com.havryliuk.mathchecker.model.entity.Entity;
import com.havryliuk.mathchecker.model.exceptions.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class EntityTransaction {
    private static final Logger LOG = LogManager.getLogger(EntityTransaction.class);

    private Connection connection;
    private final ConnectionManager connectionManager;

    public EntityTransaction() {
        connectionManager = ConnectionManager.getInstance();
    }

    public void init(AbstractDao<? extends Entity> dao) throws DAOException {
        if (connection == null) {
            try {
                connection = connectionManager.getConnection();
            } catch (SQLException e) {
                LOG.error("Error setting autocommit to value false.", e);
                throw new DAOException(e);
            }
        }
        dao.setConnection(connection);
    }


    @SafeVarargs
    public final void initTransaction(AbstractDao<? extends Entity> dao, AbstractDao<? extends Entity>... daos)
            throws DAOException {
        try {
            if (connection == null) {
                connection = connectionManager.getConnection();
            }
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            LOG.error("Error setting autocommit to value false.", e);
            throw new DAOException(e);
        }
        dao.setConnection(connection);
        for (AbstractDao<? extends Entity> daoElement : daos) {
            daoElement.setConnection(connection);
        }
    }

    public void end() {
        if (connection != null) {
            connectionManager.close(connection);
            connection = null;
        }
    }

    public void endTransaction() {
        if (connection != null) {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                LOG.error("Error setting autocommit to value true.", e);
            }
            connectionManager.close(connection);
            connection = null;
        }
    }

    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            LOG.error("Error in rolling back changes.", e);
        }
    }

}
