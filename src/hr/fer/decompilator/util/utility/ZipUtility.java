package hr.fer.decompilator.util.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipUtility {
    public static void unzip(String zipFile, String outputDir) {
        byte[] buffer = new byte[1024];
        try {
            ZipInputStream zis = new ZipInputStream(new FileInputStream(new File(zipFile)));
            ZipEntry zipEntry = zis.getNextEntry();
            while(zipEntry != null) {
                String fileName = zipEntry.getName();
                File file = new File(outputDir + "/" + fileName);

                new File(file.getParent()).mkdirs();

                FileOutputStream fos = new FileOutputStream(file);

                int len;
                while((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }

                fos.close();
                zipEntry = zis.getNextEntry();
            }

            zis.closeEntry();
            zis.close();
        } catch (Exception e) {
            System.out.println("Error while extracting zip file, exiting ...");
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}
