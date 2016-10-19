package com.bidServ.model.applicationModule;

import com.bidServ.model.applicationModule.common.BidServAM;
import com.bidServ.model.view.LoggedInUserVOImpl;
import com.bidServ.model.view.LoggedInUserVORowImpl;
import com.bidServ.model.view.PostVOImpl;
import com.bidServ.model.view.bid.MyBidsVOImpl;
import com.bidServ.model.view.post.EntireNetworkPostVOImpl;
import com.bidServ.model.view.post.PrimaryConnPostVOImpl;
import com.bidServ.model.view.post.SecondaryConnPostVOImpl;

import java.math.BigDecimal;

import oracle.jbo.Row;
import oracle.jbo.server.ApplicationModuleImpl;
import oracle.jbo.server.DBTransactionImpl;
import oracle.jbo.server.RowImpl;
import oracle.jbo.server.ViewLinkImpl;
import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Jun 29 13:27:24 IST 2016
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class BidServAMImpl extends ApplicationModuleImpl implements BidServAM {
    /**
     * This is the default constructor (do not remove).
     */
    public BidServAMImpl() {
    }

    /**
     * Container's getter for PostVO1.
     * @return PostVO1
     */
    public PostVOImpl getPost() {
        return (PostVOImpl) findViewObject("Post");
    }

    /**
     * Container's getter for createPostVO.
     * @return createPostVO
     */
    public PostVOImpl getcreatePostVO() {
        return (PostVOImpl) findViewObject("createPostVO");
    }

    /**
     * Container's getter for SupplierNetworkVO.
     * @return SupplierNetworkVO
     */
    public EntireNetworkPostVOImpl getSupplierNetworkVO() {
        return (EntireNetworkPostVOImpl) findViewObject("SupplierNetworkVO");
    }

    /**
     * Container's getter for SecondaryConnVO.
     * @return SecondaryConnVO
     */
    public SecondaryConnPostVOImpl getSecondaryConnVO() {
        return (SecondaryConnPostVOImpl) findViewObject("SecondaryConnVO");
    }

    /**
     * Container's getter for PrimaryConnVO.
     * @return PrimaryConnVO
     */
    public PrimaryConnPostVOImpl getPrimaryConnVO() {
        return (PrimaryConnPostVOImpl) findViewObject("PrimaryConnVO");
    }
    
    public void searchPost(String voName, String keyword){
        ViewObjectImpl vo = null;
        if(voName.equals("E")){
            vo = getSupplierNetworkVO();
        }else if(voName.equals("S")){
            vo = getSecondaryConnVO();
        }else if(voName.equals("P")){
            vo = getPrimaryConnVO();
        }else if(voName.equals("M")){
            vo = getMyPost();
        }else if(voName.equals("B")){
            vo = getMyBidsVO1();
        }
        if(vo != null){
            if (keyword == null || keyword.length()==0){
                vo.removeApplyViewCriteriaName("SearchVC");
            }else{
                vo.setApplyViewCriteriaName("SearchVC",true);
                vo.setNamedWhereClauseParam("keyword", keyword);
            }
             vo.executeQuery();
        }
    }

    /**
     * Container's getter for PostVO2.
     * @return PostVO2
     */
    public PostVOImpl getMyPost() {
        return (PostVOImpl) findViewObject("MyPost");
    }
    
    public void setMyPostVC(BigDecimal userId){
        System.out.println("====================setMyPostVC "+userId);
        getMyPost().setNamedWhereClauseParam("bindUserId", userId);
        getMyPost().removeApplyViewCriteriaName("SearchVC");
        getMyPost().setNamedWhereClauseParam("keyword", null);
        getMyPost().executeQuery();
    }
    
    public void setMyBidsVC(BigDecimal userId){
        getMyBidsVO1().setNamedWhereClauseParam("bindUserId", userId);
        getMyBidsVO1().executeQuery();
    }    

    /**
     * Container's getter for MyBidsVO1.
     * @return MyBidsVO1
     */
    public MyBidsVOImpl getMyBidsVO1() {
        return (MyBidsVOImpl) findViewObject("MyBidsVO1");
    }


    public void postDetails(String source, String userId, String postId){
        getPost().setNamedWhereClauseParam("bindPostId", postId);
        if("B".equals(source)){
            getBids().setApplyViewCriteriaName("MyBidsVC");
            getBids().setNamedWhereClauseParam("bindUserId", userId);
        }else{
            getBids().removeApplyViewCriteriaName("MyBidsVC");
        }
        getPost().executeQuery();
    }

    /**
     * Container's getter for BidVO1.
     * @return BidVO1
     */
    public ViewObjectImpl getBids() {
        return (ViewObjectImpl) findViewObject("Bids");
    }

    /**
     * Container's getter for PostToBidVL1.
     * @return PostToBidVL1
     */
    public ViewLinkImpl getPostToBidVL1() {
        return (ViewLinkImpl) findViewLink("PostToBidVL1");
    }

    /**
     * Container's getter for ChatVO1.
     * @return ChatVO1
     */
    public ViewObjectImpl getChats() {
        return (ViewObjectImpl) findViewObject("Chats");
    }

    /**
     * Container's getter for BidToChatVL1.
     * @return BidToChatVL1
     */
    public ViewLinkImpl getBidToChatVL1() {
        return (ViewLinkImpl) findViewLink("BidToChatVL1");
    }

    /**
     * Container's getter for ChatVO1.
     * @return ChatVO1
     */
    public ViewObjectImpl getBidChats() {
        return (ViewObjectImpl) findViewObject("BidChats");
    }
    
    public void createPostRow(){
        System.out.println("====================createPostRow "+ getcreatePostVO().getRowCount());
        Row row = getcreatePostVO().createRow();
        getcreatePostVO().insertRow(row);
        row.setAttribute("CompanyId", ((LoggedInUserVORowImpl) getLoggedInUser().getCurrentRow()).getCompanyId());
        row.setAttribute("UserId", ((LoggedInUserVORowImpl) getLoggedInUser().getCurrentRow()).getUserId());
        row.setNewRowState(Row.STATUS_INITIALIZED);
        System.out.println("====================createPostRow later "+ getcreatePostVO().getRowCount());
    }
    
    public void createComment(int bidId, int userId, String username,String comment){
        System.out.println("====================createComment "+userId+", "+username);
        ViewObjectImpl chatVO = getBidChats();
        Row chat = chatVO.createRow();
        chat.setAttribute("UserId", userId);
        chat.setAttribute("UserName", username);
        chat.setAttribute("BidId", bidId);
        chat.setAttribute("Comment", comment);
        java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        chat.setAttribute("ChatTime",date);
        this.getDBTransaction().commit();
        chatVO.executeQuery();
        
        
    }

    /**
     * Container's getter for SampleUserVO1.
     * @return SampleUserVO1
     */
    public ViewObjectImpl getSampleUser() {
        return (ViewObjectImpl) findViewObject("SampleUser");
    }

    /**
     * Container's getter for LoggedInUserVO1.
     * @return LoggedInUserVO1
     */
    public LoggedInUserVOImpl getLoggedInUser() {
        return (LoggedInUserVOImpl) findViewObject("LoggedInUser");
    }
    
    public Long initLoggedInUser(){
        System.out.println("====================initLoggedInUser");
        LoggedInUserVOImpl userVO = getLoggedInUser();
        LoggedInUserVORowImpl userRow = (LoggedInUserVORowImpl)userVO.createRow();
        userVO.insertRow(userRow);
        
        userRow.setUserId((Long)getSampleUser().getCurrentRow().getAttribute("UserId"));
        userRow.setName((String)getSampleUser().getCurrentRow().getAttribute("User"));
        userRow.setCompanyId((Long)getSampleUser().getCurrentRow().getAttribute("CompanyId"));
        userRow.setCompanyName((String)getSampleUser().getCurrentRow().getAttribute("Company"));
        userRow.setLogoURL((String)getSampleUser().getCurrentRow().getAttribute("LogoURL"));
        
        System.out.println("===================="+userRow.getUserId()+", "+userRow.getCompanyId());
        return userRow.getUserId();
        
    }
    
    public Long initCompany(){
        System.out.println("====================initCompany");
        LoggedInUserVOImpl userVO = getLoggedInUser();
        LoggedInUserVORowImpl userRow = (LoggedInUserVORowImpl)userVO.getCurrentRow();
        System.out.println("===================="+userRow.getCompanyId());
        getPrimaryConnVO().setNamedWhereClauseParam("bindCompId", userRow.getCompanyId());
        getPrimaryConnVO().clearCache();
        getSecondaryConnVO().setNamedWhereClauseParam("bindCompId", userRow.getCompanyId());
        getSecondaryConnVO().clearCache();
        return userRow.getCompanyId();
        
    }
    
    public void initChatforBid(Long bindBidId){
        System.out.println("====================initChatforBid");
        getBidChats().clearCache();
        getBidChats().setNamedWhereClauseParam("bindBidId", bindBidId);
    }
}

