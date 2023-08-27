<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
    <c:if test="${!empty equations}">
        <table class="center">
            <th>ID</th>
            <th>Equation</th>
            <th>Roots</th>
            <th>Delete</th>
            <c:forEach items="${equations}" var="equation">
                <tr>
                    <td>${equation.id}</td>
                    <td>${equation.equality}</td>
                    <td><a class="link" href="<c:url value='/equations/includingRoots?equationId=${equation.id}'/>">Show
                            roots</a></td>
                    <td><a class="link" href="<c:url value='/equations/remove?equationId=${equation.id}'/>">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>
<div>
    <c:if test="${empty equations}">
        <div class="">
            There is no any equations yet.
            <a class="link" href="/equations/create">Create a new one.</a>
        </div>
    </c:if>
</div>