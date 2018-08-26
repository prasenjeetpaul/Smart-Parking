package notfastjustfurious.epam.smartparking.entity;

public class DedicatedUserAttribute {
    private String loginTime;
    private String logoutTime;
    private String slotID;
    private String failedInstanceNumber;
    private String noOfStars;
    private String isDelay;


    private String isFloat;

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(String logoutTime) {
        this.logoutTime = logoutTime;
    }

    public String getSlotID() {
        return slotID;
    }

    public void setSlotID(String slotID) {
        this.slotID = slotID;
    }

    public String getFailedInstanceNumber() {
        return failedInstanceNumber;
    }

    public void setFailedInstanceNumber(String failedInstanceNumber) {
        this.failedInstanceNumber = failedInstanceNumber;
    }

    public String getNoOfStars() {
        return noOfStars;
    }

    public void setNoOfStars(String noOfStars) {
        this.noOfStars = noOfStars;
    }

    public String isDelay() {
        return isDelay;
    }

    public void setDelay(String delay) {
        isDelay = delay;
    }

    public String getIsFloat() {
        return isFloat;
    }

    public void setIsFloat(String isFloat) {
        this.isFloat = isFloat;
    }

    public String toString() {
        return "SlotID: " + slotID
                + ", No. of Stars: " + noOfStars
                + ", Login Time: " + loginTime
                + ", Logout Time: " + logoutTime
                + ", Failed Instance: " + failedInstanceNumber
                + ", isDelay: " + isDelay
                + ", isFloat: " + isFloat;
    }
}
