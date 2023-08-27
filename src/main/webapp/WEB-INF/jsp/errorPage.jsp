<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="${sessionScope.locale.language}">
    <head>
        <link href="view/css/common.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <div>
            Request from ${pageContext.errorData.requestURI} is failed
            <br />
            Servlet name or type: ${pageContext.errorData.servletName}
            <br />
            Status code: ${pageContext.errorData.statusCode}
        </div>
    </body>
</html>