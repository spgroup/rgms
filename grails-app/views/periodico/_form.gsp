<%@ page import="rgms.Periodico" %>

<div class="fieldcontain ${hasErrors(bean: periodicoInstance, field: 'title', 'error')} ">
	<label for="title">
		<g:message code="periodico.title.label" default="Title" />
		
	</label>
	<g:textField name="title" value="${periodicoInstance?.title}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: periodicoInstance, field: 'author', 'error')} ">
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

<div class="fieldcontain ${hasErrors(bean: periodicoInstance, field: 'journal', 'error')} ">
	<label for="journal">
		<g:message code="periodico.journal.label" default="Journal" />
		
	</label>
	<g:textField name="journal" value="${periodicoInstance?.journal}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: periodicoInstance, field: 'number', 'error')} required">
	<label for="number">
		<g:message code="periodico.number.label" default="Number" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="number" required="" value="${fieldValue(bean: periodicoInstance, field: 'number')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: periodicoInstance, field: 'pageInitial', 'error')} required">
	<label for="pageInitial">
		<g:message code="periodico.pageInitial.label" default="PageInitial" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="pageInitial" required="" value="${fieldValue(bean: periodicoInstance, field: 'pageInitial')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: periodicoInstance, field: 'pageFinal', 'error')} required">
	<label for="pageFinal">
		<g:message code="periodico.pageFinal.label" default="PageFinal" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="pageFinal" required="" value="${fieldValue(bean: periodicoInstance, field: 'pageFinal')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: periodicoInstance, field: 'volume', 'error')} required">
	<label for="volume">
		<g:message code="periodico.volume.label" default="Volume" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="volume" required="" value="${fieldValue(bean: periodicoInstance, field: 'volume')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: periodicoInstance, field: 'year', 'error')} required">
	<label for="year">
		<g:message code="periodico.year.label" default="Year" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="year" required="" value="${fieldValue(bean: periodicoInstance, field: 'year')}"/>
</div>

