package cm.xenonbyte.gestitre.domain.context;

import cm.xenonbyte.gestitre.domain.admin.vo.Timezone;

/**
 * @author bamk
 * @version 1.0
 * @since 09/02/2025
 */
public final class TimezoneContext {
    private static final ThreadLocal<Timezone> context = new InheritableThreadLocal<>();

    public static Timezone current() {
        return context.get();
    }

    public static void set(String timezone) {
        context.set(Timezone.from(timezone));
    }

    public static void clear() {
        context.remove();
    }
}
