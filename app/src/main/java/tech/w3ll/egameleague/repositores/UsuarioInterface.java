package repositorios;

import modelos.Usuario;

public interface UsuarioInterface {
    public Usuario fazerCadastro(String usuario, String email, String senha, String telefone);
    public void addUsuarios(Usuario usuario);
    public boolean removerUsuario(Usuario usuarioRemover);
    public Usuario buscarUsuario(String email, String senha);
}
