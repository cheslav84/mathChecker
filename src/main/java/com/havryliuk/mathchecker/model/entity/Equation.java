package com.havryliuk.mathchecker.model.entity;

import java.util.Set;

public class Equation implements Entity {
    private long id;
    private final String equality;
    private final String [] expressions;

    private Set<Root> roots;

    public Equation(final String equality) {
        this.equality = equality;
        this.expressions = equality.split("=");
    }

    public Equation(final long id, final String equality) {
        this.id = id;
        this.equality = equality;
        this.expressions = equality.split("=");
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEquality() {
        return equality;
    }

    public String[] getExpressions() {
        return expressions;
    }

    public Set<Root> getRoots() {
        return roots;
    }

    public void setRoots(Set<Root> roots) {
        this.roots = roots;
    }

    public void addRoot(Root root) {
        roots.add(root);
    }

    public String getLeftExpression() {
        return expressions[0];
    }

    public String getRightExpression() {
        return expressions[1];
    }

    @Override
    public String toString() {
        return equality;
    }
}
