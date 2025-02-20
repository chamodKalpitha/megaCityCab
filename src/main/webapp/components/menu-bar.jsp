<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" >
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">
</head>

<body>
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark px-3">
	    <div class="container-fluid">

	        <a class="navbar-brand d-flex align-items-center" href="#">
	            <span class="fs-4 fw-bold">🚗 Mega City Cab</span>
	        </a>
	
	        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
	            <span class="navbar-toggler-icon"></span>
	        </button>

	        <div class="collapse navbar-collapse" id="navbarNav">
	            <ul class="navbar-nav me-auto">
	                ${sessionScope.accountType == 'ADMIN' ? 
	                    '<li class="nav-item"><a class="nav-link" href="users">Users</a></li>
	                     <li class="nav-item"><a class="nav-link" href="vehicles">Vehicles</a></li>
	                     <li class="nav-item"><a class="nav-link" href="drivers">Drivers</a></li>
	                     <li class="nav-item"><a class="nav-link" href="bookings">Bookings</a></li>
	                     <li class="nav-item"><a class="nav-link" href="profile">Profile</a></li>' : '' }
	                ${sessionScope.accountType == 'STAFF' ? 
	                    '<li class="nav-item"><a class="nav-link" href="#">Vehicles</a></li>
	                     <li class="nav-item"><a class="nav-link" href="#">Drivers</a></li>
	                     <li class="nav-item"><a class="nav-link" href="#">Bookings</a></li>
	                     <li class="nav-item"><a class="nav-link" href="#">Profile</a></li>' : '' }
	                     
	            </ul>
				<a href="${pageContext.request.contextPath}/logout">
					<button class="btn btn-primary ">Logout</button>
				</a>

	        </div>
	    </div>
	</nav>
	<script type="text/javascript">
		document.querySelectorAll('.nav-link').forEach(link => {
		    if (window.location.href.includes(link.getAttribute('href'))) {
		        link.classList.add('active');
		    }
		});
	</script>
</body>
</html>