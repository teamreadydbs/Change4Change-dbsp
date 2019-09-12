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

@DynamoDBTable(tableName = "changechange-mobilehub-2075487980-Merchant")

public class MerchantDO {
    private String _userId;
    private Double _merchantBalance;
    private String _merchantName;

    @DynamoDBHashKey(attributeName = "userId")
    @DynamoDBAttribute(attributeName = "userId")
    public String getUserId() {
        return _userId;
    }

    public void setUserId(final String _userId) {
        this._userId = _userId;
    }
    @DynamoDBAttribute(attributeName = "merchantBalance")
    public Double getMerchantBalance() {
        return _merchantBalance;
    }

    public void setMerchantBalance(final Double _merchantBalance) {
        this._merchantBalance = _merchantBalance;
    }
    @DynamoDBAttribute(attributeName = "merchantName")
    public String getMerchantName() {
        return _merchantName;
    }

    public void setMerchantName(final String _merchantName) {
        this._merchantName = _merchantName;
    }

}
