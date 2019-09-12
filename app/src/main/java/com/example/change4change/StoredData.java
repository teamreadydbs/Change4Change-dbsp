package com.example.change4change;

import java.util.List;

public class StoredData {


    // UserDO
    public static String username ="";
    public static String password = "";
    public static Double balance = 0.0 ;
    public static int bankAccount = 0;
    public static String charity = "";
    public static String merchant ="";


    public static List<String> lastTransaction;
    public static String name = "";
    public static boolean doNotShowAgain = false;
    public static boolean autoDonation  = false;
    public static boolean setDefaultCharityDNSA  = false;

    //MerchantDO
    public static String merchantUserId = "";
    public static String merchantName = "";
    public static Double merchantBalance = 0.0;

    //CharityDO
    public static String charityUserId = "";
    public static Double charityBalance = 0.0 ;
    public static String charityDescription;

    //TransactionDO
    public static String transactionUserId  = "";
    public static Double transAmt = 0.0;
    public static Double transAmtCharity = 0.0;
    public static String transCharity  = "";
    public static String transDate ="";
    public static String transMerchant = "";
    public static String transUser = "";

    public static List<TransactionsDO> transactionsDOS;

    public static List<TransactionsDO> getTransactionsDOS() {
        return transactionsDOS;
    }

    public static void setTransactionsDOS(List<TransactionsDO> transactionsDOS) {
        StoredData.transactionsDOS = transactionsDOS;
    }

    public static String getUsername(){
        return StoredData.username;
    }

    public void setUsername(String s){
        StoredData.username = s;
    }
    public static String getPassword(){
        return StoredData.password;
    }
    public void setPassword(String s){
        StoredData.password = s;
    }
    public static Double getBalance(){
        return StoredData.balance;
    }

    public void setBalance(Double s) {
        StoredData.balance = s;
    }

    public static int getBankAccount(){
        return StoredData.bankAccount;
    }

    public void setBankAccount(int s) {
        StoredData.bankAccount = s;
    }

    public static String getCharity(){
        return StoredData.charity;
    }

    public void setCharity(String s){
        StoredData.charity = s;
    }
    public static String getMerchant(){
        return StoredData.merchant;
    }

    public void setMerchant(String s){
        StoredData.merchant = s;
    }
    public static Double getCharityBalance(){
        return StoredData.charityBalance;
    }

    public void setCharityBalance(Double s) {
        StoredData.charityBalance = s;
    }
    public static Double getMerchantBalance(){
        return StoredData.merchantBalance;
    }

    public void setMerchantBalance(Double s) {
        StoredData.merchantBalance = s;
    }

    public static List<String> getLastTransaction(){
        return StoredData.lastTransaction;
    }

    public void setLastTransaction(List<String> s) {
        StoredData.lastTransaction = s;
    }

    public void setName(String s){
        StoredData.name = s;
    }
    public static String getName(){
        return StoredData.name;
    }

    public static boolean getDoNotShowAgain(){
        return StoredData.doNotShowAgain;
    }

    public void setDoNotShowAgain(boolean s){
        StoredData.doNotShowAgain = s;
    }

    public static boolean getAutoDonation() {
        return autoDonation;
    }

    public static void setAutoDonation(boolean autoDonation) {
        StoredData.autoDonation = autoDonation;
    }

    public static boolean getSetDefaultCharityDNSA() {
        return setDefaultCharityDNSA;
    }

    public static void setSetDefaultCharityDNSA(boolean setDefaultCharityDNSA) {
        StoredData.setDefaultCharityDNSA = setDefaultCharityDNSA;
    }

    public static String getMerchantUserId() {
        return merchantUserId;
    }

    public static void setMerchantUserId(String merchantUserId) {
        StoredData.merchantUserId = merchantUserId;
    }

    public static String getMerchantName() {
        return merchantName;
    }

    public static void setMerchantName(String merchantName) {
        StoredData.merchantName = merchantName;
    }

    public static String getCharityUserId() {
        return charityUserId;
    }

    public static void setCharityUserId(String charityUserId) {
        StoredData.charityUserId = charityUserId;
    }

    public static String getCharityDescription() {
        return charityDescription;
    }

    public static void setCharityDescription(String charityDescription) {
        StoredData.charityDescription = charityDescription;
    }

    public static String getTransactionUserId() {
        return transactionUserId;
    }

    public static void setTransactionUserId(String transactionUserId) {
        StoredData.transactionUserId = transactionUserId;
    }

    public static Double getTransAmt() {
        return transAmt;
    }

    public static void setTransAmt(Double transAmt) {
        StoredData.transAmt = transAmt;
    }

    public static Double getTransAmtCharity() {
        return transAmtCharity;
    }

    public static void setTransAmtCharity(Double transAmtCharity) {
        StoredData.transAmtCharity = transAmtCharity;
    }

    public static String getTransCharity() {
        return transCharity;
    }

    public static void setTransCharity(String transCharity) {
        StoredData.transCharity = transCharity;
    }

    public static String getTransDate() {
        return transDate;
    }

    public static void setTransDate(String transDate) {
        StoredData.transDate = transDate;
    }

    public static String getTransMerchant() {
        return transMerchant;
    }

    public static void setTransMerchant(String transMerchant) {
        StoredData.transMerchant = transMerchant;
    }

    public static String getTransUser() {
        return transUser;
    }

    public static void setTransUser(String transUser) {
        StoredData.transUser = transUser;
    }
}
