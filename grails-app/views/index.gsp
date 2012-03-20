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
		<style>
			#sortable { list-style-type: none; margin: 0; padding: 0; width: 60%; }
			#sortable li { margin: 0 3px 3px 3px; padding: 0.4em; padding-left: 1.5em; font-size: 1.4em; height: 18px; }
			#sortable li span { position: absolute; margin-left: -1.3em; }
		</style>
			<script>
				$(function() {
					$( "#sortable" ).sortable();
					$( "#sortable" ).disableSelection();
				});
			</script>


		<div class="demo">

		<ul id="sortable">
			<li class="ui-state-default"><span class="ui-icon ui-icon-arrowthick-2-n-s"></span>Item 1</li>
			<li class="ui-state-default"><span class="ui-icon ui-icon-arrowthick-2-n-s"></span>Item 2</li>
			<li class="ui-state-default"><span class="ui-icon ui-icon-arrowthick-2-n-s"></span>Item 3</li>
			<li class="ui-state-default"><span class="ui-icon ui-icon-arrowthick-2-n-s"></span>Item 4</li>
			<li class="ui-state-default"><span class="ui-icon ui-icon-arrowthick-2-n-s"></span>Item 5</li>
			<li class="ui-state-default"><span class="ui-icon ui-icon-arrowthick-2-n-s"></span>Item 6</li>
			<li class="ui-state-default"><span class="ui-icon ui-icon-arrowthick-2-n-s"></span>Item 7</li>
		</ul>

		</div><!-- End demo -->



		<div class="demo-description" style="display: none; ">
		<p>
			Enable a group of DOM elements to be sortable. Click on and drag an
			element to a new spot within the list, and the other items will adjust to
			fit. By default, sortable items share <code>draggable</code> properties.
		</p>
		</div><!-- End demo-description -->
	</body>
</html>
