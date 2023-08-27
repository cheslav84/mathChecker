package com.havryliuk.mathchecker.model.database.dao;

import com.havryliuk.mathchecker.model.entity.Entity;
import com.havryliuk.mathchecker.model.exceptions.DAOException;
import java.sql.Connection;


public abstract class AbstractDao<T extends Entity> {

    protected Connection connection;

    public AbstractDao() {
    }

    public abstract T create(T entity) throws DAOException;


    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
