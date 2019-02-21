package abs.view;

import java.util.ArrayList;
import java.util.List;

/*

    General utility to build web pages  (similar to StringBuilder)
    (this is not the way to do it in real life)

    *** Nothing to do here ***

 */
public class PageBuilder {

    private final List<String> head = new ArrayList<>();
    private final List<String> body = new ArrayList<>();

    public PageBuilder addTitle(String title) {
        head.add("<title>" + title + "</title>");
        return this;
    }

    public PageBuilder addRedirect(String url) {
        head.add("<meta http-equiv=\"refresh\" content=\"0; URL=" + url + "\"/>");
        return this;
    }

    public PageBuilder addLineBreak() {
        body.add("<br/>");
        return this;
    }

    public PageBuilder addAnchor(String url, String display) {
        body.add("<a href=\"" + url + "\">" + display + "</a>");
        return this;
    }

    public PageBuilder addH1(String text) {
        body.add("<h1>" + text + "</h1>");
        return this;
    }

    public PageBuilder addH2(String text) {
        body.add("<h2>" + text + "</h2>");
        return this;
    }

    public PageBuilder addParagraph(String text) {
        body.add("<p>" + text + "</p>");
        return this;
    }

    public <T> PageBuilder addTable(List<T> data) {
        StringBuilder sb = new StringBuilder();
        sb.append("<table><thead></thead><tbody>");
        for (T t : data) {
            sb.append("<tr><td>");
            sb.append(t.toString());
            sb.append("</td></tr>");
        }
        sb.append("</tbody></table>");
        body.add(sb.toString());
        return this;
    }

    public PageBuilder addForm(String action, String legend, List<String> names) {
        StringBuilder sb = new StringBuilder();
        sb.append("<form action=\"").append(action)
                .append("\" method=\"POST\">")
                .append("<fieldset><legend>")
                .append(legend).append("</legend>");
        for (String name : names) {
            sb.append(name);
            sb.append("<br/>");
            sb.append("<input type=\"text\" name=\"");
            sb.append(name);
            sb.append("\"><br/>");
        }
        sb.append("<input type=\"submit\" value=\"Submit\">");
        sb.append("</fieldset></form>");
        body.add(sb.toString());
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<!doctype html><html lang=\"en\">")
                .append("<head><link rel=\"icon\" href=\"data:;base64,=\"><meta charset=\"utf-8\">");
        for (String s : head) {
            sb.append(s);
        }
        sb.append("</head>").append("<body>");
        for (String s : body) {
            sb.append(s);
        }
        sb.append("</body>").append("</html>");
        return sb.toString();
    }
}
