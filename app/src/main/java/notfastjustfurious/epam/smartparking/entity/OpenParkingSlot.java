package notfastjustfurious.epam.smartparking.entity;

public class OpenParkingSlot {
    private String slotNumber;
    private String fillTime;

    public OpenParkingSlot(String slotNumber, String fillTime){
        this.slotNumber = slotNumber;
        this.fillTime = fillTime;
    }

    public String getSlotNumber() {
        return slotNumber;
    }

    public String getFillTime() {
        return fillTime;
    }


}
