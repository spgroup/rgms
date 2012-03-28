<%@ page import="rgms.Ferramenta" %>



<div class="fieldcontain ${hasErrors(bean: ferramentaInstance, field: 'author', 'error')} ">
	<table>	
		<tr>
			<td>
				<input type="button" value="Add Author" id="add" />
			</td>
		</tr>
		<tbody class='repetir'>
			<tr>
				<td>			
					<label for="author">
						<g:message code="ferramenta.author.label" default="Author" />
		
					</label>
					<g:textField name="author" value="${ferramentaInstance?.author}"/>
				</td>
			</tr>
		</tbody>
	</table>
</div>

<div class="fieldcontain ${hasErrors(bean: ferramentaInstance, field: 'descricao', 'error')} ">
	<label for="descricao">
		<g:message code="ferramenta.descricao.label" default="Descricao" />
		
	</label>
	<g:textField name="descricao" value="${ferramentaInstance?.descricao}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: ferramentaInstance, field: 'link', 'error')} ">
	<label for="link">
		<g:message code="ferramenta.link.label" default="Link" />
		
	</label>
	<g:textField name="link" value="${ferramentaInstance?.link}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: ferramentaInstance, field: 'publicacaoAssociada', 'error')} ">
	<label for="publicacaoAssociada">
		<g:message code="ferramenta.publicacaoAssociada.label" default="Publicacao Associada" />
		
	</label>
	<g:textField name="publicacaoAssociada" value="${ferramentaInstance?.publicacaoAssociada}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: ferramentaInstance, field: 'title', 'error')} ">
	<label for="title">
		<g:message code="ferramenta.title.label" default="Title" />
		
	</label>
	<g:textField name="title" value="${ferramentaInstance?.title}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: ferramentaInstance, field: 'year', 'error')} required">
	<label for="year">
		<g:message code="ferramenta.year.label" default="Year" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="year" required="" value="${fieldValue(bean: ferramentaInstance, field: 'year')}"/>
</div>

