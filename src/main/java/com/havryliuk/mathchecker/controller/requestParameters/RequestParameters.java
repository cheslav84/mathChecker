package com.havryliuk.mathchecker.controller.requestParameters;

/**
 * A list of parameters that can be got from HttpServletRequest,
 * and we receive from the view layer.
 */
public interface RequestParameters {
    String EQUATION_ID = "equationId";
    String EQUATION = "equation";
    String ROOT = "root";
    String ROOTS_AMOUNT = "rootsAmount";

}
