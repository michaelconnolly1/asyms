package com.example.michael.aysms.model;

import android.net.Uri;
import android.util.Log;

import com.example.michael.aysms.App;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by laptop on 07/09/2018.
 */

public class Message implements Comparable<Message> {
    private MessageEntityDao MessageDao;
    private MessageEntity messageDetails;

    public Message(DaoSession daoSession) {
        Log.d("Message Constructor:", "Creating the instance");
        setDaoSession(daoSession);
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        messageDetails = new MessageEntity((long)1, today, 0, "Title 1", "Message 1", "", 0, null);
    }

    public MessageEntity getMessageDetails() {
        return messageDetails;
    }

    public void setMessageDetails(MessageEntity messageDetails) {
        this.messageDetails = messageDetails;
        insertMessage(this.messageDetails);
    }

    public void setDaoSession(DaoSession daoSession) {
        if (MessageDao == null) {
            MessageDao = daoSession.getMessageEntityDao();
        }
    }

    public MessageEntityDao getMessageDao() {
        return MessageDao;
    }

    public MessageEntity getMessages() {
        List<MessageEntity> pList = MessageDao.queryBuilder().build().list();
        Log.i("getMessage: SIZE", Integer.toString(pList.size()));
        if (pList.size() == 0) {
            Log.i("Messages: getMessage", "SIZE IS 0");
            insertMessage(messageDetails);
            return messageDetails;
        }
        else {
            messageDetails = pList.get(0);
            return messageDetails;
        }
    }

    public MessageEntity createMessage(int dbMessageID, String title, String message, String sender, int type) {
        MessageEntity n1 = new MessageEntity();
        n1.setMessageID(null);

        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        n1.setMessageDate(today);
        n1.setDbMessageID(dbMessageID);
        n1.setTitle(title);
        n1.setMessageBody(message);
        n1.setSender(sender);
        n1.setMessageType(type);
        insertMessage(n1);
        return n1;
    }

    public List getAllMessages() {
        List<MessageEntity> pList = MessageDao.queryBuilder().orderAsc(MessageEntityDao.Properties.MessageDate).build().list();
        Log.i("getMessageAll: SIZE", Integer.toString(pList.size()));
        if (pList.size() > 0) {
            Log.i("getMessageAll: ", pList.get(0).getMessageBody());
            for (MessageEntity e : pList) {
                if (e.getFileName() == null)
                    Log.d("getMessageAll: ", e.getMessageID() + " " + e.getMessageDate() + " " + e.getMessageBody() + " " + e.getDbMessageID());
                else
                    Log.d("getMessageAll: ", e.getMessageID() + " " + e.getMessageDate() + " " + e.getMessageBody() + " " + e.getDbMessageID() + " " + e.getFileName());
            }
        }
        return pList;
    }

    public List getAllMessages(int patientID, int type) {
        List<MessageEntity> pList = MessageDao.queryBuilder().whereOr(MessageEntityDao.Properties.Sender.eq(Integer.toString(patientID)), MessageEntityDao.Properties.MessageType.eq(type)).orderAsc(MessageEntityDao.Properties.MessageDate).build().list();

        Log.i("getMessageAll: SIZE", Integer.toString(pList.size()));
        if (pList.size() > 0) {
            Log.i("getMessageAll: ", pList.get(0).getMessageBody());
            for (MessageEntity e : pList) {
                if (e.getFileName() == null)
                    Log.d("getMessageAll: ", e.getMessageID() + " " + e.getMessageDate() + " " + e.getMessageBody() + " " + e.getDbMessageID());
                else
                    Log.d("getMessageAll: ", e.getMessageID() + " " + e.getMessageDate() + " " + e.getMessageBody() + " " + e.getDbMessageID() + " " + e.getFileName());
            }
        }
        return pList;
    }

    public MessageEntity getMessageByID(String ID) {
        Long messageID = Long.parseLong(ID);
        Log.d("getMessageByID", Long.toString(messageID));
        List<MessageEntity> pList = MessageDao.queryBuilder().where(MessageEntityDao.Properties.MessageID.eq(messageID)).list();
        Log.i("getMessageByID: SIZE", Integer.toString(pList.size()));
        if (pList.size() > 0) {
            messageDetails = pList.get(0);
            return pList.get(0);
        }
        else
        return null;
    }

    public void insertMessage(MessageEntity message) {
        if (message.getMessageID() != null)
            Log.d("INSERT MESSAGE", Long.toString(message.getMessageID()) + " " + message.getDbMessageID() + " " + message.getMessageBody());
        MessageDao.insertOrReplace(message);
        messageDetails = message;
        if (message.getMessageID() != null)
            Log.d("INSERT MESSAGE", Long.toString(message.getMessageID()) + " " + message.getDbMessageID() + " " + message.getMessageBody());
    }

    public void deleteAllMessages() {
        MessageDao.deleteAll();
    }

    public void addMessage(String message, int type, String fileName) {
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        MessageEntity n1 = new MessageEntity();
        n1.setMessageID(null);
        n1.setMessageDate(today);
        n1.setMessageBody(message);
        n1.setMessageType(type);
        n1.setFileName(fileName);
        insertMessage(n1);
    }

    public void MessageSuccess(MessageEntity message, int successFlag) {
        Log.d("MESSAGE FAIL", Long.toString(message.getMessageID()) + " " + message.getDbMessageID() + " " + message.getMessageBody());
        message.setDbMessageID(successFlag);
        insertMessage(message);
        Log.d("MESSAGE FAIL", Long.toString(message.getMessageID()) + " " + message.getDbMessageID() + " " + message.getMessageBody());
    }

    @Override
    public int compareTo(Message message) {
        return messageDetails.getMessageDate().compareTo(messageDetails.getMessageDate());
    }
}

