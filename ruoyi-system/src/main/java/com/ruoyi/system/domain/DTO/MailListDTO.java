package com.ruoyi.system.domain.DTO;

/**
 * @author Yancheng Liu
 * @create 2021-03-11 10:35
 */
public class MailListDTO {

    private Integer ID;

    private String MailFrom;

    private String MailFromName;

    private String MailTo;

    private String MailCC;

    private String MailSubject;

    private String MailBody;

    private boolean isBodyHtml;

    private boolean isPriorityHigh;

    private String MailAttachFile1;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getMailFrom() {
        return MailFrom;
    }

    public void setMailFrom(String mailFrom) {
        MailFrom = mailFrom;
    }

    public String getMailFromName() {
        return MailFromName;
    }

    public void setMailFromName(String mailFromName) {
        MailFromName = mailFromName;
    }

    public String getMailTo() {
        return MailTo;
    }

    public void setMailTo(String mailTo) {
        MailTo = mailTo;
    }

    public String getMailCC() {
        return MailCC;
    }

    public void setMailCC(String mailCC) {
        MailCC = mailCC;
    }

    public String getMailSubject() {
        return MailSubject;
    }

    public void setMailSubject(String mailSubject) {
        MailSubject = mailSubject;
    }

    public String getMailBody() {
        return MailBody;
    }

    public void setMailBody(String mailBody) {
        MailBody = mailBody;
    }

    public boolean isBodyHtml() {
        return isBodyHtml;
    }

    public void setBodyHtml(boolean bodyHtml) {
        isBodyHtml = bodyHtml;
    }

    public boolean isPriorityHigh() {
        return isPriorityHigh;
    }

    public void setPriorityHigh(boolean priorityHigh) {
        isPriorityHigh = priorityHigh;
    }

    public String getMailAttachFile1() {
        return MailAttachFile1;
    }

    public void setMailAttachFile1(String mailAttachFile1) {
        MailAttachFile1 = mailAttachFile1;
    }
}
