<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Login</title>
    </head>
    <body>

        <section id="header">
            <jsp:include page="./components/header.jsp"/>
        </section>

        <section id="content" class="teal-bg"> 
            <div class="row">
                <div class="col-12 text-center ">
                    <div class="d-flex justify-content-center items-align-center divider dark-blue">
                        <h3 class="my-auto font-weight-bold">
                            Seems like you are not logged in!
                            <hr style="color: var(--dark-blue);"/>
                            Please login or register a new account. 
                            <hr style="color: var(--dark-blue);"/>
                        </h3>
                    </div>
                </div>
            </div>

            <div class="row justify-content-around border-bottom">
                <div class="col-xs-12 col-md-6 p-4 m-4">
                    <jsp:include page="./components/loginForm.jsp"/>
                    <h3 style="color: red;">
                        <%= session.getAttribute("errorMessage")==(null)?"":session.getAttribute("errorMessage") %>
                        <% session.removeAttribute("errorMessage"); %>
                    </h3>
                </div>
                <div class="col-xs-12 col-md-6 p-4 m-4">
                </div>
            </div>
        </section>

        <section id="footer">
            <jsp:include page="./components/footer.jsp"/>
        </section>
    </body>
</html>
