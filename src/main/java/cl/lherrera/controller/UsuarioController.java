package cl.lherrera.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cl.lherrera.conf.model.entity.Usuario;
import cl.lherrera.service.UsuarioService;

@Controller
@RequestMapping(value = "/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService servicio;
    
    @GetMapping
    public String home(
        @ModelAttribute("mensaje") String mensaje, 
        ModelMap mapa
    ) {
        System.out.println("mensaje:" + mensaje);
        if(mensaje != null)
            mapa.put("mensaje", mensaje);
        mapa.put("usuarios", servicio.obtenerTodos());

        return "usuario/home";
    }
    
    @PostMapping("/actualizar")
    public String actualizar(
        ModelMap mapa,
        RedirectAttributes atributos,
        @ModelAttribute Usuario usuario, 
        @RequestParam(name = "imagen", required = false) MultipartFile archivo) {
        if(archivo.isEmpty())
            servicio.actualizar(usuario);
        else
            servicio.actualizar(usuario, archivo);
        
        atributos.addFlashAttribute("mensaje", "Usuario actualizado");
        
        return "redirect:/usuarios";
    }

    @PostMapping
    public String ingresar(
        RedirectAttributes atributos,
        @ModelAttribute Usuario usuario, 
        @RequestParam("imagen") MultipartFile archivo) {
        Usuario Usuariorespuesta = servicio.ingresar(usuario, archivo); 
        
        atributos.addFlashAttribute(
            "mensaje", 
            "Usuario: " 
            + Usuariorespuesta.getCorreo()
            + ", ingresado."
        );

        return "redirect:/usuarios";
    }

    @GetMapping(value = "/eliminar")
    public String eliminar(
        @RequestParam Integer id, 
        RedirectAttributes atributos) {
        Usuario usuario = servicio.buscar(id);
        servicio.eliminar(usuario);
        String mensaje = "Usuario: " + usuario.getCorreo() + " eliminado";
        atributos.addFlashAttribute("mensaje", mensaje);

        return "redirect:/usuarios";
    }
    
}
