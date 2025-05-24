package co.edu.unbosque;

import co.edu.unbosque.view.VistaSimulacion;
import co.edu.unbosque.controller.ControladorSimulacion;
import javax.swing.SwingUtilities;

/**
 * Clase principal para iniciar la simulación de tráfico.
 * @author Adrian Camilo Morales S.
 * @version 1.0
 */
public class SimulacionTrafico {
    /**
     * Método principal que inicia la aplicación.
     * @param args argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VistaSimulacion vista = new VistaSimulacion();
            ControladorSimulacion controlador = new ControladorSimulacion(vista);

            vista.setVisible(true);
        });
    }
}
