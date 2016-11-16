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
// ---    Wed Oct 19 17:36:34 IST 2016
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class UserImpl extends EntityImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        UserId,
        CompanyId,
        Status,
        IsAdmin,
        Name,
        Phone,
        Email,
        Address,
        InvitationId,
        ImageUrl,
        ThumbnailUrl,
        ModifiedByUser,
        ModifiedDate,
        Language,
        ObjectVersionNumber,
        Post,
        VerificationRequest,
        ReportedEntity;
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


    public static final int USERID = AttributesEnum.UserId.index();
    public static final int COMPANYID = AttributesEnum.CompanyId.index();
    public static final int STATUS = AttributesEnum.Status.index();
    public static final int ISADMIN = AttributesEnum.IsAdmin.index();
    public static final int NAME = AttributesEnum.Name.index();
    public static final int PHONE = AttributesEnum.Phone.index();
    public static final int EMAIL = AttributesEnum.Email.index();
    public static final int ADDRESS = AttributesEnum.Address.index();
    public static final int INVITATIONID = AttributesEnum.InvitationId.index();
    public static final int IMAGEURL = AttributesEnum.ImageUrl.index();
    public static final int THUMBNAILURL = AttributesEnum.ThumbnailUrl.index();
    public static final int MODIFIEDBYUSER = AttributesEnum.ModifiedByUser.index();
    public static final int MODIFIEDDATE = AttributesEnum.ModifiedDate.index();
    public static final int LANGUAGE = AttributesEnum.Language.index();
    public static final int OBJECTVERSIONNUMBER = AttributesEnum.ObjectVersionNumber.index();
    public static final int POST = AttributesEnum.Post.index();
    public static final int VERIFICATIONREQUEST = AttributesEnum.VerificationRequest.index();
    public static final int REPORTEDENTITY = AttributesEnum.ReportedEntity.index();

    /**
     * This is the default constructor (do not remove).
     */
    public UserImpl() {
    }

    /**
     * @param userId key constituent

     * @return a Key object based on given key constituents.
     */
    public static Key createPrimaryKey(BigDecimal userId) {
        return new Key(new Object[] { userId });
    }

    /**
     * @return the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        return EntityDefImpl.findDefObject("com.bidServ.model.entity.User");
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
                        setAttribute("UserId", BigDecimal.valueOf(rs.getLong(1)));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

