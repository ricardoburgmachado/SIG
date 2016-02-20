package Modelo.Mapas;

import Modelo.GerenciamentoUsuarios.Usuario;
import Repositorio.RepositorioServicoMapa;
import java.util.ArrayList;
import javax.persistence.Entity;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author ricado
 */
@Component
public class ImplementaServicoMapa extends ServicoMapa{

    
    private RepositorioServicoMapa repositorioServicoMapa;
    
    @Autowired(required = true )
    ImplementaServicoMapa(RepositorioServicoMapa r){
        this.repositorioServicoMapa = r;
    }
    
    @Override
    public ArrayList<Marcador> buscarRegistrosMarcadores(Usuario u) {
        return repositorioServicoMapa.buscarPeloUsuario(u);
    }

    @Override
    public ArrayList<Marcador> buscarRegistrosMarcadores() {
        System.out.println("repositorioServicoMapa: "+repositorioServicoMapa.toString());
        return repositorioServicoMapa.buscarMarcadores();
    }

    @Override
    public ServicoMapa construirMapa(String tipo, double latitudeInicial, double longitudeInicial) {
        super.setTipo(tipo);
        super.setLatitudeInicial(latitudeInicial);
        super.setLongitudeInicial(longitudeInicial);
        return this;
    }
    
    
    
    
    
}
