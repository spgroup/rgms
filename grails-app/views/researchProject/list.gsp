<!-- #if($researchProject) -->
<%@ page import="rgms.researchProject.ResearchProject" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'researchProject.label', default: 'ResearchProject')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-researchProject" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
                <li><g:link class="myProjects" action="myProjects"><g:message code="default.researchproject.myProjects"/></g:link></li>
			</ul>
		</div>

        <!-- #if($XMLImp && $ReseachProject) -->
        <div class="xml" role="xmlUpload">
            <ul>
                <br>
                <g:form controller="XML" action="uploadXMLResearchProject" method="post" enctype="multipart/form-data">
                    <label for="file">&nbsp;&nbsp;&nbsp;&nbsp;Importar XML:</label>
                    <input type="file" name="file" id="file"/>
                    <input class="save" type="submit" value="Enviar"/>
                </g:form>
            </ul>
        </div>
        <!-- #end -->

        <div class="filter" role="filter">
            <ul>
                <br>
                <g:form controller="ResearchProject" action="filter" method="post" enctype="multipart/form-data">
                    <label for="projectName">&nbsp;&nbsp;&nbsp;&nbsp;Filtrar por nome:</label>
                    <input type="text" name="projectName" id="projectName"/>
                    <input class="save" type="submit" value="Filtrar"/>
                </g:form>
            </ul>
        </div>

		<div id="list-researchProject" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="projectName" title="${message(code: 'researchProject.projectName.label', default: 'Project Name')}" />
					
						<g:sortableColumn property="description" title="${message(code: 'researchProject.description.label', default: 'Description')}" />
					
						<g:sortableColumn property="status" title="${message(code: 'researchProject.status.label', default: 'Status')}" />
					
						<g:sortableColumn property="startYear" title="${message(code: 'researchProject.startYear.label', default: 'Start Year')}" />
					
						<g:sortableColumn property="endYear" title="${message(code: 'researchProject.endYear.label', default: 'End Year')}" />
					
						<g:sortableColumn property="responsavel" title="${message(code: 'researchProject.responsible.label', default: 'Responsavel')}" />

                        <g:sortableColumn property="members" title="${message(code: 'researchProject.members.label', default: 'Members')}" />

                    </tr>
				</thead>
				<tbody>
				<g:each in="${researchProjectInstanceList}" status="i" var="researchProjectInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${researchProjectInstance.id}">${fieldValue(bean: researchProjectInstance, field: "projectName")}</g:link></td>
					
						<td>${fieldValue(bean: researchProjectInstance, field: "description")}</td>
					
						<td>${fieldValue(bean: researchProjectInstance, field: "status")}</td>
					
						<td>${fieldValue(bean: researchProjectInstance, field: "startYear")}</td>
					
						<td>${fieldValue(bean: researchProjectInstance, field: "endYear")}</td>
					
						<td>${fieldValue(bean: researchProjectInstance, field: "responsible")}</td>

                        <td>${fieldValue(bean: researchProjectInstance, field: "members")}</td>
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${researchProjectInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
<!-- #end -->