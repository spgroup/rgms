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
				<li><g:link class="list" controller="member" action="list">Members</g:link></li>
				<li><g:link class="list" controller="publicacao" action="">Publicação</g:link></li>
			</ul>
		</div>	
		<div class="content">
			<h1>RGMS</h1>
			<p>Escolha uma opção do menu acima</p>
		</div>
	</body>
</html>
