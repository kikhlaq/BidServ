package com.bidServ.model.entity;

import com.bidServ.model.applicationModule.BidServAMImpl;

import com.bidServ.model.view.common.LoggedInUserVORowImpl;

import java.math.BigDecimal;

import java.util.Calendar;

import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.TransactionEvent;

public class BaseEntityImpl extends EntityImpl{
    public BaseEntityImpl() {
        super();
    }


    @Override
    protected void prepareForDML(int i, TransactionEvent transactionEvent) {
        // TODO Implement this method
        System.out.println("====================prepareForDML s ");
        BidServAMImpl am = (BidServAMImpl)this.getDBTransaction().getRootApplicationModule();
        
        this.setAttribute("ModifiedByUser",  BigDecimal.valueOf(((LoggedInUserVORowImpl) am.getLoggedInUser().getCurrentRow()).getUserId()));
        this.setAttribute("ModifiedDate", new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
        this.setAttribute("Language", "US");
        this.setAttribute("ObjectVersionNumber", (this.getAttribute("ObjectVersionNumber") == null ? (new BigDecimal(1)) : ((BigDecimal)this.getAttribute("ObjectVersionNumber")).add(new BigDecimal(1))));
        super.prepareForDML(i, transactionEvent);
        System.out.println("====================prepareForDML e ");
    }
}
