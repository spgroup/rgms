
<html>
<head>
<meta name="layout" content="main"/>
<g:set var="title" value="${message(code: 'user.register.title', default: 'RGMS')}" />
<title>${title}</title>
<!--<link rel="shortcut icon" href="/images/icon.ico" type="image/x-icon" />-->
<style type="text/css" media="screen">
#status {
  background-color: #eee;
  border: .2em solid #fff;
  margin: 2em 2em 1em;
  padding: 1em;
  //  width: 12em; 12em
  width: 15em;
  float: left;
  -moz-box-shadow: 0px 0px 1.25em #ccc;
  -webkit-box-shadow: 0px 0px 1.25em #ccc;
  box-shadow: 0px 0px 1.25em #ccc;
  -moz-border-radius: 0.6em;
  -webkit-border-radius: 0.6em;
  border-radius: 0.6em;
}

.ie6 #status {
  display: inline; /* float double margin fix http://www.positioniseverything.net/explorer/doubled-margin.html */
}

#status ul {
  font-size: 0.9em;
  list-style-type: none;
  margin-bottom: 0.6em;
  padding: 0;
}

#status li {
  line-height: 1.3;
}

#status h1 {
  text-transform: uppercase;
  font-size: 1.1em;
  margin: 0 0 0.3em;
}

#page-body {
  margin: 2em 1em 1.25em 18em;
}

h2 {
  margin-top: 1em;
  margin-bottom: 0.3em;
  font-size: 1em;
}

p {
  line-height: 1.5;
  margin: 0.25em 0;
}

#controller-list ul {
  list-style-position: inside;
}

#controller-list li {
  line-height: 1.3;
  list-style-position: inside;
  margin: 0.25em 0;
}

@media screen and (max-width: 480px) {
  #status {
    display: none;
    }
#page-body {
margin: 0 1em 1em;
}
#page-body h1 {
margin-top: 0;
}
}
</style>
</head>
<body>
  <div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
  </div>
  <div class="body">
    <!--    <h1><g:message code="default.create.label" args="[entityName]" /></h1>
        <g:if test="{flash.message}">
          <div class="message">{flash.message}</div>
        </g:if>
        <g:hasErrors bean="{memberInstance}">
          <div class="errors">
            <g:renderErrors bean="{memberInstance}" as="list" />
          </div>
        </g:hasErrors>-->
    <!--    <g:form action="save" >-->
      <g:hasErrors bean="${memberInstance}">
          <div class="errors">
              <g:renderErrors bean="${memberInstance}" as="list" />
          </div>
      </g:hasErrors>
    <div id="status" style="position: relative; margin-left: 25%; margin-right: 80%;">
      <h1 style="font-size: 20px; font-weight: bold; font-family: verdana;">
        Register
      </h1>
      <br />
      <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
      </g:if>

      <form id="log" action="register" method="post" >
        <input type="hidden" name="targetUri" value="" />

        <table>
          <tbody>
            <tr>
              <td style="text-align: right; font-size: 14px;">Name: </td>
              <td><input type="text" name="name" required="true" value="${memberInstance?.name}" style="font-size: 12px; font-weight: bold; border: solid 1px #d0d0d0; height: 23px; width: 250px;"/></td>
            </tr>
            <tr>
              <td style="text-align: right; font-size: 14px;">Username: </td>
              <td><input type="text" name="username" required="true" value="${memberInstance?.username}" style="font-size: 12px; font-weight: bold; border: solid 1px #d0d0d0; height: 23px; width: 250px;"/></td>
            </tr>
            <tr>
              <td style="text-align: right; font-size: 14px;">Password: </td>
              <td><input type="password" name="password1" required="true" value="" style="font-size: 12px; font-weight: bold; border: solid 1px #d0d0d0; height: 23px; width: 250px;"/></td>
            </tr>
            <tr>
              <td style="text-align: right; font-size: 14px;">Repeat Password: </td>
              <td><input type="password" name="password2" required="true" value="" style="font-size: 12px; font-weight: bold; border: solid 1px #d0d0d0; height: 23px; width: 250px;"/></td>
            </tr>
            <tr>
              <td style="text-align: right; font-size: 14px;">Email: </td>
              <td><input type="text" name="email" required="true" value="${memberInstance?.email}" style="font-size: 12px; font-weight: bold; border: solid 1px #d0d0d0; height: 23px; width: 250px;"/></td>
            </tr>
            <tr>
              <td style="text-align: right; font-size: 14px;">University: </td>
              <td><input type="text" name="university" required="true" value="${memberInstance?.university}" style="font-size: 12px; font-weight: bold; border: solid 1px #d0d0d0; height: 23px; width: 250px;"/></td>
            </tr>
            <tr>
            </tr>  
            <!--#if($facebook)-->
            <tr>
              <td ></td>
              <td style="font-size: 14px;">Entre com o Facebook: 
                <div id="login_" style="text-align: left;">
                    <div style="text-align: left;">
                        <input id="login" style="text-align: left;" value="Login" type="button">
                    </div>
                    <div id="user-info" style="display: none;"></div>

                    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"></script>

                    <div id="fb-root"></div>

                    <script src="http://connect.facebook.net/en_US/all.js"></script>
                    
                    <script>
                          // initialize the library with the API key
                        FB.init({ appId: ${grailsApplication.config.appid},oauth: true});

                          // fetch the status on load
                        //FB.getLoginStatus(handleSessionResponse2);

                        $('#login').bind('click', function() {
                            FB.login(handleSessionResponse,{scope: 'email,publish_actions'});
                        });

                        $('#logout').bind('click', function() {
                            FB.logout(handleSessionResponse);
                        });

                        $('#disconnect').bind('click', function() {
                            FB.api({ method: 'Auth.revokeAuthorization' }, function(response) {
                                clearDisplay();
                            });
                        });

                          // no user, clear display
                        function clearDisplay() {
                            $('#user-info').hide('fast');
                        }

                          // handle a session response from any of the auth related calls
                        function handleSessionResponse(response) {
                            // if we dont have a session, just hide the user info
                            if (!(response.status=="connected")) {
                              clearDisplay();
                              return;
                            }

                            document.getElementById("access_token").value = response.authResponse.accessToken
                            document.getElementById("facebook_id").value = response.authResponse.userID
                        }
                    </script>

                </div>
              </td>
            </tr>
            <tr>
              <td style="text-align: right; font-size: 14px;">FacebookID: </td>
              <td><input type="text" id="facebook_id" name="facebook_id" value="" style="font-size: 12px; font-weight: bold; border: solid 1px #d0d0d0; height: 23px; width: 250px;"/></td>
              <td></td>
            </tr>
            <tr>
              <td style="text-align: right; font-size: 14px;">Access_Token: </td>
              <td><input type="text" id="access_token" name="access_token" value="" style="font-size: 12px; font-weight: bold; border: solid 1px #d0d0d0; height: 23px; width: 250px;"/></td>
            </tr>
            <!-- #end -->
            <!--<tr>
              <td style="text-align: right; font-size: 14px;">City: </td>
              <td><input type="text" name="city" value="" style="font-size: 12px; font-weight: bold; border: solid 1px #d0d0d0; height: 23px; width: 250px;"/></td>
            </tr>
            <tr>
              <td style="text-align: right; font-size: 14px;">Country: </td>
              <td><input type="text" name="country" value="" style="font-size: 12px; font-weight: bold; border: solid 1px #d0d0d0; height: 23px; width: 250px;"/></td>
            </tr>-->
            <tr>
              <td style="text-align: right; font-size: 14px;">Status: </td>
              <td><g:select name="status" required="true" from="${["Graduate Student", "MSc Student", "PhD Student", "Professor", "Researcher"]}" value="${memberInstance?.status}" noSelection="['':'-Choose your status-']"/></td>
          </tr>       


          <tr>
            <td></td>
            <td></td>
          </tr>
          <tr>
            <td></td>
            <td><span><input type="submit" value="Register" style="font-size: 12px; font-weight: bold; border: solid 1px #316166; background: #339933;	padding: 4px 10px 4px 10px; color: #FFFFFF; border-radius: 6px;box-shadow: 1px 1px 5px rgba(0, 0, 0, 0.2);" /></span></td> <!--#1c94cd-->
          </tr>
          </tbody>
        </table>
      </form> 
    </div>
    <!--    <div class="buttons">
          <span class="button"><g:submitButton name="register" class="save" value="{register}"/></span>
        </div>-->
    <!--    </g:form>-->
  </div>
</body>
</html>
