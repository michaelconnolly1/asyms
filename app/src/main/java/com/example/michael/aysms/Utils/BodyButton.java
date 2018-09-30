package com.example.michael.aysms.Utils;

/**
 * Created by laptop on 13/08/2018.
 */

public class BodyButton {
    int X;
    int Y;
    boolean newPain;
    int severity;
    int bother;
    int painRG;
    int severityRG;
    int botherRG;

    public BodyButton(int X, int Y, boolean pain, int severity, int bother, int painRG, int severityRG, int botherRG) {
        this.X = X;
        this.Y = Y;
        this. newPain = pain;
        this.severity = severity;
        this.bother = bother;
        this.painRG = painRG;
        this.severityRG = severityRG;
        this.botherRG = botherRG;
    }

    public int getX() { return this.X;}
    public int getY() { return this.Y;}
    public boolean getPain() { return this.newPain;}
    public int getSeverity() { return this.severity;}
    public int getBother() { return this.bother;}
    public int getPainRG() { return this.painRG;}
    public int getSeverityRG() { return this.severityRG;}
    public int getBotherRG() { return this.botherRG;}
    public void setPainRG(int painRGValue) { this.painRG = painRGValue;}
    public void setSeverityRG(int severityRGValue) { this.severityRG = severityRGValue;}
    public void setBotherRG(int botherRGValue) { this.botherRG = botherRGValue;}
}