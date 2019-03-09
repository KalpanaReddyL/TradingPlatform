/**
 * 
 */
package com.company.project.trading.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.company.project.trading.assembler.TradingPlatformAssembler;
import com.company.project.trading.dto.AmountSettlementDTO;
import com.company.project.trading.enumerations.InstructionType;
import com.company.project.trading.model.Instruction;
import com.company.project.trading.validations.ValidationHelper;

/**
 * This trading service holds the logic for the actual processing of instructions
 * @author kalpana
 *
 */
public class TradingService implements ITradingService {

	@Override
	public List<AmountSettlementDTO> calculateBuyAmtSettledForDate(List<Instruction> list) {
		System.out.println("TradingService: entering calculateBuyAmtSettledForDate method");
		if(list!=null && list.size()>0){
			return calculateAmtSettledForDate(list.stream().filter(f -> f.getInstructionType().equals(InstructionType.BUY))
					.collect(Collectors.toList()));
		}else{
			System.out.println("TradingService: exiting calculateBuyAmtSettledForDate method with null due to empty instructions");
			return null;
		}
	}

	@Override
	public List<AmountSettlementDTO> calculateSellAmtSettledForDate(List<Instruction> list) {
		System.out.println("TradingService: entering calculateSellAmtSettledForDate method");
		if(list!=null && list.size()>0){
			return calculateAmtSettledForDate(list.stream().filter(f -> f.getInstructionType().equals(InstructionType.SELL))
					.collect(Collectors.toList()));
		}else{
			System.out.println("TradingService: exiting calculateSellAmtSettledForDate method with null due to empty instructions");
			return null;
		}
	}

	@Override
	public List<Instruction> fetchRankingBasedOnInstructionType(List<Instruction> instructionList,
			InstructionType type) {
		System.out.println("TradingService: entering fetchRankingBasedOnInstructionType method");
		List<Instruction> sortedInstructionList = new ArrayList<>();
		List<Instruction> filteredList = instructionList.stream().filter(f -> f.getInstructionType().equals(type))
				.collect(Collectors.toList());
		System.out.println("TradingService: fetchRankingBasedOnInstructionType method -  filtered instruction list size "+filteredList.size());
		for (Instruction instruction : filteredList) {
			if (instruction != null) {
				sortedInstructionList.add(instruction);
				if (type.equals(InstructionType.BUY)) {
					sortedInstructionList.sort(Comparator.comparing(Instruction::getAmount).reversed());
				} else {
					sortedInstructionList.sort(Comparator.comparing(Instruction::getAmount));
				}
			}
		}
		System.out.println("TradingService: fetchRankingBasedOnInstructionType method -  sorted instruction list size "+sortedInstructionList.size());
		System.out.println("TradingService: exiting fetchRankingBasedOnInstructionType method");
		return sortedInstructionList;
	}

	/**
	 * This method fetches the list of either buy or sell instructions
	 * For each settlement date, the total amount is to be calculated
	 * @param list list of instructions either buy or sell
	 * @return list of AmountSettlementDTO
	 */
	public List<AmountSettlementDTO> calculateAmtSettledForDate(List<Instruction> list) {
		System.out.println("TradingService: entering calculateAmtSettledForDate method");
		Map<LocalDate, Double> amountSettlementDateMap = new HashMap<>();
		performValidations(list);
		calculateSettlementBasedOnDate(list, amountSettlementDateMap);
		TradingPlatformAssembler assembler = new TradingPlatformAssembler();
		return assembler.toClient(amountSettlementDateMap);
	}

	/**
	 * Based on settlement/value date the amount is added to the existing map or a new record is added to the map
	 * @param list list of instructions either Buy or sell
	 * @param amountSettlementDateMap map holding the total amount settled on each type for each date
	 */
	private void calculateSettlementBasedOnDate(List<Instruction> list,
			Map<LocalDate, Double> amountSettlementDateMap) {

		System.out.println("TradingService: entering calculateSettlementBasedOnDate method");
		for (Instruction instruction : list) {
			if (amountSettlementDateMap != null && instruction != null && instruction.getSettlementDate() != null) {
				updateSettlementDatesBasedOnCurrency(instruction);
				if (amountSettlementDateMap.get(instruction.getSettlementDate()) != null) {
					amountSettlementDateMap.put(instruction.getSettlementDate(),
							instruction.getAmount() + amountSettlementDateMap.get(instruction.getSettlementDate()));
				} else {
					amountSettlementDateMap.put(instruction.getSettlementDate(), instruction.getAmount());
				}
			}
		}
		System.out.println("TradingService: exiting calculateSettlementBasedOnDate method");
	}

	/**
	 * For non working days make the necessary updates for the settlement dates in instruction object
	 * UC1 : If currency is AED or SAR and if the settlement date is on sunday, 
	 * 		 we shall move the settlement date by one day even if its working date in their trading exchange
	 * 		 Since in this application, the trades are settled in US exchange and the trading currency is USD, 
	 * 		 the assumption is to move the settlement date for these currencies as well 
	 * UC2 : If the currency is other than AED or SAR, if the settlement date falls on saturday or sunday,
	 * 		 the settlement date is moved by 1 and 2 days respectively.
	 * 
	 * @param instruction
	 */
	private void updateSettlementDatesBasedOnCurrency(Instruction instruction) {
		System.out.println("TradingService: entering updateSettlementDatesBasedOnCurrency method");
		if ((instruction.getTradingCurrency().equalsIgnoreCase("AED")
				|| instruction.getTradingCurrency().equalsIgnoreCase("SAR"))
				&& DayOfWeek.of(instruction.getSettlementDate().get(ChronoField.DAY_OF_WEEK))
						.equals(DayOfWeek.SUNDAY)) {
			instruction.setSettlementDate(instruction.getSettlementDate().plusDays(1));
		} else if (DayOfWeek.of(instruction.getSettlementDate().get(ChronoField.DAY_OF_WEEK))
				.equals(DayOfWeek.SATURDAY)) {
			instruction.setSettlementDate(instruction.getSettlementDate().plusDays(2));
		} else if (DayOfWeek.of(instruction.getSettlementDate().get(ChronoField.DAY_OF_WEEK))
				.equals(DayOfWeek.SUNDAY)) {
			instruction.setSettlementDate(instruction.getSettlementDate().plusDays(1));
		}
		System.out.println("TradingService: exiting updateSettlementDatesBasedOnCurrency method");
	}

	/**
	 * Before processing the instructions, we shall perform necessary validations as well
	 * Skip invalid instructions from processing
	 * @param instructionList
	 */
	private void performValidations(List<Instruction> instructionList) {
		System.out.println("TradingService: entering performValidations method");
		ValidationHelper validationHelper = new ValidationHelper();
		validationHelper.performInstructionLevelValidations(instructionList);
		System.out.println("TradingService: exiting performValidations method");
	}

}
