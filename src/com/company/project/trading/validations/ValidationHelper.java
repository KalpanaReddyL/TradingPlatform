package com.company.project.trading.validations;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

import com.company.project.trading.enumerations.InstructionType;
import com.company.project.trading.exceptions.ValidationException;
import com.company.project.trading.model.Asset;
import com.company.project.trading.model.Instruction;

/**
 * Validations class to perform validations
 * 
 * @author kalpana
 *
 */
public class ValidationHelper {

	/**
	 * Perform all the instruction validations before processing these
	 * instruction for further reports calculations If a particular instruction
	 * is not valid, ignore that instruction for reporting
	 * 
	 * @param instructionList
	 *            list of instructions
	 */
	public void performInstructionLevelValidations(List<Instruction> instructionList) {

		Iterator<Instruction> instructionIterator = instructionList.iterator();
		while (instructionIterator.hasNext()) {
			Instruction instruction = instructionIterator.next();
			if (instruction != null) {
				try {
					checkForAssetValidations(instruction.getAsset());
					checkCurrencyValidations(instruction.getTradingCurrency());
					checkUnitsValidations(instruction.getUnits());
					checkSettlementDateValidations(instruction.getSettlementDate());
					checkForeignExchangeValidations(instruction.getForeignExchangeRate());
					checkInstructionTypeValidations(instruction.getInstructionType());
					checkAmountValidations(instruction);
				} catch (ValidationException e) {
					System.err.println(" Instruction validation failed for instruction " + instruction.getId());
					System.err.println(e.getMessage());
					instructionIterator.remove();
				}
			}
		}
	}

	/**
	 * Perform amount level validations A buy instruction should always have
	 * positive amount and A sell instruction should always have negative amount
	 * 
	 * @param instruction
	 *            instruction to be validated
	 * @throws ValidationException
	 */
	private void checkAmountValidations(Instruction instruction) throws ValidationException {
		if (instruction.getInstructionType().equals(InstructionType.BUY) && instruction.getAmount() < 0) {
			throw new ValidationException("Buy Instruction Amount validation failed");
		} else if (instruction.getInstructionType().equals(InstructionType.SELL) && instruction.getAmount() > 0) {
			throw new ValidationException("Sell Instruction Amount validation failed");
		}
	}

	/**
	 * Perform instruction type validations. If the instruction type is null or
	 * not Buy or sell throw the exception
	 * 
	 * @param instructionType
	 * @throws ValidationException
	 */
	private void checkInstructionTypeValidations(InstructionType instructionType) throws ValidationException {
		if (instructionType == null) {
			throw new ValidationException("Instruction Type validation failed");
		} else if (!(instructionType.equals(InstructionType.BUY) || instructionType.equals(InstructionType.SELL))) {
			throw new ValidationException("Instruction Type value validation failed");
		}
	}

	/**
	 * Validate exchange rate. It should not be less than 0
	 * 
	 * @param foreignExchangeRate
	 * @throws ValidationException
	 */
	private void checkForeignExchangeValidations(double foreignExchangeRate) throws ValidationException {
		if (foreignExchangeRate < 0.0) {
			throw new ValidationException("Foreign exchange rate validation failed");
		}
	}

	/**
	 * Validate settlement date to not be null
	 * 
	 * @param settlementDate
	 * @throws ValidationException
	 */
	private void checkSettlementDateValidations(LocalDate settlementDate) throws ValidationException {
		if (settlementDate == null) {
			throw new ValidationException("Settlement date validation failed");
		}
	}

	/**
	 * Units cannot be less than 0. This needs to be validated
	 * 
	 * @param units
	 * @throws ValidationException
	 */
	private void checkUnitsValidations(int units) throws ValidationException {
		if (units < 0) {
			throw new ValidationException("Units Validation failed");
		}
	}

	/**
	 * Currency is mandatory and hence necessary null checks and empty checks
	 * are validated
	 * 
	 * @param currency
	 * @throws ValidationException
	 */
	private void checkCurrencyValidations(String currency) throws ValidationException {
		if (currency == null || currency.trim().equals("")) {
			throw new ValidationException("Currency Validation failed");
		} else if (currency.length() != 3) {
			throw new ValidationException("Currency value Validation failed");
		}
	}

	/**
	 * Asset level validations include asset name being null or empty and the
	 * price being less than 0
	 * 
	 * @param asset
	 * @throws ValidationException
	 */
	private void checkForAssetValidations(Asset asset) throws ValidationException {

		if (asset != null) {
			if (asset.getName() == null || asset.getName().trim().equals("")) {
				throw new ValidationException("Asset Name Validation failed");
			} else if (asset.getPrice() < 0.0) {
				throw new ValidationException("Asset price Validation failed");
			}
		} else {
			throw new ValidationException("Asset Validation failed");
		}
	}

}
