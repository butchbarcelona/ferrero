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

    public String getString(){
        return station;
    }

    public int getIndex() {
        return index;
    }

    public static Stations getStation(String s){

        switch(s){
            case "A":case "Station A":case "a":
                return Stations.A;
            case "B":case "Station B":case "b":
                return Stations.B;
            case "C":case "Station C":case "c":
                return Stations.C;

        }

        return null;
    }
}
