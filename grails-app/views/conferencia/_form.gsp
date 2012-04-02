<%@ page import="rgms.Conferencia" %>


<div class="fieldcontain ${hasErrors(bean: conferenciaInstance, field: 'title', 'error')} required">
	<label for="title">
		<g:message code="conferencia.title.label" default="Title" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="title" required="" value="${conferenciaInstance?.title}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: conferenciaInstance, field: 'author', 'error')}">
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
						<g:message code="conferencia.author.label" default="Author" />
		
					</label>
					<g:textField name="author" value="${conferenciaInstance?.author}"/>
				</td>
			</tr>
		</tbody>
	</table>
</div>

<div class="fieldcontain ${hasErrors(bean: conferenciaInstance, field: 'conference', 'error')} required">
	<label for="conference">
		<g:message code="conferencia.conference.label" default="Conference" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="conference" required="" value="${conferenciaInstance?.conference}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: conferenciaInstance, field: 'year', 'error')} required">
	<label for="year">
		<g:message code="conferencia.year.label" default="Year" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="year" required="" value="${fieldValue(bean: conferenciaInstance, field: 'year')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: conferenciaInstance, field: 'pageInitial', 'error')} required">
	<label for="pageInitial">
		<g:message code="conferencia.pageInitial.label" default="PageInitial" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" id="num" name="pageInitial" required="" value="${fieldValue(bean: conferenciaInstance, field: 'pageInitial')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: conferenciaInstance, field: 'pageFinal', 'error')} required">
	<label for="pageFinal">
		<g:message code="conferencia.pageFinal.label" default="PageFinal" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" id="num" name="pageFinal" required="" value="${fieldValue(bean: conferenciaInstance, field: 'pageFinal')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: conferenciaInstance, field: 'month', 'error')} required">
	<label for="month">
		<g:message code="conferencia.month.label" default="Month" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" id="num" name="month" required="" value="${fieldValue(bean: conferenciaInstance, field: 'month')}"/>
</div>

