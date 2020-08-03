package com.newland.corpxin.model;

import lombok.Data;
/**
 * @Description:
 * @Author: Ljh
 * @Date 2020/7/22 9:54
 */
@Data
public class BasicInfo {
    private String id;

    private String entName;

    private String legalPerson;

    private String openStatus;

    private String regCapital;

    private String realCapital;

    private String industry;

    private String unifiedCode;

    private String taxNo;

    private String licenseNumber;

    private String orgNo;

    private String authority;

    private String startDate;

    private String entType;

    private String openStart;

    private String openEnd;

    private String district;

    private String annualDate;

    private String regAddr;

    private String scope;

    private String endDate;

    private Long lastUpdateTimestamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName == null ? null : entName.trim();
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson == null ? null : legalPerson.trim();
    }

    public String getOpenStatus() {
        return openStatus;
    }

    public void setOpenStatus(String openStatus) {
        this.openStatus = openStatus == null ? null : openStatus.trim();
    }

    public String getRegCapital() {
        return regCapital;
    }

    public void setRegCapital(String regCapital) {
        this.regCapital = regCapital == null ? null : regCapital.trim();
    }

    public String getRealCapital() {
        return realCapital;
    }

    public void setRealCapital(String realCapital) {
        this.realCapital = realCapital == null ? null : realCapital.trim();
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry == null ? null : industry.trim();
    }

    public String getUnifiedCode() {
        return unifiedCode;
    }

    public void setUnifiedCode(String unifiedCode) {
        this.unifiedCode = unifiedCode == null ? null : unifiedCode.trim();
    }

    public String getTaxNo() {
        return taxNo;
    }

    public void setTaxNo(String taxNo) {
        this.taxNo = taxNo == null ? null : taxNo.trim();
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber == null ? null : licenseNumber.trim();
    }

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo == null ? null : orgNo.trim();
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority == null ? null : authority.trim();
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate == null ? null : startDate.trim();
    }

    public String getEntType() {
        return entType;
    }

    public void setEntType(String entType) {
        this.entType = entType == null ? null : entType.trim();
    }

    public String getOpenStart() {
        return openStart;
    }

    public void setOpenStart(String openStart) {
        this.openStart = openStart == null ? null : openStart.trim();
    }

    public String getOpenEnd() {
        return openEnd;
    }

    public void setOpenEnd(String openEnd) {
        this.openEnd = openEnd == null ? null : openEnd.trim();
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district == null ? null : district.trim();
    }

    public String getAnnualDate() {
        return annualDate;
    }

    public void setAnnualDate(String annualDate) {
        this.annualDate = annualDate == null ? null : annualDate.trim();
    }

    public String getRegAddr() {
        return regAddr;
    }

    public void setRegAddr(String regAddr) {
        this.regAddr = regAddr == null ? null : regAddr.trim();
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope == null ? null : scope.trim();
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate == null ? null : endDate.trim();
    }

    public Long getLastUpdateTimestamp() {
        return lastUpdateTimestamp;
    }

    public void setLastUpdateTimestamp(Long lastUpdateTimestamp) {
        this.lastUpdateTimestamp = lastUpdateTimestamp;
    }
}