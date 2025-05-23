package repositorios;

import interfaces.InterfaceTorneio;
import modelos.Torneio;

import java.util.ArrayList;
import java.util.List;

public class TorneioRepoLista implements InterfaceTorneio {
    List<Torneio> torneios;

    public TorneioRepoLista() {
        this.torneios = new ArrayList<>();
    }

    @Override
    public boolean cadastrarTorneio(Torneio torneio) {
        boolean cadastrado;

        try {
            torneios.add(torneio);
            cadastrado = true;
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar torneio!");
            cadastrado = false;
        }
        return cadastrado;
    }

    @Override
    public Torneio buscarTorneioPorNome(String nomeTorneio) {
        Torneio torneioBuscado = null;

        try {
            for (Torneio t : torneios){
                if (nomeTorneio.equals(t.getNomeDoTorneio())){
                    torneioBuscado = t;
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar torneio!");
            torneioBuscado = null;
        }

        return torneioBuscado;
    }

    @Override
    public boolean atualizarTorneio(String nomeTorneio, Torneio torneioAtualizado) {
        boolean atualizado;
        Torneio busca;

        try {
            busca = buscarTorneioPorNome(nomeTorneio);
            torneios.remove(busca);
            torneios.add(torneioAtualizado);
            atualizado = true;
        } catch (Exception e) {
            System.out.println("Erro ao atualizar torneio!");
            atualizado = false;
        }
        return atualizado;
    }

    @Override
    public boolean removerTorneioPorNome(String nomeTorneio) {
        boolean removido;
        Torneio busca;

        try {
            busca = buscarTorneioPorNome(nomeTorneio);
            torneios.remove(busca);
            removido = true;
        } catch (Exception e) {
            removido = false;
        }
        return removido;
    }
}
