<%@ page import="rgms.researchProject.Funder" %>



<div class="fieldcontain ${hasErrors(bean: funderInstance, field: 'code', 'error')} required">
	<label for="code">
		<g:message code="funder.code.label" default="Code" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="code" type="number" value="${funderInstance.code}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: funderInstance, field: 'nature', 'error')} required">
	<label for="nature">
		<g:message code="funder.nature.label" default="Nature" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="nature" from="${funderInstance.constraints.nature.inList}" required="" value="${funderInstance?.nature}" valueMessagePrefix="funder.nature"/>
</div>

<div class="fieldcontain ${hasErrors(bean: funderInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="funder.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" maxlength="100" required="" value="${funderInstance?.name}"/>
</div>

