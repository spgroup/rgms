<!doctype html>

<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Research Group Management System</title>
	</head>
	<body>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" controller="member" action="index"><g:message code="default.member.label"/></g:link></li>
				<li><g:link class="list" controller="publication" action="index"><g:message code="default.publication.label"/></g:link></li>
			</ul>
		</div>	
		<div class="content">
			<h1>RGMS</h1>
			<p><g:message code="default.MSGIni.label"/></p>
		</div>
	</body>
</html>
