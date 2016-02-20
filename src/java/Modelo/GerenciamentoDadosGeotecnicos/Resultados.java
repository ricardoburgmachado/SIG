/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modelo.GerenciamentoDadosGeotecnicos;

import java.io.Serializable;
import org.json.JSONArray;

/**
 *
 * @author Ricardo
 */
public class Resultados implements Serializable {
    private JSONArray dados;

    public Resultados(){
        this.dados = new JSONArray();
    }
    
    /**
     * @return the dados
     */
    public JSONArray getDados() {
        return dados;
    }

    /**
     * @param dados the dados to set
     */
    public void setDados(JSONArray dados) {
        this.dados = dados;
    }
}
