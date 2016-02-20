/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Repositorio;

import Modelo.GerenciamentoDadosGeotecnicos.Ilha;
import Modelo.GerenciamentoUsuarios.Usuario;
import Modelo.Mapas.Marcador;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ricado
 */
@Repository
public class RepositorioServicoMapa {
    
    
    private DaoIlha daoIlha;
    
    @Autowired(required = true )
    public RepositorioServicoMapa(DaoIlha daoIlha){
        this.daoIlha = daoIlha;
    }
    
    
    public ArrayList<Marcador>  buscarPeloUsuario(Usuario u) {
        ArrayList<Ilha> ilhas = daoIlha.buscarPeloUsuario(u);
        ArrayList<Marcador> marcadores;
        
        if(ilhas.size() > 0){
            marcadores = new ArrayList<>();
            for(Ilha i: ilhas){
                marcadores.add(new Marcador(i.getId(), i.getLatitude(), i.getLongitude(), i.getLocal()));
            }
            return marcadores;
        }else{
            return null;
        }
    }
    
    /**
     * Redebe as ilhas do BD obtem as informações necessárias e realiza a contrução de marcadores para serem repassados para a VIEW
     * @return 
     */
    public ArrayList<Marcador> buscarMarcadores(){
    
        ArrayList<Ilha> ilhas = daoIlha.buscarTodos();        
        ArrayList<Marcador> marcadores;
        
        if(ilhas.size() > 0){
            marcadores = new ArrayList<>();
            for(Ilha i: ilhas){
                marcadores.add(new Marcador(i.getId(), i.getLatitude(), i.getLongitude(), i.getLocal()));
            }
            return marcadores;
        }else{
            return null;
        }
    }
    
    
}
