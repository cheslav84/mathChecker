package com.havryliuk.mathchecker.model.util;

import com.havryliuk.mathchecker.model.entity.Equation;
import com.havryliuk.mathchecker.model.entity.Root;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;


public class EquationEvaluator {
    private final Expression leftExpression;
    private final Expression rightExpression;

    public EquationEvaluator(Equation equation) {
        this.leftExpression = new ExpressionBuilder(equation.getLeftExpression())
                .variables("x")
                .build();
        this.rightExpression = new ExpressionBuilder(equation.getRightExpression())
                .variables("x")
                .build();
    }

    public boolean isRoot (Root root) {
        return isRoot(root.getRootNumber());
    }

    public boolean isRoot (double root) {
        double epsilon = 10e-9;
        return Math.abs(evaluateLeft(root) - evaluateRight(root)) <= epsilon;
    }

    public boolean isExpressionsValid() {
        return leftExpression.validate().isValid() && rightExpression.validate().isValid();
    }

    double evaluateLeft(double root) {
        leftExpression.setVariable("x", root);
        return leftExpression.evaluate();
    }

    double evaluateRight(double root) {
        rightExpression.setVariable("x", root);
        return rightExpression.evaluate();
    }

}
