package tech.w3ll.egameleague.repositores.listas;

import tech.w3ll.egameleague.modelos.StatisticasModel;

import java.util.List;
import java.util.Optional;

public interface InterfaceListStatistiscasRepository {
    /**
     * Interface para um repositório de listas de Estatísticas.
     * Define métodos para adicionar, listar, buscar, atualizar e remover estatísticas.
     */
    public interface InterfaceRepoListasEstatiscas {

        /**
         * Adiciona uma estatística ao repositório.
         */
        void adicionar(StatisticasModel estatistica);

        /**
         * Lista todas as estatísticas presentes no repositório.
         */
        List<StatisticasModel> listarTodos();

        /**
         * Busca estatísticas pelo nome do participante e torneio.
         */
        Optional<StatisticasModel> buscarPorNomeETorneio(String nome, String torneioId);

        /**
         * Atualiza uma estatística específica.
         */
        boolean atualizarEstatisticas (StatisticasModel estatisticaAtualizada);

        /**
         * Remove uma estatística pelo nome do participante e torneio.
         */
        public boolean removerEstatisticas (String nome, String torneioId);
    }
}
