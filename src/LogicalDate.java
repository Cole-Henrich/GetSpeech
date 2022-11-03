import java.sql.Date;
import java.time.Month;

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
    public static LogicalDate valueOf(String string) {
        DatePair d = new DatePair(0, "January 24, 2009");
        String[] split = string.split(",");
        int year = Integer.parseInt(split[1].strip());
        int month = Month.valueOf(split[0].replaceAll("[0-9]", "")).getValue();
        int day = Integer.parseInt(split[0].replaceAll("[^0-9]", ""));
        return new LogicalDate(year, month, day);
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
