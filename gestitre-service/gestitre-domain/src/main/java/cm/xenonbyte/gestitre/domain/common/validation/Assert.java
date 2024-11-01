package cm.xenonbyte.gestitre.domain.common.validation;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author bamk
 * @version 1.0
 * @since 13/10/2024
 */
public final class Assert {

    private static final String EMAIL_REGEX = "^[^@]+@[^@]+\\.[^@]+$";

    public static ObjectAssert field(String field, Object value) {
        return new ObjectAssert(field, value);
    }


    public static class ObjectAssert {

        private final String field;
        private final Object value;

        public ObjectAssert(String field, Object value) {
            this.field = field;
            this.value = value;
        }

        public ObjectAssert notNull() {
            if(value == null) {
                throw InvalidFieldBadException.forNullValue(field);
            }
            return this;
        }

        public ObjectAssert notNull(Object value) {
            if(value == null) {
                throw InvalidFieldBadException.forNullValue(field);
            }
            return this;
        }

        public ObjectAssert isOneOf(List<String> values, String target) {
            if(values.stream().noneMatch(val -> val.equals(target))) {
                throw InvalidFieldBadException.forIsOneOfValue(field, String.join(",", values));
            }
            return this;
        }

        public ObjectAssert notEmpty(String target) {
            if(target.isEmpty()) {
                throw InvalidFieldBadException.forEmptyValue(field);
            }
            return this;
        }

        public ObjectAssert notPositive(Double target) {
            if(target <= 0 ) {
                throw InvalidFieldBadException.forPositiveValue(field);
            }
            return this;
        }

        public ObjectAssert notPositive(BigDecimal target) {
            if(target.compareTo(BigDecimal.ZERO) < 0 ) {
                throw InvalidFieldBadException.forPositiveValue(field);
            }
            return this;
        }

        /**
         *
         * @param threshold: min value to be valid
         * @param length: current value to validate
         * @return
         */

        public ObjectAssert minLength(int threshold, int length) {
            if(length < threshold ) {
                throw InvalidFieldBadException.forMinLengthValue(field, threshold);
            }
            return this;
        }

        public ObjectAssert isEmail(String target) {
            if(!target.matches(EMAIL_REGEX)) {
                throw InvalidFieldBadException.forEmailValue(field);
            }
            return this;
        }

        public ObjectAssert notPositive(Long target) {
            if(target < 0 ) {
                throw InvalidFieldBadException.forPositiveValue(field);
            }
            return this;
        }

        public ObjectAssert notNumberValue(String target) {
            try {
                Integer.parseInt(target.replaceAll("\\s", ""));
                return this;
            } catch (NumberFormatException nfe) {
                throw InvalidFieldBadException.forNumberValue(field);
            }
        }

        public ObjectAssert notPositive(Integer target) {
            if(target < 0 ) {
                throw InvalidFieldBadException.forPositiveValue(field);
            }
            return this;
        }
    }

}
