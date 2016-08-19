package com.bidServ.bean;

import java.util.Map;

import javax.el.ELContext;

import javax.el.ExpressionFactory;

import javax.el.ValueExpression;

import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.DataControlFrame;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import org.apache.myfaces.trinidad.event.DisclosureEvent;

public class CommonManagedBean {
    public CommonManagedBean() {
        super();
    }
    
    public static Map getPageFlowScope(){
       return (AdfFacesContext.getCurrentInstance().getPageFlowScope());
    }
    
    public static void rollback(){
          Map sessionMap =
              FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
          BindingContext context =
              (BindingContext)sessionMap.get(BindingContext.CONTEXT_ID);
          String currentFrameName = context.getCurrentDataControlFrame();
          DataControlFrame dcFrame =
              context.findDataControlFrame(currentFrameName);
          dcFrame.rollback();
          //dcFrame.beginTransaction(null);
          System.out.println("====================roll");
      }

    public void tabDisclosureListener(DisclosureEvent disclosureEvent) {
        System.out.println("====================disclosureEvent = "+disclosureEvent.getComponent().getId()+", "+disclosureEvent.isExpanded());
        String newPostCreated = (String)getRequestScope().get("newPostCreated");
        if(newPostCreated != null){
            return ;
        }
        
        String isNewPostTab = (String) getPageFlowScope().get("isNewPostTab");
        if (isNewPostTab != null){
            rollback();
            getPageFlowScope().put("isNewPostTab", null);
            System.out.println("====================cancel post");
        }
    }
    
    public static Map getRequestScope(){
        return FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
    }

    public void submitPost(ActionEvent actionEvent) {
        System.out.println("====================submitPost");
        commit();
        ShellBackingBean shellBean = (ShellBackingBean)FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().get("ShellBackingBean");
        //DisclosureEvent createPost = new DisclosureEvent(shellBean.getCreatePost(),false);
        //createPost.queue();
        getPageFlowScope().put("isNewPostTab", null);
        DisclosureEvent myPost = new DisclosureEvent(shellBean.getMyPosts(),true);
        myPost.queue();
        AdfFacesContext.getCurrentInstance().addPartialTarget(shellBean.getPanelTab());
        // Add event code here...
    }
    
    public void goToMyPost(){
        
    }
    
    public static void commit()
     {System.out.println("====================1");
            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding operationBinding = bindings.getOperationBinding("Commit");
            Object result = operationBinding.execute();
            System.out.println("====================3");
        }
    
    public static Map getBackingBeanScope(){
        System.out.println("====================4");
                String el = "#{backingBeanScope}";
                FacesContext facesContext = FacesContext.getCurrentInstance();
                ELContext elContext = facesContext.getELContext();
                ExpressionFactory expressionFactory =
                facesContext.getApplication().getExpressionFactory();
                ValueExpression exp =
                expressionFactory.createValueExpression(elContext, el,
                Object.class);
                Map map =(Map)exp.getValue(elContext);
        System.out.println("====================5");
                return map;
    }

    public void saveComment(ActionEvent actionEvent) {
        System.out.println("====================saveComment");
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding = bindings.getOperationBinding("createComment");
        Object result = operationBinding.execute();
        System.out.println("====================3");
        ShellBackingBean shellBean = (ShellBackingBean)FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().get("ShellBackingBean");
        shellBean.getComment().resetValue();
        getRequestScope().put("comment",null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(shellBean.getComment());
    }

    public void cancelCommentPopup(ActionEvent actionEvent) {
        // Add event code here...
        ShellBackingBean shellBean = (ShellBackingBean)FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().get("ShellBackingBean");
        shellBean.getChatPopup().hide();
    }

    public void showChat(ActionEvent actionEvent) {
        ShellBackingBean shellBean = (ShellBackingBean)FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().get("ShellBackingBean");
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        shellBean.getChatPopup().show(hints);
        // Add event code here...
    }
}
