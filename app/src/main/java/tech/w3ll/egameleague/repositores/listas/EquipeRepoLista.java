package tech.w3ll.egameleague.repositores.listas;

import tech.w3ll.egameleague.modelos.Equipe;
import tech.w3ll.egameleague.repositores.IEquipeRepo;

import java.util.ArrayList;
import java.util.List;

public class EquipeRepoLista implements IEquipeRepo {
    private List<Equipe> times = new ArrayList<>();

    public void adicionar(Equipe time) {
        if (buscarPorNome(time.getNomeDaEquipe()) == null) {
            times.add(time);
        }
        else {
            System.out.println("Já existe um time com esse nome.");
        }
    }

    public void remover(Equipe time) {
        times.remove(time);
    }

    public List<Equipe> listarTodos() {
        return new ArrayList<>(times);
    }

    public Equipe buscarPorNome(String nome) {
        for (Equipe t : times) {
            if (t.getNomeDaEquipe().equalsIgnoreCase(nome)) {
                return t;
            }
        }
        return null;
    }

    public Equipe buscarPorId(int index) {
        if (index >= 0 && index < times.size()) {
            return times.get(index);
        }
        System.out.println("Id não encontrado:" + index);
        return null;
    }

    public void limpar() {
        times.clear();
    }

    public void resetarTabela() {
        System.out.println("Resetar tabela (No banco de dados vai fazer sentido)");
    }
}