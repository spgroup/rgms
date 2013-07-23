<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
    <meta name="layout" content="main"/>
    <title>Insert title here</title>
</head>

<body>
<div class="body">
    <br/>
    <g:form controller="BibtexFileController" method="post" action="upload" enctype="multipart/form-data">
        <input type="file" name="file"/>
        <input type="submit"/>
    </g:form>
</div>
</body>
</html>