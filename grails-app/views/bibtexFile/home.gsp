<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="rgms.publication.BibtexFileController" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${ message(code: 'bibtexfile.label', default: 'BibTexImport')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
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