
<%@ page import="rgms.Publication" %>
<!doctype html>
<html>
  <head>
    <meta name="layout" content="main">
  <g:set var="entityName" value="${message(code: 'publication.label', default: 'Publication')}" />
  <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
  <a href="#list-publication" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
  <div class="nav" role="navigation">
    <ul>
      <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
      <li><a href='/rgms/periodico/list'><g:message code="default.periodico.label"/></a></li>
	  <li><a href='/rgms/conferencia/list'><g:message code="default.conferencia.label"/></a></li>
	  <li><a href='/rgms/ferramenta/list'><g:message code="default.ferramenta.label"/></a></li>
	  <!-- #if($maisresul) -->
	   <li><a href='/rgms/dissertacao/list'><g:message code="default.dissertacao.label"/></a></li>
	   <li><a href='/rgms/tese/list'><g:message code="default.tese.label"/></a></li>
	   <li><a href='/rgms/bookChapter/list'><g:message code="default.bookchapter.label"/></a></li>
	   <li><a href='/rgms/technicalReport/list'><g:message code="default.technicalreport.label"/></a></li>
      <!-- #end -->
	  <!-- #if($Bibtex) -->
	   <li><g:link action="pdftodasPublicacoes" controller="publicacao">BibTex</g:link></li>
	  <!-- #end -->
    </ul>
  </div>
  <div id="list-publication" class="content scaffold-list" role="main">
    <h1><g:message code="default.list.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
      <div class="message" role="status">${flash.message}</div>
    </g:if>
    <table>
      <thead>
        <tr>

      <g:sortableColumn property="title" title="${message(code: 'publication.title.label', default: 'Title')}" />

      <g:sortableColumn property="publicationDate" title="${message(code: 'publication.publicationDate.label', default: 'Publication Date')}" />

      <th><g:message code="publication.researchLine.label" default="Research Line" /></th>

      </tr>
      </thead>
      <tbody>
      <g:each in="${publicationInstanceList}" status="i" var="publicationInstance">
        <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

          <td><g:link action="show" id="${publicationInstance.id}">${fieldValue(bean: publicationInstance, field: "title")}</g:link></td>

        <td><g:formatDate date="${publicationInstance.publicationDate}" /></td>

        <td>${fieldValue(bean: publicationInstance, field: "researchLine")}</td>

        </tr>
      </g:each>
      </tbody>
    </table>
    <div class="pagination">
      <g:paginate total="${Publication.count()}" />
    </div>
  </div>
</body>
</html>
