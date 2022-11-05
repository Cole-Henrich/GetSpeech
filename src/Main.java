import java.io.IOException;

public class Main {
    private static final String metabase = "https://obamawhitehouse.archives.gov/briefing-room/weekly-address?page=";
    public static void main(String[] args) throws IOException {
//        getSpeech(new int[]{41});
        for (int i = 30; i<475;i++){
            new GetAR(i);
        }
    }
    private static void getSpeech(int[] arguments) throws IOException {
        for (int arg:arguments){
            new GetSpeech(arg, true, true, false);
        }
    }
    private static void downloadMP3s() throws IOException {
        for (int arg = 0; arg < 431; arg++){
            new GetSpeech(arg, false, false, true);
        }
    }
}
