package com.example.entities;

import java.util.List;


public class Passenger {
	private String pnr;
	private List<String> seats;
	public Passenger() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Passenger(String pnr, List<String> seats) {
		super();
		this.pnr = pnr;
		this.seats = seats;
	}
	public String getPnr() {
		return pnr;
	}
	public void setPnr(String pnr) {
		this.pnr = pnr;
	}
	public List<String> getSeats() {
		return seats;
	}
	public void setSeats(List<String> seats) {
		this.seats = seats;
	}

}
