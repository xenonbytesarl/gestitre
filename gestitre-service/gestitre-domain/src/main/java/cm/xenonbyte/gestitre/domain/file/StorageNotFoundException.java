package cm.xenonbyte.gestitre.domain.file;

import cm.xenonbyte.gestitre.domain.common.exception.BaseDomainNotFoundException;

/**
 * @author bamk
 * @version 1.0
 * @since 08/10/2024
 */
public class StorageNotFoundException extends BaseDomainNotFoundException {
    public static final String FILE_NOT_FOUND_EXCEPTION = "StorageNotFoundException.1";

    protected StorageNotFoundException(Object[] args) {
        super(FILE_NOT_FOUND_EXCEPTION, args);
    }
}
