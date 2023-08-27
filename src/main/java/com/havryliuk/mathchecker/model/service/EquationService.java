package com.havryliuk.mathchecker.model.service;

import com.havryliuk.mathchecker.model.database.dao.EntityTransaction;
import com.havryliuk.mathchecker.model.database.dao.daoImpl.EquationDao;
import com.havryliuk.mathchecker.model.database.dao.daoImpl.RootDao;
import com.havryliuk.mathchecker.model.entity.Equation;
import com.havryliuk.mathchecker.model.entity.Root;
import com.havryliuk.mathchecker.model.exceptions.DAOException;
import com.havryliuk.mathchecker.model.exceptions.ServiceException;
import com.havryliuk.mathchecker.model.util.annotations.Autowired;
import com.havryliuk.mathchecker.model.util.annotations.Injectable;
import com.havryliuk.mathchecker.model.util.validation.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Set;

public class EquationService implements Injectable {
    private static final Logger LOG = LogManager.getLogger(EquationService.class);

    @Autowired
    @SuppressWarnings("unused")
    private EntityTransaction transaction;
    @Autowired
    @SuppressWarnings("unused")
    private EquationDao equationDao;
    @Autowired
    @SuppressWarnings("unused")
    private RootDao rootDao;

    public EquationService() {
    }

    public Equation createEquation(Equation equation) throws ServiceException {
        try {
            Validator.validate(equation);
            transaction.initTransaction(equationDao);
            checkIfExists(equation);
            equationDao.create(equation);
            LOG.debug("Received valid equation: '{}'", equation);
            return equation;
        } catch (DAOException e) {
            transaction.rollback();
            String message = "Equation is correct but wasn't been saved.";
            LOG.error(message, e);
            throw new ServiceException(message, e);
        } finally {
            transaction.endTransaction();
        }
    }

    public Equation getEquationIncludingRoots(long equationId) throws ServiceException {
        Equation equation = null;
        try {
            transaction.initTransaction(equationDao, rootDao);
            equation = equationDao.findById(equationId)
                    .orElseThrow(() -> new ServiceException("Equation hasn't been found. " +
                            "Please try again later or contact your administrator."));
            Set<Root> roots = rootDao.findAllByEquationId(equationId);
            equation.setRoots(roots);
        } catch (DAOException e) {
            handleException(e);
        } finally {
            transaction.endTransaction();
        }
        return equation;
    }

    public List<Equation> getAllEquations() throws ServiceException {
        List<Equation> equations = null;
        try {
            transaction.init(equationDao);
            equations = equationDao.findAll();
        } catch (DAOException e) {
            handleException(e);
        } finally {
            transaction.end();
        }
        return equations;
    }


    public List<Equation> getEquationsByRoot(double rootNumber) throws ServiceException {
        List<Equation> equations = null;
        try {
            transaction.init(equationDao);
            equations = equationDao.findByRoot(rootNumber);
        } catch (DAOException e) {
            handleException(e);
        } finally {
            transaction.end();
        }
        return equations;
    }

    public List<Equation> getEquationsByRootsAmount(int rootsAmount) throws ServiceException {
        List<Equation> equations = null;
        try {
            transaction.init(equationDao);
            equations = equationDao.findByRootsAmount(rootsAmount);
        } catch (DAOException e) {
            handleException(e);
        } finally {
            transaction.end();
        }
        return equations;
    }

    private void checkIfExists(Equation equation) throws ServiceException {
        if (equalityExists(equation.getEquality())) {
            String message = equation + " equation has already exist.";
            LOG.error(message);
            throw new ServiceException(message);
        }
    }

    private boolean equalityExists(String equality) {
        return equationDao.findByEquality(equality).isPresent();
    }

    private void handleException(DAOException e) throws ServiceException {
        String message = "Equations is currently unavailable. " +
                "Please try again later or contact your administrator.";
        LOG.error(message, e);
        throw new ServiceException(message, e);
    }


}
