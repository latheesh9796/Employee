<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
   <head>
      <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
      <link href='https://fonts.googleapis.com/css?family=Sofia' rel='stylesheet'>
      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
      <link rel="stylesheet" type="text/css" href="./css/Projects.css">
      <link rel="stylesheet" type="text/css" href="./css/index.css">
   </head>
   <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
   <script src="https://www.kryogenix.org/code/browser/sorttable/sorttable.js"></script>
   <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>  
   <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
   <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
   <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
   <body>
      <div class="container" id="login">
         <h1 id="center"> Login </h1>
         <br>
         <form class="form-horizontal" action="UserManagement" method="POST">
            <div class="form-group">
               <label class="control-label col-sm-2" for="email">Email:</label>
               <div class="col-sm-10">
                  <input type="email" class="form-control" id="email" placeholder="Enter email" name="email" required>
               </div>
            </div>
            <div class="form-group">
               <label class="control-label col-sm-2" for="pwd">Password:</label>
               <div class="col-sm-10">          
                  <input type="password" class="form-control" id="pwd" placeholder="Enter password" name="password" required>
               </div>
            </div>
            <div class="form-group">
               <div class="col-sm-offset-2 col-sm-10">
                  <button type="submit" class="btn btn-default" name="operation" value="Login">Submit</button>
                  <button type="button" class="btn btn-default" onclick="showRegister()">Create New Account</button>
               </div>
            </div>
         </form>
      </div>
      <div class="container" id="register" style="display:none">
         <h1 id="center"> Sign up </h1>
         <br>
         <form class="form-horizontal" action="UserManagement" method="POST">
            <div class="form-group">
               <label class="control-label col-sm-2" for="email">Email:</label>
               <div class="col-sm-10">
                  <input type="email" class="form-control" id="email" placeholder="Enter email" name="email" required>
               </div>
            </div>
            <div class="form-group">
               <label class="control-label col-sm-2" for="pwd">Password:</label>
               <div class="col-sm-10">          
                  <input type="password" class="form-control" id="pwd" placeholder="Enter password" name="password" required>
               </div>
            </div>
            <div class="form-group">
               <label class="control-label col-sm-2" for="pwd">Confirm Password:</label>
               <div class="col-sm-10">          
                  <input type="password" class="form-control" id="pwd" placeholder="Enter password" name="confirmPassword" required>
               </div>
            </div>
            <div class="form-group">
               <div class="col-sm-offset-2 col-sm-10">
                  <button type="submit" class="btn btn-default" name="operation" value="Sign Up">Register</button>
                  <button type="button" class="btn btn-default" onclick="showLogin()">I already have an account</button>
               </div>
            </div>
         </form>
      </div>
      <c:choose>
         <c:when test="${not empty message}">
            <div id="positive">${message}</div>
            <script type="text/javascript">window.onload = function() {
               var x = document.getElementById("positive");
               x.className = "show";
               setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
               }  
            </script>
         </c:when>
      </c:choose>
      <c:choose>
         <c:when test="${not empty failmessage}">
            <div id="negative">${failmessage}</div>
            <script type="text/javascript">window.onload = function() {
               var x = document.getElementById("negative");
               x.className = "show";
               setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
               }  
            </script>
         </c:when>
      </c:choose>
   </body>
   <footer>
      <jsp:include page="Footer.jsp" />
   </footer>
   <script>
      function showRegister() {
          document.getElementById("register").style.opacity = "1";
          document.getElementById('register').style.pointerEvents = 'auto';
          document.getElementById("login").style.opacity = "0.15";
          document.getElementById('login').style.pointerEvents = 'none';
          var x = document.getElementById("register");
          if (x.style.display === "none") {
              x.style.display = "block";
          }
      }
      function showLogin() {
          document.getElementById("login").style.opacity = "1";
          document.getElementById('login').style.pointerEvents = 'auto';
          document.getElementById("register").style.opacity = "0.15";
          document.getElementById('register').style.pointerEvents = 'none';
          var x = document.getElementById("login");
          if (x.style.display === "none") {
              x.style.display = "block";
          }
      }
   </script>
</html>
