<%-- 
    Document   : products
    Created on : Jan 7, 2022, 2:04:51 PM
    Author     : r3nb0
--%>

<%@page import="entities.Product"%>
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
        <% 
            Collection<ProductCategory> categories = (Collection<ProductCategory>) session.getAttribute("categories");
            Collection<Product> products = (Collection<Product>) session.getAttribute("products");
        %>
        <section id="header">
            <jsp:include page="components/header.jsp"/>
        </section>
        <section id="content">
            <!-- filters for mobile -->
            <div id="filterModal" class="modal fade m-auto" tabindex="-1" role="dialog" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" >Filters:</h5>
                            <div class="close btn btn-sm btn-outline-danger" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true" class="red">&times;</span>
                            </div>
                        </div>
                        <div class="modal-body">
                            <form action="products" method="POST">
                                <div class="form-group p-2">
                                    <h5>Filter by name:</h5>
                                    <input type="search" value="" minlength="1" maxlength="40" autocomplete="true" 
                                           placeholder="eg. Drone" id="filterByName" name="search"
                                           class="form-control form-control-sm"/>
                                </div>

                                <div class="form-group p-2">
                                    <h5>Sort by:</h5>
                                    <div class="btn-group">
                                        <div class="row w-100 mx-2">
                                            <input type="radio" name="sort" value="lh" id="lh" checked class="col-12"/>
                                            <label for="lh" class="col-12">Low to high</label>
                                        </div>
                                        <div class="row w-100 mx-2">
                                            <input type="radio" name="sort" value="hl" id="hl" class="col-12"/>
                                            <label for="hl" class="col-12">High to low</label></input>
                                        </div>
                                        <div class="row w-100 mx-2">
                                            <input type="radio" name="sort" value="az" id="az" class="col-12"/>
                                            <label for="az" class="col-12">A to Z</label></input>
                                        </div>

                                        <div class="row w-100 mx-2">
                                            <input type="radio" name="sort" value="za" id="za" class="col-12"/>
                                            <label for="za" class="col-12">Z to A</label></input>
                                        </div>

                                    </div>
                                    <hr/>
                                </div>

                                <div class="form-group p-2">
                                    <h5>Choose categories:</h5>
                                    <div class="form-group">                      
                                        <label for="filterCategories">Select category:</label>
                                        <select data-live-search="true" multiple="true"
                                                class="form-select form-select-sm" name="categories" id="filterCategories"
                                                onChange="setCategories(this)">
                                            <option id="-1" value="-1">All categories</option>
                                            <%
                                    if (categories != null && categories.size() != 0)
                                    for (ProductCategory cat : categories) {
                                            %>
                                            <option id="<%=cat.getId()%>" value="<%=cat.getId()%>"> <%=cat.getName()%></option>
                                            <%
                                            }
                                            %>
                                        </select>
                                        <p class="form-text">Select one or more categories from list (CTRL + click)</p>
                                    </div>
                                    <hr/>
                                </div>

                                <div class="form-group p-2">
                                    <h5>Price range:</h5>
                                    <div class="form-group">
                                        <div class="input-group mb-3">
                                            <input id="minPrice" name="minPrice" value="1" onchange="setMinPrice(this)" 
                                                   type="number" min="0" max="999999" 
                                                   class="form-control" placeholder="Min" 
                                                   value="0" aria-label="Min"/>
                                            <span class="input-group-text">-</span>
                                            <input id="maxPrice" name="maxPrice" onchange="setMaxPrice(this)"
                                                   type="number" min="1" max="100000" 
                                                   class="form-control" placeholder="Max" 
                                                   value="100000" aria-label="Max"/>
                                        </div>
                                        <p class="form-text">Prices are in Euros (€)</p>
                                    </div>
                                    <hr/>
                                </div>
                                <div class="form-group p-auto m-0 w-100">
                                    <input type="submit" name="action" value="Apply" class="btn btn-lg btn-dark m-1 w-100"/>
                                    <input type="submit" name="action" value="Reset" class="btn btn-lg btn-warning m-1 w-100"/>
                                    <button type="button" class="btn btn-lg btn-outline-danger m-1 w-100" data-dismiss="modal">Close</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>


            <div class="row text-center">
                <!-- modal activator -->
                <div data-toggle="modal" data-target="#filterModal" class="d-block d-md-none floater"
                     onclick="showFilterModal(true)">
                    <i class="material-icons material-icons-outline align-self-center" 
                       style="font-size: xx-large;">filter_alt</i>
                </div>

                <!-- filters for desktop -->
                <div class="col-xs-12 d-none d-md-flex col-md-5 col-lg-3 yellow-bg shadow" style="height: auto;">
                    <form action="products" method="POST" class="py-3 pt-lg-4">
                        <div class="form-group p-2">
                            <h5>Filter by name:</h5>
                            <input type="search" value="" minlength="1" maxlength="40" autocomplete="true" 
                                   placeholder="eg. Drone" id="filterByName" name="search"
                                   class="form-control form-control-sm"/>
                        </div>

                        <div class="form-group p-2">
                            <h5>Sort by:</h5>
                            <div class="btn-group">
                                <div class="row w-100 mx-2">
                                    <input type="radio" name="sort" value="lh" id="lh" checked class="col-12"/>
                                    <label for="lh" class="col-12">Low to high</label>
                                </div>
                                <div class="row w-100 mx-2">
                                    <input type="radio" name="sort" value="hl" id="hl" class="col-12"/>
                                    <label for="hl" class="col-12">High to low</label></input>
                                </div>
                                <div class="row w-100 mx-2">
                                    <input type="radio" name="sort" value="az" id="az" class="col-12"/>
                                    <label for="az" class="col-12">A to Z</label></input>
                                </div>

                                <div class="row w-100 mx-2">
                                    <input type="radio" name="sort" value="za" id="za" class="col-12"/>
                                    <label for="za" class="col-12">Z to A</label></input>
                                </div>

                            </div>
                            <hr/>
                        </div>

                        <div class="form-group p-2">
                            <h5>Choose categories:</h5>
                            <div class="form-group">                      
                                <label for="filterCategories">Select category:</label>
                                <select data-live-search="true" multiple="true"
                                        class="form-select form-select-sm" name="categories" id="filterCategories"
                                        onChange="setCategories(this)">
                                    <option id="-1" value="-1">All categories</option>
                                    <%
                            if (categories != null && categories.size() != 0)
                            for (ProductCategory cat : categories) {
                                    %>
                                    <option id="<%=cat.getId()%>" value="<%=cat.getId()%>"> <%=cat.getName()%></option>
                                    <%
                                    }
                                    %>
                                </select>
                                <p class="form-text">Select one or more categories from list (CTRL + click)</p>
                            </div>
                            <hr/>
                        </div>

                        <div class="form-group p-2">
                            <h5>Price range:</h5>
                            <div class="form-group">
                                <div class="input-group mb-3">
                                    <input id="minPrice" name="minPrice" value="1" onchange="setMinPrice(this)" 
                                           type="number" min="0" max="999999" 
                                           class="form-control" placeholder="Min" 
                                           value="0" aria-label="Min"/>
                                    <span class="input-group-text">-</span>
                                    <input id="maxPrice" name="maxPrice" onchange="setMaxPrice(this)"
                                           type="number" min="1" max="100000" 
                                           class="form-control" placeholder="Max" 
                                           value="100000" aria-label="Max"/>
                                </div>
                                <p class="form-text">Prices are in Euro (EUR / €)</p>
                            </div>
                            <hr/>
                        </div>
                        <div class="form-group p-auto m-0 w-100">
                            <input type="submit" name="action" value="Apply" class="btn btn-lg btn-dark m-1 w-100"/>
                            <input type="submit" name="action" value="Reset" class="btn btn-lg btn-outline-danger m-1 w-100"/>
                        </div>
                    </form>
                </div>

                <!--PRODUCT LIST-->
                <div class="col-xs-12 col-md-7 col-lg-9">
                    <div style="min-height: 75vh;">
                        <div class="m-2 py-5 px-2 row"  id="productList">
                            <%
                            if (products != null && products.size() != 0) {
                            for (Product pro : products) { 
                            %>
                            <jsp:include page="components/productTile.jsp">
                                <jsp:param name="id" value="<%= pro.getId()%>"/>
                                <jsp:param name="name" value="<%= pro.getName()%>"/>
                                <jsp:param name="price" value="<%= pro.getPrice() %>"/>
                                <jsp:param name="description" value="<%= pro.getDescription() %>"/>
                                <jsp:param name="categoryId" value="<%= pro.getProductCategoryId().getId()%>"/>
                                <jsp:param name="image" value="<%= pro.getImage() %>"/>
                            </jsp:include>
                            <%
                                }
                            } else {
                            %>
                            <h2 class="divider">No products</h2>
                            <%
                            }
                            %>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <section id="footer">
        <jsp:include page="components/footer.jsp"/>
    </section>
    <script>
        //<![CDATA[
        var minPrice = 0;
        var maxPrice = 100000;
            

        function setMinPrice(e) {
            if (Number.parseInt(e.value) >= maxPrice) {
                alert('Minimum price(' + e.value + ')cannot be higher than maximum price(' + maxPrice + ')!');
                document.getElementById('minPrice').setAttribute("max", (maxPrice));
                document.getElementById('minPrice').value = (maxPrice - 1);
            } else {
                minPrice = Number.parseInt(e.value);
            }
            console.log(minPrice);
        }

        function setMaxPrice(e) {
            if (Number.parseInt(e.value) <= minPrice) {
                alert('Maximum price(' + e.value + ')cannot be lower than mnimum price(' + minPrice + ')!');
                document.getElementById('maxPrice').setAttribute("min", (minPrice));
                document.getElementById('maxPrice').value = (Number.parseInt(minPrice) + 1);
            } else {
                maxPrice = Number.parseInt(e.value);
            }
            console.log(maxPrice);
        }
//]]>
    </script>
</body>
</html>
