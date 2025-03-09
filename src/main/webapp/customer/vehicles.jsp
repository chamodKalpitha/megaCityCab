<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>Vehicles</title>
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
		<h2 class="mb-0">Vehicle Details</h2>
	</div>
		<div class="d-flex justify-content-end mb-3">
            <form class="d-flex me-2" method="GET">
                <input class="form-control" type="search" placeholder="Search by brand" aria-label="Search" name="search" value="<%= searchQuery %>">
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
	                    <th>Image</th>
	                    <th>Vehicle</th>
	                    <th>Capacity</th>
	                    <th>Vehicle Type</th>
	                    <th>Rate per Km</th>
	                    <th>Rate per Day</th>
	                    <th>Action</th>
	                </tr>
	            </thead>
	            <tbody>
	            <c:choose>
		            <c:when test="${not empty vehicles}">
		                <c:forEach var="vehicle" items="${vehicles}" varStatus="status">
		                    <tr>
								<td>${(page - 1) * entries + status.index + 1}</td>
								<td><img src="${vehicle.imageURLString}" class="img-fluid pointer" style="height: 40px; width: auto; cursor: pointer;" onclick="showImage('${vehicle.imageURLString}')"/></td>
		                        <td>${vehicle.vehicleBrand} - ${vehicle.vehicleModel}</td>
		                        <td>${vehicle.capacity}</td>
		                        <td>${vehicle.vehicleType}</td>
		                        <td>${vehicle.ratePerKM}</td>
		                        <td>${vehicle.ratePerDay}</td>
		                        <td class="align-middle text-center">
		                        <c:if test="${not empty sessionScope.accountType and sessionScope.accountType eq 'CUSTOMER'}">
		                        	<button class="btn btn-dark" class="btn btn-sm btn-info" onclick="bookAVehicle('${vehicle.vehicleId}')">Book</button></c:if>
		                        <c:if test="${empty sessionScope.accountType}"><a href="login"><button class="btn btn-dark" class="btn btn-sm btn-info">Book</button></a></c:if>
		                        </td>
		                    </tr>
		                </c:forEach>
		            </c:when>
		            <c:otherwise>
		                <tr>
		                    <td colspan="7" class="text-center">No users found</td>
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
	<jsp:include page="../components/toaster.jsp" />
	<c:if test="${message ne null}">
	   <div class="alert alert-danger" role="alert">
		${message}
		</div>
	</c:if>
	<script>
	
	function bookAVehicle(vehicleId) {
	    const date = new Date().toISOString().split('T')[0]; 
	    Swal.fire({
	        title: "Book a Ride ",
	        html: `
	            <form id="bookingForm" action="${pageContext.request.contextPath}/customer/create-booking" method="POST">
	                <!-- Booking Date -->
	                <div class="mb-3">
	                    <label for="bookingDate" class="form-label text-start d-block">Booking Date</label>
	                    <input type="date" class="form-control" id="bookingDate" name="bookingDate" placeholder="Select a date" min="`+date+`" required>
	                </div>

	                <!-- Pricing Type -->
	                <div class="mb-5">
	                    <label for="pricingType" class="form-label text-start d-block">Pricing Type</label>
	                    <select class="form-select" id="pricingType" name="pricingType" required>
	                        <option value="PER_KM_WITH_DRIVER">Per KM with Driver</option>
	                        <option value="PER_KM_WITHOUT_DRIVER">Per KM without Driver</option>
	                        <option value="PER_DAY_WITH_DRIVER">Per Day with Driver</option>
	                        <option value="PER_DAY_WITHOUT_DRIVER">Per Day without Driver</option>
	                    </select>
	                </div>
	                
	                <input type="hidden" value="`+vehicleId+`" name="vehicleId">

	                <div class="text-center">
	                    <button type="submit" class="btn btn-dark w-100">Book</button>
	                </div>
	            </form>
	        `,
	        showCancelButton: false,
	        showConfirmButton: false
	    });
	}


    const urlParams = new URLSearchParams(window.location.search);
    const Message = urlParams.get('error');
    if (Message) {
        Swal.fire({
            position: "top-end",
            icon: "error",
            title: Message,
            showConfirmButton: false,
            timer: 1500
        });
    }
    
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

	function handleDelete(id){
		const url = `${pageContext.request.contextPath}/dashboard/delete-vehicle?search=<%= searchQuery %>&entries=<%= entries %>&page=<%= currentPage %>&vehicleId=`+id;
	
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
	function showVehicleDetails(vehicleId, vehicleBrand, vehicleModel, plateNumber, capacity, vehicleStatus,vehicleType,ratePerKM,ratePerDay) {
	    Swal.fire({
	        title: "Vehicle Details",
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

	function showImage(url){
		Swal.fire({
			  imageUrl: url,
			  imageAlt: "image",
			});
	}


</script>
</body>
</html>