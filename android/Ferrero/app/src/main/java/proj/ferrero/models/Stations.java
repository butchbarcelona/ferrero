package proj.ferrero.models;

/**
 * Created by tonnyquintos on 1/16/16.
 */
public enum Stations {

    A("Station A", 1),
    B("Station B", 2),
    C("Station C", 3);

    String station;
    int index;
    Stations(String station, int index){
        this.station = station;
        this.index = index;
    }

    public String getStation(){
        return station;
    }

    public int getIndex() {
        return index;
    }
}
