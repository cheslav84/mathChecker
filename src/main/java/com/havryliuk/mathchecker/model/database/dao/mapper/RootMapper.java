package com.havryliuk.mathchecker.model.database.dao.mapper;

import com.havryliuk.mathchecker.model.database.databaseFieds.DatabaseRows;
import com.havryliuk.mathchecker.model.entity.Root;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RootMapper {

    public static Root mapRoot(ResultSet rs) throws SQLException {
        long id = rs.getLong(DatabaseRows.ROOT_EQUATION_ID);
        double root = rs.getDouble(DatabaseRows.ROOT_NUMBER);
        return new Root(id, root);
    }
}
