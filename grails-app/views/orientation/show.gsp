
<%@ page import="rgms.member.Orientation" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'orientation.label', default: 'Orientation')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-orientation" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-orientation" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list orientation">
			
				<g:if test="${orientationInstance?.tipo}">
				<li class="fieldcontain">
					<span id="tipo-label" class="property-label"><g:message code="orientation.tipo.label" default="Tipo" /></span>
					
						<span class="property-value" aria-labelledby="tipo-label"><g:fieldValue bean="${orientationInstance}" field="tipo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${orientationInstance?.orientando}">
				<li class="fieldcontain">
					<span id="orientando-label" class="property-label"><g:message code="orientation.orientando.label" default="Orientando" /></span>
					
						<span class="property-value" aria-labelledby="orientando-label"><g:link controller="member" action="show" id="${orientationInstance?.orientando?.id}">${orientationInstance?.orientando?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${orientationInstance?.orientador}">
				<li class="fieldcontain">
					<span id="orientador-label" class="property-label"><g:message code="orientation.orientador.label" default="Orientador" /></span>
					
						<span class="property-value" aria-labelledby="orientador-label"><g:link controller="member" action="show" id="${orientationInstance?.orientador?.id}">${orientationInstance?.orientador?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${orientationInstance?.periodico}">
				<li class="fieldcontain">
					<span id="periodico-label" class="property-label"><g:message code="orientation.periodico.label" default="Periodico" /></span>
					
						<span class="property-value" aria-labelledby="periodico-label"><g:link controller="periodico" action="show" id="${orientationInstance?.periodico?.id}">${orientationInstance?.periodico?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${orientationInstance?.descricao}">
				<li class="fieldcontain">
					<span id="descricao-label" class="property-label"><g:message code="orientation.descricao.label" default="Descricao" /></span>
					
						<span class="property-value" aria-labelledby="descricao-label"><g:fieldValue bean="${orientationInstance}" field="descricao"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${orientationInstance?.id}" />
					<g:link class="edit" action="edit" id="${orientationInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
