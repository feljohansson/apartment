package abs.model;

import java.util.ArrayList;
import java.util.List;


/*
        An apartment owned by someone and hired by a lodger
 */
public class Apartment {
    private final String id;
    private final Person owner;
    private final int maxGuests;
    private final List<Period> occupied = new ArrayList<>();

    public Apartment(String id, Person owner, int maxGuests) {
        this.id = id;
        this.owner = owner;
        this.maxGuests = maxGuests;
    }

    public Apartment(String id) {
        this.id = id;
        this.owner = null;
        this.maxGuests = 0;
    }

    // TODO a lot
    public String getId() {
        return id;
    }

    public Person getOwner() {
        return owner;
    }

    public int getMaxGuests() {
        return maxGuests;
    }

    public List<Period> getOccupied() {
        return occupied;
    }

    public boolean isFree(Period pd) {
        if (occupied.contains(pd)) {
            return false;
        } return true;
    }

    @Override
    public String toString() {
        return "Apartment{" +
                "id='" + id + '\'' +
                ", owner='" + owner + '\'' +
                ", max guests='" + maxGuests + '\'' +
                '}';
    }


}
