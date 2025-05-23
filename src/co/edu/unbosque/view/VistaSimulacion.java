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
        panelGrid.setBackground(Color.DARK_GRAY);
        add(new JScrollPane(panelGrid), BorderLayout.CENTER);

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
        panelGrid.setLayout(new GridLayout(ciudad.getFilas(), ciudad.getColumnas(), 1, 1));

        Calle[][] grid = ciudad.getGrid();

        for (int i = 0; i < ciudad.getFilas(); i++) {
            for (int j = 0; j < ciudad.getColumnas(); j++) {
                JPanel celda = crearCeldaCalle(grid[i][j]);
                panelGrid.add(celda);
            }
        }

        panelGrid.revalidate();
        panelGrid.repaint();
    }

    private JPanel crearCeldaCalle(Calle calle) {
        JPanel celda = new JPanel();
        celda.setPreferredSize(new Dimension(40, 40));
        celda.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // Color base según dirección
        Color colorBase = obtenerColorDireccion(calle.getDireccionFlujo());
        celda.setBackground(colorBase);

        // Añadir información visual
        StringBuilder info = new StringBuilder();

        // Semáforo
        if (calle.tieneSemaforo()) {
            Color colorSemaforo = calle.getSemaforo().getEstado() == EstadoSemaforo.VERDE
                    ? Color.GREEN : Color.RED;
            celda.setBorder(BorderFactory.createLineBorder(colorSemaforo, 3));
            info.append("S");
        }

        // Vehículos
        if (!calle.getVehiculos().isEmpty()) {
            info.append("V").append(calle.getVehiculos().size());
            celda.setBackground(celda.getBackground().darker());
        }

        // Eventos
        if (!calle.getEventos().isEmpty()) {
            EventoEspecial evento = calle.getEventos().get(0);
            switch (evento.getTipo()) {
                case ACCIDENTE:
                    celda.setBackground(Color.RED.darker());
                    info.append("A");
                    break;
                case ESTADO_VIA:
                    celda.setBackground(Color.ORANGE);
                    info.append("E");
                    break;
                case TRAFICO:
                    celda.setBackground(Color.YELLOW.darker());
                    info.append("T");
                    break;
            }
        }

        if (info.length() > 0) {
            JLabel label = new JLabel(info.toString(), SwingConstants.CENTER);
            label.setForeground(Color.WHITE);
            label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
            celda.add(label);
        }

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
