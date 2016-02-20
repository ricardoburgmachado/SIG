/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modelo.GerenciamentoArquivos;

import Modelo.Excessoes.Excessao;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 *
 * @author ricado
 */
public interface LeitorDocumento {
    
    public boolean iniciaLeitura(Object o, Excessao excessao);
    
    public String obterNome();
    
    public String obterLocal();
    
    public String obterEndereco();
    
    public String obterAutor();
    
    public String obterReferencia();
    
    public String obterEnsaiosRealizados();
    
    public Double obterLatitude();
    
    public Double obterLongitude();
    
    public Date obterDataExecucao();
    
    public List obterValoresParametro(String classe, String parametro, int index);
    
    public int obterQuantidadeEnsaios();     
  
    public String obterClasseEnsaio(int index);
    
    public Set obterParametros(String classe);
    
    public String obterUnidadeParametro(String parametro, String classe, int planilha);
    
    public String obterSiglaParametro(String parametro, String classe, int planilha);
    
    public String obterNomeFantasiaParametro(String parametro, String classe, int planilha);
    
    
}
