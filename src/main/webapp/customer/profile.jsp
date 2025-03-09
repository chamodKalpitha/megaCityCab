<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Profile</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>
    <jsp:include page="../components/menu-bar.jsp" />
    <jsp:include page="../components/toaster.jsp" />

    <div class="container mt-4">
        <h2 class="mb-4 text-center">Profile</h2>
        
        <form class="mx-auto mb-5" style="max-width: 500px;" method="POST" action="${pageContext.request.contextPath}/profile">

            <div class="mb-3">
                <input type="hidden" class="form-control" id="customerId" name="customerId" value="${customer.userDTO.userId}" readonly>
            </div>

            <!-- Name -->
            <div class="mb-3">
                <label for="name" class="form-label">Name</label>
                <input type="text" class="form-control" id="name" name="name" value="${customer.customerName}" required>
            </div>

            <!-- Email -->
            <div class="mb-3">
                <label for="email" class="form-label">Email</label>
                <input type="email" class="form-control" id="email" name="email" value="${customer.userDTO.userEmail}" required>
            </div>
            
            <!-- Address -->
	        <div class="mb-3">
	            <label for="address" class="form-label">Address</label>
	            <input type="text" class="form-control" id="address" value="${customer.address}" name="address" required>
	        </div>
	        
	        <!-- Contact Number -->
	        <div class="mb-3">
	            <label for="contactNumber" class="form-label">Contact</label>
	            <input type="text" class="form-control" id="contactNumber" value="${customer.contactNumber}" name="contactNumber" required>
	        </div>
 
            <div class="text-center">
                <button type="submit" class="btn btn-dark w-100">Update</button>
            </div>
        </form>
    </div>
    <jsp:include page="../components/footer.jsp"/>
    <script>
    
	    const urlParams2 = new URLSearchParams(window.location.search);
	    const successMessage = urlParams2.get('success');
	    if (successMessage) {
	        Swal.fire({
	            position: "top-end",
	            icon: "success",
	            title: Message,
	            showConfirmButton: false,
	            timer: 1500
	        });
	    }
    </script>
</body>
</html>
