package modelo;

import utils.ReflexaoTabela;
import utils.RetornoCampos;

import java.lang.reflect.Field;
import java.util.List;

public abstract class SuperTabela<TypePK> {
    @SuppressWarnings("unchecked")
    public TypePK getPk() {
        return (TypePK) ReflexaoTabela.getPkValue(this);
    }

    public String getPkName() {
        return ReflexaoTabela.getPkName(this);
    }

    public void setPk(TypePK value) {
        ReflexaoTabela.setPkValue(this, value);
    }

    public String getNomeTabela() {
        return ReflexaoTabela.getNomeTabela(this);
    }

    public List<String> getCamposObrigatorios() {
        return ReflexaoTabela.getCampoObrigatorio(this);
    }

    public RetornoCampos camposObrigatoriosPreenchidos() {
        return ReflexaoTabela.validarAtributosObrigatorios(this);
    }

    public Boolean isCamposObrigatoriosOK(){
        return ReflexaoTabela.todosAtributosObrigatoriosPreenchidos(this);
    }
}
