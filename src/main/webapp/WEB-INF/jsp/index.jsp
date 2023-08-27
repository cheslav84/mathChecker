<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta content="text/html; charset=UTF-8">
        <link href="view/css/common.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <h2 class="title">Math Checker</h2>
        <div class="container">
            <div class="navigation">
                <a class="link" href="equations/create">Enter a new equation</a>
                <div class="dropdown">
                    <span class="link">Show equations</span>
                    <div class="dropdown-content">
                        <div class="dropdown-item">
                           <a class="link" href="equations/all">All the equations</a>
                        </div>
                        <div class="dropdown-item">
                            <span class="link">Equations with roots amount of</span>
                            <form method="get" action="equations/withRootsAmount" name="equationForm">
                                <input class="center narrow" type="number" name="rootsAmount" minlength="1" maxlength="2" required="true" value="${root}" />
                                <input type="submit" value="Find" />
                            </form>
                        </div>
                        <div class="dropdown-item">
                            <span class="link">Equations with root of</span>
                            <form method="get" action="equations/withRootNumber" name="equationForm">
                                <input class="center" type="number" name="root" minlength="1" maxlength="20" required="true" value="${root}" />
                                <input type="submit" value="Find" />
                            </form> 
                        </div>
                    </div>
                  </div>
            </div>
        </div>
    </body>
</html>
