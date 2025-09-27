/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import excepciones.LoginError;
import java.io.IOException;
import java.net.URL;
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
 *
 * @author 2dam
 */
public class FXMLDocumentController implements Initializable {

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

    @FXML
    private void loginSQL(ActionEvent event) throws LoginError {
        dao = new DaoImplementacionMySql();
        login();
    }

    @FXML
    private void loginFichero(ActionEvent event) throws LoginError {
        dao = new DaoImplementacionFichero();
        login();
    }

    private void login() throws LoginError {
        String nombre = txtUsuario.getText();
        String password = txtContrasenia.getText();

        Usuario usu = new Usuario();
        usu.setNombre(nombre);
        usu.setPassword(password);

        try {
            Usuario logeado = dao.login(usu);
            mostrarVentanaUsuario(logeado);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Acceso Concedido");
            alert.setHeaderText("Inicio de sesión exitoso");
            alert.setContentText("¡Bienvenido, " + logeado.getNombre() + "!");
            alert.showAndWait();

        } catch (LoginError e) {
            e.visualizarMen();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dao = new DaoImplementacionMySql();

    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        labelUsuario.setText(usuario.getNombre());
        labelNombreReal.setText(usuario.getNombreReal());
        labelApellido.setText(usuario.getApellido());
        labelEmail.setText(usuario.getEmail());
        labelDireccion.setText(usuario.getDireccion());
    }

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
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
