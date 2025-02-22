<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mega city cab</title>
<link rel="stylesheet" href="../css/bootstrap.min.css" />
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">
</head>
<body>
	<jsp:include page="./components/menu-bar.jsp"/>
	    <!-- Hero Section -->
    <header class="bg-primary text-white text-center py-5">
        <div class="container">
            <h1 class="fw-bold">Reliable Cab Service in Colombo</h1>
            <p class="lead">Thousands of customers trust Mega City Cab for safe and comfortable rides.</p>
            <a href="vehicles" class="btn btn-lg btn-light text-primary fw-bold mt-3">Book a Ride <i class="bi bi-arrow-right"></i></a>
        </div>
    </header>

    <!-- Features Section -->
    <section class="container py-5">
        <div class="row text-center">
            <div class="col-md-4">
                <i class="bi bi-car-front-fill text-primary fs-1"></i>
                <h4 class="mt-3">Easy Booking</h4>
                <p>Book a cab online in just a few clicks.</p>
            </div>
            <div class="col-md-4">
                <i class="bi bi-cash-coin text-primary fs-1"></i>
                <h4 class="mt-3">Affordable Rates</h4>
                <p>Transparent pricing with no hidden charges.</p>
            </div>
            <div class="col-md-4">
                <i class="bi bi-shield-lock-fill text-primary fs-1"></i>
                <h4 class="mt-3">Safe & Secure</h4>
                <p>Well-maintained cars and experienced drivers.</p>
            </div>
        </div>
    </section>
    
   <section class="container py-5">
        <h2 class="text-center fw-bold">How It Works</h2>
        <div class="row text-center mt-4">
            <div class="col-md-4">
                <i class="bi bi-phone text-primary fs-1"></i>
                <h4 class="mt-3">1. Book Online</h4>
                <p>Enter your pickup and drop-off details on our website.</p>
            </div>
            <div class="col-md-4">
                <i class="bi bi-geo-alt text-primary fs-1"></i>
                <h4 class="mt-3">2. Get a Cab</h4>
                <p>Our system finds the nearest available cab for you.</p>
            </div>
            <div class="col-md-4">
                <i class="bi bi-check-circle text-primary fs-1"></i>
                <h4 class="mt-3">3. Enjoy Your Ride</h4>
                <p>Relax and reach your destination safely.</p>
            </div>
        </div>
    </section>
    
    <section class="bg-light py-5">
        <div class="container">
            <h2 class="text-center fw-bold">What Our Customers Say</h2>
            <div class="row mt-4">
                <div class="col-md-4">
                    <div class="p-3 border rounded shadow-sm bg-white">
                        <p>⭐⭐⭐⭐⭐ "Mega City Cab is my go-to cab service. Always on time and super comfortable!"</p>
                        <small class="fw-bold">- Chamod Weerasinghe J.</small>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="p-3 border rounded shadow-sm bg-white">
                        <p>⭐⭐⭐⭐⭐ "Affordable and reliable! I use it for my daily commute to work."</p>
                        <small class="fw-bold">-  Bhagaya Rathnayaka.</small>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="p-3 border rounded shadow-sm bg-white">
                        <p>⭐⭐⭐⭐⭐ "Friendly drivers and clean cars. Best cab service in Colombo!"</p>
                        <small class="fw-bold">- Anjali P.</small>
                    </div>
                </div>
            </div>
        </div>
    </section>
    
	<jsp:include page="./components/footer.jsp"/>


    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>