package com.example.entities;


public class Ticket {
	public Ticket() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Ticket(String pnr, String seat) {
		super();
		this.pnr = pnr;
		this.seat = seat;
	}
	public String getPnr() {
		return pnr;
	}
	public void setPnr(String pnr) {
		this.pnr = pnr;
	}
	public String getSeat() {
		return seat;
	}
	public void setSeat(String seat) {
		this.seat = seat;
	}
	private String pnr;
	private String seat;
}
