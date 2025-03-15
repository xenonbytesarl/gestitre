package cm.xenonbyte.gestitre.domain.common.exception;

/**
 * @author bamk
 * @version 1.0
 * @since 10/08/2024
 */
public abstract class BaseDomainTechnicalException extends RuntimeException {

    protected Object[] args;

    protected BaseDomainTechnicalException(String message) {
        super(message);
    }

    protected BaseDomainTechnicalException(String message, Object[] args) {
        super(message);
        this.args = args;
    }

    public Object[] getArgs() {
        return args;
    }
}
