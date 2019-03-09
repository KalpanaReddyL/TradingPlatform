package com.company.project.trading.model;

import java.time.LocalDate;

import com.company.project.trading.enumerations.InstructionType;

/**
 * This class is for the instruction type entity
 * @author kalpana
 *
 */
public class Instruction {
	/** Id for the instruction */
	private int id;
	/** Asset which holds its name and its price */
	private Asset asset;
	/** Date of receiving instruction */
	private LocalDate instructionDate;
	/** Value date */
	private LocalDate settlementDate;
	/** Currency where the trading is triggered */
	private String tradingCurrency;
	/** units of the assets purchased */
	private int units;
	/** Buy or sell instruction */
	private InstructionType instructionType;
	/** Amount calculated for the instruction */
	private double amount;
	/** Exchange Rate for the trade */
	private double foreignExchangeRate;

	/**
	 * @param id
	 * @param asset
	 * @param instructionDate
	 * @param settlementDate
	 * @param tradingCurrency
	 * @param units
	 * @param instructionType
	 * @param foreignExchangeRate
	 */
	public Instruction(int id, Asset asset, LocalDate instructionDate, LocalDate settlementDate, String tradingCurrency,
			int units, InstructionType instructionType, double foreignExchangeRate) {
		this.id = id;
		this.asset = asset;
		this.instructionDate = instructionDate;
		this.settlementDate = settlementDate;
		this.tradingCurrency = tradingCurrency;
		this.units = units;
		this.instructionType = instructionType;
		this.foreignExchangeRate = foreignExchangeRate;
		this.amount = foreignExchangeRate * units * asset.getPrice();
	}

	/**
	 * 
	 */
	public Instruction() {

	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the tradingCurrency
	 */
	public String getTradingCurrency() {
		return tradingCurrency;
	}

	/**
	 * @param tradingCurrency
	 *            the tradingCurrency to set
	 */
	public void setTradingCurrency(String tradingCurrency) {
		this.tradingCurrency = tradingCurrency;
	}

	/**
	 * @return the asset
	 */
	public Asset getAsset() {
		return asset;
	}

	/**
	 * @param asset
	 *            the asset to set
	 */
	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	/**
	 * @return the instructionDate
	 */
	public LocalDate getInstructionDate() {
		return instructionDate;
	}

	/**
	 * @param instructionDate
	 *            the instructionDate to set
	 */
	public void setInstructionDate(LocalDate instructionDate) {
		this.instructionDate = instructionDate;
	}

	/**
	 * @return the settlementDate
	 */
	public LocalDate getSettlementDate() {
		return settlementDate;
	}

	/**
	 * @param settlementDate
	 *            the settlementDate to set
	 */
	public void setSettlementDate(LocalDate settlementDate) {
		this.settlementDate = settlementDate;
	}

	/**
	 * @return the units
	 */
	public int getUnits() {
		return units;
	}

	/**
	 * @param units
	 *            the units to set
	 */
	public void setUnits(int units) {
		this.units = units;
	}

	/**
	 * @return the instructionType
	 */
	public InstructionType getInstructionType() {
		return instructionType;
	}

	/**
	 * @param instructionType
	 *            the instructionType to set
	 */
	public void setInstructionType(InstructionType instructionType) {
		this.instructionType = instructionType;
	}

	/**
	 * Calculate the amount based on the other attributes Also change the sign
	 * of the amount if it is sell type transaction
	 * 
	 * @return the amount
	 */
	public double getAmount() {
		if (asset != null) {
			amount = units * foreignExchangeRate * asset.getPrice();
			if (instructionType != null && instructionType.equals(InstructionType.SELL)) {
				amount *= -1;
			}
		}
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * @return the foreignExchangeRate
	 */
	public double getForeignExchangeRate() {
		return foreignExchangeRate;
	}

	/**
	 * @param foreignExchangeRate
	 *            the foreignExchangeRate to set
	 */
	public void setForeignExchangeRate(double foreignExchangeRate) {
		this.foreignExchangeRate = foreignExchangeRate;
	}
}
