/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.GerenciamentoArquivos;

import Modelo.GerenciamentoDadosGeotecnicos.Evento;
import java.io.IOException;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import org.json.JSONException;

/**
 *
 * @author Ricardo
 */
public abstract class ConstrutorDocumentoIlha {

    EscritorDocumentoIlha escritor;
    Evento evento;

    
    public ConstrutorDocumentoIlha(EscritorDocumentoIlha escritor, Evento evento){
        this.escritor = escritor;
        this.evento = evento;
    }
    
    public abstract void construirDocumentoIlha()throws IOException, WriteException, RowsExceededException, JSONException;
    
    public abstract void construirEnsaios();
    
}
