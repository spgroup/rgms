<%@page import="rgms.member.Member"%>
<%@ page import="rgms.publication.Tese" %>

<div class="fieldcontain">
	<label for="title">
		<g:message code="tese.title.label" default="Title" />
	</label>
	<g:textField name="title" value="${teseInstance?.title}"/>
</div>

<div class="fieldcontain">
	<label for="publicationDate">
		<g:message code="tese.publicationInitalDate.label" default="Initial Date" />
	</label>
	<g:datePicker name="publicationInitialDate" precision="day"  value="${teseInstance?.publicationDate}"  />
</div>

<div class="fieldcontain">
    <label for="publicationDate">
        <g:message code="tese.publicationEndDate.label" default="End Date" />
    </label>
    <g:datePicker name="publicationEndDate" precision="day"  value="${teseInstance?.publicationDate}"  />
</div>

<div class="fieldcontain">
	<label for="school">
		<g:message code="tese.school.label" default="School" />
	</label>
	<g:textField name="school" value="${teseInstance?.school}"/>
</div>

