
<%@ page import="rgms.publication.ResearchLine" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'researchLine.label', default: 'Research Line')}" />
        <g:set var="member" value="${message(code: 'member.label', default: '')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-researchLine" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
                <form name="input" action="listSearchMember" method="post">
                    <input type="text" name="member">
                    <input type="submit" value="Buscar Membro">
                </form>

                <form name="input" action="listSearchArticle" method="post">
                    <input type="text" name="artigo">
                    <input type="submit" value="Buscar Artigo">
                </form>
            </ul>
		</div>

        <!-- #if($XMLImp && $ReseachLine) -->
        <div class="xml" role="xmlUpload">
            <ul>
                <br>
                <g:form controller="XML" action="uploadXMLResearchLine" method="post" enctype="multipart/form-data">
                    <label for="file">&nbsp;&nbsp;&nbsp;&nbsp;Importar XML:</label>
                    <input type="file" name="file" id="file"/>
                    <input class="save" type="submit" value="Enviar"/>
                </g:form>
            </ul>
        </div>
        <!-- #end -->

		<div id="list-researchLine" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="name" title="${message(code: 'researchLine.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="description" title="${message(code: 'researchLine.description.label', default: 'Description')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${researchLineInstanceList}" status="i" var="researchLineInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${researchLineInstance.id}">${fieldValue(bean: researchLineInstance, field: "name")}</g:link></td>
					
						<td>${fieldValue(bean: researchLineInstance, field: "description")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${researchLineInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
