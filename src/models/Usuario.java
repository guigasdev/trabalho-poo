package models;

import exceptions.UsuarioNaoEncontradoException;
import interfaces.Exibivel;

public class Usuario implements Exibivel {
    private String nome;
    private String matricula;

    public Usuario(String nome, String matricula) {
        this.nome = nome;
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public void verificarSeUsuarioExiste(boolean existe) throws UsuarioNaoEncontradoException {
        if (!existe) {
            throw new UsuarioNaoEncontradoException("Usuário não encontrado!");
        }
    }

    @Override
    public void exibir() {
        System.out.println("Usuário: " + nome + " - Matrícula: " + matricula);
    }
}
