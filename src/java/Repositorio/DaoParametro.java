package Repositorio;

import Modelo.GerenciamentoDadosGeotecnicos.Classe;
import Modelo.GerenciamentoDadosGeotecnicos.Parametro;
import java.util.ArrayList;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
@Component
public class DaoParametro extends DAO{

    public Integer salvar(Parametro p) {
        return super.salvar(p);
    }
   
    public Object buscarPeloId(int id) {
        return super.buscarPeloId(id, Parametro.class);
    }

    public Object buscarPeloNome(String nome) {
        return super.buscarPorCampo("nome", nome, Parametro.class);
    }

    public ArrayList<Parametro> buscarTodos() {
        return (ArrayList<Parametro>) super.buscarTodos(Parametro.class);
    }
    
    public boolean excluir(int id) {
        return super.excluir(id, Parametro.class);
    }
    
    
    public ArrayList<Integer> buscarPorEnsaio(int idEnsaio){        
        String query = "SELECT DISTINCT valor.fk_id_parametro FROM valor WHERE valor.fk_id_ensaio = :idEnsaio";
        return (ArrayList<Integer>) buscarQuery(query, new String[]{ "idEnsaio",}, new int[]{ idEnsaio }, null );    
    }
    
    /*
    private Session session;
    private Transaction tx;    
    public int salvarParametro(Parametro p) {
        session = HibernateUtil.openSession();
        Transaction tx = null;
        Integer identificador;
        try {
            tx = session.getTransaction();
            tx.begin();
            session.saveOrUpdate(p);
            identificador = (Integer) session.getIdentifier(p);
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            identificador = -1;
        }
        return identificador;       
    }
    
    public int salvarParametro(Parametro p, Session session) {        
        Integer identificador;
        try {
            //tx = session.getTransaction();
            //tx.begin();
            //session.getTransaction().begin();
            session.saveOrUpdate(p);
            identificador = (Integer) session.getIdentifier(p);
            //tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            identificador = -1;
        }
        return identificador;       
    }

    public ArrayList<Parametro> buscarParametros() {        
        session = HibernateUtil.openSession();
        ArrayList<Parametro> objetos = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Parametro.class);
            ProjectionList projection = Projections.projectionList();
            objetos = (ArrayList<Parametro>) criteria.list();            
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        System.out.println("QTD. RETORNO BD PARAMETROS: "+objetos.size());
        return objetos;
    }
    
    public Parametro buscarParametro(int id){
    
        session = HibernateUtil.openSession();
        Parametro objeto = null;
        try {
            tx = session.getTransaction();
            tx.begin();
            Criteria criteria = session.createCriteria(Parametro.class);
            criteria.add(Restrictions.idEq(id));
            objeto = (Parametro) criteria.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        return objeto;
    }
    
    public Parametro buscarParametro(String nomeParam){
    
        session = HibernateUtil.openSession();
        Parametro objeto = null;
        try {
            tx = session.getTransaction();
            tx.begin();
            Criteria criteria = session.createCriteria(Parametro.class);
            criteria.add(Restrictions.eq("nome", nomeParam));          
            objeto = (Parametro) criteria.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        return objeto;
    }
    */
}
