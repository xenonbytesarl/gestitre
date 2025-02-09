package cm.xenonbyte.gestitre.infrastructure.admin;

/**
 * @author bamk
 * @version 1.0
 * @since 09/02/2025
 */
public enum TimezoneJpa {
    Africa_Brazzaville("Africa/Brazzaville"),
    Africa_Douala("Africa/Douala"),
    Africa_Kinshasa("Africa/Kinshasa"),
    Africa_Libreville("Africa/Libreville"),
    Europe_Paris("Europe/Paris"),
    America_Los_Angeles("America/Los_Angeles");

    private final String name;

    TimezoneJpa(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
