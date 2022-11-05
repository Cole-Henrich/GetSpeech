import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;

public class GetAR {
    private final String metabase = "https://www.americanrhetoric.com/";
    private final String index_url = "https://www.americanrhetoric.com/barackobamaspeeches.htm";
    private Document index;
    private Document speech;
    private String text;
    private File textfile;
    private File mp3;
    private File pdf;
    private String mp3_url;
    private String speech_url;
    private String pdf_url;
    private String title;
    private String dateName;
    private LogicalDate logicalDate;

    private String indexHTML;
    private String speechHTML;
    public GetAR(int arg) throws IOException {
        org.jsoup.nodes.Document index = org.jsoup.Jsoup.connect(index_url).get();
        indexHTML = index.html();
        Elements tables = index.getElementsByAttributeValueMatching("id", "AutoNumber1");
        Element table = tables.get(0);
        Elements trs = table.getElementsByTag("tr");
        Element tr = trs.get(arg);
        Elements datetds = tr.getElementsByAttributeValueMatching("bgcolor", "#E2EBFE");
        if (datetds.size() > 0) {
            Element datetd = datetds.get(0);
            dateName = datetd.text();
            logicalDate = LogicalDate.valueOf(dateName);
            Elements titletds = tr.getElementsByAttributeValueMatching("bgcolor", "#FFFFFF");
            Element titletd = titletds.get(0);
            title = titletd.text();
            Elements title_as = titletd.getElementsByTag("a");
            Element title_a = title_as.get(0);
            speech_url = metabase + title_a.attr("href");
            speech = org.jsoup.Jsoup.connect(speech_url).get();
            speechHTML = speech.html();
            Elements mp3tds = tr.getElementsByAttributeValueMatching("bgcolor", "#800000");
            if (mp3tds.size() > 0) {
                Element mp3td = mp3tds.get(0);
                Elements mp3_as = mp3td.getElementsByTag("a");
                Element mp3_a = mp3_as.get(0);
                mp3_url = metabase + mp3_a.attr("href");
                String specifics = logicalDate.getY() + ":" + logicalDate.getM() + ":" + logicalDate.getD() + "\s" + title;
                String mp3filepath = "/Users/colehenrich/Desktop/Barack-Obama-Speeches/Audio/" + specifics + ".mp3";
                Path mp3path = Path.of(mp3filepath);
                mp3 = mp3path.toFile();
                URLConnection conn = new URL(mp3_url).openConnection();
                InputStream is = conn.getInputStream();
                OutputStream outstream = new FileOutputStream(mp3);
                byte[] buffer = new byte[4096];
                int len;
                while ((len = is.read(buffer)) > 0) {
                    outstream.write(buffer, 0, len);
                }
                outstream.close();
                Elements texttds = speech.getElementsByAttributeValueContaining("width", "77%");
                if (texttds.size() > 0) {
                    Element texttd = texttds.get(0);
                    Elements ps = texttd.getElementsByTag("p");
                    StringBuilder textBuilder = new StringBuilder();
                    for (Element p : ps) {
                        textBuilder.append(p.text()).append("\n");
                    }
                    text = textBuilder.toString();
                    String textfilepath = "/Users/colehenrich/Desktop/Barack-Obama-Speeches/Text" + specifics + ".txt";
                    Path path = Path.of(textfilepath);
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
                    System.out.println(text);
                    textfile_ps.close();
                    System.setOut(System.out);
                }
            }
        }
    }
}
