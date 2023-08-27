package com.havryliuk.mathchecker.controller.dispatchers;

import jakarta.servlet.http.HttpServletRequest;

public class PathBuilder {

    public static String getPath(HttpServletRequest request){
        return "../WEB-INF/jsp" + request.getServletPath() + ".jsp";
    }

}
