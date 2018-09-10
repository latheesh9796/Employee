<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
   <head>
      <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
      <link href='https://fonts.googleapis.com/css?family=Sofia' rel='stylesheet'>
      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
      <link rel="stylesheet" type="text/css" href="./css/Projects.css">
   </head>
   <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
   <script src ="./sortable.js"></script>
   <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
   <script src="https://code.jquery.com/jquery-3.3.1.js"></script>
   <script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
   <script src="https://cdn.datatables.net/buttons/1.5.2/js/dataTables.buttons.min.js"></script>
   <script src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>
   <script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/pdfmake.min.js"></script>
   <script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/vfs_fonts.js"></script>
   <script src="https://cdn.datatables.net/buttons/1.5.2/js/buttons.html5.min.js"></script>
   <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.2/jspdf.min.js"></script>
   <header>
      <jsp:include page="Header.jsp" />
   </header>
   <body>
      <h1> Project Management </h1>
      <div>
         <form action="createProject" method="POST"><button class="w3-btn w3-green left-button">
            <i class="fa fa-plus-square size-16px"></i>
            &ensp;Add Project
            </button>
         </form>
         <c:choose>
            <c:when test="${not empty projects}">
               <button class="w3-btn w3-orange right-button" onclick="javascript:downloadProjects();"><i class="fa fa-download" aria-hidden="true"></i>&nbsp;Download PDF</button>
            </c:when>
         </c:choose>
      <c:choose>
         <c:when test="${addMenu == 'Yes'}">
            <script> 
               window.onload = function() {
               document.getElementById('id01').style.display='block';
               } 
            </script>
         </c:when>
      </c:choose>
         <input id="myInput" type="text" placeholder="Search.."> 
      </div>
      <c:choose>
         <c:when test="${not empty projects}">
            <div id="customers" class="hide">
               <div class="table-responsive" >
                  <table id="tbl" class="table table-hover">
                     <thead>
                        <tr>
                           <th>ID</th>
                           <th>Name</th>
                        </tr>
                     </thead>
                     <tbody>
                        <c:forEach items="${projects}" var="project">
                           <tr>
                              <td>${project.id}</td>
                              <td>${project.name}</td>
                           </tr>
                        </c:forEach>
                     </tbody>
                  </table>
               </div>
            </div>
         </c:when>
         <c:otherwise>
            <img src="./img/NoData.png" alt="No Data" width="257" height="349">
         </c:otherwise>
      </c:choose>
      <c:choose>
         <c:when test="${not empty projects}">
            <div class="w3-container">
               <div class = "scroll-table-400px">
                  <table class="w3-table-all w3-hoverable sortable">
                     <thead>
                        <tr id="table-header">
                           <td>ID</td>
                           <td>Name</td>
                           <td>Team Members</td>
                           <td>View/Restore</td>
                            <td>Modify</td>
                            <td>Delete</td>
                        </tr>
                     </thead>
                     <tbody id="myTable">
                        <c:forEach items="${projects}" var="project">
                           <tr>
                              <td>${project.id}
                              </td>
                              <td>${project.name}</td>
                              <c:if test="${project.status == 'Active'}">
                                 <form action="AddEmployees" method="POST">
                                    <input type="hidden" name="id" value="${project.id}">
                                    <td><button title="Add Employee"name="operation" value="AddMember" class="w3-btn w3-ripple w3-green action">
                                       <i class="fa fa-user-plus"></i></button>
                                    </td>
                                 </form>
                                    <td>
                                 <form action="ViewProject" method="POST">
                                 <input type="hidden" name="id" value="${project.id}">
                                 <button title="View" name="operation" value="View" class="w3-btn w3-ripple w3-orange action"><i class="fa fa-eye fa_custom white"></i></i></button></td></form>
                                 <form action="ModifyProject" method="POST">
                                 <input type="hidden" name="id" value="${project.id}">
                                   <td><button title="Modify" class="w3-btn w3-ripple w3-blue action"><i class="fa fa-edit"></i></button></td><td></form>
                                 <form action="DeleteProject" method="POST">
                                 <input type="hidden" name="id" value="${project.id}">
                                 <button title="Delete" onClick="return confirm('Are you sure you want to delete this project?');" class="w3-btn w3-ripple w3-red action"><i class="fa fa-trash"></i></button></td>                                 </form></td>
                              </c:if>
                              <c:if test="${project.status == 'Inactive'}"> 
                              <td><button class="w3-btn w3-ripple w3-red action" onclick="alert('Restore project to add members in it!');">
                              <i class="fa fa-ban size-22px"></i></i></button></td>
                              <input type="hidden" name="id" value="${project.id}">
                              <i class="fas fa-user-slash"></i>
                              <td><form action="RestoreProject" method="POST">
                              <input type="hidden" name="id" value="${project.id}">
                              <button title="Restore" name="operation" value="Restore" onClick="return confirm('Are you sure you want to restore project ${project.id}?');" class="w3-btn w3-ripple w3-orange action"><i class="fa fa-refresh white"></i></button></form></td><td><button title="Modify" class="w3-btn w3-ripple w3-blue action disabled"><i class="fa fa-edit"></i></button></td><td><button title="Delete"class="w3-btn w3-ripple w3-red action disabled"><i class="fa fa-trash"></i></button></td>
                              
                              </c:if>
                              </form>
                           </tr>
                        </c:forEach>
                     </tbody>
                  </table>
               </div>
            </div>
         </c:when>
         <c:otherwise>
            <img src="./img/NODATAA.png" alt="No Data" width="257" height="349">
         </c:otherwise>
      </c:choose>
      <c:choose>
         <c:when test="${view == 'Yes'}">
            <script type="text/javascript">
               window.onload = function() {
               document.getElementById('id06').style.display='block';
               } 
            </script>
         </c:when>
      </c:choose>
      <c:choose>
         <c:when test="${modifyReq == 'Yes'}">
            <script> 
               window.onload = function() {
               document.getElementById('id04').style.display='block';
               }
            </script>
         </c:when>
      </c:choose>
      <c:choose>
         <c:when test="${addEmployee == 'Yes'}">
            <script> 
               window.onload = function() {
               document.getElementById('id03').style.display='block';
               }
            </script>
         </c:when>
      </c:choose>
      <c:choose>
         <c:when test="${removeEmployee == 'Yes'}">
            <script> 
               window.onload = function() {
               document.getElementById('id05').style.display='block';
               }
            </script>
         </c:when>
      </c:choose>
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
      <div id="id01" class="modal">
            <div class="container modal-content animate">
               <span onclick="document.getElementById('id01').style.display='none'" class="close" title="Close Modal">&times;</span>
               <form:form action="addProject" commandName="project" method="POST">
               <h2> Enter Project Details </h2>
                Name :
                <spring:bind path="project.name">
                <input type="text" name="name"/>
                </spring:bind>
               <input type="submit" class="w3-panel w3-block w3-hover-orange" id="center" value="Add Project">
               </form:form>
            </div>
      </div> 
<div id="id03" class="modal">
         <form class="modal-content animate" action="AddEmployeesToProject" method="POST">
            <div class="container" >
               <span onclick="document.getElementById('id03').style.display='none'" class="close" title="Close Modal">&times;</span>
               <h2>
               Modify Team Members </h1>
               <c:choose>
                  <c:when test="${not empty employees}">
                     <div class="w3-container">
                        <input id="employeeInput" type="text" placeholder="Search.."> 
                     </div>
                     <div class="scroll-table-200px">
                        <table class="w3-table-all w3-hoverable sortable employees" >
                           <thead>
                              <tr id="table-header">
                                 <td>ID</td>
                                 <td>Name</td>
                                 <td>Designation</td>
                                 <td>Rating</td>
                                 <td>Experience</td>
                                 <td>Action</td>
                              </tr>
                           </thead>
                           <tbody id="employeeTable">
                              <c:forEach items="${employees}" var="employee">
                                 <div>
                                    <tr>
                                       <td>${employee.id}</td>
                                       <td>${employee.name}</td>
                                       <td>${employee.designation}</td>
                                       <td>${employee.rating}</td>
                                       <td>${employee.experience}</td>
                                       <td><input type="checkbox" name="select" value="${employee.id}"></td>
                                    </tr>
                                 </div>
                              </c:forEach>
                           </tbody>
                        </table>
                     </div>
            </div>
            </c:when>
            </c:choose>
            <input type="hidden" name="id" value="${projectId}"><br>
            <input type="submit" class="w3-panel w3-block w3-hover-orange " id="center" name="operation" value="Add Employee">
      </div>
      </form>
      </div>



      <div id="id04" class="modal">
            <div class="container modal-content animate">
               <span onclick="document.getElementById('id04').style.display='none'" class="close" title="Close Modal">&times;</span>
               <h2> Modify Project Details </h2>
               <form:form action="ModifyProjectDetails" commandName="project" method="POST">
                Name :
                <spring:bind path="project.name">
                <input type="text" name="name" value="${project.name}"/>
                </spring:bind>
               <input type="submit" class="w3-panel w3-block w3-hover-orange"  id="center" name="operation" value="Update">
                </form:form>
            </div>
      </div>
      <div id="id05" class="modal">
         <form class="modal-content animate" action="RemoveEmployeeFromProject" method="POST">
            <span onclick="document.getElementById('id05').style.display='none'" class="close" title="Close Modal">&times;</span>
            <div class="container" >
               <h2 > Modify Team Members </h2>
               <label for="name"><b>Enter Employee ID </b></label>
               <input type="text" name="employeeId" pattern="\d{1,5}" title="Only digits" required/>
               <input type="hidden" name="id" value="${projectId}"><br>
               <input type="submit" class="w3-panel w3-block w3-hover-orange" id="center" name="operation" value="Remove Employee">
            </div>
         </form>
      </div>
       <div id="id06" class="modal">
         <form class="modal-content animate" action="RemoveEmployeeFromProject" method="POST">
            <div class="container">
               <span onclick="document.getElementById('id06').style.display='none'" class="close" title="Close Modal">&times;</span><br>
         <form action="RemoveEmployeeFromProject" method="post">
         <table class="w3-table-all w3-hoverable sortable" style="width:50%; margin-left:300px;">
         <thead>
         <tr id="table-header">
         <td>Project ID</td>
         <td>Name</td>
         </tr>
         </thead>
         <tbody id="myTable"> 
         <tr>
         <td>${project.id}</td>
         <td>${project.name}</td>
         </tr>
         </tbody>
         </table><br>
         <c:choose>
         <c:when test="${not empty employees}">
         <div class="scroll-table-200px">
         <table class="w3-table-all w3-hoverable sortable">
         <thead>
         <tr id="table-header">
         <td>Employee ID</td>
         <td>Name</td>
         <td>Designation</td>
         <td>Action</td>
         </tr>
         </thead>
         <tbody id="myTable">
         <c:forEach items="${employees}" var="employee">
         <tr>
         <td>${employee.id}</td>
         <td>${employee.name}</td>
         <td>${employee.designation}</td>       
         <td>
         <form action="RemoveEmployeeFromProject" name="operation" method="POST" value="Remove Employee">
         <input type="hidden" name="employeeId" value="${employee.id}">
         <input type="hidden" name="id" value="${project.id}">
         <button title="Remove Employee" onClick="return confirm('Are you sure you want to delete remove Employee ${employee.id} from ${project.name} ?');" name="operation" value="Remove Employee" class="w3-btn w3-ripple w3-red action">
         <i class="fa fa-user-times"></i>
         </button>
         </form>
         </td>
         </tr>
         </c:forEach>
         </tbody>
         </table></div><br>
         </c:when>
         </c:choose>
         </form>
         </div>
      </div>
   </body>
   <footer>
      <jsp:include page="Footer.jsp" />
   </footer>
   <script src ="./js/projects.js"></script>
</html>
