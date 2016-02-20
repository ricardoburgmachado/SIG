/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.GerenciamentoArquivos;

import jxl.write.WriteException;
import org.json.JSONArray;
import org.json.JSONException;

/**
 *
 * @author Ricardo
 */
public abstract class EscritorResultados {

    public BibliotecaArquivos biblioteca;    
    public Object arquivo;
    
    public EscritorResultados(Object arquivo, BibliotecaArquivos biblioteca) throws WriteException, JSONException{
        this.biblioteca = biblioteca;
        this.biblioteca.inserirArquivo(arquivo);
        this.biblioteca.definirFontes();
    }
    public abstract void setarResultados(JSONArray resultados);
    
    public abstract void finalizarEscrita();
    
}
