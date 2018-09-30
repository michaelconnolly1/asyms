package com.example.michael.aysms.model;

import android.net.Uri;
import android.util.Log;

import com.example.michael.aysms.Utils.AysmsDate;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by laptop on 06/09/2018.
 */

@Entity
public class MessageEntity implements Comparable<MessageEntity> {
    @Id(autoincrement = true)
    private Long messageID;

    private Date messageDate;
    private int dbMessageID;
    private String title;
    private String messageBody;
    private String sender;
    private int messageType;
    private String fileName;

    @Generated(hash = 1920318659)
    public MessageEntity(Long messageID, Date messageDate, int dbMessageID,
            String title, String messageBody, String sender, int messageType,
            String fileName) {
        this.messageID = messageID;
        this.messageDate = messageDate;
        this.dbMessageID = dbMessageID;
        this.title = title;
        this.messageBody = messageBody;
        this.sender = sender;
        this.messageType = messageType;
        this.fileName = fileName;
    }

    @Generated(hash = 1797882234)
    public MessageEntity() {
    }

    @Override
    public int compareTo(MessageEntity message) {
        AysmsDate date = new AysmsDate();
        String dateString = date.convertDateToString(messageDate);
        AysmsDate prevDate = new AysmsDate();
        String prevDateString = date.convertDateToString(message.messageDate);
        Log.d("COMPARE", dateString + " " + prevDateString);
        return boolToInt(dateString.equals(prevDateString));
    }

    private int boolToInt(boolean b) {
        return b ? 1 : 0;
    }

    public Long getMessageID() {
        return this.messageID;
    }

    public void setMessageID(Long messageID) {
        this.messageID = messageID;
    }

    public Date getMessageDate() {
        return this.messageDate;
    }

    public void setMessageDate(Date messageDate) {
        this.messageDate = messageDate;
    }

    public int getDbMessageID() {
        return this.dbMessageID;
    }

    public void setDbMessageID(int dbMessageID) {
        this.dbMessageID = dbMessageID;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessageBody() {
        return this.messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public String getSender() {
        return this.sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public int getMessageType() {
        return this.messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
