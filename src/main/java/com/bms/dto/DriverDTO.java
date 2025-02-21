package com.bms.dto;

import com.bms.enums.DriverStatus;

public class DriverDTO {
    private int driverId;
    private String driverName;
    private String nicNumber;
    private String contactNumber;
    private DriverStatus driverStatus;
    private boolean isDelete;
    
    public DriverDTO(int driverId, String driverName, String nicNumber) {
		super();
		this.driverId = driverId;
		this.driverName = driverName;
		this.nicNumber = nicNumber;
	}

	public DriverDTO(int driverId, String driverName, String nicNumber, String contactNumber, DriverStatus driverStatus) {
        this.driverId = driverId;
        this.driverName = driverName;
        this.nicNumber = nicNumber;
        this.contactNumber = contactNumber;
        this.driverStatus = driverStatus;
    }

	public DriverDTO(String driverName, String nicNumber, String contactNumber) {
		super();
		this.driverName = driverName;
		this.nicNumber = nicNumber;
		this.contactNumber = contactNumber;
	}

	public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getNicNumber() {
        return nicNumber;
    }

    public void setNicNumber(String nicNumber) {
        this.nicNumber = nicNumber;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public DriverStatus getDriverStatus() {
        return driverStatus;
    }

    public void setDriverStatus(DriverStatus driverStatus) {
        this.driverStatus = driverStatus;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }
}
