package com.havryliuk.mathchecker.model.entity;

public class Root implements Entity {
    private final long equationId;
    private final double rootNumber;

    public Root(long equationId, double rootNumber) {
        this.equationId = equationId;
        this.rootNumber = rootNumber;
    }

    public long getEquationId() {
        return equationId;
    }

    public double getRootNumber() {
        return rootNumber;
    }

    @Override
    public String toString() {
        return String.valueOf(rootNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Root root = (Root) o;
        if (equationId != root.equationId) return false;
        return Double.compare(root.rootNumber, rootNumber) == 0;
    }

    @Override
    public int hashCode() {
        int result = (int) (equationId ^ (equationId >>> 32));
        long temp = Double.doubleToLongBits(rootNumber);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
