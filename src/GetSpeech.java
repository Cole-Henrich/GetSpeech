import org.jsoup.helper.ValidationException;
import org.jsoup.nodes.Document;

import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Scanner;

public class GetSpeech {
    private final String metabase = "https://obamawhitehouse.archives.gov/briefing-room/weekly-address?page=";
    private int pagenumber;
    private String meta_url;
    private String numpage_url;
    private String speechpage_url;
    private String mp4_url;
    private String mp3_url;
    private Document numpage;
    private Document speechpage;
    private String speechpage_title;
    private String numpage_html;
    private String speechpage_html;
    private File textfile;
    private String textfile_title;
    private String speechdate;
    private String texttext;
    private File mp3;
    public GetSpeech(int arg, boolean openTXT, boolean openHTML, boolean downloadMP3) throws IOException {
        DateTriplet[] all = DATES.d;
        DateTriplet q = all[arg];
        String sought = q.getValue();
        for (int i = 1; i < 44; i++) {
            String testURL = metabase + i;
            org.jsoup.nodes.Document document = org.jsoup.Jsoup.connect(testURL).get();
            String html = document.html();
//            System.out.println(html);
            if (html.contains(sought) || html.contains(sought.strip()) || html.contains(sought.toLowerCase()) || html.contains(sought.toUpperCase())) {
                numpage_url = testURL;
                System.out.println(numpage_url);
                break;
            }
        }
        boolean connected = true;
        try {
            numpage = org.jsoup.Jsoup.connect(numpage_url).get();
        } catch (ValidationException validationException) {
            connected = false;
            validationException.printStackTrace();
        }
        if (connected) {
            numpage_html = numpage.html();
            boolean Break = false;
            org.jsoup.nodes.Element div1;
            org.jsoup.select.Elements els1 = numpage.getElementsByAttributeValueContaining("class", "views-row views-row-");
            for (int i = 0; i < els1.size(); i++) {
                org.jsoup.nodes.Element e = els1.get(i);
                if (e.html().contains(sought)) {
                    speechpage_url = "https://obamawhitehouse.archives.gov" + e.children().get(1).children().get(0).children().get(0).attr("href");
                    speechpage_title = "wa";
                    break;
                }
            }
            System.out.println(speechpage_url);
            speechpage = org.jsoup.Jsoup.connect(speechpage_url).get();

            speechpage_html = speechpage.html();
            StringBuilder tt = new StringBuilder();
            org.jsoup.select.Elements paragraphs = speechpage.getElementsByAttributeValueContaining("class", "field-items");
            for (org.jsoup.nodes.Element paragraph : paragraphs) {
                tt.append(paragraph.text()).append("\n\n");
            }
            texttext = tt.toString();
            System.out.println(texttext);
            org.jsoup.select.Elements mp3elements = speechpage.getElementsByAttributeValueContaining("class", "link-weekly-address link-mp3");
            if (!mp3elements.isEmpty()) {
                mp3_url = mp3elements.get(0).attr("href");
                String examplepath = "/Users/colehenrich/Desktop/Barack-Obama-Speeches/2009:9:19 wa.txt";
                String examplemp3path = "/Users/colehenrich/Desktop/Barack-Obama-Speeches/2009:9:19 wa.mp3";
                String specifics = q.getlogicaldate().getY() + ":" + q.getlogicaldate().getM() + ":" + q.getlogicaldate().getD() + "\s" + speechpage_title;
                String textfilepath = "/Users/colehenrich/Desktop/Barack-Obama-Speeches/" + specifics;
                String mp3filepath = "/Users/colehenrich/Desktop/Barack-Obama-Speeches/Audio/" + specifics + ".mp3";

                Path path = Path.of(textfilepath);
                Path mp3path = Path.of(mp3filepath);
                textfile = path.toFile();
                textfile.delete();
                if (!textfile.exists()) {
                    textfile = Files.createFile(path).toFile();
                }
                textfile.setWritable(true);
                FileWriter finalWriter = new FileWriter(textfile);
                finalWriter.flush();
                finalWriter.write("");
                finalWriter.close();
                PrintStream textfile_ps = new PrintStream(textfile);
                System.setOut(textfile_ps);
                System.out.println(texttext);
                textfile_ps.close();
                System.setOut(System.out);
                System.out.println("0");
                Desktop desktop = Desktop.getDesktop();
                if (openHTML) {
                    desktop.browse(URI.create(speechpage_url));
                }
                if (openTXT) {
                    if (textfile.exists()) {
                        desktop.open(textfile);
                    }
                }
//        System.out.println("1");
                mp3 = mp3path.toFile();
                if (downloadMP3) {
//        InputStream in = new URL(mp3_url).openStream();
//        Files.copy(in, mp3path, StandardCopyOption.REPLACE_EXISTING);
                    URLConnection conn = new URL(mp3_url).openConnection();
                    InputStream is = conn.getInputStream();

                    OutputStream outstream = new FileOutputStream(mp3);
                    byte[] buffer = new byte[4096];
                    int len;
                    while ((len = is.read(buffer)) > 0) {
                        outstream.write(buffer, 0, len);
                    }
                    outstream.close();
                }
            }
        }
    }
}
