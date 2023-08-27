package com.havryliuk.mathchecker.model.database.dao.daoImpl;

import com.havryliuk.mathchecker.model.database.dao.AbstractDao;
import com.havryliuk.mathchecker.model.database.dao.mapper.EquationMapper;
import com.havryliuk.mathchecker.model.database.databaseFieds.DatabaseRows;
import com.havryliuk.mathchecker.model.entity.Equation;
import com.havryliuk.mathchecker.model.exceptions.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EquationDao extends AbstractDao<Equation> {
    private static final Logger LOG = LogManager.getLogger(EquationDao.class);

    @Override
    public Equation create(Equation equation) throws DAOException {
        String query = "INSERT INTO equation (" + DatabaseRows.EQUATION_EQUALITY + ") values (?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, equation.getEquality());
            int insertionsAmount = stmt.executeUpdate();
            if (insertionsAmount > 0) {
                setEquationId(equation, stmt);
            }
            LOG.info("The equation '{}' has been added to database.", equation);
        } catch (SQLException e){
            String errorMessage = "Error in inserting equation to database.";
            LOG.error(errorMessage, e);
            throw new DAOException(errorMessage, e);
        }
        return equation;
    }

    public Optional<Equation> findById(long id) throws DAOException {
        Equation equation;
        String query = "SELECT * FROM equation WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            equation = acceptEquation(stmt);
            LOG.debug("The equation '{}' has been received from database.", equation);
        } catch (SQLException e) {
            String errorMessage = "Error in getting equation from database.";
            LOG.error(errorMessage, e);
            throw new DAOException(errorMessage, e);
        }
        return Optional.ofNullable(equation);
    }

    public Optional<Equation> findByEquality(String equality) throws DAOException {
        Equation equation;
        String query = "SELECT * FROM equation WHERE " + DatabaseRows.EQUATION_EQUALITY + "=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, equality);
            equation = acceptEquation(stmt);
            LOG.debug("The equation '{}' has been received from database.", equation);
        } catch (SQLException e) {
            String errorMessage = "Error in getting equation from database.";
            LOG.error(errorMessage, e);
            throw new DAOException(errorMessage, e);
        }
        return Optional.ofNullable(equation);
    }

    public List<Equation> findAll() throws DAOException {
        List<Equation> equations;
        String query = "SELECT * FROM equation ORDER BY " + DatabaseRows.EQUATION_ID;
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            equations = acceptEquations(stmt);
            LOG.debug("Equations has been received from database.");
        } catch (SQLException e) {
            String errorMessage = "Error in getting equations from database.";
            LOG.error(errorMessage, e);
            throw new DAOException(errorMessage, e);
        }
        return equations;
    }

    public List<Equation> findByRoot(double rootNumber) throws DAOException {
        List<Equation> equations;
        String query = "SELECT " + DatabaseRows.EQUATION_ID + ", " + DatabaseRows.EQUATION_EQUALITY +
                " FROM equation JOIN root ON " +
                DatabaseRows.EQUATION_ID + "=" + DatabaseRows.ROOT_EQUATION_ID +
                " WHERE " + DatabaseRows.ROOT_NUMBER + "=?" +
                " ORDER BY " + DatabaseRows.EQUATION_ID;
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDouble(1, rootNumber);
            equations = acceptEquations(stmt);
            LOG.debug("Equations with root {} has been received from database.", rootNumber);
        } catch (SQLException e) {
            String errorMessage = "Error in getting equations from database.";
            LOG.error(errorMessage, e);
            throw new DAOException(errorMessage, e);
        }
        return equations;
    }


    public List<Equation> findByRootsAmount(int rootsAmount) throws DAOException {
        List<Equation> equations;
        String query = "SELECT * FROM equation WHERE " +
                "(SELECT count(" + DatabaseRows.ROOT_EQUATION_ID +
                ") FROM root WHERE " + DatabaseRows.EQUATION_ID + "=" + DatabaseRows.ROOT_EQUATION_ID + ")=?" +
        " ORDER BY " + DatabaseRows.EQUATION_ID;
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDouble(1, rootsAmount);
            equations = acceptEquations(stmt);
            LOG.debug("Equations with {} root(s) amount has been received from database.", rootsAmount);
        } catch (SQLException e) {
            String errorMessage = "Error in getting equations from database.";
            LOG.error(errorMessage, e);
            throw new DAOException(errorMessage, e);
        }
        return equations;
    }

    private Equation acceptEquation(PreparedStatement stmt) throws SQLException {
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return EquationMapper.mapEquation(rs);
            }
        }
        return null;
    }

    private  List<Equation> acceptEquations(PreparedStatement stmt) throws SQLException {
        List<Equation> equations = new ArrayList<>();
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                equations.add(EquationMapper.mapEquation(rs));
            }
        }
        return equations;
    }

    private void setEquationId(Equation equation, PreparedStatement stmt) throws SQLException {
        try (ResultSet rs = stmt.getGeneratedKeys()) {
            if (rs.next()) {
                equation.setId(rs.getLong(1));
            }
        }
    }

}
