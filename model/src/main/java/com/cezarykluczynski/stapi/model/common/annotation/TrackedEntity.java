package com.cezarykluczynski.stapi.model.common.annotation;

import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import org.springframework.data.repository.CrudRepository;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface TrackedEntity {

	TrackedEntityType type();

	boolean apiEntity() default true;

	boolean metricsEntity() default false;

	Class<? extends CrudRepository> repository();

	String singularName();

	String pluralName();

}
