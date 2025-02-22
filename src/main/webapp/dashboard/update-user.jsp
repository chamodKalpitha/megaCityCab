<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit User</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
    <jsp:include page="../components/menu-bar.jsp" />
    <jsp:include page="../components/toaster.jsp" />

    <div class="container mt-4">
        <h2 class="mb-4 text-center">Edit User</h2>
        
        <form class="mx-auto mb-5" style="max-width: 500px;" method="POST" action="${pageContext.request.contextPath}/dashboard/update-user">
            <!-- User ID (Read-only) -->
            <div class="mb-3">
                <label for="userId" class="form-label">User ID</label>
                <input type="text" class="form-control" id="userId" name="userId" value="${user.userId}" readonly>
            </div>

            <!-- Name -->
            <div class="mb-3">
                <label for="name" class="form-label">Name</label>
                <input type="text" class="form-control" id="name" name="name" value="${user.userName}" required>
            </div>

            <!-- Email -->
            <div class="mb-3">
                <label for="email" class="form-label">Email</label>
                <input type="email" class="form-control" id="email" name="email" value="${user.userEmail}" required>
            </div>

            <!-- Account Status -->
            <div class="mb-3">
                <label for="accountStatus" class="form-label">Account Status</label>
                <select class="form-select" id="accountStatus" name="accountStatus" required>
                    <option value="ACTIVE" ${user.accountStatus == 'ACTIVE' ? 'selected' : ''}>Active</option>
                    <option value="INACTIVE" ${user.accountStatus == 'INACTIVE' ? 'selected' : ''}>Inactive</option>
                </select>
            </div>

            <!-- Account Type -->
            <div class="mb-3">
                <label for="accountType" class="form-label">Account Type</label>
                <select class="form-select" id="accountType" name="accountType" required>
                    <option value="STAFF" ${user.accountType == 'STAFF' ? 'selected' : ''}>Staff</option>
                    <option value="MANAGER" ${user.accountType == 'MANAGER' ? 'selected' : ''}>Manager</option>
                </select>
            </div>

            <!-- Update Button -->
            <div class="text-center">
                <button type="submit" class="btn btn-primary w-100">Update User</button>
            </div>
        </form>
    </div>
    <jsp:include page="../components/footer.jsp"/>
</body>
</html>
