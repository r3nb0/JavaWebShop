<%@page import="services.IpService"%>
<%@page import="models.CartModel"%>
<%@page import="models.UserModel"%>
<base href="<%= IpService.getBaseUrl(request) %>" target="_self">
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1"/>

<link rel="preconnect" href="https://fonts.googleapis.com"/>
<link rel="preconnect" href="https://fonts.gstatic.com"/>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet"/>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" 
      integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" 
      rel="stylesheet" crossorigin="anonymous"/>
<link href="${pageContext.request.contextPath}/resources/css/styles.css"
      rel="stylesheet"/>
<link href="${pageContext.request.contextPath}/resources/css/gFont-urbanist.css"
      rel="stylesheet"/>  
<script src="${pageContext.request.contextPath}/resources/js/jquery.slim.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/popper.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.bundle.min.js"></script>


<script src="https://cdn.datatables.net/1.10.22/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.10.22/js/dataTables.bootstrap4.min.js"></script>


<%
HttpSession sess = request.getSession();
if (sess.getAttribute("errorMessage") != null) {
%>

<div style="position: absolute; top: 10vh; left: 70%;"
     class="alert alert-dismissible alert-warning d-flex align-items-center w-25 fade show opacity-75" role="alert">
    <div class="bi flex-shrink-0 me-2" aria-label="Warning:">
        <i class="material-icons material-icons-outline align-self-center"
           style="font-size: 2rem;">warning</i>
    </div>
    <div>
        <p><%= sess.getAttribute("errorMessage") %></p>
    </div>
    <div onclick="dismissAlert(this)" class="btn-close btn-sm btn-outline-danger" data-bs-dismiss="alert" aria-label="Close">
    </div>
</div>

<%
    sess.removeAttribute("errorMessage");
} else if (sess.getAttribute("infoMessage") != null) {
%>

<div style="position: absolute; top: 10vh; left: 70%;"
     class="alert alert-dismissible alert-success d-flex align-items-center w-25 fade show" role="alert">
    <div class="bi flex-shrink-0 me-2" aria-label="Info:">
        <i class="material-icons material-icons-outline align-self-center"
           style="font-size: 2rem;">info</i>
    </div>
    <div>
        <p><%= sess.getAttribute("infoMessage") %></p>
    </div>
    <div onclick="dismissAlert(this)" class="btn-close btn-sm btn-outline-danger" data-bs-dismiss="alert" aria-label="Close">
    </div>
</div>

<%
    sess.removeAttribute("infoMessage");
}
%>

<script>
//<![CDATA[
$('#ordersTable').DataTable();
$('#logsTable').DataTable();
$('#productsTable').DataTable();
function dismissAlert(e){
    var alert = e.parentElement;
    alert.parentElement.removeChild(alert);
}
//]]>
</script>

<section id="header">
    <%
    UserModel user = (UserModel) session.getAttribute("user");
    CartModel cart = (CartModel) session.getAttribute("cart");
    if (user == null) {
        user = new UserModel();
        user.setLogged(false);
        user.setRole("user"); 
    }
    %>
    <nav class="navbar navbar-expand-lg navbar-light dark-blue-bg white-text px-4 shadow fixed-top"
         style="margin-bottom: 12vh;">
        <a href="home">My Little Web Shop</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <div class="m-0 p-0 w-100">
                <ul class="navbar-nav mr-auto d-flex text-center align-items-center justify-content-center">

                    <%
                    if (user.isLogged()) {
                    %>

                    <li class="nav-item">
                        <a href="../account/account" class="nav-link">
                            <i class="material-icons material-icons-outline align-self-center"
                               style="font-size: 2rem;">account_circle</i>
                        </a>
                    </li>
                    <%
                    } else {
                    %>
                    <li class="nav-item">
                        <div class="d-flex justify-content-center">
                            <a href="../login" class="nav-link">Login</a> 
                            <span class="nav-link">/</span>
                            <a href="../register" class="nav-link">Register</a>
                        </div>
                    </li>
                    <%
                    }
                    %>

                    <li class="nav-item">
                        <a href="../categories" class="nav-link">Categories</a>
                    </li>
                    <li class="nav-item">
                        <a href="../products" class="nav-link">Products</a>
                    </li>
                    <li class="nav-item">
                        <a href="../about" class="nav-link">About Us</a>
                    </li>
                    <%
                    if (user != null && user.isLogged() && user.hasRole("admin")) {
                    %>
                    <li class="nav-item">
                        <a href="../admin" class="nav-link yellow">Admin Panel</a>
                    </li>
                    <%
                    }
                    %>
                    <% if (user.isLogged()) {
                    %>
                    <li class="nav-item">
                        <a href="../logout" class="nav-link btn btn-outline-secondary mx-2 my-0 px-auto">Logout</a>
                    </li>
                    <%
                    }
                    if (cart != null) {
                    %>

                    <li class="nav-item mr-3">
                        <a href="../cart" class="nav-link mx-2 my-0 px-auto position-relative">
                            <i class="material-icons material-icons-outline align-self-center" style="font-size: 1.3rem;">shopping_cart</i>
                            <span class="position-absolute top-0 start-100
                                  translate-middle badge rounded-pill yellow-bg text-dark">
                                <%= cart!=null?cart.getItems().size():"0" %>
                                <span class="visually-hidden">number of categories</span>

                        </a>
                    </li>
                    <%
                }
                    %>

                    <li class="nav-item items-align-center">
                        <hr/>
                    </li>
                    <li class="nav-item items-align-center pl-3">
                        <form action="products" method="POST" class="d-felx  justify-content-center">
                            <div class="d-flex m-0 p-auto">
                                <input class="form-control mr-2" name="search" type="search" placeholder="Candle" style="z-index: 100;" aria-label="Search"/>
                                <input name="minPrice" type="hidden" value="1" aria-label="minPrice"/>
                                <input name="maxPrice" type="hidden" value="100000" aria-label="maxPrice"/>
                                <input name="sort" type="hidden" value="az" aria-label="sort"/>
                                <button class="btn btn-outline-success h-75" style="transform: translateX(-2px); z-index: 90;" value="Apply" name="action" type="submit">
                                    <i class="material-icons material-icons-outline align-self-center" style="font-size: 1.3rem;">search</i>
                                </button>
                            </div>
                        </form>
                    </li>

                </ul>
            </div>
        </div>
    </nav>
</section>