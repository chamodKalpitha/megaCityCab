<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add New Driver</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
    <jsp:include page="../components/menu-bar.jsp" />
    <jsp:include page="../components/toaster.jsp" />

    <div class="container mt-4">
        <h2 class="mb-4 text-center">Add New Driver</h2>
        <form class="mx-auto mb-5" style="max-width: 500px;" method="POST" action="${pageContext.request.contextPath}/dashboard/add-driver">
            
            <!-- Driver Name -->
            <div class="mb-3">
                <label for="driverName" class="form-label">Driver Name</label>
                <input type="text" class="form-control" id="driverName" placeholder="Enter driver name" name="driverName" required>
            </div>

            <!-- NIC Number -->
            <div class="mb-3">
                <label for="nicNumber" class="form-label">NIC Number</label>
                <input type="text" class="form-control" id="nicNumber" placeholder="Enter NIC number" name="nicNumber" required>
            </div>

            <!-- Contact Number -->
            <div class="mb-3">
                <label for="contactNumber" class="form-label">Contact Number</label>
                <input type="text" class="form-control" id="contactNumber" placeholder="Enter contact number" name="contactNumber" required>
            </div>

            <!-- Submit Button -->
            <div class="text-center">
                <button type="submit" class="btn btn-dark w-100">Add Driver</button>
            </div>
        </form>
    </div>
    <jsp:include page="../components/footer.jsp"/>
</body>
</html>
