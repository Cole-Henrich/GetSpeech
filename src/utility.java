import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class utility {
    private static HashMap<Integer, String[]> Months = new HashMap<>();
    private static void MapMonths(){
        Months.put(0, new String[]{"January", "Jan"});
        Months.put(1, new String[]{"February", "Feb"});
        Months.put(2, new String[]{"March", "Mar"});
        Months.put(3, new String[]{"April", "Apr"});
        Months.put(4, new String[]{"May"});
        Months.put(5, new String[]{"June", "Jun"});
        Months.put(6, new String[]{"July", "Jul"});
        Months.put(7, new String[]{"August", "Aug"});
        Months.put(8, new String[]{"September", "Sep"});
        Months.put(9, new String[]{"October", "Oct"});
        Months.put(10, new String[]{"November", "Nov"});
        Months.put(11, new String[]{"December", "Dec"});
    }
    public static int MonthValueOf(String name){
        int rtn = -1;
        if (name.contains("Jan"))rtn=0;
        if (name.contains("Feb"))rtn=1;
        if (name.contains("Mar"))rtn=2;
        if (name.contains("Apr"))rtn=3;
        if (name.contains("May"))rtn=4;
        if (name.contains("Jun"))rtn=5;
        if (name.contains("Jul"))rtn=6;
        if (name.contains("Aug"))rtn=7;
        if (name.contains("Sep"))rtn=8;
        if (name.contains("Oct"))rtn=9;
        if (name.contains("Nov"))rtn=10;
        if (name.contains("Dec"))rtn=11;
        return rtn+1;
    }
    public static int mvo(String name){
        MapMonths();
        Set<Map.Entry<Integer, String[]>> entrySet=Months.entrySet();
        for (Map.Entry<Integer, String[]> entry:entrySet) {
            for (String s:entry.getValue()){
                if (s.equalsIgnoreCase(name)){
                    return entry.getKey();
                }
            }
        }
        return -1;
    }
    public static String appendLeading0format(int x){
        if (x > 0 && x < 10){return "0"+x;}
        return ""+x;
    }
}
