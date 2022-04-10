/**
 * 
 */
package com.termproject.csd4464.model;

/**
 * @author abhinavmittal
 * 
 * Model class for table "bank_types" with bankTypeId, bankType.
 *
 */
public class BankTypesModel {

	private Long bankTypeId;

	private String bankType;

	/**
	 * @return the bankTypeId
	 */
	public Long getBankTypeId() {
		return bankTypeId;
	}

	/**
	 * @param bankTypeId the bankTypeId to set
	 */
	public void setBankTypeId(Long bankTypeId) {
		this.bankTypeId = bankTypeId;
	}

	/**
	 * @return the banktype
	 */
	public String getBankType() {
		return bankType;
	}

	/**
	 * @param banktype the banktype to set
	 */
	public void setBankType(String banktype) {
		this.bankType = banktype;
	}

}
