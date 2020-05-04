package com.example.newsblog.util;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public final class ImageUploadUtil {

    private static final long MAX_UPLOAD_AVATAR_SIZE = 307200;

    /**
     * Метод для конвертации аватара
     *
     * @param avatar MultipartFile который загружает пользователь
     * @return byte[] для сохранения в бд
     * если размер файла больше 300кб возвращаем null
     * если тип файла не является изображением возвращаем null
     * если не возможно прочитать данные из файла возвращаем null
     * @throws IOException
     */
    public static byte[] avatarConvert(MultipartFile avatar) throws IOException {
        String avatarType = imageType(avatar.getContentType());
        if (avatar.getSize() > MAX_UPLOAD_AVATAR_SIZE) {
            return null;
        }
        if (avatarType == null) {
            return null;
        } else {
            return toByteArrayAutoClosable(cropImageSquare(avatar.getBytes()), avatarType);
        }
    }

    /**
     * @param image обрезаное изображение приведенное к квадрату.
     * @param type  тип файла
     * @return byte[] переконвертируем в массив байт BufferedImage
     * @throws IOException
     */
    private static byte[] toByteArrayAutoClosable(BufferedImage image, String type) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(image, type.substring(6), out);
        return out.toByteArray();
    }

    /**
     * @param image картинка которую загружает пользователь
     * @return BufferedImage изображение приведенное к квадратной форме
     * @throws IOException
     */
    private static BufferedImage cropImageSquare(byte[] image) throws IOException {
        // Получаем объект BufferedImage из массива байтов
        InputStream in = new ByteArrayInputStream(image);
        BufferedImage originalImage = ImageIO.read(in);

        // Получаем размеры изображения
        int height = originalImage.getHeight();
        int width = originalImage.getWidth();

        // Проверяем если изображение уже квадратное
        if (height == width) {
            return originalImage;
        }

        // Вычисляем размер квадрата по меньшей стороне
        int squareSize = (Math.min(height, width));

        // Координаты середины изображения
        int xc = width / 2;
        int yc = height / 2;

        // Обрезаем
        BufferedImage croppedImage = originalImage.getSubimage(
                xc - (squareSize / 2), // координата x верхнего левого угла
                yc - (squareSize / 2), // координата у верхнего левого угла
                squareSize,            // ширина
                squareSize             // высота
        );

        return croppedImage;
    }

    /**
     * @param contentType тип загружаемого пользователем файла
     * @return если формат не является изображением возвращем null, если формат является изображением возвращаем его
     */
    private static String imageType(String contentType) {
        if (contentType == null)
            return null;
        if (!contentType.toLowerCase().startsWith("image/")) {
            return null;
        } else return contentType;
    }
}


