<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>About us</title>
    </head>
    <body>
        <section id="header">
            <jsp:include page="./components/header.jsp"/>
        </section>

        
        <section id="about">
             <div class="row text-center text-uppercase divider">
                 <div class="m-auto p-auto">
                     <h3 class="text-white">This is our story...</h3>
                 </div>
             </div>
 
             <div class="row text-center dark-blue-bg p-5" style="height: 50vh;">
                 <div class="col-xs-12 col-md-6 dark-blue-bg items-align-center d-flex" style="color: #fefefe;">
                     <h3 class="mx-auto my-3 p-5 h-100" style="transform: rotateZ(-15deg);">Simple.</h3>
                 </div>
                 <div class="col-xs-12 col-md-6  align-items-center d-flex" style="color: white;">
                     <p class="lead text-capitalize w-75 font-weight-bold lh-medium m-auto p-auto">
                         New  market leading company specialized in promoting, selling products, solving problems and helping to save the environment. It's a well-known name as our main product line and we're proud of it.
                     </p>
                 </div>
             </div>
             <div class="row text-center teal-bg" style="height: 50vh;">
                 <div class="col-xs-12 col-md-6 teal-bg align-items-center d-flex">
                     <p class="lead text-capitalize w-75 font-weight-bold lh-medium m-auto p-auto">
                         We have a team of highly skilled management experts to help us produce products in the US in an innovative way that we can't produce anywhere else. We have the knowledge and focus to create top shelf products in the marketplace that are truly innovative and are a true success in any product segment.
                     </p>
                 </div>
                 <div class="col-xs-12 col-md-6 teal-bg align-items-center d-flex" style="color: #333;">
                     <h3 class="mx-auto my-3 p-5 h-100" style="transform: rotateZ(15deg);">Innovative.</h3>
                 </div>
             </div>
 
             <div class="row text-center light-blue-bg" style="height: 50vh;">
                 <div class="col-xs-12 col-md-6 light-blue-bg align-items-center d-flex" style="color: #333;">
                     <h3 class="mx-auto my-3 p-5 h-100" style="transform: rotateZ(-15deg);">Effective.</h3>
                 </div>
                 <div class="col-xs-12 col-md-6 light-blue-bg align-items-center d-flex" style="color: #333;">
                     <p class="lead text-capitalize w-75 font-weight-bold lh-medium m-auto p-auto">
                         A team of dedicated and dedicated employees helps our team succeed through product design, development and delivery. They are our most valued assets and the highest-quality employees.
                     </p>
                 </div>
             </div>
             <div class="row text-center text-uppercase divider">
                 <h5 class="m-auto p-auto text-white">Thank you for staying with us on this wonderful journey!</h5>
             </div>
         </section>
        
        
        <section id="header">
            <jsp:include page="./components/footer.jsp"/>
        </section>

    </body>
</html>
