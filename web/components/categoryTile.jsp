<%@page import="models.UserModel"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="col h-100">
    <div id="category-<%= request.getParameter("id")%>" 
         class="card shadow rounded w-auto h-25 m-3 bg-transparent item-tile">
        <div class="card-body p-4">
            <h1 class="w-auto p-0 m-0" ><%= request.getParameter("name")%></h1>
            <hr/>
            <p class="text-muted"><%= request.getParameter("description")%></p>
        </div>
        <div class="card-footer p-4">
            <form action="products" method="POST">
                <input type="hidden" name="categories" value='<%= request.getParameter("id")%>'/>
                <input type="hidden" name="search" value=""/>
                <input type="hidden" name="sort" value="az"/>
                <input type="hidden" name="minPrice" value="0"/>
                <input type="hidden" name="maxPrice" value="100000"/>
                <button type="submit" value="apply" name="action" 
                        class="btn btn-outline-primary text-uppercase" id="showCategory-<%= request.getParameter("id")%>">Browse product in this category</button>
            </form>
        </div>
    </div>
</div>
