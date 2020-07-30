package cl.lherrera.conf.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.lherrera.conf.model.entity.Usuario;

public interface UsuarioDao extends JpaRepository<Usuario, Integer>{}
