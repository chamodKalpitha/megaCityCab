<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>Users</title>
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
		<h2 class="mb-0">User Details</h2>
		<a href="${pageContext.request.contextPath}/admin/add-user">
			<button class="btn btn-dark"><i class="bi bi-plus-lg"></i> Add User</button>
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
	                    <th>Name</th>
	                    <th>Email</th>
	                    <th>Account Status</th>
	                    <th>Action</th>
	                </tr>
	            </thead>
	            <tbody>
	            <c:choose>
		            <c:when test="${not empty users}">
		                <c:forEach var="user" items="${users}" varStatus="status">
		                    <tr>
								<td>${(page - 1) * entries + status.index + 1}</td>
		                        <td>${user.userName}</td>
		                        <td>${user.userEmail}</td>
		                        <td>${user.accountStatus}</td>
		                        <td class="d-flex justify-content-center gap-2">
			                        <a href="${pageContext.request.contextPath}/admin/update-user?userId=${user.userId}" class="btn btn-sm btn-primary"><i class="bi bi-pencil"></i></a>
			                        <button class="btn btn-sm btn-info" class="btn btn-sm btn-info" onclick="showUserDetails(${user.userId}, '${user.userName}', '${user.userEmail}', '${user.accountStatus}', '${user.accountType}')"><i class="bi bi-eye"></i></button>
			                        <button class="btn btn-sm btn-danger" data-user-id="${user.userId}" onclick="handleDelete(${user.userId})"><i class="bi bi-trash"></i></button>
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
	<c:if test="${message ne null}">
	   <div class="alert alert-danger" role="alert">
		${message}
		</div>
	</c:if>
	<script>

	function handleDelete(id){
		const url = `${pageContext.request.contextPath}/admin/delete-user?search=<%= searchQuery %>&entries=<%= entries %>&page=<%= currentPage %>&userId=`+id;
	
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
	function showUserDetails(userId, username, useremail, accountStatus, accountType) {
	    Swal.fire({
	        title: "User Details",
	        html: 
	            "<div style='text-align: left;'>" +
	                "<p><strong>User ID:</strong> " + userId + "</p>" +
	                "<p><strong>Name:</strong> " + username + "</p>" +
	                "<p><strong>Email:</strong> " + useremail + "</p>" +
	                "<p><strong>Account Status:</strong> " + accountStatus + "</p>" +
	                "<p><strong>Account Type:</strong> " + accountType + "</p>" +
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