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