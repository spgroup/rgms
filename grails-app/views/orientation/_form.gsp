<!-- #if($Orientation) -->
<%@ page import="rgms.member.Orientation" %>

<div class="fieldcontain ${hasErrors(bean: orientationInstance, field: 'tipo', 'error')} required">
	<label for="tipo">
		<g:message code="orientation.tipo.label" default="Tipo" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="tipo" from="${orientationInstance.constraints.tipo.inList}" required="" value="${orientationInstance?.tipo}" valueMessagePrefix="orientation.tipo"/>
</div>

<div class="fieldcontain ${hasErrors(bean: orientationInstance, field: 'orientando', 'error')} ">
	<label for="orientando">
		<g:message code="orientation.orientando.label" default="Orientando" />
		
	</label>
	<g:textField name="orientando" value="${orientationInstance?.orientando}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: orientationInstance, field: 'orientador', 'error')} required">
	<label for="orientador">
		<g:message code="orientation.orientador.label" default="Orientador" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="orientador" name="orientador.id" from="${rgms.member.Member.list()}" optionKey="id" required="" value="${orientationInstance?.orientador?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: orientationInstance, field: 'tituloTese', 'error')} required">
	<label for="tituloTese">
		<g:message code="orientation.tituloTese.label" default="Titulo Tese" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="tituloTese" required="" value="${orientationInstance?.tituloTese}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: orientationInstance, field: 'anoPublicacao', 'error')} required">
	<label for="anoPublicacao">
		<g:message code="orientation.anoPublicacao.label" default="Ano Publicacao" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="anoPublicacao" type="number" value="${orientationInstance.anoPublicacao}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: orientationInstance, field: 'instituicao', 'error')} required">
	<label for="instituicao">
		<g:message code="orientation.instituicao.label" default="Instituicao" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="instituicao" required="" value="${orientationInstance?.instituicao}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: orientationInstance, field: 'curso', 'error')} ">
	<label for="curso">
		<g:message code="orientation.curso.label" default="Curso" />
		
	</label>
	<g:textField name="curso" value="${orientationInstance?.curso}"/>
</div>
<!--#end -->
