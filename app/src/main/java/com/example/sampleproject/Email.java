package com.example.sampleproject;

import com.google.gson.annotations.SerializedName;

public class Email {
    private String idtableEmail;
    private String tableEmailEmailAddress;
    private boolean tableEmailValidate;

    public Email(String tableEmailEmailAddress, boolean tableEmailValidate) {
        this.tableEmailEmailAddress = tableEmailEmailAddress;
        this.tableEmailValidate = tableEmailValidate;
    }

    public void setIdtableEmail(String idtableEmail) {
        this.idtableEmail = idtableEmail;
    }

    public String getIdtableEmail() {
        return idtableEmail;
    }

    public String getTableEmailEmailAddress() {
        return tableEmailEmailAddress;
    }

    public boolean isTableEmailValidate() {
        return tableEmailValidate;
    }
}
