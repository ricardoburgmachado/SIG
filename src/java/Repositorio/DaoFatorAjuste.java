/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Repositorio;

import Modelo.GerenciamentoArquivos.TemplatePadrao;
import Modelo.GerenciamentoDadosGeotecnicos.Classe;
import Modelo.GerenciamentoDadosGeotecnicos.Parametro;
import Modelo.GerenciamentoExpressoes.FatorAjuste;
import Modelo.GerenciamentoExpressoes.ValorElemento;
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
public class DaoFatorAjuste extends DAO {
    
    public Integer salvar(FatorAjuste f) {
        return super.salvar(f);
    }
   
    public Object buscarPeloId(int id) {
        return super.buscarPeloId(id, FatorAjuste.class);
    }

    public Object buscarPorNome(String nome) {
        return super.buscarPorCampo("nome", nome, FatorAjuste.class);
    }

    public ArrayList<FatorAjuste> buscarTodos() {
        ArrayList<FatorAjuste> tp = (ArrayList<FatorAjuste>) super.buscarTodos(FatorAjuste.class);        
        if( !verificaNulo(tp) ){
            return tp;
        }else{
            return null;
        }
    }

    public boolean excluir(int id) {
        return super.excluir(id, FatorAjuste.class);
    }

    
    
    /*
    private Session session;
    private Transaction tx;
    public DaoFatorAjuste(){}
    
    
    public ArrayList<FatorAjuste> buscarTodos(){        
        session = HibernateUtil.openSession();
        ArrayList<FatorAjuste> objetos = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(FatorAjuste.class);
            ProjectionList projection = Projections.projectionList();
            objetos = (ArrayList<FatorAjuste>) criteria.list();            
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }       
        return objetos;
    }
    
    public Integer salvarFatorAjuste(FatorAjuste classe){
        
        session = HibernateUtil.openSession();
        Transaction tx = null;
        Integer identificador;
        try {
            tx = session.getTransaction();
            tx.begin();
            session.saveOrUpdate(classe);
            identificador = (Integer) session.getIdentifier(classe);
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            identificador = -1;
        }
        return identificador;
       
    }
    
    public FatorAjuste buscarFatorAjuste(int id){
        session = HibernateUtil.openSession();
        FatorAjuste objeto = null;
        try {
            tx = session.getTransaction();
            tx.begin();
            Criteria criteria = session.createCriteria(FatorAjuste.class);
            criteria.add(Restrictions.idEq(id));
            objeto = (FatorAjuste) criteria.uniqueResult();
            if(!tx.wasCommitted())            
                tx.commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        }
        return objeto;
    }
    
     public boolean excluirFatorAjuste(int id){
        FatorAjuste c = buscarFatorAjuste(id);        
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
     
     
    public FatorAjuste buscarFatorAjuste(String nomeFator){
        session = HibernateUtil.openSession();
        FatorAjuste objeto = null;
        try {
            tx = session.getTransaction();
            tx.begin();
            Criteria criteria = session.createCriteria(FatorAjuste.class);
            criteria.add(Restrictions.eq("nome", nomeFator));          
            objeto = (FatorAjuste) criteria.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        return objeto;
    }
    */
    
    
    
    
    
    
    
    
    
    
    
    
}
