<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
   <head>
      <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
      <link href='https://fonts.googleapis.com/css?family=Sofia' rel='stylesheet'>
      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
      <link rel="stylesheet" type="text/css" href="./css/Clients.css">
   </head>
   <header>
      <jsp:include page="Header.jsp" />
   </header>
   <body>
      <c:choose>
         <c:when test="${addProject == 'Yes'}">
            <script> 
               window.onload = function() {
               document.getElementById('id03').style.display='block';
               }
            </script>
         </c:when>
      </c:choose>
   <div id="id03" class="modal">
            <div class="container modal-content animate">
               <span onclick="document.getElementById('id03').style.display='none'" class="close" title="Close Modal">&times;</span>
               <c:choose>
                  <c:when test="${not empty projects}">
                     <h2> Assign Projects to Client </h2>
                     <div class="w3-container">
                        <form action="addProjectsToClient" method="POST">
                        <input id="projectInput" type="text" placeholder="Search.."> 
                     <div class="scroll-table-200px">
                        <table class="w3-table-all w3-hoverable sortable projects">
                           <thead>
                              <tr id="table-header">
                                 <td>ID</td>
                                 <td>Name</td>
                                 <td>Action</td>
                              </tr>
                           </thead>
                           <tbody id="projectTable">
                              <c:forEach items="${projects}" var="project">
                                 <tr>
                                    <td>${project.id}
                                    </td>
                                    <td>${project.name}</td>
                                    <td><input type="checkbox" name="select" value="${project.id}"></td>
                                 </tr>
                              </c:forEach>
                           </tbody>
                        </table>
                     </div>
                     <input type="hidden" name="id" value="${clientId}"><br>
                 <input type="submit" class="w3-panel w3-block w3-hover-orange " id="center" name="operation" value="Add Project"></div>
            </form>
            </c:when>
            <c:otherwise>
            <img src="./img/NODATAA.png" alt="No Data" width="257" height="349">
            </c:otherwise>
            </c:choose>

            </div>
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
      <h1> Client Management</h1>
      <div>         <form action="createClient" method="POST"><button name="operation" value="Add Client" class="w3-btn w3-green left-button">
            <i class="fa fa-plus-square size-16px"></i>
            &ensp;Add Client
            </button>
         </form>
         <c:choose>
            <c:when test="${not empty clients}">
               <button class="w3-btn w3-orange right-button" onclick="javascript:downloadClients();"><i class="fa fa-download" aria-hidden="true"></i>&nbsp;Download PDF</button>
            </c:when>
         </c:choose>
         <c:choose>
            <c:when test="${not empty clients}">
               <div id="customers" class="hide">
                  <div class="table-responsive" >
                     <table id="tbl" class="table table-hover">
                        <thead>
                           <tr>
                              <th>ID</th>
                              <th>Name</th>
                              <th>Description</th>
                           </tr>
                        </thead>
                        <tbody>
                           <c:forEach items="${clients}" var="client">
                              <tr>
                                 <td>${client.id}</td>
                                 <td>${client.name}</td>
                                 <td>${client.description}</td>
                              </tr>
                           </c:forEach>
                        </tbody>
                     </table>
                  </div>
               </div>
            </c:when>
            <c:otherwise>
               <img src="NoData.png" alt="No Data" width="257" height="349">
            </c:otherwise>
         </c:choose>
         <input id="myInput" type="text" placeholder="Search..">
      </div>
      <c:choose>
         <c:when test="${not empty clients}">
            <div class="w3-container">
               <div class="scroll-table-400px">
                  <table class="w3-table-all w3-hoverable sortable">
                     <thead>
                        <tr id="table-header">
                           <td>ID</td>
                           <td>Name</td>
                           <td>Description</td>
                           <td>Projects</td>
                           <td>View/Restore</td>
                            <td>Modify</td>
                            <td>Delete</td>
                        </tr>
                     </thead>
                     <tbody id="myTable">
                        <c:forEach items="${clients}" var="client">
                           <tr>
                              <td>${client.id}
                              </td>
                              <td>${client.name}</td>
                              <td>${client.description}</td>
                              <c:choose>
                                 <c:when test="${client.status == 'Active'}">
                                    <form action="addProjects" method="POST">
                                       <input type="hidden" name="id" value="${client.id}">
                                       <td><button title="Add Project"name="operation" value="addProject" class="w3-btn w3-ripple w3-green action">
                                          <i class="fa fa-user-plus"></i></button>
                                       </td>
                                       <td></form>
                                    <form action="ViewClient" method="POST">
                                    <input type="hidden" name="id" value="${client.id}">
                                    <div>
                                    <button title="View" name="operation" value="View" class="w3-btn w3-ripple w3-orange action"><i class="fa fa-eye fa_custom white"></i></button></form></td><td></form>
                                    <form action="ModifyClient" method="POST">
                                    <input type="hidden" name="id" value="${client.id}">
                                    <button title="Modify"name="operation" value="Search" class="w3-btn w3-ripple w3-blue action"><i class="fa fa-edit"></i></button></form></td><td>
                                    <form action="DeleteClient" method="POST">
                                    <input type="hidden" name="id" value="${client.id}">
                                    <button title="Delete" name="operation" value="Delete" onClick="return confirm('Are you sure you want to delete this client?');" class="w3-btn w3-ripple w3-red action"><i class="fa fa-trash"></i></button></form>
                                    </div>
                                    </form></td>
                                 </c:when>
                                 <c:otherwise>
                                    <td><button class="w3-btn w3-ripple w3-red action" onclick="alert('Restore client to add project in it!');"><i class="fa fa-ban size-22px"></i></i></button></td>
                                    <input type="hidden" name="id" value="${client.id}">
                                    <i class="fas fa-user-slash"></i>
                                    <td>
                                       <form action="RestoreClient" method="POST">
                                          <input type="hidden" name="id" value="${client.id}">
                                          <button title="Restore" name="operation" value="Restore" onClick="return confirm('Are you sure you want to restore client ${client.id}?');" class="w3-btn w3-ripple w3-orange action"><i class="fa fa-refresh style white"></i></button>
                                    </td><td><button title="Modify" class="w3-btn w3-ripple w3-blue action disabled"><i class="fa fa-edit"></i></button></td><td><button title="Delete"class="w3-btn w3-ripple w3-red action disabled"><i class="fa fa-trash"></i></button></td>
                                    </form>
                                 </c:otherwise>
                              </c:choose>
                           </tr>
                        </c:forEach>
                     </tbody>
                  </table>
               </div>
            </div>
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
      <c:choose>
         <c:when test="${view == 'Yes'}">
            <script type="text/javascript">
               window.onload = function() {
               document.getElementById('id06').style.display='block';
               } 
            </script>
         </c:when>
      </c:choose>
  <div id="id01" class="modal">
            <div class="container modal-content animate">
               <span onclick="document.getElementById('id01').style.display='none'" class="close" title="Close Modal">&times;</span>
               <h2> Enter Client Details </h2>
                    <form:form action="addClient" commandName="client" method="POST">
                                Name :
                                <spring:bind path="client.name">
                                <input type="text" name="name"/>
                                </spring:bind>
                                Description :
                                <spring:bind path="client.description">
                                <input type="text" name="description"/>
                                </spring:bind><br>
                            <c:forEach items="${client.listOfAddresses}" varStatus="status">
                            Address :
                            <form:input path="listOfAddresses[${status.index}].address" />
                            State :
                            <form:input path="listOfAddresses[${status.index}].state" />
                            Country :
                            <form:input path="listOfAddresses[${status.index}].country" /><br><br>
                            </c:forEach>
                            <form:button style="width:100%">Submit</form:button>    
                            </form:form>
                    </div>
                </div>




       


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
    

      <div id="id06" class="modal">
            <div class="container modal-content animate">
               <span onclick="document.getElementById('id06').style.display='none'" class="close" title="Close Modal">&times;</span><br>
               <table class="w3-table-all w3-hoverable sortable align-left-300" id="myTable" >
                  <thead>
                     <tr id="table-header">
                        <td>Client ID</td>
                        <td>Name</td>
                        <td>Description</td>
                     </tr>
                  </thead>
                  <tbody>
                     <tr>
                        <td>${client.id}</td>
                        <td>${client.name}</td>
                        <td>${client.description}</td>
                     </tr>
                  </tbody>
               </table>
               <br>
               <c:choose>
                  <c:when test="${not empty projects}">
                     <div class="scroll-table-200px">
                        <table class="w3-table-all w3-hoverable sortable" style="width:70%; margin-left:165px;" id="myTable">
                           <thead>
                              <tr  id="table-header">
                                 <td>Project ID</td>
                                 <td>Name</td>
                                 <td>Action</td>
                              </tr>
                           </thead>
                           <tbody>
                              <c:forEach items="${projects}" var="project">
                                 <tr>
                                    <td>${project.id}</td>
                                    <td>${project.name}</td>
                                    <td>
         <form action="RemoveProjectFromClient" name="operation" value="Remove Project" method="post">
         <input type="hidden" name="projectId" value="${project.id}">  
         <input type="hidden" name="clientId" value="${client.id}">     
         <button title="Remove Project" onClick="return confirm('Are you sure you want to delete Project ${project.id} from ${client.name} ?');" name="operation" value="Remove Project" class="w3-btn w3-ripple w3-red action">
         <i class="fa fa-user-times"></i>
         </button>
         </form></td>
         </tr>
         </c:forEach>
         </tbody>
         </table></div><br>
         </c:when>
         </c:choose>
         </div>
      </div>
   </body>
   <footer>
      <jsp:include page="Footer.jsp" />
   </footer>
   <script src ="./js/clients.js"></script>
</html>
