package com.newland.corpxin.conf;

public interface SqlConstant {
	
	String SQL_QUERY_OFFSET = "select topic,`partition`,offset from kafka_offset where topic = ? and consumer_id = ?";
	
	String SQL_UPDATE_OFFSET = "insert into kafka_offset(topic,`partition`,consumer_id,offset) "
			+ "values(?,?,?,?) "
			+ "ON DUPLICATE KEY update "
			+ "offset = values(offset)";

	String SQL_INSERT_BASICINFO_ERROR = "insert into ads_company_basicinfo_error (ent_name, legal_person, open_status, " +
			"reg_capital, real_capital, industry, unified_code, tax_no, license_number, org_no, authority, start_date, " +
			"ent_type, open_start, open_end, district, annual_date, reg_addr, scope, end_date, last_update_timestamp) " +
			"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


	String SQL_INSERT_UPDATE_BASICINFO = "insert into ads_company_basicinfo (ent_name, legal_person, open_status, " +
			"reg_capital, real_capital, industry, unified_code, tax_no, license_number, org_no, authority, start_date, " +
			"ent_type, open_start, open_end, district, annual_date, reg_addr, scope, end_date, last_update_timestamp) " +
			"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) " +
			"ON DUPLICATE KEY update " +
			"ent_name = values(ent_name), legal_person = values(legal_person), open_status = values(open_status), " +
			"reg_capital = values(reg_capital), real_capital = values(real_capital), industry = values(industry), " +
			"unified_code = values(unified_code), tax_no = values(tax_no), license_number = values(license_number), " +
			"org_no = values(org_no), authority = values(authority), start_date = values(start_date), " +
			"ent_type = values(ent_type), open_start = values(open_start), open_end = values(open_end), " +
			"district = values(district), annual_date = values(annual_date), reg_addr = values(reg_addr), " +
			"scope = values(scope), end_date = values(end_date), last_update_timestamp = values(last_update_timestamp)";
}
