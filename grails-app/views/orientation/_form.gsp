<%@ page import="rgms.member.Orientation" %>



<div class="fieldcontain ${hasErrors(bean: orientationInstance, field: 'tipo', 'error')} required">
	<label for="tipo">
		<g:message code="orientation.tipo.label" default="Tipo" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="tipo" from="${orientationInstance.constraints.tipo.inList}" required="" value="${orientationInstance?.tipo}" valueMessagePrefix="orientation.tipo"/>
</div>

<div class="fieldcontain ${hasErrors(bean: orientationInstance, field: 'orientando', 'error')} required">
	<label for="orientando">
		<g:message code="orientation.orientando.label" default="Orientando" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="orientando" name="orientando.id" from="${rgms.member.Member.list()}" optionKey="id" required="" value="${orientationInstance?.orientando?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: orientationInstance, field: 'orientador', 'error')} required">
	<label for="orientador">
		<g:message code="orientation.orientador.label" default="Orientador" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="orientador" name="orientador.id" from="${rgms.member.Member.list()}" optionKey="id" required="" value="${orientationInstance?.orientador?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: orientationInstance, field: 'periodico', 'error')} required">
	<label for="periodico">
		<g:message code="orientation.periodico.label" default="Periodico" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="periodico" name="periodico.id" from="${rgms.publication.Periodico.list()}" optionKey="id" required="" value="${orientationInstance?.periodico?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: orientationInstance, field: 'descricao', 'error')} ">
	<label for="descricao">
		<g:message code="orientation.descricao.label" default="Descricao" />
		
	</label>
	<g:textField name="descricao" value="${orientationInstance?.descricao}"/>
</div>

