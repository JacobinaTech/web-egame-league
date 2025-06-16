package tech.w3ll.egameleague.repositores;

import tech.w3ll.egameleague.modelos.Torneio;

public interface InterfaceTorneio {
    boolean cadastrarTorneio(Torneio torneio);
    Torneio buscarTorneioPorNome(String nomeTorneio);
    boolean atualizarTorneio(String nomeTorneio, Torneio torneio);
    boolean removerTorneioPorNome(String nomeTorneio);
}
