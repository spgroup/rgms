<%@ page import="rgms.publication.Tese" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'tese.label', default: 'Tese')}" />
		<title><g:message code="default.search.label" args="[entityName]" /></title>
        <script type="text/javascript" src="/rgms/js/jquery-ui-1.8.18.custom.min.js"></script>
        <script>
            $(function() {
                var searches = <%='['+((java.lang.String)session.getAttribute("previous_search_string")).toString()+']'%>;
                $( "#title" ).autocomplete({
                    source: searches,
                    autoFocus: true
                });
            });
        </script>
	</head>
	<body>
		<a href="#search-tese" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="search-tese" class="content scaffold-create" role="main">
			<h1><g:message code="default.search.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${teseInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${teseInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form action="searchList" >
				<fieldset class="form">
					<g:render template="search"/>
				</fieldset>
				<fieldset class="buttons">
					<g:submitButton name="search" class="search" value="${message(code: 'default.button.search.label', default: 'Search')}" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
