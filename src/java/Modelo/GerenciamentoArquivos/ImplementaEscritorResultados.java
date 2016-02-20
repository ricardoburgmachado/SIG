/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.GerenciamentoArquivos;

import Modelo.GerenciamentoDadosGeotecnicos.Ilha;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.json.JSONArray;
import org.json.JSONException;

/**
 *
 * @author Ricardo
 */
public class ImplementaEscritorResultados extends EscritorResultados {

    private WritableCellFormat timesBoldUnderline;
    private WritableCellFormat times;
    private WritableWorkbook writableWorkbook;
    private WritableSheet excelSheet;
    
    public ImplementaEscritorResultados(Object arquivo, BibliotecaArquivos biblioteca) throws WriteException, JSONException {
        super(arquivo, biblioteca);
        super.biblioteca.inserirArquivo(arquivo);
        super.biblioteca.definirFontes();
        this.writableWorkbook = (WritableWorkbook) arquivo;
    }

    @Override
    public void setarResultados(JSONArray resultados) {
        
        writableWorkbook.createSheet("Dados de Identificação", 0);
        WritableSheet sheet = writableWorkbook.getSheet(0);
        biblioteca.adicionarCabecalho(sheet, 0, 0, "Dados exportados do sistema SAPPGAM");
        try {
            for(int i = 0,cont = 0; i < resultados.getJSONArray(0).length();i++, cont += 5){
                biblioteca.adicionarCabecalho(sheet, 1, 0+cont, "Ilha");//linha, coluna
                biblioteca.adicionarCabecalho(sheet, 1, 1+cont, "Classe(s) do(s) ensaio(s)");
                biblioteca.adicionarCabecalho(sheet, 1, 2+cont, "Parâmetros");
                biblioteca.adicionarCabecalho(sheet, 1, 3+cont, "Valor x");
                biblioteca.adicionarCabecalho(sheet, 1, 4+cont, "Valor y");       
            }
            for(int x=0, colIlha=0, colEnsaio=1, colParam=2, colX=3, colY=4; x < resultados.getJSONArray(0).length() ; x++, colIlha+=5, colEnsaio+=5, colParam+=5, colX+=5, colY+=5 ){               
                biblioteca.adicionarConteudo(sheet, 2, colIlha, resultados.getJSONArray(0).getJSONObject(x).getJSONArray("nomeIlha").getString(0));
                biblioteca.adicionarConteudo(sheet, 2, colEnsaio, resultados.getJSONArray(0).getJSONObject(x).getJSONArray("classeEnsaio").getString(0));
                biblioteca.adicionarConteudo(sheet, 2, colParam, "X: "+resultados.getJSONArray(0).getJSONObject(x).getJSONArray("eixoX").getString(0)+" | Y: "+resultados.getJSONArray(0).getJSONObject(x).getJSONArray("eixoY").getString(0) );                
                
                if(resultados.getJSONArray(0).getJSONObject(x).getJSONArray("coordenadas").length() > 0){
                    for(int lin = 0; lin < resultados.getJSONArray(0).getJSONObject(x).getJSONArray("coordenadas").length() ; lin++){     
                        biblioteca.adicionarConteudo(sheet, lin+2, colX, resultados.getJSONArray(0).getJSONObject(x).getJSONArray("coordenadas").getJSONObject(lin).getJSONArray("x").getDouble(0)+"" );
                        biblioteca.adicionarConteudo(sheet, lin+2, colY, resultados.getJSONArray(0).getJSONObject(x).getJSONArray("coordenadas").getJSONObject(lin).getJSONArray("y").getDouble(0) +"" );
                    }                
                }
            }                         
        } catch (JSONException ex) {
            Logger.getLogger(ImplementaEscritorResultados.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void finalizarEscrita() {
        biblioteca.escreverArquivo();        
        biblioteca.fecharArquivo();
    }
    
}
