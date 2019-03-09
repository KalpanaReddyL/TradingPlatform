package com.company.project.trading.appl;

import java.util.List;

import com.company.project.trading.dto.AmountSettlementDTO;
import com.company.project.trading.model.Instruction;
import com.company.project.trading.service.ITradingService;
import com.company.project.trading.service.TradingService;
import com.company.project.trading.util.TradingUtil;

/**
 * Main application for testing the trading reports
 * @author kalpana
 *
 */
public class TradingPlatformApplication {

	public static void main(String[] args) {
		System.out.println("TradingPlatformApplication: entering main method ");
		ITradingService iTradingService = new TradingService();
		TradingUtil tradingUtil = new TradingUtil();
		List<Instruction> instructionList = tradingUtil.fetchInstructions();
		if(instructionList!=null){
			System.out.println("TradingPlatformApplication: main method - instruction size which are processed"+instructionList.size());
			List<AmountSettlementDTO> amountSettlementDTOs = iTradingService.calculateBuyAmtSettledForDate(instructionList);
			if(amountSettlementDTOs!=null){
				System.out.println("TradingPlatformApplication: main method - number of days instructions are purchased as per report "+amountSettlementDTOs.size());
			}
		}else{
			System.out.println("TradingPlatformApplication: main method - no instructions were processed");
		}
		System.out.println("TradingPlatformApplication: exiting main method ");
	}
}
