package abs.view;

import java.util.List;

/*
     Dynamic pages for the ABS application
     (this is not the way to do it in real life)

      *** Nothing to do here ***

 */
public class Pages {

    public String getHomePage() {
        PageBuilder pb = new PageBuilder();
        return pb.addTitle("ABS")
                .addH1("Welcome to Apartment Booking System")
                .addParagraph("Select link to do your job")
                .addAnchor("/persons", "View Persons").addLineBreak()
                .addAnchor("/apartments", "View Apartments").addLineBreak()
                .addAnchor("/bookings", "View Bookings").addLineBreak()
                .toString();
    }

    // Text like "Persons", s last!!
    public <T> String getListPage(String text, List<T> data) {
        String textLC = text.toLowerCase();
        PageBuilder pb = new PageBuilder();
        return pb.addTitle(text)
                .addH1(text)
                .addParagraph("All " + textLC + " in system")
                .addTable(data).addLineBreak()
                .addAnchor("/" + textLC + "/add-form", "Add").addLineBreak()
                .addAnchor("/" + textLC + "/del-form", "Delete").addLineBreak()
                .addAnchor("/", "Home")
                .toString();
    }

    // Topic like "Person", **NO** s last!
    public String getAddPage(String topic, String msg, List<String> labels) {
        String textLC = topic.toLowerCase();
        PageBuilder pb = new PageBuilder();
        return pb.addTitle("Add " + textLC)
                .addH1("Add " + textLC)
                .addParagraph(msg)
                .addForm("/" + textLC, "Add " + topic, labels)
                .toString();
    }

    // Topic like "Person", **NO** s last!
    public String getDeletePage(String topic, String msg, List<String> labels) {
        String textLC = topic.toLowerCase();
        PageBuilder pb = new PageBuilder();
        return pb.addTitle("Delete " + topic)
                .addH1("Delete " + topic)
                .addParagraph(msg)
                // Can't send HTTP DELETE using a form. Add ?delete=true to use with POST
                .addForm("/" + textLC + "?delete=true", "Delete " + topic, labels)
                .toString();
    }

    public String getRedirectPage(String url) {
        PageBuilder pb = new PageBuilder();
        return pb.addRedirect(url).toString();
    }

    public String getErrorPage(String msg, int statusCode) {
        PageBuilder pb = new PageBuilder();
        return pb.addTitle("Error")
                .addH1("An Error occured")
                .addParagraph(msg)
                .addParagraph("Code: " + statusCode)
                .addAnchor("/", "Home").toString();
    }
}
