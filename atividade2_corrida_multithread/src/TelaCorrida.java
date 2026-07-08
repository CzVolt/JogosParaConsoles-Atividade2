import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TelaCorrida extends JFrame {

    public static final double DISTANCIA_FINAL = 200.0;

    private ArrayList<Carro> carros;
    private ArrayList<CarroThread> threads;
    private JTextArea areaLog;
    private PainelPista painelPista;
    private JButton botaoIniciar;
    private JButton botaoReiniciar;

    public TelaCorrida() {
        super("Corrida de Carros");

        carros = new ArrayList<Carro>();
        threads = new ArrayList<CarroThread>();

        criarCarros();

        painelPista = new PainelPista();
        areaLog = new JTextArea(10, 42);
        areaLog.setEditable(false);

        botaoIniciar = new JButton("Iniciar");
        botaoReiniciar = new JButton("Reiniciar");

        botaoIniciar.addActionListener(e -> iniciarCorrida());
        botaoReiniciar.addActionListener(e -> reiniciarCorrida());

        JPanel painelBotoes = new JPanel();
        painelBotoes.add(botaoIniciar);
        painelBotoes.add(botaoReiniciar);

        setLayout(new BorderLayout());
        add(painelPista, BorderLayout.CENTER);
        add(new JScrollPane(areaLog), BorderLayout.EAST);
        add(painelBotoes, BorderLayout.SOUTH);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void criarCarros() {
        carros.clear();

        carros.add(new Carro("Carro_01", Color.RED));
        carros.add(new Carro("Carro_02", Color.BLUE));
        carros.add(new Carro("Carro_03", Color.ORANGE));
        carros.add(new Carro("Carro_04", Color.GREEN));
        carros.add(new Carro("Carro_05", Color.MAGENTA));
    }

    private void iniciarCorrida() {
        botaoIniciar.setEnabled(false);
        areaLog.setText("");
        threads.clear();

        for (Carro carro : carros) {
            CarroThread thread = new CarroThread(carro, this);
            threads.add(thread);
            thread.start();
        }
    }

    private void reiniciarCorrida() {
        for (CarroThread thread : threads) {
            thread.parar();
        }

        threads.clear();
        criarCarros();
        areaLog.setText("");
        botaoIniciar.setEnabled(true);
        repaint();
    }

    public void escreverLog(String texto) {
        SwingUtilities.invokeLater(() -> {
            areaLog.append(texto + "\n");
            areaLog.setCaretPosition(areaLog.getDocument().getLength());
        });
    }

    private class PainelPista extends JPanel {

        public PainelPista() {
            setPreferredSize(new Dimension(680, 360));
            setBackground(new Color(55, 55, 55));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            int inicioX = 80;
            int chegadaX = getWidth() - 70;
            int larguraCarro = 45;
            int alturaCarro = 24;

            g.setColor(Color.WHITE);
            g.drawString("Corrida multithread - cada carro é uma thread", 20, 20);

            g.setColor(Color.YELLOW);
            g.fillRect(chegadaX, 35, 5, 300);
            g.drawString("Chegada", chegadaX - 25, 350);

            for (int i = 0; i < carros.size(); i++) {
                Carro carro = carros.get(i);
                int y = 50 + i * 55;

                g.setColor(Color.LIGHT_GRAY);
                g.drawLine(inicioX, y + 30, chegadaX, y + 30);

                g.setColor(Color.WHITE);
                g.drawString(carro.getNome(), 15, y + 25);

                double porcentagem = carro.getPosicao() / DISTANCIA_FINAL;
                int x = inicioX + (int) ((chegadaX - inicioX - larguraCarro) * porcentagem);

                g.setColor(carro.getCor());
                g.fillRect(x, y + 8, larguraCarro, alturaCarro);

                g.setColor(Color.BLACK);
                g.fillOval(x + 5, y + 27, 10, 10);
                g.fillOval(x + 30, y + 27, 10, 10);

                g.setColor(Color.WHITE);
                g.drawString((int) carro.getPosicao() + "m", x + 5, y + 6);
            }
        }
    }
}
