package com.company.project.trading.assembler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.company.project.trading.dto.AmountSettlementDTO;

/**
 * This class is used for data conversion where we are converting to and from DTOs
 * @author kalpana
 *
 */
public class TradingPlatformAssembler {
	
	/**
	 * Convert the map holding the date and amount into a list amount settlement dto
	 * which holds the date and the amount of all the trades settled that day
	 * @param amountSettlementDateMap
	 * @return list of amountsettlementDTO
	 */
	public List<AmountSettlementDTO> toClient(Map<LocalDate, Double> amountSettlementDateMap){
		System.out.println("TradingPlatformAssembler: entering toClient method");
		List<AmountSettlementDTO> amountSettlementDtoList = new ArrayList<>();
		System.out.println("TradingPlatformAssembler: entering toClient method");
		if(amountSettlementDateMap!=null){
			System.out.println("TradingPlatformAssembler: toClient method - before map to dto conversion");
			for(Entry<LocalDate, Double> mapKeyValue : amountSettlementDateMap.entrySet()){
				AmountSettlementDTO amountSettlementDTO = new AmountSettlementDTO();
				amountSettlementDTO.setSettlementDate(mapKeyValue.getKey());
				amountSettlementDTO.setAmount(mapKeyValue.getValue());
				amountSettlementDtoList.add(amountSettlementDTO);
			}
			System.out.println("TradingPlatformAssembler: toClient method - after map to dto conversion");
		}else{
			System.out.println("TradingPlatformAssembler: toClient method - no elements to perform conversion from map to dto");
		}
		System.out.println("TradingPlatformAssembler: exiting toClient method");
		return amountSettlementDtoList;
	}

}
