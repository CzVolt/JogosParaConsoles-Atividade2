import java.awt.Color;

public class Carro {

    private String nome;
    private Color cor;
    private double velocidade;
    private double posicao;
    private boolean terminou;

    public Carro(String nome, Color cor) {
        this.nome = nome;
        this.cor = cor;
        this.velocidade = 0;
        this.posicao = 0;
        this.terminou = false;
    }

    public synchronized double atualizar(double aceleracao, double tempo) {
        double posicaoAnterior = posicao;

        // V = V0 + a*t
        velocidade = velocidade + aceleracao * tempo;

        // S = S0 + V*t
        posicao = posicao + velocidade * tempo;

        if (posicao > TelaCorrida.DISTANCIA_FINAL) {
            posicao = TelaCorrida.DISTANCIA_FINAL;
        }

        return posicao - posicaoAnterior;
    }

    public synchronized double getPosicao() {
        return posicao;
    }

    public synchronized boolean terminou() {
        return terminou;
    }

    public synchronized void marcarFim() {
        terminou = true;
    }

    public String getNome() {
        return nome;
    }

    public Color getCor() {
        return cor;
    }
}
