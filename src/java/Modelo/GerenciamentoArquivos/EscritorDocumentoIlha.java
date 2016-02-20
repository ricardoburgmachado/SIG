/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.GerenciamentoArquivos;

import Modelo.GerenciamentoDadosGeotecnicos.Ensaio;
import Modelo.GerenciamentoDadosGeotecnicos.Evento;
import java.io.IOException;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Ricardo
 */

public abstract class EscritorDocumentoIlha {


    public BibliotecaArquivos biblioteca;
    
    @Autowired(required = true)    
    public EscritorDocumentoIlha(Object arquivo, BibliotecaArquivos  biblioteca) throws WriteException, JSONException {
        this.biblioteca = biblioteca;
        biblioteca.inserirArquivo(arquivo);
        biblioteca.definirFontes();
    }
    
    public abstract void setarIdentificacao(Evento evento);
    
    public abstract void setarEnsaio(Ensaio ensaio, int index)throws IOException, WriteException, RowsExceededException, JSONException;    
    
    public abstract void finalizarEscrita();    
}


