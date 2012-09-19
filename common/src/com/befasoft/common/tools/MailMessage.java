package com.befasoft.common.tools;

public class MailMessage {
    String to, replayTo, replayToID, cc, bcc;
    String subject, text;
    String[] fileAttachments;
    MailMessageImages[] imageAttachments;

    public MailMessage(String to, String subject, String text) {
        this.to = to;
        this.subject = subject;
        this.text = text;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getReplayTo() {
        return replayTo;
    }

    public void setReplayTo(String replayTo) {
        this.replayTo = replayTo;
    }

    public String getReplayToID() {
        return replayToID;
    }

    public void setReplayToID(String replayToID) {
        this.replayToID = replayToID;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String[] getFileAttachments() {
        return fileAttachments;
    }

    public void setFileAttachments(String[] fileAttachments) {
        this.fileAttachments = fileAttachments;
    }

    public MailMessageImages[] getImageAttachments() {
        return imageAttachments;
    }

    public void setImageAttachments(MailMessageImages[] imageAttachments) {
        this.imageAttachments = imageAttachments;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }
}
