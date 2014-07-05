<%@ page import="rgms.visit.Visit" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'visit.label', default: 'Visit')}" />
    <title><g:message code="default.confirm.label" args="[entityName]" /></title>
</head>
<body>
<a href="#confirm-visit" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<g:render template="navigation"/>
<div id="confirm-visit" class="content scaffold-edit" role="main">
    <h1><g:message code="default.confirm.label" args="[entityName]" /></h1>
    <g:render template="errors"/>
    <g:message code="visit.label.confirmation" />
    <g:form method="post" >
    	<g:hiddenField name="id" value="${visitInstance?.id}" />
        <g:hiddenField name="version" value="${visitInstance?.version}" />
        <fieldset class="form">
            <div class="fieldcontain ${hasErrors(bean: visitInstance, field: 'visitor', 'error')} required">
    <label for="visitor">
        <g:message code="visit.label.new.name" default="New name of the Visitor" />
        <span class="required-indicator">*</span>
		    </label>
		    <g:textField id="visitor" name="nameVisitor" from="${rgms.visit.Visitor.list()}" optionKey="id" required="" value="${fieldValue(bean: visitInstance, field: "visitor.name")}2" class="many-to-one"/>
		</div>
        </fieldset>
        <fieldset class="buttons">
            <g:actionSubmit class="save" action="update" value="${message(code: 'default.boolean.true', default: 'Yes')}" />
            <g:actionSubmit class="show" action="show" value="${message(code: 'default.boolean.false', default: 'No')}" />
        </fieldset>
    </g:form>
</div>
</body>
</html>
