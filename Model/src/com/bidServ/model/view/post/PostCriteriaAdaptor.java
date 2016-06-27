package com.bidServ.model.view.post;

import oracle.jbo.AttributeDef;
import oracle.jbo.CriteriaAdapter;
import oracle.jbo.ViewCriteria;
import oracle.jbo.server.CriteriaAdapterImpl;

public class PostCriteriaAdaptor extends CriteriaAdapterImpl  implements CriteriaAdapter {
    public PostCriteriaAdaptor() {
        super();
    }

    @Override
//    public String getCriteriaClause(ViewCriteria viewCriteria) {
//        // TODO Implement this method
//        System.out.println("VC ==========="+viewCriteria.getName());
//        if(viewCriteria.getName().equals("SearchVC")){
//            return "UPPER(Post.POST_DESCRIPTION) LIKE CONCAT('%',?,'%')";
//        }else{
//            return null;
//        }
//        
//    }
    
    protected String getCriteriaClause(AttributeDef[] attrDefs, 
                                       ViewCriteria criteria) 
    {
        System.out.println("VC n==========="+criteria.getName());
        if(criteria.getName().equals("SearchVC")){
            return "UPPER(Post.POST_DESCRIPTION) LIKE CONCAT('%',?,'%')";
        }else{
            return "Post.VISIBILITY_CODE = 'ENTIRE_NETWORK'";
        }
    }
    
}
