/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controlador;

import Modelo.GerenciamentoDadosGeotecnicos.Ensaio;
import Modelo.GerenciamentoUsuarios.Perfil;
import Repositorio.RepositorioEnsaio;
import Repositorio.RepositorioPerfil;
import java.beans.PropertyEditorSupport;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author ricado
 */
public class EnsaioPropertyEditor extends PropertyEditorSupport{
    
    private RepositorioEnsaio repositorioEnsaio;    
    private Ensaio ensaio;
    
    @Autowired
    public EnsaioPropertyEditor(RepositorioEnsaio repositorioEnsaio) {      
        this.repositorioEnsaio = repositorioEnsaio;        
    }
    
    @Override
    public void setAsText(String text) {        
        Integer id = Integer.parseInt(text);
        ensaio = (Ensaio) repositorioEnsaio.buscarPeloId(id);
        super.setValue(ensaio);
    }
}
