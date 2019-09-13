Author: [Leander](leander.ymg@gmail.com),[Hong Jun](hong.jun.teoh@accenture.com), [Halley Vincent](yeohalleyvincent@gmail.com), [Phyllis](phyllis.thong.en@gmail.com)

# Change 4 Change

- ## Tools Required
  - [Android Studio](https://developer.android.com/studio)
  - Android Phone `minSdkVersion 26`
  - Android Gradle `3.3.2'`
  - Maven Gradle Plugin `2.1`
  - Amazon Web Service Account
  
  
- ## Backend   
  - AWS Mobile Hub 
    - Create an android project 
    - Download Cloud Config
    - Create raw directory in apps res folder
    - Add awsconfiguration.json to app
    - nable NoQLDatabase(DynamoDB) at Mobile Hub
    - Create relevant tables needed with relevant datatypes
    - Add AWS Mobile SDK 
    - Allow internet & access network state in AndroidManifest
    - Add AWS dependencies to app/gradle.
    - Under Identity and Access Management(IAM), add your mobile hub to allow DynamoDB full access for permission policies. 
    - More details on Mobile Hub set up Guide [Link](https://docs.aws.amazon.com/aws-mobile/latest/developerguide/mobile-hub-getting-started.html#mobile-hub-add-aws-mobile-sdk-connect-to-your-backend)

  - AWS Dynamo DB
    - This is where the data is stored and can be edited from here directly. 

- ## Demo Data: 
  - Charity table
    ```
     Charity table
     CharityID
     Charity Balance
     Charity Description 
    ```
   - Merchant Table
     ```
     MerchantID
     Merchant Balance
     Merchant Name
     ```
   - Transactions
     ```
     TransactionID(timestamp)
     Transaction Amount to Merchant
     Transaction Amount to Charity
     Transaction Date
     Transaction MerchantID
     Transaction UserID
     Transaction CharityID
     ```
   - Users
     ```
     UserID
     UserPassword
     Balance
     Bank Account
     Charity
     lastTransaction(List of all transaction tied to user)
     AutoDonation(Boolean)
     DoNotShowAgain(Boolean)
     name
     setDefaultCharity(Boolean)
     ```

- ## Libraries:
  - [ZXing Scanner](https://github.com/zxing/zxing) 
  - [Material Components](https://material.io/)

  
- ## Libraries License
  - Zxing `Apache license 2.0`
  - Material Components `Apache license 2.0`
  - Android Studio `Apache License 2.0`


