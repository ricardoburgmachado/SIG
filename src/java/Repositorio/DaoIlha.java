/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repositorio;

import Modelo.GerenciamentoDadosGeotecnicos.Classe;
import Modelo.GerenciamentoDadosGeotecnicos.Ensaio;
import Modelo.GerenciamentoDadosGeotecnicos.Ilha;
import Modelo.GerenciamentoDadosGeotecnicos.Valor;
import Modelo.GerenciamentoExpressoes.FatorAjuste;
import Modelo.GerenciamentoUsuarios.Perfil;
import Modelo.GerenciamentoUsuarios.Usuario;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

/**
 *
 * @author ricado
 */
@Component
public class DaoIlha extends DAO {

    public Integer salvar(Ilha ilha) {
        return super.salvar(ilha);
    }
    
    public ArrayList<Integer> buscarIdsEnsaios(int idIlha){ 
        String query = "SELECT ensaio.id_ensaio FROM ensaio WHERE ensaio.fk_id_ilha = :idIlha";
        return (ArrayList<Integer>) buscarQuery(query, new String[]{ "idIlha",}, new int[]{ idIlha }, null );        
    }   
    
    public ArrayList<Ilha> buscarPeloUsuario(Usuario u) {
        String query = "SELECT * FROM ilha WHERE ilha.fk_id_usuario = :idUsuario";
        return (ArrayList<Ilha>) buscarQuery(query, new String[]{ "idUsuario",}, new int[]{ u.getId() }, Ilha.class );            
    }
    
    public Object buscarPeloId(int id) {
        return super.buscarPeloId(id, Ilha.class);
    }

    public ArrayList<Ilha> buscarTodos() {
        return (ArrayList<Ilha>) super.buscarTodos(Ilha.class);
    }

    public boolean excluir(int id) {
        return super.excluir(id, Ilha.class);
    }
    
}
