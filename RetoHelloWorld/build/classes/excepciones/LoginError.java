package excepciones;

import javafx.scene.control.Alert;
import javax.swing.JOptionPane;

public class LoginError extends Exception {

    private static final long serialVersionUID = 1L;
    private String mensaje;

    public LoginError(String mensaje) {
        this.mensaje = mensaje;
    }

    public void visualizarMen() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Acceso Denegado");
        alert.setHeaderText("Error de autenticación");
        alert.setContentText(this.getMessage());
        alert.showAndWait();
    }
}
