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
 *
 * @author 2dam
 */
public class DaoImplementacionFichero implements Dao, Serializable {

    private static final Logger logger = Logger.getLogger(DaoImplementacionFichero.class.getName());

    public DaoImplementacionFichero() {

        File f = new File("usuarios.dat");
        if (!f.exists()) {
            logger.info("Archivo usuarios.dat no existe. Precargando usuarios de ejemplo...");

            precargarUsuarios();
        }
    }

    private void precargarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(new Usuario(1, "Miguel", "1234", "Miguel Ángel", "López", "miguel.lopez@mail.com", "Calle Sol 12, Ciudad A"));
        usuarios.add(new Usuario(2, "Laura", "1234", "Laura Isabel", "García", "laura.garcia@mail.com", "Av. Luna 34, Ciudad B"));
        usuarios.add(new Usuario(3, "Pedro", "1234", "Pedro Antonio", "Martínez", "pedro.martinez@mail.com", "Calle Estrella 56, Ciudad C"));
        usuarios.add(new Usuario(4, "Sofía", "1234", "Sofía Fernández", "Fernández", "sofia.fernandez@mail.com", "Plaza Mayor 78, Ciudad D"));

        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream("usuarios.dat"));
            oos.writeObject(usuarios);
            logger.info("Usuarios de ejemplo precargados correctamente.");

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error al precargar usuarios: {0}", e.getMessage());
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Usuario login(Usuario usu) throws LoginError {
        List<Usuario> usuarios = leerUsuarios();
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

    private List<Usuario> leerUsuarios() {
        File f = new File("usuarios.dat");
        if (!f.exists()) {
            logger.warning("Intento de leer usuarios pero el archivo usuarios.dat no existe.");

            return new ArrayList<>();
        }

        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream("usuarios.dat"));
            return (List<Usuario>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error al leer usuarios del archivo: {0}", e.getMessage());
            return new ArrayList<>();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
