package cm.xenonbyte.gestitre.domain.file;


import cm.xenonbyte.gestitre.domain.common.annotation.DomainService;
import cm.xenonbyte.gestitre.domain.common.vo.Filename;
import cm.xenonbyte.gestitre.domain.common.vo.Image;
import cm.xenonbyte.gestitre.domain.common.vo.StorageLocation;
import cm.xenonbyte.gestitre.domain.common.vo.Text;
import cm.xenonbyte.gestitre.domain.file.port.primary.StorageManager;
import jakarta.annotation.Nonnull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;

/**
 * @author bamk
 * @version 1.0
 * @since 27/08/2024
 */
@DomainService
public final class StorageManagerDomainService implements StorageManager {

    @Override
    public Filename store(@Nonnull Image image, @Nonnull StorageLocation location) {

        try {
            Path destinationFile = Paths.get(image.name().value()).normalize().toAbsolutePath();
            if(!Files.exists(destinationFile.getParent())) {
                Files.createDirectories(destinationFile.getParent());
            }
            try(InputStream inputStream = image.content()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }
            return Filename.of(Text.of(destinationFile.toString()));
        } catch (IOException exception) {
            throw new StorageBadException(new String[]{image.name().value()});
        }
    }

    @Override
    public String fileToBase64(@Nonnull String filename) throws IOException {
        File file = new File(filename);
        if(file.exists()) {
            byte[] fileData;
            try (FileInputStream inputStream = new FileInputStream(file)) {
                fileData = new byte[(int) file.length()];
                inputStream.read(fileData);
            }
            return Base64.getEncoder().encodeToString(fileData);
        } else {
            return null;
        }
    }

    @Override
    public String mimeType(@Nonnull String filename) throws IOException {
        return filename == null || filename.isEmpty()? null: Paths.get(filename).toUri().toURL().openConnection().getContentType();
    }
}
