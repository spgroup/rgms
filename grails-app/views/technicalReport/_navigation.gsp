		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
				<%--#if($Report)--%>		
			    <li><g:link class="list" action="report"><g:message code="default.report.label" args="[entityName]" /></g:link></li>
				<%--#end--%>
			</ul>
		</div>