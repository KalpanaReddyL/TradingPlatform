package com.company.project.trading.dto;

import java.time.LocalDate;

/**
 * DTO for data transfering purpose
 * It holds the date and amount necessary for reporting purpose
 * @author kalpana
 *
 */
public class AmountSettlementDTO {
	
	/** Total amount settled*/
	private double amount;
	/**Value Date details */
	private LocalDate settlementDate;
	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	/**
	 * @return the settlementDate
	 */
	public LocalDate getSettlementDate() {
		return settlementDate;
	}
	/**
	 * @param settlementDate the settlementDate to set
	 */
	public void setSettlementDate(LocalDate settlementDate) {
		this.settlementDate = settlementDate;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AmountSettlementDTO [amount=" + amount + ", settlementDate=" + settlementDate + "]";
	}
}
