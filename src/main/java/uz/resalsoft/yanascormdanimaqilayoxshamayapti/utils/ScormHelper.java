package uz.resalsoft.yanascormdanimaqilayoxshamayapti.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Component
public class ScormHelper {


    private static final String SCORM_STORAGE_PATH = "scorm_packages/";


    public static String generateCourseId() {
        return UUID.randomUUID().toString();
    }


    public  String getHrefFileName(String courseId) {
        try {
            // Create the path to the manifest file
            Path manifestPath = Paths.get(SCORM_STORAGE_PATH, courseId, "imsmanifest.xml").toAbsolutePath().normalize();
            File manifestFile = manifestPath.toFile();

            // Check if the manifest file exists
            if (!manifestFile.exists()) {
                System.err.println("Manifest file does not exist: " + manifestFile.getAbsolutePath());
                return null;
            }

            // Get the href attribute from the manifest
            String href = getHrefFromManifest(manifestFile);

            // Return the href if found
            if (href != null && !href.isEmpty()) {
                return href;
            } else {
                System.err.println("No href found in the manifest.");
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private  String getHrefFromManifest(File manifestFile) {
        try {
            // Load and parse the XML file
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(manifestFile);

            // Get the root element
            Element root = document.getDocumentElement();

            // Try to find resource elements using SCORM 1.2 and SCORM 2004 namespaces
            NodeList resources = root.getElementsByTagNameNS("http://www.imsglobal.org/xsd/imscp_v1p1", "resource");
            if (resources.getLength() == 0) {
                resources = root.getElementsByTagNameNS("http://www.imsproject.org/xsd/imscp_rootv1p1p2", "resource");
            }

            // Retrieve the href attribute from the first resource element found
            if (resources.getLength() > 0) {
                Element resourceElement = (Element) resources.item(0);
                return resourceElement.getAttribute("href");
            } else {
                System.err.println("No resource elements found in the manifest.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


 /*   public static String getHrefFileName(String courseId) {
        try {
            // Manifest faylining yo'lini yaratish
            Path manifestPath = Paths.get(SCORM_STORAGE_PATH, courseId, "imsmanifest.xml").toAbsolutePath().normalize();
            File manifestFile = manifestPath.toFile();

            // Manifestdan href atributini olish
            String href = getHrefFromManifest(manifestFile);

            // Agar href topilsa, faqat fayl nomini qaytarish
            if (href != null && !href.isEmpty()) {
                return href;
            } else {
                // Agar href topilmasa, 404 qaytarish
                return null;
            }

        } catch (Exception e) {
            return null;
        }
    }

    private static String getHrefFromManifest(File manifestFile) {
        try {
            // XML faylni yuklash va pars qilish
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true); // Namespace-larni hisobga olish
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(manifestFile);

            // Root elementdan `resource` elementlarini qidirish
            Element root = document.getDocumentElement();

            // Namespace bilan `resource` elementlarini olish
            NodeList resources = root.getElementsByTagNameNS("http://www.imsproject.org/xsd/imscp_rootv1p1p2", "resource");

            // Birinchi `resource` elementining `href` atributini olish
            if (resources.getLength() > 0) {
                Element resourceElement = (Element) resources.item(0);
                return resourceElement.getAttribute("href");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
*/


    // Method to unzip the file and save the contents
    public static void unzip(InputStream zipFile, String destDir) throws IOException {
        try (ZipInputStream zis = new ZipInputStream(zipFile)) {
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                File newFile = new File(destDir, zipEntry.getName());
                if (zipEntry.isDirectory()) {
                    newFile.mkdirs();
                } else {
                    // Ensure the parent directories exist
                    new File(newFile.getParent()).mkdirs();

                    // Write file content
                    try (FileOutputStream fos = new FileOutputStream(newFile)) {
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                    }
                }
                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
        }
    }







}
