<!-- #if($researchProject) -->
<%@ page import="rgms.researchProject.ResearchProject" %>



<div class="fieldcontain ${hasErrors(bean: researchProjectInstance, field: 'projectName', 'error')}">
	<label for="projectName">
		<g:message code="researchProject.projectName.label" default="Project Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textArea name="projectName" cols="40" rows="5" maxlength="300" value="${researchProjectInstance?.projectName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: researchProjectInstance, field: 'description', 'error')}">
	<label for="description">
		<g:message code="researchProject.description.label" default="Description" />
		<span class="required-indicator">*</span>
	</label>
	<g:textArea name="description" cols="40" rows="5" maxlength="3000" value="${researchProjectInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: researchProjectInstance, field: 'status', 'error')} required">
	<label for="status">
		<g:message code="researchProject.status.label" default="Status" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="status" from="${researchProjectInstance.constraints.status.inList}" required="" value="${researchProjectInstance?.status}" valueMessagePrefix="researchProject.status"/>
</div>

<div class="fieldcontain ${hasErrors(bean: researchProjectInstance, field: 'startYear', 'error')} required">
	<label for="startYear">
		<g:message code="researchProject.startYear.label" default="Start Year" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="startYear" type="number" value="${researchProjectInstance.startYear}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: researchProjectInstance, field: 'endYear', 'error')} required">
	<label for="endYear">
		<g:message code="researchProject.endYear.label" default="End Year" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="endYear" type="number" value="${researchProjectInstance.endYear}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: researchProjectInstance, field: 'responsible', 'error')} ">
	<label for="responsible">
		<g:message code="researchProject.responsible.label" default="Responsible" />
		
	</label>
	<g:textArea name="responsible" cols="40" rows="5" maxlength="300" value="${researchProjectInstance?.responsible}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: researchProjectInstance, field: 'funders', 'error')} ">
	<label for="funders">
		<g:message code="researchProject.funders.label" default="Funders" />
		
	</label>
	<g:select name="funders" from="${rgms.researchProject.Funder.list()}" multiple="multiple" optionKey="id" size="5" value="${researchProjectInstance?.funders*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: researchProjectInstance, field: 'members', 'error')} " id="memberList">
	<label for="members">
		<g:message code="researchProject.members.label" default="Members" />
		
	</label>
    <g:textField name="members" id="members" value="${researchProjectInstance?.members}"/>
</div>
<!-- #end -->