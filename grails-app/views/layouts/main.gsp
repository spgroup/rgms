<!doctype html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>
	
		<link type="text/css" href="/rgms/css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" />	
		<script type="text/javascript" src="/rgms/js/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="/rgms/js/jquery-ui-1.8.18.custom.min.js"></script>
		<script type="text/javascript" src="../js/jquery.quicksearch.js"></script>
		<script type="text/javascript" src="../js/jquery.maskedinput-1.1.4.pack.js"></script>
		<script language="javascript" type="text/javascript">
			$(document).ready(function(){

				function verificaNumero(e) {
					  if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
					  return false;
					  }
				}
									
				$(document).ready(function() {
					$("input#num").keypress(verificaNumero);
				});					
				
				$( "#datepicker" ).datepicker();


				/* Quando o botão adicionar for clicado... */
				$('input#add').click(function(){
					/* Pega uma linha existente */
					var linha = '<td>\
								<label for="author">\
									Author\
								</label>\
									<input type="text" name="author" value="" id="author">\
							</td>';
					 //var linha = $('.repetir:first').html();

					/* Acrescenta uma nova linha */
					var qtd = $('.repetir').length;
					 $('.repetir:first').before('<tr class="repetir">' +linha + '<tr>');
					 $('.repetir:first').find('input').attr({'name': 'author', 'id': 'author'+qtd}).focus();
				});

				$('input#search').quicksearch('table tbody tr');

				$("input#year").mask("9999");
			});
					
		</script>	
	
	
	
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title><g:layoutTitle default="Grails"/></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
		<link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">
		<link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" type="text/css">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'mobile.css')}" type="text/css">
		<g:layoutHead/>
        <r:layoutResources />
	</head>
	<body>
		<div id="grailsLogo" role="banner"><a href="http://grails.org"><img src="${resource(dir: 'images', file: 'grails_logo.png')}" alt="Grails"/></a></div>
		<g:layoutBody/>
		<div class="footer" role="contentinfo"></div>
		<div id="spinner" class="spinner" style="display:none;"><g:message code="spinner.alt" default="Loading&hellip;"/></div>
		<g:javascript library="application"/>
        <r:layoutResources />
	</body>
</html>