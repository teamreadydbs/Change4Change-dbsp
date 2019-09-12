package com.example.change4change;

public class TransactionEntry {

    private String lbID;
    private String lbTranstype;
    private String lbTransAmt;
    private String lbDateInfo;


    public TransactionEntry (String lbID,String lbTranstype,String lbTransAmt,String lbDateInfo) {
        this.lbID = lbID;
        this.lbTranstype = lbTranstype;
        this.lbTransAmt = lbTransAmt;
        this.lbDateInfo = lbDateInfo;
    }

    public String getLbID() {
        return lbID;
    }
    public String getLbTranstype() {
        return lbTranstype;
    }
    public String getLbTransAmt() {
        return lbTransAmt;
    }
    public String getLbDateInfo() {
        return lbDateInfo;
    }



}
