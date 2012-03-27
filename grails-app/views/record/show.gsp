

<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'record.label', default: 'Record')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-record" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-record" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list record">
			
				<g:if test="${recordInstance?.start}">
				<li class="fieldcontain">
					<span id="start-label" class="property-label"><g:message code="record.start.label" default="Start" /></span>
					
						<span class="property-value" aria-labelledby="start-label"><g:formatDate date="${recordInstance?.start}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${recordInstance?.end}">
				<li class="fieldcontain">
					<span id="end-label" class="property-label"><g:message code="record.end.label" default="End" /></span>
					
						<span class="property-value" aria-labelledby="end-label"><g:formatDate date="${recordInstance?.end}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${recordInstance?.status_H}">
				<li class="fieldcontain">
					<span id="status_H-label" class="property-label"><g:message code="record.status_H.label" default="Status H" /></span>
					
						<span class="property-value" aria-labelledby="status_H-label"><g:fieldValue bean="${recordInstance}" field="status_H"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${recordInstance?.id}" />
					<g:link class="edit" action="edit" id="${recordInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
