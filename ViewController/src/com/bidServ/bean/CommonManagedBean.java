package com.bidServ.bean;

import java.io.FileOutputStream;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import java.util.Properties;



import javax.el.ELContext;

import javax.el.ExpressionFactory;

import javax.el.MethodExpression;
import javax.el.ValueExpression;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;

import javax.faces.event.ActionListener;
import javax.faces.event.FacesEvent;
import javax.faces.event.ValueChangeEvent;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Transport;

import javax.naming.Context;

import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.InitialDirContext;

import javax.naming.ldap.InitialLdapContext;

import javax.naming.ldap.LdapContext;

import javax.servlet.ServletContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import oracle.adf.model.AttributeBinding;
import oracle.adf.model.BindingContext;
import oracle.adf.model.DataControlFrame;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.fragment.RichRegion;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.Key;

import oracle.jbo.uicli.binding.JUEventBinding;

import org.apache.myfaces.trinidad.event.DisclosureEvent;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.UploadedFile;

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
        ShellBackingBean shellBean = (ShellBackingBean)getPageFlowScope().get("ShellBackingBean");
        //DisclosureEvent createPost = new DisclosureEvent(shellBean.getCreatePost(),false);
        //createPost.queue();
        System.out.println("====================submitPost "+shellBean);
        getPageFlowScope().put("isNewPostTab", null);
        
//        BindingContainer bindingContainer = BindingContext.getCurrent().getCurrentBindingsEntry();
//            JUEventBinding eventBinding = (JUEventBinding)bindingContainer.get("eventBinding");
//            
//            ActionListener actionListener = (ActionListener)eventBinding.getListener();
//            actionListener.processAction(actionEvent);
        DisclosureEvent myPost = new DisclosureEvent(shellBean.getMyPosts(),true);
        myPost.queue();
        AdfFacesContext.getCurrentInstance().addPartialTarget(shellBean.getPanelTab());
        // Add event code here...
    }
    
    public void handleNewPostEvent() {
        System.out.println("====================handleNewPostEvent");
        ShellBackingBean shellBean = (ShellBackingBean)getPageFlowScope().get("ShellBackingBean");
        System.out.println("====================submitPost "+shellBean);
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
        ShellBackingBean shellBean = (ShellBackingBean)getPageFlowScope().get("ShellBackingBean");
        shellBean.getComment().resetValue();
        getRequestScope().put("comment",null);
        AdfFacesContext.getCurrentInstance().addPartialTarget(shellBean.getComment());
    }

    public void cancelCommentPopup(ActionEvent actionEvent) {
        // Add event code here...
        ShellBackingBean shellBean = (ShellBackingBean)getPageFlowScope().get("ShellBackingBean");
        shellBean.getChatPopup().hide();
    }
    
    public void cancelBidCommentPopup(ActionEvent actionEvent) {
        // Add event code here...
        ShellBackingBean shellBean = (ShellBackingBean)getPageFlowScope().get("ShellBackingBean");
        shellBean.getMyBidsChatPopup().hide();
    }

    public void showChat(ActionEvent actionEvent) {
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding = bindings.getOperationBinding("initChatforBid");
        Object result = operationBinding.execute();
        
        ShellBackingBean shellBean = (ShellBackingBean)getPageFlowScope().get("ShellBackingBean");
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        shellBean.getChatPopup().show(hints);
        // Add event code here...
    }
    
    public void showBidChat(ActionEvent actionEvent) {
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding = bindings.getOperationBinding("initChatforBid");
        Object result = operationBinding.execute();
        
        ShellBackingBean shellBean = (ShellBackingBean)getPageFlowScope().get("ShellBackingBean");
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        shellBean.getMyBidsChatPopup().show(hints);
        // Add event code here...
    }

    public void addProfilePicListener(ActionEvent actionEvent) {
        ShellBackingBean shellBean = (ShellBackingBean)getPageFlowScope().get("ShellBackingBean");
        //shellBean.getProfilePicFile().queueEvent(actionEvent);
        // Add event code here...
    }

    public void uploadProfilePicListener(ValueChangeEvent valueChangeEvent) {
        System.out.println("====================uploadProfilePicListener");
        
        UploadedFile file = (UploadedFile) valueChangeEvent.getNewValue();
        String fileName = file.getFilename();
        
        ServletContext servletContext = (ServletContext) FacesContext
                .getCurrentInstance().getExternalContext().getContext();
        String path = "com/bidServ/resource/images/"+fileName;
        String absPath = servletContext.getRealPath("com/bidServ/resource/images/")+"/"+fileName;
        System.out.println("====================uploadProfilePicListener "+path);
        
        InputStream inputStream = null;
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(absPath);
            inputStream = file.getInputStream();
            byte[] buffer = new byte[8192];
            int bytesRead = 0;
            while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }
            fileOutputStream.flush();
            fileOutputStream.close();
            System.out.println("====================uploadProfilePicListener 3");
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
            }
        }
        
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        AttributeBinding attrBinding = (AttributeBinding)bindings.getControlBinding("ImageUrl");
        attrBinding.setInputValue(path);
        ShellBackingBean shellBean = (ShellBackingBean)getPageFlowScope().get("RegisterBean");
        AdfFacesContext.getCurrentInstance().addPartialTarget(shellBean.getProfilePic());
        // Add event code here...
    }

    public void submitRegistrationListener(ActionEvent actionEvent) {
        System.out.println("====================submitRegistrationListener 1");
        String companyMode = (String)getPageFlowScope().get("companyMode");
        if("NEW".equals(companyMode)){
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage message = null;
            boolean errorFound = false;
            ShellBackingBean shellBean = (ShellBackingBean)getPageFlowScope().get("RegisterBean");
            if (shellBean.getCompanyName() != null && shellBean.getCompanyName().getValue() == null) {
                message = new FacesMessage("Name is mandatory.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                fc.addMessage(null, message);
                errorFound = true;
                System.out.println("====================submitRegistrationListener errorFound");
            }
            if (shellBean.getAddress() != null && shellBean.getAddress().getValue() == null) {
                message = new FacesMessage("Address is mandatory.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                fc.addMessage(null, message);
                errorFound = true;
                System.out.println("====================submitRegistrationListener errorFound");
            }
            if (shellBean.getPhone() != null && shellBean.getPhone().getValue() == null) {
                message = new FacesMessage("Phone is mandatory.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                fc.addMessage(null, message);
                errorFound = true;
                System.out.println("====================submitRegistrationListener errorFound");
            }
                    
            if (errorFound){
                getRequestScope().put("error","Y");
                return;
            }
        }
        
        
        
        // Add event code here...
        
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        
        String mode = (String)getPageFlowScope().get("mode");
        if("PROFILE".equals(mode)){
            commit();
            return;
        }else{
            OperationBinding operationBinding = bindings.getOperationBinding("storePassword");
            Object result = operationBinding.execute();
        }
        
        if(!"NEW".equals(companyMode)){
            OperationBinding operationBinding = bindings.getOperationBinding("deleteNewCompanay");
            AttributeBinding attrBinding = (AttributeBinding)bindings.getControlBinding("IsAdmin");
            attrBinding.setInputValue("N");
            attrBinding = (AttributeBinding)bindings.getControlBinding("Status2");
            attrBinding.setInputValue("PENDING");
            Object result = operationBinding.execute();
            System.out.println("====================submitRegistrationListener 3");
        }else{
            AttributeBinding attrBinding = (AttributeBinding)bindings.getControlBinding("IsAdmin");
            attrBinding.setInputValue("Y");
            attrBinding = (AttributeBinding)bindings.getControlBinding("Status2");
            attrBinding.setInputValue("APPROVED");
            attrBinding = (AttributeBinding)bindings.getControlBinding("Status");
            attrBinding.setInputValue("APPROVED");
        }
        
        System.out.println("====================submitRegistrationListener email "+getPageFlowScope().get("email"));
        System.out.println("====================submitRegistrationListener code "+getPageFlowScope().get("invitationCode}"));
        if (getPageFlowScope().get("email") != null && getPageFlowScope().get("invitationCode") != null) {
            OperationBinding operationBinding = bindings.getOperationBinding("invitationAccepted");
            Object result = operationBinding.execute();

        }
        
        commit();
        if ("NEW".equals(companyMode)) {
            AttributeBinding attrBinding = (AttributeBinding) bindings.getControlBinding("CompanyId2");
            System.out.println("====================submitRegistrationListener CompanyId " +
                               ((AttributeBinding) bindings.getControlBinding("CompanyId")).getInputValue());
            attrBinding.setInputValue(((AttributeBinding) bindings.getControlBinding("CompanyId")).getInputValue());
            System.out.println("====================submitRegistrationListener CompanyId " +
                               attrBinding.getInputValue());

            commit();
        }
        
    }
    
    public void logout(){
        getPageFlowScope().clear();
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/faces/Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void uploadCompLogoListener(ValueChangeEvent valueChangeEvent) {
        System.out.println("====================uploadCompLogoListener");
        UploadedFile file = (UploadedFile) valueChangeEvent.getNewValue();
        String fileName = file.getFilename();
        
        ServletContext servletContext = (ServletContext) FacesContext
                .getCurrentInstance().getExternalContext().getContext();
        String path = "com/bidServ/resource/images/"+fileName;
        String absPath = servletContext.getRealPath("com/bidServ/resource/images/")+"/"+fileName;
        System.out.println("====================uploadProfilePicListener "+path);
        
        InputStream inputStream = null;
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(absPath);
            inputStream = file.getInputStream();
            byte[] buffer = new byte[8192];
            int bytesRead = 0;
            while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }
            fileOutputStream.flush();
            fileOutputStream.close();
            System.out.println("====================uploadProfilePicListener 3");
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
            }
        }
        
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        AttributeBinding attrBinding = (AttributeBinding)bindings.getControlBinding("LogoUrl1");
        attrBinding.setInputValue(path);
        ShellBackingBean shellBean = (ShellBackingBean)getPageFlowScope().get("RegisterBean");
        AdfFacesContext.getCurrentInstance().addPartialTarget(shellBean.getCompanyLogo());
        // Add event code here...
    }

    public void createConnectionListener(DialogEvent dialogEvent) {
        // Add event code here...
        if (dialogEvent.getOutcome().equals(DialogEvent.Outcome.ok)) {
            System.out.println("====================createConnectionListener 3");
            ShellBackingBean shellBean = (ShellBackingBean)getPageFlowScope().get("ShellBackingBean");
            RowKeySet selectedRowKeys = shellBean.getCompanyTable().getSelectedRowKeys();
            ArrayList selectedKeys = getSelectedKeys(selectedRowKeys);
            System.out.println("====================createConnectionListener ="+shellBean.getCompanyTable().getSelectedRowKeys().size());
            getRequestScope().put("selectedKeys",selectedKeys);
            
            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding operationBinding = bindings.getOperationBinding("createConnection");
            String result = (String)operationBinding.execute();
            commit();
            
            DCIteratorBinding iter = ((DCBindingContainer)bindings).findIteratorBinding("ConnectionIterator");
             iter.executeQuery();
             
            AdfFacesContext.getCurrentInstance().addPartialTarget(shellBean.getConnectionTable());
            
            FacesContext fc = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage("Connection(s) submitted for approval.");
            message.setSeverity(FacesMessage.SEVERITY_INFO);
            fc.addMessage(null, message);
            
            if(result != null){
                message =
                    new FacesMessage("Connections already exist, declined or pending for these companies: " +
                                     result);
                message.setSeverity(FacesMessage.SEVERITY_INFO);
                fc.addMessage(null, message);
            }
                
            
            
        }
    }
    
    private static ArrayList getSelectedKeys(RowKeySet keySet){
        ArrayList keys = null;
        Iterator selectionSetIterator = null;
        Key rowKey = null;
        if (keySet != null && keySet.size() >0){
            keys = new ArrayList();
            selectionSetIterator = keySet.iterator();
            while (selectionSetIterator.hasNext()){
                rowKey = (Key)((List)selectionSetIterator.next()).get(0);
                keys.add(rowKey);
           }
        }
        return keys;
    }

    public void connResponseListener(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding = bindings.getOperationBinding("updateConnectionRequest");
        String result = (String)operationBinding.execute();
        commit();
        
        DCIteratorBinding iter = ((DCBindingContainer)bindings).findIteratorBinding("ConnectionIterator");
         iter.executeQuery();
         
         String respCode = (String)getRequestScope().get("respCode");
         String response = null;
         if ("ACCEPT".equals(respCode)){
             response="Connection accepted successfully.";
         }else if ("DECLINE".equals(respCode)){
             response="Connection declined successfully.";
         }else if ("DELETE".equals(respCode)){
             response="Connection deleted successfully.";
         }else{
             response="Connection withdrawn successfully.";
         }
        FacesContext fc = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(response);
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        fc.addMessage(null, message);
         
    }

    public void userResponseListener(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding = bindings.getOperationBinding("updateUserRequest");
        String result = (String)operationBinding.execute();
        commit();
        
        DCIteratorBinding iter = ((DCBindingContainer)bindings).findIteratorBinding("CompanyUserIterator");
         iter.executeQuery();
         
         String actionCode = (String)getRequestScope().get("actionCode");
         String response = null;
         if ("APPROVE".equals(actionCode)){
             response="User approved successfully.";
         }else if ("REJECT".equals(actionCode)){
             response="User rejected successfully.";
         }else if ("DELETE".equals(actionCode)){
             response="User deleted successfully.";
         }else{
             response="User made Administrator successfully.";
         }
        FacesContext fc = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(response);
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        fc.addMessage(null, message);
    }

    public void createBidListener(DialogEvent dialogEvent) {
        if (dialogEvent.getOutcome().equals(DialogEvent.Outcome.ok)){
            // Add event code here...
            ShellBackingBean shellBean = (ShellBackingBean)getPageFlowScope().get("ShellBackingBean");
            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding operationBinding = bindings.getOperationBinding("createBid");
            String result = (String)operationBinding.execute();
            commit();
            
            DCIteratorBinding iter = ((DCBindingContainer)bindings).findIteratorBinding("BidsIterator");
             iter.executeQuery();
             
            AttributeBinding attrBinding = (AttributeBinding)bindings.getControlBinding("IsBidAllowed");
            attrBinding.setInputValue("N");
            
             
            AdfFacesContext.getCurrentInstance().addPartialTarget(shellBean.getBids());
            AdfFacesContext.getCurrentInstance().addPartialTarget(shellBean.getCreateBidButton());
        }
        
        
    }
    
    private void navigate(FacesEvent event, String outcome) {
        RichRegion regionComponent = null;

        for (UIComponent uic = event.getComponent().getParent(); uic != null; uic = uic.getParent()) {
            if (uic instanceof RichRegion) {
                regionComponent = (RichRegion) uic;
                break;
            }
        }

        if (regionComponent != null) {
            FacesContext fc = FacesContext.getCurrentInstance();
            ExpressionFactory ef = fc.getApplication().getExpressionFactory();
            ELContext elc = fc.getELContext();
            MethodExpression me = ef.createMethodExpression(elc, outcome, String.class, new Class[] { });
            regionComponent.queueActionEventInRegion(me, null, null, false, -1, -1, event.getPhaseId());
        }

    }

    public String registerNavigationListener() {
        System.out.println("====================registerNavigationListener 1");
        System.out.println("====================registerNavigationListener "+ getRequestScope().get("error"));
        if(getRequestScope().get("error") != null)
            return null;
        // Add event code here...
        String companyMode = (String)getPageFlowScope().get("companyMode");
        if ("NEW".equals(companyMode)){
            getRequestScope().put("toAdmin","Y");
        }
        return "goToHome";
    }

    public String registerNextListener() {
        System.out.println("====================registerNextListener 1");
        FacesContext fc = FacesContext.getCurrentInstance();
        FacesMessage message = null;
        boolean errorFound = false;
        ShellBackingBean shellBean = (ShellBackingBean)getPageFlowScope().get("RegisterBean");
        if (shellBean.getUserName() != null && shellBean.getUserName().getValue() == null) {
            message = new FacesMessage("Name is mandatory.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, message);
            errorFound = true;
            System.out.println("====================registerNextListener errorFound");
        }
        if (shellBean.getUserPhone() != null && shellBean.getUserPhone().getValue() == null) {
            message = new FacesMessage("Phone is mandatory.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, message);
            errorFound = true;
            System.out.println("====================registerNextListener errorFound");
        }
        if (shellBean.getUserEmail() != null && shellBean.getUserEmail().getValue() == null) {
            message = new FacesMessage("Email is mandatory.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, message);
            errorFound = true;
            System.out.println("====================registerNextListener errorFound");
        }
        if (shellBean.getUserAddress() != null && shellBean.getUserAddress().getValue() == null) {
            message = new FacesMessage("Address is mandatory.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, message);
            errorFound = true;
            System.out.println("====================registerNextListener errorFound");
        }
        if (shellBean.getUserPassword() != null && shellBean.getUserPassword().getValue() == null) {
            message = new FacesMessage("Password is mandatory.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, message);
            errorFound = true;
            System.out.println("====================registerNextListener errorFound");
        }
        if (shellBean.getUserResetPassword() != null && shellBean.getUserResetPassword().getValue() == null) {
            message = new FacesMessage("Reset password is mandatory.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, message);
            errorFound = true;
            System.out.println("====================registerNextListener errorFound");
        }
        if (shellBean.getUserPassword() != null  && shellBean.getUserResetPassword() != null &&
            shellBean.getUserPassword().getValue() != null && shellBean.getUserResetPassword().getValue() != null) {
            if(!shellBean.getUserPassword().getValue().equals(shellBean.getUserResetPassword().getValue())){
                message = new FacesMessage("Password and Reset password does not match");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                fc.addMessage(null, message);
                errorFound = true;
                System.out.println("====================registerNextListener errorFound");
            }
        }
                
        if (errorFound){
            getRequestScope().put("error","Y");
            return null;
        }
        // Add event code here...
        return "next";
    }

    public void grantBidListener(ActionEvent actionEvent) {
        // Add event code here...
        ShellBackingBean shellBean = (ShellBackingBean)getPageFlowScope().get("ShellBackingBean");
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding = bindings.getOperationBinding("grantBid");
        String result = (String)operationBinding.execute();
        commit();
        
        DCIteratorBinding iter = ((DCBindingContainer)bindings).findIteratorBinding("BidsIterator");
         iter.executeQuery();
         
        AttributeBinding attrBinding = (AttributeBinding)bindings.getControlBinding("IsBidAllowed");
        attrBinding.setInputValue("N");
        
         
        AdfFacesContext.getCurrentInstance().addPartialTarget(shellBean.getBids());
        AdfFacesContext.getCurrentInstance().addPartialTarget(shellBean.getCreateBidButton());
    }

    public void inviteListener(ActionEvent actionEvent) {
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding = bindings.getOperationBinding("sendInvites");
        String result = (String)operationBinding.execute();
        commit();

        FacesContext fc = FacesContext.getCurrentInstance();
        FacesMessage msg = null;
        boolean errorFound = false;
        msg = new FacesMessage("Invitations sent successfully.");
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        fc.addMessage(null, msg);
        
        if(true)
        return ;
        
        
    }
    
    public void initSession(String username){
        ADFContext.getCurrent().getSessionScope().put("user", username);
    }

    public void requestVerificationListener(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding = bindings.getOperationBinding("requestVerification");
        String result = (String)operationBinding.execute();
        
        AttributeBinding attrBinding = (AttributeBinding)bindings.getControlBinding("IsVerified");
        attrBinding.setInputValue("N");
        commit();

        FacesContext fc = FacesContext.getCurrentInstance();
        FacesMessage msg = null;
        boolean errorFound = false;
        msg = new FacesMessage("Verification request sent successfully.");
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        fc.addMessage(null, msg);
    }

    public void completeVerificationListener(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding = bindings.getOperationBinding("completeVerificationRequest");
        String result = (String)operationBinding.execute();
        commit();
        
        DCIteratorBinding iter = ((DCBindingContainer)bindings).findIteratorBinding("VerificationRequestIterator");
         iter.executeQuery();
        
    }

    public void reportUserListener(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding = bindings.getOperationBinding("reportUser");
        String result = (String)operationBinding.execute();
        commit();
        
        FacesContext fc = FacesContext.getCurrentInstance();
        FacesMessage msg = null;
        boolean errorFound = false;
        msg = new FacesMessage("User reported successfully.");
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        fc.addMessage(null, msg);
    }

    public void reportCompanyListener(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding = bindings.getOperationBinding("reportCompany");
        String result = (String)operationBinding.execute();
        commit();
        
        FacesContext fc = FacesContext.getCurrentInstance();
        FacesMessage msg = null;
        boolean errorFound = false;
        msg = new FacesMessage("Company reported successfully.");
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        fc.addMessage(null, msg);
    }

    public void handleReportResponse(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding = bindings.getOperationBinding("handlReportedEntity");
        String result = (String)operationBinding.execute();
        commit();
        
        String type = (String) getRequestScope().get("reportType");
        if("USER".equals(type)){
            DCIteratorBinding iter = ((DCBindingContainer)bindings).findIteratorBinding("ReportedUserIterator");
             iter.executeQuery();
        }else{
            DCIteratorBinding iter = ((DCBindingContainer)bindings).findIteratorBinding("ReportedComanyIterator");
             iter.executeQuery();
        }
        
    }

    public void uploadPostAttachmentListener(ValueChangeEvent valueChangeEvent) {
        System.out.println("====================uploadPostAttachmentListener ");
        UploadedFile file = (UploadedFile) valueChangeEvent.getNewValue();
        String fileName = file.getFilename();
        String path = null;
        String id = getPageFlowScope().get("attachmentId").toString();
        
        InputStream inputStream = null;
        try{
            inputStream = file.getInputStream();
            path = AttachmentHelper.uploadAttachment("POST", id, fileName, inputStream);
            System.out.println("====================uploadPostAttachmentListener ="+path);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(path != null){
            getPageFlowScope().put("attachmentUrl", path);
            getPageFlowScope().put("attachmentName", fileName);
            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding operationBinding = bindings.getOperationBinding("createAttachment");
            String result = (String)operationBinding.execute();
            commit();
            DCIteratorBinding iter = ((DCBindingContainer)bindings).findIteratorBinding("PostAttachmentIterator");
             iter.executeQuery();
            System.out.println("====================doneeeeeeeeeeeeeee ="+path);
        }
        ShellBackingBean shellBean = (ShellBackingBean)getPageFlowScope().get("ShellBackingBean");
        shellBean.getPostAttachmentPopup().hide();
        
        getPageFlowScope().put("attachmentType", null);
        getPageFlowScope().put("attachmentId", null);
        getPageFlowScope().put("attachmentUrl", null);
        getPageFlowScope().put("attachmentName", null);
        
        // Add event code here...
    }

    public void openPostAttachmentListener(ActionEvent actionEvent) {
        // Add event code here...
        ShellBackingBean shellBean = (ShellBackingBean)getPageFlowScope().get("ShellBackingBean");
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        shellBean.getPostAttachmentPopup().show(hints);
    }

    public void openBidAttachmentPopup(ActionEvent actionEvent) {
        ShellBackingBean shellBean = (ShellBackingBean)getPageFlowScope().get("ShellBackingBean");
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        shellBean.getBidAttachmentPopup().show(hints);
        // Add event code here...
    }

    public void uploadBidAttachmentListener(ValueChangeEvent valueChangeEvent) {
        System.out.println("====================uploadBidAttachmentListener ");
        UploadedFile file = (UploadedFile) valueChangeEvent.getNewValue();
        String fileName = file.getFilename();
        String path = null;
        String id = getPageFlowScope().get("attachmentId").toString();
        
        InputStream inputStream = null;
        try{
            inputStream = file.getInputStream();
            path = AttachmentHelper.uploadAttachment("BID", id, fileName, inputStream);
            System.out.println("====================uploadBidAttachmentListener ="+path);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(path != null){
            getPageFlowScope().put("attachmentUrl", path);
            getPageFlowScope().put("attachmentName", fileName);
            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding operationBinding = bindings.getOperationBinding("createAttachment");
            String result = (String)operationBinding.execute();
            commit();
            DCIteratorBinding iter = ((DCBindingContainer)bindings).findIteratorBinding("BidAttachmentIterator");
             iter.executeQuery();
            System.out.println("==========uploadBidAttachmentListener==========doneeeeeeeeeeeeeee ="+path);
        }
        ShellBackingBean shellBean = (ShellBackingBean)getPageFlowScope().get("ShellBackingBean");
        shellBean.getBidAttachmentPopup().hide();
        
        getPageFlowScope().put("attachmentType", null);
        getPageFlowScope().put("attachmentId", null);
        getPageFlowScope().put("attachmentUrl", null);
        getPageFlowScope().put("attachmentName", null);
        // Add event code here...
    }

    public void showBidAttachmentListener(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        OperationBinding operationBinding = bindings.getOperationBinding("setBidRow");
        String result = (String)operationBinding.execute();
        DCIteratorBinding iter = ((DCBindingContainer)bindings).findIteratorBinding("BidAttachmentIterator");
        iter.executeQuery();
        
        ShellBackingBean shellBean = (ShellBackingBean)getPageFlowScope().get("ShellBackingBean");
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        shellBean.getAllBidAttachmentPopup().show(hints);
    }

    public void openBidPopup(ActionEvent actionEvent) {
        // Add event code here...
        ShellBackingBean shellBean = (ShellBackingBean)getPageFlowScope().get("ShellBackingBean");
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        shellBean.getEditBidPopup().show(hints);
    }

    public void editBidListener(DialogEvent dialogEvent) {
        // Add event code here...
        if (dialogEvent.getOutcome().equals(DialogEvent.Outcome.ok)){
            // Add event code here...
            ShellBackingBean shellBean = (ShellBackingBean)getPageFlowScope().get("ShellBackingBean");
            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding operationBinding = bindings.getOperationBinding("editBid");
            String result = (String)operationBinding.execute();
            commit();
            
            DCIteratorBinding iter = ((DCBindingContainer)bindings).findIteratorBinding("BidsIterator");
             iter.executeQuery();
             
             
            AdfFacesContext.getCurrentInstance().addPartialTarget(shellBean.getBids());
        }
    }

    public void loginListener(ActionEvent actionEvent) {
        // Add event code here...
        
        
    }

    public void closePostListener(ActionEvent actionEvent) {
        // Add event code here...
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
        AttributeBinding attrBinding = (AttributeBinding)bindings.getControlBinding("PostStatusCode");
        attrBinding.setInputValue("CLOSED");
        
//         attrBinding = (AttributeBinding)bindings.getControlBinding("ImageUrl");
//        attrBinding.setInputValue(path);
        commit();
        
    }

    public String login() {
        // Add event code here...
        FacesContext fc = FacesContext.getCurrentInstance();
        FacesMessage message = null;
        boolean errorFound = false;
        if(getRequestScope().get("username") == null){
            message = new FacesMessage("Username is mandatory.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, message);
            errorFound = true;
            System.out.println("====================registerNextListener errorFound");
            
        }
        if(getRequestScope().get("password") == null){
            message = new FacesMessage("Password is mandatory.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(null, message);
            errorFound = true;
            System.out.println("====================registerNextListener errorFound");
            
        }
        if(getRequestScope().get("username") != null && getRequestScope().get("password") != null){
            return "login";
        }else{
            return null;    
        }
        
    }

    public void loginFailed() {
        FacesContext fc = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage("Username or Password is incorrect.");
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        fc.addMessage(null, message);
    }
}
