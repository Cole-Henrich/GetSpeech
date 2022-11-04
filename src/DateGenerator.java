import java.io.FileWriter;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.Month;
import java.util.*;

public class DateGenerator {
    private ArrayList<DateTriplet> dates;
    public DateGenerator() throws IOException {
        dates = new ArrayList<>();
        int count = 0;
        Calendar cal = new GregorianCalendar();
        for (int year = 2010; year < 2018; year++) {
            cal.set(Calendar.YEAR, year);
            for (int monthIndex = 0; monthIndex < 12; monthIndex++) {
                cal.set(Calendar.MONTH, monthIndex);
                cal.set(Calendar.DAY_OF_MONTH, 0);
                do {
                    // get the day of the week for the current day
                    int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
                    // check if it is a Saturday
                    if (dayOfWeek == Calendar.SATURDAY) {
                        // print the day - but you could add them to a list or whatever
//                        System.out.println(cal.get(Calendar.DAY_OF_MONTH));
                        String monthNameinitial = null;
                        String dayOfWeekName = null;
//                        try
//                        {
                            monthNameinitial = Month.of(cal.get(Calendar.MONTH)+1).name().strip();
//                        }
//                        catch(DateTimeException invalidMonthOfYear){
//                            break;
//                        }
//                        try
                       // {
                            dayOfWeekName = DayOfWeek.of(cal.get(Calendar.DAY_OF_WEEK)).name().strip();
                       // }
//                        catch(DateTimeException e){
//                            break;
//                        }
                        String appendedLeading0 = utility.appendLeading0format(cal.get(Calendar.DAY_OF_MONTH));
                        String monthName = monthNameinitial.toLowerCase().substring(1);
                        monthName = String.valueOf(monthNameinitial.charAt(0)).toUpperCase()+monthName;
                        String name = monthName+"\s"+appendedLeading0+",\s"+year;
                        String fullname = dayOfWeekName+"\s"+name;
                        DateTriplet t = new DateTriplet(count, name,new LogicalDate(year, monthIndex+1,cal.get(Calendar.DAY_OF_MONTH)));
                        dates.add(t);
                        count++;
                    }
                    // advance to the next day
                    cal.add(Calendar.DAY_OF_YEAR, 1);
                }
                while (cal.get(Calendar.MONTH) == monthIndex);
            }
            cal.add(Calendar.YEAR, 1);
        }
    }

    public ArrayList<DateTriplet> getdates() {
        return dates;
    }
    public static void main(String[] args) throws IOException {
        write();
    }
    private static void write() throws IOException {
        String start = """                                
                public class DATES {
                    public static final DateTriplet[] d = {
                """;
        String end = """
                    };
                }
                """;
        String example = """         
                public class DATES {
                    public static final DateTriplet[] d = {
                        new DateTriplet(0, "JANUARY 02, 2010", new LogicalDate(2010, 1, 2)),
                    };
                }
                """;
        DateGenerator d = new DateGenerator();
        ArrayList<DateTriplet> t = d.getdates();
        FileWriter fileWriter = new FileWriter(files.DATES);
        fileWriter.flush();
        fileWriter.write(start);
        for (int i = 0; i < t.size()-1; i++) {
            DateTriplet dateTriplet = t.get(i);
            LogicalDate ld = dateTriplet.getlogicaldate();
            String all = dateTriplet.getKey() + " " + dateTriplet.getValue() + " " + ld.getY() + " " + ld.getM() + " " + ld.getD();
            fileWriter.write("new DateTriplet(" + dateTriplet.getKey() + ",\s" + "\"" + dateTriplet.getValue() + "\", new LogicalDate(" + ld.getY() + ",\s" + ld.getM() + ",\s" + ld.getD() + ")),\n");
        }
        DateTriplet dateTriplet = t.get(t.size()-1);
        LogicalDate ld = dateTriplet.getlogicaldate();
        fileWriter.write("new DateTriplet(" + dateTriplet.getKey() + ",\s" + "\"" + dateTriplet.getValue() + "\", new LogicalDate(" + ld.getY() + ",\s" + ld.getM() + ",\s" + ld.getD() + "))\n");
        fileWriter.write(end);
        fileWriter.close();
    }
    private static void print() throws IOException {
        DateGenerator d = new DateGenerator();
        ArrayList<DateTriplet> t = d.getdates();
        for (DateTriplet dateTriplet : t) {
            LogicalDate ld = dateTriplet.getlogicaldate();
            System.out.println(dateTriplet.getKey()+" "+dateTriplet.getValue()+" "+ld.getY()+" "+ld.getM()+" "+ld.getD());

        }
    }
}
