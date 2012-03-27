
<%@ page import="rgms.Member" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'member.label', default: 'Member')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-member" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-member" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list member">
			
				<g:if test="${memberInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="member.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${memberInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${memberInstance?.email}">
				<li class="fieldcontain">
					<span id="email-label" class="property-label"><g:message code="member.email.label" default="Email" /></span>
					
						<span class="property-value" aria-labelledby="email-label"><g:fieldValue bean="${memberInstance}" field="email"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${memberInstance?.affiliation}">
				<li class="fieldcontain">
					<span id="affiliation-label" class="property-label"><g:message code="member.affiliation.label" default="Affiliation" /></span>
					
						<span class="property-value" aria-labelledby="affiliation-label"><g:fieldValue bean="${memberInstance}" field="affiliation"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${memberInstance?.university}">
				<li class="fieldcontain">
					<span id="university-label" class="property-label"><g:message code="member.university.label" default="University" /></span>
					
						<span class="property-value" aria-labelledby="university-label"><g:fieldValue bean="${memberInstance}" field="university"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${memberInstance?.phone}">
				<li class="fieldcontain">
					<span id="phone-label" class="property-label"><g:message code="member.phone.label" default="Phone" /></span>
					
						<span class="property-value" aria-labelledby="phone-label"><g:fieldValue bean="${memberInstance}" field="phone"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${memberInstance?.website}">
				<li class="fieldcontain">
					<span id="website-label" class="property-label"><g:message code="member.website.label" default="Website" /></span>
					
						<span class="property-value" aria-labelledby="website-label"><g:fieldValue bean="${memberInstance}" field="website"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${memberInstance?.city}">
				<li class="fieldcontain">
					<span id="city-label" class="property-label"><g:message code="member.city.label" default="City" /></span>
					
						<span class="property-value" aria-labelledby="city-label"><g:fieldValue bean="${memberInstance}" field="city"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${memberInstance?.country}">
				<li class="fieldcontain">
					<span id="country-label" class="property-label"><g:message code="member.country.label" default="Country" /></span>
					
						<span class="property-value" aria-labelledby="country-label"><g:fieldValue bean="${memberInstance}" field="country"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${memberInstance?.active}">
				<li class="fieldcontain">
					<span id="active-label" class="property-label"><g:message code="member.active.label" default="Active" /></span>
					
						<span class="property-value" aria-labelledby="active-label"><g:formatBoolean boolean="${memberInstance?.active}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${memberInstance?.memberships}">
				<li class="fieldcontain">
					<span id="memberships-label" class="property-label"><g:message code="member.memberships.label" default="Memberships" /></span>
					
						<g:each in="${memberInstance.memberships}" var="m">
						<span class="property-value" aria-labelledby="memberships-label"><g:link controller="membership" action="show" id="${m.id}">${m?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${memberInstance?.publications}">
				<li class="fieldcontain">
					<span id="publications-label" class="property-label"><g:message code="member.publications.label" default="Publications" /></span>
					
						<g:each in="${memberInstance.publications}" var="p">
						<span class="property-value" aria-labelledby="publications-label"><g:link controller="publication" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${memberInstance?.id}" />
					<g:link class="edit" action="edit" id="${memberInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
