<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="rgms.publication.XMLController" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${ message(code: 'xml.label', default: 'XMLImport')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>

<body>
<div class="body">
    <div class="nav" role="navigation">
        <ul>
            <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        </ul>
    </div>
    <br/>
    <g:form controller="XML" method="post" action="upload" enctype="multipart/form-data">
        <input type="file" id="fileInput" name="file"/>
        <input type="submit"/>
    </g:form>
</div>
</body>
</html>
