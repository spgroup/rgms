
<!doctype html>

<html>
<head>
<meta name="layout" content="main"/>
<title>RGMS</title>
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
  <a href="#page-body" class="skip"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
  <!--  <div id="status" role="complementary">
      <h1>Application Status</h1>
      <ul>
        <li>App version: <g:meta name="app.version"/></li>
        <li>Grails version: <g:meta name="app.grails.version"/></li>
        <li>Groovy version: ${org.codehaus.groovy.runtime.InvokerHelper.getVersion()}</li>
        <li>JVM version: ${System.getProperty('java.version')}</li>
        <li>Reloading active: ${grails.util.Environment.reloadingAgentEnabled}</li>
        <li>Controllers: ${grailsApplication.controllerClasses.size()}</li>
        <li>Domains: ${grailsApplication.domainClasses.size()}</li>
        <li>Services: ${grailsApplication.serviceClasses.size()}</li>
        <li>Tag Libraries: ${grailsApplication.tagLibClasses.size()}</li>
      </ul>
      <h1>Installed Plugins</h1>
      <ul>
        <g:each var="plugin" in="${applicationContext.getBean('pluginManager').allPlugins}">
          <li>${plugin.name} - ${plugin.version}</li>
        </g:each>
      </ul>
    </div>-->
  <div style="margin-left: 2em; margin-right: 2em;">
    <h1>Welcome to Research Group Management System (RGMS)</h1>
    <p>Congratulations, you have successfully started your first Grails application! At the moment
      this is the default page, feel free to modify it to either redirect to a controller or display whatever
      content you may choose. Below is a list of controllers that are currently deployed in this application,
      click on each to execute its default action:</p>
  </div>
  <!--  <div id="page-body" role="main">-->
  <!--    <h1>Welcome to Grails</h1>
      <p>Congratulations, you have successfully started your first Grails application! At the moment
        this is the default page, feel free to modify it to either redirect to a controller or display whatever
        content you may choose. Below is a list of controllers that are currently deployed in this application,
        click on each to execute its default action:</p>-->

  <!--    <div id="controller-list" role="navigation">
        <h2>Available Controllers:</h2>
        <ul>
          <g:each var="c" in="{grailsApplication.controllerClasses.sort { it.fullName } }">
            <li class="controller"><g:link controller="{c.logicalPropertyName}">{c.fullName}</g:link></li>
          </g:each>
        </ul>
      </div>-->
  <div id="status" style="position: relative; margin-left: 25%; margin-right: 80%;">
    <h1 style="font-size: 20px; font-weight: bold; font-family: verdana;">
      Login
    </h1>
    <br />
    <g:if test="${flash.message}">
      <div class="message" role="status">${flash.message}</div>
    </g:if>
    <form id="log" action="auth/signIn" method="post" >
      <input type="hidden" name="targetUri" value="" />

      <table>
        <tbody>
          <tr>
            <td style="text-align: right; font-size: 14px;">Username: </td>
            <td><input type="text" name="username" required="true" value="" style="font-size: 12px; font-weight: bold; border: solid 1px #d0d0d0; height: 23px; width: 250px;"/></td>
          </tr>
          <tr>
            <td style="text-align: right; font-size: 14px;">Password: </td>
            <td><input type="password" name="password" required="true" value="" style="font-size: 12px; font-weight: bold; border: solid 1px #d0d0d0; height: 23px; width: 250px;"/></td>
          </tr>
          <tr>
            <td></td>
            <td></td>
          </tr>
          <tr>
            <td></td>
            <td><span><input type="submit" value="Sign In" style="font-size: 12px; font-weight: bold; border: solid 1px #316166; background: #339933;	padding: 4px 10px 4px 10px; color: #FFFFFF; border-radius: 6px;box-shadow: 1px 1px 5px rgba(0, 0, 0, 0.2);" /></span><span style="float: right;"><input id="rememberMe" type="checkbox" name="rememberMe" /> Remember me?<br/><a href="auth/lostPassword">I have lost my password</a><br/><a href="auth/register">Create an account</a></span></td> <!--#1c94cd-->
          </tr>
        </tbody>
      </table>
    </form> 
  </div>
  <!--  </div>-->
</body>
</html>
