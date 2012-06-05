



<div class="fieldcontain ${hasErrors(bean: recordInstance, field: 'start', 'error')} required">
	<label for="start">
		<g:message code="record.start.label" default="Start" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="start" precision="day"  value="${recordInstance?.start}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: recordInstance, field: 'end', 'error')} required">
	<label for="end">
		<g:message code="record.end.label" default="End" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="end" precision="day"  value="${recordInstance?.end}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: recordInstance, field: 'status_H', 'error')} required">
	<label for="status_H">
		<g:message code="record.status_H.label" default="Status H" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="status_H" required="" value="${recordInstance?.status_H}"/>
</div>

