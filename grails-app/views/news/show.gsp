
<%@ page import="rgms.news.News" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'news.label', default: 'News')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-news" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-news" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list news">
			
				<g:if test="${newsInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="news.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${newsInstance}" field="description"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${newsInstance?.date}">
				<li class="fieldcontain">
					<span id="date-label" class="property-label"><g:message code="news.date.label" default="Date" /></span>
					
						<span class="property-value" aria-labelledby="date-label"><g:formatDate date="${newsInstance?.date}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${newsInstance?.researchGroup}">
				<li class="fieldcontain">
					<span id="researchGroup-label" class="property-label"><g:message code="news.researchGroup.label" default="Research Group" /></span>
					
						<span class="property-value" aria-labelledby="researchGroup-label"><g:link controller="researchGroup" action="show" id="${newsInstance?.researchGroup?.id}">${newsInstance?.researchGroup?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${newsInstance?.id}" />
					<g:link class="edit" action="edit" id="${newsInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
