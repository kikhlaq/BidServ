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
            return "UPPER(POST_DESCRIPTION) LIKE CONCAT('%',:keyword,'%') " + 
            "or UPPER(PRODUCT) LIKE CONCAT('%',:keyword,'%') " + 
            "or UPPER(POST_TYPE) LIKE CONCAT('%',:keyword,'%')";
        }else if(criteria.getName().equals("PrimaryPostCriteria")){
            return "SOURCE_COMPANY_ID = :bindCompId";
        }else if(criteria.getName().equals("SecondaryConnPostVOCriteria")){
            return "SOURCE_COMPANY_ID = :bindCompId";
        }else if (criteria.getName().equals("EntireNetworkVC")){
            return "Post.VISIBILITY_CODE = 'ENTIRE_NETWORK'";
        }else if (criteria.getName().equals("MyPostVC")){
            return "Post.USER_ID = :bindUserId";
        }else if (criteria.getName().equals("MyBidsVC")){
            return "Bid.USER_ID = :bindUserId";
        }else if (criteria.getName().equals("PostIdVC")){
            return "Post.POST_ID = :bindPostId";
        }else{
            return null;
        }
    }
    
}
