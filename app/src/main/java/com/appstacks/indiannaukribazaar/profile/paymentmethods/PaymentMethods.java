package com.appstacks.indiannaukribazaar.profile.paymentmethods;

public class PaymentMethods {

   private String primaryBankName;
   private String accountNumber;
   private String ifscCode;
   private String branchName;
   private String accountType;
   private String upiID;
   private String uniqueId;


    public PaymentMethods() {
    }


    public PaymentMethods(String primaryBankName, String accountNumber, String ifscCode, String branchName, String accountType, String upiID, String uniqueId) {
        this.primaryBankName = primaryBankName;
        this.accountNumber = accountNumber;
        this.ifscCode = ifscCode;
        this.branchName = branchName;
        this.accountType = accountType;
        this.upiID = upiID;
        this.uniqueId = uniqueId;
    }

    public String getPrimaryBankName() {
        return primaryBankName;
    }

    public void setPrimaryBankName(String primaryBankName) {
        this.primaryBankName = primaryBankName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getUpiID() {
        return upiID;
    }

    public void setUpiID(String upiID) {
        this.upiID = upiID;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }
}
