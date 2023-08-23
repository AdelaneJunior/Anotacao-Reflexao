package teste;

import modelo.Profissional;
import modelo.SuperTabela;
import modelo.Tabela;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class TesteTabels {
    public static void main(String[] args) {
        Tabela tab = new Tabela();
        tab.setId(10);
        tab.setNome("nome");
        printTableData(tab);
        tab.setPk(20);
        printTableData(tab);
        Profissional p = new Profissional();
        p.setCpf(11122233345L);
        p.setNome("Profissional da alegria");
        p.setIdade(33);
        printTableData(p);
        p.setCpf(99922233345L);
        p.setIdade(null);
        printTableData(p);
    }

    private static void printTableData(SuperTabela tab) {
        System.out.println("Classe: " + tab.getClass().getName());
        System.out.println("Table Name: " + tab.getNomeTabela());
        System.out.println("Tabela pknome: " + tab.getPkName());
        System.out.println("Valor PK: " + tab.getPk());
        List<String> lista = tab.getCamposObrigatorios();
        System.out.println("Atributos Obrigatorios: " + lista);
        System.out.println("Atributos Obrigatorios Preenchidos: " + tab.camposObrigatoriosPreenchidos().getListaCamposValidados());
        System.out.println("Todos Atributos Obrigatorios Preenchidos: " + tab.isCamposObrigatoriosOK() );
        System.out.println("------------------------------");
    }
}
