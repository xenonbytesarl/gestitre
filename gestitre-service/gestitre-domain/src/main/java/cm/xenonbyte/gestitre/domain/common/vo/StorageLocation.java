package cm.xenonbyte.gestitre.domain.common.vo;

import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import jakarta.annotation.Nonnull;

import java.util.Objects;
import static cm.xenonbyte.gestitre.domain.common.constant.CommonConstant.SLASH;

/**
 * @author bamk
 * @version 1.0
 * @since 27/08/2024
 */
public record StorageLocation(Text path) {

    public StorageLocation(@Nonnull Text path) {
        this.path = Objects.requireNonNull(path);
    }

    public static StorageLocation of(@Nonnull Text location) {
        Assert.field("Storage location", location)
                .notNull()
                .notNull(location.value())
                .notEmpty(location.value());
        return new StorageLocation(location);
    }

    public static StorageLocation computeStoragePtah(String root, String location) {
        return new StorageLocation(Text.of(root).concat(SLASH).concat(location));
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        StorageLocation that = (StorageLocation) object;
        return Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(path);
    }
}
