package com.bidServ.bean;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichListView;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputFile;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.layout.RichPanelTabbed;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;
import oracle.adf.view.rich.component.rich.nav.RichLink;
import oracle.adf.view.rich.component.rich.output.RichImage;

public class ShellBackingBean {
    private RichShowDetailItem myPosts;
    private RichShowDetailItem createPost;
    private RichPanelTabbed panelTab;
    private RichPopup chatPopup;
    private RichPopup myBidsChatPopup;
    private RichInputText comment;
    private RichInputFile profilePicFile;
    private RichImage profilePic;
    private RichInputFile companyLogoFile;
    private RichImage companyLogo;
    private RichTable companyTable;
    private RichTable connectionTable;
    private RichInputText companyName;
    private RichInputText address;
    private RichInputText phone;
    private RichInputText userName;
    private RichInputText userPhone;
    private RichInputText userEmail;
    private RichInputText userAddress;
    private RichListView bids;
    private RichLink createBidButton;

    public void setMyPosts(RichShowDetailItem myPosts) {
        this.myPosts = myPosts;
    }

    public RichShowDetailItem getMyPosts() {
        return myPosts;
    }

    public void setCreatePost(RichShowDetailItem createPost) {
        this.createPost = createPost;
    }

    public RichShowDetailItem getCreatePost() {
        return createPost;
    }

    public void setPanelTab(RichPanelTabbed panelTab) {
        this.panelTab = panelTab;
    }

    public RichPanelTabbed getPanelTab() {
        return panelTab;
    }

    public void setChatPopup(RichPopup chatPopup) {
        this.chatPopup = chatPopup;
    }

    public RichPopup getChatPopup() {
        return chatPopup;
    }

    public void setComment(RichInputText comment) {
        this.comment = comment;
    }

    public RichInputText getComment() {
        return comment;
    }

    public void setMyBidsChatPopup(RichPopup myBidsChatPopup) {
        this.myBidsChatPopup = myBidsChatPopup;
    }

    public RichPopup getMyBidsChatPopup() {
        return myBidsChatPopup;
    }

    public void setProfilePicFile(RichInputFile profilePicFile) {
        this.profilePicFile = profilePicFile;
    }

    public RichInputFile getProfilePicFile() {
        return profilePicFile;
    }

    public void setProfilePic(RichImage profilePic) {
        this.profilePic = profilePic;
    }

    public RichImage getProfilePic() {
        return profilePic;
    }

    public void setCompanyLogoFile(RichInputFile companyLogoFile) {
        this.companyLogoFile = companyLogoFile;
    }

    public RichInputFile getCompanyLogoFile() {
        return companyLogoFile;
    }

    public void setCompanyLogo(RichImage companyLogo) {
        this.companyLogo = companyLogo;
    }

    public RichImage getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyTable(RichTable companyTable) {
        this.companyTable = companyTable;
    }

    public RichTable getCompanyTable() {
        return companyTable;
    }

    public void setConnectionTable(RichTable connectionTable) {
        this.connectionTable = connectionTable;
    }

    public RichTable getConnectionTable() {
        return connectionTable;
    }

    public void setCompanyName(RichInputText companyName) {
        this.companyName = companyName;
    }

    public RichInputText getCompanyName() {
        return companyName;
    }

    public void setAddress(RichInputText address) {
        this.address = address;
    }

    public RichInputText getAddress() {
        return address;
    }

    public void setPhone(RichInputText phone) {
        this.phone = phone;
    }

    public RichInputText getPhone() {
        return phone;
    }

    public void setUserName(RichInputText userName) {
        this.userName = userName;
    }

    public RichInputText getUserName() {
        return userName;
    }

    public void setUserPhone(RichInputText userPhone) {
        this.userPhone = userPhone;
    }

    public RichInputText getUserPhone() {
        return userPhone;
    }

    public void setUserEmail(RichInputText userEmail) {
        this.userEmail = userEmail;
    }

    public RichInputText getUserEmail() {
        return userEmail;
    }

    public void setUserAddress(RichInputText userAddress) {
        this.userAddress = userAddress;
    }

    public RichInputText getUserAddress() {
        return userAddress;
    }

    public void setBids(RichListView bids) {
        this.bids = bids;
    }

    public RichListView getBids() {
        return bids;
    }

    public void setCreateBidButton(RichLink createBidButton) {
        this.createBidButton = createBidButton;
    }

    public RichLink getCreateBidButton() {
        return createBidButton;
    }
}
