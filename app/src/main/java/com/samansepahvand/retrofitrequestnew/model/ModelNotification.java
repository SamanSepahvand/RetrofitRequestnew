package com.samansepahvand.retrofitrequestnew.model;


public class ModelNotification {



    public int id;
    //notificaton section

    int notifId;
    String notifTitle;
    String notifBody;

    //data of notification section
    String dataTitle;
    String dataBody;
    String dataKey1;
    String dataImage;

    String senderusername;
    String dataSender;
    //more variable for more help
    String notifDate;
    int messageType;
    int GroupType;

    int GroupId;

    int ReceiverId;
    int SenderId;
    boolean isRead;

    int state;

    String  clickAction;
    int invoiceId;


    public String getClickAction() {
        return clickAction;
    }

    public void setClickAction(String clickAction) {
        this.clickAction = clickAction;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getSenderusername() {
        return senderusername;
    }

    public void setSenderusername(String senderusername) {
        this.senderusername = senderusername;
    }

    public int getSenderId() {
        return SenderId;
    }

    public void setSenderId(int senderId) {
        SenderId = senderId;
    }

    public int getReceiverId() {
        return ReceiverId;
    }

    public void setReceiverId(int receiverId) {
        ReceiverId = receiverId;
    }

    public int getGroupId() {
        return GroupId;
    }

    public void setGroupId(int groupId) {
        GroupId = groupId;
    }



    public int getGroupType() {
        return GroupType;
    }

    public void setGroupType(int groupType) {
        GroupType = groupType;
    }





    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public int getNotifId() {
        return notifId;
    }

    public void setNotifId(int notifId) {
        this.notifId = notifId;
    }

    public boolean getIsRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNotifTitle() {
        return notifTitle;
    }

    public void setNotifTitle(String notifTitle) {
        this.notifTitle = notifTitle;
    }

    public String getNotifBody() {
        return notifBody;
    }

    public void setNotifBody(String notifBody) {
        this.notifBody = notifBody;
    }

    public String getDataTitle() {
        return dataTitle;
    }

    public void setDataTitle(String dataTitle) {
        this.dataTitle = dataTitle;
    }

    public String getDataBody() {
        return dataBody;
    }

    public void setDataBody(String dataBody) {
        this.dataBody = dataBody;
    }

    public String getDataKey1() {
        return dataKey1;
    }

    public void setDataKey1(String dataKey1) {
        this.dataKey1 = dataKey1;
    }

    public String getDataImage() {
        return dataImage;
    }

    public void setDataImage(String dataImage) {
        this.dataImage = dataImage;
    }

    public String getDataSender() {
        return dataSender;
    }

    public void setDataSender(String dataSender) {
        this.dataSender = dataSender;
    }

    public String getNotifDate() {
        return notifDate;
    }

    public void setNotifDate(String notifDate) {
        this.notifDate = notifDate;
    }
}