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

@DynamoDBTable(tableName = "changechange-mobilehub-2075487980-Charities")

public class CharitiesDO {
    private String _userId;
    private Double _charityBalance;
    private String _charityDescription;

    @DynamoDBHashKey(attributeName = "userId")
    @DynamoDBAttribute(attributeName = "userId")
    public String getUserId() {
        return _userId;
    }

    public void setUserId(final String _userId) {
        this._userId = _userId;
    }
    @DynamoDBAttribute(attributeName = "charityBalance")
    public Double getCharityBalance() {
        return _charityBalance;
    }

    public void setCharityBalance(final Double _charityBalance) {
        this._charityBalance = _charityBalance;
    }
    @DynamoDBAttribute(attributeName = "charityDescription")
    public String getCharityDescription() {
        return _charityDescription;
    }

    public void setCharityDescription(final String _charityDescription) {
        this._charityDescription = _charityDescription;
    }

}
