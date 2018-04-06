package annotations.sourceAnnotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 *Element marked by @Transcendental means it probably will used or will be removed
 */

@Transcendental
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.ANNOTATION_TYPE, CONSTRUCTOR, FIELD, LOCAL_VARIABLE, METHOD, MODULE, PACKAGE, TYPE
        , TYPE_PARAMETER, TYPE_USE, PARAMETER})
public @interface Transcendental {
}
