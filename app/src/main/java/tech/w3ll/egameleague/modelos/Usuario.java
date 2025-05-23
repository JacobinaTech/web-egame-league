package modelos;

public class Usuario {
    private String nomeDousuario;
    private String email;
    private String senha;
    private String telefone;

    public Usuario(String nomeDousuario) {
        this.nomeDousuario = nomeDousuario;
    }

    public String getNomeDousuario() {
        return nomeDousuario;
    }

    public void setNomeDousuario(String nomeDousuario) {
        this.nomeDousuario = nomeDousuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
