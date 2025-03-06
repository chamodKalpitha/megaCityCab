<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>Bookings</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
	
	<style>
	        .pagination-container {
	
	            display: flex;
	            align-items: center;
	            justify-content: space-between;
	        }
	        
	        .pagination-info {
	        	background-color:red;
	        }
	</style>
</head>
<body>

    <%
        // Get query parameters with default values
        String searchQuery = request.getParameter("search") != null ? request.getParameter("search") : "";
        String entries = request.getParameter("entries") != null ? request.getParameter("entries") : "10";
        int currentPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        int totalEntries = Integer.parseInt(request.getAttribute("count").toString());
        int totalPages = (int) Math.ceil((double) totalEntries / Integer.parseInt(entries));
    %>
    
	<jsp:include page="../components/menu-bar.jsp" />
	<div class="container mt-4">
	<div class="d-flex justify-content-between align-items-center mb-3">
		<h2 class="mb-0">Booking Details</h2>
		<a href="${pageContext.request.contextPath}/dashboard/add-booking">
			<button class="btn btn-dark d-none"><i class="bi bi-plus-lg"></i> Add Booking</button>
		</a> 
	</div>
		<div class="d-flex justify-content-end mb-3">
            <form class="d-flex me-2" method="GET">
                <input class="form-control" type="search" placeholder="Search..." aria-label="Search" name="search" value="<%= searchQuery %>">
                <input type="hidden" name="entries" value="<%= entries %>">
                <input type="hidden" name="page" value="1">
                <button type="submit" class="btn btn-dark">Search</button>
            </form>
      </div>
	    <div class="table-responsive">
	        <table class="table table-bordered table-hover align-middle">
	            <thead class="table-light">
	                <tr>
	                    <th>No</th>
	                    <th>Customer Name</th>
	                    <th>Number Plate</th>
	                    <th>Booking Date</th>
	                    <th>Booking Status</th>
	                    <th>Pricing Type</th>
	                    <th>Action</th>
	                </tr>
	            </thead>
	            <tbody>
	            <c:choose>
		            <c:when test="${not empty bookings}">
		                <c:forEach var="booking" items="${bookings}" varStatus="status">
							<c:set var="driverIdValue" value="${empty booking.driverId ? 'Not Assigned' : booking.driverId}" />
		                    <tr>
								<td>${(page - 1) * entries + status.index + 1}</td>
		                        <td><a style="cursor: pointer;" class="pointer" 
		                        onclick="showCustomerDetails(${booking.customerDTO.customerId}, '${booking.customerDTO.customerName}', '${booking.customerDTO.address}', '${booking.customerDTO.nicNumber}', '${booking.customerDTO.contactNumber}')">${booking.customerDTO.customerName}</a></td>
		                        <td>
		                        	<a style="cursor: pointer;" class="pointer" onclick="showVehicleDetails(${booking.vehicleDTO.vehicleId}, '${booking.vehicleDTO.vehicleBrand}', '${booking.vehicleDTO.vehicleModel}', '${booking.vehicleDTO.plateNumber}', ${booking.vehicleDTO.capacity}, '${booking.vehicleDTO.vehicleStatus}', '${booking.vehicleDTO.vehicleType}','${booking.vehicleDTO.ratePerKM}','${booking.vehicleDTO.ratePerDay}','${booking.vehicleDTO.imageURLString}')">${booking.vehicleDTO.plateNumber}</a>
		                        </td>
		                        <td>${booking.bookingDate}</td>
								<td>${booking.bookingStatus}</td>
								<td>
								    <c:if test="${empty booking.driverId}">
								        <c:choose>
								            <c:when test="${booking.pricingType.toString() eq 'PER_KM_WITH_DRIVER' or booking.pricingType.toString() eq 'PER_DAY_WITH_DRIVER'}">
								                <a class="btn btn-sm btn-warning">
								                    <i class="bi bi-exclamation-triangle"></i>
								                </a>
								            </c:when>
								        </c:choose>
								    </c:if>
								    ${booking.pricingType}
								</td>
		                        <td class="d-flex justify-content-center gap-2 h-full">
			                        <c:if test="${booking.bookingStatus eq 'COMPLETED'}">
			                        	<a href="${pageContext.request.contextPath}/dashboard/print-bill?bookingId=${booking.bookingId}" class="btn btn-sm btn-dark"><i class="bi bi-receipt"></i></a>
			                        </c:if>
			                        <a href="${pageContext.request.contextPath}/dashboard/update-booking?bookingId=${booking.bookingId}" class="btn btn-sm btn-primary"><i class="bi bi-pencil"></i></a>
			                        <button class="btn btn-sm btn-info" class="btn btn-sm btn-info" 
			                        onclick="showBookingDetails(${booking.bookingId}, ${booking.customerDTO.customerId}, ${booking.bookedVehicleId}, '${booking.bookingDate}', '${booking.bookingStatus}', '${driverIdValue}', ' ${booking.pricingType}')"><i class="bi bi-eye"></i></button>
			                        <button class="btn btn-sm btn-danger" data-user-id="${booking.bookingId}" onclick="handleDelete(${booking.bookingId})"><i class="bi bi-x-octagon"></i></button>
		                        </td>
		                    </tr>
		                </c:forEach>
		            </c:when>
		            <c:otherwise>
		                <tr>
		                    <td colspan="7" class="text-center">No data found</td>
		                </tr>
		            </c:otherwise>
		        </c:choose>
	            </tbody>
	        </table>
	    </div>
	    
	    	    <!-- Pagination Controls -->
		 <div class="pagination-container mt-3 mb-5">
            <p id="pagination-info">Showing <%= ((currentPage - 1) * Integer.parseInt(entries) + 1) %> to 
                <%= Math.min(currentPage * Integer.parseInt(entries), totalEntries) %> of <%= totalEntries %> entries</p>
            <nav>
                <ul class="pagination mb-0">
                    <li class="page-item <%= (currentPage == 1) ? "disabled" : "" %>">
                        <a class="page-link" href="?search=<%= searchQuery %>&entries=<%= entries %>&page=<%= Math.max(1, currentPage - 1) %>">&laquo;</a>
                    </li>
                    <li class="page-item <%= (currentPage == totalPages) ? "disabled" : "" %>">
                        <a class="page-link" href="?search=<%= searchQuery %>&entries=<%= entries %>&page=<%= Math.min(currentPage + 1, totalPages) %>">&raquo;</a>
                    </li>
                </ul>
            </nav>

            <!-- Items Per Page Dropdown -->
            <div>
                <form class="d-flex me-2 item-center" method="GET">
                    <label for="itemsPerPage">Show entries</label>
                    <select id="itemsPerPage" class="form-select d-inline w-auto mx-2" name="entries" onchange="this.form.submit()">
                        <option value="5" <%= "5".equals(entries) ? "selected" : "" %>>5</option>
                        <option value="10" <%= "10".equals(entries) ? "selected" : "" %>>10</option>
                        <option value="15" <%= "15".equals(entries) ? "selected" : "" %>>15</option>
                    </select>
                    <input type="hidden" name="search" value="<%= searchQuery %>">
                    <input type="hidden" name="page" value="1">
                </form>
            </div>
        </div>
	</div>
	<jsp:include page="../components/footer.jsp"/>
	<c:if test="${message ne null}">
	   <div class="alert alert-danger" role="alert">
		${message}
		</div>
	</c:if>
	<script>

	function handleDelete(id){
		const url = `${pageContext.request.contextPath}/dashboard/delete-booking?search=<%= searchQuery %>&entries=<%= entries %>&page=<%= currentPage %>&bookingId=`+id;
	
		Swal.fire({
            title: "Are you sure?",
            text: "This action cannot be undone!",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#d33",
            cancelButtonColor: "#3085d6",
            confirmButtonText: "Yes, delete it!"
        }).then((result) => {
            if (result.isConfirmed) {
                window.location.href = url;
            }
        });
	}
	
	function showVehicleDetails(vehicleId, vehicleBrand, vehicleModel, plateNumber, capacity, vehicleStatus,vehicleType,ratePerKM,ratePerDay,url) {
	    Swal.fire({
	        title: "Vehicle Details",
	        imageUrl: url,
	        html: 
	            "<div style='text-align: left;'>" +
	                "<p><strong>Vehicle ID:</strong> " + vehicleId + "</p>" +
	                "<p><strong>Vehicle Brand:</strong> " + vehicleBrand + "</p>" +
	                "<p><strong>Vehicle Model:</strong> " + vehicleModel + "</p>" +
	                "<p><strong>Plate Number:</strong> " + plateNumber + "</p>" +
	                "<p><strong>Capacity:</strong> " + capacity + "</p>" +
	                "<p><strong>Vehicle Status:</strong> " + vehicleStatus + "</p>" +
	                "<p><strong>Vehicle Type:</strong> " + vehicleType + "</p>" +
	                "<p><strong>Per KM:</strong> " + ratePerKM + "</p>" +
	                "<p><strong>Per Day:</strong> " + ratePerDay + "</p>" +
	            "</div>",
	        confirmButtonText: "OK",
	        confirmButtonColor: "#3085d6",
	        background: "#f8f9fa",
	        customClass: {
	            popup: "rounded-3 shadow-lg",
	            title: "fw-bold"
	        }
	    });
	}
	
	function showCustomerDetails(customerId, customerName, address, nic_number, contact_number) {
	    Swal.fire({
	        title: "Customer Details",
	        html: 
	            "<div style='text-align: left;'>" +
	                "<p><strong>Customer ID:</strong> " + customerId + "</p>" +
	                "<p><strong>Customer Name:</strong> " + customerName + "</p>" +
	                "<p><strong>Address:</strong> " + address + "</p>" +
	                "<p><strong>NIC Number:</strong> " + nic_number + "</p>" +
	                "<p><strong>Contact:</strong> " + contact_number + "</p>" +
	            "</div>",
	        icon: "info",
	        confirmButtonText: "OK",
	        confirmButtonColor: "#3085d6",
	        background: "#f8f9fa",
	        customClass: {
	            popup: "rounded-3 shadow-lg",
	            title: "fw-bold"
	        }
	    });
	}
	
	function showBookingDetails(bookingId, customerId, vehicledId, date, status,driverId,pricing_type) {
	    Swal.fire({
	        title: "Customer Details",
	        html: 
	            "<div style='text-align: left;'>" +
	                "<p><strong>Booking ID:</strong> " + bookingId + "</p>" +
	                "<p><strong>Customer Id:</strong> " + customerId + "</p>" +
	                "<p><strong>Booking Date:</strong> " + vehicledId + "</p>" +
	                "<p><strong>Booking Date:</strong> " + date + "</p>" +
	                "<p><strong>Booking Status</strong> " + status + "</p>" +
	                "<p><strong>Driver Id:</strong> " + driverId + "</p>" +
	                "<p><strong>Pricing Type:</strong> " + pricing_type + "</p>" +
	                
	            "</div>",
	        icon: "info",
	        confirmButtonText: "OK",
	        confirmButtonColor: "#3085d6",
	        background: "#f8f9fa",
	        customClass: {
	            popup: "rounded-3 shadow-lg",
	            title: "fw-bold"
	        }
	    });
	}


</script>
</body>
</html>