package com.havryliuk.mathchecker.model.database.dao.daoImpl;

import com.havryliuk.mathchecker.model.database.dao.AbstractDao;
import com.havryliuk.mathchecker.model.database.dao.mapper.RootMapper;
import com.havryliuk.mathchecker.model.database.databaseFieds.DatabaseRows;
import com.havryliuk.mathchecker.model.entity.Root;
import com.havryliuk.mathchecker.model.exceptions.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class RootDao extends AbstractDao<Root> {
    private static final Logger LOG = LogManager.getLogger(RootDao.class);

    @SuppressWarnings("UnusedAssignment")
    @Override
    public Root create(Root root) throws DAOException {
        String query = "INSERT INTO root ("
                + DatabaseRows.ROOT_EQUATION_ID + ", "
                + DatabaseRows.ROOT_NUMBER
                + ") values (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            int k = 1;
            stmt.setLong(k++, root.getEquationId());
            stmt.setDouble(k++, root.getRootNumber());
            stmt.executeUpdate();
            LOG.info("The equation root '{}' has been added to database.", root);
        } catch (SQLException e){
            String errorMessage = "Error in inserting root to database.";
            LOG.error(errorMessage, e);
            throw new DAOException(errorMessage, e);
        }
        return root;
    }

    @SuppressWarnings("UnusedAssignment")
    public Optional<Root> findById(long equationId, double rootNumber) throws DAOException {
        Root root = null;
        String query = "SELECT * FROM root WHERE "
                + DatabaseRows.ROOT_EQUATION_ID + "=? AND "
                + DatabaseRows.ROOT_NUMBER + "=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            int k = 1;
            stmt.setLong(k++, equationId);
            stmt.setDouble(k++, rootNumber);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    root = RootMapper.mapRoot(rs);
                }
            }
            LOG.debug("The root '{}' has been received from database.", root);
        } catch (SQLException e) {
            String errorMessage = "Error in getting root from database.";
            LOG.error(errorMessage, e);
            throw new DAOException(errorMessage, e);
        }
        return Optional.ofNullable(root);
    }


    public Set<Root> findAllByEquationId(long equationId) throws DAOException {
        Set<Root> roots = new HashSet<>();
        String query = "SELECT * FROM root WHERE " + DatabaseRows.ROOT_EQUATION_ID + " = ?" ;
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, equationId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    roots.add(RootMapper.mapRoot(rs));
                }
            }
            LOG.debug("Roots has been received from database.");
        } catch (SQLException e) {
            String errorMessage = "Error in getting roots from database.";
            LOG.error(errorMessage, e);
            throw new DAOException(errorMessage, e);
        }
        return roots;
    }




}
