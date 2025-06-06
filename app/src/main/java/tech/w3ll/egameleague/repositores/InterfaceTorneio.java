package interfaces;

import modelos.Torneio;

public interface InterfaceTorneio {
    boolean cadastrarTorneio(Torneio torneio);
    Torneio buscarTorneioPorNome(String nomeTorneio);
    boolean atualizarTorneio(String nomeTorneio, Torneio torneio);
    boolean removerTorneioPorNome(String nomeTorneio);
}
