/* 	Name: Tyler Johnson, Justin Dang, Branigan Geoates
	Username: group1
	Problem Set: Final Project
	Due Date: December 9th, 2021
*/

package tools;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MoneyHandler {

    public static float getRoundedMoney(float money){
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);


        return Float.valueOf(df.format(money));
    }

    public static String getFormattedMoney(float money){
        return String.format("$%,.2f", getRoundedMoney(money));
    }
    
}
