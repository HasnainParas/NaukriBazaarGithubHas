package com.appstacks.indiannaukribazaar.profile.resume;

public class Resume {


    private String downloadUrl;
    private String pdfTitle;
    private String Size;
    private String time;

    public Resume(String downloadUrl, String pdfTitle, String size, String time) {
        this.downloadUrl = downloadUrl;
        this.pdfTitle = pdfTitle;
        Size = size;
        this.time = time;
    }

    public Resume() {
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

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
