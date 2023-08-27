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
                    </div>
                    <form method="post" action="create" name="equationForm">
                        <input class="center" type="text" name="equation" minlength="3" maxlength="255" required="true" value="${equation}" />
                        <input type="submit" value="Save" />
                        <div class="error-message">
                            <c:out value="${errorMessage}" />
                        </div>
                    </form> 
            </div>
        </div>
    </body>
</html>