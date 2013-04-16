<%@ page import="rgms.visit.Visit" %>



<div class="fieldcontain ${hasErrors(bean: visitInstance, field: 'dataInicio', 'error')} required">
	<label for="dataInicio">
		<g:message code="visit.dataInicio.label" default="Data Inicio" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="dataInicio" precision="day"  value="${visitInstance?.dataInicio}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: visitInstance, field: 'dataFim', 'error')} required">
	<label for="dataFim">
		<g:message code="visit.dataFim.label" default="Data Fim" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="dataFim" precision="day"  value="${visitInstance?.dataFim}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: visitInstance, field: 'visitor', 'error')} required">
	<label for="visitor">
		<g:message code="visit.visitor.label" default="Visitor" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField id="visitor" name="name" from="${rgms.visit.Visitor.list()}" optionKey="id" required="" value="${visitInstance?.visitor?.id}" class="many-to-one"/>
</div>

