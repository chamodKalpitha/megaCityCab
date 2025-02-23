<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Customer Registration</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
    <jsp:include page="../components/menu-bar.jsp" />
    <jsp:include page="../components/toaster.jsp" />

    <div class="container mt-4">
        <h2 class="mb-4 text-center">Registration</h2>
        <form class="mx-auto mb-5" style="max-width: 700px;" method="POST" action="${pageContext.request.contextPath}/register" onsubmit="return validateForm()">
            <div class="row g-3">
                <!-- Full Name -->
                <div class="col-md-6">
                    <label for="customerName" class="form-label">Full Name</label>
                    <input type="text" class="form-control" id="customerName" name="customerName" required>
                </div>
                <!-- Email -->
                <div class="col-md-6">
                    <label for="userEmail" class="form-label">Email</label>
                    <input type="email" class="form-control" id="userEmail" name="userEmail" required>
                </div>
                <!-- NIC Number -->
                <div class="col-md-6">
                    <label for="nicNumber" class="form-label">NIC Number</label>
                    <input type="text" class="form-control" id="nicNumber" name="nicNumber" required>
                </div>
                <!-- Contact Number -->
                <div class="col-md-6">
                    <label for="contactNumber" class="form-label">Contact Number</label>
                    <input type="text" class="form-control" id="contactNumber" name="contactNumber" required>
                </div>
                <!-- Address (Full Width) -->
                <div class="col-12">
                    <label for="address" class="form-label">Address</label>
                    <input type="text" class="form-control" id="address" name="address" required>
                </div>
                <!-- Password -->
                <div class="col-md-6">
                    <label for="password" class="form-label">Password</label>
                    <input type="password" class="form-control" id="password" name="password" required>
                </div>
                <!-- Confirm Password -->
                <div class="col-md-6">
                    <label for="confirmPassword" class="form-label">Confirm Password</label>
                    <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required>
                    <small id="passwordError" class="text-danger" style="display: none;">Passwords do not match</small>
                </div>
            </div>
            <div class="text-center mt-4">
                <button type="submit" class="btn btn-dark w-100">Register</button>
            </div>
        </form>
    </div>
    
    <jsp:include page="../components/footer.jsp"/>
    
    <script>
        function validateForm() {
            var password = document.getElementById("password").value;
            var confirmPassword = document.getElementById("confirmPassword").value;
            var passwordError = document.getElementById("passwordError");
            
            if (password !== confirmPassword) {
                passwordError.style.display = "block";
                return false;
            } else {
                passwordError.style.display = "none";
                return true;
            }
        }
    </script>
</body>
</html>
