package cm.xenonbyte.gestitre.domain.file.port.primary;

import cm.xenonbyte.gestitre.domain.common.vo.Filename;
import cm.xenonbyte.gestitre.domain.common.vo.Image;
import cm.xenonbyte.gestitre.domain.common.vo.StorageLocation;
import jakarta.annotation.Nonnull;

import java.io.IOException;

/**
 * @author bamk
 * @version 1.0
 * @since 27/08/2024
 */
public interface StorageManager {
    Filename store(@Nonnull Image image, @Nonnull StorageLocation location);
    String fileToBase64(@Nonnull String filename) throws IOException;
    String mimeType(@Nonnull String filename) throws IOException;
}
