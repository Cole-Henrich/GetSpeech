import java.io.IOException;

public class Main {
    private static final String metabase = "https://obamawhitehouse.archives.gov/briefing-room/weekly-address?page=";
    public static void main(String[] args) throws IOException {
        downloadMP3s();
    }
    private static void getSpeech(int[] arguments) throws IOException {
        for (int arg:arguments){
            new GetSpeech(arg, true, true, false);
        }
    }
    private static void downloadMP3s() throws IOException {
        for (int arg = 0; arg < 450; arg++){
            new GetSpeech(arg, false, false, true);
        }
    }
}
