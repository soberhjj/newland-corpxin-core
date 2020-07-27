package com.newland.corpxin.model;

import java.util.ArrayList;
import java.util.List;

public class BasicInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BasicInfoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andEntNameIsNull() {
            addCriterion("ent_name is null");
            return (Criteria) this;
        }

        public Criteria andEntNameIsNotNull() {
            addCriterion("ent_name is not null");
            return (Criteria) this;
        }

        public Criteria andEntNameEqualTo(String value) {
            addCriterion("ent_name =", value, "entName");
            return (Criteria) this;
        }

        public Criteria andEntNameNotEqualTo(String value) {
            addCriterion("ent_name <>", value, "entName");
            return (Criteria) this;
        }

        public Criteria andEntNameGreaterThan(String value) {
            addCriterion("ent_name >", value, "entName");
            return (Criteria) this;
        }

        public Criteria andEntNameGreaterThanOrEqualTo(String value) {
            addCriterion("ent_name >=", value, "entName");
            return (Criteria) this;
        }

        public Criteria andEntNameLessThan(String value) {
            addCriterion("ent_name <", value, "entName");
            return (Criteria) this;
        }

        public Criteria andEntNameLessThanOrEqualTo(String value) {
            addCriterion("ent_name <=", value, "entName");
            return (Criteria) this;
        }

        public Criteria andEntNameLike(String value) {
            addCriterion("ent_name like", value, "entName");
            return (Criteria) this;
        }

        public Criteria andEntNameNotLike(String value) {
            addCriterion("ent_name not like", value, "entName");
            return (Criteria) this;
        }

        public Criteria andEntNameIn(List<String> values) {
            addCriterion("ent_name in", values, "entName");
            return (Criteria) this;
        }

        public Criteria andEntNameNotIn(List<String> values) {
            addCriterion("ent_name not in", values, "entName");
            return (Criteria) this;
        }

        public Criteria andEntNameBetween(String value1, String value2) {
            addCriterion("ent_name between", value1, value2, "entName");
            return (Criteria) this;
        }

        public Criteria andEntNameNotBetween(String value1, String value2) {
            addCriterion("ent_name not between", value1, value2, "entName");
            return (Criteria) this;
        }

        public Criteria andLegalPersonIsNull() {
            addCriterion("legal_person is null");
            return (Criteria) this;
        }

        public Criteria andLegalPersonIsNotNull() {
            addCriterion("legal_person is not null");
            return (Criteria) this;
        }

        public Criteria andLegalPersonEqualTo(String value) {
            addCriterion("legal_person =", value, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonNotEqualTo(String value) {
            addCriterion("legal_person <>", value, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonGreaterThan(String value) {
            addCriterion("legal_person >", value, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonGreaterThanOrEqualTo(String value) {
            addCriterion("legal_person >=", value, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonLessThan(String value) {
            addCriterion("legal_person <", value, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonLessThanOrEqualTo(String value) {
            addCriterion("legal_person <=", value, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonLike(String value) {
            addCriterion("legal_person like", value, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonNotLike(String value) {
            addCriterion("legal_person not like", value, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonIn(List<String> values) {
            addCriterion("legal_person in", values, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonNotIn(List<String> values) {
            addCriterion("legal_person not in", values, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonBetween(String value1, String value2) {
            addCriterion("legal_person between", value1, value2, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andLegalPersonNotBetween(String value1, String value2) {
            addCriterion("legal_person not between", value1, value2, "legalPerson");
            return (Criteria) this;
        }

        public Criteria andOpenStatusIsNull() {
            addCriterion("open_status is null");
            return (Criteria) this;
        }

        public Criteria andOpenStatusIsNotNull() {
            addCriterion("open_status is not null");
            return (Criteria) this;
        }

        public Criteria andOpenStatusEqualTo(String value) {
            addCriterion("open_status =", value, "openStatus");
            return (Criteria) this;
        }

        public Criteria andOpenStatusNotEqualTo(String value) {
            addCriterion("open_status <>", value, "openStatus");
            return (Criteria) this;
        }

        public Criteria andOpenStatusGreaterThan(String value) {
            addCriterion("open_status >", value, "openStatus");
            return (Criteria) this;
        }

        public Criteria andOpenStatusGreaterThanOrEqualTo(String value) {
            addCriterion("open_status >=", value, "openStatus");
            return (Criteria) this;
        }

        public Criteria andOpenStatusLessThan(String value) {
            addCriterion("open_status <", value, "openStatus");
            return (Criteria) this;
        }

        public Criteria andOpenStatusLessThanOrEqualTo(String value) {
            addCriterion("open_status <=", value, "openStatus");
            return (Criteria) this;
        }

        public Criteria andOpenStatusLike(String value) {
            addCriterion("open_status like", value, "openStatus");
            return (Criteria) this;
        }

        public Criteria andOpenStatusNotLike(String value) {
            addCriterion("open_status not like", value, "openStatus");
            return (Criteria) this;
        }

        public Criteria andOpenStatusIn(List<String> values) {
            addCriterion("open_status in", values, "openStatus");
            return (Criteria) this;
        }

        public Criteria andOpenStatusNotIn(List<String> values) {
            addCriterion("open_status not in", values, "openStatus");
            return (Criteria) this;
        }

        public Criteria andOpenStatusBetween(String value1, String value2) {
            addCriterion("open_status between", value1, value2, "openStatus");
            return (Criteria) this;
        }

        public Criteria andOpenStatusNotBetween(String value1, String value2) {
            addCriterion("open_status not between", value1, value2, "openStatus");
            return (Criteria) this;
        }

        public Criteria andRegCapitalIsNull() {
            addCriterion("reg_capital is null");
            return (Criteria) this;
        }

        public Criteria andRegCapitalIsNotNull() {
            addCriterion("reg_capital is not null");
            return (Criteria) this;
        }

        public Criteria andRegCapitalEqualTo(String value) {
            addCriterion("reg_capital =", value, "regCapital");
            return (Criteria) this;
        }

        public Criteria andRegCapitalNotEqualTo(String value) {
            addCriterion("reg_capital <>", value, "regCapital");
            return (Criteria) this;
        }

        public Criteria andRegCapitalGreaterThan(String value) {
            addCriterion("reg_capital >", value, "regCapital");
            return (Criteria) this;
        }

        public Criteria andRegCapitalGreaterThanOrEqualTo(String value) {
            addCriterion("reg_capital >=", value, "regCapital");
            return (Criteria) this;
        }

        public Criteria andRegCapitalLessThan(String value) {
            addCriterion("reg_capital <", value, "regCapital");
            return (Criteria) this;
        }

        public Criteria andRegCapitalLessThanOrEqualTo(String value) {
            addCriterion("reg_capital <=", value, "regCapital");
            return (Criteria) this;
        }

        public Criteria andRegCapitalLike(String value) {
            addCriterion("reg_capital like", value, "regCapital");
            return (Criteria) this;
        }

        public Criteria andRegCapitalNotLike(String value) {
            addCriterion("reg_capital not like", value, "regCapital");
            return (Criteria) this;
        }

        public Criteria andRegCapitalIn(List<String> values) {
            addCriterion("reg_capital in", values, "regCapital");
            return (Criteria) this;
        }

        public Criteria andRegCapitalNotIn(List<String> values) {
            addCriterion("reg_capital not in", values, "regCapital");
            return (Criteria) this;
        }

        public Criteria andRegCapitalBetween(String value1, String value2) {
            addCriterion("reg_capital between", value1, value2, "regCapital");
            return (Criteria) this;
        }

        public Criteria andRegCapitalNotBetween(String value1, String value2) {
            addCriterion("reg_capital not between", value1, value2, "regCapital");
            return (Criteria) this;
        }

        public Criteria andRealCapitalIsNull() {
            addCriterion("real_capital is null");
            return (Criteria) this;
        }

        public Criteria andRealCapitalIsNotNull() {
            addCriterion("real_capital is not null");
            return (Criteria) this;
        }

        public Criteria andRealCapitalEqualTo(String value) {
            addCriterion("real_capital =", value, "realCapital");
            return (Criteria) this;
        }

        public Criteria andRealCapitalNotEqualTo(String value) {
            addCriterion("real_capital <>", value, "realCapital");
            return (Criteria) this;
        }

        public Criteria andRealCapitalGreaterThan(String value) {
            addCriterion("real_capital >", value, "realCapital");
            return (Criteria) this;
        }

        public Criteria andRealCapitalGreaterThanOrEqualTo(String value) {
            addCriterion("real_capital >=", value, "realCapital");
            return (Criteria) this;
        }

        public Criteria andRealCapitalLessThan(String value) {
            addCriterion("real_capital <", value, "realCapital");
            return (Criteria) this;
        }

        public Criteria andRealCapitalLessThanOrEqualTo(String value) {
            addCriterion("real_capital <=", value, "realCapital");
            return (Criteria) this;
        }

        public Criteria andRealCapitalLike(String value) {
            addCriterion("real_capital like", value, "realCapital");
            return (Criteria) this;
        }

        public Criteria andRealCapitalNotLike(String value) {
            addCriterion("real_capital not like", value, "realCapital");
            return (Criteria) this;
        }

        public Criteria andRealCapitalIn(List<String> values) {
            addCriterion("real_capital in", values, "realCapital");
            return (Criteria) this;
        }

        public Criteria andRealCapitalNotIn(List<String> values) {
            addCriterion("real_capital not in", values, "realCapital");
            return (Criteria) this;
        }

        public Criteria andRealCapitalBetween(String value1, String value2) {
            addCriterion("real_capital between", value1, value2, "realCapital");
            return (Criteria) this;
        }

        public Criteria andRealCapitalNotBetween(String value1, String value2) {
            addCriterion("real_capital not between", value1, value2, "realCapital");
            return (Criteria) this;
        }

        public Criteria andIndustryIsNull() {
            addCriterion("industry is null");
            return (Criteria) this;
        }

        public Criteria andIndustryIsNotNull() {
            addCriterion("industry is not null");
            return (Criteria) this;
        }

        public Criteria andIndustryEqualTo(String value) {
            addCriterion("industry =", value, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryNotEqualTo(String value) {
            addCriterion("industry <>", value, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryGreaterThan(String value) {
            addCriterion("industry >", value, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryGreaterThanOrEqualTo(String value) {
            addCriterion("industry >=", value, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryLessThan(String value) {
            addCriterion("industry <", value, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryLessThanOrEqualTo(String value) {
            addCriterion("industry <=", value, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryLike(String value) {
            addCriterion("industry like", value, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryNotLike(String value) {
            addCriterion("industry not like", value, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryIn(List<String> values) {
            addCriterion("industry in", values, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryNotIn(List<String> values) {
            addCriterion("industry not in", values, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryBetween(String value1, String value2) {
            addCriterion("industry between", value1, value2, "industry");
            return (Criteria) this;
        }

        public Criteria andIndustryNotBetween(String value1, String value2) {
            addCriterion("industry not between", value1, value2, "industry");
            return (Criteria) this;
        }

        public Criteria andUnifiedCodeIsNull() {
            addCriterion("unified_code is null");
            return (Criteria) this;
        }

        public Criteria andUnifiedCodeIsNotNull() {
            addCriterion("unified_code is not null");
            return (Criteria) this;
        }

        public Criteria andUnifiedCodeEqualTo(String value) {
            addCriterion("unified_code =", value, "unifiedCode");
            return (Criteria) this;
        }

        public Criteria andUnifiedCodeNotEqualTo(String value) {
            addCriterion("unified_code <>", value, "unifiedCode");
            return (Criteria) this;
        }

        public Criteria andUnifiedCodeGreaterThan(String value) {
            addCriterion("unified_code >", value, "unifiedCode");
            return (Criteria) this;
        }

        public Criteria andUnifiedCodeGreaterThanOrEqualTo(String value) {
            addCriterion("unified_code >=", value, "unifiedCode");
            return (Criteria) this;
        }

        public Criteria andUnifiedCodeLessThan(String value) {
            addCriterion("unified_code <", value, "unifiedCode");
            return (Criteria) this;
        }

        public Criteria andUnifiedCodeLessThanOrEqualTo(String value) {
            addCriterion("unified_code <=", value, "unifiedCode");
            return (Criteria) this;
        }

        public Criteria andUnifiedCodeLike(String value) {
            addCriterion("unified_code like", value, "unifiedCode");
            return (Criteria) this;
        }

        public Criteria andUnifiedCodeNotLike(String value) {
            addCriterion("unified_code not like", value, "unifiedCode");
            return (Criteria) this;
        }

        public Criteria andUnifiedCodeIn(List<String> values) {
            addCriterion("unified_code in", values, "unifiedCode");
            return (Criteria) this;
        }

        public Criteria andUnifiedCodeNotIn(List<String> values) {
            addCriterion("unified_code not in", values, "unifiedCode");
            return (Criteria) this;
        }

        public Criteria andUnifiedCodeBetween(String value1, String value2) {
            addCriterion("unified_code between", value1, value2, "unifiedCode");
            return (Criteria) this;
        }

        public Criteria andUnifiedCodeNotBetween(String value1, String value2) {
            addCriterion("unified_code not between", value1, value2, "unifiedCode");
            return (Criteria) this;
        }

        public Criteria andTaxNoIsNull() {
            addCriterion("tax_no is null");
            return (Criteria) this;
        }

        public Criteria andTaxNoIsNotNull() {
            addCriterion("tax_no is not null");
            return (Criteria) this;
        }

        public Criteria andTaxNoEqualTo(String value) {
            addCriterion("tax_no =", value, "taxNo");
            return (Criteria) this;
        }

        public Criteria andTaxNoNotEqualTo(String value) {
            addCriterion("tax_no <>", value, "taxNo");
            return (Criteria) this;
        }

        public Criteria andTaxNoGreaterThan(String value) {
            addCriterion("tax_no >", value, "taxNo");
            return (Criteria) this;
        }

        public Criteria andTaxNoGreaterThanOrEqualTo(String value) {
            addCriterion("tax_no >=", value, "taxNo");
            return (Criteria) this;
        }

        public Criteria andTaxNoLessThan(String value) {
            addCriterion("tax_no <", value, "taxNo");
            return (Criteria) this;
        }

        public Criteria andTaxNoLessThanOrEqualTo(String value) {
            addCriterion("tax_no <=", value, "taxNo");
            return (Criteria) this;
        }

        public Criteria andTaxNoLike(String value) {
            addCriterion("tax_no like", value, "taxNo");
            return (Criteria) this;
        }

        public Criteria andTaxNoNotLike(String value) {
            addCriterion("tax_no not like", value, "taxNo");
            return (Criteria) this;
        }

        public Criteria andTaxNoIn(List<String> values) {
            addCriterion("tax_no in", values, "taxNo");
            return (Criteria) this;
        }

        public Criteria andTaxNoNotIn(List<String> values) {
            addCriterion("tax_no not in", values, "taxNo");
            return (Criteria) this;
        }

        public Criteria andTaxNoBetween(String value1, String value2) {
            addCriterion("tax_no between", value1, value2, "taxNo");
            return (Criteria) this;
        }

        public Criteria andTaxNoNotBetween(String value1, String value2) {
            addCriterion("tax_no not between", value1, value2, "taxNo");
            return (Criteria) this;
        }

        public Criteria andLicenseNumberIsNull() {
            addCriterion("license_number is null");
            return (Criteria) this;
        }

        public Criteria andLicenseNumberIsNotNull() {
            addCriterion("license_number is not null");
            return (Criteria) this;
        }

        public Criteria andLicenseNumberEqualTo(String value) {
            addCriterion("license_number =", value, "licenseNumber");
            return (Criteria) this;
        }

        public Criteria andLicenseNumberNotEqualTo(String value) {
            addCriterion("license_number <>", value, "licenseNumber");
            return (Criteria) this;
        }

        public Criteria andLicenseNumberGreaterThan(String value) {
            addCriterion("license_number >", value, "licenseNumber");
            return (Criteria) this;
        }

        public Criteria andLicenseNumberGreaterThanOrEqualTo(String value) {
            addCriterion("license_number >=", value, "licenseNumber");
            return (Criteria) this;
        }

        public Criteria andLicenseNumberLessThan(String value) {
            addCriterion("license_number <", value, "licenseNumber");
            return (Criteria) this;
        }

        public Criteria andLicenseNumberLessThanOrEqualTo(String value) {
            addCriterion("license_number <=", value, "licenseNumber");
            return (Criteria) this;
        }

        public Criteria andLicenseNumberLike(String value) {
            addCriterion("license_number like", value, "licenseNumber");
            return (Criteria) this;
        }

        public Criteria andLicenseNumberNotLike(String value) {
            addCriterion("license_number not like", value, "licenseNumber");
            return (Criteria) this;
        }

        public Criteria andLicenseNumberIn(List<String> values) {
            addCriterion("license_number in", values, "licenseNumber");
            return (Criteria) this;
        }

        public Criteria andLicenseNumberNotIn(List<String> values) {
            addCriterion("license_number not in", values, "licenseNumber");
            return (Criteria) this;
        }

        public Criteria andLicenseNumberBetween(String value1, String value2) {
            addCriterion("license_number between", value1, value2, "licenseNumber");
            return (Criteria) this;
        }

        public Criteria andLicenseNumberNotBetween(String value1, String value2) {
            addCriterion("license_number not between", value1, value2, "licenseNumber");
            return (Criteria) this;
        }

        public Criteria andOrgNoIsNull() {
            addCriterion("org_no is null");
            return (Criteria) this;
        }

        public Criteria andOrgNoIsNotNull() {
            addCriterion("org_no is not null");
            return (Criteria) this;
        }

        public Criteria andOrgNoEqualTo(String value) {
            addCriterion("org_no =", value, "orgNo");
            return (Criteria) this;
        }

        public Criteria andOrgNoNotEqualTo(String value) {
            addCriterion("org_no <>", value, "orgNo");
            return (Criteria) this;
        }

        public Criteria andOrgNoGreaterThan(String value) {
            addCriterion("org_no >", value, "orgNo");
            return (Criteria) this;
        }

        public Criteria andOrgNoGreaterThanOrEqualTo(String value) {
            addCriterion("org_no >=", value, "orgNo");
            return (Criteria) this;
        }

        public Criteria andOrgNoLessThan(String value) {
            addCriterion("org_no <", value, "orgNo");
            return (Criteria) this;
        }

        public Criteria andOrgNoLessThanOrEqualTo(String value) {
            addCriterion("org_no <=", value, "orgNo");
            return (Criteria) this;
        }

        public Criteria andOrgNoLike(String value) {
            addCriterion("org_no like", value, "orgNo");
            return (Criteria) this;
        }

        public Criteria andOrgNoNotLike(String value) {
            addCriterion("org_no not like", value, "orgNo");
            return (Criteria) this;
        }

        public Criteria andOrgNoIn(List<String> values) {
            addCriterion("org_no in", values, "orgNo");
            return (Criteria) this;
        }

        public Criteria andOrgNoNotIn(List<String> values) {
            addCriterion("org_no not in", values, "orgNo");
            return (Criteria) this;
        }

        public Criteria andOrgNoBetween(String value1, String value2) {
            addCriterion("org_no between", value1, value2, "orgNo");
            return (Criteria) this;
        }

        public Criteria andOrgNoNotBetween(String value1, String value2) {
            addCriterion("org_no not between", value1, value2, "orgNo");
            return (Criteria) this;
        }

        public Criteria andAuthorityIsNull() {
            addCriterion("authority is null");
            return (Criteria) this;
        }

        public Criteria andAuthorityIsNotNull() {
            addCriterion("authority is not null");
            return (Criteria) this;
        }

        public Criteria andAuthorityEqualTo(String value) {
            addCriterion("authority =", value, "authority");
            return (Criteria) this;
        }

        public Criteria andAuthorityNotEqualTo(String value) {
            addCriterion("authority <>", value, "authority");
            return (Criteria) this;
        }

        public Criteria andAuthorityGreaterThan(String value) {
            addCriterion("authority >", value, "authority");
            return (Criteria) this;
        }

        public Criteria andAuthorityGreaterThanOrEqualTo(String value) {
            addCriterion("authority >=", value, "authority");
            return (Criteria) this;
        }

        public Criteria andAuthorityLessThan(String value) {
            addCriterion("authority <", value, "authority");
            return (Criteria) this;
        }

        public Criteria andAuthorityLessThanOrEqualTo(String value) {
            addCriterion("authority <=", value, "authority");
            return (Criteria) this;
        }

        public Criteria andAuthorityLike(String value) {
            addCriterion("authority like", value, "authority");
            return (Criteria) this;
        }

        public Criteria andAuthorityNotLike(String value) {
            addCriterion("authority not like", value, "authority");
            return (Criteria) this;
        }

        public Criteria andAuthorityIn(List<String> values) {
            addCriterion("authority in", values, "authority");
            return (Criteria) this;
        }

        public Criteria andAuthorityNotIn(List<String> values) {
            addCriterion("authority not in", values, "authority");
            return (Criteria) this;
        }

        public Criteria andAuthorityBetween(String value1, String value2) {
            addCriterion("authority between", value1, value2, "authority");
            return (Criteria) this;
        }

        public Criteria andAuthorityNotBetween(String value1, String value2) {
            addCriterion("authority not between", value1, value2, "authority");
            return (Criteria) this;
        }

        public Criteria andStartDateIsNull() {
            addCriterion("start_date is null");
            return (Criteria) this;
        }

        public Criteria andStartDateIsNotNull() {
            addCriterion("start_date is not null");
            return (Criteria) this;
        }

        public Criteria andStartDateEqualTo(String value) {
            addCriterion("start_date =", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotEqualTo(String value) {
            addCriterion("start_date <>", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateGreaterThan(String value) {
            addCriterion("start_date >", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateGreaterThanOrEqualTo(String value) {
            addCriterion("start_date >=", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateLessThan(String value) {
            addCriterion("start_date <", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateLessThanOrEqualTo(String value) {
            addCriterion("start_date <=", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateLike(String value) {
            addCriterion("start_date like", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotLike(String value) {
            addCriterion("start_date not like", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateIn(List<String> values) {
            addCriterion("start_date in", values, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotIn(List<String> values) {
            addCriterion("start_date not in", values, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateBetween(String value1, String value2) {
            addCriterion("start_date between", value1, value2, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotBetween(String value1, String value2) {
            addCriterion("start_date not between", value1, value2, "startDate");
            return (Criteria) this;
        }

        public Criteria andEntTypeIsNull() {
            addCriterion("ent_type is null");
            return (Criteria) this;
        }

        public Criteria andEntTypeIsNotNull() {
            addCriterion("ent_type is not null");
            return (Criteria) this;
        }

        public Criteria andEntTypeEqualTo(String value) {
            addCriterion("ent_type =", value, "entType");
            return (Criteria) this;
        }

        public Criteria andEntTypeNotEqualTo(String value) {
            addCriterion("ent_type <>", value, "entType");
            return (Criteria) this;
        }

        public Criteria andEntTypeGreaterThan(String value) {
            addCriterion("ent_type >", value, "entType");
            return (Criteria) this;
        }

        public Criteria andEntTypeGreaterThanOrEqualTo(String value) {
            addCriterion("ent_type >=", value, "entType");
            return (Criteria) this;
        }

        public Criteria andEntTypeLessThan(String value) {
            addCriterion("ent_type <", value, "entType");
            return (Criteria) this;
        }

        public Criteria andEntTypeLessThanOrEqualTo(String value) {
            addCriterion("ent_type <=", value, "entType");
            return (Criteria) this;
        }

        public Criteria andEntTypeLike(String value) {
            addCriterion("ent_type like", value, "entType");
            return (Criteria) this;
        }

        public Criteria andEntTypeNotLike(String value) {
            addCriterion("ent_type not like", value, "entType");
            return (Criteria) this;
        }

        public Criteria andEntTypeIn(List<String> values) {
            addCriterion("ent_type in", values, "entType");
            return (Criteria) this;
        }

        public Criteria andEntTypeNotIn(List<String> values) {
            addCriterion("ent_type not in", values, "entType");
            return (Criteria) this;
        }

        public Criteria andEntTypeBetween(String value1, String value2) {
            addCriterion("ent_type between", value1, value2, "entType");
            return (Criteria) this;
        }

        public Criteria andEntTypeNotBetween(String value1, String value2) {
            addCriterion("ent_type not between", value1, value2, "entType");
            return (Criteria) this;
        }

        public Criteria andOpenStartIsNull() {
            addCriterion("open_start is null");
            return (Criteria) this;
        }

        public Criteria andOpenStartIsNotNull() {
            addCriterion("open_start is not null");
            return (Criteria) this;
        }

        public Criteria andOpenStartEqualTo(String value) {
            addCriterion("open_start =", value, "openStart");
            return (Criteria) this;
        }

        public Criteria andOpenStartNotEqualTo(String value) {
            addCriterion("open_start <>", value, "openStart");
            return (Criteria) this;
        }

        public Criteria andOpenStartGreaterThan(String value) {
            addCriterion("open_start >", value, "openStart");
            return (Criteria) this;
        }

        public Criteria andOpenStartGreaterThanOrEqualTo(String value) {
            addCriterion("open_start >=", value, "openStart");
            return (Criteria) this;
        }

        public Criteria andOpenStartLessThan(String value) {
            addCriterion("open_start <", value, "openStart");
            return (Criteria) this;
        }

        public Criteria andOpenStartLessThanOrEqualTo(String value) {
            addCriterion("open_start <=", value, "openStart");
            return (Criteria) this;
        }

        public Criteria andOpenStartLike(String value) {
            addCriterion("open_start like", value, "openStart");
            return (Criteria) this;
        }

        public Criteria andOpenStartNotLike(String value) {
            addCriterion("open_start not like", value, "openStart");
            return (Criteria) this;
        }

        public Criteria andOpenStartIn(List<String> values) {
            addCriterion("open_start in", values, "openStart");
            return (Criteria) this;
        }

        public Criteria andOpenStartNotIn(List<String> values) {
            addCriterion("open_start not in", values, "openStart");
            return (Criteria) this;
        }

        public Criteria andOpenStartBetween(String value1, String value2) {
            addCriterion("open_start between", value1, value2, "openStart");
            return (Criteria) this;
        }

        public Criteria andOpenStartNotBetween(String value1, String value2) {
            addCriterion("open_start not between", value1, value2, "openStart");
            return (Criteria) this;
        }

        public Criteria andOpenEndIsNull() {
            addCriterion("open_end is null");
            return (Criteria) this;
        }

        public Criteria andOpenEndIsNotNull() {
            addCriterion("open_end is not null");
            return (Criteria) this;
        }

        public Criteria andOpenEndEqualTo(String value) {
            addCriterion("open_end =", value, "openEnd");
            return (Criteria) this;
        }

        public Criteria andOpenEndNotEqualTo(String value) {
            addCriterion("open_end <>", value, "openEnd");
            return (Criteria) this;
        }

        public Criteria andOpenEndGreaterThan(String value) {
            addCriterion("open_end >", value, "openEnd");
            return (Criteria) this;
        }

        public Criteria andOpenEndGreaterThanOrEqualTo(String value) {
            addCriterion("open_end >=", value, "openEnd");
            return (Criteria) this;
        }

        public Criteria andOpenEndLessThan(String value) {
            addCriterion("open_end <", value, "openEnd");
            return (Criteria) this;
        }

        public Criteria andOpenEndLessThanOrEqualTo(String value) {
            addCriterion("open_end <=", value, "openEnd");
            return (Criteria) this;
        }

        public Criteria andOpenEndLike(String value) {
            addCriterion("open_end like", value, "openEnd");
            return (Criteria) this;
        }

        public Criteria andOpenEndNotLike(String value) {
            addCriterion("open_end not like", value, "openEnd");
            return (Criteria) this;
        }

        public Criteria andOpenEndIn(List<String> values) {
            addCriterion("open_end in", values, "openEnd");
            return (Criteria) this;
        }

        public Criteria andOpenEndNotIn(List<String> values) {
            addCriterion("open_end not in", values, "openEnd");
            return (Criteria) this;
        }

        public Criteria andOpenEndBetween(String value1, String value2) {
            addCriterion("open_end between", value1, value2, "openEnd");
            return (Criteria) this;
        }

        public Criteria andOpenEndNotBetween(String value1, String value2) {
            addCriterion("open_end not between", value1, value2, "openEnd");
            return (Criteria) this;
        }

        public Criteria andDistrictIsNull() {
            addCriterion("district is null");
            return (Criteria) this;
        }

        public Criteria andDistrictIsNotNull() {
            addCriterion("district is not null");
            return (Criteria) this;
        }

        public Criteria andDistrictEqualTo(String value) {
            addCriterion("district =", value, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictNotEqualTo(String value) {
            addCriterion("district <>", value, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictGreaterThan(String value) {
            addCriterion("district >", value, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictGreaterThanOrEqualTo(String value) {
            addCriterion("district >=", value, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictLessThan(String value) {
            addCriterion("district <", value, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictLessThanOrEqualTo(String value) {
            addCriterion("district <=", value, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictLike(String value) {
            addCriterion("district like", value, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictNotLike(String value) {
            addCriterion("district not like", value, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictIn(List<String> values) {
            addCriterion("district in", values, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictNotIn(List<String> values) {
            addCriterion("district not in", values, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictBetween(String value1, String value2) {
            addCriterion("district between", value1, value2, "district");
            return (Criteria) this;
        }

        public Criteria andDistrictNotBetween(String value1, String value2) {
            addCriterion("district not between", value1, value2, "district");
            return (Criteria) this;
        }

        public Criteria andAnnualDateIsNull() {
            addCriterion("annual_date is null");
            return (Criteria) this;
        }

        public Criteria andAnnualDateIsNotNull() {
            addCriterion("annual_date is not null");
            return (Criteria) this;
        }

        public Criteria andAnnualDateEqualTo(String value) {
            addCriterion("annual_date =", value, "annualDate");
            return (Criteria) this;
        }

        public Criteria andAnnualDateNotEqualTo(String value) {
            addCriterion("annual_date <>", value, "annualDate");
            return (Criteria) this;
        }

        public Criteria andAnnualDateGreaterThan(String value) {
            addCriterion("annual_date >", value, "annualDate");
            return (Criteria) this;
        }

        public Criteria andAnnualDateGreaterThanOrEqualTo(String value) {
            addCriterion("annual_date >=", value, "annualDate");
            return (Criteria) this;
        }

        public Criteria andAnnualDateLessThan(String value) {
            addCriterion("annual_date <", value, "annualDate");
            return (Criteria) this;
        }

        public Criteria andAnnualDateLessThanOrEqualTo(String value) {
            addCriterion("annual_date <=", value, "annualDate");
            return (Criteria) this;
        }

        public Criteria andAnnualDateLike(String value) {
            addCriterion("annual_date like", value, "annualDate");
            return (Criteria) this;
        }

        public Criteria andAnnualDateNotLike(String value) {
            addCriterion("annual_date not like", value, "annualDate");
            return (Criteria) this;
        }

        public Criteria andAnnualDateIn(List<String> values) {
            addCriterion("annual_date in", values, "annualDate");
            return (Criteria) this;
        }

        public Criteria andAnnualDateNotIn(List<String> values) {
            addCriterion("annual_date not in", values, "annualDate");
            return (Criteria) this;
        }

        public Criteria andAnnualDateBetween(String value1, String value2) {
            addCriterion("annual_date between", value1, value2, "annualDate");
            return (Criteria) this;
        }

        public Criteria andAnnualDateNotBetween(String value1, String value2) {
            addCriterion("annual_date not between", value1, value2, "annualDate");
            return (Criteria) this;
        }

        public Criteria andRegAddrIsNull() {
            addCriterion("reg_addr is null");
            return (Criteria) this;
        }

        public Criteria andRegAddrIsNotNull() {
            addCriterion("reg_addr is not null");
            return (Criteria) this;
        }

        public Criteria andRegAddrEqualTo(String value) {
            addCriterion("reg_addr =", value, "regAddr");
            return (Criteria) this;
        }

        public Criteria andRegAddrNotEqualTo(String value) {
            addCriterion("reg_addr <>", value, "regAddr");
            return (Criteria) this;
        }

        public Criteria andRegAddrGreaterThan(String value) {
            addCriterion("reg_addr >", value, "regAddr");
            return (Criteria) this;
        }

        public Criteria andRegAddrGreaterThanOrEqualTo(String value) {
            addCriterion("reg_addr >=", value, "regAddr");
            return (Criteria) this;
        }

        public Criteria andRegAddrLessThan(String value) {
            addCriterion("reg_addr <", value, "regAddr");
            return (Criteria) this;
        }

        public Criteria andRegAddrLessThanOrEqualTo(String value) {
            addCriterion("reg_addr <=", value, "regAddr");
            return (Criteria) this;
        }

        public Criteria andRegAddrLike(String value) {
            addCriterion("reg_addr like", value, "regAddr");
            return (Criteria) this;
        }

        public Criteria andRegAddrNotLike(String value) {
            addCriterion("reg_addr not like", value, "regAddr");
            return (Criteria) this;
        }

        public Criteria andRegAddrIn(List<String> values) {
            addCriterion("reg_addr in", values, "regAddr");
            return (Criteria) this;
        }

        public Criteria andRegAddrNotIn(List<String> values) {
            addCriterion("reg_addr not in", values, "regAddr");
            return (Criteria) this;
        }

        public Criteria andRegAddrBetween(String value1, String value2) {
            addCriterion("reg_addr between", value1, value2, "regAddr");
            return (Criteria) this;
        }

        public Criteria andRegAddrNotBetween(String value1, String value2) {
            addCriterion("reg_addr not between", value1, value2, "regAddr");
            return (Criteria) this;
        }

        public Criteria andScopeIsNull() {
            addCriterion("scope is null");
            return (Criteria) this;
        }

        public Criteria andScopeIsNotNull() {
            addCriterion("scope is not null");
            return (Criteria) this;
        }

        public Criteria andScopeEqualTo(String value) {
            addCriterion("scope =", value, "scope");
            return (Criteria) this;
        }

        public Criteria andScopeNotEqualTo(String value) {
            addCriterion("scope <>", value, "scope");
            return (Criteria) this;
        }

        public Criteria andScopeGreaterThan(String value) {
            addCriterion("scope >", value, "scope");
            return (Criteria) this;
        }

        public Criteria andScopeGreaterThanOrEqualTo(String value) {
            addCriterion("scope >=", value, "scope");
            return (Criteria) this;
        }

        public Criteria andScopeLessThan(String value) {
            addCriterion("scope <", value, "scope");
            return (Criteria) this;
        }

        public Criteria andScopeLessThanOrEqualTo(String value) {
            addCriterion("scope <=", value, "scope");
            return (Criteria) this;
        }

        public Criteria andScopeLike(String value) {
            addCriterion("scope like", value, "scope");
            return (Criteria) this;
        }

        public Criteria andScopeNotLike(String value) {
            addCriterion("scope not like", value, "scope");
            return (Criteria) this;
        }

        public Criteria andScopeIn(List<String> values) {
            addCriterion("scope in", values, "scope");
            return (Criteria) this;
        }

        public Criteria andScopeNotIn(List<String> values) {
            addCriterion("scope not in", values, "scope");
            return (Criteria) this;
        }

        public Criteria andScopeBetween(String value1, String value2) {
            addCriterion("scope between", value1, value2, "scope");
            return (Criteria) this;
        }

        public Criteria andScopeNotBetween(String value1, String value2) {
            addCriterion("scope not between", value1, value2, "scope");
            return (Criteria) this;
        }

        public Criteria andEndDateIsNull() {
            addCriterion("end_date is null");
            return (Criteria) this;
        }

        public Criteria andEndDateIsNotNull() {
            addCriterion("end_date is not null");
            return (Criteria) this;
        }

        public Criteria andEndDateEqualTo(String value) {
            addCriterion("end_date =", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotEqualTo(String value) {
            addCriterion("end_date <>", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateGreaterThan(String value) {
            addCriterion("end_date >", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateGreaterThanOrEqualTo(String value) {
            addCriterion("end_date >=", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateLessThan(String value) {
            addCriterion("end_date <", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateLessThanOrEqualTo(String value) {
            addCriterion("end_date <=", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateLike(String value) {
            addCriterion("end_date like", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotLike(String value) {
            addCriterion("end_date not like", value, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateIn(List<String> values) {
            addCriterion("end_date in", values, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotIn(List<String> values) {
            addCriterion("end_date not in", values, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateBetween(String value1, String value2) {
            addCriterion("end_date between", value1, value2, "endDate");
            return (Criteria) this;
        }

        public Criteria andEndDateNotBetween(String value1, String value2) {
            addCriterion("end_date not between", value1, value2, "endDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimestampIsNull() {
            addCriterion("last_update_timestamp is null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimestampIsNotNull() {
            addCriterion("last_update_timestamp is not null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimestampEqualTo(Long value) {
            addCriterion("last_update_timestamp =", value, "lastUpdateTimestamp");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimestampNotEqualTo(Long value) {
            addCriterion("last_update_timestamp <>", value, "lastUpdateTimestamp");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimestampGreaterThan(Long value) {
            addCriterion("last_update_timestamp >", value, "lastUpdateTimestamp");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimestampGreaterThanOrEqualTo(Long value) {
            addCriterion("last_update_timestamp >=", value, "lastUpdateTimestamp");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimestampLessThan(Long value) {
            addCriterion("last_update_timestamp <", value, "lastUpdateTimestamp");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimestampLessThanOrEqualTo(Long value) {
            addCriterion("last_update_timestamp <=", value, "lastUpdateTimestamp");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimestampIn(List<Long> values) {
            addCriterion("last_update_timestamp in", values, "lastUpdateTimestamp");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimestampNotIn(List<Long> values) {
            addCriterion("last_update_timestamp not in", values, "lastUpdateTimestamp");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimestampBetween(Long value1, Long value2) {
            addCriterion("last_update_timestamp between", value1, value2, "lastUpdateTimestamp");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimestampNotBetween(Long value1, Long value2) {
            addCriterion("last_update_timestamp not between", value1, value2, "lastUpdateTimestamp");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}