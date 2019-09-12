package com.example.change4change;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;
import java.util.Map;
import java.util.Set;

@DynamoDBTable(tableName = "changechange-mobilehub-2075487980-transactions")

public class TransactionsDO {
    private String _userId;
    private Double _transAmt;
    private Double _transAmtCharity;
    private String _transCharity;
    private String _transDate;
    private String _transMerchant;
    private String _transUser;

    @DynamoDBHashKey(attributeName = "userId")
    @DynamoDBAttribute(attributeName = "userId")
    public String getUserId() {
        return _userId;
    }

    public void setUserId(final String _userId) {
        this._userId = _userId;
    }
    @DynamoDBAttribute(attributeName = "transAmt")
    public Double getTransAmt() {
        return _transAmt;
    }

    public void setTransAmt(final Double _transAmt) {
        this._transAmt = _transAmt;
    }
    @DynamoDBAttribute(attributeName = "transAmtCharity")
    public Double getTransAmtCharity() {
        return _transAmtCharity;
    }

    public void setTransAmtCharity(final Double _transAmtCharity) {
        this._transAmtCharity = _transAmtCharity;
    }
    @DynamoDBAttribute(attributeName = "transCharity")
    public String getTransCharity() {
        return _transCharity;
    }

    public void setTransCharity(final String _transCharity) {
        this._transCharity = _transCharity;
    }
    @DynamoDBAttribute(attributeName = "transDate")
    public String getTransDate() {
        return _transDate;
    }

    public void setTransDate(final String _transDate) {
        this._transDate = _transDate;
    }
    @DynamoDBAttribute(attributeName = "transMerchant")
    public String getTransMerchant() {
        return _transMerchant;
    }

    public void setTransMerchant(final String _transMerchant) {
        this._transMerchant = _transMerchant;
    }
    @DynamoDBAttribute(attributeName = "transUser")
    public String getTransUser() {
        return _transUser;
    }

    public void setTransUser(final String _transUser) {
        this._transUser = _transUser;
    }

}
