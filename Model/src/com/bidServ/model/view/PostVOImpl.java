package com.bidServ.model.view;

import com.bidServ.model.view.post.PostCriteriaAdaptor;

import java.math.BigDecimal;

import oracle.jbo.CriteriaAdapter;
import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Sat Jul 30 15:28:40 IST 2016
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class PostVOImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public PostVOImpl() {
    }
    
    private static CriteriaAdapter postCriteriaAdaptor = new PostCriteriaAdaptor();
    public CriteriaAdapter getCriteriaAdapter(){
        return postCriteriaAdaptor;
    }
}
