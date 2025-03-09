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
        <h2 class="mb-4 text-center">Generate Bill</h2>
        
        <form class="mx-auto mb-5" style="max-width: 500px;" method="POST" action="${pageContext.request.contextPath}/dashboard/print-bill">
            <!-- Booking ID (Read-only) -->
            <div class="mb-3">
                <label for="bookingId" class="form-label">Booking ID</label>
                <input type="text" class="form-control" id="bookingId" name="bookingId" value="${booking.bookingId}" readonly>
            </div>

            <!-- Vehicle ID -->
            <div class="mb-3">
                <label for="bookedVehicleId" class="form-label">Vehicle</label>                
                <select class="form-select" id="bookedVehicleId" name="bookedVehicleId" disabled>
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
                <input type="date" class="form-control" id="bookingDate" name="bookingDate" value="${booking.bookingDate}" disabled>
            </div>

            <!-- Pricing Type -->
            <div class="mb-3">
                <label for="pricingType" class="form-label">Pricing Type</label>
                <select class="form-select" id="pricingType" name="pricingType" disabled>
                    <option value="PER_KM_WITH_DRIVER" ${booking.pricingType == 'PER_KM_WITH_DRIVER' ? 'selected' : ''}>Per KM with Driver</option>
                    <option value="PER_KM_WITHOUT_DRIVER" ${booking.pricingType == 'PER_KM_WITHOUT_DRIVER' ? 'selected' : ''}>Per KM without Driver</option>
                    <option value="PER_DAY_WITH_DRIVER" ${booking.pricingType == 'PER_DAY_WITH_DRIVER' ? 'selected' : ''}>Per Day with Driver</option>
                    <option value="PER_DAY_WITHOUT_DRIVER" ${booking.pricingType == 'PER_DAY_WITHOUT_DRIVER' ? 'selected' : ''}>Per Day without Driver</option>
                </select>
           </div>
           
           
           	<c:if test="${booking.pricingType eq 'PER_KM_WITH_DRIVER'}">
           	
           	    <!-- PER_KM_WITH_DRIVER -->
	            <div class="mb-3">
	                <label for="kmCount" class="form-label">KM Count</label>
	                <input type="number" class="form-control" id="kmCount" name="kmCount" placeholder="Enter km count" required>
	            </div>
	            
	            <div class="mb-3">
	                <label for="driverCharges" class="form-label">Driver charges per KM</label>
	                <input type="number" class="form-control" id="driverCharges" name="driverCharges" placeholder="Enter driver charges" required>
	            </div>
           	</c:if>
           	
             <c:if test="${booking.pricingType eq 'PER_KM_WITHOUT_DRIVER'}">
             
	            <!-- PER_KM_WITHOUT_DRIVER -->
	            <div class="mb-3">
	                <label for="kmCount" class="form-label">KM Count</label>
	                <input type="number" class="form-control" id="kmCount" name="kmCount" placeholder="Enter km count" required>
	            </div>
           	</c:if>
           	
           	<c:if test="${booking.pricingType eq 'PER_DAY_WITH_DRIVER'}">
           	
	            <!-- PER_DAY_WITH_DRIVER -->
	            <div class="mb-3">
	                <label for="completedDate" class="form-label">Completed date</label>
	                <input type="date" class="form-control" id="completedDate" name="completedDate" placeholder="Select completed date" required>
	            </div>
	            
	            <div class="mb-3">
	                <label for="driverCharges" class="form-label">Driver charges per Day</label>
	                <input type="number" class="form-control" id="driverCharges" name="driverCharges" placeholder="Enter driver charges" required>
	            </div>
           	</c:if>
           	
           <c:if test="${booking.pricingType eq 'PER_DAY_WITHOUT_DRIVER'}">
           
	            <!-- PER_DAY_WITHOUT_DRIVER -->
	            <div class="mb-3">
	                <label for="completedDate" class="form-label">Completed date</label>
	                <input type="date" class="form-control" id="completedDate" name="completedDate" placeholder="Select completed date" required>
	            </div>
           	</c:if>
           	
           	
           <!-- Payment Method -->
			<div class="mb-3">
				<label for="paymentMethod" class="form-label">Payment method</label>
				<select class="form-select" aria-label="Default select a payment method" id="paymentMethod" name="paymentMethod" required>
					<option value="CARD">Card</option>
					<option value="CASH">Cash</option>
				</select>
			 </div>

            <!-- Update Button -->
            <div class="text-center">
                <button type="submit" class="btn btn-dark w-100">Generate Bill</button>
            </div>
        </form>
    </div>
    <jsp:include page="../components/footer.jsp"/>
    <script>
    
    </script>
</body>
</html>
