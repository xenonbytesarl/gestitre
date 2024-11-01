package cm.xenonbyte.gestitre.domain.common.vo;

import cm.xenonbyte.gestitre.domain.common.validation.Assert;
import jakarta.annotation.Nonnull;

import java.io.InputStream;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static cm.xenonbyte.gestitre.domain.common.constant.CommonConstant.EMPTY;
import static cm.xenonbyte.gestitre.domain.common.constant.CommonConstant.PLUS;
import static cm.xenonbyte.gestitre.domain.common.constant.CommonConstant.POINT;
import static cm.xenonbyte.gestitre.domain.common.constant.CommonConstant.SLASH;
import static cm.xenonbyte.gestitre.domain.common.constant.CommonConstant.UNDERSCORE;


/**
 * @author bamk
 * @version 1.0
 * @since 20/08/2024
 */
public record Image(Text name, InputStream content) {

    public Image(@Nonnull Text name, @Nonnull InputStream content) {
        this.name = Objects.requireNonNull(name);
        this.content = Objects.requireNonNull(content);
    }

    public static Image with(@Nonnull Text name, InputStream content) {
        Assert.field("Image filename", name)
                .notNull()
                .notNull(name.value())
                .notEmpty(name.value());

        Assert.field("Image content", content)
                .notNull();
        return new Image(name, content);
    }

    public Image computeImageName(String rootTypePath) {
        Text computedFileName = StorageLocation.computeStoragePtah(rootTypePath).path()
                .concat(SLASH)
                .concat(
                        ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSSZ"))
                                .replace(PLUS, EMPTY)
                                .replace(POINT, EMPTY))
                .concat(UNDERSCORE)
                .concat(name.value());
        return Image.with(computedFileName, content);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Image image = (Image) object;
        return Objects.equals(name, image.name) && Objects.equals(content, image.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, content);
    }
}
