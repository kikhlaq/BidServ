package com.bidServ.model.view.post;

import oracle.jbo.AttributeDef;
import oracle.jbo.CriteriaAdapter;
import oracle.jbo.ViewCriteria;
import oracle.jbo.server.CriteriaAdapterImpl;

public class PostCriteriaAdaptor extends CriteriaAdapterImpl  implements CriteriaAdapter {
    public PostCriteriaAdaptor() {
        super();
    }

    protected String getCriteriaClause(AttributeDef[] attrDefs, 
                                       ViewCriteria criteria) 
    {
        System.out.println("VC n==========="+criteria.getViewObject().getName());
       // if(criteria.getViewObject().getName())
        if(criteria.getName().equals("SearchVC")){
            return "UPPER(Post.POST_DESCRIPTION) LIKE CONCAT('%',:keyword,'%')";
        }else if(criteria.getName().equals("PrimaryPostCriteria")){
            return "Connection.SOURCE_COMPANY_ID = :bindCompId";
        }else if(criteria.getName().equals("SecondaryConnPostVOCriteria")){
            return "Connection1.SOURCE_COMPANY_ID = :bindCompId";
        }else if (criteria.getName().equals("EntireNetworkVC")){
            return "Post.VISIBILITY_CODE = 'ENTIRE_NETWORK'";
        }else{
            return null;
        }
    }
    
}
