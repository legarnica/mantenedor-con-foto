package cl.lherrera.conf.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String nombre;
    private String correo;
    private String contrasenia;
    private String urlImagen;


    /**
     * Método diseñado para poder convertir esta entidad
     * en una cadena de texto, con formáto json.
     * Esto es útil para los js, que tengamos
     * en la vista 
     */
    public String toJson() {
        Usuario aux = new Usuario(id, nombre, correo, contrasenia, urlImagen);
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(aux);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        
        return jsonString;
    }
    
    
    
}
