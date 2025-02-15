package cm.xenonbyte.gestitre.application.admin.dto;

import cm.xenonbyte.gestitre.domain.admin.vo.Timezone;
import cm.xenonbyte.gestitre.domain.admin.vo.TimezoneNameBadException;

import java.util.Arrays;

/**
 * @author bamk
 * @version 1.0
 * @since 09/02/2025
 */
public enum TimezoneView {
    Africa_Brazzaville("Africa/Brazzaville"),
    Africa_Douala("Africa/Douala"),
    Africa_Kinshasa("Africa/Kinshasa"),
    Africa_Libreville("Africa/Libreville"),
    Europe_Paris("Europe/Paris"),
    America_Los_Angeles("America/Los_Angeles");

    private final String name;

    TimezoneView(String name) {
        this.name = name;
    }

    public static Timezone from(String name) {
        return Arrays.stream(Timezone.values())
                .filter(value -> value.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new TimezoneNameBadException(new String[]{name}));
    }

    public String getName() {
        return name;
    }
}
