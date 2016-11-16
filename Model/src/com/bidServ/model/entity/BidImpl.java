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
// ---    Fri Oct 28 21:59:31 IST 2016
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class BidImpl extends EntityImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        BidId,
        CompanyId,
        PostId,
        UserId,
        BidDescription,
        BidStatusCode,
        Quantity,
        Uom,
        Price,
        Amount,
        Currency,
        DeliveryDate,
        ModifiedByUser,
        ModifiedDate,
        Language,
        ObjectVersionNumber,
        ChangeLog,
        Post,
        Company;
        static AttributesEnum[] vals = null;
        ;
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


    public static final int BIDID = AttributesEnum.BidId.index();
    public static final int COMPANYID = AttributesEnum.CompanyId.index();
    public static final int POSTID = AttributesEnum.PostId.index();
    public static final int USERID = AttributesEnum.UserId.index();
    public static final int BIDDESCRIPTION = AttributesEnum.BidDescription.index();
    public static final int BIDSTATUSCODE = AttributesEnum.BidStatusCode.index();
    public static final int QUANTITY = AttributesEnum.Quantity.index();
    public static final int UOM = AttributesEnum.Uom.index();
    public static final int PRICE = AttributesEnum.Price.index();
    public static final int AMOUNT = AttributesEnum.Amount.index();
    public static final int CURRENCY = AttributesEnum.Currency.index();
    public static final int DELIVERYDATE = AttributesEnum.DeliveryDate.index();
    public static final int MODIFIEDBYUSER = AttributesEnum.ModifiedByUser.index();
    public static final int MODIFIEDDATE = AttributesEnum.ModifiedDate.index();
    public static final int LANGUAGE = AttributesEnum.Language.index();
    public static final int OBJECTVERSIONNUMBER = AttributesEnum.ObjectVersionNumber.index();
    public static final int CHANGELOG = AttributesEnum.ChangeLog.index();
    public static final int POST = AttributesEnum.Post.index();
    public static final int COMPANY = AttributesEnum.Company.index();

    /**
     * This is the default constructor (do not remove).
     */
    public BidImpl() {
    }

    /**
     * @param bidId key constituent

     * @return a Key object based on given key constituents.
     */
    public static Key createPrimaryKey(BigDecimal bidId) {
        return new Key(new Object[] { bidId });
    }

    /**
     * @return the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        return EntityDefImpl.findDefObject("com.bidServ.model.entity.Bid");
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
                        setAttribute("BidId", BigDecimal.valueOf(rs.getLong(1)));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
