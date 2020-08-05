package com.spring.reactive.loan.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document
public class LoanEntity {
    @Id
    private String loanid;
    private String userid;



    private String loneType;
    private Double loneAmount;
    private LocalDate loneDate;
    private Double rateOfInterest;
    private Double durationOfLone;


    public LoanEntity(String userid, String loneType, Double loneAmount, LocalDate loneDate, Double rateOfInterest, Double durationOfLone) {
        this.loanid = loanid;
        this.userid = userid;
        this.loneType = loneType;
        this.loneAmount = loneAmount;
        this.loneDate = loneDate;
        this.rateOfInterest = rateOfInterest;
        this.durationOfLone = durationOfLone;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getLoneType() {
        return loneType;
    }

    public void setLoneType(String loneType) {
        this.loneType = loneType;
    }

    public Double getLoneAmount() {
        return loneAmount;
    }

    public void setLoneAmount(Double loneAmount) {
        this.loneAmount = loneAmount;
    }

    public LocalDate getLoneDate() {
        return loneDate;
    }

    public void setLoneDate(LocalDate loneDate) {
        this.loneDate = loneDate;
    }

    public Double getRateOfInterest() {
        return rateOfInterest;
    }

    public void setRateOfInterest(Double rateOfInterest) {
        this.rateOfInterest = rateOfInterest;
    }

    public Double getDurationOfLone() {
        return durationOfLone;
    }

    public void setDurationOfLone(Double durationOfLone) {
        this.durationOfLone = durationOfLone;
    }

    public String getLoanid() {return loanid; }

    public void setLoanid(String loanid) { this.loanid = loanid; }
}
