/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controlador;

import Modelo.Excessoes.Excessao;
import Modelo.GerenciamentoDadosGeotecnicos.Classe;
import Modelo.GerenciamentoDadosGeotecnicos.ImplementaConstrutorEvento;
import Modelo.GerenciamentoUsuarios.Usuario;
import Modelo.Mapas.ImplementaServicoMapa;
import Modelo.Mapas.Marcador;
import Modelo.Mapas.ServicoMapa;
import Repositorio.RepositorioClasse;
import Repositorio.RepositorioServicoMapa;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.jboss.weld.context.bound.Bound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author ricado
 */
@Controller
public class ServicoMapaControlador extends ControladorGenerico {
    
    @Autowired
    private RepositorioServicoMapa repositorioServicoMapa;
    private List<Marcador> marcadores;
    @Autowired
    private ServicoMapa servicoMapa;
    
    /**
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/buscarMarcadores"},  method = RequestMethod.GET  )
    public List<Marcador> buscarMarcadores(HttpServletRequest request) {
        
        //servicoMapa = new ImplementaServicoMapa();
        if( possuiPermissao("ListarIlhas", request)){                        
            try{
                //marcadores = repositorioServicoMapa.buscarMarcadores();
                marcadores = servicoMapa.buscarRegistrosMarcadores();
            }catch(Excessao di){ }        
            return marcadores;            
        }else if( possuiPermissao("ListarIlhasProprias", request)){                
            try{            
                Usuario user = (Usuario) request.getSession().getAttribute("usuario");
                //marcadores = repositorioServicoMapa.buscarPeloUsuario(user);               
                marcadores = servicoMapa.buscarRegistrosMarcadores(user);
            }catch(Excessao di){ }        
            return marcadores;
            
        }else{
            return null;
        }         
    }
 
    
    @ResponseBody
    @RequestMapping(value = {"/buscarMapa"},method = RequestMethod.GET)
    public ServicoMapa buscarMapa() {

        //servicoMapa = new ImplementaServicoMapa();
        return servicoMapa.construirMapa("HYBRID", -22.965585, -43.370004);
        /*servicoMapa = new ImplementaServicoMapa();
        servicoMapa.setTipo("HYBRID");        
        servicoMapa.setLatitudeInicial(-22.965585);
        servicoMapa.setLongitudeInicial(-43.370004);        
        servicoMapa.setUrlServico("https://maps.googleapis.com/maps/api/js?v=3.exp&signed_in=true&callback=js_mapInitialize");        
        return servicoMapa;*/
    }

    @Override
    protected void converterTipos(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        
    }

    @Override
    public boolean estaContido(int identificador, List a) {
        return false;
    }
    
}
