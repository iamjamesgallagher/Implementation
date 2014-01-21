package models;

import java.util.ArrayList;


public class Stock {

	private String name;
	
	private ArrayList<TimePoint> timeandprice;

	private ArrayList<TimePoint> purchased;
		
	public Stock(String s ){
		this.name = s;
		this.timeandprice = new ArrayList<TimePoint>();
		this.purchased = new ArrayList<TimePoint>();
		
	}

	public String getName() {
		return name;
	}

	public ArrayList<TimePoint> getDataArray() {
		return this.timeandprice;
	}
	
	public TimePoint getDataPoint(int n) {
		return this.timeandprice.get(n);
	}
	
	public int DataLength() {
		return timeandprice.size();
	}

	public void setTimeandprice(TimePoint t) {
		this.timeandprice.add(t);
	}



	public ArrayList<TimePoint> getPurchaseArray() {
		return this.purchased;
	}
	
	public TimePoint getPurchasePoint(int n) {
		return this.purchased.get(n);
	}
	
	public int PurchaseLength() {
		return purchased.size();
	}

	public void setPurchase(TimePoint t) {
		this.purchased.add(t);
	}
	

	public double getOpen() {
		return this.timeandprice.get(0).getprice();
	}
	
//	public double getClose() {
//		int i = timeandprice.size();
//		return this.timeandprice.get(this.timeandprice.size()).getprice();
//	}
	
	
	public String toString(){
//		return "Stock" +":" + this.name + ", Open :" + getOpen()/*+ ", Close :" + this.getClose()*/  ;
		return "Stock" +":" + this.name  ;
		
	}
	
	
}
