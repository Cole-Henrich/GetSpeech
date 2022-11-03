import org.jsoup.nodes.Document;

import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
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
    public GetSpeech(int arg) throws IOException {
        DateTriplet[] all = DATES.d;
        DateTriplet q = all[arg];
//        DateGenerator dateGenerator = new DateGenerator();
//        DateTriplet[] all = DATES.d;
//        DateTriplet q = all[PAST.length];
//        PAST.length++;
//        FileWriter updateLength = new FileWriter(files.PAST);
//        Scanner scanner = new Scanner(files.PAST);
//        StringBuilder contents = new StringBuilder();
//        while (scanner.hasNextLine()){
//            String nextLine = scanner.nextLine();
//            contents.append(nextLine);
//        }
//        String s = contents.toString();
//        s = s.replaceAll("[0-9]", String.valueOf(PAST.length));
//
//        updateLength.write(s);
//        updateLength.close();
        String sought = q.getValue();
        for (int i = 1; i < 44; i++) {
            String testURL = metabase+i;
            org.jsoup.nodes.Document document = org.jsoup.Jsoup.connect(testURL).get();
            String html = document.html();
//            System.out.println(html);
            if (html.contains(sought)){
                numpage_url = testURL;
                System.out.println(numpage_url);
                break;
            }
        }
        numpage = org.jsoup.Jsoup.connect(numpage_url).get();
        numpage_html = numpage.html();
        boolean Break = false;
        org.jsoup.nodes.Element div1;
        org.jsoup.select.Elements els1 = numpage.getElementsByAttributeValueContaining("class", "views-row views-row-");
        for (int i = 0; i < els1.size(); i++) {
            org.jsoup.nodes.Element e = els1.get(i);
            if (e.html().contains(sought)){
                speechpage_url = "https://obamawhitehouse.archives.gov"+e.children().get(1).children().get(0).children().get(0).attr("href");
                speechpage_title = "wa";
                break;
            }
        }
//        for (int i = 0; i < numpage.children().size(); i++) {
//            div1 = numpage.children().get(i);
////            System.out.println(div1.html());
//            if (div1.className().contains("views-row views-row")){
//                System.out.println(div1.html());
//                for (int j = 0; j < div1.children().size(); j++) {
//                    org.jsoup.nodes.Element child = div1.children().get(j);
//                    if (child.className().contains("views-field views-field-created")){
//                        if (child.children().get(0).tagName().equals("span") && child.children().get(0).className().equals("field-content")){
//                            if (child.children().get(0).html().equalsIgnoreCase(sought)){
//                                    //speechpage_url = numpage.children().get(i).children().get(j).children().get(1).children().get(0).children().get(0).attr("href");
//                                    speechpage_url = child.children().get(1).children().get(0).children().get(0).attr("href");
//                                speechpage_title = child.children().get(1).children().get(0).children().get(0).html();
//                                Break = true;
//                                break;
//                            }
//                        }
//                    }
//                }
//            }
//            if (Break) {break;}
//        }
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
        String examplepath = "/Users/colehenrich/Desktop/Barack-Obama-Speeches/2009:9:19 wa.txt";
        String textfilepath = "/Users/colehenrich/Desktop/Barack-Obama-Speeches/"+q.getlogicaldate().getY()+":"+q.getlogicaldate().getM()+":"+q.getlogicaldate().getD()+"\s"+speechpage_title;
        Path path = Path.of(textfilepath);

        textfile = path.toFile();
        textfile.delete();
        if (!textfile.exists()){
            textfile = Files.createFile(path).toFile();
        }
        textfile.setWritable(true);
        FileWriter finalWriter = new FileWriter(textfile);
        finalWriter.flush();
        finalWriter.write("");
        finalWriter.close();
        PrintStream textfile_ps = new PrintStream(textfile);
        System.setOut(textfile_ps);;
        System.out.println(texttext);
        System.setOut(System.out);
        Desktop desktop = Desktop.getDesktop();
        desktop.browse(URI.create(speechpage_url));
        if(textfile.exists()) {desktop.open(textfile);}

}
}
