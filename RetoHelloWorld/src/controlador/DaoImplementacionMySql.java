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
import javax.swing.JOptionPane;
import modelo.Usuario;

/**
 *
 * @author 2dam
 */
public class DaoImplementacionMySql implements Dao {

    // Atributo para conexion
    private ResourceBundle configFile;
    private String urlBD, userBD, passwordBD;
    // Atributos
    private Connection con;
    private PreparedStatement stmt;
    // Sentencias SQL

    final String LOGIN = "SELECT * FROM USUARIO WHERE nom = ? AND pass = ?";

    public DaoImplementacionMySql() {
        this.configFile = ResourceBundle.getBundle("modelo.configClase");
        this.urlBD = this.configFile.getString("Conn");
        this.userBD = this.configFile.getString("DBUser");
        this.passwordBD = this.configFile.getString("DBPass");
    }

    private void openConnection() {
        try {
            con = DriverManager.getConnection(urlBD, this.userBD, this.passwordBD);
            /*
			 * con = DriverManager.getConnection(
			 * "jdbc:mysql://localhost:3306/tienda_brico?serverTimezone=Europe/Madrid&useSSL=false",
			 * "root", "abcd*1234");
             */
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void closeConnection() {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void closeResult(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

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

            }
        } catch (SQLException e) {
            throw new LoginError("Error con el SQL");
        } finally {
            closeResult(rs);
            closeConnection();
        }
        return usuarioAutenticado;
    }
}
