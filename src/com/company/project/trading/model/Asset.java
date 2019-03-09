package com.company.project.trading.model;

/**
 * Asset class holding the asset name and price
 * @author kalpana
 *
 */
public class Asset {

	/** Name of the asset  **/
	private String name;
	/** Price of the asset  **/
	private double price;
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}
	
	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	
}
