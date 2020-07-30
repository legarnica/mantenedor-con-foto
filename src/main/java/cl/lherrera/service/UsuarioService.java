package cl.lherrera.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cl.lherrera.conf.model.dao.UsuarioDao;
import cl.lherrera.conf.model.entity.Usuario;
import cl.lherrera.service.utils.UtileriaDeArchivos;

@Service
public class UsuarioService {
    private final Logger logger = LoggerFactory.getLogger(UsuarioService.class);
    
    @Autowired
    private UsuarioDao dao;
    
    @Autowired
    private UtileriaDeArchivos archivos;
    
    public List<Usuario> obtenerTodos(){
        return dao.findAll();
    }
    
    public Usuario ingresar(Usuario usuario, MultipartFile archivo) {
        logger.info("Ingresando al usuario: " + usuario.toString());
        String nombreArchivo = archivos.subirArchivo(archivo);
        usuario.setUrlImagen(nombreArchivo);
        usuario.setId(null);
        return dao.save(usuario);
    }
    
    public Usuario buscar(Integer id) {
        return dao.findById(id).orElse(null);    
    }
    
    public Usuario eliminar(Usuario usuario) {
        String nombreArchivo = usuario.getUrlImagen();
        boolean Archivoeliminado = archivos.eliminarArchivoPorNombre(nombreArchivo);

        if(!Archivoeliminado) {
            logger.error("El archivo: " + nombreArchivo + " no pudo ser eliminado.");
        }
        
        dao.delete(usuario);
        
        return usuario;
    }

    public Usuario actualizar(Usuario usuario) {
        return dao.save(usuario);
    }

    public Usuario actualizar(Usuario usuario, MultipartFile archivo) {
        Usuario usuarioAnterior = dao.findById(usuario.getId()).orElse(null);
        // eliminamos la imagen anterior
        archivos.eliminarArchivoPorNombre(usuarioAnterior.getUrlImagen());
        // subimos la nueva
        String nombreArchivo = archivos.subirArchivo(archivo);
        // actualizamos el registro en la base de datos
        usuario.setUrlImagen(nombreArchivo);

        return dao.save(usuario);
    }
    
    
}
