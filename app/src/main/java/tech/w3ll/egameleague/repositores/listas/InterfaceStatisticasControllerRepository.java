package tech.w3ll.egameleague.repositores.listas;

import tech.w3ll.egameleague.modelos.StatiscasController;

import java.util.List;
import java.util.Optional;

public interface InterfaceStatisticasControllerRepository {
    /**
     * Interface para um repositório de listas de ControladorEstatisticas.
     * Define métodos para adicionar, listar, buscar, atualizar e remover controladores de estatísticas.
     */
    public interface InterfaceRepoListasControladoEstatisticas {

        /**
         * Adiciona um novo controlador ao repositório.
         */
        void adicionar( StatiscasController controlador);

        /**
         * Lista todos os controladores presentes no repositório.
         */
        List<StatiscasController> listarTodos();

        /**
         * Busca um controlador pelo índice na lista.
         */
        Optional<StatiscasController> buscarPorIndice(int indice);

        /**
         * Atualiza um controlador pelo índice.
         */
        boolean atualizarPorIndice(int indice, StatiscasController controladorAtualizado);

        /**
         * Remove um controlador pelo índice.
         */
        boolean removerPorIndice(int indice);

        /**
         * Busca o primeiro controlador da lista, caso exista.
         */
        Optional<InterfaceStatisticasControllerRepository> buscarPrimeiro();

    }
}
