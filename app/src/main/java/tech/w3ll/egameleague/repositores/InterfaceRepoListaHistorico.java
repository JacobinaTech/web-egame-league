package tech.w3ll.egameleague.repositores;

import tech.w3ll.egameleague.modelos.Historico;
import java.util.List;
import java.util.Optional;


public interface InterfaceRepoListaHistorico {

        void adicionar(Historico evento);

        List<Historico> listarTodos();

        Optional<Historico> buscarPorIndice(int indice);

        List<Historico> filtrarPorJogo(String nomeJogo);

        boolean atualizarPorIndice(int indice, Historico eventoAtualizado);

        boolean removerPorIndice(int indice);

        boolean removerPorJogo(String nomeJogo);

        Optional<Historico> buscarPrimeiro();
}