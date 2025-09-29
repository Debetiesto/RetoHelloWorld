package excepciones;

import javafx.scene.control.Alert;
import javax.swing.JOptionPane;

/**
 * Se lanza cuando un usuario intenta autenticarse con credenciales inválidas 
 * o cuando ocurre algún fallo durante la autenticación.
 * 
 * @author 2dam
 */
public class LoginError extends Exception {

    private static final long serialVersionUID = 1L;
    private String mensaje;

    /**
     * Constructor de la excepción LoginError
     * 
     * @param mensaje descripción del error de login
     */
    public LoginError(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * Muestra un cuadro de diálogo de error utilizando JavaFX
     * con la información del mensaje asociado a esta excepción.
     */
    public void visualizarMen() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Acceso Denegado");
        alert.setHeaderText("Error de autenticación");
        alert.setContentText(this.getMessage());
        alert.showAndWait();
    }
}
