<form method="POST" id="loginForm" class="m-0 p-0 h-100" 
      action="login"/>
<div class="card text-center bg-light" style="height: 45vh;">
    <div class="card-header card-title pull-left">
        <h3>Login</h3>
    </div>
    <div class="card-body card-text">
        <div class="mb-3">
            <div class="input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text">Email address:</span>
                </div>
                <input type="email" max="50" min="3" class="form-control" placeholder="email@mail.com"
                       id="email" name="email"/>
            </div>
            <div id="loginEmailHelp" class="form-text">~We'll never share your email with anyone else</div>
        </div>

        <div class="input-group mb-3">
            <div class="input-group-prepend">
                <span class="input-group-text">Password:</span>
            </div>
            <input autocomplete="off" autocorrect="off" autocapitalize="off" type="password" name="password" class="form-control" id="password" placeholder="******"/>
            <div class="input-group-append">
                <span class="input-group-text icon" id="loginToggle" style="cursor: pointer;" onclick="showHideLoginPassword()">Show</span>
            </div>
        </div>
        <input id="loginButton" type="submit" value="Login"
               class="btn btn-warning"/>
        <h4 style="color: red;"><%= session.getAttribute("errorMessage")!=null?session.getAttribute("errorMessage"):"" %></h4>
        <% session.removeAttribute("errorMessage"); %>
    </div>
</div>
</form>
<script>
    //<![CDATA[
    function showHideLoginPassword() {
        var el = document.getElementById('password');
        var eye = document.getElementById('loginToggle');
        if (el.type == 'text') {
            el.type = 'password';
            eye.innerHTML = "Show";
        } else {
            el.type = 'text';
            eye.innerHTML = "Hide";
        }
    }
    //]]>
</script>
