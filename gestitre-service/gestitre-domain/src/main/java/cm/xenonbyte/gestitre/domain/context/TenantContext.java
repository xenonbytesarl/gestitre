package cm.xenonbyte.gestitre.domain.context;


import java.util.UUID;

/**
 * @author bamk
 * @version 1.0
 * @since 22/11/2024
 */

public final class TenantContext {

    private static final ThreadLocal<UUID> context = new InheritableThreadLocal<>();

    public static UUID current() {
        return context.get();
    }

    public static void set(UUID tenantId) {
        context.set(tenantId);
    }

    public static void clear() {
        context.remove();
    }

}
