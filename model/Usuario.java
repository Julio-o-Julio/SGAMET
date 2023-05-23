package model;

public class Usuario {
    private String email;
    private String nomeUsuario;
    private String senha;

    public String getEmail() {
        return email;
    }

    public Usuario(String email, String nomeUsuario, String senha) {
        this.email = email;
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }
    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }


    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
