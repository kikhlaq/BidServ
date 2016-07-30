package com.bidServ.bean;

import oracle.adf.view.rich.component.rich.layout.RichPanelTabbed;
import oracle.adf.view.rich.component.rich.layout.RichShowDetailItem;

public class ShellBackingBean {
    private RichShowDetailItem myPosts;
    private RichShowDetailItem createPost;
    private RichPanelTabbed panelTab;

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
}
