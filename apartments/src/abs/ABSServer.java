package abs;

import abs.view.Pages;
import abs.model.*;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.*;

import static java.lang.System.out;
import static java.net.HttpURLConnection.HTTP_ACCEPTED;
import static java.net.HttpURLConnection.HTTP_BAD_METHOD;
import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;

import java.net.URLDecoder;
/*
        Web front end to the Apartment booking system

        *** NOT MUCH to do here ***

 */
public class ABSServer {

    // The only reference to the model
    private final ABS abs = new ABS(new ABSTestData());  // During development
    // Helper to get specific pages
    private final Pages ps = new Pages();

    // -----  Methods calling the model -----------------------------------------
    // TODO When all test pass uncomment one method at the time and run the Server
    // TODO NOTE: Must also uncomment row in method handlePost below


    private String handleAddPerson(String args) {
        String properArgs = URLDecoder.decode(args);
        List<String> vs = toList(properArgs);
        Person person = new Person(vs.get(0), vs.get(1), vs.get(2), vs.get(3), vs.get(4));
        if (abs.add(person)) {
            return ps.getRedirectPage(PERSONS_URL);
        } else {
            return ps.getErrorPage("Could't add " + person, HTTP_INTERNAL_ERROR); // Bad request
        }
    }


    private String handleRemovePerson(String args) {
        String id = args.split("=")[1];
        if (abs.remove(new Person(id))) {
            return ps.getRedirectPage(PERSONS_URL);
        } else {
            return ps.getErrorPage("Could't remove " + id, HTTP_INTERNAL_ERROR);
        }
    }

    private String handleAddApartment(String args) {
        List<String> vs = toList(args);
        Person owner = new Person(vs.get(1));
        Apartment apartment = new Apartment(vs.get(0), owner, Integer.valueOf(vs.get(2)));
        if (abs.add(apartment)) {
            return ps.getRedirectPage(APARTMENTS_URL);
        } else {
            return ps.getErrorPage("Could't add " + apartment, HTTP_INTERNAL_ERROR);
        }
    }


    private String handleRemoveApartment(String args) {
        String id = args.split("=")[1];
        if (abs.remove(new Apartment(id))) {
            return ps.getRedirectPage(APARTMENTS_URL);
        } else {
            return ps.getErrorPage("Couldn't remove " + args, HTTP_INTERNAL_ERROR);
        }
    }


    private String handleAddBooking(String args) {
        List<String> vs = toList(args);
        Person guest = abs.find(new Person(vs.get(3)));
        out.println(guest);
        Apartment apartment = abs.find(new Apartment(vs.get(2)));
        out.println(apartment);
        Period period = new Period(vs.get(1));
        Booking booking = new Booking(vs.get(0), guest, apartment, Integer.valueOf(vs.get(4)), period);
        out.println(booking);
        if (abs.add(booking)) {
            return ps.getRedirectPage(BOOKINGS_URL);
        } else {
            return ps.getErrorPage("Could't add " + booking, HTTP_INTERNAL_ERROR);
        }
    }


    private String handleRemoveBooking(String args) {
        String id = args.split("=")[1];
        if (abs.remove(new Booking(id))) {
            return ps.getRedirectPage(BOOKINGS_URL);
        } else {
            return ps.getErrorPage("Couldn't remove " + id, HTTP_INTERNAL_ERROR);
        }
    }


    // --------------- Nothing to do below this ----------------------------

    public final int DEFAULT_PORT = 8080;

    // The URLs (also known as URIs)
    public final String ADD = "/add-form";
    public final String DEL = "/del-form";
    public final String HOME_URL = "/";
    public final String PERSONS_URL = "/persons";
    public final String PERSON_URL = "/person";
    public final String APARTMENTS_URL = "/apartments";
    public final String APARTMENT_URL = "/apartment";
    public final String BOOKINGS_URL = "/bookings";
    public final String BOOKING_URL = "/booking";


    private void run() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(DEFAULT_PORT), 0);
        HttpContext context = server.createContext(HOME_URL);
        context.setHandler(this::handleRequest);
        server.start();
        out.println("Server started. Visit localhost:" + server.getAddress().getPort());
    }

    private void handlePost(HttpExchange exchange) throws IOException {
        Scanner sc = new Scanner(exchange.getRequestBody());
        String args = sc.nextLine();
        out.println("Arguments " + args);
        String uri = exchange.getRequestURI().toString();
        String response = "";
        int statusCode = HTTP_ACCEPTED;
        if (uri.equals(PERSON_URL)) {
            response = handleAddPerson(args);
        } else if (uri.equals(PERSON_URL + "?delete=true")) {
            response = handleRemovePerson(args);
        } else if (uri.equals(APARTMENT_URL)) {
            response = handleAddApartment(args);
        } else if (uri.equals(APARTMENT_URL + "?delete=true")) {
            response = handleRemoveApartment(args);
        } else if (uri.equals(BOOKING_URL)) {
            response = handleAddBooking(args);                  //TODO
        } else if (uri.equals(BOOKING_URL + "?delete=true")) {
            response = handleRemoveBooking(args);               //TODO
        } else {
            statusCode = HTTP_INTERNAL_ERROR;
            response = ps.getErrorPage("No matching URL " + uri, statusCode);
        }
        sendResponse(exchange, statusCode, response);
    }

    // Rather ugly method!
    private void handleGet(HttpExchange exchange) throws IOException {
        String uri = exchange.getRequestURI().toString();
        String response;
        int statusCode = HTTP_ACCEPTED;
        if (uri.equals(HOME_URL)) {
            response = ps.getHomePage();
            /*
                Persons
            */
        } else if (uri.equals(PERSONS_URL)) {
            response = ps.getListPage("Persons", abs.getPersons());
        } else if (uri.equals(PERSONS_URL + ADD)) {
            response = ps.getAddPage("Person", "Add a Person",
                    Arrays.asList("Id", "Name", "Street", "Phone", "Email"));
        } else if (uri.equals(PERSONS_URL + DEL)) {
            response = ps.getDeletePage("Person", "Delete a Person", Arrays.asList("Id"));
        /*
            Apartments
         */
        } else if (uri.equals(APARTMENTS_URL)) {
            response = ps.getListPage("Apartments", abs.getApartments());
        } else if (uri.equals(APARTMENTS_URL + ADD)) {
            response = ps.getAddPage("Apartment", "Add an apartment",
                    Arrays.asList("Id", "OwnerId", "MaxGuests"));
        } else if (uri.equals(APARTMENTS_URL + DEL)) {
            response = ps.getDeletePage("Apartment", "Delete an apartment", Arrays.asList("Id"));
        /*
            Bookings
         */
        } else if (uri.equals(BOOKINGS_URL)) {
            response = ps.getListPage("Bookings", abs.getBookings());
        } else if (uri.equals(BOOKINGS_URL + ADD)) {
            response = ps.getAddPage("Booking",
                    "Add a booking. Period format is: \"yyyy-mm-ddXyyyy-mm-dd\"",
                    Arrays.asList("Id", "Period", "ApartmentId", "GuestId", "NPersons"));
        } else if (uri.equals(BOOKINGS_URL + DEL)) {
            response = ps.getDeletePage("Booking", "Delete a booking", Arrays.asList("Id"));
        /*
            Error
         */
        } else {
            statusCode = HTTP_INTERNAL_ERROR;
            response = ps.getErrorPage("No such URL " + uri, statusCode);
        }
        sendResponse(exchange, statusCode, response);
    }

    private void handleRequest(HttpExchange exchange) {
        try {
            out.println(exchange.getRequestMethod());
            out.println(exchange.getRequestURI());
            if (exchange.getRequestMethod().equals("GET")) {
                handleGet(exchange);
            } else if (exchange.getRequestMethod().equals("POST")) {
                handlePost(exchange);
            } else {
                String errorPage = ps
                        .getErrorPage("Can only handle GET and POST "
                                + exchange.getRequestMethod(), HTTP_BAD_METHOD);
                sendResponse(exchange, HTTP_BAD_METHOD, errorPage);
            }
        } catch (IOException ioe) {
            out.println("An IO exception occurred " + ioe.getMessage());
        } catch (Exception e) {
            out.println("Exception occurred " + e.getMessage());
        }
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    /*
        Split up incoming arguments into a list of values
        argString:  Id=1&Name=Olle&Street=Ollevägen& ...
        result: ["1", "Olle", "Ollevägen", ...]
        NOTE: Order is important. See form fields in pages
     */
    private List<String> toList(String argString) {
        List<String> values = new ArrayList<>();
        String[] nameValues = argString.split("&");
        for (String s : nameValues) {
            values.add(s.split("=")[1]);
        }
        return values;
    }

    public static void main(String[] args) throws IOException {
        new ABSServer().run();
    }


}

