<%@page import="java.util.Collections"%>
<%@page import="java.util.stream.Collector"%>
<%@page import="services.IpService"%>
<%@page import="models.UserModel"%>
<%@page import="java.util.stream.Collectors"%>
<%@page import="java.util.ArrayList"%>
<%@page import="bl.Manager"%>
<%@page import="bl.IManage"%>
<%@page import="entities.ProductCategory"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.List"%>
<%@page import="entities.Product"%>
<%@ page import="bl.*, entities.*, services.*, models.*" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" 
      xmlns="http://www.w3.org/1999/xhtml"
      xmlns:h="http://xmlns.jcp.org/jsf/html">
    <head>
        <title>Homepage</title>
    </head>
    <body>
        
        <%
            Manager manager = new Manager(IpService.getClientIpAddr(request));
            
            List<Product> products = new ArrayList<>(manager.getProducts());
            List<ProductCategory> categories = new ArrayList<>(manager.getCategories());
            boolean firstProduct = true;
        %>
        
        <section id="header">
            <jsp:include page="./components/header.jsp"/>
        </section>

        <section id="content" class="mt-5">
            
            <div id="myCarousel" style="height: 75vh;" class="carousel slide m-0 p-0" data-ride="carousel">
                <ol class="carousel-indicators">
                    <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                    <li data-target="#myCarousel" data-slide-to="1"></li>
                    <li data-target="#myCarousel" data-slide-to="2"></li>
                </ol>
                <div class="carousel-inner" style="max-height: 75vh;">
                    <%
                        if (products != null) {
                        int max = products.size();
                        if (products.size() > 6) {
                            max = 6;
                        } else {
                            max = products.size();
                        }
                            for (Product product : new ArrayList<Product>(products.subList(0, max))) {
                    %>
                    <div class="carousel-item <%= firstProduct?"active":"" %>" style="height: 75vh;">
                        <img class="img-fluid w-100 bd-placeholder-img" src="<%= product.getImage()%>"/>
                        <div class="container">
                            <div class="carousel-caption text-center carousel-item-card shadow">
                                <h1 class="dark"><%= product.getName() %></h1>
                                <p class="dark"><%= product.getDescription()%></p>
                                <p class="dark"><a class="btn btn-lg btn-dark" href="/product?id=<%= product.getId() %>">Buy now</a></p>
                            </div>
                        </div>
                    </div>
                    <%
                        firstProduct = false;
                        }
                    }
                    %>

                </div>
                <button class="carousel-control-prev" type="button" data-target="#myCarousel" data-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                </button>
                <button class="carousel-control-next" type="button" data-target="#myCarousel" data-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                </button>
            </div>



            <div class="row w-100 m-0 px-2 py-4 text-center dark-blue-bg" style="line-height: 2em; letter-spacing: 0.5em;">
                <h1 class="col-12 yellow text-uppercase">
                    <strong>Different categories</strong>
                    <br/>for<br/>
                    <strong>different buyers</strong>
                    <hr class="yellow" style="border-bottom: 2px; border-color: var(--dark-blue);"/></h1>
            </div>

            <section id="categoryList">
                <div class="m-0 p-4 text-center teal-bg">
                    <div class="m-0 p-0 pb-5 row">
                        <%
                        if (categories != null || categories.size() != 0)
                        for (ProductCategory cat : categories) {
                        %>
                        <jsp:include page="components/categoryTile.jsp">
                            <jsp:param name="id" value="<%= cat.getId()%>"/>
                            <jsp:param name="name" value="<%= cat.getName()%>"/>
                            <jsp:param name="description" value="<%= cat.getDescription()%>"/>
                        </jsp:include>
                        <%
                        }
                        %>
                    </div>
                </div>
            </section>

            <div class="row h-25 w-100 m-0 px-2 py-4 text-center divider dark-blue-bg shadow" style="line-height: 2em; letter-spacing: 0.5em;">
                <h1 class="col text-uppercase yellow">What we can<strong><br/>offer YOU<br/>
                        <hr class="yellow" style="border-bottom: 2px; border-color: var(--highlight);"/></strong>
                </h1>
            </div>

            <section id="product-list" class="teal-bg">
                <div style="min-height: 75vh; width: 100vw;">
                    <div class="py-5">
                        <div class="m-0 p-0 row h-100">

                            <%
                            if (products != null || products.size() != 0)
                            for (Product pro : products) {
                            %>
                            <jsp:include page="components/productTile.jsp">
                                <jsp:param name="id" value="<%= pro.getId()%>"/>
                                <jsp:param name="name" value="<%= pro.getName()%>"/>
                                <jsp:param name="price" value="<%= pro.getPrice() %>"/>
                                <jsp:param name="description" value="<%= pro.getDescription() %>"/>
                                <jsp:param name="categoryId" value="<%= pro.getProductCategoryId().getId()%>"/>
                                <jsp:param name="image" value="<%= pro.getImage() %>"/>
                            </jsp:include>
                            <%
                            }
                            %>
                        </div>
                    </div>
                </div>
            </section>

            <section class="row m-0 p-4 d-flex justify-content-center">
                <div class="col-12 lead text-center text-uppercase">
                    <h3>What do others say about us?</h3>
                </div>
                <div class="w-100"></div>

                <div class="col-md-6">
                    <div class="card rounded m-3 p-5 bg-white borderless shadow h-100">
                        <div class="card-text text-center">
                            <blockquote class="blockquote">With their train that goes "choo-choo" when pushed, my kid was fascinated. Even i played with him for hours! Truly awesome toy!</blockquote>
                        </div>
                        <div class="d-flex justify-content-center pt-2 pb-2">
                            <div class="d-flex justify-content-center"><div class="blockquote-footer">John Ivoon, Student</div></div>
                        </div>
                    </div>
                </div>

                <div class="col-md-6 align-items-center">
                    <div class="card rounded m-3 p-5 bg-white borderless shadow text-center h-100">
                        <div class="align-items-center h-50">
                            <div class="card-text">
                                <blockquote class="blockquote">Great good quality products. I would say my money's well spent here! Also, i love the looks of this web shop. =)</blockquote>
                            </div>
                            <div class="card-text">
                                <div class="blockquote-footer">Pjotr Macig, Cj Cleaning solutions</div>
                            </div>
                        </div>
                    </div>
                </div>            
            </section>
        </section>

        <section id="footer">
            <jsp:include page="./components/footer.jsp"/>
        </section>
    </body>
</html>