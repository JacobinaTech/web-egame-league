package tech.w3ll.egameleague.modelos;

import java.util.ArrayList;

public class Tabela {
    private ArrayList<Time> times;
    private ArrayList<Partida> partidasAtuais;

    public Tabela(ArrayList<Time> times) {
        this.times = new ArrayList<>(times);
    }

    public boolean temCampeao() {
        int naoEliminados = 0;
        for (Time t : times) {
            if (!t.isEliminado()) {
                naoEliminados = naoEliminados + 1;
            }
        }
        return naoEliminados == 1;
    }

    public Time getCampeao() {
        for (Time t : times) {
            if (!t.isEliminado()) {
                return t;
            }
        }
        return null;
    }

    public void gerarConfrontos() {
        ArrayList<Time> emJogo = new ArrayList<>();
        for (Time t : times) {
            if (!t.isEliminado()) {
                emJogo.add(t);
            }
        }

        partidasAtuais = new ArrayList<>();

        if (emJogo.size() % 2 != 0) {
            Time avanca = emJogo.remove(emJogo.size() - 1);
            System.out.println(avanca.getNome() + " avança automaticamente!");
            partidasAtuais.add(new Partida(avanca, null));
        }

        for (int i = 0; i < emJogo.size(); i = i + 2) {
            partidasAtuais.add(new Partida(emJogo.get(i), emJogo.get(i + 1)));
        }
    }

    public void mostrarConfrontos() {
        System.out.println("\nConfrontos da rodada:");
        for (Partida p : partidasAtuais) {
            p.exibirPartida();
        }
    }

    public void adicionarTime(Time novoTime) {
        times.add(novoTime);
    }

    public ArrayList<Partida> getPartidasAtuais() {
        return this.partidasAtuais;
    }

    public void avancarParaProximaFase() {
        ArrayList<Time> vencedores = new ArrayList<>();
        if (this.partidasAtuais != null) {
            for (Partida p : this.partidasAtuais) {
                if (p.getVencedor() != null) {
                    vencedores.add(p.getVencedor());
                } else if (p.getTime2() == null) {
                    vencedores.add(p.getTime1());
                }
            }
        }
        this.times = vencedores;
    }
}