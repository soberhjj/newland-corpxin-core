package com.newland.corpxin.service;

import com.newland.corpxin.conf.SqlConstant;
import com.newland.corpxin.model.BasicInfo;
import com.newland.corpxin.util.DBConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @Description:
 * @Author: Ljh
 * @Date 2020/7/22 11:11
 */
public class MysqlService {

    public void saveBasicInfo(BasicInfo basicInfo) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DBConnectionManager.getInstance().getConnection();
            stmt = conn.prepareStatement(SqlConstant.SQL_INSERT_UPDATE_BASICINFO);
            stmt.setString(1,basicInfo.getId());
            stmt.setString(2,basicInfo.getEntName());
            stmt.setString(3,basicInfo.getLegalPerson());
            stmt.setString(4,basicInfo.getOpenStatus());
            stmt.setString(5,basicInfo.getRegCapital());
            stmt.setString(6,basicInfo.getRealCapital());
            stmt.setString(7,basicInfo.getIndustry());
            stmt.setString(8,basicInfo.getUnifiedCode());
            stmt.setString(9,basicInfo.getTaxNo());
            stmt.setString(10,basicInfo.getLicenseNumber());
            stmt.setString(11,basicInfo.getOrgNo());
            stmt.setString(12,basicInfo.getAuthority());
            stmt.setString(13,basicInfo.getStartDate());
            stmt.setString(14,basicInfo.getEntType());
            stmt.setString(15,basicInfo.getOpenStart());
            stmt.setString(16,basicInfo.getOpenEnd());
            stmt.setString(17,basicInfo.getDistrict());
            stmt.setString(18,basicInfo.getAnnualDate());
            stmt.setString(19,basicInfo.getRegAddr());
            stmt.setString(20,basicInfo.getScope());
            stmt.setString(21,basicInfo.getEndDate());
            stmt.setLong(22,basicInfo.getLastUpdateTimestamp());
            stmt.execute();
        } catch (Exception e) {
            throw e;
        } finally{
            if(stmt != null){
                stmt.close();
            }
            if(conn != null){
                conn.close();
            }
        }
    }

    public void saveBasicInfoError(BasicInfo basicInfo) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DBConnectionManager.getInstance().getConnection();
            stmt = conn.prepareStatement(SqlConstant.SQL_INSERT_BASICINFO_ERROR);
            stmt.setString(1,basicInfo.getEntName());
            stmt.setString(2,basicInfo.getLegalPerson());
            stmt.setString(3,basicInfo.getOpenStatus());
            stmt.setString(4,basicInfo.getRegCapital());
            stmt.setString(5,basicInfo.getRealCapital());
            stmt.setString(6,basicInfo.getIndustry());
            stmt.setString(7,basicInfo.getUnifiedCode());
            stmt.setString(8,basicInfo.getTaxNo());
            stmt.setString(9,basicInfo.getLicenseNumber());
            stmt.setString(10,basicInfo.getOrgNo());
            stmt.setString(11,basicInfo.getAuthority());
            stmt.setString(12,basicInfo.getStartDate());
            stmt.setString(13,basicInfo.getEntType());
            stmt.setString(14,basicInfo.getOpenStart());
            stmt.setString(15,basicInfo.getOpenEnd());
            stmt.setString(16,basicInfo.getDistrict());
            stmt.setString(17,basicInfo.getAnnualDate());
            stmt.setString(18,basicInfo.getRegAddr());
            stmt.setString(19,basicInfo.getScope());
            stmt.setString(20,basicInfo.getEndDate());
            stmt.setLong(21,basicInfo.getLastUpdateTimestamp());
            stmt.execute();
        } catch (Exception e) {
            throw e;
        } finally{
            if(stmt != null){
                stmt.close();
            }
            if(conn != null){
                conn.close();
            }
        }
    }
}
