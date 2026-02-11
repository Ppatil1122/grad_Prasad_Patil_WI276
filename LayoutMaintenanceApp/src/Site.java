public class Site {
    private int siteId;
    private int siteNumber;
    private String siteType;
    private int lengthFt;
    private int widthFt;
    private int areaSqft;
    private String occupancyStatus;
    private Integer ownerId;

    // Constructor
    public Site(int siteNumber, String siteType, int lengthFt, int widthFt, String occupancyStatus, Integer ownerId) {
        this.siteNumber = siteNumber;
        this.siteType = siteType;
        this.lengthFt = lengthFt;
        this.widthFt = widthFt;
        this.areaSqft = lengthFt * widthFt;
        this.occupancyStatus = occupancyStatus;
        this.ownerId = ownerId;
    }

    // Calculate maintenance
    public int calculateMaintenance() {
        int rate = occupancyStatus.equals("OPEN") ? 6 : 9;
        return areaSqft * rate;
    }

    // Getters
    public int getSiteId() { return siteId; }
    public int getSiteNumber() { return siteNumber; }
    public String getSiteType() { return siteType; }
    public int getLengthFt() { return lengthFt; }
    public int getWidthFt() { return widthFt; }
    public int getAreaSqft() { return areaSqft; }
    public String getOccupancyStatus() { return occupancyStatus; }
    public Integer getOwnerId() { return ownerId; }

    // Setters
    public void setSiteId(int siteId) { this.siteId = siteId; }
}