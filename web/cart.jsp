<%-- 
    Document   : cart
    Created on : Jan 19, 2022, 6:50:43 PM
    Author     : r3nb0
--%>

<%@page import="services.IpService"%>
<%@page import="bl.Manager"%>
<%@page import="entities.PaymentGateway"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="models.UserModel"%>
<%@page import="models.CartModel"%>
<%@page import="models.CartItem"%>
<%@page import="java.util.Collection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Your Cart</title>
    </head>
    <body>
        <%
            CartModel cart = null;
            cart = (CartModel) session.getAttribute("cart");
            UserModel user = (UserModel) session.getAttribute("user");
            Collection<PaymentGateway> gateways = (Collection<PaymentGateway>) session.getAttribute("gateways");
            %>
        <section id="header">
            <jsp:include page="./components/header.jsp"/>
        </section>
        <section id="content">
            <div class="row m-auto px-auto py-3 h-100">
                <div class="col-xs-12 col-md-5 order-xs-2 order-md-1 items-align-center align-self-center my-5">
                    <div class="card shadow my-3">
                        <form action="order" method="POST">
                            <div class="card-header">
                                <p class="lead">Checkout here</p>
                                <hr/>
                            </div>
                            <div class="card-body text-center">
                                <%
                                if (user != null &&
                                user.isLogged() &&
                                cart != null && 
                                cart.getItems().size() > 0 &&
                                cart.getTotal().intValue() != 0) {
                                %>
                                <div class="input-group mb-3">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text">Username:</span>
                                    </div>
                                    <input type="text" disabled class="input-group-text form-control" 
                                           value="<%= user.getUsername() %>" />
                                </div>
                                <div class="input-group mb-3">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text">Email:</span>
                                    </div>
                                    <input type="email" disabled class="input-group-text form-control" 
                                           value="<%= user.getEmail() %>" />
                                </div>
                                <div class="input-group mb-3">
                                    <div class="input-group-prepend">
                                        <span class="input-group-text">Order message:</span>
                                    </div>
                                    <input type="text" min="0" max="2500" name="message" class="input-group-append form-control" 
                                           value="" placeholder="My message to the seller" />
                                </div>
                                <%
                                } else {
                                %>
                                <h4 style="color: red;">Either your cart is empty or you are not logged in!</h4>
                                <h5>${error}</h5>
                                <%
                                }
                                %>

                                <!--payment-->
                                <div class="form-group row justify-content-between">
                                    <p class="lead col-12">Pay via:</p>
                                    <% 
                                    if (gateways != null) {
                                        boolean first = true;
                                        for (PaymentGateway pg : gateways) {
                                    %>
                                    <div class="form-check form-check-inline col-xs-12 col-md-4 align-items-center">
                                        <input class="form-check-input d-flex" <%= first?"checked":"" %> required="true" 
                                               type="radio" name="payment" value="<%= pg.getName() %>" 
                                               id="<%= pg.getName() %>"/>
                                        <label class="form-check-label noselect card rounded shadow-sm d-flex" for="<%= pg.getName() %>">
                                            <div>
                                                <p class="lead"><%= pg.getName() %></p>
                                                <small class="small"><%= pg.getDescription() %></small>
                                            </div>
                                        </label>
                                    </div>
                                    <%
                                        first = false;
                                        }
                                    }
                                    %>
                                </div>
                            </div>
                            <div class="card-footer text-center"> 
                                <div class="form-group d-flex justify-content-center">
                                    <button type="submit" <%= (cart==null || user==null || cart.getItems().size() == 0 || gateways == null)?"disabled":"" %> 
                                            name="action" value="pay" class="btn btn-lg btn-outline-primary d-flex">
                                        <span>Pay & Order</span>
                                        <i class="material-icons material-icons-outline align-self-center" style="font-size: 1.3rem;">credit_card</i>
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>

                <div class="col-xs-12 col-md-7 order-xs-1 order-md-2 items-align-center align-self-center">
                    <div class="card m-3 shadow align-self-center my-auto">
                        <div class="card-header p-3">
                            <h4>Review your cart:</h4>
                        </div>
                        <div class="card-body p-3">
                            <ul class="list-group mb-3" style="overflow-y: scroll; min-height: 40vh;">
                                <% 
                                    if (cart != null && !cart.getItems().isEmpty()) {
                                    int counter = 1;
                                        for (CartItem item : cart.getItems()) {
                                %>
                                <li class="list-group-item">
                                    <form id="cartItem<%= item.getId() %>Form" action="cart" method="POST">
                                        <input type="hidden" name="productId" value="<%= item.getId() %>"/>
                                        <div class="d-flex justify-content-between">
                                            <h5><%= counter %>.</h5>  
                                            <a href="./product/<%= item.getId() %>"><h5><%= item.getItemName() %></h5></a>

                                            <div class="d-flex justify-content-center">
                                                <div class="input-group">
                                                    <div class="input-group-prepend">
                                                        <snap class="input-group-text"> pcs. </snap>
                                                    </div>
                                                    <input type="number" min="1" placeholder="1" class="form-control" 
                                                           required="true" name="amount" value="<%= item.getAmount() %>"/>
                                                    <div class="input-group-append">
                                                        <button type="submit" class="btn btn-outline-success" 
                                                                name="action" value="apply">
                                                            <i class="material-icons material-icons-outline align-self-center" style="font-size: 1.3rem;">check</i>
                                                        </button>
                                                        <button type="submit" class="btn btn-outline-danger" 
                                                                name="action" value="remove">
                                                            <i class="material-icons material-icons-outline align-self-center" style="font-size: 1.3rem;">clear</i>
                                                        </button>
                                                    </div>
                                                </div>
                                            </div>
                                            <h5><%= item.getPrice().toString().substring(0, item.getPrice().toString().indexOf(".") + 3) %>€ (per piece)</h5>
                                    </form>
                                </li>
                                <hr/>
                                <%
                                    counter++;
                                }
                                } else {
                                %>
                                <li class="list-group-item d-flex justify-content-between lh-condensed">
                                    <div>
                                        <h6 class="my-0">Cart is empty!</h6>
                                        <small class="text-muted">No items in your cart. 
                                            First you have to add something to your shopping cart to be able to continue to checkout!</small>
                                    </div>
                                    <span class="text-muted">0kn</span>
                                </li>
                                <%
                                    }
                                %>

                            </ul>
                            <div class="list-group-item d-flex justify-content-end">
                                <div>
                                    <h4> Shipping: +10.00 € </h4>
                                <h4>Total: <%= cart != null?cart.getTotal().setScale(2, BigDecimal.ROUND_HALF_EVEN).add(new BigDecimal("10")):"0" %> €</h4>
                                </div>
                            </div>
                        </div>
                        <div class="card-footer d-flex justify-content-around text-center p-3">
                            <a href="./products" class="btn btn-outline-primary d-flex">
                                <span>Continue shopping</span>
                                <i class="material-icons material-icons-outline align-self-center" style="font-size: 1.3rem;">list_alt</i>
                            </a>
                            <form id="clearCartForm" action="cart" method="POST">
                                <input type="hidden" name="productId" value="0"/>
                                <input type="hidden" name="amount" value="0"/>
                                <button type="submit" name="action" value="clear" class="btn btn-outline-danger d-flex">
                                    <span>Empty Cart</span>
                                    <i class="material-icons material-icons-outline align-self-center" style="font-size: 1.3rem;">delete</i>
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <section id="footer">
        <jsp:include page="./components/footer.jsp"/>
    </section>
</body>
</html>
