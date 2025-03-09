package com.bms.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bms.dao.BillDAO;
import com.bms.dto.BillCalculateDateDTO;
import com.bms.dto.BillDTO;
import com.bms.model.Bill;
import com.bms.service.BillingService;

@ExtendWith(MockitoExtension.class)
class BillControllerTest {

    @Mock
    private BillDAO billDAO;

    @Mock
    private BillingService billingService;

    @InjectMocks
    private BillController billController;

    private BillCalculateDateDTO billCalculateDateDTO;
    private BillDTO billDTO;
    private Bill bill;

    @BeforeEach
    void setUp() {
        billCalculateDateDTO = mock(BillCalculateDateDTO.class);
        billDTO = mock(BillDTO.class);
        bill = mock(Bill.class);
    }

    @Test
    void testGetBillById() throws SQLException {
        int bookingId = 123;
        when(billDAO.getBillById(bookingId)).thenReturn(billDTO);

        BillDTO result = billController.getBillById(bookingId);

        assertNotNull(result);
        assertEquals(billDTO, result);
        verify(billDAO, times(1)).getBillById(bookingId);
    }
}
