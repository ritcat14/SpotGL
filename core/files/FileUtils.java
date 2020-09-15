package core.files;

import core.GLEngine;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class FileUtils {

    private FileUtils() {}

    public static String loadAsString(String file) {
        StringBuilder result = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String buffer = "";
            while ((buffer = reader.readLine()) != null) {
                result.append(buffer + '\n');
            }
            reader.close();
        } catch (Exception e) {
            System.err.println("Failed to load file: " + file);
            e.printStackTrace();
        }
        return result.toString();
    }

    public static BufferedImage getImage(String fileName) {
        BufferedImage loadedImage = null;
        try {
            loadedImage = ImageIO.read(Objects.requireNonNull(GLEngine.class.getClassLoader().getResourceAsStream(fileName)));
        } catch (NullPointerException e) {
            System.out.println("Failed to find file: " + fileName);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadedImage;
    }

}
