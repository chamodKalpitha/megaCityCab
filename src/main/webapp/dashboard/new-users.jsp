<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"></script>

</head>
<body>
	<jsp:include page="../components/menu-bar.jsp" />
	<jsp:include page="../components/toaster.jsp" />
	
	<div class="container mt-4">
    <!-- Form Title -->
	    <h2 class="mb-4 text-center">Add New User</h2>
	    <form class="mx-auto" style="max-width: 500px;" method="POST" action="${pageContext.request.contextPath}/dashboard/add-user">
	        <!-- Name -->
	        <div class="mb-3">
	            <label for="name" class="form-label">Name</label>
	            <input type="text" class="form-control" id="name" placeholder="Enter full name" name="name" required>
	        </div>
	        
	       <!-- Email -->
	        <div class="mb-3">
	            <label for="email" class="form-label">Email</label>
	            <input type="email" class="form-control" id="email" placeholder="Enter email" name="email" required>
	        </div>
	
			<!-- Account type -->
			<div class="mb-4">
				<label for="accountType" class="form-label">Account Type</label>
				<select class="form-select" aria-label="Default select example" id="accountType" name="accountType" required>
					<option value="STAFF">Staff</option>
					<option value="MANAGER">Manager</option>
				</select>
			 </div>
	        <!-- Submit Button -->
	        <div class="text-center">
	            <button type="submit" class="btn btn-dark w-100">Add Staff</button>
	        </div>
	    </form>
	</div>
	<jsp:include page="../components/footer.jsp"/>
</body>
</html>