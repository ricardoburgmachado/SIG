/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.GerenciamentoArquivos;

import Modelo.GerenciamentoDadosGeotecnicos.Ensaio;
import Modelo.GerenciamentoDadosGeotecnicos.Evento;
import Modelo.GerenciamentoDadosGeotecnicos.Ilha;
import java.io.IOException;
import java.util.List;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.stereotype.Component;

/**
 *
 * @author Ricardo
 */
@Component
public interface BibliotecaArquivos {
    
    public void definirFontes()throws WriteException, JSONException;
    
    public void inserirArquivo(Object arquivo);
    
    public void escreverArquivo();
    
    public void fecharArquivo();
    
    public void adicionarConteudo(Object planilha, int linha, int coluna, String conteudo);
    
    public void adicionarCabecalho(Object planilha, int linha, int coluna, String conteudo);

    // apartir daqui é para leitura
    public Object obterConteudo(int indexPlanilha, int linha, int coluna);    
    
    public Object obterConteudo(Object ensaio, int linha, int coluna);        
    
    public int obterQuantidadeEnsaios();        

    public Object acessarEnsaio(int indexEnsaio);
    
    public int obterQuantidadeLinhasEnsaio(Object ensaio);        
}
