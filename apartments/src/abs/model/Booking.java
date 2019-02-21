package abs.model;


/*
    A Booking of an apartment for some period by some lodger
 */
public class Booking {

    private final String id;
    private final Period period;
    private final Apartment apartment;
    private final Person guest;
    private final int nPersons;

    public Booking(String id, Person guest, Apartment apartment, int nPersons, Period period) {
        this.id = id;
        this.period = period;
        this.apartment = apartment;
        this.guest = guest;
        this.nPersons = nPersons;
    }

    public Booking(String id) {
        this.id = id;
        this.period = null;
        this.apartment = null;
        this.guest = null;
        this.nPersons = 0;
    }

    // TODO a lot
    public  String getId() {
        return id;
    }

    public Period getPeriod() {
        return period;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public Person getGuest() {
        return guest;
    }

    public int getnPersons() {
        return nPersons;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id='" + id + '\'' +
                ", period='" + period + '\'' +
                ", apartment='" + apartment.getId() + '\'' +
                ", guest='" + guest.getId() + '\'' +
                ", nPersons='" + nPersons + '\'' +
                '}';
    }
}
