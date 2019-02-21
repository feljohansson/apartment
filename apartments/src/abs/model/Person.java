package abs.model;

/*
        A model of a Person (as an owner or as a lodger)

        *** Nothing to do here ***
 */
public class Person {
    private final String id;
    private final String name;
    private String street;
    private String phone;
    private String email;

    public Person(String id, String name, String street, String phone, String email) {
        this.id = id;
        this.name = name;
        this.street = street;
        this.phone = phone;
        this.email = email;
    }

    // Make it simple for users of class
    public Person(String[] data) {
        this(data[0], data[1], data[2], data[3], data[4]);
    }

    // Used for search/delete (by id)
    public Person(String id) {
        this(id, null, null, null, null);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // To get a useful output. By using this we don't need a lot of getters
    // to be able to display id, name, etc in the web pages (NOT used in real life)
    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", street='" + street + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    // Must have to be able to find "by value" in collections
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        } else {
            Person person = (Person) o;
            return id != null ? id.equals(person.id) : person.id == null;
        }
    }

    // If have equals() also must have this (more in other courses)
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
