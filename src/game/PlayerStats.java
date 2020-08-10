package game;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class PlayerStats extends Main {

    private double rateFactor = 10000;
    private double massStatModifier = 1;

    // Rate values are the base used in further calculations
    // i.e. fuelRate is first calculated (multiplied? divided?) with speed before it impacts the base variable fuel
    private double hunger;
    private double hungerRate = 30*massStatModifier/rateFactor;
    private double thirst;
    private double thirstRate = 50*massStatModifier/rateFactor;
    private double fuel;
    private double fuelRate = massStatModifier/rateFactor;
    private double restroom;
    private double restroomRate = 35*massStatModifier/rateFactor;
    private double fatigue;
    private double fatigueRate = 12*massStatModifier/rateFactor;
    private int cash = 500;

    private double speed;
    LocalDate currentDate = LocalDate.now(); // Create a date object - current date
    //Date currentDate = new SimpleDateFormat("MMMMM dd, yyyy").parse("July 1, 2020");
    //Date currentDate = Calendar.getInstance().getTime();
    private double distanceTraveled;
    private double distanceToNextLandmark;
    private int lastLandmarkIndex = 0;
    //private int nextLandmarkIndex = 1;

    private String defaultCarID = "car";
    private String defaultCarSpriteURL = "carSpriteDefault.png";
    private String vehicleID = defaultCarID; // temporary, to change with more vehicle additions
    private String carSpriteURL = defaultCarSpriteURL; // temporary, to change with more vehicle additions
    //41private volatile Boolean isMoving = false;
    private boolean setupComplete = false;

    /**
     * String array that contains the landmark names, distance from the starting point, and size (amenities available).
     */
    String[][] landmarkAttributes = {
            {"Home", "1", "none"},
            {"St. John's", "2", "large"},
            {"Paradise", "11", "small"},
            {"CBS", "33", "large"},
            {"Holyrood", "47", "small"},
            {"Brigus Junction", "70", "small"},
            {"Bellevue", "124", "small"},
            {"Goobies", "164", "small"},
            {"Clarenville", "191", "large"},
            {"Glovertown", "279", "small"},
            {"Gambo", "292", "small"},
            {"Gander", "336", "large"},
            {"Glenwood", "357", "small"},
            {"Bishop's Falls", "414", "small"},
            {"Grand Falls - Windsor", "430", "large"},
            {"Badger", "458", "none"},
            {"South Brook", "511", "small"},
            {"Sheppardville", "553", "small"},
            {"Deer Lake", "640", "large"},
            {"Pasadena", "663", "small"},
            {"Corner Brook", "692", "large"}
    };



    public PlayerStats(
            double hunger,
            double thirst,
            double fuel,
            double restroom,
            double fatigue,
            int speed,
            //Date currentDate
            double distanceTraveled,
            int cash

//            int distanceToNextLandmark,
//            String defaultCarID,
//            String defaultCarSpriteURL,
//            String vehicleID,
//            String carSpriteURL,
//            Boolean isMoving,
//            boolean setupComplete, String[][] landmarkAttributes
    ) throws ParseException {
        this.hunger = hunger;
        this.thirst = thirst;
        this.fuel = fuel;
        this.restroom = restroom;
        this.fatigue = fatigue;
        this.speed = speed;
        //this.currentDate = currentDate;//
        this.distanceTraveled = distanceTraveled;
        this.cash = cash;
//        this.distanceToNextLandmark = distanceToNextLandmark;
//        this.defaultCarID = defaultCarID;
//        this.defaultCarSpriteURL = defaultCarSpriteURL;
//        this.vehicleID = vehicleID;
//        this.carSpriteURL = carSpriteURL;
//        this.isMoving = isMoving;
//        this.setupComplete = setupComplete;
//        this.landmarkAttributes = landmarkAttributes;
        //this.landmarkAttributes[1][0] = nextLandMark;
    }

    public void clampPlayerStats() {
        if (getFuel() < 0) {
            setFuel(0);
        } else if (getFuel() > 100) {
            setFuel(100);
        }
        if (getHunger() < 0) {
            setHunger(0);
        } else if (getHunger() > 100) {
            setHunger(100);
        }
        if (getThirst() < 0) {
            setThirst(0);
        } else if (getThirst() > 100) {
            setThirst(100);
        }
        if (getRestroom() < 0) {
            setRestroom(0);
        } else if (getRestroom() > 100) {
            setRestroom(100);
        }
        if (getFatigue() < 0) {
            setFatigue(0);
        } else if (getFatigue() > 100) {
            setFatigue(100);
        }
    }



    // Getters and Setters
    public double getSpeed() {
        return speed;
    }
    public void setSpeed(double Speed) {
        speed = Speed;
    }

    double getDistanceTraveled() {
        //return String.valueOf(landmarkAttributes[0][1]);
        //return Double.parseDouble(landmarkAttributes[0][1]);
        return distanceTraveled;
    }
    void setDistanceTraveled(double DistanceTraveled) {
        distanceTraveled = DistanceTraveled;
    }

    double getDistanceToNextLandmark() {
        return distanceToNextLandmark;
    }
    void setDistanceToNextLandmark(int DistanceToNextLandmark) {
        distanceToNextLandmark = DistanceToNextLandmark;
    }

    LocalDate getCurrentDate() {
        return currentDate;
    }
    void setCurrentDate(LocalDate CurrentDate) {
        currentDate = CurrentDate;
    }

    public double getHunger() {
        if (hunger <= 0) {
            hunger = 0;
        }
        return hunger;
    }
    void setHunger(double Hunger) {
        hunger = Hunger;
    }
    public double getHungerRate() {
        return hungerRate;
    }

    double getThirst() {
        if (hunger <= 0) {
            hunger = 0;
        }
        return thirst;
    }
    void setThirst(double Thirst) {
        thirst = Thirst;
    }
    public double getThirstRate() {
        return thirstRate;
    }

    double getFuel() {
        if (fuel <= 0) {
            fuel = 0;
        }
        return fuel;
    }
    void setFuel(double Fuel) {
        fuel = Fuel;
    }
    public double getFuelRate() {
        return fuelRate;
    }

    double getRestroom() {
        if (restroom <= 0) {
            restroom = 0;
        }
        return restroom;
    }
    void setRestroom(double Restroom) {
        restroom = Restroom;
    }
    public double getRestroomRate() {
        return restroomRate;
    }

    double getFatigue() {
        if (fatigue <= 0) {
            fatigue = 0;
        }
        return fatigue;
    }
    void setFatigue(double Fatigue) {
        fatigue = Fatigue;
    }
    public double getFatigueRate() {
        return fatigueRate;
    }

    public String getNextLandmarkName() {
        return landmarkAttributes[lastLandmarkIndex+1][0];
    }

    public String getLastLandmarkName() {
        return landmarkAttributes[lastLandmarkIndex][0];
    }

    public int getLastLandmarkIndex() {
        return lastLandmarkIndex;
    }
    public void setLastLandmarkIndex(int newIndex) {
        lastLandmarkIndex = newIndex;
    }

    public int getCash() {
        return cash;
    }
    public void setCash(int Cash) {
        cash = Cash;
    }

    //boolean isMoving() { return isMoving; }
    //void setMoving(boolean IsMoving) { isMoving = IsMoving; }

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



//    /**
//     * Saves variables of game.PlayerStats to SaveGame.ser
//     *
//     * @throws IOException
//     */
//    static void saveData() throws IOException {
//
//        FileOutputStream fileOutputStream = new FileOutputStream("SaveGame.ser");
//        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
//
//        //objectOutputStream.writeObject(game.PlayerStats);
//
//        objectOutputStream.close();
//        fileOutputStream.close();
//    }
//
//
//    /**
//     * Loads data from SaveGame.ser,
//     * assigns data corresponding variables in game.PlayerStats.
//     *
//     * @throws IOException
//     * @throws ClassNotFoundException
//     */
//    static void loadData() throws IOException, ClassNotFoundException {
//
//        FileInputStream fileInputStream = new FileInputStream("SaveGame.ser");
//        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
//
//        //game.PlayerStats = (PlayerStats) objectInputStream.readObject();
//
//        fileInputStream.close();
//        objectInputStream.close();
//    }
}



