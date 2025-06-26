package tech.w3ll.egameleague.repositores.listas;

import tech.w3ll.egameleague.modelos.Estatisticas;
import tech.w3ll.egameleague.repositores.InterfaceRepoListasEstatiscas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Comparator;
import java.util.stream.Collectors;

public class RepoListaEstatisticas implements InterfaceRepoListasEstatiscas {

    private static final List<Estatisticas> estatisticas = new ArrayList<>();

    @Override
    public void adicionar(Estatisticas estatistica) {
        estatisticas.add(estatistica);
    }

    @Override
    public List<Estatisticas> listarTodos() {
        return new ArrayList<>(estatisticas);
    }

    @Override
    public Optional<Estatisticas> buscarPorNomeETorneio(String nome, String torneioId) {
        return estatisticas.stream()
                .filter(e -> e.getNomeJogadorOuEquipe().equals(nome)
                        && e.getNomeTorneio().equals(torneioId))
                .findFirst();
    }

    @Override
    public boolean atualizarEstatisticas(Estatisticas estatisticaAtualizada) {
        for (int i = 0; i < estatisticas.size(); i++) {
            Estatisticas e = estatisticas.get(i);
            if (e.getNomeJogadorOuEquipe().equals(estatisticaAtualizada.getNomeJogadorOuEquipe())
                    && e.getNomeTorneio().equals(estatisticaAtualizada.getNomeTorneio())) {
                estatisticas.set(i, estatisticaAtualizada);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removerEstatisticas(String nome, String torneioId) {
        return estatisticas.removeIf(e -> e.getNomeJogadorOuEquipe().equals(nome)
                && e.getNomeTorneio().equals(torneioId));
    }

    @Override
    public Optional<Estatisticas> buscarPorIndice(int indice) {
        if (indice >= 0 && indice < estatisticas.size()) {
            return Optional.of(estatisticas.get(indice));
        }
        return Optional.empty();
    }

    @Override
    public boolean atualizarPorIndice(int indice, Estatisticas controladorAtualizado) {
        if (indice >= 0 && indice < estatisticas.size()) {
            estatisticas.set(indice, controladorAtualizado);
            return true;
        }
        return false;
    }

    @Override
    public boolean removerPorIndice(int indice) {
        if (indice >= 0 && indice < estatisticas.size()) {
            estatisticas.remove(indice);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Estatisticas> buscarPrimeiro() {
        if (estatisticas.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(estatisticas.get(0));
    }

    public void registrarVitoria(String nomeJogadorOuEquipe, String nomeTorneio) {
        Estatisticas estatistica = buscarOuCriarEstatistica(nomeJogadorOuEquipe, nomeTorneio);
        estatistica.adicionarVitoria();
        atualizarRanking();
    }

    public void registrarDerrota(String nomeJogadorOuEquipe, String nomeTorneio) {
        Estatisticas estatistica = buscarOuCriarEstatistica(nomeJogadorOuEquipe, nomeTorneio);
        estatistica.adicionarDerrota();
        atualizarRanking();
    }

    private Estatisticas buscarOuCriarEstatistica(String nomeJogadorOuEquipe, String nomeTorneio) {
        return estatisticas.stream()
                .filter(e -> e.getNomeJogadorOuEquipe().equalsIgnoreCase(nomeJogadorOuEquipe) &&
                        e.getNomeTorneio().equalsIgnoreCase(nomeTorneio))
                .findFirst()
                .orElseGet(() -> {
                    Estatisticas novaEstatistica = new Estatisticas(nomeJogadorOuEquipe, nomeTorneio, 0, 0, 0);
                    estatisticas.add(novaEstatistica);
                    return novaEstatistica;
                });
    }

    public void atualizarRanking() {
        Map<String, List<Estatisticas>> estatisticasPorTorneio = estatisticas.stream()
                .collect(Collectors.groupingBy(Estatisticas::getNomeTorneio));

        for (List<Estatisticas> statsDoTorneio : estatisticasPorTorneio.values()) {
            statsDoTorneio.sort(Comparator.comparingInt(Estatisticas::getPontos).reversed());
            for (int i = 0; i < statsDoTorneio.size(); i++) {
                statsDoTorneio.get(i).setRanking(i + 1);
            }
        }
    }

    public List<Estatisticas> filtrarPorTorneio(String nomeTorneio) {
        if (nomeTorneio == null || nomeTorneio.trim().isEmpty()) {
            return listarTodos();
        }
        return estatisticas.stream()
                .filter(e -> e.getNomeTorneio().equalsIgnoreCase(nomeTorneio))
                .collect(Collectors.toList());
    }
}