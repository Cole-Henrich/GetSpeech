import java.sql.Date;
import java.time.Month;
import java.util.Arrays;

public class LogicalDate {
    private int y;
    private int m;
    private int d;
    public LogicalDate(){

    }
    public LogicalDate(int YEAR, int MONTH, int DAY){
        setY(YEAR);
        setM(MONTH);
        setD(DAY);
    }
    public static LogicalDate valueOf1(String string) {
        DatePair d = new DatePair(0, "January 24, 2009");
        String[] split = string.split(",");
        int year = Integer.parseInt(split[1].strip());
        int month = Month.valueOf(split[0].replaceAll("[0-9]", "")).getValue();
        int day = Integer.parseInt(split[0].replaceAll("[^0-9]", ""));
        return new LogicalDate(year, month, day);
    }
    public static LogicalDate valueOf2(String string){
        String[] split = string.split(" ");
        int year = Integer.parseInt(split[2].strip());
        int month = utility.MonthValueOf(split[1].replaceAll("[0-9]", ""));
        int day = Integer.parseInt(split[0].replaceAll("[^0-9]", ""));
        return new LogicalDate(year, month, day);
    }
    public static LogicalDate valueOf(String string){
        String temp1 = string.toLowerCase();
        temp1 = temp1.replaceAll("[a-z]", "");
        if (string.length()-temp1.length() > 3){
            return valueOf1(string);
        }
        else {
            return valueOf2(string);
        }
    }
    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int getM() {
        return m;
    }

    public void setD(int d) {
        this.d = d;
    }

    public int getD() {
        return d;
    }
}
