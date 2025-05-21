package tech.w3ll.egameleague.modelos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StatiscasController {
    /**
     * Classe responsável por controlar e gerenciar as estatísticas dos jogadores ou equipes.
     * Aqui são feitas operações como atualizar ranking, registrar vitórias/derrotas e filtrar estatísticas.
     */

        // Lista que armazena as estatísticas de todos os jogadores ou equipes
        private List<StatisticasModel> estatisticasList;

        /**
         * Construtor da classe. Inicializa a lista de estatísticas.
         */
        public StatiscasController() {
            this.estatisticasList = new ArrayList<>();
        }

        /**
         * Atualiza o ranking dos jogadores/equipes com base nos pontos.
         * Ordena a lista e define a posição (ranking) de cada um.
         */
        public void atualizarRanking() {
            Collections.sort(estatisticasList, Comparator.comparingInt(StatisticasModel::getPontos).reversed());

            // Após ordenar, define o ranking de cada estatística
            for (int i = 0; i < estatisticasList.size(); i++) {
                estatisticasList.get(i).setRanking(i + 1);
            }
        }

        /**
         * Filtra as estatísticas apenas para um determinado torneio.
         * @param nomeTorneio Nome ou ID do torneio
         * @return Lista de estatísticas referentes ao torneio informado
         */
        public List<StatisticasModel> filtrarPorTorneio(String nomeTorneio) {
            List<StatisticasModel> resultado = new ArrayList<>();
            for (StatisticasModel estatistica : estatisticasList) {
                if (estatistica.getTorneioId().equals(nomeTorneio)) {
                    resultado.add(estatistica);
                }
            }
            return resultado;
        }

        /**
         * Registra uma vitória para o jogador/equipe em um torneio específico.
         * @param nomeJogadorOuEquipe Nome do jogador ou equipe
         * @param torneioId Nome ou ID do torneio
         */
        public void registrarVitoria(String nomeJogadorOuEquipe, String torneioId) {
            StatisticasModel estatistica = buscarOuCriarEstatistica(nomeJogadorOuEquipe, torneioId);
            estatistica.adicionarVitoria();
            atualizarRanking();
        }

        /**
         * Registra uma derrota para o jogador/equipe em um torneio específico.
         * @param nomeJogadorOuEquipe Nome do jogador ou equipe
         * @param nomeTorneio Nome ou ID do torneio
         */
        public void registrarDerrota(String nomeJogadorOuEquipe, String nomeTorneio) {
            StatisticasModel estatistica = buscarOuCriarEstatistica(nomeJogadorOuEquipe, nomeTorneio);
            estatistica.adicionarDerrota();
            atualizarRanking();
        }

        /**
         * Busca uma estatística existente ou cria uma nova, caso não exista.
         * @param nomeJogadorOuEquipe Nome do jogador ou equipe
         * @param nomeTorneio Nome ou ID do torneio
         * @return Objeto Estatisticas correspondente
         */
        private StatisticasModel buscarOuCriarEstatistica(String nomeJogadorOuEquipe, String nomeTorneio) {
            for (StatisticasModel estatistica : estatisticasList) {
                if (estatistica.getNomeJogadorOuEquipe().equals(nomeJogadorOuEquipe) &&
                        estatistica.getTorneioId().equals(nomeTorneio)) {
                    return estatistica;
                }
            }

            // Se não encontrar, cria nova estatística
            StatisticasModel novaEstatistica = new StatisticasModel(
                    nomeJogadorOuEquipe, nomeTorneio, 0, 0, 0);
            estatisticasList.add(novaEstatistica);
            return novaEstatistica;
        }

        /**
         * Retorna o ranking de todos os jogadores/equipes como uma String formatada.
         * @return String com o ranking
         */
        public String exibirRanking() {
            StringBuilder sb = new StringBuilder();
            sb.append("===== RANKING =====\n");
            for (StatisticasModel estatistica : estatisticasList) {
                sb.append(String.format("#%d - %s\n",
                        estatistica.getRanking(),
                        estatistica.toString()));
            }
            return sb.toString();
        }

        /**
         * Retorna a lista de estatísticas (usado para consultas).
         */
        public List<StatisticasModel> getEstatisticasList() {
            return estatisticasList;
        }
    }

