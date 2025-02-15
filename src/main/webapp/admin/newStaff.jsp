<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark px-3">
	    <div class="container-fluid">

	        <a class="navbar-brand d-flex align-items-center" href="#">
	            <span class="fs-4 fw-bold">{ }</span>
	        </a>
	
	        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
	            <span class="navbar-toggler-icon"></span>
	        </button>

	        <div class="collapse navbar-collapse" id="navbarNav">
	            <ul class="navbar-nav me-auto">
	                <li class="nav-item"><a class="nav-link active" href="#">Staff</a></li>
	                <li class="nav-item"><a class="nav-link" href="#">Fleets</a></li>
	                <li class="nav-item"><a class="nav-link" href="#">Drivers</a></li>
	                <li class="nav-item"><a class="nav-link" href="#">Bookings</a></li>
	                <li class="nav-item"><a class="nav-link" href="#">Profile</a></li>
	            </ul>
	
				<a href="./login.jsp">
					<button class="btn btn-primary ">Logout</button>
				</a>

	        </div>
	    </div>
	</nav>
	<div class="container mt-4">
    <!-- Form Title -->
	    <h2 class="mb-4 text-center">Add New Staff</h2>
	
	    <!-- Staff Form -->
	    <form class="mx-auto" style="max-width: 500px;">
	        <!-- Name -->
	        <div class="mb-3">
	            <label for="name" class="form-label">Name</label>
	            <input type="text" class="form-control" id="name" placeholder="Enter full name" required>
	        </div>
	
	        <!-- Employee Number -->
	        <div class="mb-3">
	            <label for="empNumber" class="form-label">Employee Number</label>
	            <input type="text" class="form-control" id="empNumber" placeholder="EMP12345" required>
	        </div>
	
	        <!-- Email -->
	        <div class="mb-3">
	            <label for="email" class="form-label">Email</label>
	            <input type="email" class="form-control" id="email" placeholder="Enter email" required>
	        </div>
	
	        <!-- Birthday -->
	        <div class="mb-3">
	            <label for="birthday" class="form-label">Birthday</label>
	            <input type="date" class="form-control" id="birthday" required>
	        </div>
	
	        <!-- Submit Button -->
	        <div class="text-center">
	            <button type="submit" class="btn btn-dark w-100">Add Staff</button>
	        </div>
	    </form>
	</div>
</body>
</html>