<%@page import="com.paypal.api.payments.Payment"%>
<%@page import="com.paypal.api.payments.ShippingAddress"%>
<%@page import="com.paypal.api.payments.PayerInfo"%>
<%@page import="com.paypal.api.payments.Transaction"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Review cart</title>
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
            <form action="order/execute" method="POST">
                <input type="hidden" name="paymentId" value="${param.paymentId}"/>
                <input type="hidden" name="payerId" value="${param.payerId}"/>


                <div class="row">
                    <div class="col-12 text-center">
                        <h1>You have to confirm you are sure you want to order these items and click "Confirm & Pay" button.</h1>
                        <h1>If you don't, we will not receive your order.</h1>

                    </div>
                    <div class="col p-3">
                        <div class="card m-3">
                            <table id="tablePayer" class="table table-bordered table-hover text-center mx-auto p-auto">
                                <tr><td colspan="3"><h3>Payer details</h3></td></tr>
                                <tr>
                                    <td class="lead">Email:</td>
                                    <td><%= payer.getEmail() %></td>
                                </tr>
                                <tr>
                                    <td class="lead">First name:</td>
                                    <td><%= payer.getFirstName() %></td>
                                </tr>
                                <tr>
                                    <td class="lead">Last name:</td>
                                    <td><%= payer.getLastName() %></td>
                                </tr>
                                <tr>
                                </tr>
                            </table>
                        </div>
                    </div>

                    <div class="col p-3">
                        <div class="card m-3">
                            <table id="tableShipping" class="table table-bordered table-hover text-center mx-auto p-auto">
                                <tr><td colspan="5"><h3>Shipping details</h3></td></tr>
                                <tr>
                                    <td class="lead">Recipients name:</td>                    <td><%= shippingAddress.getRecipientName() %></td>

                                </tr>
                                <tr>
                                    <td class="lead">Street:</td>               
                                    <td><%= shippingAddress.getLine1() %></td>
                                </tr>
                                <tr>
                                    <td class="lead">City:</td>                  
                                    <td><%= shippingAddress.getCity() %></td>
                                </tr>
                                <tr>
                                    <td class="lead">Postal Code (PO Box):</td>   
                                    <td><%= shippingAddress.getPostalCode() %></td>
                                </tr>
                                <tr>
                                    <td class="lead">Country:</td>                   
                                    <td><%= shippingAddress.getCountryCode() %></td>
                                </tr>
                            </table>
                        </div>
                    </div>
                    <div class="col p-3">
                        <div class="card m-3">
                            <table id="tableTransaction" class="table table-bordered table-hover text-center mx-auto p-auto">
                                <tr scope="row"><td colspan="5"><h3>Transaction details</h3></td></tr>
                                <tr scope="row">
                                    <td class="lead">Description:</td>             
                                    <td><%= trans.getDescription() %></td>

                                </tr>
                                <tr scope="row">
                                    <td class="lead">Invoice ID:</td>            
                                    <td><b>#<%= trans.getInvoiceNumber() %></b></td>
                                </tr>
                                <tr scope="row">
                                    <td class="lead">Subtotal:</td>            
                                    <td><%= trans.getAmount().getDetails().getSubtotal() + " " + trans.getAmount().getCurrency() %></td>
                                </tr>
                                <tr scope="row">
                                    <td class="lead">Shipping:</td>       
                                    <td><%= trans.getAmount().getDetails().getShipping() + " " + trans.getAmount().getCurrency() %></td>
                                </tr>
                                <tr scope="row">
                                    <td class="lead">Tax: </td>          
                                    <td><%= trans.getAmount().getDetails().getTax() + " " + trans.getAmount().getCurrency() %></td>
                                </tr>
                                <tr scope="row">
                                    <td class="lead">Total:</td>  
                                    <td><%= trans.getAmount().getTotal() + " " + trans.getAmount().getCurrency() %></td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
                <hr/>
                <div class="row">
                    <div class="col p-4 mx-3 mx-md-5">
                        <input class="btn btn-lg btn-warning" onclick="disableButton(this)" value="Confirm & Pay" type="submit"/>
                    </div>
                </div>
            </form>
        </section>

        <section id="footer">
            <jsp:include page="../components/footer.jsp"/>
        </section>
        
        
        <script>
        //<![CDATA[
        function disableButton(e) {
            e.disable = true;
        }
        //]]>
        </script>
    </body>
</html>
