package de.shop.util.interceptor;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.interceptor.InterceptorBinding;


@InterceptorBinding
@Target({ TYPE, METHOD })
@Retention(RUNTIME)
@Documented
public @interface Log {
}
