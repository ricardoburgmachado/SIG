/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Repositorio;

import Modelo.GerenciamentoDadosGeotecnicos.Classe;
import Modelo.GerenciamentoUsuarios.Perfil;
import Modelo.GerenciamentoUsuarios.Usuario;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ricado
 */
@Component
public class DAOClasse extends DAO{
    
    
    public Integer salvar(Classe classe) {
        return super.salvar(classe);
    }
   
    public Object buscarPeloId(int id) {
        return super.buscarPeloId(id, Classe.class);
    }

    public Object buscarPorDescricao(String descricao) {
        return super.buscarPorCampo("descricao", descricao, Classe.class);
    }

    public ArrayList<Classe> buscarTodos() {
        return (ArrayList<Classe>) super.buscarTodos(Classe.class);
    }

    public boolean excluir(int id) {
        return super.excluir(id, Classe.class);
    }

    
    /*
    public Integer salvarClasse(Classe classe){
        
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
    
    public ArrayList<Classe> buscarTodasClasses(){
        
        session = HibernateUtil.openSession();
        
        ArrayList<Classe> objetos = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Classe.class);
            ProjectionList projection = Projections.projectionList();
            objetos = (ArrayList<Classe>) criteria.list();            
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        return objetos;
    }
    

    
    public Classe buscarClasse(int id){
        session = HibernateUtil.openSession();
        
        Classe objeto = null;
        try {
            tx = session.getTransaction();
            tx.begin();
            Criteria criteria = session.createCriteria(Classe.class);
            criteria.add(Restrictions.idEq(id));
            objeto = (Classe) criteria.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        return objeto;
    }
  
    
    public boolean excluirClasse(int id){
        Classe c = buscarClasse(id);
        
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
    
    public Classe buscarClasse(String descricao){
        session = HibernateUtil.openSession();
        
        Classe objeto = null;
        try {
            tx = session.getTransaction();
            tx.begin();
            Criteria criteria = session.createCriteria(Classe.class);
            criteria.add(Restrictions.eq("descricao", descricao));          
            objeto = (Classe) criteria.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        return objeto;
    }
    */
    
    
    
}
