package web_acc.service.image;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import web_acc.dto.ImageDTO;
import web_acc.entity.CustomUser;
import web_acc.entity.Image;
import web_acc.repository.ImageRepository;

import java.io.IOException;

@Service
public class ImageService {
    private final ImageRepository imageRepository;
    private final CompressingService compressingService;
    public static final Logger logger = LogManager.getLogger(ImageService.class);

    public ImageService(ImageRepository imageRepository, CompressingService compressingService) {
        this.imageRepository = imageRepository;
        this.compressingService = compressingService;
    }

    public Image saveImage(ImageDTO imageDto, CustomUser customUser) throws IOException {
        MultipartFile imageFile = imageDto.getImage();

        String imageExtension = imageFile.getContentType();
        String originalFileName = imageFile.getOriginalFilename();

        byte[] imageBytes = imageFile.getBytes();
        byte[] compressedImageBytes = compressingService.compressJPEG(imageBytes);

        Image image = Image.builder()
                .customUser(customUser)
                .bytes(compressedImageBytes)
                .name(originalFileName)
                .extension(imageExtension).build();

        return imageRepository.save(image);
    }

    public Image getImage(CustomUser customUser) throws IOException {
        Image image = imageRepository.findByCustomUserUuid(customUser.getUuid())
                .orElseThrow(() -> new UsernameNotFoundException("Image by this uuid not found"));

        byte[] decompressedImageBytes = compressingService.decompressJPEG(image.getBytes());
        image.setBytes(decompressedImageBytes);

        return image;
    }


}
