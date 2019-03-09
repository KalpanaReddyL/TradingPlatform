package com.company.project.trading.junit;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.company.project.trading.dto.AmountSettlementDTO;
import com.company.project.trading.enumerations.InstructionType;
import com.company.project.trading.model.Asset;
import com.company.project.trading.model.Instruction;
import com.company.project.trading.service.ITradingService;
import com.company.project.trading.service.TradingService;

/**
 * Unit tetsting class for our reporting application
 * @author kalpana
 *
 */
public class TradingPlatformTest {
	Instruction instrTestName = populateInstruction(133456, " ", 0.5,"GBP", "B", "25-Mar-2019","26-Mar-2019",2354,12.423);
	Instruction instrWrongUnits = populateInstruction(153456, "CS", 0.5,"GBP", "B", "25-Mar-2019","26-Mar-2019",-100,12.423);
	Instruction instrWrongPrice = populateInstruction(163456, "CS", 0.5,"GBP", "B", "25-Mar-2019","26-Mar-2019",2354,-12.423);
	Instruction instrWrongCurrency = populateInstruction(173456, "CS", 0.5," ", "B", "25-Mar-2019","26-Mar-2019",2354,12.423);
	Instruction instrWrongDate = populateInstruction(183456, "CS", 0.5,"GBP", "B", "25-Mar-2019",null,2354,12.423);
	Instruction instrWrongFxRate = populateInstruction(193456, "CS", -0.2,"GBP", "B", "25-Mar-2019","26-Mar-2019",2354,12.423);
	
	//Buy input for the ranking test case
	Instruction buyInstr1 = populateInstruction(123456, "CS", 0.5,"GBP", "B", "25-Mar-2019","26-Mar-2019",2354,12.423);
	Instruction buyInstr2 = populateInstruction(123457, "CS", 0.3, "GBP","B", "24-Mar-2019","26-Mar-2019",17253,123.45);
	Instruction buyInstr3 = populateInstruction(123458, "CS", 0.59,"GBP", "B", "23-Mar-2019","26-Mar-2019",100,564.9);
	Instruction buyInstr4 = populateInstruction(123459, "CS", 0.8, "GBP","B", "29-Mar-2019","26-Mar-2019",172657,345.9);
	Instruction buyInstr5 = populateInstruction(123450, "CS", 0.12,"GBP", "B", "20-Mar-2019","26-Mar-2019",89,834.8);
	Instruction sameBuyInstr4 = populateInstruction(223459, "CS", 0.8, "GBP","B", "29-Mar-2019","26-Mar-2019",172657,345.9);

	//Sell input for the ranking test case
	Instruction sellInstr1 = populateInstruction(223456, "CS", 0.5,"GBP", "S", "21-Mar-2019","26-Mar-2019",2354,12.423);
	Instruction sellInstr2 = populateInstruction(223457, "CS", 0.3, "GBP","S", "22-Mar-2019","26-Mar-2019",17253,123.45);
	Instruction sellInstr3 = populateInstruction(223458, "CS", 0.59, "GBP","S", "27-Mar-2019","26-Mar-2019",100,564.9);
	Instruction sellInstr4 = populateInstruction(323459, "CS", 0.8,"GBP", "S", "28-Mar-2019","26-Mar-2019",172657,345.9);
	Instruction sellInstr5 = populateInstruction(323450, "CS", 0.12, "GBP","S", "20-Mar-2019","26-Mar-2019",89,834.8);
	Instruction sameSellInstr1 = populateInstruction(323458, "CS", 0.8,"GBP", "S", "28-Mar-2019","26-Mar-2019",172657,345.9);
	
	//Buy input for testing same date amount use case
	Instruction buyInstr11 = populateInstruction(923456, "CS", 0.5,"GBP", "B", "25-Mar-2019","26-Mar-2019",1000,15);
	Instruction buyInstr21 = populateInstruction(923457, "CS", 0.3, "CHF","B", "25-Mar-2019","26-Mar-2019",500,25);
	Instruction buyInstr31 = populateInstruction(923458, "CS", 0.59,"AUD", "B", "25-Mar-2019","26-Mar-2019",100,35);
	Instruction buyInstr41 = populateInstruction(923459, "CS", 0.8, "AED","B", "25-Mar-2019","26-Mar-2019",250,45);
	Instruction buyInstr51 = populateInstruction(923450, "CS", 0.12,"SAR", "B", "25-Mar-2019","26-Mar-2019",450,55);
	
	//Sell input for testing same date amount use case
	Instruction sellInstr11 = populateInstruction(823456, "CS", 0.5,"GBP", "S", "25-Mar-2019","26-Mar-2019",2000,85);
	Instruction sellInstr21 = populateInstruction(823457, "CS", 0.3, "CHF","S", "25-Mar-2019","26-Mar-2019",1500,75);
	Instruction sellInstr31 = populateInstruction(823458, "CS", 0.59, "AUD","S", "25-Mar-2019","26-Mar-2019",1400,65);
	Instruction sellInstr41 = populateInstruction(823459, "CS", 0.8,"AED", "S", "25-Mar-2019","26-Mar-2019",750,55);
	Instruction sellInstr51 = populateInstruction(823450, "CS", 0.12, "SAR","S", "25-Mar-2019","26-Mar-2019",950,50);
	
	//Buy input for testing multiple date amount use case
	Instruction buyInstr12 = populateInstruction(923456, "CS", 0.5,"GBP", "B", "25-Mar-2019","26-Mar-2019",1000,15);
	Instruction buyInstr22 = populateInstruction(923457, "CS", 0.3, "CHF","B", "25-Mar-2019","26-Mar-2019",500,25);
	Instruction buyInstr32 = populateInstruction(923458, "CS", 0.59,"AUD", "B", "25-Mar-2019","27-Mar-2019",100,35);
	Instruction buyInstr42 = populateInstruction(923459, "CS", 0.8, "AED","B", "25-Mar-2019","27-Mar-2019",250,45);
	Instruction buyInstr52 = populateInstruction(923450, "CS", 0.12,"SAR", "B", "25-Mar-2019","28-Mar-2019",450,55);
	Instruction buyInstr62 = populateInstruction(923450, "CS", 1,"USD", "B", "25-Mar-2019","28-Mar-2019",550,80);
	
	//Sell input for testing multiple date amount use case
	Instruction sellInstr12 = populateInstruction(823456, "CS", 0.5,"GBP", "S", "25-Mar-2019","26-Mar-2019",2000,85);
	Instruction sellInstr22 = populateInstruction(823457, "CS", 0.3, "CHF","S", "25-Mar-2019","26-Mar-2019",1500,75);
	Instruction sellInstr32 = populateInstruction(823458, "CS", 0.59, "AUD","S", "25-Mar-2019","27-Mar-2019",1400,65);
	Instruction sellInstr42 = populateInstruction(823459, "CS", 0.8,"AED", "S", "25-Mar-2019","27-Mar-2019",750,55);
	Instruction sellInstr52 = populateInstruction(823450, "CS", 0.12, "SAR","S", "25-Mar-2019","28-Mar-2019",950,50);
	Instruction sellInstr62 = populateInstruction(823450, "CS", 1, "USD","S", "25-Mar-2019","28-Mar-2019",900,75);
	
	//Buy input for testing multiple date amount with non-working date use case
	Instruction buyInstr13 = populateInstruction(923456, "CS", 0.5,"GBP", "B", "20-Mar-2019","23-Mar-2019",1000,15);
	Instruction buyInstr23 = populateInstruction(923457, "CS", 0.3, "CHF","B", "20-Mar-2019","24-Mar-2019",500,25);
	Instruction buyInstr33 = populateInstruction(923458, "CS", 0.59,"AUD", "B", "20-Mar-2019","25-Mar-2019",100,35);
	Instruction buyInstr43 = populateInstruction(923459, "CS", 0.8, "AED","B", "14-Mar-2019","16-Mar-2019",250,45);
	Instruction buyInstr53 = populateInstruction(923450, "CS", 0.12,"SAR", "B", "14-Mar-2019","17-Mar-2019",450,55);
	Instruction buyInstr63 = populateInstruction(923450, "CS", 1,"USD", "B", "14-Mar-2019","17-Mar-2019",550,80);
	
	//Sell input for testing multiple date amount with non-working date use case
	Instruction sellInstr13 = populateInstruction(823456, "CS", 0.5,"GBP", "S", "20-Mar-2019","23-Mar-2019",2000,85);
	Instruction sellInstr23 = populateInstruction(823457, "CS", 0.3, "CHF","S", "20-Mar-2019","24-Mar-2019",1500,75);
	Instruction sellInstr33 = populateInstruction(823458, "CS", 0.59, "AUD","S", "20-Mar-2019","25-Mar-2019",1400,65);
	Instruction sellInstr43 = populateInstruction(823459, "CS", 0.8,"AED", "S", "15-Mar-2019","16-Mar-2019",750,55);
	Instruction sellInstr53 = populateInstruction(823450, "CS", 0.12, "SAR","S", "15-Mar-2019","17-Mar-2019",950,50);
	Instruction sellInstr63 = populateInstruction(823450, "CS", 1, "USD","S", "15-Mar-2019","17-Mar-2019",900,75);


	@Test
	public void testValidationLogic(){
		ITradingService tradingService = new TradingService();
		List<Instruction> instructionList = new ArrayList<>();
		instructionList.add(instrTestName);
		instructionList.add(instrWrongCurrency);
		instructionList.add(instrWrongDate);
		instructionList.add(instrWrongFxRate);
		instructionList.add(instrWrongPrice);
		instructionList.add(instrWrongUnits);
		List<AmountSettlementDTO> list = tradingService.calculateBuyAmtSettledForDate(instructionList);
		Assert.assertTrue("Expected the list size as 0 but got "+list.size(), list.size()==0);
	}
	@Test
	public void testRankingBuyOnlyCase(){
		
		ITradingService tradingService = new TradingService();
		List<Instruction> instructionList = new ArrayList<>();
		instructionList.add(buyInstr1);
		instructionList.add(buyInstr2);
		instructionList.add(buyInstr3);
		instructionList.add(buyInstr4);
		instructionList.add(buyInstr5);
		List<Instruction> list = tradingService.fetchRankingBasedOnInstructionType(instructionList, InstructionType.BUY);
		Assert.assertTrue("Expected the list size as 5 but got "+list.size(), list.size()==5);
		Assert.assertTrue("Expected the highest ranking as 123459 but got as "+list.get(0).getId(), list.get(0).getId()==123459);
	}
	
	@Test
	public void testRankingSellOnlyCase(){
		
		ITradingService tradingService = new TradingService();
		List<Instruction> instructionList = new ArrayList<>();
		instructionList.add(sellInstr1);
		instructionList.add(sellInstr2);
		instructionList.add(sellInstr3);
		instructionList.add(sellInstr4);
		instructionList.add(sellInstr5);
		List<Instruction> list = tradingService.fetchRankingBasedOnInstructionType(instructionList, InstructionType.SELL);
		Assert.assertTrue("Expected the list size as 5 but got "+list.size(), list.size()==5);
		Assert.assertTrue("Expected the highest ranking as 323459 but got as "+list.get(0).getId(), list.get(0).getId()==323459);
	}
	
	@Test
	public void testRankingSellHavingSameAmount(){
		
		ITradingService tradingService = new TradingService();
		List<Instruction> instructionList = new ArrayList<>();
		instructionList.add(sellInstr1);
		instructionList.add(sellInstr2);
		instructionList.add(sellInstr3);
		instructionList.add(sellInstr4);
		instructionList.add(sellInstr5);
		instructionList.add(sameSellInstr1);
		List<Instruction> list = tradingService.fetchRankingBasedOnInstructionType(instructionList, InstructionType.SELL);
		Assert.assertTrue("Expected the list size as 6 but got "+list.size(), list.size()==6);
		Assert.assertTrue("Expected the highest ranking as 323459 or 323458 but got as "+list.get(0).getId(), list.get(0).getId()==323459 || list.get(0).getId()==323458);
	}
	
	@Test
	public void testRankingBuyHavingSameAmount(){
		
		ITradingService tradingService = new TradingService();
		List<Instruction> instructionList = new ArrayList<>();
		instructionList.add(buyInstr1);
		instructionList.add(buyInstr2);
		instructionList.add(buyInstr3);
		instructionList.add(buyInstr4);
		instructionList.add(buyInstr5);
		instructionList.add(sameBuyInstr4);
		List<Instruction> list = tradingService.fetchRankingBasedOnInstructionType(instructionList, InstructionType.BUY);
		Assert.assertTrue("Expected the list size as 6 but got "+list.size(), list.size()==6);
		Assert.assertTrue("Expected the highest ranking as 223459 or 123459 but got as "+list.get(0).getId(), list.get(0).getId()==223459 || list.get(0).getId()==123459);
	}

	@Test
	public void fetchBuySellSameSettlementAmountForDate(){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
		ITradingService tradingService = new TradingService();
		List<Instruction> instructionList = new ArrayList<>();
		instructionList.add(buyInstr11);
		instructionList.add(buyInstr21);
		instructionList.add(buyInstr31);
		instructionList.add(buyInstr41);
		instructionList.add(buyInstr51);
		instructionList.add(sellInstr11);
		instructionList.add(sellInstr21);
		instructionList.add(sellInstr31);
		instructionList.add(sellInstr41);
		instructionList.add(sellInstr51);
		List<AmountSettlementDTO> amountSettlementDTOs = tradingService.calculateBuyAmtSettledForDate(instructionList);
		Assert.assertTrue("Expected the amountSettlementDTOs to not be null but it is null", amountSettlementDTOs!=null);
		Assert.assertTrue("Expected the list size as 1 but got "+amountSettlementDTOs.size(), amountSettlementDTOs.size()==1);
		Assert.assertTrue("Expected the settlement date to be 26-Mar-2019 but got "+amountSettlementDTOs.get(0).getSettlementDate(), amountSettlementDTOs.get(0).getSettlementDate().equals(LocalDate.parse("26-Mar-2019", formatter)));
		Assert.assertTrue("Expected the amount to be 25285 but got "+amountSettlementDTOs.get(0).getAmount(), amountSettlementDTOs.get(0).getAmount()==25285);
		amountSettlementDTOs = tradingService.calculateSellAmtSettledForDate(instructionList);
		Assert.assertTrue("Expected the amountSettlementDTOs to not be null but it is null", amountSettlementDTOs!=null);
		Assert.assertTrue("Expected the list size as 1 but got "+amountSettlementDTOs.size(), amountSettlementDTOs.size()==1);
		Assert.assertTrue("Expected the settlement date to be 26-Mar-2019 but got "+amountSettlementDTOs.get(0).getSettlementDate(), amountSettlementDTOs.get(0).getSettlementDate().equals(LocalDate.parse("26-Mar-2019", formatter)));
		Assert.assertTrue("Expected the amount to be -211140 but got "+amountSettlementDTOs.get(0).getAmount(), amountSettlementDTOs.get(0).getAmount()==-211140);
	}
	
	@Test
	public void fetchBuySellMultipleSettlementAmountForDate(){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
		ITradingService tradingService = new TradingService();
		List<Instruction> instructionList = new ArrayList<>();
		instructionList.add(buyInstr12);
		instructionList.add(buyInstr22);
		instructionList.add(buyInstr32);
		instructionList.add(buyInstr42);
		instructionList.add(buyInstr52);
		instructionList.add(buyInstr62);
		instructionList.add(sellInstr12);
		instructionList.add(sellInstr22);
		instructionList.add(sellInstr32);
		instructionList.add(sellInstr42);
		instructionList.add(sellInstr52);
		instructionList.add(sellInstr62);
		List<AmountSettlementDTO> amountSettlementDTOs = tradingService.calculateBuyAmtSettledForDate(instructionList);
		Assert.assertTrue("Expected the amountSettlementDTOs to not be null but it is null", amountSettlementDTOs!=null);
		Assert.assertTrue("Expected the list size as 3 but got "+amountSettlementDTOs.size(), amountSettlementDTOs.size()==3);
		for(AmountSettlementDTO amountSettlementDTO: amountSettlementDTOs){
			if(amountSettlementDTO.getSettlementDate().equals(LocalDate.parse("26-Mar-2019", formatter))){
				Assert.assertTrue("Expected the amount to be 11250 but got "+amountSettlementDTO.getAmount(),amountSettlementDTO.getAmount()==11250);
			}else if(amountSettlementDTO.getSettlementDate().equals(LocalDate.parse("27-Mar-2019", formatter))){
				Assert.assertTrue("Expected the amount to be 11065 but got "+amountSettlementDTO.getAmount(),amountSettlementDTO.getAmount()==11065);
			}else if(amountSettlementDTO.getSettlementDate().equals(LocalDate.parse("28-Mar-2019", formatter))){
				Assert.assertTrue("Expected the amount to be 46970 but got "+amountSettlementDTO.getAmount(),amountSettlementDTO.getAmount()==46970);
			}
		}
		amountSettlementDTOs = tradingService.calculateSellAmtSettledForDate(instructionList);
		Assert.assertTrue("Expected the amountSettlementDTOs to not be null but it is null", amountSettlementDTOs!=null);
		Assert.assertTrue("Expected the list size as 3 but got "+amountSettlementDTOs.size(), amountSettlementDTOs.size()==3);
		for(AmountSettlementDTO amountSettlementDTO: amountSettlementDTOs){
			if(amountSettlementDTO.getSettlementDate().equals(LocalDate.parse("26-Mar-2019", formatter))){
				Assert.assertTrue("Expected the amount to be -118750 but got "+amountSettlementDTO.getAmount(),amountSettlementDTO.getAmount()==-118750);
			}else if(amountSettlementDTO.getSettlementDate().equals(LocalDate.parse("27-Mar-2019", formatter))){
				Assert.assertTrue("Expected the amount to be -86690 but got "+amountSettlementDTO.getAmount(),amountSettlementDTO.getAmount()==-86690);
			}else if(amountSettlementDTO.getSettlementDate().equals(LocalDate.parse("28-Mar-2019", formatter))){
				Assert.assertTrue("Expected the amount to be -73200 but got "+amountSettlementDTO.getAmount(),amountSettlementDTO.getAmount()==-73200);
			}
		}
	}
	
	@Test
	public void fetchBuySellMultipleSettlementAmountForDate_NonWorkingDates(){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
		ITradingService tradingService = new TradingService();
		List<Instruction> instructionList = new ArrayList<>();
		instructionList.add(buyInstr13);
		instructionList.add(buyInstr23);
		instructionList.add(buyInstr33);
		instructionList.add(buyInstr43);
		instructionList.add(buyInstr53);
		instructionList.add(buyInstr63);
		instructionList.add(sellInstr13);
		instructionList.add(sellInstr23);
		instructionList.add(sellInstr33);
		instructionList.add(sellInstr43);
		instructionList.add(sellInstr53);
		instructionList.add(sellInstr63);
		List<AmountSettlementDTO> amountSettlementDTOs = tradingService.calculateBuyAmtSettledForDate(instructionList);
		Assert.assertTrue("Expected the amountSettlementDTOs to not be null but it is null", amountSettlementDTOs!=null);
		Assert.assertTrue("Expected the list size as 2 but got "+amountSettlementDTOs.size(), amountSettlementDTOs.size()==2);
		for(AmountSettlementDTO amountSettlementDTO: amountSettlementDTOs){
			if(amountSettlementDTO.getSettlementDate().equals(LocalDate.parse("25-Mar-2019", formatter))){
				Assert.assertTrue("Expected the amount to be 13315 but got "+amountSettlementDTO.getAmount(),amountSettlementDTO.getAmount()==13315);
			}else if(amountSettlementDTO.getSettlementDate().equals(LocalDate.parse("18-Mar-2019", formatter))){
				Assert.assertTrue("Expected the amount to be 55970 but got "+amountSettlementDTO.getAmount(),amountSettlementDTO.getAmount()==55970);
			}
		}
		amountSettlementDTOs = tradingService.calculateSellAmtSettledForDate(instructionList);
		Assert.assertTrue("Expected the amountSettlementDTOs to not be null but it is null", amountSettlementDTOs!=null);
		Assert.assertTrue("Expected the list size as 2 but got "+amountSettlementDTOs.size(), amountSettlementDTOs.size()==2);
		for(AmountSettlementDTO amountSettlementDTO: amountSettlementDTOs){
			if(amountSettlementDTO.getSettlementDate().equals(LocalDate.parse("25-Mar-2019", formatter))){
				Assert.assertTrue("Expected the amount to be -172440 but got "+amountSettlementDTO.getAmount(),amountSettlementDTO.getAmount()==-172440);
			}else if(amountSettlementDTO.getSettlementDate().equals(LocalDate.parse("18-Mar-2019", formatter))){
				Assert.assertTrue("Expected the amount to be -106200 but got "+amountSettlementDTO.getAmount(),amountSettlementDTO.getAmount()==-106200);
			}
		}
	}

	private Instruction populateInstruction(int id, String assetName, double foreignExchangeRate, String tradingCurrency, String instructionType, String instuctionDate, String settlementDate, int units,
			double price) {
		Instruction instruction = new Instruction();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
		instruction.setId(id);
		Asset asset = new Asset();
		asset.setName(assetName);
		asset.setPrice(price);
		instruction.setAsset(asset);
		instruction.setTradingCurrency(tradingCurrency);
		instruction.setInstructionDate(LocalDate.parse(instuctionDate, formatter));
		if(settlementDate!=null){
			instruction.setSettlementDate(LocalDate.parse(settlementDate, formatter));
		}
		instruction.setInstructionType(InstructionType.fromValue(instructionType));
		instruction.setUnits(units);
		instruction.setForeignExchangeRate(foreignExchangeRate);
		return instruction;
	}
}
