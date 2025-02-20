package com.bms.model;

import com.bms.enums.VehicleStatus;
import com.bms.enums.VehicleType;

public class Vehicle {
	private int vehicleId;
	private String vehicleBrand;
	private String vehicleModel;
	private String plateNumber;
	private int capacity;
	private VehicleStatus vehicleStatus;
	private VehicleType vehicleType;
	private String imageURLString;
	private double ratePerKM;
    private boolean isDelete;
    
	public Vehicle(int vehicleId, String vehicleBrand, String vehicleModel, String plateNumber, int capacity,
			VehicleStatus vehicleStatus, VehicleType vehicleType, String imageURLString, double ratePerKM) {
		super();
		this.vehicleId = vehicleId;
		this.vehicleBrand = vehicleBrand;
		this.vehicleModel = vehicleModel;
		this.plateNumber = plateNumber;
		this.capacity = capacity;
		this.vehicleStatus = vehicleStatus;
		this.vehicleType = vehicleType;
		this.imageURLString = imageURLString;
		this.ratePerKM = ratePerKM;
	}

	public Vehicle(String vehicleBrand, String vehicleModel, String plateNumber, int capacity,
			VehicleStatus vehicleStatus, VehicleType vehicleType, String imageURLString, double ratePerKM) {
		super();
		this.vehicleBrand = vehicleBrand;
		this.vehicleModel = vehicleModel;
		this.plateNumber = plateNumber;
		this.capacity = capacity;
		this.vehicleStatus = vehicleStatus;
		this.vehicleType = vehicleType;
		this.imageURLString = imageURLString;
		this.ratePerKM = ratePerKM;
	}

	public int getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getVehicleBrand() {
		return vehicleBrand;
	}

	public void setVehicleBrand(String vehicleBrand) {
		this.vehicleBrand = vehicleBrand;
	}

	public String getVehicleModel() {
		return vehicleModel;
	}

	public void setVehicleModel(String vehicleModel) {
		this.vehicleModel = vehicleModel;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public VehicleStatus getVehicleStatus() {
		return vehicleStatus;
	}

	public void setVehicleStatus(VehicleStatus vehicleStatus) {
		this.vehicleStatus = vehicleStatus;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

	public double getRatePerKM() {
		return ratePerKM;
	}

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public String getImageURLString() {
		return imageURLString;
	}

	public void setImageURLString(String imageURLString) {
		this.imageURLString = imageURLString;
	}

	public void setRatePerKM(double ratePerKM) {
		this.ratePerKM = ratePerKM;
	}
		
}
