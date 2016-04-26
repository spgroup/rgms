<g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
</g:if>
<g:hasErrors bean="${bookInstance}">
    <ul class="errors" role="alert">
        <g:eachError bean="${bookInstance}" var="error">
            <li <g:if test="${error in org.springframework.validation.ObjectError}">data-field-id="${error.}"</g:if>><g:message error="${error}"/></li>
        </g:eachError>
    </ul>
</g:hasErrors>
