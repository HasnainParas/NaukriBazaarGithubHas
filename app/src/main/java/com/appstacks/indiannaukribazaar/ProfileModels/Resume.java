package com.appstacks.indiannaukribazaar.ProfileModels;

public class Resume {


    private String downloadUrl;
    private String pdfTitle;

    public Resume() {
    }

    public Resume(String downloadUrl, String pdfTitle) {
        this.downloadUrl = downloadUrl;
        this.pdfTitle = pdfTitle;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getPdfTitle() {
        return pdfTitle;
    }

    public void setPdfTitle(String pdfTitle) {
        this.pdfTitle = pdfTitle;
    }
}
