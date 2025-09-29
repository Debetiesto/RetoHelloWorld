/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import excepciones.LoginError;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.Usuario;

/**
 *
 * @author 2dam
 */
public class DaoImplementacionMySql implements Dao {

    private static final Logger logger = Logger.getLogger(DaoImplementacionMySql.class.getName());

    // Atributo para conexion
    private ResourceBundle configFile;
    private String urlBD, userBD, passwordBD;
    // Atributos
    private Connection con;
    private PreparedStatement stmt;
    // Sentencias SQL

    final String LOGIN = "SELECT * FROM USUARIO WHERE nom = ? AND pass = ?";

    /**
     * Constructor DaoImplementacion
     */
    public DaoImplementacionMySql() {
        this.configFile = ResourceBundle.getBundle("modelo.configClase");
        this.urlBD = this.configFile.getString("Conn");
        this.userBD = this.configFile.getString("DBUser");
        this.passwordBD = this.configFile.getString("DBPass");
    }

    /**
     * Método para conectar a la base de datos
     */
    private void openConnection() {
        try {
            con = DriverManager.getConnection(urlBD, this.userBD, this.passwordBD);
            logger.info("Conexión abierta con la base de datos.");

            /*
			 * con = DriverManager.getConnection(
			 * "jdbc:mysql://localhost:3306/tienda_brico?serverTimezone=Europe/Madrid&useSSL=false",
			 * "root", "abcd*1234");
             */
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al abrir conexión con BD: {0}", e.getMessage());
        }
    }

    /**
     * Método para cerrar la conexión a la base de datos
     */
    private void closeConnection() {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
            logger.info("Conexión cerrada correctamente.");

        } catch (SQLException e) {
            logger.log(Level.WARNING, "Error al cerrar conexión: {0}", e.getMessage());
        }
    }

    /**
     * Método para teminar el proceso ResultSet
     * @param rs 
     */
    private void closeResult(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                logger.log(Level.WARNING, "Error al cerrar ResultSet: {0}", e.getMessage());
            }
        }
    }

    /**
     * Método de login del usuario
     * @param usu usuario con las credenciales a validar
     * @return un objeto de usuario o null si no lo encuentra
     * @throws LoginError si el usuario/contraseña son incorrectos
     *         o si ocurre un error SQL durante el proceso
     */
    @Override
    public Usuario login(Usuario usu) throws LoginError {
        ResultSet rs = null;
        openConnection();
        Usuario usuarioAutenticado = null;

        try {
            stmt = con.prepareStatement(LOGIN);

            stmt.setString(1, usu.getNombre());
            stmt.setString(2, usu.getPassword());

            rs = stmt.executeQuery();

            if (!rs.next()) {
                logger.warning("Login fallido (SQL) para usuario: " + usu.getNombre());

                throw new LoginError("Usuario o password incorrecto");
            } else {

                usuarioAutenticado = new Usuario();
                usuarioAutenticado.setIdU(rs.getInt("idU"));
                usuarioAutenticado.setNombre(rs.getString("nom"));
                usuarioAutenticado.setPassword(rs.getString("pass"));
                usuarioAutenticado.setNombreReal(rs.getString("nombre_real"));
                usuarioAutenticado.setApellido(rs.getString("apellido"));
                usuarioAutenticado.setEmail(rs.getString("email"));
                usuarioAutenticado.setDireccion(rs.getString("direccion"));
                logger.info("Login exitoso (SQL) para usuario: " + usu.getNombre());

            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error SQL durante login: {0}", e.getMessage());

            throw new LoginError("Error con el SQL");
        } finally {
            closeResult(rs);
            closeConnection();
        }
        return usuarioAutenticado;
    }
}
