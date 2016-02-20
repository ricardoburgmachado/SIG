/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.GerenciamentoArquivos;

import java.io.IOException;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Ricardo
 */
public class ImplementaConstrutorTemplateResultados extends ConstrutorTemplateResultados {

    public ImplementaConstrutorTemplateResultados(EscritorResultados escritor, JSONArray dados) {
        super(escritor, dados);
    }


    @Override
    public void construirResultados() throws IOException, WriteException, RowsExceededException, JSONException {
        super.escritor.setarResultados(super.dados);
        super.escritor.finalizarEscrita();
    }

    
    
    /*
    private JExcel jExcel;
   
    @Autowired(required = true)
    public ImplementaConstrutorTemplateResultados(JExcel jExcel){
        this.jExcel = jExcel;
    }
    
    @Override
    public void construirResultados(WritableWorkbook workbook, JSONArray dados) throws IOException, WriteException, RowsExceededException, JSONException{
        jExcel.setEstrutura(workbook);
        jExcel.preencheEstruturaResultados(dados);
    }
    */
}
