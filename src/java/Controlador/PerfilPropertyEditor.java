package Controlador;

import Modelo.GerenciamentoUsuarios.Perfil;
import Repositorio.RepositorioPerfil;
import java.beans.PropertyEditorSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PerfilPropertyEditor extends PropertyEditorSupport {

    private RepositorioPerfil repositorioPerfil;
    
    private Perfil perfil;
    
    @Autowired(required = true)
    public PerfilPropertyEditor(RepositorioPerfil repositorioPerfil) {      
        this.repositorioPerfil = repositorioPerfil;        
    }
    
    @Override
    public void setAsText(String text) {        
        Integer id = Integer.parseInt(text);
        perfil = (Perfil) repositorioPerfil.buscarPeloId(id);       
        super.setValue(perfil);
    }

}
