package com.example.michael.aysms.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Michael on 01/08/2018.
 */

@Entity
public class BodyEntity {
    @Id
    private Date dateTime;
    private int bodyWidth;
    private int bodyHeight;
    private int bitmapWidth;
    private int bitmapHeight;
    private int nButtons;
    private int button1X;
    private int button1Y;
    private boolean button1NewPain;
    private int button1Severity;
    private int button1BotherLevel;
    private int button2X;
    private int button2Y;
    private boolean button2NewPain;
    private int button2Severity;
    private int button2BotherLevel;
    private int button3X;
    private int button3Y;
    private boolean button3NewPain;
    private int button3Severity;
    private int button3BotherLevel;
    private int button4X;
    private int button4Y;
    private boolean button4NewPain;
    private int button4Severity;
    private int button4BotherLevel;
    @Generated(hash = 1249404235)
    public BodyEntity(Date dateTime, int bodyWidth, int bodyHeight, int bitmapWidth,
            int bitmapHeight, int nButtons, int button1X, int button1Y,
            boolean button1NewPain, int button1Severity, int button1BotherLevel,
            int button2X, int button2Y, boolean button2NewPain, int button2Severity,
            int button2BotherLevel, int button3X, int button3Y,
            boolean button3NewPain, int button3Severity, int button3BotherLevel,
            int button4X, int button4Y, boolean button4NewPain, int button4Severity,
            int button4BotherLevel) {
        this.dateTime = dateTime;
        this.bodyWidth = bodyWidth;
        this.bodyHeight = bodyHeight;
        this.bitmapWidth = bitmapWidth;
        this.bitmapHeight = bitmapHeight;
        this.nButtons = nButtons;
        this.button1X = button1X;
        this.button1Y = button1Y;
        this.button1NewPain = button1NewPain;
        this.button1Severity = button1Severity;
        this.button1BotherLevel = button1BotherLevel;
        this.button2X = button2X;
        this.button2Y = button2Y;
        this.button2NewPain = button2NewPain;
        this.button2Severity = button2Severity;
        this.button2BotherLevel = button2BotherLevel;
        this.button3X = button3X;
        this.button3Y = button3Y;
        this.button3NewPain = button3NewPain;
        this.button3Severity = button3Severity;
        this.button3BotherLevel = button3BotherLevel;
        this.button4X = button4X;
        this.button4Y = button4Y;
        this.button4NewPain = button4NewPain;
        this.button4Severity = button4Severity;
        this.button4BotherLevel = button4BotherLevel;
    }
    @Generated(hash = 895523924)
    public BodyEntity() {
    }
    public Date getDateTime() {
        return this.dateTime;
    }
    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
    public int getBodyWidth() {
        return this.bodyWidth;
    }
    public void setBodyWidth(int bodyWidth) {
        this.bodyWidth = bodyWidth;
    }
    public int getBodyHeight() {
        return this.bodyHeight;
    }
    public void setBodyHeight(int bodyHeight) {
        this.bodyHeight = bodyHeight;
    }
    public int getBitmapWidth() {
        return this.bitmapWidth;
    }
    public void setBitmapWidth(int bitmapWidth) {
        this.bitmapWidth = bitmapWidth;
    }
    public int getBitmapHeight() {
        return this.bitmapHeight;
    }
    public void setBitmapHeight(int bitmapHeight) {
        this.bitmapHeight = bitmapHeight;
    }
    public int getNButtons() {
        return this.nButtons;
    }
    public void setNButtons(int nButtons) {
        this.nButtons = nButtons;
    }
    public int getButton1X() {
        return this.button1X;
    }
    public void setButton1X(int button1X) {
        this.button1X = button1X;
    }
    public int getButton1Y() {
        return this.button1Y;
    }
    public void setButton1Y(int button1Y) {
        this.button1Y = button1Y;
    }
    public boolean getButton1NewPain() {
        return this.button1NewPain;
    }
    public void setButton1NewPain(boolean button1NewPain) {
        this.button1NewPain = button1NewPain;
    }
    public int getButton1Severity() {
        return this.button1Severity;
    }
    public void setButton1Severity(int button1Severity) {
        this.button1Severity = button1Severity;
    }
    public int getButton1BotherLevel() {
        return this.button1BotherLevel;
    }
    public void setButton1BotherLevel(int button1BotherLevel) {
        this.button1BotherLevel = button1BotherLevel;
    }
    public int getButton2X() {
        return this.button2X;
    }
    public void setButton2X(int button2X) {
        this.button2X = button2X;
    }
    public int getButton2Y() {
        return this.button2Y;
    }
    public void setButton2Y(int button2Y) {
        this.button2Y = button2Y;
    }
    public boolean getButton2NewPain() {
        return this.button2NewPain;
    }
    public void setButton2NewPain(boolean button2NewPain) {
        this.button2NewPain = button2NewPain;
    }
    public int getButton2Severity() {
        return this.button2Severity;
    }
    public void setButton2Severity(int button2Severity) {
        this.button2Severity = button2Severity;
    }
    public int getButton2BotherLevel() {
        return this.button2BotherLevel;
    }
    public void setButton2BotherLevel(int button2BotherLevel) {
        this.button2BotherLevel = button2BotherLevel;
    }
    public int getButton3X() {
        return this.button3X;
    }
    public void setButton3X(int button3X) {
        this.button3X = button3X;
    }
    public int getButton3Y() {
        return this.button3Y;
    }
    public void setButton3Y(int button3Y) {
        this.button3Y = button3Y;
    }
    public boolean getButton3NewPain() {
        return this.button3NewPain;
    }
    public void setButton3NewPain(boolean button3NewPain) {
        this.button3NewPain = button3NewPain;
    }
    public int getButton3Severity() {
        return this.button3Severity;
    }
    public void setButton3Severity(int button3Severity) {
        this.button3Severity = button3Severity;
    }
    public int getButton3BotherLevel() {
        return this.button3BotherLevel;
    }
    public void setButton3BotherLevel(int button3BotherLevel) {
        this.button3BotherLevel = button3BotherLevel;
    }
    public int getButton4X() {
        return this.button4X;
    }
    public void setButton4X(int button4X) {
        this.button4X = button4X;
    }
    public int getButton4Y() {
        return this.button4Y;
    }
    public void setButton4Y(int button4Y) {
        this.button4Y = button4Y;
    }
    public boolean getButton4NewPain() {
        return this.button4NewPain;
    }
    public void setButton4NewPain(boolean button4NewPain) {
        this.button4NewPain = button4NewPain;
    }
    public int getButton4Severity() {
        return this.button4Severity;
    }
    public void setButton4Severity(int button4Severity) {
        this.button4Severity = button4Severity;
    }
    public int getButton4BotherLevel() {
        return this.button4BotherLevel;
    }
    public void setButton4BotherLevel(int button4BotherLevel) {
        this.button4BotherLevel = button4BotherLevel;
    }
}
