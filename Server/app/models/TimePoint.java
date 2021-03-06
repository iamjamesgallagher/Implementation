package models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimePoint {	

	private long milTime;

	private Date time;
	private double volume;	
	
	private double price;
	
	private double close ;
	private double open ;	
	private double high;
	private double low;

		
private double amountPurchased;

private double valuePurchased;

public double getValuePurchased() {
	return valuePurchased;
}

public void setValuePurchased(double d) {
	this.valuePurchased = d;
}
	
	public double getAmountPurchased() {
		return amountPurchased;
	}

	public void setAmountPurchased(double d) {
		this.amountPurchased = d;
	}
	




	
	public double getHigh() {
		return high;
	}

	public void setHigh(double high) {
		this.high = high;
	}

	public double getLow() {
		return low;
	}

	public void setLow(double low) {
		this.low = low;
	}

	public double getMilTime() {
		return milTime;
	}

	public void setMilTime(long low) {
		this.milTime = low;
	}

	public double getClose() {
		return close;
	}

	public void setClose(double close) {
		this.close = close;
	}

	public double getOpen() {
		return open;
	}

	public void setOpen(double open) {
		this.open = open;
	}

	
	
	DateFormat dateFormat = new SimpleDateFormat("HH:mm");
	
	public TimePoint(Date d, double p){
		this.time = d;
		this.high = p;
		
	}

	public TimePoint(Date d, double p, double v){
		this.time = d;
		this.high = p;
		this.volume= v;
		
	}

	public TimePoint(){
		this.time = null;
		this.price = 0;
		this.volume = 0;
	}
	
	public TimePoint(long m,Date d,double b,double a, double h, double l , double c, double o){
		this.milTime = m;
		this.time = d;
		this.price = b;
		this.volume = a;
		this.high = h;
		this.low = l;
		this.close = c;
		this.open = o;
		
	}
	
	public Date getTime() {
		return this.time;
	}
	
	public void setTime(Date time) {
		this.time = time;
	}
	
	public double getprice() {
		return this.price;
	}

	public void setprice(double price) {
		this.price = price;
	}


	public double getvolume() {
		return volume;
	}


	public void setvolume(double volume) {
		this.volume = volume;
	}

	public String toString(){
		return "Time" +":" + dateFormat.format(this.time.getTime())+ ", Price :" + this.price + ", Volume : " + this.volume;
		
	}
	
	
}
