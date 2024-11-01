package cm.xenonbyte.gestitre.domain.company.entity;

import cm.xenonbyte.gestitre.domain.common.entity.BaseEntity;
import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import cm.xenonbyte.gestitre.domain.common.vo.Active;
import cm.xenonbyte.gestitre.domain.common.vo.Name;
import cm.xenonbyte.gestitre.domain.company.vo.CertificateTemplateId;

import java.util.UUID;

/**
 * @author bamk
 * @version 1.0
 * @since 01/11/2024
 */
public class CertificateTemplate extends BaseEntity<CertificateTemplateId> {
    private final Name name;
    private Active active;


    public CertificateTemplate(Name name) {
        this.name = name;
    }

    private CertificateTemplate(Builder builder) {
        setId(builder.id);
        name = builder.name;
        active = builder.active;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Name getName() {
        return name;
    }

    public Active getActive() {
        return active;
    }

    public void validateMandatoryFields() {
        Assert.field("Name", name)
                .notNull();
    }

    public void initializeDefaultValues() {
        setId(new CertificateTemplateId(UUID.randomUUID()));
        active = Active.with(true);
    }

    public static final class Builder {
        private CertificateTemplateId id;
        private Name name;
        private Active active;

        private Builder() {
        }

        public Builder id(CertificateTemplateId val) {
            id = val;
            return this;
        }

        public Builder name(Name val) {
            name = val;
            return this;
        }

        public Builder active(Active val) {
            active = val;
            return this;
        }

        public CertificateTemplate build() {
            return new CertificateTemplate(this);
        }
    }
}
