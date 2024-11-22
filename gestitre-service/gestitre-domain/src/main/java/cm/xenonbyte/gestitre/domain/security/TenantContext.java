package cm.xenonbyte.gestitre.domain.security;

import cm.xenonbyte.gestitre.domain.common.vo.Name;
import cm.xenonbyte.gestitre.domain.common.vo.Text;

/**
 * @author bamk
 * @version 1.0
 * @since 22/11/2024
 */

public final class TenantContext {

    private static final ThreadLocal<Name> context = new InheritableThreadLocal<>();

    public static Name current() {
        return context.get();
    }

    public static void set(String tenantName) {
        context.set(Name.of(Text.of(tenantName)));
    }

    public static void clear() {
        context.remove();
    }

}
