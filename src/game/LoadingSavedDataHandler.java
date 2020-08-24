package game;

public class LoadingSavedDataHandler {

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
        String resultStr = "";
        int startPoint = str.indexOf("fatigue") + "fatigue".length()+1;
        for (int i = startPoint; i <str.length() ; i++) {
            if(str.charAt(i) != '|'){
                resultStr += str.charAt(i);
            }
            else {break;}
        }
        return Double.parseDouble(resultStr);

    }
}
