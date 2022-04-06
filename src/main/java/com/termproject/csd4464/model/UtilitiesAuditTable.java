/**
 * 
 */
package com.termproject.csd4464.model;

import java.sql.Date;

/**
 * @author abhinavmittal
 *
 */
public class UtilitiesAuditTable {

	private Long utilityAuditId;

	private UtilityBillsModel utilityBillsModel;

	private Double amount;

	private Date paidOn;

	/**
	 * @return the utilityAuditId
	 */
	public Long getUtilityAuditId() {
		return utilityAuditId;
	}

	/**
	 * @param utilityAuditId the utilityAuditId to set
	 */
	public void setUtilityAuditId(Long utilityAuditId) {
		this.utilityAuditId = utilityAuditId;
	}

	/**
	 * @return the utilityBillsModel
	 */
	public UtilityBillsModel getUtilityBillsModel() {
		return utilityBillsModel;
	}

	/**
	 * @param utilityBillsModel the utilityBillsModel to set
	 */
	public void setUtilityBillsModel(UtilityBillsModel utilityBillsModel) {
		this.utilityBillsModel = utilityBillsModel;
	}

	/**
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/**
	 * @return the paidOn
	 */
	public Date getPaidOn() {
		return paidOn;
	}

	/**
	 * @param paidOn the paidOn to set
	 */
	public void setPaidOn(Date paidOn) {
		this.paidOn = paidOn;
	}

}
