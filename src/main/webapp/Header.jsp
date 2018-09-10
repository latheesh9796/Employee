<!DOCTYPE html>
<html>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src ="./js/sortable.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.3.1.js"></script>
    <script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/buttons/1.5.2/js/dataTables.buttons.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/pdfmake.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.36/vfs_fonts.js"></script>
    <script src="https://cdn.datatables.net/buttons/1.5.2/js/buttons.html5.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.2/jspdf.min.js"></script>
<head>
  <link rel="stylesheet" type="text/css" href="./css/Headers.css">
  <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
</head>
<body>
<ul>
  <li><a href="/PEC/menu.jsp" class="tab">Home</a></li>
  <li><a href="employeePage" class="tab">Employee</a></li>
  <li><a href="projectPage" class="tab">Project</a></li>
  <li><a href="clientPage" class="tab">Client</a></li>
  <li id="right">
<form action="UserManagement" method="POST"><button name="operation" value="logout" style="color:black"class="w3-btn w3-white">
      &ensp;Logout
      </button></form></li>
</ul>
</body>

