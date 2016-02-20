package Repositorio;

import Modelo.GerenciamentoDadosGeotecnicos.Classe;
import Modelo.GerenciamentoDadosGeotecnicos.Ilha;
import Modelo.GerenciamentoDadosGeotecnicos.Parametro;
import Modelo.GerenciamentoExpressoes.Elemento;
import Modelo.GerenciamentoExpressoes.ParametroPlotagem;
import Modelo.GerenciamentoUsuarios.Usuario;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

/**
 *
 * @author ricado
 */
@Component
public class DaoParametroPlotagem extends DAO {
 
    public Integer salvar(ParametroPlotagem pp) {
        System.out.println("DAO: ID= "+pp.getId()+" NOME= "+pp.getNome()); 
        return super.salvar(pp);
    }
   
    public Object buscarPeloId(int id) {
        return super.buscarPeloId(id, ParametroPlotagem.class);
    }

    public Object buscarPeloNome(String nome) {
        return super.buscarPorCampo("nome", nome, ParametroPlotagem.class);
    }

    public ArrayList<ParametroPlotagem> buscarTodos() {
        return (ArrayList<ParametroPlotagem>) super.buscarTodos(ParametroPlotagem.class);
    }

    public boolean excluir(int id) {
        return super.excluir(id, ParametroPlotagem.class);
    }
    
    public ArrayList<ParametroPlotagem> buscarPeloUsuario(Usuario u) {
        String query = "SELECT * FROM parametro_plotagem WHERE parametro_plotagem.fk_id_d_usuario = :idUsuario";
        return (ArrayList<ParametroPlotagem>) buscarQuery(query, new String[]{ "idUsuario",}, new int[]{ u.getId() }, ParametroPlotagem.class );            
    }

    
}
