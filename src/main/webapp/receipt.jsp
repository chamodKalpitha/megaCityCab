<%@ page import="com.bms.dto.BillDTO, com.bms.enums.PaymentMethod, com.bms.enums.PricingType" %>
<%@ page import="com.bms.dto.BookingDTO, com.bms.dto.VehicleDTO" %>
<%
    BillDTO bill = (BillDTO) request.getAttribute("bill");
    BookingDTO booking = bill.getBookingDTO();
    VehicleDTO vehicle = bill.getVehicleDTO();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Receipt</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        .receipt-container {
            border: 1px solid #ccc;
            padding: 20px;
            max-width: 600px;
            margin: 0 auto;
            text-align: center;
        }
        h2 {
            color: #333;
        }
        table {
            width: 100%;
            margin-top: 20px;
            border-collapse: collapse;
        }
        table, th, td {
            border: 1px solid #ccc;
        }
        th, td {
            padding: 10px;
            text-align: left;
        }
        .print-btn {
            margin-top: 20px;
            padding: 10px 20px;
            background-color: #007BFF;
            color: white;
            border: none;
            cursor: pointer;
            border-radius: 5px;
        }
        .print-btn:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
<div class="receipt-container">
    <h2>Booking Receipt</h2>
    <p>Bill ID: <%= bill.getBillId() %></p>
    <p>Booking ID: <%= bill.getBookingId() %></p>

    <table>
        <tr>
            <th>Vehicle</th>
            <td><%= vehicle.getVehicleBrand() %> - <%= vehicle.getVehicleModel() %></td>
        </tr>
        <tr>
            <th>Plate Number</th>
            <td><%= vehicle.getPlateNumber() %></td>
        </tr>
        <tr>
            <th>Total Kilometers</th>
            <td><%= bill.getTotalKm() %> KM</td>
        </tr>
        <tr>
            <th>Total Days</th>
            <td><%= bill.getTotalDays() %></td>
        </tr>
        <tr>
            <th>Sub Total</th>
            <td>Rs. <%= bill.getTotalAmount() %>0</td>
        </tr>
        <tr>
            <th>Tax</th>
            <td>Rs. <%= bill.getTotalTax() %>0</td>
        </tr>
        <tr>
            <th>Total</th>
            <td>Rs. <%= bill.getTotalTax()+bill.getTotalAmount() %>0</td>
        </tr>
        <tr>
            <th>Payment Method</th>
            <td><%= bill.getPaymentMethod() %></td>
        </tr>
        <tr>
            <th>Pricing Type</th>
            <td><%= booking.getPricingType() %></td>
        </tr>
    </table>

    <button class="print-btn" onclick="window.print()">Print Receipt</button>
</div>
</body>
</html>
