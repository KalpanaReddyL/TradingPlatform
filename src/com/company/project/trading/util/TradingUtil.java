package com.company.project.trading.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.company.project.trading.enumerations.InstructionType;
import com.company.project.trading.model.Asset;
import com.company.project.trading.model.Instruction;

/**
 * Util class for operations related to reading files and performing reporting operations
 * @author kalpana
 *
 */
public class TradingUtil {

	/**
	 * Read the file holding all the instructions and convert it into list of Instruction objects
	 * @return list of instructions
	 */
	public List<Instruction> fetchInstructions() {
		System.out.println("TradingUtil: entering fetchInstructions method ");
		List<Instruction> instructionList = new ArrayList<>();
		File file = new File("TradingInput.txt");
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
			System.out.println("TradingUtil: fetchInstructions method - reading the file");
			while (scanner.hasNextLine()) {
				String instructionString = scanner.nextLine();
				String[] instructionInput = instructionString.split(",");
				Instruction instruction = populateInstructionFromString(instructionInput);
				if (instruction != null) {
					instructionList.add(instruction);
				}
			}
			System.out.println("TradingUtil: fetchInstructions - instructions added from file are "+instructionList.size());
		} catch (FileNotFoundException e) {
			System.err.println("TradingUtil: fetchInstructions - Error while processing file");
		}finally{
			scanner.close();
			System.out.println("TradingUtil: exiting fetchInstructions method ");
		}
		System.out.println("TradingUtil: exiting fetchInstructions method ");
		return instructionList;
	}

	/**
	 * The single instruction read in the file is converted to a instruction object.
	 * @param instructionInput string holding the instruction read from file
	 * @return newly created instruction
	 */
	private Instruction populateInstructionFromString(String[] instructionInput) {
		System.out.println("TradingUtil: entering populateInstructionFromString method ");
		if (instructionInput.length < 9 && instructionInput.length > 0) {
			System.err.println(
					"TradingUtil: populateInstructionFromString - The instruction has some data missing, hence skipping this instruction " + instructionInput[0]);
		} else {
			Instruction instruction = new Instruction();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
			instruction.setId(Integer.valueOf(instructionInput[0]));
			Asset asset = new Asset();
			asset.setName(instructionInput[1]);
			asset.setPrice(Double.valueOf(instructionInput[8]));
			instruction.setAsset(asset);
			instruction.setInstructionType(InstructionType.fromValue(instructionInput[2]));
			instruction.setForeignExchangeRate(Double.valueOf(instructionInput[3]));
			instruction.setTradingCurrency(instructionInput[4]);
			instruction.setInstructionDate(LocalDate.parse(instructionInput[5], formatter));
			instruction.setSettlementDate(LocalDate.parse(instructionInput[6], formatter));
			instruction.setUnits(Integer.parseInt(instructionInput[7]));
			System.out.println("TradingUtil: exiting populateInstructionFromString method ");
			return instruction;
		}
		System.out.println("TradingUtil: exiting populateInstructionFromString method returning null since improper data");
		return null;
	}

	public static void main(String[] args) {
		TradingUtil util = new TradingUtil();
		util.fetchInstructions();
	}
}
