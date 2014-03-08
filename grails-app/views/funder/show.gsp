<!-- #if($funder) -->
<%@ page import="rgms.researchProject.Funder" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'funder.label', default: 'Funder')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-funder" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-funder" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list funder">
			
				<g:if test="${funderInstance?.code}">
				<li class="fieldcontain">
					<span id="code-label" class="property-label"><g:message code="funder.code.label" default="Code" /></span>
					
						<span class="property-value" aria-labelledby="code-label"><g:fieldValue bean="${funderInstance}" field="code"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${funderInstance?.nature}">
				<li class="fieldcontain">
					<span id="nature-label" class="property-label"><g:message code="funder.nature.label" default="Nature" /></span>
					
						<span class="property-value" aria-labelledby="nature-label"><g:fieldValue bean="${funderInstance}" field="nature"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${funderInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="funder.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${funderInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${funderInstance?.id}" />
					<g:link class="edit" action="edit" id="${funderInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
<!-- #end -->