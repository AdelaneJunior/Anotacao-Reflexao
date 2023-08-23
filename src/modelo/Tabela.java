package modelo;

import anotacao.Campo;
import anotacao.Tabel;

@Tabel
public class Tabela extends SuperTabela<Integer> {
    @Campo(colunaNome = "super_ID", isPk = true, isObrigatorio = true)
    private Integer id;
    @Campo(colunaNome = "txt_nome", isObrigatorio = true)
    private String nome;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}

