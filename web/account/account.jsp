<%@page import="java.math.BigDecimal"%>
<%@page import="services.IpService"%>
<%@page import="bl.Manager"%>
<%@page import="entities.InvoiceItem"%>
<%@page import="java.util.Collection"%>
<%@page import="entities.Invoice"%>
<%@page import="models.UserModel"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>LittleShop - Account</title>
    </head>
    <body>

        <section id="header">
            <jsp:include page="../components/header.jsp"/>
        </section>

        <section id="content"> 
            <%  UserModel user = null;
                int numberOfOrders = 0;
                int totalPaid = 0;
                Collection<Invoice> invoiceList = null;
                HttpSession sess = request.getSession();
                
                user = (UserModel) sess.getAttribute("user");
                invoiceList = new Manager(IpService.getClientIpAddr(request)).getInvoicesForUser(user!=null?user.getId():0);
                if (invoiceList == null || invoiceList.size() == 0) {
                    numberOfOrders = 0;
                } else {
                    numberOfOrders = invoiceList.size();
                }
                if (user != null && user.isLogged()) {
            %>
            <div class="text-center">
                <h3>Account</h3>
            </div>
            <div class="row m-0 p-0">
                <div class="col-xs-12 col-md-6 order-md-1 p-3">
                    <div class="card card-body p-3 m-3 shadow">
                        <div class="d-flex flex-column align-items-center text-center p-3">
                            <h4 class="dark-blue">Profile: </h4>
                            <img class="rounded-circle" width="150px" src="https://st3.depositphotos.com/15648834/17930/v/600/depositphotos_179308454-stock-illustration-unknown-person-silhouette-glasses-profile.jpg"/>
                            <span class="font-weight-bold"><%= user.getUsername()%></span>
                            <span class="text-black-50"><%= user.getEmail()%></span>
                        </div>
                    </div>
                </div>
                <div class="col-xs-12 col-md-6 order-md-2 p-3">

                    <div class="card p-3 card-body shadow">
                        <h3>Orders:</h3>
                        <span class="position-absolute top-0 start-0 translate-middle badge rounded-pill yellow-bg text-dark">
                            <%= numberOfOrders %>
                            <span class="visually-hidden">number of categories</span>
                        </span>
                        <div class="h-75">
                            <ul class="list-group mb-3" style="overflow-y: scroll; max-height: 45vh;">
                                <% 
                                    if (invoiceList != null && !invoiceList.isEmpty()) {
                                        for (Invoice inv : invoiceList) {
                                %>
                                <li class="list-group-item list-group-item-success">
                                    <div class="d-flex justify-content-between m-0 p-0">
                                        <h5 class="my-0">#<%= inv.getId() + "(" %> <%= (inv.getTransactionId()!=null?inv.getTransactionId():"") +")"%></h5>
                                        <h5><%= inv.getTimeOfOrder() %> </h5>
                                    </div>
                                    <hr/>
                                    <div class="d-flex justify-content-between">
                                        <div>
                                            <div>
                                                <h6>Username:</h6>
                                                <p><%= inv.getAccountId().getUsername()%></p>
                                            </div>
                                            <div>
                                                <h6>Email:</h6>
                                                <a href="mailto:<%= inv.getAccountId().getEmail()%>"><%= inv.getAccountId().getEmail()%></a>
                                            </div>
                                        </div>
                                        <div class="mx-2">
                                            <h6>Items:</h6>
                                            <ul style="list-style: none; overflow-y: scroll; height: 25vh;" class="list-group">
                                                <%
                                                if (inv.getInvoiceItemCollection() != null && inv.getInvoiceItemCollection().size()>0) {
                                                for (InvoiceItem item : inv.getInvoiceItemCollection()) {
                                                %>
                                                <li class="list-group-item">
                                                    <p><%= item.getProductId().getName() %> (x <%= item.getAmount() %>) - </p>
                                                    <p class="font-weight-bold"> <%= item.getPrice().toString().substring(0, item.getPrice().toString().indexOf('.') + 3) %> €</p>
                                                </li> 
                                                <%
                                                }
                                            } else {
                                                %>
                                                <li class="list-group-item">
                                                    <small style="color: red;">NO ITEMS!</small>
                                                </li> 
                                                <%
                                                }
                                                %>

                                            </ul>
                                        </div>
                                    </div>
                                    <hr/>
                                    <div class="d-flex justify-content-between m-0 p-0">
                                        <h4>Total:</h4> 
                                        <h5><%= inv.getTotalPrice().toString().substring(0, inv.getTotalPrice().toString().indexOf('.') + 3) %> €</h5>
                                        <h5><b><%= inv.getPaymentId().getName() + (inv.getTransactionId()==null?"":'(' + inv.getTransactionId() + ')')  %></b></h5>
                                    </div>
                                    <hr/>
                                </li>
                                <hr/>
                                <%
                                    totalPaid += inv.getTotalPrice().intValue();
                                    }
                                %>
                                <%
                                } else {
                                %>
                                <li class="list-group-item d-flex justify-content-between lh-condensed">
                                    <div>
                                        <h6 class="my-0">No orders yet!</h6>
                                        <small class="text-muted">Nobody has made any orders!</small>
                                    </div>
                                    <span class="text-muted"></span>
                                </li>
                                <%
                                    }
                                %>
                            </ul>
                            <div class="text-center">
                            <i class="material-icons material-icons-outline align-self-center scroll-down" style="font-size: 2rem;">expand_more</i>
                            </div>
                        </div>
                        <hr class="mb-4"/>
                        <div class="text-center">
                            <h6 class="my-0">You paid us a total of <b><%= totalPaid %> €</b></h6>
                        </div>
                    </div>
                </div>
                <!--                <div class="col-xs-12 col-md-4 order-md-3 p-3">
                                    <div class="card p-3 card-body shadow" style="display: none;">
                                        <h4 class="d-flex justify-content-between align-items-center mb-3">
                                            <span class="dark-blue">Order: </span>
                                            <span class="badge badge-warning badge-pill dark-blue">#1122</span>
                                        </h4>
                                        <div class="h-75" style="overflow-y: scroll;">
                                            <ul class="list-group mb-3 dark-blue">
                                                <li class="list-group-item d-flex justify-content-between lh-condensed">
                                                    <div>
                                                        <h6 class="my-0">Product name</h6>
                                                        <small class="text-muted">Brief description</small>
                                                    </div>
                                                    <span class="text-muted">$12</span>
                                                </li>
                                                <li class="list-group-item d-flex justify-content-between lh-condensed">
                                                    <div>
                                                        <h6 class="my-0">Product name</h6>
                                                        <small class="text-muted">Brief description</small>
                                                    </div>
                                                    <span class="text-muted">$12</span>
                                                </li>
                                                <li class="list-group-item d-flex justify-content-between lh-condensed">
                                                    <div>
                                                        <h6 class="my-0">Product name</h6>
                                                        <small class="text-muted">Brief description</small>
                                                    </div>
                                                    <span class="text-muted">$12</span>
                                                </li>
                                            </ul>
                                        </div>
                                        <div class="list-group-item d-flex justify-content-between">
                                            <span>Total (HRK)</span>
                                            <strong>20kn</strong>
                                        </div>
                                        <hr class="mb-4"/>
                                    </div>
                                </div>-->
            </div>
            <%
            } else {
                response.sendRedirect("./login.jsp");
                }
            %>
        </section>

        <section id="footer">
            <jsp:include page="../components/footer.jsp"/>
        </section>
    </body>
</html>
