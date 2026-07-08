import java.util.Random;

public class CarroThread extends Thread {

    private Carro carro;
    private TelaCorrida tela;
    private boolean rodando;
    private Random random;

    public CarroThread(Carro carro, TelaCorrida tela) {
        this.carro = carro;
        this.tela = tela;
        this.rodando = true;
        this.random = new Random();
    }

    public void parar() {
        rodando = false;
    }

    @Override
    public void run() {
        double tempo = 1.0;

        while (rodando && !carro.terminou()) {
            double aceleracao = 5 + random.nextInt(16);
            double movimento = carro.atualizar(aceleracao, tempo);

            int andou = (int) Math.round(movimento);
            int total = (int) Math.round(carro.getPosicao());

            tela.escreverLog("O " + carro.getNome() + " andou " + andou + "m        e já percorreu " + total + "m");

            if (carro.getPosicao() >= TelaCorrida.DISTANCIA_FINAL) {
                carro.marcarFim();
                tela.escreverLog(carro.getNome() + " alcançou a linha de chegada.");
            }

            tela.repaint();

            try {
                Thread.sleep(450 + random.nextInt(350));
            } catch (InterruptedException e) {
                rodando = false;
            }
        }
    }
}
