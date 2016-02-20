/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.GerenciamentoArquivos;

import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import jxl.Workbook;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import org.json.JSONArray;
import org.json.JSONException;

/**
 *
 * @author Ricardo
 */
public abstract class ConstrutorTemplateResultados {
    
    EscritorResultados escritor;
    JSONArray dados;
    
    public ConstrutorTemplateResultados(EscritorResultados escritor, JSONArray dados){
        this.escritor = escritor;
        this.dados = dados;
    }
    
    public abstract void construirResultados()throws IOException, WriteException, RowsExceededException, JSONException;

    
}
