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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Usuario;

/**
 * Controlador principal para la vista de login (FXML).
 * @author 2dam
 */
public class FXMLDocumentController implements Initializable {

    private static final Logger logger = Logger.getLogger(FXMLDocumentController.class.getName());

    @FXML
    private Button buttonSQL;

    @FXML
    private Button btnFichero;

    @FXML
    private Label lblUsuario;

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtContrasenia;

    @FXML
    private Label labelUsuario;

    @FXML
    private Label labelNombreReal;

    @FXML
    private Label labelApellido;

    @FXML
    private Label labelEmail;

    @FXML
    private Label labelDireccion;

    private Dao dao;

    private Usuario usuario;

    /**
     * Evento botón loginSQL
     * @param event acción del botón
     * @throws LoginError si las credenciales son inválidas o ocurre un error en la BD
     */
    @FXML
    private void loginSQL(ActionEvent event) throws LoginError {
        dao = new DaoImplementacionMySql();
        login();
    }

    /**
     * Evento botón loginFichero
     * @param event acción del botón
     * @throws LoginError si las credenciales son inválidas o ocurre un error en el fichero
     */
    @FXML
    private void loginFichero(ActionEvent event) throws LoginError {
        dao = new DaoImplementacionFichero();
        login();
    }

    /**
     * Método de login
     * @throws LoginError si el login falla
     */
    private void login() throws LoginError {
        String nombre = txtUsuario.getText();
        String password = txtContrasenia.getText();

        Usuario usu = new Usuario();
        usu.setNombre(nombre);
        usu.setPassword(password);

        try {
            Usuario logeado = dao.login(usu);
            mostrarVentanaUsuario(logeado);

            logger.info("Usuario autenticado: " + logeado.getNombre());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Acceso Concedido");
            alert.setHeaderText("Inicio de sesión exitoso");
            alert.setContentText("¡Bienvenido, " + logeado.getNombre() + "!");
            alert.showAndWait();

        } catch (LoginError e) {
            logger.warning("Error de login: " + e.getMessage());

            e.visualizarMen();
        }
    }

    /**
     * Inicializar dao
     * @param url ubicación del archivo FXML
     * @param rb recursos de internacionalización
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dao = new DaoImplementacionMySql();
        logger.info("Aplicación inicializada, DAO configurado a MySQL.");

    }

    /**
     * Método para agregar datos al objeto usuario
     * @param usuario el usuario autenticado
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        labelUsuario.setText(usuario.getNombre());
        labelNombreReal.setText(usuario.getNombreReal());
        labelApellido.setText(usuario.getApellido());
        labelEmail.setText(usuario.getEmail());
        labelDireccion.setText(usuario.getDireccion());
    }

    /**
     * Método para mostrar la ventana del usuario logeado
     * @param logeado el usuario que ha iniciado sesión correctamente
     */
    private void mostrarVentanaUsuario(Usuario logeado) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/VistaUsuario.fxml"));
            loader.setController(this);
            Parent root = loader.load();

            setUsuario(logeado);

            Stage stage = (Stage) txtUsuario.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error al cargar vista de usuario: {0}", ex.getMessage());
        }
    }

     /**
     * Método para cargar previamente datos en el fichero
     * Este método solo se ejecuta la primera vez, cuando no existe el fichero.
     */
    public void precargarUsuarios() {
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

     /**
     * Método para leer usuarios
     * 
     * @return una lista de usuarios. si el fichero no existe u ocurre un error,
     * se devuelve una lista vacía.
     */
    public List<Usuario> leerUsuarios() {
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
