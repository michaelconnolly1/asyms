package com.example.michael.aysms.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Michael on 31/07/2018.
 */
@Entity
public class ProblemsOrIssuesEntity {
    @Id(autoincrement = true)
    private Long problemID;

    private Date dateTime;
    private int problemNo;
    private String problem;
    private int severity;
    private int botherLevel;
    @Generated(hash = 1534981828)
    public ProblemsOrIssuesEntity(Long problemID, Date dateTime, int problemNo,
            String problem, int severity, int botherLevel) {
        this.problemID = problemID;
        this.dateTime = dateTime;
        this.problemNo = problemNo;
        this.problem = problem;
        this.severity = severity;
        this.botherLevel = botherLevel;
    }
    @Generated(hash = 407514615)
    public ProblemsOrIssuesEntity() {
    }
    public Long getProblemID() {
        return this.problemID;
    }
    public void setProblemID(Long problemID) {
        this.problemID = problemID;
    }
    public Date getDateTime() {
        return this.dateTime;
    }
    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
    public int getProblemNo() {
        return this.problemNo;
    }
    public void setProblemNo(int problemNo) {
        this.problemNo = problemNo;
    }
    public String getProblem() {
        return this.problem;
    }
    public void setProblem(String problem) {
        this.problem = problem;
    }
    public int getSeverity() {
        return this.severity;
    }
    public void setSeverity(int severity) {
        this.severity = severity;
    }
    public int getBotherLevel() {
        return this.botherLevel;
    }
    public void setBotherLevel(int botherLevel) {
        this.botherLevel = botherLevel;
    }
}
