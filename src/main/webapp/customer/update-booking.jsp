<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit Booking</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
    <jsp:include page="../components/menu-bar.jsp" />
    <jsp:include page="../components/toaster.jsp" />

    <div class="container mt-4">
        <h2 class="mb-4 text-center">Edit Booking</h2>
        
        <form class="mx-auto mb-5" style="max-width: 500px;" method="POST" action="${pageContext.request.contextPath}/update-booking?bookingId=${booking.bookingId}">
            <!-- Booking ID (Read-only) -->
            <div class="mb-3">
                <label for="bookingId" class="form-label">Booking ID</label>
                <input type="text" class="form-control" id="bookingId" name="bookingId" value="${booking.bookingId}" readonly>
            </div>

            <!-- Vehicle ID -->
            <div class="mb-3">
                <label for="bookedVehicleId" class="form-label">Vehicle</label>
                <select class="form-select" id="bookedVehicleId" name="bookedVehicleId" required>
                    <c:forEach var="vehicle" items="${vehicles}">
                        <option value="${vehicle.vehicleId}" ${vehicle.vehicleId == booking.bookedVehicleId ? 'selected' : ''}>
                            ${vehicle.vehicleBrand} ${vehicle.vehicleModel}-(${vehicle.plateNumber})
                        </option>
                    </c:forEach>
                </select>
            </div>

            <!-- Booking Date -->
            <div class="mb-3">
                <label for="bookingDate" class="form-label">Booking Date</label>
                <input type="date" class="form-control" id="bookingDate" name="bookingDate" value="${booking.bookingDate}" required>
            </div>

            <!-- Pricing Type -->
            <div class="mb-3">
                <label for="pricingType" class="form-label">Pricing Type</label>
                <select class="form-select" id="pricingType" name="pricingType" required>
                    <option value="PER_KM_WITH_DRIVER" ${booking.pricingType == 'PER_KM_WITH_DRIVER' ? 'selected' : ''}>Per KM with Driver</option>
                    <option value="PER_KM_WITHOUT_DRIVER" ${booking.pricingType == 'PER_KM_WITHOUT_DRIVER' ? 'selected' : ''}>Per KM without Driver</option>
                    <option value="PER_DAY_WITH_DRIVER" ${booking.pricingType == 'PER_DAY_WITH_DRIVER' ? 'selected' : ''}>Per Day with Driver</option>
                    <option value="PER_DAY_WITHOUT_DRIVER" ${booking.pricingType == 'PER_DAY_WITHOUT_DRIVER' ? 'selected' : ''}>Per Day without Driver</option>
                </select>
           </div>
            
            <!-- Update Button -->
            <div class="text-center">
                <button type="submit" class="btn btn-dark w-100">Update Booking</button>
            </div>
        </form>
    </div>
    <jsp:include page="../components/footer.jsp"/>
    <script>
    

    </script>
</body>
</html>
