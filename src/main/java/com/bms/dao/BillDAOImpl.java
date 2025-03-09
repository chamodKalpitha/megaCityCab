package com.bms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.bms.config.DatabaseConfig;
import com.bms.dto.BillDTO;
import com.bms.dto.BookingDTO;
import com.bms.dto.VehicleDTO;
import com.bms.enums.BookingStatus;
import com.bms.enums.PaymentMethod;
import com.bms.enums.PricingType;
import com.bms.model.Bill;

public class BillDAOImpl implements BillDAO{
	
    private final Connection connection;
    
    public BillDAOImpl() {
        this.connection = DatabaseConfig.getInstance().getConnection();
    }


	@Override
	public boolean createBill(Bill bill) throws SQLException {
        String query = "INSERT INTO bill (booking_id, total_KM, total_days, total_amount, tax_amount, payment_method) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, bill.getBookingId());
            stmt.setDouble(2, bill.getTotalKm());
            stmt.setInt(3, bill.getTotalDays());
            stmt.setDouble(4, bill.getTotalAmount());
            stmt.setDouble(5, bill.getTotalTax());
            stmt.setString(6, bill.getPaymentMethod().toString());

            stmt.executeUpdate();
            return true;
        }
	}

	@Override
	public BillDTO getBillById(int bookingId) throws SQLException {
		String sql = "SELECT b.booking_id, b.user_id, b.booked_vehicle_id, b.booking_date, b.booking_status, b.driver_id, b.pricing_type, " +
                "bill.bill_id, bill.total_KM, bill.total_days, bill.total_amount, bill.tax_amount, bill.payment_method, " +
                "v.vehicle_brand, v.vehicle_model, v.plate_number, v.capacity, v.vehicle_status, v.vehicle_type, v.rate_per_km, v.rate_per_day, v.image_url_string " +
                "FROM booking b " +
                "JOIN bill ON b.booking_id = bill.booking_id " +
                "JOIN vehicle v ON b.booked_vehicle_id = v.vehicle_id " +
                "WHERE b.is_delete = 0 AND bill.is_delete = 0 AND v.is_delete = 0 AND b.booking_id = ?";
	    try (PreparedStatement ps = connection.prepareStatement(sql)) {
	        ps.setInt(1, bookingId);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	        	VehicleDTO vehicleDTO = new VehicleDTO(rs.getInt("booked_vehicle_id"), rs.getString("vehicle_brand"),rs.getString("vehicle_model"),rs.getString("plate_number"));
                BookingDTO bookingDTO = new BookingDTO(
                        rs.getInt("booking_id"),
                        rs.getInt("booked_vehicle_id"),
                        rs.getDate("booking_date"),
                        BookingStatus.valueOf(rs.getString("booking_status")),
                        PricingType.valueOf(rs.getString("pricing_type"))
                );
                
                
                
                BillDTO billDTO;
                
                if(bookingDTO.getPricingType()==PricingType.PER_DAY_WITH_DRIVER || bookingDTO.getPricingType()==PricingType.PER_DAY_WITHOUT_DRIVER) {
                	billDTO = new BillDTO(rs.getInt("bill_id"),
                            rs.getInt("booking_id"),
                            rs.getInt("total_days"),
                            rs.getDouble("total_amount"),
                            rs.getDouble("tax_amount"),
                            PaymentMethod.valueOf(rs.getString("payment_method")),
                            bookingDTO,
                            vehicleDTO);

                }else {

                	billDTO = new BillDTO(rs.getInt("bill_id"),
                            rs.getInt("booking_id"),
                            rs.getDouble("total_KM"),
                            rs.getDouble("total_amount"),
                            rs.getDouble("tax_amount"),
                            PaymentMethod.valueOf(rs.getString("payment_method")),
                            bookingDTO,
                            vehicleDTO);
                }
	        	
	            return billDTO;
	        }
	    }
	    return null;
	}


}
