package abs.model;

import abs.ABSTestData;

import java.awt.print.Book;
import java.security.acl.Owner;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*

        The Apartment Booking System (ABS)

 */
public class ABS {

    private final List<Apartment> apartments;
    private final List<Person> persons;
    private final List<Booking> bookings;

    public ABS() {
        apartments = new ArrayList<>();
        persons = new ArrayList<>();
        bookings = new ArrayList<>();
    }

    public ABS(ABSTestData testData) {
        this.apartments = testData.apartments;
        this.bookings = testData.bookings;
        this.persons = testData.persons;
    }


    // TODO a lot...
    //----- Person  ----------------------------------------------------------------------------------------------------
    public boolean add(Person pn) {
        if (!persons.contains(pn)) {
            persons.add(pn);
            return true;
        }
        return false;
    }

    public Person find(Person pn) {
        if (persons.contains(pn)) {
            int indexOfPerson = persons.indexOf(pn);
            return persons.get(indexOfPerson);
        }
        return null;
    }

    public boolean remove(Person pn) {
        if (persons.contains(pn)) {
            if (bookings.size() == 0 && apartments.size() == 0) {
                persons.remove(pn);
                return true;
            } else {
                List<Person> bookedPersons = new ArrayList<>();
                Iterator<Booking> bookingIterator = bookings.iterator();
                while (bookingIterator.hasNext()) {
                    Booking booking = bookingIterator.next();
                    bookedPersons.add(booking.getGuest());
                }
                Iterator<Apartment> apartmentIterator = apartments.iterator();
                while (apartmentIterator.hasNext()) {
                    Apartment apartment = apartmentIterator.next();
                    bookedPersons.add(apartment.getOwner());
                }
                if (!bookedPersons.contains(pn)) {
                    persons.remove(pn);
                    return true;
                }
            }
        }
        return false;
    }

    //---- Apartment  --------------------------------------------------------------------------------------------------
    /*
    public boolean add(Apartment ap) {
        if (apartments.size() == 0 && persons.contains(ap.getOwner())) {
            apartments.add(ap);
            return true;
        } else {
            Iterator<Apartment> apartmentIterator = apartments.iterator();
            List<String> apartmentIds = new ArrayList<>();
            while (apartmentIterator.hasNext()) {
                Apartment apartment = apartmentIterator.next();
                apartmentIds.add(apartment.getId());
            }
            if ((!apartmentIds.contains(ap.getId())) && persons.contains(ap.getOwner())) {
                apartments.add(ap);
                return true;
            }
        }
        return false;
    } */

    public boolean add(Apartment ap) {
        Iterator<Apartment> apartmentIterator = apartments.iterator();
        List<String> apartmentIds = new ArrayList<>();
        while (apartmentIterator.hasNext()) {
            Apartment apartment = apartmentIterator.next();
            apartmentIds.add(apartment.getId());
        }
        if (!apartmentIds.contains(ap.getId()) && persons.contains(ap.getOwner())) {
            apartments.add(ap);
            return true;
        }
        return false;
    }


    public Apartment find(Apartment ap) {
        Iterator<Apartment> apartmentIterator = apartments.iterator();
        List<String> apartmentIds = new ArrayList<>();
        while (apartmentIterator.hasNext()) {
            Apartment alAp = apartmentIterator.next();
            apartmentIds.add(alAp.getId());
        }
        for (int i = 0; i < apartmentIds.size(); i++) {
            if (apartmentIds.get(i).equals(ap.getId())) {
                int index = apartmentIds.indexOf(ap.getId());
                Apartment apartment = apartments.get(index);
                return apartment;
            }
        }
        return null;
    }

    public boolean remove(Apartment ap) {
        Iterator<Apartment> apartmentIterator = apartments.iterator();
        List<String> apartmentIds = new ArrayList<>();
        while (apartmentIterator.hasNext()) {
            Apartment apartment = apartmentIterator.next();
            apartmentIds.add(apartment.getId());
        }
        Iterator<Booking> bookingIterator = bookings.iterator();
        List<Apartment> bookedApartments = new ArrayList<>();
        while (bookingIterator.hasNext()) {
            Booking booking = bookingIterator.next();
            bookedApartments.add(booking.getApartment());
        }
        if (apartmentIds.contains(ap.getId())) {
            int index = apartmentIds.indexOf(ap.getId());
            Apartment apartment = apartments.get(index);
            if (!bookedApartments.contains(apartment)) {
                apartments.remove(apartment);
                return true;
            }
        }
        return false;
    }


    //---- Booking  ----------------------------------------------------------------------------------------------------

    /*public boolean add(Booking b) {
        if (!bookings.contains(b)) {
            Apartment bAp = b.getApartment();
            Person bGuest = b.getGuest();
            Period bPeriod = b.getPeriod();
            if (apartments.contains(bAp) && persons.contains(bGuest) && b.getnPersons() <= bAp.getMaxGuests()) {
                Iterator<Period> occIterator = bAp.getOccupied().iterator();
                if (bAp.getOccupied().size() == 0) {
                    bAp.getOccupied().add(bPeriod);
                    bookings.add(b);
                    return true;
                } else {
                    while (occIterator.hasNext()) {
                        Period apOcc = occIterator.next();
                        if (!apOcc.overlaps(bPeriod)) {
                            bAp.getOccupied().add(bPeriod);
                            bookings.add(b);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
*/
    /*
    public boolean add(Booking b) {
        Iterator<Booking> bookingIterator = bookings.iterator();
        List<String> bookingIds = new ArrayList<>();
        while (bookingIterator.hasNext()) {
            Booking booking = bookingIterator.next();
            bookingIds.add(booking.getId());
        }
        if (!bookingIds.contains(b.getId())) {
            int index = bookingIds.indexOf(b.getId());
            Booking booking = bookings.get(index);
            Apartment bookingApartment = booking.getApartment();
            Person bookingGuest = booking.getGuest();
            Period bookingPeriod = booking.getPeriod();
            Iterator<Period> periodIterator = bookingApartment.getOccupied().iterator();
            List<Period> periods = new ArrayList<>();
            while (periodIterator.hasNext()) {
                Period period = periodIterator.next();
                periods.add(period);
            }
            for (int i = 0; i < periods.size(); i++) {
                if (periods.get(i).overlaps(bookingPeriod))
                    return false;
            }
            if (persons.contains(bookingGuest) && apartments.contains(bookingApartment) && booking.getnPersons() <= bookingApartment.getMaxGuests()) {
                bookings.add(booking);
                return true;
            }
        } return false;
    }
*/

    public boolean add(Booking b) {
        Apartment bookingApartment = b.getApartment();
        Person bookingGuest = b.getGuest();
        Period bookingPeriod = b.getPeriod();
        Iterator<Period> periodIterator = bookingApartment.getOccupied().iterator();
        List<Period> periods = new ArrayList<>();
        while (periodIterator.hasNext()) {
            Period period = periodIterator.next();
            periods.add(period);
        }
        for (int i = 0; i < periods.size(); i++) {
            if (periods.get(i).overlaps(bookingPeriod)) {
                return false;
            }
        }
        Iterator<Booking> bookingIterator = bookings.iterator();
        List<String> bookingIds = new ArrayList<>();
        while (bookingIterator.hasNext()) {
            Booking booking = bookingIterator.next();
            bookingIds.add(booking.getId());
        }
        if (!bookingIds.contains(b.getId())) {
            if (persons.contains(bookingGuest) && apartments.contains(bookingApartment) && b.getnPersons() <= bookingApartment.getMaxGuests()) {
                bookingApartment.getOccupied().add(bookingPeriod);
                bookings.add(b);
                return true;
            }
        }
        return false;
    }

    public boolean remove(Booking b) {
        if (bookings.contains(b)) {
            Apartment bookedApartment = b.getApartment();
            Period bookedPeriod = b.getPeriod();
            bookedApartment.getOccupied().remove(bookedPeriod);
            bookings.remove(b);
            return true;
        }
        return false;
    }


    public Booking find(Booking b) {
        Iterator<Booking> bookingIterator = bookings.iterator();
        while (bookingIterator.hasNext()) {
            Booking nextBooking = bookingIterator.next();
            if (nextBooking.getId() == b.getId()) {
                return nextBooking;
            }
        }
        return null;
    }

    // ---- For UI -----------------

    public List<Apartment> getApartments() {
        return apartments;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

}
