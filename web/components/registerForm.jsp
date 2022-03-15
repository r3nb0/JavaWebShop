<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<form action="register" method="POST" id="accountRegisterForm" class="m-0 p-0">
    <div class="card text-center bg-light" style="height: 45vh;">
        <div class="card-header card-title pull-left">
            <h3>Register</h3>
        </div>
        <div class="card-body card-text">
            <div class="mb-3">
                <div class="input-group"> 
                    <div class="input-group-prepend">
                        <span class="input-group-text" id="registerEmail">Email address:</span>
                    </div>
                    <input type="text" value="" name="email" class="form-control" id="registerEmail" />
                </div>
                <div id="registerEmailHelp" class="form-text">~We'll never share your email with anyone else</div>
            </div>

            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text">Password:</span>
                </div>
                <input autocomplete="off" autocorrect="off" autocapitalize="off" type="password" class="form-control" name="password" id="registerPassword"/>
                <div class="input-group-append">
                    <span class="input-group-text icon" style="cursor: pointer;" 
                          onclick="showHideRegisterPassword()" id="registerToggle">Show</span>
                </div>
            </div>
            <div id="registerPasswordHelp" class="form-text">~Password must contain at least 6 characters</div>

            <button id="registerButton" type="submit" class="btn btn-warning">Register</button>

            <h3 style="color: red;">
                <%= session.getAttribute("errorMessage")==(null)?"":session.getAttribute("errorMessage") %>
                <% session.removeAttribute("errorMessage"); %>
            </h3>
        </div>
    </div>
</form>

<script>
    //<![CDATA[
    function showHideRegisterPassword() {
        var el = document.getElementById('registerPassword');
        var eye = document.getElementById('registerToggle');
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