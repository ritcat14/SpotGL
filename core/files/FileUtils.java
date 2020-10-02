package SpotGL.core.files;

import SpotGL.core.GLEngine;
import SpotGL.core.graphics.Texture;
import SpotGL.core.objects.maps.*;
import SpotJava.JavaMain;
import de.matthiasmann.twl.utils.PNGDecoder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.imageio.ImageIO;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.lwjgl.opengl.GL11.*;

public class FileUtils {

    private FileUtils() {}

    public static StringBuilder loadAsString(String file) {
        StringBuilder result = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getFileFromResourceAsStream(file)));
            String buffer = "";
            while ((buffer = reader.readLine()) != null) {
                result.append(buffer).append("\n");
            }
            reader.close();
        } catch (Exception e) {
            System.err.println("Failed to load file: " + file);
            e.printStackTrace();
            System.exit(-1);
        }
        return result;
    }

    public static Texture loadTexture(String fileName) {
        PNGDecoder decoder = null;
        int textureID = -1;
        try {
            decoder = new PNGDecoder(Texture.class.getResourceAsStream(fileName));
            ByteBuffer buf = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
            decoder.decode(buf, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
            buf.flip();

            textureID = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, textureID);
            glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buf);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        } catch (Exception e) {
            System.err.println("Failed to load texture: " + fileName);
            e.printStackTrace();
        }
        return new Texture(textureID);
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

    private static InputStream getFileFromResourceAsStream(String fileName) {

        // The class loader that loaded the class
        ClassLoader classLoader = JavaMain.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }

    }

    public static MapData readMap(String mapFile) throws ParserConfigurationException, SAXException, IOException {
        //Initialize a list of employees
        List<Layer> layers = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(mapFile));
        document.getDocumentElement().normalize();
        NodeList nList = document.getElementsByTagName("layer");

        for (int temp = 0; temp < nList.getLength(); temp++) {

            Node node = nList.item(temp);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                List<Chunk> chunks = new ArrayList<>();

                NodeList dataList = eElement.getElementsByTagName("data");

                for (int dataTemp = 0; dataTemp < dataList.getLength(); dataTemp++) {

                    Node dataNode = dataList.item(dataTemp);

                    if (dataNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element dataElement = (Element) dataNode;
                        NodeList chunkList = dataElement.getElementsByTagName("chunk");

                        for (int chunkTemp = 0; chunkTemp < chunkList.getLength(); chunkTemp++) {

                            Node chunkNode = chunkList.item(chunkTemp);

                            if (chunkNode.getNodeType() == Node.ELEMENT_NODE) {
                                Element chunkElement = (Element) chunkNode;

                                int chunkWidth = Integer.parseInt(chunkElement.getAttribute("width"));
                                int chunkHeight = Integer.parseInt(chunkElement.getAttribute("height"));
                                int[][] chunkData = new int[chunkWidth][chunkHeight];

                                String chunkDataString = chunkNode.getTextContent();

                                int[] chunkDataRaw = new int[chunkWidth * chunkHeight];
                                String[] chunkStringRaw = chunkDataString.split(",");

                                for (int i = 0; i < chunkStringRaw.length - 1; i++) chunkDataRaw[i] =
                                        Integer.parseInt(chunkStringRaw[i].trim());

                                for (int y = 0; y < chunkHeight; y++) {
                                    for (int x = 0; x < chunkWidth; x++) {
                                        chunkData[y][x] = chunkDataRaw[x + y * chunkWidth];
                                    }
                                }

                                chunks.add(new Chunk(chunkTemp, Integer.parseInt(chunkElement.getAttribute("x")),
                                        Integer.parseInt(chunkElement.getAttribute("y")), chunkWidth, chunkHeight, chunkData));
                            }
                        }
                    }
                }

                layers.add(new Layer(Integer.parseInt(eElement.getAttribute("id")), eElement.getAttribute("name"),
                        Integer.parseInt(eElement.getAttribute("width")),
                        Integer.parseInt(eElement.getAttribute("height")), chunks));
            }
        }

        return new MapData(layers, new TileSet());
    }

}
