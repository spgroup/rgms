<div id="members_id" class="fieldcontain ${hasErrors(bean: researchGroupInstance, field: 'members', 'error')} ">
	<label for="members">
		<g:message code="researchGroup.members.label" default="Members" />
		
	</label>
	<g:select name="members" from="${membersInstance}" multiple="multiple" optionKey="id" size="5" value="${membersInstance*.id}" class="many-to-many"/>
</div>