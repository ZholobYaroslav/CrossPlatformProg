package com.company;

public class Villa extends Flat
{
    private double landArea;
    private boolean hasGarden;

    public Villa(double area, int roomCount, String residentalArea, double pricePeSquareMeter, double infrastructureDistance, double landArea, boolean hasGarden) {
        super(area, roomCount, residentalArea, pricePeSquareMeter, infrastructureDistance);
        this.landArea = landArea;
        this.hasGarden = hasGarden;
    }
    public Villa() {
        super();
        this.landArea = 0.0;
        this.hasGarden = false;
    }

    public double getLandArea() {
        return landArea;
    }
    public void setLandArea(double landArea) {
        this.landArea = landArea;
    }

    public boolean isHasGarden() {
        return hasGarden;
    }
    public void setHasGarden(boolean hasGarden) {
        this.hasGarden = hasGarden;
    }

    @Override
    public String toString() {
        return super.toString() +  ", landArea='" + landArea + '\'' +
                ", hasGarden=" + hasGarden;
    }
    @Override
    public void parse(String line) {
        super.parse(line);
        this.landArea = Double.parseDouble(line.split(" ")[6]);
        this.hasGarden= Boolean.parseBoolean(line.split(" ")[7]);
    }
}
