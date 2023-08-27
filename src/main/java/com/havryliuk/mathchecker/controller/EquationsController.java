package com.havryliuk.mathchecker.controller;

import com.havryliuk.mathchecker.controller.requestParameters.RequestParameters;
import com.havryliuk.mathchecker.controller.dispatchers.PathBuilder;
import com.havryliuk.mathchecker.model.entity.Equation;
import com.havryliuk.mathchecker.model.exceptions.ServiceException;
import com.havryliuk.mathchecker.model.service.EquationService;
import com.havryliuk.mathchecker.model.util.annotations.ApplicationProcessor;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet(urlPatterns = {
        "/equations/create",
        "/equations/includingRoots",
        "/equations/all",
        "/equations/withRootsAmount",
        "/equations/withRootNumber"
})
public class EquationsController extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(EquationsController.class);

    private EquationService equationService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        equationService = ApplicationProcessor.getInstance(EquationService.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        LOG.debug("EquationsController, doGet.");
        setAttributes(request);
        request.getRequestDispatcher(PathBuilder.getPath(request))
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        LOG.debug("EquationsController, doPost.");
        String receivedEquation = request.getParameter(RequestParameters.EQUATION)
                .replaceAll("х","x");
        Equation equation = new Equation(receivedEquation);
        try {
            equationService.createEquation(equation);
            LOG.info("Received valid equation: '{}'", equation);
            response.sendRedirect("/equations/all");
        } catch (IllegalArgumentException | ServiceException e ) {
            request.setAttribute("equation", equation);
            setAndLogError(request, e.getMessage());
            request.getRequestDispatcher(PathBuilder.getPath(request))
                    .forward(request, response);
        }
    }

    private void setAttributes(HttpServletRequest request) {
        switch (request.getRequestURI()) {
            case "/equations/all" -> setAllEquationsAttribute(request);
            case "/equations/includingRoots" -> setEquationIncludingRootsAttribute(request);
            case "/equations/withRootsAmount" -> setEquationWithRootsAmountAttribute(request);
            case "/equations/withRootNumber" -> setEquationWithRootNumberAttribute(request);
        }
    }

    private void setAllEquationsAttribute(HttpServletRequest request) {
        try {
            List<Equation> equations = equationService.getAllEquations();
            request.setAttribute("equations", equations);
            LOG.debug("Received equation list '{}'", Arrays.toString(equations.toArray()));
        } catch (ServiceException e) {
            setAndLogError(request, e.getMessage());
        }
    }


    private void setEquationIncludingRootsAttribute(HttpServletRequest request) {
        try {
            long id = Long.parseLong(request.getParameter(RequestParameters.EQUATION_ID));
            Equation equation = equationService.getEquationIncludingRoots(id);
            request.setAttribute("equation", equation);
            moveErrorMessageFromSessionToRequest(request);
            LOG.debug("Received equation {} with roots '{}'",
                    equation, Arrays.toString(equation.getRoots().toArray()));
        } catch (ServiceException e) {
            setAndLogError(request, e.getMessage());
        }
    }

    private void setEquationWithRootsAmountAttribute(HttpServletRequest request) {
        try {
            String enteredNumber = request.getParameter(RequestParameters.ROOTS_AMOUNT);
            int rootsAmount = Integer.parseInt(enteredNumber);
            List<Equation> equations = equationService.getEquationsByRootsAmount(rootsAmount);
            request.setAttribute("equations", equations);
            LOG.info("Received equation list '{}'", Arrays.toString(equations.toArray()));
        } catch (ServiceException e) {
            setAndLogError(request, e.getMessage());
        }
    }

    private void setEquationWithRootNumberAttribute(HttpServletRequest request) {
        try {
            String enteredNumber = request.getParameter(RequestParameters.ROOT).replace(",", ".");
            double rootNumber = Double.parseDouble(enteredNumber);
            List<Equation> equations = equationService.getEquationsByRoot(rootNumber);
            request.setAttribute("equations", equations);
            LOG.info("Received equation list '{}'", Arrays.toString(equations.toArray()));
        } catch (ServiceException e) {
            setAndLogError(request, e.getMessage());
        }
    }

    private void setAndLogError(HttpServletRequest request, String errorMessage) {
        LOG.error(errorMessage);
        request.setAttribute("errorMessage", errorMessage);
    }

    private void moveErrorMessageFromSessionToRequest(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String errorMessage = (String) session.getAttribute("errorMessage");
        if (errorMessage != null) {
            session.removeAttribute("errorMessage");
            request.setAttribute("errorMessage", errorMessage);
        }
    }
}