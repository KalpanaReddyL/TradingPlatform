package com.company.project.trading.service;

import java.util.List;

import com.company.project.trading.dto.AmountSettlementDTO;
import com.company.project.trading.enumerations.InstructionType;
import com.company.project.trading.model.Instruction;

/**
 * Interface for the necessary services
 * @author kalpana
 *
 */
public interface ITradingService {

	/**
	 * This is the method which calculates the purchase(buy) amount for each
	 * date. The input being a list of Instructions which holds the Buy
	 * instructions
	 * 
	 * @param list
	 *            List of Buy instructions
	 * @return Amount settled for each date
	 */
	public List<AmountSettlementDTO> calculateBuyAmtSettledForDate(List<Instruction> list);

	/**
	 * This is the method which calculates the sell amount for each date. The
	 * input being a list of Instructions which holds the Sell instructions
	 * 
	 * @param list
	 *            List of Sell instructions
	 * @return Amount settled for each date
	 */
	public List<AmountSettlementDTO> calculateSellAmtSettledForDate(List<Instruction> list);

	/**
	 * Based on the instruction type, all the instructions needs to ranked
	 * Highest buy or highest sell should be on top and in the order based on the amount
	 * @param instructionList list of instructions 
	 * @param type instruction type either sell or buy
	 * @return list of instructions
	 */
	public List<Instruction> fetchRankingBasedOnInstructionType(List<Instruction> instructionList,
			InstructionType type);
}
