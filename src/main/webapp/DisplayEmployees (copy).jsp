<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
   <head>
      <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
      <link href='https://fonts.googleapis.com/css?family=Sofia' rel='stylesheet'>
      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
      <link rel="stylesheet" type="text/css" href="./css/Employees.css">
   </head>
   <header>
      <jsp:include page="Header.jsp" />
   </header>
   <body>
      <h1>Employee Management</h1>
      <div> 
         <form action="getDateRestriction" method="POST"><button name="operation" value="Add Employee" class="w3-btn w3-green left-button">
            <i class="fa fa-plus-square size-16px"></i>
            &ensp;Add Employee
            </button>
         </form>
         <c:choose>
            <c:when test="${not empty employees}">
               <button class="w3-btn w3-orange right-button" onclick="javascript:downloadEmployees();"><i class="fa fa-download" aria-hidden="true"></i>&nbsp;Download PDF</button>
            </c:when>
         </c:choose>
         <input id="myInput" class="margin-top-14px" type="text" placeholder="Search..">
      </div>
      <c:choose>
         <c:when test="${not empty employees}">
            <div id="customers" class="hide">
               <div class="table-responsive" >
                  <table id="tbl" class="table table-hover">
                     <thead>
                        <tr>
                           <th>ID</th>
                           <th>Name</th>
                           <th>Email</th>
                           <th>Age</th>
                           <th>Experience</th>
                           <th>Designation</th>
                           <th>Rating</th>
                        </tr>
                     </thead>
                     <tbody>
                        <c:forEach items="${employees}" var="employee">
                           <tr>
                              <td>${employee.id}</td>
                              <td>${employee.name}</td>
                              <td>${employee.email}</td>
                              <td>${employee.age}</td>
                              <td>${employee.experience}</td>
                              <td>${employee.designation}</td>
                              <td>${employee.rating}</td>
                           </tr>
                        </c:forEach>
                     </tbody>
                  </table>
               </div>
            </div>
         </c:when>
      </c:choose>
      <c:choose>
         <c:when test="${not empty employees}">
            <div class="w3-container">
               <div class="scroll-table-400px">
                  <table class="w3-table-all w3-hoverable sortable">
                     <thead>
                        <tr id="table-header">
                           <td>ID</td>
                           <td>Name</td>
                           <td>Email</td>
                           <td>Designation</td>
                           <td>Age</td>
                           <td>Date Of Birth</td>
                           <td>Rating</td>
                           <td>Date of Joining</td>
                           <td>Experience</td>
                           <td>View/Restore</td>
                            <td>Modify</td>
                            <td>Delete</td>
                        </tr>
                     </thead>
                     <tbody id="myTable">
                        <c:forEach items="${employees}" var="employee">
                           <tr>
                              <td>${employee.id}
                              </td>
                              <td>${employee.name}</td>
                              <td>${employee.email}</td>
                              <td>${employee.designation}</td>
                              <td>${employee.age}</td>
                              <td>${employee.dob}</td>
                              <td>${employee.rating}</td>
                              <td>${employee.dateOfJoining}</td>
                              <td>${employee.experience}</td>
                                <c:if test="${employee.status == 'Active'}">
                              <td>
                                    <form action="ViewEmployeeDetails" method="POST">
                                       <input type="hidden" name="id" value="${employee.id}">
                                       <button title="View" name="operation" value="View" class="w3-btn w3-ripple w3-orange action"><i class="fa fa-eye fa_custom white"></i></button></form></td><td>
                                    <form action="EditEmployeeDetails" method="POST">
                                       <input type="hidden" name="id" value="${employee.id}">
                                       <button title="Modify" name="operation" value="Search" class="w3-btn w3-ripple w3-blue action"><i class="fa fa-edit"></i></button></form></td><td>
                                    <form action="DeleteEmployee" method="POST">
                                       <input type="hidden" name="id" value="${employee.id}">
                                       <button title="Delete" name="operation" value="Delete" onClick="return confirm('Are you sure you want to delete employee ?');" class="w3-btn w3-ripple w3-red action"><i class="fa fa-trash"></i></button>
                                    </form></td>
                                 </c:if>
                                 <c:if test="${employee.status == 'Inactive'}">
                                    <input type="hidden" name="id" value="${employee.id}"><td>
                                    <form action="RestoreEmployee" method="POST">
                                       <input type="hidden" name="id" value="${employee.id}">
                                       <button title="Restore" name="operation" value="Restore" onClick="return confirm('Are you sure you want to restore employee ${employee.id}?');" class="w3-btn w3-ripple w3-orange"><i class="fa fa-refresh white"></i></button></form></td><td><button title="Modify" class="w3-btn w3-ripple w3-blue action disabled"><i class="fa fa-edit"></i></button></td><td><button title="Delete"class="w3-btn w3-ripple w3-red action disabled"><i class="fa fa-trash"></i></button></td>
                                 </c:if>
                              </td>
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
         <c:when test="${addMenu == 'Yes'}">
            <script> 
               window.onload = function() {
               document.getElementById('id03').style.display='block';
               } 
            </script>
         </c:when>
      </c:choose>
      <div id="id03" class="modal">
         <div class="modal-content animate">
            <span onclick="document.getElementById('id03').style.display='none'" class="close" title="Close Modal">&times;</span>
            <div class="container" id="unscroll">
               <h2 class="center-sofia"> Enter Employee Details </h2>
               <form:form action="addEmployee" commandName="employee" method="POST">
            <div class="row"><div class="col-sm-3">
                                Name :
                                <spring:bind path="employee.name">
                                <input type="text" name="name"/>
                                </spring:bind>
                                Designation :
                                <spring:bind path="employee.designation">
                                <input type="text" name="designation"/>
                                </spring:bind>
                                &nbsp; Email :&nbsp;
                                <spring:bind path="employee.email">
                                <input type="email" name="email"/>
                                </spring:bind>
                            </div><br>
                            <div class="col-sm-3">
                                DOB : &nbsp;
                                <spring:bind path="employee.dob">
                                <input type="date" name="dob" required/>
                                </spring:bind>
                                &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;DOJ :
                                <spring:bind path="employee.dateOfJoining">
                                <input type="date" name="dateOfJoining" max="${maxDOJ}" required/>
                                </spring:bind>
                                Rating :
                                <spring:bind path="employee.rating">
                                <input type="number" name="rating" min="1" max="5"/><br><br>
                                </spring:bind>
                            </div><br>
                            <c:forEach items="${employee.listOfAddresses}" varStatus="status">
                        <c:choose>
                             <c:when test="${status.index == '1'}"> 
                                <b>Permanent address</b><br>
                             </c:when>
                          </c:choose>
                        <c:choose>
                             <c:when test="${status.index == '0'}"> 
                                <b>Present address</b><br>
                             </c:when>
                          </c:choose>
                            Address :
                            <form:input path="listOfAddresses[${status.index}].address" />
                            &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;State :
                            <form:input path="listOfAddresses[${status.index}].state" />
                            &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;Country :
                            <form:input path="listOfAddresses[${status.index}].country" /><br><br>
                            </c:forEach>

                            <form:button style="width:100%">Submit</form:button>
                    </div>
                </div>
               </form:form>
      </div>
    </div>

      <c:choose>
         <c:when test="${View == 'Yes'}">
            <script> 
               window.onload = function() {
               document.getElementById('id04').style.display='block';
               } 
            </script>
         </c:when>
      </c:choose>
      <c:choose>
         <c:when test="${modifyReq == 'Yes'}">
            <script> 
               window.onload = function() {
               document.getElementById('id00').style.display='block';
               } 
            </script>
         </c:when>
      </c:choose>
         <div id="id00" class="modal">
         <div class="modal-content animate">
            <span onclick="document.getElementById('id00').style.display='none'" class="close" title="Close Modal">&times;</span>
            <div class="container" id="unscroll">
               <h2 class="center-sofia"> Enter Employee Details </h2>
               <form:form action="addEmployee" commandName="employee" method="POST">
            <div class="row"><div class="col-sm-3">
                                Name :
                                <spring:bind path="employee.name">
                                <input type="text" value="${employee.name}"  value= name="name"/>
                                </spring:bind>
                                Designation :
                                <spring:bind path="employee.designation">
                                <input type="text" value="${employee.designation}"  name="designation"/>
                                </spring:bind>
                                &nbsp; Email :&nbsp;
                                <spring:bind path="employee.email">
                                <input type="email" value="${employee.email}"  name="email"/>
                                </spring:bind>
                            </div><br>
                            <div class="col-sm-3">
                                DOB : &nbsp;
                                <spring:bind path="employee.dob">
                                <input type="date" value="${employee.dob}"  name="dob" required/>
                                </spring:bind>
                                &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;DOJ :
                                <spring:bind path="employee.dateOfJoining">
                                <input type="date" value="${employee.dateOfJoining}"  name="dateOfJoining" max="${maxDOJ}" required/>
                                </spring:bind>
                                Rating :
                                <spring:bind path="employee.rating">
                                <input type="number" value="${employee.rating}"  name="rating" min="1" max="5"/><br><br>
                                </spring:bind>
                                                        </div><br>
                            <c:forEach items="${employee.listOfAddresses}" varStatus="status" var="address">
                        <c:choose>
                             <c:when test="${status.index == '1'}"> 
                                <b>Permanent address</b><br>
                             </c:when>
                          </c:choose>
                        <c:choose>
                             <c:when test="${status.index == '0'}"> 
                                <b>Present address</b><br>
                             </c:when>
                          </c:choose>
                            Address :
                            <form:input path="listOfAddresses[${status.index}].address" value="${address.address}" />
                            &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;State :
                            <form:input path="listOfAddresses[${status.index}].state" value="${address.state}"/>
                            &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;Country :
                            <form:input path="listOfAddresses[${status.index}].country" value="${address.country}"/><br><br>
                            </c:forEach>

                            <form:button style="width:100%">Submit</form:button>
                    </div>
                </div>
               </form:form>
      </div>
    </div>
      <div id="id04" class="modal">
         <form class="modal-content animate" action="EmployeeManagement" method="POST">
            <span onclick="document.getElementById('id04').style.display='none'" class="close" title="Close Modal">&times;</span>
            <div class="container">
               <br>
               <table class="w3-table-all w3-hoverable sortable">
                  <thead>
                     <tr id="table-header">
                        <td>ID</td>
                        <td>Name</td>
                        <td>Email</td>
                        <td>Designation</td>
                        <td>Age</td>
                        <td>Date Of Birth</td>
                        <td>Rating</td>
                        <td>Date of Joining</td>
                        <td>Experience</td>
                     </tr>
                  </thead>
                  <tbody>
                     <tr>
                        <td>${employee.id}</td>
                        <td>${employee.name}</td>
                        <td>${employee.email}</td>
                        <td>${employee.designation}</td>
                        <td>${employee.age}</td>
                        <td>${employee.dob}</td>
                        <td>${employee.rating}</td>
                        <td>${employee.dateOfJoining}</td>
                        <td>${employee.experience}</td>
                     <tr>
                  </tbody>
               </table>
               <br>
               <table class="w3-table-all w3-hoverable sortable">
                  <thead>
                     <tr id="table-header">
                        <td>ID</td>
                        <td>Address</td>
                        <td>State</td>
                        <td>Country</td>
                        <td>Type</td>
                     </tr>
                  </thead>
                  <tbody>
                    <c:forEach items="${employee.listOfAddresses}" varStatus="status" var="address">
                     <tr>
                        <td>${address.id}</td>
                        <td>${address.address}</td>
                        <td>${address.state}</td>
                        <td>${address.country}</td>
                        <td>${address.type}</td>
                     <tr>
                  </tbody>
                </c:forEach>
               </table>
               <br>
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
   <script src ="./js/employees.js"></script>
</html>
