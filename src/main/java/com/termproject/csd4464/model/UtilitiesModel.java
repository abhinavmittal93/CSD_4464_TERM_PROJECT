/**
 * 
 */
package com.termproject.csd4464.model;

/**
 * @author abhinavmittal
 * 
 * Model class for table "utilities" with utilityId, utilityType, utilityName.
 *
 */
public class UtilitiesModel {

	private Long utilityId;

	private String utilityType;

	private String utilityName;

	/**
	 * @return the utilityId
	 */
	public Long getUtilityId() {
		return utilityId;
	}

	/**
	 * @param utilityId the utilityId to set
	 */
	public void setUtilityId(Long utilityId) {
		this.utilityId = utilityId;
	}

	/**
	 * @return the utilityType
	 */
	public String getUtilityType() {
		return utilityType;
	}

	/**
	 * @param utilityType the utilityType to set
	 */
	public void setUtilityType(String utilityType) {
		this.utilityType = utilityType;
	}

	/**
	 * @return the utilityName
	 */
	public String getUtilityName() {
		return utilityName;
	}

	/**
	 * @param utilityName the utilityName to set
	 */
	public void setUtilityName(String utilityName) {
		this.utilityName = utilityName;
	}

}
