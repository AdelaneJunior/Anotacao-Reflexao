package modelo;

import anotacao.Campo;
import anotacao.Tabel;
import utils.ReflexaoTabela;

@Tabel()
public class Profissional extends SuperTabela<Long> {
    @Campo(colunaNome = "cpf", isPk = true, isObrigatorio = true)
    private Long cpf;
    @Campo(colunaNome = "nome_profissional", isObrigatorio = true)
    private String nome;

    @Campo(colunaNome = "idade", isObrigatorio = true)
    private Integer idade;

    public Long getCpf() {
        return cpf;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    @Override
    public String getNomeTabela() {
        //return ReflexaoTabela.getUCFirst(super.getTableName());
        return "CoiSA";
    }
}

