<%@ page import="rgms.Ferramenta" %>



<div class="fieldcontain ${hasErrors(bean: ferramentaInstance, field: 'author', 'error')} required">
	<label for="author">
		<g:message code="ferramenta.author.label" default="Author" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="author" required="" value="${ferramentaInstance?.author}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: ferramentaInstance, field: 'title', 'error')} required">
	<label for="title">
		<g:message code="ferramenta.title.label" default="Title" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="title" required="" value="${ferramentaInstance?.title}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: ferramentaInstance, field: 'year', 'error')} required">
	<label for="year">
		<g:message code="ferramenta.year.label" default="Year" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="year" required="" value="${ferramentaInstance?.year}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: ferramentaInstance, field: 'link', 'error')} required">
	<label for="link">
		<g:message code="ferramenta.link.label" default="Link" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="link" required="" value="${ferramentaInstance?.link}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: ferramentaInstance, field: 'descricao', 'error')} ">
	<label for="descricao">
		<g:message code="ferramenta.descricao.label" default="Descricao" />
		
	</label>
	<g:textField name="descricao" value="${ferramentaInstance?.descricao}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: ferramentaInstance, field: 'publicacaoAssociada', 'error')} ">
	<label for="publicacaoAssociada">
		<g:message code="ferramenta.publicacaoAssociada.label" default="Publicacao Associada" />
		
	</label>
	<g:textField name="publicacaoAssociada" value="${ferramentaInstance?.publicacaoAssociada}"/>
</div>

