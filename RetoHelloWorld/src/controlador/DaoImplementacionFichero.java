/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import excepciones.LoginError;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Usuario;

/**
 * Implementación de la interfaz Dao con fichero.
 * 
 * @author 2dam
 */
public class DaoImplementacionFichero implements Dao, Serializable {

    private static final Logger logger = Logger.getLogger(DaoImplementacionFichero.class.getName());
    private FXMLDocumentController controlador = new FXMLDocumentController();
    /**
     * Comprueba si existe el fichero,
     * en caso contrario, lo crea y precarga usuarios de ejemplo.
     */
    public DaoImplementacionFichero() {

        File f = new File("usuarios.dat");
        if (!f.exists()) {
            logger.info("Archivo usuarios.dat no existe. Precargando usuarios de ejemplo...");

            controlador.precargarUsuarios();
        }
    }

    /**
     * Método de login del fichero
     * 
     * @param usu usuario con nombre y contraseña a comprobar
     * @return un objecto autenticado si las credenciales son correctas
     * @throws LoginError si el usuario no existe o las credenciales no coinciden
     */
    @Override
    public Usuario login(Usuario usu) throws LoginError {
        List<Usuario> usuarios = controlador.leerUsuarios();
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario u = usuarios.get(i);
            if (u.getNombre().equals(usu.getNombre()) && u.getPassword().equals(usu.getPassword())) {
                logger.info("Login exitoso para usuario (fichero): " + u.getNombre());

                return u;
            }
        }
        logger.warning("Login fallido para usuario (fichero): " + usu.getNombre());

        throw new LoginError("Usuario o contraseña incorrectos");
    }

 

}
