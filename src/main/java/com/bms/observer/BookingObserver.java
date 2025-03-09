package com.bms.observer;

import com.bms.dto.BookingDTO;

public interface BookingObserver {
    void onBookingCreated(BookingDTO bookingDTO);
}