package com.bms.service;

import javax.mail.*;
import javax.mail.internet.*;

import com.bms.config.EmailConfig;
import com.bms.dto.BookingDTO;
import com.bms.enums.PricingType;
import com.bms.observer.BookingObserver;

import java.util.Date;
import java.util.Properties;

public class EmailService implements BookingObserver {
   
    @Override
    public void onBookingCreated(BookingDTO bookingDTO) {
        sendBookingConfirmation(bookingDTO);
    }

    public void sendBookingConfirmation(BookingDTO bookingDTO) {
        String customerEmail = bookingDTO.getUserDTO().getUserEmail();
        String vehicleModel = bookingDTO.getVehicleDTO().getVehicleBrand() + " " + bookingDTO.getVehicleDTO().getVehicleModel();
        String vehicleNumberPlate = bookingDTO.getVehicleDTO().getPlateNumber();
        Date startDate = bookingDTO.getBookingDate();
        PricingType pricingType = bookingDTO.getPricingType();

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", EmailConfig.getSmtpHost());
        props.put("mail.smtp.port", EmailConfig.getSmtpPort());

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EmailConfig.getUsername(), EmailConfig.getPassword());
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EmailConfig.getUsername()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(customerEmail));
            message.setSubject("Booking Confirmation - " + vehicleNumberPlate);

            StringBuilder emailContent = new StringBuilder();
            emailContent.append("Dear Customer,\n\n")
                        .append("Your vehicle booking has been successfully confirmed!\n\n")
                        .append("Booking Details:\n")
                        .append("------------------------------------------------------\n")
                        .append("Vehicle: ").append(vehicleModel).append("\n")
                        .append("Vehicle Number Plate: ").append(vehicleNumberPlate).append("\n")
                        .append("Booking Start Date: ").append(startDate).append("\n")
                        .append("Pricing Type: ").append(pricingType).append("\n")
                        .append("------------------------------------------------------\n\n")
                        .append("For more details regarding pricing, please refer to your invoice.\n\n")
                        .append("Thank you for choosing our service!\n")
                        .append("For any inquiries, feel free to contact us.\n\n")
                        .append("Best Regards,\nMega City Cab Team");

            message.setText(emailContent.toString());
            Transport.send(message);
            System.out.println("Email sent successfully to " + customerEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }  
}
