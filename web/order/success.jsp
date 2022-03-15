<%@page import="models.OrderDetailModel"%>
<%@page import="models.UserModel"%>
<%@page import="entities.Invoice"%>
<%@page import="com.paypal.api.payments.Payment"%>
<%@page import="com.paypal.api.payments.ShippingAddress"%>
<%@page import="com.paypal.api.payments.PayerInfo"%>
<%@page import="com.paypal.api.payments.Transaction"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Payment receipt</title>
    </head>
    <body>
        <%
        Invoice invoice = (Invoice) session.getAttribute("invoice");
        UserModel user = (UserModel) session.getAttribute("user");
        OrderDetailModel order = (OrderDetailModel) session.getAttribute("order");
        %>
        <section id="header">
            <jsp:include page="../components/header.jsp"/>
        </section>

        <section id="content">
            <div class="w-100 text-center pt-4">
                <h1>Payment done! Thank you for shopping with us! =)</h1>
                <h4>Please remember your order/invoice id since it's reference number to your order.</h4>
            </div>
            <div class="row">
                <div class="col p-3">
                    <%
                if (user != null) {
                    %>
                    <div class="card card-body m-3 p-md-5">

                        <table id="tablePayer" class="table table-bordered table-hover text-center mx-auto p-auto">
                            <tr><td colspan="3"><h3>Payer details</h3></td></tr>
                            <tr>
                                <td class="lead">Email:</td>
                                <td><%= user.getEmail() %></td>
                            </tr>
                            <tr>
                                <td class="lead">Username:</td>
                                <td><%= user.getUsername() %></td>
                            </tr>
                        </table>
                    </div>
                    <%
                    }
                    %>
                    <div class="card card-body m-3 p-md-5">
                        <table id="tableMerchant" class="table table-bordered table-hover text-center mx-auto p-auto">
                            <tr scope="row"><td colspan="5"><h3>Merchant:</h3></td></tr>
                            <tr>
                                <td class="lead">Merchant:</td>
                                <td>Little Shop ltd.</td>
                            </tr>
                            <tr>
                                <td class="lead">Address:</td>
                                <td>Zagrebačka ceta 121</td>
                            </tr>
                            <tr>
                                <td class="lead">Post box (PO Box):</td>
                                <td>10360</td>
                            </tr>
                            <tr>
                                <td class="lead">City:</td>
                                <td>Zagreb</td>
                            </tr>
                        </table>
                    </div>
                </div>

                <%
                if (invoice != null) {
                %>
                <div class="col p-3">
                    <div class="card card-body m-3 p-md-5">

                        <div class="card card-body m-3 p-md-5">
                            <table id="tableTransaction" class="table table-bordered table-hover text-center mx-auto p-auto">
                                <tr scope="row"><td colspan="5"><h3>Transaction details</h3></td></tr>
                                <tr scope="row">
                                    <td class="lead">Order id:</td>
                                    <td><b>#<%= invoice.getId()%></b></td>
                                </tr>
                                <tr scope="row">
                                    <td class="lead">Order message:</td>
                                    <td><%= invoice.getMessage() %></td>
                                </tr>
                                <tr scope="row">
                                    <td class="lead">Order total:</td>
                                    <td><%= invoice.getTotalPrice().setScale(2).toString() %> €</td>
                                </tr>
                                <tr scope="row">
                                    <td class="lead">Time of order: </td>
                                    <td><%= invoice.getTimeOfOrder() %></td>
                                </tr>
                                <tr scope="row">
                                    <td class="lead">Paid with: </td>
                                    <td><%= invoice.getPaymentId().getName() %></td>
                                </tr>
                                <tr scope="row">
                                    <td class="lead">Items ordered:</td>
                                    <td><%= order.getProductName() %></td>
                                </tr>
                                <tr scope="row">
                                    <td class="lead">Subtotal:</td>
                                    <td><%= order.getSubtotal()%> €</td>
                                </tr>
                                <tr scope="row">
                                    <td class="lead">Tax:</td>
                                    <td><%= order.getTax()%> €</td>
                                </tr>
                                <tr scope="row">
                                    <td class="lead">Shipping:</td>
                                    <td><%= order.getShipping() %> €</td>
                                </tr>
                                <tr scope="row">
                                    <td class="lead">Total:</td>
                                    <td><%= order.getTotal()%> €</td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
                <%
                }%>

            </div>
            <div class="row">
                <div class="col p-4">
                    <a class="btn btn-lg btn-outline-dark" href="./home">Go back home</a>
                </div>
            </div>
        </section>

        <section id="footer">
            <jsp:include page="../components/footer.jsp"/>
        </section>
    </body>
</html>