<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Login page</title>
    
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">
    <style>
        .center-container {
            min-height: 100vh;
        }
    </style>
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
	                <li class="nav-item"><a class="nav-link active" href="#">Home</a></li>
	                <li class="nav-item"><a class="nav-link" href="#">Features</a></li>
	                <li class="nav-item"><a class="nav-link" href="#">Pricing</a></li>
	                <li class="nav-item"><a class="nav-link" href="#">FAQs</a></li>
	                <li class="nav-item"><a class="nav-link" href="#">About</a></li>
	            </ul>
	
				<a href="./login.jsp">
					<button class="btn btn-primary ">Login</button>
				</a>

	        </div>
	    </div>
	</nav>
	 <div class="d-flex justify-content-center align-items-center center-container">
        <div class="card p-4 shadow-lg" style="width: 44rem;">
            <div class="card-body">
                <h5 class="card-title text-center mb-3">Login</h5>
                <c:if test="${error ne null}">
                <div class="alert alert-danger" role="alert">
					${error}
					</div>
				</c:if>

                <form action="login" method="POST">
                    <div class="mb-3">
                        <label for="email" class="form-label">Email address</label>
                        <input type="email" class="form-control" id="email" aria-describedby="email" name="email" required>
                       
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label">Password</label>
                        <input type="password" class="form-control" id="password" name="password" required>
                        
                    </div>
                    <div class="mb-3 form-check">
                        <input type="checkbox" class="form-check-input" id="rememberMe" name="rememberMe">
                        <label class="form-check-label" for="rememberMe" >Remember me</label>
                    </div>
                    <button type="submit" class="btn btn-primary w-100">Login</button>
                    
                </form>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    
  </body>
</html>
