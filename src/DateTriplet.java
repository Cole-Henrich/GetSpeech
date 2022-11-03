public class DateTriplet {
    private int key;
    private String value;
    private LogicalDate logicaldate;
    public DateTriplet(int KEY, String VALUE, LogicalDate logicalDate){
        key = KEY;
        value = VALUE;
        logicaldate = logicalDate;
    }
    public LogicalDate getlogicaldate() {
        return logicaldate;
    }
    public String getValue() {
        return value;
    }
    public int getKey() {
        return key;
    }
}
