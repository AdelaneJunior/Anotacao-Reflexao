package utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import anotacao.Campo;
import anotacao.Tabel;
import modelo.SuperTabela;

public class ReflexaoTabela {
    private static Field getPkField(SuperTabela tab) {
        validarParametroTab(tab);
        int numPks = 0;
        Field pkField = null;
        String pkNome = null;

        Field[] atributos = getAtributosDeclarados(tab);

        // percorrer os atributos procurando pela anotacao @Campo
        for (Field attr : atributos) {
            if (attr.isAnnotationPresent(Campo.class)) {
                Campo cmp = attr.getAnnotation(Campo.class);
                if (cmp.isPk()) {
                    pkField = attr;
                    pkNome = cmp.colunaNome();
                    numPks++;
                }
            }
        }
        if (pkField == null || pkNome.isEmpty()) {
            throw new RuntimeException(
                    "Classe: " + tab.getClass().getName() + " não tem nenhum atributo anotado com @Campo(isPk=True)");
        } else if (numPks > 1) {
            throw new RuntimeException(
                    "Classe: " + tab.getClass().getName() + " tem mais de um atributo anotado com @Campo(isPk=True)");
        }
        return pkField;
    }

    public static String getPkName(SuperTabela tab) {
        Field pkField = getPkField(tab);
        Campo cmp = pkField.getAnnotation(Campo.class);
        return cmp.colunaNome();

    }

    public static Object getPkValue(SuperTabela tab) {
        Field pkField = getPkField(tab);
        String pkMethodName = "get" + getUCFirst(pkField.getName());
        return invokeGetMethod(tab, pkMethodName);
    }

    private static void validarParametroTab(SuperTabela tab) {
        if (tab == null) {
            throw new RuntimeException("Erro Metodo ReflexaoTabela.getPkNome, deve receber um objeto não nulo");
        }
    }

    private static Object invokeGetMethod(SuperTabela tab, String pkMethodName) {
        return invokeMethod(tab, pkMethodName, null);
    }

    private static Object invokeMethod(SuperTabela tab, String pkMethodName, Object value) {
        Class<?> cls = tab.getClass();

        Method pkMethod = null;
        try {
            if (value == null) {
                pkMethod = cls.getMethod(pkMethodName);
                return pkMethod.invoke(tab);
            } else {
                pkMethod = cls.getMethod(pkMethodName, value.getClass());
                return pkMethod.invoke(tab, value);
            }

        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException("A classe: " + cls.getName() + " não possui o metodo: " + pkMethodName + "(" + value.getClass().getName() + ") com visibilidade pública, ou não existe!");
        } catch (IllegalAccessException |
                 IllegalArgumentException |
                 InvocationTargetException e) {
            throw new RuntimeException("Houve um erro desconhecido na execução do método:" + cls.getName() + "." + pkMethodName, e);
        }
    }

    public static String getUCFirst(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public static void setPkValue(SuperTabela<?> tab, Object value) {
        Field pkField = getPkField(tab);
        setValue(tab, pkField, value);
    }

    public static void setValue(SuperTabela<?> tab, Field field, Object value) {
        String pkMethodName = "set" + getUCFirst(field.getName());
        invokeMethod(tab, pkMethodName, value);
    }

    public static List<Field> getIsObrigatorio(SuperTabela tab) {

        List<Field> atrObrigatorios = new ArrayList<>();
        Field[] atributos = getAtributosDeclarados(tab);

        for (Field atributo : atributos) {

            if (atributo.isAnnotationPresent(Campo.class)) {
                Campo campo = atributo.getAnnotation(Campo.class);
                if (campo.isObrigatorio()) {
                    atrObrigatorios.add(atributo);
                }
            }
        }

        return atrObrigatorios;

    }

    public static List<String> getCampoObrigatorio(SuperTabela tab) {

        List<String> atrObrigatorios = new ArrayList<>();
        Field[] atributos = getAtributosDeclarados(tab);

        for (Field atributo : atributos) {

            if (atributo.isAnnotationPresent(Campo.class)) {
                Campo campo = atributo.getAnnotation(Campo.class);
                if (campo.isObrigatorio()) {
                    atrObrigatorios.add(atributo.getName());
                }
            }
        }

        return atrObrigatorios;

    }
    public static RetornoCampos validarAtributosObrigatorios(SuperTabela tab) {

        List<Field> atributosObrigatorios = getIsObrigatorio(tab);

        RetornoCampos retornoCampos = new RetornoCampos();
        retornoCampos.setListaCamposValidados(new HashMap<>());
        retornoCampos.setValido(true);

        if (!atributosObrigatorios.isEmpty()) {

            for (Field atributo : atributosObrigatorios) {

                Campo cmp = atributo.getAnnotation(Campo.class);
                String getAtributo = "get" + getUCFirst(atributo.getName());
                Object objeto = invokeGetMethod(tab, getAtributo);

                if (Objects.nonNull(objeto)) {

                    retornoCampos.getListaCamposValidados().put(atributo.getName(), "ok");

                } else if (Objects.isNull(objeto)) {

                    retornoCampos.setValido(false);
                    retornoCampos.getListaCamposValidados().put(atributo.getName(), "null");

                } else if (objeto instanceof String) {
                    retornoCampos.setValido(false);
                    retornoCampos.getListaCamposValidados().put(atributo.getName(), "vazio");
                }
            }
        }

        return retornoCampos;
    }

    public static Boolean todosAtributosObrigatoriosPreenchidos(SuperTabela tab){
        return validarAtributosObrigatorios(tab).getValido();
    }

    public static Field[] getAtributosDeclarados(SuperTabela tab) {
        // obter metadado do objeto SuperTabela
        Class<?> cls = tab.getClass();
        // obter nome dos atributos da classe
        return cls.getDeclaredFields();
    }

    public static String getNomeTabela(SuperTabela tab){

        Class<?> cls = tab.getClass();
        if(cls.isAnnotationPresent(Tabel.class)){
            Tabel tabel = cls.getAnnotation(Tabel.class);
            if(!tabel.nomeTabela().isEmpty()){
                return tabel.nomeTabela();
            }
        }
        return cls.getSimpleName().toLowerCase();
    }

}
