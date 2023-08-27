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
                <a href="../index">Home page</a>
            </div>

            <div>
                <c:if test="${!empty equation.roots}">
                    <table class="center">
                        <th>ID</th>
                        <th>Equation</th>
                        <th>Roots</th>
                        <th>Delete</th>
                        <c:forEach items="${equation.roots}" var="root">
                            <tr>
                                <td>${equation.id}</td>
                                <td>${equation.equality}</td>
                                <td><a href="<c:url value='/root/equation/${equation.id}'/>">Show roots</a></td>
                                <td><a href="<c:url value='/equation/remove/${equation.id}'/>">Delete</a></td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:if>
            </div>
            <div>
                <c:if test="${empty equation.roots}">
                    <div class="">
                        <p>${equation.equality}</p>
                        There is no any roots for the equation "${equation.equality}" yet.
                        <a href="/equations/create">Create a new one.</a> 
                    </div>
                </c:if>
            </div>
    </div>


   
    </body>
</html>