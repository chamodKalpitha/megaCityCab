<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">
</head>

<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark px-3">
        <div class="container-fluid">
            <a class="navbar-brand d-flex align-items-center" href="/megaCityCab/">
                <span class="fs-4 fw-bold">🚗 Mega City Cab</span>
            </a>

            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <c:if test="${not empty sessionScope.accountType and sessionScope.accountType eq 'ADMIN'}">
                        <li class="nav-item"><a class="nav-link" href="staffs">Staffs</a></li>
                        <li class="nav-item"><a class="nav-link" href="vehicles">Vehicles</a></li>
                        <li class="nav-item"><a class="nav-link" href="drivers">Drivers</a></li>
                        <li class="nav-item"><a class="nav-link" href="bookings">Bookings</a></li>
                        <li class="nav-item"><a class="nav-link" href="profile">Profile</a></li>
                    </c:if>

                    <c:if test="${not empty sessionScope.accountType and sessionScope.accountType eq 'MANAGER'}">
                        <li class="nav-item"><a class="nav-link" href="vehicles">Vehicles</a></li>
                        <li class="nav-item"><a class="nav-link" href="drivers">Drivers</a></li>
                        <li class="nav-item"><a class="nav-link" href="bookings">Bookings</a></li>
                        <li class="nav-item"><a class="nav-link" href="profile">Profile</a></li>
                    </c:if>

                    <c:if test="${not empty sessionScope.accountType and sessionScope.accountType eq 'STAFF'}">
                        <li class="nav-item"><a class="nav-link" href="vehicles">Vehicles</a></li>
                        <li class="nav-item"><a class="nav-link" href="drivers">Drivers</a></li>
                        <li class="nav-item"><a class="nav-link" href="bookings">Bookings</a></li>
                        <li class="nav-item"><a class="nav-link" href="profile">Profile</a></li>
                    </c:if>

                    <c:if test="${not empty sessionScope.accountType and sessionScope.accountType eq 'CUSTOMER'}">
                        <li class="nav-item"><a class="nav-link" href="vehicles">Vehicles</a></li>
                        <li class="nav-item"><a class="nav-link" href="bookings">Bookings</a></li>
                        <li class="nav-item"><a class="nav-link" href="profile">Profile</a></li>
                    </c:if>
                    
                    <c:if test="${sessionScope.accountType eq null}">
                        <li class="nav-item"><a class="nav-link" href="/megaCityCab/">Home</a></li>
                        <li class="nav-item"><a class="nav-link" href="/megaCityCab/vehicles">Vehicles</a></li>
                    </c:if>
                    
                </ul>

                <!-- Show Login & Register if the user is NOT logged in -->
                <c:if test="${empty sessionScope.accountType}">
                    <a href="${pageContext.request.contextPath}/login" class="btn btn-outline-light me-2">Login</a>
                    <a href="${pageContext.request.contextPath}/register" class="btn btn-primary">Register</a>
                </c:if>

                <!-- Show Logout if the user IS logged in -->
                <c:if test="${not empty sessionScope.accountType}">
                    <a href="${pageContext.request.contextPath}/logout">
                        <button class="btn btn-danger">Logout</button>
                    </a>
                </c:if>
            </div>
        </div>
    </nav>

    <script type="text/javascript">
        document.querySelectorAll('.nav-link').forEach(link => {
            if (window.location.href.includes(link.getAttribute('href'))) {
                link.classList.add('active');
            }
        });
    </script>
</body>
</html>
