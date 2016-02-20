/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Repositorio;

import Modelo.GerenciamentoArquivos.TemplatePadrao;
import Modelo.GerenciamentoDadosGeotecnicos.Classe;
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
public class DaoTemplatePadrao extends DAO {
    
    public Integer salvar(TemplatePadrao tp) {
        return super.salvar(tp);
    }
   
    public Object buscarPeloId(int id) {
        return super.buscarPeloId(id, TemplatePadrao.class);
    }

    public Object buscarPeloNome(String nome) {
        return super.buscarPorCampo("nome", nome, TemplatePadrao.class);
    }

    public ArrayList<TemplatePadrao> buscarTodos() {
        ArrayList<TemplatePadrao> tp = (ArrayList<TemplatePadrao>) super.buscarTodos(TemplatePadrao.class);        
        if( !verificaNulo(tp) ){
            return tp;
        }else{
            return null;
        }        
    }

    public boolean excluir(int id) {
        return super.excluir(id, TemplatePadrao.class);
    }

    
    
    /*
    private Session session;
    private Transaction tx;        
    public DaoTemplatePadrao(){}
    
    
    public Integer salvarTemplatePadrao(TemplatePadrao o){
        
        session = HibernateUtil.openSession();
        Transaction tx = null;
        Integer identificador;
        try {
            tx = session.getTransaction();
            tx.begin();
            session.saveOrUpdate(o);
            identificador = (Integer) session.getIdentifier(o);
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            identificador = -1;
        }
        return identificador;       
    }
    
     public ArrayList<TemplatePadrao> buscarTodos(){
        
        session = HibernateUtil.openSession();
        ArrayList<TemplatePadrao> objetos = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(TemplatePadrao.class);
            ProjectionList projection = Projections.projectionList();
            objetos = (ArrayList<TemplatePadrao>) criteria.list();            
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        return objetos;
    }
    
     
    public TemplatePadrao buscarTemplate(int id){
        session = HibernateUtil.openSession();
        TemplatePadrao objeto = null;
        try {
            tx = session.getTransaction();
            tx.begin();
            Criteria criteria = session.createCriteria(TemplatePadrao.class);
            criteria.add(Restrictions.idEq(id));
            objeto = (TemplatePadrao) criteria.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        return objeto;
    }
    
    public boolean excluirTemplate(int id){
        TemplatePadrao c = buscarTemplate(id);
        
        Transaction tx = null;
        Integer identificador;
        try {
            tx = session.beginTransaction();		
            session.delete(c);
            tx.commit();
            return true;	
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            return false;
        }
    }
    
    
    public TemplatePadrao buscarTemplate(String nomeTEmplate){
        session = HibernateUtil.openSession();
        TemplatePadrao objeto = null;
        try {
            tx = session.getTransaction();
            tx.begin();
            Criteria criteria = session.createCriteria(TemplatePadrao.class);
            criteria.add(Restrictions.eq("nome", nomeTEmplate));          
            objeto = (TemplatePadrao) criteria.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        return objeto;
    }
    */
}
