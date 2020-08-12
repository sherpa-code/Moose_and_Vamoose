package game;

import java.text.ParseException;

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
    private double fatigue;
    private double fatigueRate = 12*massStatModifier/rateFactor;
    private int cash = 500;

    private double speed;
    private double distanceTraveled;
    private int lastLandmarkIndex = 0;



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
            double fatigue,
            int speed,
            double distanceTraveled,
            int cash
    ) throws ParseException {
        this.hunger = hunger;
        this.thirst = thirst;
        this.fuel = fuel;
        this.fatigue = fatigue;
        this.speed = speed;
        this.distanceTraveled = distanceTraveled;
        this.cash = cash;
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
        return distanceTraveled;
    }
    void setDistanceTraveled(double DistanceTraveled) {
        distanceTraveled = DistanceTraveled;
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
    public String getNextLandmarkSize() {
        return landmarkAttributes[lastLandmarkIndex+1][2];
    }

    public int getLastLandmarkIndex() {
        return lastLandmarkIndex;
    }
    public void setLastLandmarkIndex(int newIndex) {
        lastLandmarkIndex = newIndex;
    }

    public String getLastLandmarkName() {
        return landmarkAttributes[lastLandmarkIndex][0];
    }

    public int getCash() {
        return cash;
    }
    public void setCash(int Cash) {
        cash = Cash;
    }

}



