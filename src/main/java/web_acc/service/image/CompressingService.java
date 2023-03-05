package web_acc.service.image;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import web_acc.entity.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class CompressingService {
    public static final Logger logger = LogManager.getLogger(CompressingService.class);
    public byte[] compressJPEG(byte[] input) throws IOException {
        ByteArrayOutputStream byteArrOutputStream = new ByteArrayOutputStream();
        BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(input));
        ImageIO.write(bufferedImage,"jpg",byteArrOutputStream);

        //get the compressed image bytes
        byte[] compressedImages = byteArrOutputStream.toByteArray();

        //calculate the compression ratio
        double compressionRatio = (double) compressedImages.length / input.length;
        logger.info("Compression ratio: " + compressionRatio);

        return compressedImages;
    }

    public byte[] decompressJPEG(byte[] input) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(input);
        BufferedImage decompressedImage = ImageIO.read(byteArrayInputStream);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(decompressedImage, "jpeg", byteArrayOutputStream);

        return byteArrayOutputStream.toByteArray();
    }
}
