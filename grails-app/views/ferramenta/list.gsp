
<%@ page import="rgms.Ferramenta" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'ferramenta.label', default: 'Ferramenta')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-ferramenta" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-ferramenta" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="author" title="${message(code: 'ferramenta.author.label', default: 'Author')}" />
					
						<g:sortableColumn property="descricao" title="${message(code: 'ferramenta.descricao.label', default: 'Descricao')}" />
					
						<g:sortableColumn property="link" title="${message(code: 'ferramenta.link.label', default: 'Link')}" />
					
						<g:sortableColumn property="publicacaoAssociada" title="${message(code: 'ferramenta.publicacaoAssociada.label', default: 'Publicacao Associada')}" />
					
						<g:sortableColumn property="title" title="${message(code: 'ferramenta.title.label', default: 'Title')}" />
					
						<g:sortableColumn property="year" title="${message(code: 'ferramenta.year.label', default: 'Year')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${ferramentaInstanceList}" status="i" var="ferramentaInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${ferramentaInstance.id}">${fieldValue(bean: ferramentaInstance, field: "author")}</g:link></td>
					
						<td>${fieldValue(bean: ferramentaInstance, field: "descricao")}</td>
						
						<td><a href = "${fieldValue(bean: ferramentaInstance, field: "link")}"> ${fieldValue(bean: ferramentaInstance, field: "link")} </a></td>
					
						<td>${fieldValue(bean: ferramentaInstance, field: "publicacaoAssociada")}</td>
					
						<td>${fieldValue(bean: ferramentaInstance, field: "title")}</td>
					
						<td>${fieldValue(bean: ferramentaInstance, field: "year")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${ferramentaInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
