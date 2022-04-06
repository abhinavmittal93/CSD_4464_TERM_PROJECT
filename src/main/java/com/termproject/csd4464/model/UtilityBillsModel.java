/**
 * 
 */
package com.termproject.csd4464.model;

import java.sql.Date;

/**
 * @author abhinavmittal
 *
 */
public class UtilityBillsModel {

	private Long utilityBillId;

	private UtilitiesModel utilitiesModel;

	private Double balance;

	private String status;

	private Date generatedOn;

	private Date paidOn;

	/**
	 * @return the utilityBillId
	 */
	public Long getUtilityBillId() {
		return utilityBillId;
	}

	/**
	 * @param utilityBillId the utilityBillId to set
	 */
	public void setUtilityBillId(Long utilityBillId) {
		this.utilityBillId = utilityBillId;
	}

	/**
	 * @return the utilitiesModel
	 */
	public UtilitiesModel getUtilitiesModel() {
		return utilitiesModel;
	}

	/**
	 * @param utilitiesModel the utilitiesModel to set
	 */
	public void setUtilitiesModel(UtilitiesModel utilitiesModel) {
		this.utilitiesModel = utilitiesModel;
	}

	/**
	 * @return the balance
	 */
	public Double getBalance() {
		return balance;
	}

	/**
	 * @param balance the balance to set
	 */
	public void setBalance(Double balance) {
		this.balance = balance;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the generatedOn
	 */
	public Date getGeneratedOn() {
		return generatedOn;
	}

	/**
	 * @param generatedOn the generatedOn to set
	 */
	public void setGeneratedOn(Date generatedOn) {
		this.generatedOn = generatedOn;
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
