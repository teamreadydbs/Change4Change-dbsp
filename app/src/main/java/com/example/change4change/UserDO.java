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

@DynamoDBTable(tableName = "changechange-mobilehub-2075487980-User")

public class UserDO {
    private String _userId;
    private String _password;
    private Boolean _autoDonation;
    private Double _balance;
    private int _bankAccount;
    private String _charity;
    private Boolean _doNotShowAgain;
    private List<String> _lastTransaction;
    private String _name;
    private Boolean _setDefaultCharityDNSA;

    @DynamoDBHashKey(attributeName = "userId")
    @DynamoDBIndexHashKey(attributeName = "userId", globalSecondaryIndexName = "getUsername")
    public String getUserId() {
        return _userId;
    }

    public void setUserId(final String _userId) {
        this._userId = _userId;
    }
    @DynamoDBRangeKey(attributeName = "password")
    @DynamoDBIndexRangeKey(attributeName = "password", globalSecondaryIndexName = "getUsername")
    public String getPassword() {
        return _password;
    }

    public void setPassword(final String _password) {
        this._password = _password;
    }
    @DynamoDBAttribute(attributeName = "autoDonation")
    public Boolean getAutoDonation() {
        return _autoDonation;
    }

    public void setAutoDonation(final Boolean _autoDonation) {
        this._autoDonation = _autoDonation;
    }
    @DynamoDBAttribute(attributeName = "balance")
    public Double getBalance() {
        return _balance;
    }

    public void setBalance(final Double _balance) {
        this._balance = _balance;
    }
    @DynamoDBAttribute(attributeName = "bankAccount")
    public int getBankAccount() {
        return _bankAccount;
    }

    public void setBankAccount(final int _bankAccount) {
        this._bankAccount = _bankAccount;
    }
    @DynamoDBAttribute(attributeName = "charity")
    public String getCharity() {
        return _charity;
    }

    public void setCharity(final String _charity) {
        this._charity = _charity;
    }
    @DynamoDBAttribute(attributeName = "doNotShowAgain")
    public Boolean getDoNotShowAgain() {
        return _doNotShowAgain;
    }

    public void setDoNotShowAgain(final Boolean _doNotShowAgain) {
        this._doNotShowAgain = _doNotShowAgain;
    }
    @DynamoDBAttribute(attributeName = "lastTransaction")
    public List<String> getLastTransaction() {
        return _lastTransaction;
    }

    public void setLastTransaction(final List<String> _lastTransaction) {
        this._lastTransaction = _lastTransaction;
    }
    @DynamoDBAttribute(attributeName = "name")
    public String getName() {
        return _name;
    }

    public void setName(final String _name) {
        this._name = _name;
    }
    @DynamoDBAttribute(attributeName = "setDefaultCharityDNSA")
    public Boolean getSetDefaultCharityDNSA() {
        return _setDefaultCharityDNSA;
    }

    public void setSetDefaultCharityDNSA(final Boolean _setDefaultCharityDNSA) {
        this._setDefaultCharityDNSA = _setDefaultCharityDNSA;
    }

}
