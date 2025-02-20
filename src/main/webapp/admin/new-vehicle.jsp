<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add New Vehicle</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
    <jsp:include page="../components/menu-bar.jsp" />
    <jsp:include page="../components/toaster.jsp" />

    <div class="container mt-4">
        <h2 class="mb-4 text-center">Add New Vehicle</h2>
        <form class="mx-auto mb-5" style="max-width: 500px;" method="POST" action="${pageContext.request.contextPath}/admin/add-vehicle">
            
            <!-- Vehicle Brand -->
            <div class="mb-3">
                <label for="vehicleBrand" class="form-label">Vehicle Brand</label>
                <input type="text" class="form-control" id="vehicleBrand" placeholder="Enter vehicle brand" name="vehicleBrand" required>
            </div>

            <!-- Vehicle Model -->
            <div class="mb-3">
                <label for="vehicleModel" class="form-label">Vehicle Model</label>
                <input type="text" class="form-control" id="vehicleModel" placeholder="Enter vehicle model" name="vehicleModel" required>
            </div>

            <!-- Plate Number -->
            <div class="mb-3">
                <label for="plateNumber" class="form-label">Plate Number</label>
                <input type="text" class="form-control" id="plateNumber" placeholder="Enter plate number" name="plateNumber" required>
            </div>

            <!-- Capacity -->
            <div class="mb-3">
                <label for="capacity" class="form-label">Capacity</label>
                <input type="number" class="form-control" id="capacity" placeholder="Enter seating capacity" name="capacity" required>
            </div>

            <!-- Vehicle Status -->
            <div class="mb-3">
                <label for="vehicleStatus" class="form-label">Vehicle Status</label>
                <select class="form-select" id="vehicleStatus" name="vehicleStatus" required>
                    <option value="AVAILABLE">Available</option>
                    <option value="INSERVICE">In Service</option>
                    <option value="INUSE">In Use</option>
                </select>
            </div>

            <!-- Vehicle Type -->
            <div class="mb-3">
                <label for="vehicleType" class="form-label">Vehicle Type</label>
                <select class="form-select" id="vehicleType" name="vehicleType" required>
                    <option value="CAR">Car</option>
                    <option value="VAN">Van</option>
                </select>
            </div>

            <!-- Rate Per KM -->
            <div class="mb-3">
                <label for="ratePerKM" class="form-label">Rate Per KM</label>
                <input type="number" step="0.01" class="form-control" id="ratePerKM" placeholder="Enter rate per KM" name="ratePerKM" required>
            </div>
            
            <!-- Image -->
            <div class="mb-3">
                <label for="vehicleImage" class="form-label">Vehicle Image</label>
                <input type="text" class="form-control" id="vehicleImage" placeholder="Enter Image Url" name="vehicleImage" required>
            </div>

            <!-- Submit Button -->
            <div class="text-center">
                <button type="submit" class="btn btn-dark w-100">Add Vehicle</button>
            </div>
        </form>
    </div>
</body>
</html>
