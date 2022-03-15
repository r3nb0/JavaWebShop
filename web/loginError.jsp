<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Little Shop - Login error</title>
    </head>
    <body>
        <section id="header">
            <jsp:include page="./components/header.jsp"/>
        </section>

        <section id="content">
            <div class="row">
                <div class="col-12 text-center lead">
                    <div class="d-flex justify-content-center items-align-center divider">
                        <h3 class="my-auto">Seems like you are not logged in!<hr style="color: var(--dark-blue);"/>Please login or register a new account.<hr style="color: var(--dark-blue); border-bottom: 1px var(--dark-blue) ridge;"/></h3>
                    </div>
                </div>
            </div>

            <div class="row justify-content-around border-bottom">
                <div class="col-xs-12 col-md-6 p-4 m-4">
                    <jsp:include page="./components/loginForm.jsp"/>
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
