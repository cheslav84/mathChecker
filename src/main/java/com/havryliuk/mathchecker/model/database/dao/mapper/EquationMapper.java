package com.havryliuk.mathchecker.model.database.dao.mapper;

import com.havryliuk.mathchecker.model.database.databaseFieds.DatabaseRows;
import com.havryliuk.mathchecker.model.entity.Equation;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EquationMapper {

    public static Equation mapEquation(ResultSet rs) throws SQLException {
        long id = rs.getLong(DatabaseRows.EQUATION_ID);
        String equation = rs.getString(DatabaseRows.EQUATION_EQUALITY);
        return new Equation(id, equation);
    }
}
