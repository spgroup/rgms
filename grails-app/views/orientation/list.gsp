
<%@ page import="rgms.member.Orientation" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'orientation.label', default: 'Orientation')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-orientation" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
                <g:form url="[action:'uploadOrientationXML']" method="post" enctype="multipart/form-data">
                    <label for="file">Import orientations (XML):</label>
                    <input type="file" name="file" id="file"/>
                    <input class="save" type="submit" value="Upload"/>
                </g:form>
            </ul>
		</div>
		<div id="list-orientation" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
                        <g:sortableColumn property="id" title="${message(code: 'orientation.id.label', default: 'Id')}" />

						<g:sortableColumn property="tipo" title="${message(code: 'orientation.tipo.label', default: 'Tipo')}" />

						<g:sortableColumn property="orientando" title="${message(code: 'orientation.orientando.label', default: 'Orientando')}" />

						<th><g:message code="orientation.orientador.label" default="Orientador" /></th>
					
						<g:sortableColumn property="tituloTese" title="${message(code: 'orientation.tituloTese.label', default: 'Titulo Tese')}" />
					
						<g:sortableColumn property="anoPublicacao" title="${message(code: 'orientation.anoPublicacao.label', default: 'Ano Publicacao')}" />
					
						<g:sortableColumn property="instituicao" title="${message(code: 'orientation.instituicao.label', default: 'Instituicao')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${orientationInstanceList}" status="i" var="orientationInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${orientationInstance.id}">${fieldValue(bean: orientationInstance, field: "id")}</g:link></td>

                        <td>${fieldValue(bean: orientationInstance, field: "tipo")}</td>

                        <td>${fieldValue(bean: orientationInstance, field: "orientando")}</td>
					
						<td>${fieldValue(bean: orientationInstance, field: "orientador")}</td>
					
						<td>${fieldValue(bean: orientationInstance, field: "tituloTese")}</td>
					
						<td>${fieldValue(bean: orientationInstance, field: "anoPublicacao")}</td>
					
						<td>${fieldValue(bean: orientationInstance, field: "instituicao")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${orientationInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
