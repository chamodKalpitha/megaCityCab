<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dashboard</title>
    <link rel="stylesheet" href="../css/bootstrap.min.css" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">
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
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark px-3">
	    <div class="container-fluid">

	        <a class="navbar-brand d-flex align-items-center" href="#">
	            <span class="fs-4 fw-bold">{ }</span>
	        </a>
	
	        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
	            <span class="navbar-toggler-icon"></span>
	        </button>

	        <div class="collapse navbar-collapse" id="navbarNav">
	            <ul class="navbar-nav me-auto">
	                <li class="nav-item"><a class="nav-link active" href="#">Staff</a></li>
	                <li class="nav-item"><a class="nav-link" href="#">Fleets</a></li>
	                <li class="nav-item"><a class="nav-link" href="#">Drivers</a></li>
	                <li class="nav-item"><a class="nav-link" href="#">Bookings</a></li>
	                <li class="nav-item"><a class="nav-link" href="#">Profile</a></li>
	            </ul>
	
				<a href="./login.jsp">
					<button class="btn btn-primary ">Logout</button>
				</a>

	        </div>
	    </div>
	</nav>
	
	<div class="container mt-4">
		
	    <!-- Staff Details Header with Add Staff Button -->
		<div class="d-flex justify-content-between align-items-center mb-3">
		    <h2 class="mb-0">Staff Details</h2>
		    <a href="./newStaff.jsp">
		    	<button class="btn btn-dark"><i class="bi bi-plus-lg"></i> Add Staff</button>
		    </a>
		    
		</div>
		<div class="d-flex justify-content-end mb-3">
		    <form class="d-flex me-2">
                <input class="form-control" type="search" placeholder="Search..." aria-label="Search">
            </form>
            <button type="button" class="btn btn-dark">Search</button>

        </div>
	    <div class="table-responsive">
	        <table class="table table-bordered table-hover align-middle">
	            <thead class="table-light">
	                <tr>
	                    <th>No</th>
	                    <th>Name</th>
	                    <th>Emp Number</th>
	                    <th>Created At</th>
	                    <th>Email</th>
	                    <th>Action</th>
	                </tr>
	            </thead>
	            <tbody>
	                <!-- Sample Row -->
	                <tr>
	                    <td>1</td>
	                    <td>John Doe</td>
	                    <td>EMP12345</td>
	                    <td>2024-02-12</td>
	                    <td>chamodlive@live.com</td>
	                    <td class="d-flex justify-content-center gap-2">
	                        <button class="btn btn-sm btn-primary"><i class="bi bi-pencil"></i></button>
	                        <button class="btn btn-sm btn-info"><i class="bi bi-eye"></i></button>
	                        <button class="btn btn-sm btn-warning"><i class="bi bi-ban"></i></button>
	                        <button class="btn btn-sm btn-danger"><i class="bi bi-trash"></i></button>
	                    </td>
	                </tr>
	                <tr>
	                    <td>2</td>
	                    <td>Jane Smith</td>
	                    <td>EMP67890</td>
	                    <td>2024-02-11</td>
	                    <td>chamodlive@live.com</td>
	                    <td class="d-flex justify-content-center gap-2">
	                        <button class="btn btn-sm btn-primary"><i class="bi bi-pencil"></i></button>
	                        <button class="btn btn-sm btn-info"><i class="bi bi-eye"></i></button>
	                        <button class="btn btn-sm btn-warning"><i class="bi bi-ban"></i></button>
	                        <button class="btn btn-sm btn-danger"><i class="bi bi-trash"></i></button>
	                    </td>
	                </tr>
	            </tbody>
	        </table>
	    </div>
	    
	    	    <!-- Pagination Controls -->
		 <div class="pagination-container mt-3">
	        <p id="pagination-info" >Showing 1 to 10 of 50 entries</p>
	        <nav>
	            <ul class="pagination mb-0">
	                <li class="page-item"><button class="page-link" id="prevPage">&laquo;</button></li>
	                <li class="page-item"><button class="page-link" id="nextPage">&raquo;</button></li>
	            </ul>
	        </nav>
	        <div>
	            <label for="itemsPerPage">Show</label>
	            <select id="itemsPerPage" class="form-select d-inline w-auto mx-2">
	                <option value="5">5</option>
	                <option value="10" selected>10</option>
	                <option value="15">15</option>
	            </select>
	            entries
	        </div>
    	</div>
	</div>
	

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
	
</body>
</html>