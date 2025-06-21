package tech.w3ll.egameleague.modelos;

public class Partida {
    private int id;
    private Equipe time1;
    private Equipe time2;
    private Equipe vencedor;

    public Partida(Equipe time1, Equipe time2) {
        this.time1 = time1;
        this.time2 = time2;
    }

    public void resetar() {
        if (this.vencedor != null) {
            if (this.vencedor.equals(this.time1)) {
                if (time2 != null) time2.anularEliminacao();
            } else {
                if (time1 != null) time1.anularEliminacao();
            }
        }
        this.vencedor = null;
    }

    public void definirVencedor(Equipe novoVencedor) {
        this.resetar();

        this.vencedor = novoVencedor;
        if (vencedor.equals(time1)) {
            if (time2 != null) {
                time2.eliminar();
            }
        } else {
            if (time1 != null) {
                time1.eliminar();
            }
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Equipe getTime1() {
        return time1;
    }

    public Equipe getTime2() {
        return time2;
    }

    public Equipe getVencedor() {
        return vencedor;
    }

    public void exibirPartida() {
        if (time2 == null) {
            System.out.println(time1 + " avança automaticamente.");
        }
        else {
            System.out.println(time1 + " vs " + time2);
        }
    }
}