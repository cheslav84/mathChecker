package com.havryliuk.mathchecker.model.util.validation;

import com.havryliuk.mathchecker.model.entity.Equation;
import com.havryliuk.mathchecker.model.entity.Root;
import com.havryliuk.mathchecker.model.util.EquationEvaluator;

public class Validator {

    public static void validate(Equation equation) {
        hasTwoExpressions(equation);
        new EquationEvaluator(equation).isExpressionsValid();
        checkLength(equation.getEquality());
    }

    public static void validateRoot(Equation equation, Root root) {
        boolean isRoot = new EquationEvaluator(equation).isRoot(root);
        if (!isRoot) {
            throw new IllegalArgumentException("The '" + root +
                    "' number is not the root of equation '" + equation + "'.");
        }
    }

    private static void checkLength(String equation) {
        if (equation.length() > Regex.MAX_EQUATION_SYMBOLS) {
            throw new IllegalArgumentException("Equation is valid but it should be maximum 255 symbols long.");
        }
    }

    public static void hasTwoExpressions (Equation equation) {
        if (equation.getExpressions().length != 2) {
            throw new IllegalArgumentException("Invalid equation! It should be one equals sign.");
        }
    }

}
