<%@ page import="rgms.member.Member" %>
<%@ page import="rgms.authentication.User" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'member.label', default: 'Member')}"/>
    <title><g:message code="default.create.label" args="[entityName]"/></title>
</head>

<body>

<a href="#create-member" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                               default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]"/></g:link></li>
    </ul>
<!-- #if($XMLImp && $Member) -->
    <g:form url="[action: 'uploadMemberXML']" method="post" enctype="multipart/form-data">
        <label for="file">Import XML:</label>
        <input type="file" name="file" id="file"/>
        <input class="save" type="submit" value="Upload"/>
    </g:form>
</div>

<div id="create-member" class="content scaffold-create" role="main">
    <!-- #end -->
    <h1><g:message code="default.create.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${userMemberInstanceList.memberInstance}">
        <ul class="errors" role="alert">
            <g:eachError bean="${userMemberInstanceList.memberInstance}" var="error">
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
            <g:submitButton name="create" class="save"
                            value="${message(code: 'default.button.create.label', default: 'Create')}"/>
        </fieldset>
    </g:form>
</div>

</body>
</html>
