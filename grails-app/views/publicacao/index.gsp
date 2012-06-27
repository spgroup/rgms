<%@ page import="rgms.Conferencia" %>
<%@ page import="rgms.Periodico" %>
<%@ page import="rgms.Ferramenta" %>
#if($maisresul)
<%@ page import="rgms.Dissertacao" %>
<%@ page import="rgms.Tese" %>
#end
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Research Group Management System</title>
	</head>
	<body>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${ createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><a href='/rgms/periodico/list'><g:message code="default.periodico.label"/></a></li>
				<li><a href='/rgms/conferencia/list'><g:message code="default.conferencia.label"/></a></li>
				<li><a href='/rgms/ferramenta/list'><g:message code="default.ferramenta.label"/></a></li>
				#if($maisresul)
				<li><a href='/rgms/dissertacao/list'><g:message code="default.dissertacao.label"/></a></li>
				<li><a href='/rgms/tese/list'><g:message code="default.tese.label"/></a></li>
				#end
				#if($bibtex)
				<li><g:link action="pdftodasPublicacoes" controller="publicacao">BibTex</g:link></li>
				#end
			</ul>
		</div>	
		<div class="content">
			<h1><g:message code="default.msgBusca.label"/></h1>
		</div>

		<g:form>
			<div>
				<h1 style="float:left; margin-left:20px"><g:message code="default.search.label"/>: </h1>
				<g:textField style="margin: 12px" name="busca" type="text" size='55' id="search"/>
			</div>
		</g:form>
		</br>
		<table>
				<thead>
					<tr>
						<g:sortableColumn property="title" title="${ message(code: 'default.title.label', default: 'Title')}" />
					
						<g:sortableColumn property="author" title="${ message(code: 'default.author.label', default: 'Author')}" />
					
						<g:sortableColumn property="year" title="${ message(code: 'default.year.label', default: 'Year')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${ item}" status="i" var="oneitem">
					<tr class="${ (i % 2) == 0 ? 'even' : 'odd'}">
						
						
						<% Conferencia conf = new Conferencia() %>
						<% Periodico per = new Periodico() %>
						<% Ferramenta fer = new Ferramenta() %>
						#if($maisresul)
						<% Dissertacao diss = new Dissertacao() %>
						<% Tese tese = new Tese() %>
						#end
					
						<g:if test = "${ oneitem.class == conf.class}">
							<td><g:link action="show" controller="conferencia" id="${ oneitem.id}">${ oneitem.title}</g:link></td>
						</g:if>
						<g:if test = "${ oneitem.class == per.class}">
							<td><g:link action="show" controller="periodico" id="${ oneitem.id}">${ oneitem.title}</g:link></td>
						</g:if>
						<g:if test = "${ oneitem.class == fer.class}">
							<td><g:link action="show" controller="ferramenta" id="${ oneitem.id}">${ oneitem.title}</g:link></td>
						</g:if>
						#if($maisresul)
						<g:if test = "${ oneitem.class == diss.class}">
							<td><g:link action="show" controller="dissertacao" id="${ oneitem.id}">${ oneitem.title}</g:link></td>
						</g:if>
						<g:if test = "${ oneitem.class == tese.class}">
							<td><g:link action="show" controller="tese" id="${ oneitem.id}">${ oneitem.title}</g:link></td>
						</g:if>
						#end
						<td>${ oneitem.author}</td>
					
						<td>${ oneitem.year}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
	</body>
</html>
