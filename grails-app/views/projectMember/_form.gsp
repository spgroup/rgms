<%@ page import="rgms.researchProject.ProjectMember" %>



<div class="fieldcontain ${hasErrors(bean: projectMemberInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="projectMember.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" maxlength="100" required="" value="${projectMemberInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: projectMemberInstance, field: 'responsable', 'error')} ">
	<label for="responsable">
		<g:message code="projectMember.responsable.label" default="Responsable" />
		
	</label>
	<g:checkBox name="responsable" value="${projectMemberInstance?.responsable}" />
</div>

<div class="fieldcontain ${hasErrors(bean: projectMemberInstance, field: 'cnpqId', 'error')} required">
	<label for="cnpqId">
		<g:message code="projectMember.cnpqId.label" default="Cnpq Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="cnpqId" type="number" value="${projectMemberInstance.cnpqId}" required=""/>
</div>

