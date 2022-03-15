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
        Transaction trans = (Transaction) session.getAttribute("transaction");
        PayerInfo payer = (PayerInfo) session.getAttribute("payer");
        ShippingAddress shippingAddress = (ShippingAddress) session.getAttribute("shippingAddress");
        %>
        <section id="header">
            <jsp:include page="../components/header.jsp"/>
        </section>

        <section id="content">
            <div class="w-100 text-center">
                <h1>Payment done! Thank you for shopping with us! =)</h1>
                <h4>Please remember your order/invoice id since it's reference number to your order.</h4>
            </div>
            <input type="hidden" name="paymentId" value="${param.paymentId}"/>
            <input type="hidden" name="payerId" value="${param.payerId}"/>
            <div class="card m-3 p-md-5">
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

            <hr/>
            <%
            if (trans != null) {

            %>

            <table id="tableTransaction" class="table table-bordered table-hover text-center mx-auto p-auto">
                <tr scope="row"><td colspan="5"><h3>Transaction details</h3></td></tr>
                <tr scope="row">
                    <td class="lead">Invoice ID:</td>             
                    <td><b>#<%= trans.getInvoiceNumber()%></b></td>
                </tr>
                <tr scope="row">
                    <td class="lead">Description:</td>             
                    <td><%= trans.getDescription() %></td>
                </tr>
                <tr scope="row">
                    <td class="lead">Subtotal:</td>            
                    <td><%= trans.getAmount().getDetails().getSubtotal() + 
                                " " + trans.getAmount().getCurrency() %></td>
                </tr>
                <tr scope="row">
                    <td class="lead">Shipping:</td>       
                    <td><%= trans.getAmount().getDetails().getShipping() +
                                " " + trans.getAmount().getCurrency() %></td>
                </tr>
                <tr scope="row">
                    <td class="lead">Tax: </td>          
                    <td><%= trans.getAmount().getDetails().getTax() +
                                " " + trans.getAmount().getCurrency() %></td>
                </tr>
                <tr scope="row">
                    <td class="lead">Total:</td>  
                    <td><%= trans.getAmount().getTotal() + 
                                " " + trans.getAmount().getCurrency() %></td>
                </tr>
            </table>
            <%
}
if (payer != null) {

            %>
            <table id="tablePayer" class="table table-bordered table-hover text-center mx-auto p-auto">
                <tr><td colspan="3"><h3>Payer details</h3></td></tr>
                <tr>
                    <td class="lead">Email:</td>
                    <td><%= payer.getEmail() %></td>
                </tr>
                <tr>
                    <td class="lead">Full Name:</td>
                    <td><%= payer.getFirstName()  + " " +  payer.getMiddleName() + " " + payer.getLastName() %></td>
                </tr>
                <tr>
                    <td class="lead">Phone:</td>
                    <td><%= payer.getPhone()%></td>
                </tr>
                <tr>
                </tr>
            </table>
            <%
}
            %>
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