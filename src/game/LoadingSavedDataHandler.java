package game;

public class LoadingSavedDataHandler {


//    public static void main(String[]args){
//        String loadedData = "hunger=5.328000000000091|thirst=8.879999999999988|fuel=97.50700000000239|restroom=6.215999999999813|fatigue=2.131200000000048|speed=25.0|distanceTraveled=1.2493000000000838|cash=500|";
//
//        System.out.println("hunger: "+ hungerLoader(loadedData));
//        System.out.println("thirst: "+ thirstLoader(loadedData));
//        System.out.println("fuel: "+ fuelLoader(loadedData));
//        System.out.println("restroom: "+ restroomLoader(loadedData));
//        System.out.println("fatigue: "+ fatigueLoader(loadedData));
//
//
//    }

    public static double thirstLoader(String str){
        String resultStr = "";
        int startPoint = str.indexOf("thirst") + "thirst".length()+1;
        for (int i = startPoint; i <str.length() ; i++) {
            if(str.charAt(i) != '|'){
                resultStr += str.charAt(i);
            }
            else {break;}
        }
        return Double.parseDouble(resultStr);
    }

    public static double hungerLoader(String str){
        String resultStr = "";
        int startPoint = str.indexOf("hunger") + "hunger".length()+1;
        for (int i = startPoint; i <str.length() ; i++) {
            if(str.charAt(i) != '|'){
                resultStr += str.charAt(i);
            }
            else {break;}
        }
        return Double.parseDouble(resultStr);
    }

    public static double fuelLoader(String str){
        String resultStr = "";
        int startPoint = str.indexOf("fuel") + "fuel".length()+1;
        for (int i = startPoint; i <str.length() ; i++) {
            if(str.charAt(i) != '|'){
                resultStr += str.charAt(i);
            }
            else {break;}
        }
        return Double.parseDouble(resultStr);
    }


    public static double restroomLoader(String str){
        String resultStr = "";
        int startPoint = str.indexOf("restroom") + "restroom".length()+1;
        for (int i = startPoint; i <str.length() ; i++) {
            if(str.charAt(i) != '|'){
                resultStr += str.charAt(i);
            }
            else {break;}
        }
        return Double.parseDouble(resultStr);
    }

    public static double fatigueLoader(String str){
        String fatigueStr = "";
        int startPoint = str.indexOf("fatigue") + "fatigue".length()+1;
        for (int i = startPoint; i <str.length() ; i++) {
            if(str.charAt(i) != '|'){
                fatigueStr += str.charAt(i);
            }
            else {break;}
        }
        return Double.parseDouble(fatigueStr);

    }
}
