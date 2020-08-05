package game;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class PlayerStats extends Main {

    protected int hunger = 0;
    private int thirst = 99;
    private double fuel = 98;
    private int restroom = 97;
    private int fatigue = 96;
    private int carSpeed = 95;
    Date currentDate = new SimpleDateFormat("MMMMM dd, yyyy").parse("July 1, 2020");
    private int distanceTraveled;
    private int distanceToNextLandmark = 44;
    private String defaultCarID = "car";
    private String defaultCarSpriteURL = "carSpriteDefault.png";
    private String vehicleID = defaultCarID; // temporary, to change with more vehicle additions
    private String carSpriteURL = defaultCarSpriteURL; // temporary, to change with more vehicle additions
    private volatile Boolean isMoving = false;
    private boolean setupComplete = false;
    //private String nextLandMark;

//    Map<String, Integer> landmarksDistancesSize = new HashMap<>() {{
//        put("St. John's", 0, "large");
//        put("Paradise", 7, "large");
//        put("CBS", 11, "large");
//        put("Holyrood", 18, "small");
//        put("Brigus Junction", 18, "small");
//        put("Bellevue", 60, "small");
//        put("Goobies", 54, "small");
//        put("Clarenville", 30, "large");
//        put("Glovertown", 87, "small");
//        put("Gambo", 30, "small");
//        put("Gander", 40, "large");
//        put("Glenwood", 23, "small");
//        put("Bishop's Falls", 57, "small");
//        put("Grand Falls - Windsor", 17, "large");
//        put("Badger", 30, "none");
//        put("South Brook", 53, "small");
//        put("Sheppardville", 33, "small");
//        put("Deer Lake", 92, "large");
//        put("Pasadena", 23, "small");
//        put("Corner Brook", 78, "large");
//        //etc
//    }};

    String[][] landmarksDistancesSize = {
        {"St. John's", "0", "large"},
        {"Paradise", "7", "large"},
        {"CBS", "11", "large"},
        {"Holyrood", "18", "small"},
        {"Brigus Junction", "18", "small"},
        {"Bellevue", "60", "small"},
        {"Goobies", "54", "small"},
        {"Clarenville", "30", "large"},
        {"Glovertown", "87", "small"},
        {"Gambo", "30", "small"},
        {"Gander", "40", "large"},
        {"Glenwood", "23", "small"},
        {"Bishop's Falls", "57", "small"},
        {"Grand Falls - Windsor", "17", "large"},
        {"Badger", "30", "none"},
        {"South Brook", "53", "small"},
        {"Sheppardville", "33", "small"},
        {"Deer Lake", "92", "large"},
        {"Pasadena", "23", "small"},
        {"Corner Brook", "78", "large"}
//        {"", "", ""},
//        {"", "", ""},
//        {"", "", ""},
//        {"", "", ""},
//        {"", "", ""},
//        {"", "", ""},
//        {"", "", ""},
//        {"", "", ""},
//        {"", "", ""},
//        {"", "", ""}

        //etc
    };

    public PlayerStats(
            int hunger,
            int thirst,
            double fuel,
            int restroom,
            int fatigue,
            int carSpeed,
            //Date currentDate,
            int distanceTraveled,
//            int distanceToNextLandmark,
//            String defaultCarID,
//            String defaultCarSpriteURL,
//            String vehicleID,
//            String carSpriteURL,
//            Boolean isMoving,
//            boolean setupComplete, String[][] landmarksDistancesSize
            String nextLandMark
    ) throws ParseException {
        this.hunger = hunger;
        this.thirst = thirst;
        this.fuel = fuel;
        this.restroom = restroom;
        this.fatigue = fatigue;
        this.carSpeed = carSpeed;
        //this.currentDate = currentDate;//
        this.distanceTraveled = distanceTraveled;
//        this.distanceToNextLandmark = distanceToNextLandmark;
//        this.defaultCarID = defaultCarID;
//        this.defaultCarSpriteURL = defaultCarSpriteURL;
//        this.vehicleID = vehicleID;
//        this.carSpriteURL = carSpriteURL;
//        this.isMoving = isMoving;
//        this.setupComplete = setupComplete;
//        this.landmarksDistancesSize = landmarksDistancesSize;
        //this.landmarksDistancesSize[1][0] = nextLandMark;
    }

    void checkPlayerStats() {
        if (getHunger() == 0) {
            if (getHunger() < 0) {
                setHunger(0);
            }
            // break out of game loops // end of game
        }
        if (getThirst() == 0) {
            if (getThirst() < 0) {
                setThirst(0);
            }
            // break out of game loops // end of game
        }if (getFuel() == 0) {
            if (getFuel() < 0) {
                setFuel(0);
            }
            // break out of game loops // end of game
        }if (getRestroom() == 0) {
            if (getRestroom() < 0) {
                setRestroom(0);
            }
            // break out of game loops // end of game
        }if (getFatigue() == 0) {
            if (getFatigue() < 0) {
                setFatigue(0);
            }
            // break out of game loops // end of game
        }
    }





    /**
     * Saves variables of game.PlayerStats to SaveGame.ser
     *
     * @throws IOException
     */
    static void saveData() throws IOException {

        FileOutputStream fileOutputStream = new FileOutputStream("SaveGame.ser");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        //objectOutputStream.writeObject(game.PlayerStats);

        objectOutputStream.close();
        fileOutputStream.close();
    }


    /**
     * Loads data from SaveGame.ser,
     * assigns data corresponding variables in game.PlayerStats.
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    static void loadData() throws IOException, ClassNotFoundException {

        FileInputStream fileInputStream = new FileInputStream("SaveGame.ser");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        //game.PlayerStats = (PlayerStats) objectInputStream.readObject();

        fileInputStream.close();
        objectInputStream.close();
    }


    // Getters and Setters
    public int getCarSpeed() {
        return carSpeed;
    }
    public void setCarSpeed(int CarSpeed) {
        carSpeed = CarSpeed;
    }
    int getDistanceTraveled() {
        return parseInt(landmarksDistancesSize [1][1]);
    }
    void setDistanceTraveled(int DistanceTraveled) {
        distanceTraveled = DistanceTraveled;
    }
    int getDistanceToNextLandmark() {
        return distanceToNextLandmark;
    }
    void setDistanceToNextLandmark(int DistanceToNextLandmark) {
        distanceToNextLandmark = DistanceToNextLandmark;
    }
    Date getCurrentDate() {
        return currentDate;
    }
    void setCurrentDate(Date CurrentDate) {
        currentDate = CurrentDate;
    }
    public int getHunger() {
        return hunger;
    }
    void setHunger(int Hunger) {
        hunger = Hunger;
    }
    int getThirst() {
        return thirst;
    }
    void setThirst(int Thirst) {
        thirst = Thirst;
    }
    double getFuel() {
        return fuel;
    }
    void setFuel(double Fuel) {
        fuel = Fuel;
    }
    int getRestroom() {
        return restroom;
    }
    void setRestroom(int Restroom) {
        restroom = Restroom;
    }
    int getFatigue() {
        return fatigue;
    }
    void setFatigue(int Fatigue) {
        fatigue = Fatigue;
    }
    boolean isMoving() {
        return isMoving;
    }
    void setMoving(boolean IsMoving) {
        isMoving = IsMoving;
    }
    String getCarSpriteURL() {
        return carSpriteURL;
    }
    void setCarSpriteURL(String CarSpriteURL) {
        carSpriteURL = CarSpriteURL;
    }
    String getVehicleID() {
        return vehicleID;
    }
    void setVehicleID(String VehicleID) {
        vehicleID = VehicleID;
    }
    public boolean isFinishedSetup() {
        return setupComplete;
    }
    public void setFinishedSetup(boolean SetupComplete) {
        setupComplete = SetupComplete;
    }

    public String getNextLandMark() {
        return landmarksDistancesSize [1] [0];
    }
//    public void setNextLandMark(String nextLandMark) {
//        this.nextLandMark = nextLandMark;
//    }


}
