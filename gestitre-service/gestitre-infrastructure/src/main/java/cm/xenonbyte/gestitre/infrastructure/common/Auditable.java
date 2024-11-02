package cm.xenonbyte.gestitre.infrastructure.common;

/**
 * @author bamk
 * @version 1.0
 * @since 01/11/2024
 */
public interface Auditable {
    Audit getAudit();
    void setAudit(Audit audit);
}
