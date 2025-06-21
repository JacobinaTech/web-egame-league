package tech.w3ll.egameleague.repositores;

import tech.w3ll.egameleague.modelos.Equipe;
import tech.w3ll.egameleague.repositores.IEquipeRepo;

import java.util.List;

public interface IEquipeRepo {

    void adicionar(Equipe time);
    void remover(Equipe time);
    List<Equipe> listarTodos();
    Equipe buscarPorNome(String nome);
    Equipe buscarPorId(int id);
    void limpar();
    void resetarTabela();

}
