<%--
  Created by IntelliJ IDEA.
  User: samuel
  Date: 19/01/15
  Time: 19:29
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="rgms.publication.Book" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'book.label', default: 'Book')}"/>
    <title><g:message code="default.remove.label" args="[entityName]"/></title>
</head>

<body>
<a href="#remove-book" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                             default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]"/></g:link></li>
    </ul>
</div>

<div id="remove-book" class="content scaffold-remove" role="main">
    <h1><g:message code="default.remove.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${bookInstance}">
        <ul class="errors" role="alert">
            <g:eachError bean="${bookInstance}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                        error="${error}"/></li>
            </g:eachError>
        </ul>
    </g:hasErrors>
    <g:form action="save">
        <fieldset class="form">
            <g:render template="form"/>
        </fieldset>
        <fieldset class="buttons">
            <g:submitButton name="remove" class="save"
                            value="${message(code: 'default.remove.label', default: 'Remove')}"/>
        </fieldset>
    </g:form>
</div>
</body>
</html>
