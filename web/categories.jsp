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
        <section id="header">
            <jsp:include page="components/header.jsp"/>
        </section>
        <section id="content">
            <div class="m-0 p-4 text-center teal-bg">
                <h3>Categories</h3>
                <div class="m-0 p-0 pb-5 row">
                    <%
                    Collection<ProductCategory> categories = (Collection<ProductCategory>) session.getAttribute("categories");
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

        <section id="footer">
            <jsp:include page="components/footer.jsp"/>
        </section>
    </body>
</html>
