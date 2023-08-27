<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta content="text/html; charset=UTF-8">
        <link href="../view/css/common.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <h2 class="title">Math Checker</h2>
        <div class="container"></div>
        <div class="center">
            <div class="navigation">
                <a class="link" href="../index">Home page</a>
                <a class="link" href="../equations/all">Show all the equations</a>
            </div>

            <div>
                <c:if test="${!empty equation.roots}">
                    <p>Equation "${equation.equality}".</p>
                    <table class="center">
                        <th>Roots</th>
                        <c:forEach items="${equation.roots}" var="root">
                            <tr>
                                <td>${root.rootNumber}</td>
                            </tr>
                        </c:forEach>
                    </table>
                    <p>Create another root.</p>
                </c:if>
            </div>
            <div>
                <c:if test="${empty equation.roots}">
                    <div class="">
                        <p>There is no any roots for the equation "${equation.equality}" yet.</p>

                    </div>
                </c:if>
                <form method="post" action="/roots/create" name="rootForm">
                    <input class="center" type="number" name="root" minlength="1" maxlength="20" required="true" value="${root}" />
                    <input type="hidden" name="equationId" value="${equation.id}">
                    <input type="submit" value="Save" />
                    <div class="error-message">
                        <c:out value="${errorMessage}" />
                    </div>
                </form> 
            </div>
    </div>
  
    </body>
</html>