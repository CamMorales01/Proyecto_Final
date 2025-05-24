package co.edu.unbosque.view;

import co.edu.unbosque.model.*;
import co.edu.unbosque.controller.ControladorSimulacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Clase que implementa la interfaz gráfica de usuario para la simulación de tráfico.
 * Extiende de JFrame y proporciona una representación visual del sistema de tráfico
 * con controles interactivos.
 */
public class VistaSimulacion extends JFrame{

    private JPanel panelGrid;
    private JLabel labelTurno;
    private JLabel labelVehiculos;
    private JLabel labelSemaforos;
    private JButton btnIniciar;
    private JButton btnPausar;
    private JButton btnReset;
    private JTextField fieldFilas;
    private JTextField fieldColumnas;
    private Timer timer;
    private ControladorSimulacion controlador;

    /**
     * Constructor de la clase VistaSimulacion.
     * Inicializa la interfaz gráfica y los componentes necesarios.
     */
    public VistaSimulacion() {
        initComponents();
    }

    /**
     * Método que inicializa los componentes de la interfaz gráfica.
     */
    private void initComponents() {
        setTitle("Simulación de Tráfico Vehicular");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelControles = new JPanel(new FlowLayout());

        panelControles.add(new JLabel("Filas:"));
        fieldFilas = new JTextField("10", 5);
        panelControles.add(fieldFilas);

        panelControles.add(new JLabel("Columnas:"));
        fieldColumnas = new JTextField("10", 5);
        panelControles.add(fieldColumnas);

        btnIniciar = new JButton("Iniciar");
        btnPausar = new JButton("Pausar");
        btnReset = new JButton("Reset");

        panelControles.add(btnIniciar);
        panelControles.add(btnPausar);
        panelControles.add(btnReset);

        add(panelControles, BorderLayout.NORTH);

        JPanel panelInfo = new JPanel(new FlowLayout());
        labelTurno = new JLabel("Turno: 0");
        labelVehiculos = new JLabel("Vehículos: 0");
        labelSemaforos = new JLabel("Semáforos: 0");

        panelInfo.add(labelTurno);
        panelInfo.add(new JLabel(" | "));
        panelInfo.add(labelVehiculos);
        panelInfo.add(new JLabel(" | "));
        panelInfo.add(labelSemaforos);

        add(panelInfo, BorderLayout.SOUTH);

        //Panel principal
        panelGrid = new JPanel();
        panelGrid.setBackground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(panelGrid);
        scrollPane.setPreferredSize(new Dimension(800, 600));
        add(scrollPane, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Método que establece el controlador de la simulación.
     * @param controlador Controlador de la simulación
     */
    public void setControlador(ControladorSimulacion controlador) {
        this.controlador = controlador;

        btnIniciar.addActionListener(e -> {
            if (controlador != null) {
                controlador.iniciarSimulacion();
            }
        });

        btnPausar.addActionListener(e -> {
            if (controlador != null) {
                controlador.pausarSimulacion();
            }
        });

        btnReset.addActionListener(e -> {
            if (controlador != null) {
                controlador.resetearSimulacion();
            }
        });
    }

    /**
     * Método que actualiza la vista de la simulación con la información de la ciudad.
     * @param ciudad Ciudad a mostrar en la vista
     */
    public void actualizarVista(Ciudad ciudad) {
        SwingUtilities.invokeLater(() -> {

            labelTurno.setText("Turno: " + ciudad.getTurnoActual());
            labelVehiculos.setText("Vehículos: " + ciudad.getVehiculos().size());
            labelSemaforos.setText("Semáforos: " + ciudad.getSemaforos().size());

            actualizarGrid(ciudad);
        });
    }

    /**
     * Método que actualiza el grid de la ciudad en la vista.
     * @param ciudad Ciudad a mostrar en el grid
     */
    private void actualizarGrid(Ciudad ciudad) {
        panelGrid.removeAll();
        panelGrid.setLayout(new GridLayout(ciudad.getFilas(), ciudad.getColumnas(), 2, 2));
        panelGrid.setBackground(Color.WHITE);

        Calle[][] grid = ciudad.getGrid();

        for (int i = 0; i < ciudad.getFilas(); i++) {
            for (int j = 0; j < ciudad.getColumnas(); j++) {
                JPanel celda = crearCeldaCiudad(grid[i][j], i, j);
                panelGrid.add(celda);
            }
        }

        panelGrid.revalidate();
        panelGrid.repaint();
    }

    /**
     * Método que crea una celda de la ciudad en el grid.
     * @param calle Calle a mostrar en la celda
     * @param fila Fila de la celda
     * @param columna Columna de la celda
     * @return JPanel representando la celda de la ciudad
     */
    private JPanel crearCeldaCiudad(Calle calle, int fila, int columna) {
        JPanel celda = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int width = getWidth();
                int height = getHeight();
                boolean esInterseccion = (fila % 3 == 0 && columna % 3 == 0);
                boolean esCalleHorizontal = fila % 3 == 0;
                boolean esCalleVertical = columna % 3 == 0;
                boolean esCalle = esCalleHorizontal || esCalleVertical;

                if (esCalle) {
                    g2d.setColor(Color.WHITE);
                    g2d.fillRect(0, 0, width, height);
                    g2d.setColor(Color.LIGHT_GRAY);
                    g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{3}, 0));

                    Direccion direccion = calle.getDireccionFlujo();
                    switch (direccion) {
                        case ESTE:
                            g2d.drawLine(5, height/2, width-5, height/2);

                            g2d.drawLine(width-10, height/2-3, width-5, height/2);
                            g2d.drawLine(width-10, height/2+3, width-5, height/2);
                            break;
                        case OESTE:
                            g2d.drawLine(5, height/2, width-5, height/2);

                            g2d.drawLine(10, height/2-3, 5, height/2);
                            g2d.drawLine(10, height/2+3, 5, height/2);
                            break;
                        case NORTE:
                            g2d.drawLine(width/2, 5, width/2, height-5);

                            g2d.drawLine(width/2-3, 10, width/2, 5);
                            g2d.drawLine(width/2+3, 10, width/2, 5);
                            break;
                        case SUR:
                            g2d.drawLine(width/2, 5, width/2, height-5);

                            g2d.drawLine(width/2-3, height-10, width/2, height-5);
                            g2d.drawLine(width/2+3, height-10, width/2, height-5);
                            break;
                    }

                    //Dibujar semáforo
                    if (calle.tieneSemaforo() && esInterseccion) {
                        Color colorSemaforo = calle.getSemaforo().getEstado() == EstadoSemaforo.VERDE
                                ? Color.GREEN : Color.RED;
                        g2d.setColor(colorSemaforo);
                        g2d.fillRect(width/2-4, height/2-4, 8, 8);
                        g2d.setColor(Color.BLACK);
                        g2d.drawRect(width/2-4, height/2-4, 8, 8);
                    }

                    //Dibujar vehículos
                    if (!calle.getVehiculos().isEmpty()) {
                        int numVehiculos = Math.min(calle.getVehiculos().size(), 3);
                        for (int i = 0; i < numVehiculos; i++) {
                            g2d.setColor(Color.BLACK);
                            int offsetX = (i * 8) - (numVehiculos * 4) + width/2;
                            int offsetY = height/2 - 2;

                            g2d.fillRect(offsetX, offsetY, 6, 4);
                        }
                    }

                    //Dibujar eventos especiales
                    if (!calle.getEventos().isEmpty()) {
                        EventoEspecial evento = calle.getEventos().get(0);
                        switch (evento.getTipo()) {
                            case ACCIDENTE:
                                g2d.setColor(Color.RED);
                                g2d.fillOval(width/2-6, height/2-6, 12, 12);
                                g2d.setColor(Color.WHITE);
                                g2d.drawString("X", width/2-3, height/2+3);
                                break;
                            case ESTADO_VIA:
                                g2d.setColor(Color.ORANGE);
                                g2d.fillRect(2, 2, width-4, height-4);
                                break;
                            case TRAFICO:
                                g2d.setColor(new Color(255, 255, 0, 100));
                                g2d.fillRect(0, 0, width, height);
                                break;
                        }
                    }
                } else {
                    g2d.setColor(new Color(200, 200, 200));
                    g2d.fillRect(0, 0, width, height);
                    g2d.setColor(Color.GRAY);
                    g2d.drawRect(0, 0, width-1, height-1);
                }
            }
        };

        celda.setPreferredSize(new Dimension(30, 30));
        celda.setBackground(Color.WHITE);

        return celda;
    }

    private Color obtenerColorDireccion(Direccion direccion) {
        switch (direccion) {
            case NORTE: return new Color(100, 100, 150);
            case SUR: return new Color(150, 100, 100);
            case ESTE: return new Color(100, 150, 100);
            case OESTE: return new Color(150, 150, 100);
            default: return Color.GRAY;
        }
    }

    /**
     * Método que obtiene el número de filas del grid.
     * @return Número de filas
     */
    public int getFilas() {
        try {
            return Integer.parseInt(fieldFilas.getText());
        } catch (NumberFormatException e) {
            return 10;
        }
    }

    /**
     * Método que obtiene el número de columnas del grid.
     * @return Número de columnas
     */
    public int getColumnas() {
        try {
            return Integer.parseInt(fieldColumnas.getText());
        } catch (NumberFormatException e) {
            return 10;
        }
    }

    /**
     * Método que inicia el temporizador de la simulación.
     * @param listener ActionListener para manejar los eventos del temporizador
     */
    public void iniciarTimer(ActionListener listener) {
        if (timer != null) {
            timer.stop();
        }
        timer = new Timer(1000, listener);
        timer.start();
    }

    /**
     * Método que detiene el temporizador de la simulación.
     */
    public void pararTimer() {
        if (timer != null) {
            timer.stop();
        }
    }

    /**
     * Método que muestra un mensaje en un cuadro de diálogo.
     * @param mensaje Mensaje a mostrar
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
