package com.bidServ.model.view.setup;

import oracle.jbo.AttributeDef;
import oracle.jbo.CriteriaAdapter;
import oracle.jbo.ViewCriteria;
import oracle.jbo.server.CriteriaAdapterImpl;

public class CompanyCriteriaAdaptor extends CriteriaAdapterImpl  implements CriteriaAdapter {
    public CompanyCriteriaAdaptor() {
        super();
    }
    
    protected String getCriteriaClause(AttributeDef[] attrDefs, 
                                       ViewCriteria criteria) 
    {
        System.out.println("VC n==========="+criteria.getViewObject().getName());
       // if(criteria.getViewObject().getName())
        if(criteria.getName().equals("ByNameVC")){
            return "UPPER(Company.NAME) LIKE CONCAT(:bindName,'%')";
        }else         if(criteria.getName().equals("ByCompanyIdVC")){
            return "Company.COMPANY_ID = :bindCompId";
        }else{
            return null;
        }
    }
}
