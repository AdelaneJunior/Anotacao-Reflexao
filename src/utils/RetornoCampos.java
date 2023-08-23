package utils;

import java.util.Map;

public class RetornoCampos {
   Map<String, String> listaCamposValidados;
   Boolean valido;

   public Map<String, String> getListaCamposValidados() {
      return listaCamposValidados;
   }

   public void setListaCamposValidados(Map<String, String> listaCamposValidados) {
      this.listaCamposValidados = listaCamposValidados;
   }

   public Boolean getValido() {
      return valido;
   }

   public void setValido(Boolean valido) {
      this.valido = valido;
   }
}
