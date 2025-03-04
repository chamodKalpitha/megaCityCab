<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit Staff</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
    <jsp:include page="../components/menu-bar.jsp" />
    <jsp:include page="../components/toaster.jsp" />

    <div class="container mt-4">
        <h2 class="mb-4 text-center">Edit Staff</h2>
        
        <form class="mx-auto mb-5" style="max-width: 500px;" method="POST" action="${pageContext.request.contextPath}/dashboard/update-staff?staffId=${staff.staffId}">
            <!-- Staff ID (Read-only) -->
            <div class="mb-3">
                <label for="staffId" class="form-label">Staff ID</label>
                <input type="text" class="form-control" id="staffId" name="staffId" value="${staff.staffId}" readonly>
            </div>

            <!-- Name -->
            <div class="mb-3">
                <label for="name" class="form-label">Name</label>
                <input type="text" class="form-control" id="name" name="name" value="${staff.name}" required>
            </div>

            <!-- Email -->
            <div class="mb-3">
                <label for="email" class="form-label">Email</label>
                <input type="email" class="form-control" id="email" name="email" value="${staff.userDTO.userEmail}" required>
            </div>
            
            <!-- Address -->
	        <div class="mb-3">
	            <label for="address" class="form-label">Address</label>
	            <input type="text" class="form-control" id="address" value="${staff.address}" name="address" required>
	        </div>
	        
	        <!-- Contact Number -->
	        <div class="mb-3">
	            <label for="contactNumber" class="form-label">Contact</label>
	            <input type="text" class="form-control" id="contactNumber" value="${staff.contactNumber}" name="contactNumber" required>
	        </div>

            <!-- Account Status -->
            <div class="mb-3">
                <label for="accountStatus" class="form-label">Account Status</label>
                <select class="form-select" id="accountStatus" name="accountStatus" required>
                    <option value="ACTIVE" ${staff.userDTO.accountStatus == 'ACTIVE' ? 'selected' : ''}>Active</option>
                    <option value="INACTIVE" ${staff.userDTO.accountStatus == 'INACTIVE' ? 'selected' : ''}>Inactive</option>
                </select>
            </div>

            <!-- Account Type -->
            <div class="mb-3">
                <label for="accountType" class="form-label">Account Type</label>
                <select class="form-select" id="accountType" name="accountType" required>
                    <option value="STAFF" ${staff.userDTO.accountType == 'STAFF' ? 'selected' : ''}>Staff</option>
                    <option value="MANAGER" ${staff.userDTO.accountType == 'MANAGER' ? 'selected' : ''}>Manager</option>
                </select>
            </div>
            <!-- Update Button -->
            <div class="text-center">
                <button type="submit" class="btn btn-dark w-100">Update Staff</button>
            </div>
        </form>
    </div>
    <jsp:include page="../components/footer.jsp"/>
</body>
</html>
