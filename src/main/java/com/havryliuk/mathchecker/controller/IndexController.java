package com.havryliuk.mathchecker.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet(urlPatterns = {"/index"})
public class IndexController extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(IndexController.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        LOG.debug("index, doGet.");
        request.getRequestDispatcher("/WEB-INF/jsp/index.jsp")
                .forward(request, response);
    }

}