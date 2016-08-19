package com.bidServ.model.applicationModule.common;

import java.math.BigDecimal;

import oracle.jbo.ApplicationModule;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Jun 29 13:28:02 IST 2016
// ---------------------------------------------------------------------
public interface BidServAM extends ApplicationModule {
    void searchPost(String voName, String keyword);

    void setMyPostVC(BigDecimal userId);

    void setMyBidsVC(BigDecimal userId);

    void postDetails(String source, String userId, String postId);

    void createComment(int bidId, int userId, String username, String comment);
}

