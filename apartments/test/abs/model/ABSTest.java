package abs.model;

import com.sun.deploy.uitoolkit.impl.awt.AWTAppletAdapter;
import org.junit.Test;

import java.awt.print.Book;

import static org.junit.Assert.assertTrue;

/*
      Testing the model using JUnit
 */
public class ABSTest {

    // Test Persons
    final Person pn1 = new Person("p1:olle ollesson:ollevägen:123:olle@com".split(":"));
    final Person pn2 = new Person("p2:fia fiasson:fiavägen:456:fia@com".split(":"));
    final Person pn3 = new Person("p3:svea sveasson:sveavägen:789:svea@com".split(":"));


    /* Test Periods
                    3..p4.. 8 ..p5..12      18p620
                    |       |        |       |  |
                |---|----|--|---|----|--|----|--|------|--  (show (non)overlapping periods)
                |        |      |       |       |      |
                1...p1...5     10..p2..15     20..p3..25

     */
    final Period pd1 = new Period("2018-01-01X2018-01-05");
    final Period pd2 = new Period("2018-01-10X2018-01-15");
    final Period pd3 = new Period("2018-01-20X2018-01-25");
    final Period pd4 = new Period("2018-01-03X2018-01-08");
    final Period pd5 = new Period("2018-01-08X2018-01-12");
    final Period pd6 = new Period("2018-01-18X2018-01-20");


    // Put cursor outside methods, then right click > Run to run all tests (else only one method run)

    @Test
    public void testPeriod() {

        assertTrue(pd1.getNDays() == 5);

        assertTrue(!pd1.overlaps(pd2));  // See diagram above
        assertTrue(!pd2.overlaps(pd3));
        assertTrue(!pd3.overlaps(pd1));

        assertTrue(pd4.overlaps(pd1));
        assertTrue(pd1.overlaps(pd4));

        assertTrue(pd5.overlaps(pd2));
        assertTrue(pd2.overlaps(pd5));

        assertTrue(!pd6.overlaps(pd3));  // Because leave at 10.00 arrive at 14.00
    }

    @Test
    public void testCRUDPerson() { // CRUD = Create, Read, Update, Delete
        ABS abs = new ABS();

        assertTrue(pn1.getId().equals("p1"));
        assertTrue(pn2.getId().equals("p2"));

        assertTrue(abs.add(pn1));
        assertTrue(!abs.add(pn1)); // No duplicates
        assertTrue(abs.add(pn2));


        // Find by object
        assertTrue(abs.find(pn1) == pn1);
        // Find by id
        assertTrue(abs.find(new Person("p1")) == pn1); // Need a generic method (see also find for apartments)

        assertTrue(abs.remove(pn1));
        assertTrue(abs.find(pn1) == null);
        assertTrue(abs.find(pn2) == pn2);
        assertTrue(abs.remove(new Person("p2")));  // Generic method for delete by id
        assertTrue(abs.find(pn2) == null);


        assertTrue(abs.find(new Person("p3")) == null);  // Not registered
        assertTrue(!abs.remove(new Person("p3")));       // Not registered
    }


    @Test
    public void testCRUDApartment() {
        ABS abs = new ABS();

        // Add apartment
        Apartment ap1 = new Apartment("a1", pn1, 4);
        Apartment ap2 = new Apartment("a2", pn1, 8);

        assertTrue(!abs.add(ap1));  // Owner not registered
        abs.add(pn1);               // Register

        assertTrue(abs.add(ap1));
        assertTrue(ap1.getOwner().equals(pn1));

        assertTrue(abs.add(ap2));   // Same owner ok
        assertTrue(!abs.add(ap2));  // No duplicates

        assertTrue(abs.find(ap1) == ap1);
        assertTrue(abs.find(new Apartment("a1")) == ap1);

        assertTrue(abs.remove(ap1));
        assertTrue(!abs.remove(new Apartment("ap1")));
        assertTrue(abs.find(ap1) == null);

        assertTrue(abs.find(pn1) == pn1);  // Owner not removed!

        assertTrue(abs.find(new Apartment("a3")) == null);  // Not registered
        assertTrue(!abs.remove(new Apartment("a3")));  // Not registered
    }

    @Test
    public void testCRUDBooking() {
        ABS abs = new ABS();

        Apartment ap1 = new Apartment("a1", pn1, 4);
        Booking b1 = new Booking("b1", pn2, ap1, 2, pd1);
        assertTrue(!abs.add(b1));          // Must register persons and apartment

        abs.add(pn1);   // Owner must be registered
        abs.add(pn2);   // Lodger  must be registered
        abs.add(ap1);   // Apartment must be registered

        /* added self for debugging
        Apartment ap2 = new Apartment("a2", pn1, 8);
        abs.add(pn3);
        abs.add(ap2);
        Booking b2 = new Booking("b2", pn3, ap1, 2, pd2);
        assertTrue(abs.add(b2));
        */
        assertTrue(abs.add(b1));    // Ok

        assertTrue(!abs.find(ap1).isFree(pd1));  // Apartment occupied pd1
        assertTrue(!abs.add(b1));    // No duplicates

       Booking b2 = new Booking("b1", pn2, ap1, 8, pd2);  // No overlap with b1
        assertTrue(!abs.add(b2));                          // Too many persons

        Booking b3 = new Booking("b3", pn2, ap1, 3, pd4); // Overlaps in time with b1
        assertTrue(!abs.add(b3));

        // May not remove anything involved in a booking (remove booking first)
        assertTrue(!abs.remove(pn1));
        assertTrue(!abs.remove(ap1));

        assertTrue(abs.remove(b1));
        assertTrue(!abs.remove(b1));
        assertTrue(abs.find(new Booking("b1")) == null);
        assertTrue(abs.find(ap1).isFree(pd1));

        assertTrue(abs.remove(new Apartment("a1")));  // Not involved in any booking
        assertTrue(abs.remove(pn1));
    }
}