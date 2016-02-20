/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modelo.GerenciamentoDadosGeotecnicos;

import Modelo.GerenciamentoArquivos.LeitorDocumento;
import Repositorio.RepositorioParametro;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ricado
 */
@Component
public abstract class ConstrutorEvento {
       
    public abstract Ilha construirEvento();    
    public abstract boolean construirDadodsIdentificacao(LeitorDocumento leitor);
    public abstract void construirEnsaios(LeitorDocumento leitor);
    public abstract void construirParametros(LeitorDocumento leitor, String classe, int indexEnsaio, Ensaio ensaio);
    public abstract List construirValores(LeitorDocumento leitor, String classe, String parametro, int indexEnsaio);
    
   
}
