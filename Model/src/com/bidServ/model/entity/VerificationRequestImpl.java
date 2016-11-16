package com.bidServ.model.entity;

import java.math.BigDecimal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jbo.Key;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.TransactionEvent;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Nov 14 15:52:29 IST 2016
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class VerificationRequestImpl extends EntityImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        RequestId,
        UserId,
        CompanyId,
        Status,
        RequestDate;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public int index() {
            return AttributesEnum.firstIndex() + ordinal();
        }

        public static final int firstIndex() {
            return firstIndex;
        }

        public static int count() {
            return AttributesEnum.firstIndex() + AttributesEnum.staticValues().length;
        }

        public static final AttributesEnum[] staticValues() {
            if (vals == null) {
                vals = AttributesEnum.values();
            }
            return vals;
        }
    }


    public static final int REQUESTID = AttributesEnum.RequestId.index();
    public static final int USERID = AttributesEnum.UserId.index();
    public static final int COMPANYID = AttributesEnum.CompanyId.index();
    public static final int STATUS = AttributesEnum.Status.index();
    public static final int REQUESTDATE = AttributesEnum.RequestDate.index();

    /**
     * This is the default constructor (do not remove).
     */
    public VerificationRequestImpl() {
    }

    /**
     * @param requestId key constituent

     * @return a Key object based on given key constituents.
     */
    public static Key createPrimaryKey(Integer requestId) {
        return new Key(new Object[] { requestId });
    }

    /**
     * @return the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        return EntityDefImpl.findDefObject("com.bidServ.model.entity.VerificationRequest");
    }


    @Override
    protected void doDML(int i, TransactionEvent transactionEvent) {
        //got to call first to super, so the record is posted 
        //and we can then ask for the last insert id
        super.doDML(i, transactionEvent);
        
        //after the record is inserted, we can ask for the last insert id
        if (i == DML_INSERT) {
            try (PreparedStatement stmt =
                 this.getDBTransaction().createPreparedStatement("SELECT last_insert_id()", 1)) {
                stmt.execute();
                try (ResultSet rs = stmt.getResultSet()) {
                    if (rs.next()) {
                        setAttribute("RequestId", Integer.valueOf(rs.getInt(1)));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

