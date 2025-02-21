<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit Driver</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
    <jsp:include page="../components/menu-bar.jsp" />
    <jsp:include page="../components/toaster.jsp" />

    <div class="container mt-4">
        <h2 class="mb-4 text-center">Edit Driver</h2>
        
        <form class="mx-auto mb-5" style="max-width: 500px;" method="POST" action="${pageContext.request.contextPath}/admin/update-driver">
            <!-- Driver ID (Read-only) -->
            <div class="mb-3">
                <label for="driverId" class="form-label">Driver ID</label>
                <input type="text" class="form-control" id="driverId" name="driverId" value="${driver.driverId}" readonly>
            </div>

            <!-- Name -->
            <div class="mb-3">
                <label for="driverName" class="form-label">Name</label>
                <input type="text" class="form-control" id="driverName" name="driverName" value="${driver.driverName}" required>
            </div>

            <!-- NIC Number -->
            <div class="mb-3">
                <label for="nicNumber" class="form-label">NIC Number</label>
                <input type="text" class="form-control" id="nicNumber" name="nicNumber" value="${driver.nicNumber}" required>
            </div>

            <!-- Contact Number -->
            <div class="mb-3">
                <label for="contactNumber" class="form-label">Contact Number</label>
                <input type="text" class="form-control" id="contactNumber" name="contactNumber" value="${driver.contactNumber}" required>
            </div>

            <!-- Driver Status -->
            <div class="mb-3">
                <label for="driverStatus" class="form-label">Driver Status</label>
                <select class="form-select" id="driverStatus" name="driverStatus" required>
                    <option value="AVAILABLE" ${driver.driverStatus == 'AVAILABLE' ? 'selected' : ''}>Available</option>
                    <option value="UNAVAILABLE" ${driver.driverStatus == 'UNAVAILABLE' ? 'selected' : ''}>Unavailable</option>
                </select>
            </div>
            
            <!-- Update Button -->
            <div class="text-center">
                <button type="submit" class="btn btn-primary w-100">Update Driver</button>
            </div>
        </form>
    </div>
</body>
</html>
