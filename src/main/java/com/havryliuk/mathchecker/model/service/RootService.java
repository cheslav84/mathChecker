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

public class RootService implements Injectable {
    private static final Logger LOG = LogManager.getLogger(RootService.class);

    @Autowired
    @SuppressWarnings("unused")
    private EntityTransaction transaction;
    @Autowired
    @SuppressWarnings("unused")
    private EquationDao equationDao;

    @Autowired
    @SuppressWarnings("unused")
    private RootDao rootDao;


    public Root createRoot(Root root) throws ServiceException {
        try {
            transaction.initTransaction(equationDao, rootDao);
            Equation equation = equationDao.findById(root.getEquationId())
                    .orElseThrow(() -> new ServiceException("Equation hasn't been found. " +
                            "Please try again later or contact your administrator."));
            Validator.validateRoot(equation, root);
            checkIfExists(root);
            rootDao.create(root);
            LOG.debug("Received valid root '{}' of equation '{}.'", root, equation);
            return root;
        } catch (DAOException e) {
            transaction.rollback();
            String message = "The root can't be checked or saved. " +
                    "Please try again later or contact your administrator.";
            LOG.error(message, e);
            throw new ServiceException(message, e);
        } finally {
            transaction.endTransaction();
        }
    }

    private void checkIfExists(Root root) throws ServiceException {
        if (rootExists(root.getEquationId(), root.getRootNumber())) {
            String message = root +  " root has already exists.";
            LOG.error(message);
            throw new ServiceException(message);
        }
    }

    private boolean rootExists(long equationId, double rootNumber) {
        return rootDao.findById(equationId, rootNumber).isPresent();
    }

}
