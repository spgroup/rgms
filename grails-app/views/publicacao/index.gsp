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
			</ul>
		</div>	
		<div class="content">
			<h1>RGMS</h1>
			<br>
			<p>Que tipo de publicação deseja cadastrar?</p> <br>
			<a href='/rgms/periodico/list'> Periodico </a> <br> 
			<a href='/rgms/conferencia/list'> Conferencia </a> <br> 
			<a href='/rgms/ferramenta/list'> Ferramenta </a>
		</div>
		<div class="content">
			<h1>Listagem, busca, edição e remoção de publicações</h1>
		</div>
	</body>
</html>
