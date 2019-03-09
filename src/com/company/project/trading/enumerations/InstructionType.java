package com.company.project.trading.enumerations;
/**
 * Enum for sell and buy
 * @author kalpana
 *
 */
public enum InstructionType {

	BUY("B"), SELL("S");
	
	private InstructionType(String value) {
		this.value = value;
	}

	private String value;
	
	@Override
	public String toString() {
		return value;
	}
	
	/**
	 * To retrieve the Instruction Type from the String value
	 * @param value the string 
	 * @return
	 */
	public static InstructionType fromValue(String value) {
		for (InstructionType type : InstructionType.values()) {
			if (type.toString().equals(value)) {
				return type;
			}
		}
		return null;
	}
	
}
