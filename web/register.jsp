<%-- 
    Document   : Register
    Created on : Dec 30, 2021, 11:17:38 PM
    Author     : r3nb0
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register</title>
    </head>
    <body>
        <section id="header">
            <jsp:include page="./components/header.jsp"/>
        </section>

        <section id="content" class="teal-bg">
            <div class="row">
                <div class="col-12 text-center lead">
                    <div class="d-flex justify-content-center items-align-center divider">
                        <h3 class="my-auto dark-blue">You don't have an account?<hr style="color: var(--highlight);"/>Create your account now. Simple, easy and commitment free!<hr style="color: var(--highlight); border-bottom: 1px var(--highlight) ridge;"/></h3>
                    </div>
                </div>
            </div>


            <div class="row justify-content-around border-bottom">
                <div class="col-xs-12 col-md-6 p-0 mx-auto my-4 text-center">
                    <jsp:include page="./components/registerForm.jsp"/>
                </div>
            </div>

            <!-- 
             <div class="row justify-content-around border-bottom">
                 <div class="col-xs-12 col-md-6 p-4 m-4">
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
                                     <input type="password" class="form-control" name="password" id="registerPassword"/>
                                     <div class="input-group-append">
                                         <span class="input-group-text icon" style="cursor: pointer;" 
                                               onclick="showHideRegisterPassword()" id="registerToggle">Show</span>
                                     </div>
                                 </div>
                                 <div id="registerPasswordHelp" class="form-text">~Password must contain at least 6 characters</div>
 
                                 <button id="registerButton" type="submit" class="btn btn-warning">Register</button>
                             </div>
                         </div>
                     </form>
                 </div>
             </div>
            -->
        </section>

        <section id="footer">
            <jsp:include page="./components/footer.jsp"/>
        </section>

    </body>
</html>
