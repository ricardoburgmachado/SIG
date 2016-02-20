/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controlador;

import Modelo.GerenciamentoDadosGeotecnicos.TipoParametro;
import Modelo.GerenciamentoUsuarios.Perfil;
import Repositorio.RepositorioPerfil;
import Repositorio.RepositorioTipoParametro;
import java.beans.PropertyEditorSupport;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author ricado
 */
public class TipoParametroPropertyEditor extends PropertyEditorSupport {
    
    private RepositorioTipoParametro repositorioTipoParametro;    
    private TipoParametro tipoParametro;

    @Autowired(required = true)
    public TipoParametroPropertyEditor(RepositorioTipoParametro repositorioTipoParametro) {      
        this.repositorioTipoParametro = repositorioTipoParametro;        
    }
    
    @Override
    public void setAsText(String text) {        
        Integer id = Integer.parseInt(text);
        tipoParametro = (TipoParametro) repositorioTipoParametro.buscarPeloId(id);
        super.setValue(tipoParametro);
    }

    
    
}
