import java.io.IOException;

public class Main {
    private static final String metabase = "https://obamawhitehouse.archives.gov/briefing-room/weekly-address?page=";
    public static void main(String[] args) throws IOException {
//        String testURL = metabase+34;
//        org.jsoup.nodes.Document document = org.jsoup.Jsoup.connect(testURL).get();
//        String html = document.html();
//        System.out.println(html);
        int[] arguments = new int[]{34};
        for (int arg:arguments){
            new GetSpeech(arg);
        }
    }
}
