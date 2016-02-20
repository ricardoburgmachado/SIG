/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Repositorio;

import Modelo.GerenciamentoDadosGeotecnicos.Classe;
import Modelo.GerenciamentoDadosGeotecnicos.TipoParametro;
import Modelo.GerenciamentoExpressoes.Simbolo;
import java.util.ArrayList;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
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
public class DaoTipoParametro extends DAO {
    
    public Object buscarPeloId(int id) {
        return super.buscarPeloId(id, TipoParametro.class);
    }

    public Object buscarPorNome(String nome) {
        return super.buscarPorCampo("nome", nome, TipoParametro.class);
    }

    public ArrayList<TipoParametro> buscarTodos() {
        return (ArrayList<TipoParametro>) super.buscarTodos(TipoParametro.class);
    }
    
    
    /*
    private Session session;
    private Transaction tx;    
    public ArrayList<TipoParametro> buscarTodos(){        
        session = HibernateUtil.openSession();
        ArrayList<TipoParametro> objetos = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(TipoParametro.class);
            ProjectionList projection = Projections.projectionList();
            objetos = (ArrayList<TipoParametro>) criteria.list();            
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }       
        return objetos;
    }
    
    public TipoParametro buscar(int id){
        session = HibernateUtil.openSession();
        TipoParametro objeto = null;
        try {
            tx = session.getTransaction();
            tx.begin();
            Criteria criteria = session.createCriteria(TipoParametro.class);
            criteria.add(Restrictions.idEq(id));
            objeto = (TipoParametro) criteria.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        return objeto;
    }
    
     public TipoParametro buscar(String nomeTipoParametro){
        session = HibernateUtil.openSession();
        TipoParametro objeto = null;
        try {
            tx = session.getTransaction();
            tx.begin();
            Criteria criteria = session.createCriteria(TipoParametro.class);
            criteria.add(Restrictions.eq("nome", nomeTipoParametro));          
            objeto = (TipoParametro) criteria.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        return objeto;
    }
    */
    
    
    
}
