/* @author luisherrera
 * La siguiente clase, nos sirve de utilería
 * para poder administrar archivos en
 * el direcctorio static.
 * 
 */
package cl.lherrera.service.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class UtileriaDeArchivos {
    private final Logger logger = LoggerFactory.getLogger(UtileriaDeArchivos.class);
    private static final String IMG_PATH = "src/main/resources/static";

    /**
     * indicamos un nombre único para cada archivo
     * que subamos. 
     */
    private String nombrarArchivo(MultipartFile archivo) {
        return UUID.randomUUID() + "_" + archivo.getOriginalFilename();
    }

    /*
     * sube el archivo a la carpeta static, retornando
     * el nombre del archivo subido, para poder
     * cargarlo en una base de datos.
     */
    public String subirArchivo(MultipartFile archivo) {
        String nombreUnico = nombrarArchivo(archivo);
        String imgagenConRuta = IMG_PATH + File.separator + nombreUnico;

        // ruta completa del archivo
        Path path = Paths.get(imgagenConRuta);
        try {
            byte[] fileBytes = archivo.getBytes();
            Files.write(path, fileBytes);
            logger.info("imágen guardada: " + imgagenConRuta);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        return nombreUnico;
    }

    /**
     * a partir del nombre de archivo en la base de datos
     * se eliminará del directorio en IMG_PATH
     */
    public boolean eliminarArchivoPorNombre(String nombre) {
        File archivo = new File(IMG_PATH + File.separator + nombre);
        return archivo.delete();
    }

}
