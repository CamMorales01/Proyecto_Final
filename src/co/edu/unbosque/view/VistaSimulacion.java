package co.edu.unbosque.view;

import co.edu.unbosque.model.*;
import co.edu.unbosque.controller.ControladorSimulacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

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

    public VistaSimulacion() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Simulación de Tráfico Vehicular");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel de controles
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

        // Panel de información
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

        // Panel principal para la simulación
        panelGrid = new JPanel();
        panelGrid.setBackground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(panelGrid);
        scrollPane.setPreferredSize(new Dimension(800, 600));
        add(scrollPane, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }

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

    public void actualizarVista(Ciudad ciudad) {
        SwingUtilities.invokeLater(() -> {
            // Actualizar información
            labelTurno.setText("Turno: " + ciudad.getTurnoActual());
            labelVehiculos.setText("Vehículos: " + ciudad.getVehiculos().size());
            labelSemaforos.setText("Semáforos: " + ciudad.getSemaforos().size());

            // Actualizar grid visual
            actualizarGrid(ciudad);
        });
    }

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

    private JPanel crearCeldaCiudad(Calle calle, int fila, int columna) {
        JPanel celda = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int width = getWidth();
                int height = getHeight();

                // Determinar si es intersección (cada 3 celdas aproximadamente)
                boolean esInterseccion = (fila % 3 == 0 && columna % 3 == 0);
                boolean esCalleHorizontal = fila % 3 == 0;
                boolean esCalleVertical = columna % 3 == 0;
                boolean esCalle = esCalleHorizontal || esCalleVertical;

                if (esCalle) {
                    // Dibujar calle (fondo blanco/gris claro)
                    g2d.setColor(Color.WHITE);
                    g2d.fillRect(0, 0, width, height);

                    // Dibujar líneas de la calle
                    g2d.setColor(Color.LIGHT_GRAY);
                    g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{3}, 0));

                    // Líneas direccionales según el flujo
                    Direccion direccion = calle.getDireccionFlujo();
                    switch (direccion) {
                        case ESTE:
                            g2d.drawLine(5, height/2, width-5, height/2);
                            // Flecha
                            g2d.drawLine(width-10, height/2-3, width-5, height/2);
                            g2d.drawLine(width-10, height/2+3, width-5, height/2);
                            break;
                        case OESTE:
                            g2d.drawLine(5, height/2, width-5, height/2);
                            // Flecha
                            g2d.drawLine(10, height/2-3, 5, height/2);
                            g2d.drawLine(10, height/2+3, 5, height/2);
                            break;
                        case NORTE:
                            g2d.drawLine(width/2, 5, width/2, height-5);
                            // Flecha
                            g2d.drawLine(width/2-3, 10, width/2, 5);
                            g2d.drawLine(width/2+3, 10, width/2, 5);
                            break;
                        case SUR:
                            g2d.drawLine(width/2, 5, width/2, height-5);
                            // Flecha
                            g2d.drawLine(width/2-3, height-10, width/2, height-5);
                            g2d.drawLine(width/2+3, height-10, width/2, height-5);
                            break;
                    }

                    // Dibujar semáforo si existe
                    if (calle.tieneSemaforo() && esInterseccion) {
                        Color colorSemaforo = calle.getSemaforo().getEstado() == EstadoSemaforo.VERDE
                                ? Color.GREEN : Color.RED;
                        g2d.setColor(colorSemaforo);
                        g2d.fillRect(width/2-4, height/2-4, 8, 8);
                        g2d.setColor(Color.BLACK);
                        g2d.drawRect(width/2-4, height/2-4, 8, 8);
                    }

                    // Dibujar vehículos
                    if (!calle.getVehiculos().isEmpty()) {
                        int numVehiculos = Math.min(calle.getVehiculos().size(), 3);
                        for (int i = 0; i < numVehiculos; i++) {
                            g2d.setColor(Color.BLACK);
                            int offsetX = (i * 8) - (numVehiculos * 4) + width/2;
                            int offsetY = height/2 - 2;

                            // Dibujar pequeño rectángulo como vehículo
                            g2d.fillRect(offsetX, offsetY, 6, 4);
                        }
                    }

                    // Dibujar eventos especiales
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
                    // Dibujar manzana/bloque (gris)
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

    public int getFilas() {
        try {
            return Integer.parseInt(fieldFilas.getText());
        } catch (NumberFormatException e) {
            return 10;
        }
    }

    public int getColumnas() {
        try {
            return Integer.parseInt(fieldColumnas.getText());
        } catch (NumberFormatException e) {
            return 10;
        }
    }

    public void iniciarTimer(ActionListener listener) {
        if (timer != null) {
            timer.stop();
        }
        timer = new Timer(1000, listener); // 1 segundo por turno
        timer.start();
    }

    public void pararTimer() {
        if (timer != null) {
            timer.stop();
        }
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
