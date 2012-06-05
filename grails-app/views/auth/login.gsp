
<!doctype html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta name="layout" content="main" />
	<g:set var="title" value="${message(code: 'login', default: 'Login')}" />
	<title>${title}</title>
</head>
<body>
<div id="login" class="content" role="main">
	<h1>${title}</h1>
	<g:if test="${flash.message}">
	<div class="message" role="status">${flash.message}</div>
	</g:if>
  <g:form action="signIn">
	<fieldset class="form" style="text-align:center;">
			<input type="hidden" name="targetUri" value="${targetUri}" />
			<div class="required">
				<label for="username">
					<g:message code="member.username.label" default="Username" />
					<span class="required-indicator">*</span>
				</label>
				<g:textField name="username" required="" value="${username}"/>
			</div>
			<div class="required"><br/>
				<label for="password">
					<g:message code="member.password.label" default="Password" />
					<span class="required-indicator">*</span>
				</label>
				<g:passwordField name="password" required="" value=""/>
			</div>
			<div><br/>
				<g:checkBox name="rememberMe" value="${rememberMe}"/>
				<label for="rememberMe" style="font-size:smaller;">
					<g:message code="member.rememberMe.label" default="Remember me?" />
				</label>
				<br/>
			</div>
			<div><br/>
				<g:submitButton name="signIn" value="${message(code: 'default.button.signIn.label', default: 'Sign In')}" />
				<br/><br/><br/><g:link action="lostPassword"><g:message code="default.lostPassword.label" default="I have lost my password" /></g:link>
				
			</div>
	</fieldset>
  </g:form>
  </div>
</body>
</html>
