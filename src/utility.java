public class utility {
    public static String appendLeading0format(int x){
        if (x > 0 && x < 10){return "0"+x;}
        return ""+x;
    }
}
