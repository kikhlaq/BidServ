package com.bidServ.bean;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.layout.RichPanelTabbed;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;

public class ShellBackingBean {
    private RichShowDetailItem myPosts;
    private RichShowDetailItem createPost;
    private RichPanelTabbed panelTab;
    private RichPopup chatPopup;
    private RichInputText comment;

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
}
