package cm.xenonbyte.gestitre.infrastructure.common.annotation;

/**
 * @author bamk
 * @version 1.0
 * @since 28/11/2024
 */

import jakarta.interceptor.InterceptorBinding;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface TenantInterceptorBinding {
}
