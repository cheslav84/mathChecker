package com.havryliuk.mathchecker.controller;

import com.havryliuk.mathchecker.controller.requestParameters.RequestParameters;
import com.havryliuk.mathchecker.model.entity.Root;
import com.havryliuk.mathchecker.model.exceptions.ServiceException;
import com.havryliuk.mathchecker.model.service.RootService;
import com.havryliuk.mathchecker.model.util.annotations.ApplicationProcessor;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;


@WebServlet(urlPatterns = {"/roots/create"})
public class RootsController extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(RootsController.class);

    private RootService rootService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        rootService = ApplicationProcessor.getInstance(RootService.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        LOG.debug("RootsController, doPost.");
            request.getSession().removeAttribute("errorMessage");
        Root root = null;
        try {
            root = extractRoot(request);
            rootService.createRoot(root);
            LOG.info("Received valid root: '{}'", root);
        } catch (NumberFormatException e) {
            String errorMessage = "The '" + request.getParameter(RequestParameters.ROOT) + "' is not a number";
            setAndLogError(request, errorMessage);
        } catch (IllegalArgumentException | ServiceException e ) {
            request.setAttribute("root", root);
            setAndLogError(request, e.getMessage());
        }
        response.sendRedirect("/equations/withRoots?equationId="
                + Long.parseLong(request.getParameter(RequestParameters.EQUATION_ID)));
    }

    private Root extractRoot(HttpServletRequest request) {
        String enteredNumber = request.getParameter(RequestParameters.ROOT).replace(",", ".");
        long equationId = Long.parseLong(request.getParameter(RequestParameters.EQUATION_ID));
        double rootNumber = Double.parseDouble(enteredNumber);
        return new Root(equationId, rootNumber);
    }

    private void setAndLogError(HttpServletRequest request, String errorMessage) {
        LOG.error(errorMessage);
        request.getSession().setAttribute("errorMessage", errorMessage);
    }

}