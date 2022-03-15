<%@page import="entities.AppLog"%>
<%@page import="entities.Product"%>
<%@page import="entities.InvoiceItem"%>
<%@page import="entities.InvoiceItem"%>
<%@page import="entities.InvoiceItem"%>
<%@page import="entities.InvoiceItem"%>
<%@page import="entities.InvoiceItem"%>
<%@page import="entities.ProductCategory"%>
<%@page import="entities.Account"%>
<%@page import="services.IpService"%>
<%@page import="bl.Manager"%>
<%@page import="bl.IManage"%>
<%@page import="entities.Invoice"%>
<%@page import="java.util.Collection"%>
<%@page import="models.UserModel"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin panel</title>
    </head>
    <body>
        <section id="header">
            <jsp:include page="../components/header.jsp"/>
        </section>

        <%
            UserModel user = null;
            HttpSession sess = request.getSession(false);
            user = (UserModel) sess.getAttribute("user");
            Collection<ProductCategory> categories = null;
            categories = (Collection<ProductCategory>) sess.getAttribute("categories");
            Collection<Product> products = null;
            products = (Collection<Product>) sess.getAttribute("products");
            Collection<Invoice> orders = null;
            orders = (Collection<Invoice>) sess.getAttribute("orders");
            Collection<AppLog> logs = null;
            logs = (Collection<AppLog>) sess.getAttribute("logs");
            if (user == null || !user.isLogged() || !user.hasRole("Admin")) {
            response.sendRedirect("../login");
            }
        %>
        <section id="content" > 
            <div class="row m-0 p-0">
                <div class="col-12 text-center py-3">
                    <h2>Admin Menu</h2>
                    <hr/>
                </div>
                <div class="col-xs-0 col-md-1">
                </div>
                <div class="col-xs-12 col-md-10">
                    <div class="row m-0 p-0">

                        <!-- categories modal activator --> 
                        <div class="col p-5">
                            <div data-toggle="modal" data-target="#categoryModal" 
                                 class="btn btn-lg btn-outline-dark position-relative">
                                <h3>Categories</h3>
                                <span class="position-absolute top-0 start-100
                                      translate-middle badge rounded-pill dark-blue-bg text-warning">
                                    <%= categories!=null?categories.size():"none" %>
                                    <span class="visually-hidden">number of categories</span>
                                </span>
                            </div>
                        </div>



                        <!-- products modal activator --> 
                        <div class="col p-5">
                            <div data-toggle="modal" data-target="#productModal" 
                                 class="btn btn-lg btn-outline-dark position-relative">
                                <h3>Products</h3>
                                <span class="position-absolute top-0 start-100
                                      translate-middle badge rounded-pill dark-blue-bg text-warning">
                                    <%= products!=null?products.size():"none" %>
                                    <span class="visually-hidden">number of products</span>
                                </span>
                            </div>
                        </div>
                                    <div class="w-100"></div>
                                    
                        <!-- orders modal activator --> 
                        <div class="col p-5">
                            <div data-toggle="modal" data-target="#ordersModal" 
                                 class="btn btn-lg btn-outline-dark position-relative">
                                <h3>Orders</h3>
                                <span class="position-absolute top-0 start-100
                                      translate-middle badge rounded-pill dark-blue-bg text-warning">
                                    <%= orders!=null?orders.size():"none" %>
                                    <span class="visually-hidden">number of orders</span>
                                </span>
                            </div>
                        </div>

                        <!-- logs modal activator --> 
                        <div class="col p-5">
                            <div data-toggle="modal" data-target="#logsModal" 
                                 class="btn btn-lg btn-outline-dark position-relative">
                                <h3>Logs</h3>
                                <span class="position-absolute top-0 start-100
                                      translate-middle badge rounded-pill dark-blue-bg text-warning">
                                    <%= logs!=null?logs.size():"none" %>
                                    <span class="visually-hidden">number of logs</span>
                                </span>
                            </div>
                        </div>    


                    </div>
                </div>
                <div class="col-xs-0 col-md-1"></div>
            </div>
        </section>

        <section id="footer">
            <jsp:include page="../components/footer.jsp"/>
        </section>

        <section>
            <!-- MODALS -->
            <!-- CATEGORIES -->
            <div id="categoryModal" class="modal fade m-auto" tabindex="-1" role="dialog" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="d-flex justify-content-between align-items-center mb-3">
                                <span>Categories: <%= categories!=null?categories.size():"none" %></span>
                            </h4>
                            <div class="close btn btn-sm btn-outline-danger" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true" class="red">&times;</span>
                            </div>
                        </div>
                        <div class="modal-body">
                            <div class="h-75" style="overflow-y: scroll; max-height: 60vh;">
                                <table id="categoryTable" class="table table-responsive table-bordered table-striped">
                                    <thead class="sticky-top bg-light">
                                        <tr>
                                            <th>Name</th>
                                            <th>Description</th>
                                            <th>Actions</th>
                                        </tr>
                                    </thead>
                                    <% 
                                        if (categories != null && !categories.isEmpty()) {
                                            for (ProductCategory cat : categories) {
                                    %>
                                    <form action="category" method="POST">
                                        <tr <%= cat.getRemoved().equalsIgnoreCase("NO")?"":"style='background-color: pink;'" %>>
                                            <td class="form-group mb-2">
                                                <p class="d-none"><%= cat.getName()%></p>
                                                <input type="hidden" name="id" value="<%= cat.getId() %>" />
                                                <label for="categoryName-<%= cat.getId() %>">Name:</label>
                                                <input <%= cat.getRemoved().equalsIgnoreCase("YES")?"disabled":"" %> 
                                                    id="categoryName-<%= cat.getId() %>" class="form-control" type="text" 
                                                    minLenght="3" maxLenght="50" name="name" value="<%= cat.getName()%>" />
                                            </td>
                                            <td class="form-group mb-2">
                                                <p class="d-none"><%= cat.getDescription()%></p>
                                                <label for="categoryDesc-<%= cat.getId() %>">Description:</label>
                                                <input id="categoryDesc-<%= cat.getId() %>" class="form-control"  type="text"
                                                       minLenght="3" maxLenght="250" name="description" value="<%= cat.getDescription()%>"
                                                       <%= cat.getRemoved().equalsIgnoreCase("YES")?"disabled":"" %>  />
                                            </td>
                                            <td class="btn-group btn-group-sm btn-group-vertical">
                                                <button class="btn btn-sm btn-outline-success"  <%= cat.getRemoved().equalsIgnoreCase("YES")?"disabled":"" %> 
                                                        type="submit" value="edit" name="action">
                                                    <i class="material-icons material-icons-outline align-self-center"
                                                       style="font-size: 2rem;">save</i>
                                                </button>
                                                <button class="btn btn-sm btn-outline-danger" <%= cat.getRemoved().equalsIgnoreCase("YES")?"disabled":"" %> 
                                                        type="submit" value="remove" name="action">
                                                    <i class="material-icons material-icons-outline align-self-center" 
                                                       style="font-size: 2rem;">delete_forever</i>
                                                </button>
                                            </td>
                                        </tr>
                                    </form>
                                    <%
                                        }
                                    } else {
                                    %>
                                    <tr>
                                        <td>No data!</td>
                                        <td>No data!</td>
                                        <td></td>
                                    </tr>
                                    <%
                                        }
                                    %>
                                    <tr>
                                        <td colspan="6" class="text-center m-0 p-0">
                                            <form action="category" method="POST" class="card card-body m-2 shadow">
                                                <h4>Add new Category</h4>
                                                <input type="hidden" name="id" value="1" />
                                                <div class="form-group mb-2">
                                                    <input type="hidden" name="id" value="1" />
                                                    <label for="categoryName">Name:</label>
                                                    <input id="categoryName" class="form-control" type="text" 
                                                           minLenght="3" maxLenght="50" name="name" placeholder="Electronics" value="" />
                                                </div>
                                                <div class="form-group mb-2">
                                                    <label for="categoryDesc">Description:</label>
                                                    <input id="categoryDesc" class="form-control"  type="text" 
                                                           minLenght="3" maxLenght="250" name="description" placeholder="All things for electrical.!" value="" />
                                                </div>
                                                <hr/>
                                                <div class="d-flex justify-content-around">
                                                    <button class="btn btn-outline-success" type="submit" value="add" name="action">
                                                        <i class="material-icons material-icons-outline align-self-center"
                                                           style="font-size: 2rem;">save</i>
                                                        save
                                                    </button>
                                                </div>
                                            </form>
                                        </td>
                                    </tr>

                                </table>
                            </div>
                            <hr class="mb-4"/>
                        </div>
                    </div>
                </div>
            </div>

            <!-- ORDERS -->
            <div id="ordersModal" class="modal fade" tabindex="-1" 
                 role="dialog" aria-hidden="true">
                <div class="modal-dialog modal-xl">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="d-flex justify-content-between align-items-center mb-3">
                                <span>Orders: <%= orders!=null?orders.size():"none" %></span>
                            </h4>
                            <div class="close btn btn-sm btn-outline-danger" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true" class="red">&times;</span>
                            </div>
                        </div>
                        <div class="modal-body">
                            <div style="overflow-y: scroll; max-height: 60vh;">
                                <table id="ordersTable" class="table table-responsive table-bordered table-striped">
                                    <thead class="sticky-top bg-light">
                                        <tr>
                                            <th>Id</th>
                                            <th>Buyer</th>
                                            <th>Message</th>
                                            <th>Items</th>
                                            <th>Time of order</th>
                                            <th>Payment gateway</th>
                                            <th>Total</th>
                                        </tr>
                                    </thead>
                                    <% 
                                        if (orders != null && !orders.isEmpty()) {
                                            for (Invoice inv : orders) {
                                    %>
                                    <form action="product" method="POST">
                                        <tr <%= inv.getCompleted().equalsIgnoreCase("COMPLETED")?"style='background-color: #52b788;'":"style='background-color: pink;'" %>>
                                            <td class="container mb-2 w-auto">
                                                <b>#<%= inv.getId() %></b>
                                            </td>
                                            <td class="container mb-2 w-auto">
                                                <p>Username: <%= inv.getAccountId().getUsername() %></p>
                                                <p>Email: <%= inv.getAccountId().getEmail() %></p>
                                            </td>
                                            <td class="container mb-2 w-auto">
                                                <p><%= inv.getMessage() %></p>
                                            </td>
                                            <td class="container mb-2 w-auto">
                                                <div style="overflow-y: scroll; height: 7vh; max-height: 15vh;">
                                                    <%
                                                    if (inv.getInvoiceItemCollection() != null && inv.getInvoiceItemCollection().size()>0) {
                                                        for (InvoiceItem item : inv.getInvoiceItemCollection()) {
                                                    %>
                                                    <p><%= item.getProductId().getName() %> x <%= item.getAmount() %> - 
                                                        <b class="font-weight-bold"><%= item.getPrice().toString().substring(0, item.getPrice().toString().indexOf('.') + 3) %> kn</b>
                                                    </p>
                                                    <%
                                                        }
                                                    } else {
                                                    %>
                                                    <small style="color: red;">NO ITEMS!</small>
                                                    <%
                                                    }
                                                    %>
                                                </div>
                                            </td>
                                            <td class="container mb-2 w-auto">
                                                <p class="lead"><%= inv.getTimeOfOrder()%></p>
                                            </td>
                                            <td class="container mb-2 w-auto">
                                                <b><%= inv.getPaymentId().getName() %></b>
                                                <b>(<%= inv.getCompleted() %>)</b>
                                                <p class="lead font-weight-bold">
                                                    <%= inv.getTransactionId()!=null?"(" + inv.getTransactionId() + ")":""%>
                                                </p>
                                            </td>
                                            <td class="container mb-2 w-auto">
                                                <h5 class="lead font-weight-bold"><b><%= inv.getTotalPrice().setScale(2).toString() %></b> €</h5>
                                            </td>
                                        </tr>
                                    </form>
                                    <%
                                        }
                                    } else {
                                    %>
                                    <tr>
                                        <td>No data!</td>
                                        <td>No data!</td>
                                        <td>No data!</td>
                                        <td>No data!</td>
                                        <td>No data!</td>
                                        <td>No data!</td>
                                    </tr>
                                    <%
                                        }
                                    %>
                                </table>
                            </div>
                            <hr class="mb-4"/>
                        </div>
                    </div>
                </div>
            </div>
                                
            <div id="productModal" class="modal fade" tabindex="-1" 
                 role="dialog" aria-hidden="true">
                &nbsp;
                <div class="modal-dialog modal-xl">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="d-flex justify-content-between align-items-center mb-3">
                                <span>Products: <%= products!=null?products.size():"none" %></span>
                            </h4>
                            <div class="close btn btn-sm btn-outline-danger" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true" class="red">&times;</span>
                            </div>
                        </div>
                        <div class="modal-body">
                &nbsp;
                            <div style="overflow-y: scroll; max-height: 60vh;">
                                <table id="productsTable" class="table table-responsive table-bordered table-striped">
                                    <thead class="sticky-top bg-light">
                                        <tr>
                                            <th>Name</th>
                                            <th>Description</th>
                                            <th>Price</th>
                                            <th>Image (URL)</th>
                                            <th>Category</th>
                                            <th>Actions</th>
                                        </tr>
                                    </thead>
                                    <% 
                                        if (products != null && !products.isEmpty()) {
                                            for (Product pro : products) {
                                    %>
                                    <form action="product" method="POST">
                                        <tr <%= pro.getRemoved().equalsIgnoreCase("NO")?"":"style='background-color: pink;'" %>>
                                            <td class="form-group mb-2">
                                                <p class="d-none"><%= pro.getName()%></p>
                                                <input type="hidden" name="id" value="<%= pro.getId() %>" />
                                                <label for="categoryName-<%= pro.getId() %>">Name:</label>
                                                <input <%= pro.getRemoved().equalsIgnoreCase("YES")?"disabled":"" %> 
                                                    id="categoryName-<%= pro.getId() %>" class="form-control" type="text" 
                                                    minLenght="3" maxLenght="50" name="name" value="<%= pro.getName()%>" />
                                            </td>
                                            <td class="form-group mb-2">
                                                <p class="d-none"><%= pro.getDescription()%></p>
                                                <label for="categoryDesc-<%= pro.getId() %>">Description:</label>
                                                <input  <%= pro.getRemoved().equalsIgnoreCase("YES")?"disabled":"" %> id="categoryDesc-<%= pro.getId() %>" class="form-control"  type="text" 
                                                                                                                      minLenght="3" maxLenght="250" name="description" value="<%= pro.getDescription()%>" />
                                            </td>
                                            <td class="form-group mb-2">
                                                <p class="d-none"><%= pro.getPrice()%></p>
                                                <label for="categoryPrice-<%= pro.getId() %>">Price:</label>
                                                <input  <%= pro.getRemoved().equalsIgnoreCase("YES")?"disabled":"" %> id="categoryPrice-<%= pro.getId() %>" class="form-control"  type="text"
                                                                                                                      min="1" max="9999999" name="price" value="<%= pro.getPrice()%>" />
                                            </td>
                                            <td class="form-group mb-2">
                                                <p class="d-none"><%= pro.getImage()%></p>
                                                <label for="productImage-<%= pro.getId() %>">Image (URL):</label>
                                                <input  <%= pro.getRemoved().equalsIgnoreCase("YES")?"disabled":"" %> 
                                                    id="productImage-<%= pro.getId() %>" class="form-control"  type="text"
                                                    minLenght="1" maxLenght="999" name="image" value="<%= pro.getImage()%>" />
                                            </td>
                                            <td class="form-group mb-2">
                                                <p class="d-none"><%= pro.getProductCategoryId().getName() %></p>
                                                <div class="form-group">                      
                                                    <label for="productCategory-<%= pro.getId() %>">Select category (current <b><%= pro.getProductCategoryId().getName() %></b>):</label>
                                                    <input  <%= pro.getRemoved().equalsIgnoreCase("YES")?"disabled":"" %> 
                                                        type="hidden" value="<%=pro.getProductCategoryId().getId() %>" 
                                                        name="category" id="productCategory-<%= pro.getId() %>"/>
                                                    <select  <%= pro.getRemoved().equalsIgnoreCase("YES")?"disabled":"" %> 
                                                        id="s-productCategory-<%= pro.getId() %>" data-live-search="true"
                                                        class="form-select form-select-sm" onChange="setCategories(this)"
                                                        value="<%=pro.getProductCategoryId().getId() %>">
                                                        <%
                                                        if (categories != null && categories.size() != 0) {
                                                            for (ProductCategory cat : categories) {
                                                        %>
                                                        <option id="<%=cat.getId()%>" value="<%=cat.getId()%>"> <%=cat.getName()%></option>
                                                        <%
                                                            }
                                                        }
                                                        %>
                                                    </select>
                                                </div>
                                            </td>
                                            <td class="btn-group btn-group-sm btn-group-vertical">
                                                <button class="btn btn-sm btn-outline-success"  <%= pro.getRemoved().equalsIgnoreCase("YES")?"disabled":"" %> type="submit" value="edit" name="action">
                                                    <i class="material-icons material-icons-outline align-self-center"
                                                       style="font-size: 2rem;">save</i>
                                                </button>
                                                <button class="btn btn-sm btn-outline-danger" <%= pro.getRemoved().equalsIgnoreCase("YES")?"disabled":"" %> type="submit" value="remove" name="action">
                                                    <i class="material-icons material-icons-outline align-self-center" 
                                                       style="font-size: 2rem;">delete_forever</i>
                                                </button>
                                            </td>
                                        </tr>
                                    </form>
                                    <%
                                        }
                                    } else {
                                    %>
                                    <tr>
                                        <td>No data!</td>
                                        <td>No data!</td>
                                        <td>No data!</td>
                                        <td>No data!</td>
                                        <td>No data!</td>
                                        <td>No data!</td>
                                    </tr>
                                    <%
                                        }
                                    %>
                                    <tr>
                                        <td colspan="6" class="text-center m-0 p-0">
                                            <form action="product" method="POST" class="card card-body m-2 shadow">
                                                <h4>Add new Product</h4>
                                                <input type="hidden" name="id" value="1" />
                                                <div class="form-group mb-2">
                                                    <input type="hidden" name="id" value="1" />
                                                    <label for="productName">Name:</label>
                                                    <input id="productName" class="form-control" type="text" 
                                                           minLenght="3" maxLenght="50" name="name" placeholder="Screwdriver" value="" />
                                                </div>
                                                <div class="form-group mb-2">
                                                    <label for="productDesc">Description:</label>
                                                    <input id="productDesc" class="form-control"  type="text" 
                                                           minLenght="3" maxLenght="250" name="description" placeholder="Screws in screwes!" value="" />
                                                </div>
                                                <div class="form-group mb-2">
                                                    <label for="productPrice">Price:</label>
                                                    <input id="productPrice" class="form-control"  type="text"
                                                           min="1" max="9999999" name="price" value="10.99" />
                                                </div>
                                                <div class="form-group mb-2">
                                                    <label for="productImage">Image (URL):</label>
                                                    <input id="productImage" class="form-control"  type="text"
                                                           minLenght="1" maxLenght="999" name="image" placeholder="https://get.random.img/image123.jpg" value="" />
                                                </div>
                                                <div class="form-group mb-2">
                                                    <div class="form-group">                      
                                                        <label for="productCategoryNew">Select category:</label>
                                                        <input type="hidden" value=""
                                                               name="category" id="productCategoryNew"/>
                                                        <select id="s-productCategoryNew" data-live-search="true"
                                                                class="form-select form-select-sm"
                                                                onChange="setCategories(this)">
                                                            <%
                                                            if (categories != null && categories.size() != 0) {
                                                                for (ProductCategory cat : categories) {
                                                            %>
                                                            <option id="<%=cat.getId()%>" value="<%=cat.getId()%>"> <%=cat.getName()%></option>
                                                            <%
                                                                }
                                                            }
                                                            %>
                                                        </select>
                                                    </div>
                                                </div>
                                                <hr/>
                                                <div class="d-flex justify-content-around">
                                                    <button class="btn btn-outline-success" type="submit" value="add" name="action">
                                                        <i class="material-icons material-icons-outline align-self-center"
                                                           style="font-size: 2rem;">save</i>
                                                        save
                                                    </button>
                                                </div>
                                            </form>
                                        </td>
                                    </tr>

                                </table>
                            </div>
                            <hr class="mb-4"/>
                        </div>
                    </div>
                </div>
            </div>

            <div id="logsModal" class="modal fade" tabindex="-1" 
                 role="dialog" aria-hidden="true">
                &nbsp;
                <div class="modal-dialog modal-xl">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="d-flex justify-content-between align-items-center mb-3">
                                <span>Logs <%= logs!=null?logs.size():"none" %></span>
                            </h4>
                            <div class="close btn btn-sm btn-outline-danger" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true" class="red">&times;</span>
                            </div>
                        </div>
                        <div class="modal-body">
                &nbsp;
                            <div style="overflow-y: scroll; max-height: 60vh;">
                                <table id="logsTable" class="table table-responsive table-bordered table-striped">
                                    <thead class="sticky-top bg-light">
                                        <tr>
                                            <th>ID</th>
                                            <th>Description</th>
                                            <th>Time</th>
                                            <th>Location</th>
                                        </tr>
                                    </thead>
                                    <% 
                                        if (logs != null && !logs.isEmpty()) {
                                            for (AppLog log : logs) {
                                    %>
                                    <tr <%= log.getRemoved().equalsIgnoreCase("NO")?"":"style='background-color: pink;'" %>>
                                        <td class="form-group mb-2">
                                            <p><%= log.getId()%></p>
                                        </td>
                                        <td class="form-group mb-2">
                                            <p><%= log.getDescription()%></p>
                                        </td>
                                        <td class="form-group mb-2">
                                            <p><%= log.getTime()%></p>
                                        </td>
                                        <td class="form-group mb-2">
                                            <p><%= log.getLocation() %></p>
                                        </td>
                                    </tr>
                                    <%
                                        }
                                    } else {
                                    %>
                                    <tr>
                                        <td>No data!</td>
                                        <td>No data!</td>
                                        <td>No data!</td>
                                    </tr>
                                    <%
                                        }
                                    %>
                                </table>
                            </div>
                            <hr class="mb-4"/>
                        </div>
                    </div>
                </div>
            </div>

        </section>

        <script>
        //<![CDATA[
        $('#ordersTable').DataTable();
        $('#logsTable').DataTable();
        $('#productsTable').DataTable();
        $('#categoryTable').DataTable();
        function setCategories(e) {
            document.getElementById(e.id.substring(2, e.id.length)).value = e.value;
        }
        //]]>
        </script>
    </body>
</html>
