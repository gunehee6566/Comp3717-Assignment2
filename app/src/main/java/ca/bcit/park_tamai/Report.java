package ca.bcit.park_tamai;

public class Report {
    private String Age_Group;
    private String Classification_Reported;
    private String HA;
    private String Reported_Date;
    private String Sex;

    public String getAge_Group() {
        return Age_Group;
    }

    public void setAge_Group(String age_Group) {
        Age_Group = age_Group;
    }

    public String getClassification_Reported() {
        return Classification_Reported;
    }

    public void setClassification_Reported(String classification_Reported) {
        Classification_Reported = classification_Reported;
    }

    public String getHA() {
        return HA;
    }

    public void setHA(String HA) {
        this.HA = HA;
    }

    public String getReported_Date() {
        return Reported_Date;
    }

    public void setReported_Date(String reported_Date) {
        Reported_Date = reported_Date;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }
}
