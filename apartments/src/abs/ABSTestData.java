package abs;

import abs.model.Apartment;
import abs.model.Booking;
import abs.model.Period;
import abs.model.Person;

import java.util.ArrayList;
import java.util.List;

/*
       Use for application testing

        *** Nothing to do here ***

 */
public class ABSTestData {

    public List<Apartment> apartments = new ArrayList<>();
    public List<Person> persons = new ArrayList<>();

    public List<Booking> bookings = new ArrayList<>();

    public ABSTestData() {
        /*
            Some data is duplicate data (also used in Tests)
            Should read it from file (define once in the file)
            But this is an introductory course

         */
        // Some default data
        Person pn1 = new Person("p1:olle ollesson:ollevägen:123:olle@com".split(":"));
        Person pn2 = new Person("p2:fia fiasson:fiavägen:456:fia@com".split(":"));
        Person pn3 = new Person("p3:svea sveasson:sveavägen:789:svea@com".split(":"));
        persons.add(pn1);
        persons.add(pn2);
        persons.add(pn3);
        apartments.add(new Apartment("a1", persons.get(0), 4));
        apartments.add(new Apartment("a2", persons.get(0), 8));
        Period pd1 = new Period("2019-01-01X2019-01-05");
        bookings.add(new Booking("b1", persons.get(1), apartments.get(1), 2, pd1));
    }
}
