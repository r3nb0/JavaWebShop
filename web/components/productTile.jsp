<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<div class="col-xs-12 col-md-12 col-lg-6 p-2" style="z-index: 100;">
    <div id="product-<%= request.getParameter("id")%>-<%= request.getParameter("categoryId")%>" 
         class="card shadow product-tile rounded m-3 text-center">
        <div class="card-header card-title">
            <a href="./product?id=<%= request.getParameter("id")%>"><h3 id="name-<%= request.getParameter("id")%>"><%= request.getParameter("name")%></h3></a>
        </div>
        <div class="card-body card-text">
                <div class="w-50 d-inline-flex">
                    <img class="img-fluid img-thumbnail h-50 w-75 mx-auto" id="image-<%= request.getParameter("id")%>" src="<%= request.getParameter("image") %>" alt="<%= request.getParameter("name") %>" />
                </div>
                <div class="w-50 d-inline-flex">
                    <p id="description-<%= request.getParameter("id")%>" class="lead mx-auto"><%= request.getParameter("description")%></p>
                </div>
        </div>
        <div class="card-footer">
            <div class="pull-left lead yellow">
                <p id="price-<%= request.getParameter("id")%>" class="d-inline"><%= request.getParameter("price").substring(0, request.getParameter("price").toString().indexOf('.')+3)%></p>
                <p class="d-inline"> €</p>
            </div>
            <div class="text-center">
                <form action="cart" method="POST" class="m-0 p-2">
                    <input type="hidden" name="productId"
                           value="<%= request.getParameter("id")%>"/>
                    <input type="hidden" class="form-control form-control-sm" name="amount" value="1" placeholder="1"/>
                    <div class="btn-group">
                        <button class="btn btn-dark" type="submit" value="add" name="action">Add to cart</button>
                        <a href="./product?id=<%= request.getParameter("id")%>" id="showProduct-<%= request.getParameter("id")%>" class="btn yellow-bg shadow">Details</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
