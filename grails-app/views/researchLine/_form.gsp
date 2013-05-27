<%@ page import="rgms.publication.ResearchLine" %>



<div class="fieldcontain ${hasErrors(bean: researchLineInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="researchLine.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${researchLineInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: researchLineInstance, field: 'description', 'error')} required">
	<label for="description">
		<g:message code="researchLine.description.label" default="Description" />
		<span class="required-indicator">*</span>
	</label>
	<g:textArea name="description" cols="40" rows="5" maxlength="256" required="" value="${researchLineInstance?.description}"/>
</div>


<div class="fieldcontain ${hasErrors(bean: researchLineInstance, field: 'publications', 'error')} ">
  <label for="publications">
    <g:message code="researchLine.publications.label" default="Publications" />

  </label>
  <g:select name="publications" from="${rgms.publication.Publication.list()}" multiple="multiple" optionKey="id" size="5" value="${researchLineInstance?.publications*.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: researchLineInstance, field: 'members', 'error')} ">
  <label for="members">
    <g:message code="researchLine.members.label" default="Members" />

  </label>
  <g:select name="members" from="${rgms.member.Member.list()}" multiple="multiple" optionKey="id" size="5" value="${researchLineInstance?.members*.id}" class="many-to-one"/>
</div>

