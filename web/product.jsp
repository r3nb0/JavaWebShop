<%-- 
    Document   : products
    Created on : Jan 7, 2022, 2:04:51 PM
    Author     : r3nb0
--%>

<%@page import="entities.Product"%>
<%@page import="java.util.Collection"%>
<%@page import="entities.ProductCategory"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Products</title>
    </head>
    <body class="teal-bg">
        <% 
            Product product = (Product) session.getAttribute("product");
            if (product == null) {
            response.sendRedirect("home.jsp");
            } else {
        %>


        <%
        }
        %>
        <section id="header">
            <jsp:include page="components/header.jsp"/>
        </section>
        <section id="content">
            <div class="bg-light">
                <div class="overflow-hidden text-center dark-blue-bg" style="background-image: url('https://random.imagecdn.app/1920/1080');">
                    <div class="container">
                        <div class="container justify-content-center items-align-center my-5 py-auto">
                            <div class="text-center carousel-item-card shadow p-4 m-auto dark teal-bg">
                                <h1 class="display-4 fw-normal"><%= product.getName() %></h1>
                                <p class="lead fw-normal"><%= product.getDescription() %></p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row shadow-lg">
                    <div class="col-xs-12 col-md-6 d-none d-md-block overflow-hidden" style="height: 50vh;">
                        <img src="https://random.imagecdn.app/1000/760" alt="Random image from unspash!"/>
                    </div>
                    <div style="height: 50vh;" 
                         class="col-xs-12 col-md-6 px-2 m-auto text-center">
                        <h2 class="text-capitalize text-monospace mt-5 pt-4">
                            <%= product.getDescription() %></h2>
                        <hr/>
                    </div>
                    <div style="height: 50vh;" 
                         class="col-xs-12 col-md-6 px-2 m-auto text-center">
                        <h2 class="text-capitalize text-monospace mt-5 pt-4">
                            In category: <b><%= product.getProductCategoryId().getName() %></b>
                        </h2>
                        <hr/>
                    </div>
                    <div class="col-xs-12 col-md-6 d-none d-md-block overflow-hidden" style="height: 50vh;">
                        <img src="https://random.imagecdn.app/1000/763" alt="Random image from unspash!"/>
                    </div>
                </div>
                <div class="row teal-bg">
                    <div id="scrollTo" class="card my-4 mx-auto p-2 w-50">
                        <div class="card-header">
                            <h4>To add one <b><%= product.getName() %></b> just click on the button. <br/> If you want more, enter the amount and click add to cart! </h4>
                            <small class="text-muted">Some simple things are just effective!</small>
                        </div>
                        <div class="card-body text-center">

                            <form action="cart" method="POST" class="m-0 p-2">
                                <input type="hidden" name="productId"
                                       value="<%= product.getId() %>"/>
                                <div class="input-group">
                                    <input class="form-control input-group-prepend"
                                           type="number" min="1" max="999" 
                                           name="amount" value="1" placeholder="1"/>
                                    <button class="btn btn-warning" type="submit" value="add" name="action">Add to cart</button>

                                </div>
                            </form>
                            <h4>Price of ONE <b><%= product.getName() %></b> is <%= product.getPrice().setScale(2).toString() %> €</h4>

                        </div>
                        <div class="card-footer">
                            <small>All prices include VAT (tax 25%)</small>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </section>

    <section id="footer">
        <jsp:include page="components/footer.jsp"/>
    </section>

</body>
</html>
