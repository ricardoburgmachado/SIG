/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Repositorio;

import Modelo.GerenciamentoDadosGeotecnicos.Classe;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Ricardo
 */
public abstract class DAO{

    private Session sSalvarObj;   
    private static Session sSalvarList;   

    
    public void roolbackSalvarObj(){
        sSalvarObj.getTransaction().rollback();
    }
    public void roolbackSalvarList(){
        //System.out.println("session no roolbackSalvarList(): "+sSalvarList.toString());        
        System.out.println("a sessão sSalvarList está aberta e será abortada");
        System.out.println("Transação está ativa no roolbackSalvarList(): "+sSalvarList.getTransaction().isActive());
        //sSalvarList.getSessionFactory().getCurrentSession().getTransaction().rollback();
    }
    
    public boolean salvar(List objetos) {
        sSalvarList = HibernateUtil.openSession();
        try {            
            sSalvarList.getTransaction().begin();
            Iterator objIterator = objetos.iterator(); 
            while (objIterator.hasNext()){
                Object object = objIterator.next();
                sSalvarList.save(object);
            }
            System.out.println("Transação está ativa no salvar List: "+sSalvarList.getTransaction().isActive());
            sSalvarList.getTransaction().commit();
            System.out.println("Transação está ativa no salvar List: "+sSalvarList.getTransaction().isActive());
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            sSalvarList.getTransaction().rollback();
            return false;       
        }finally{ }
    }    
    
    public Integer salvar(Object objeto) {
        sSalvarObj = HibernateUtil.openSession();               
        Integer identificador;
        try {            
            sSalvarObj.beginTransaction();            
            sSalvarObj.saveOrUpdate(objeto);
            identificador = (Integer) sSalvarObj.getIdentifier(objeto);            
            sSalvarObj.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            sSalvarObj.getTransaction().rollback();
            identificador = -1;
        }finally{ }
        return identificador;       
    }
   
    public Object buscarPeloId(int id, Class<?> classe) {
        Session session = HibernateUtil.openSession().getSessionFactory().getCurrentSession();
        Object objeto = null;
        try {
            session.getTransaction().begin();
            Criteria criteria = session.createCriteria(classe);
            criteria.add(Restrictions.idEq(id));
            objeto = (Object) criteria.uniqueResult();
            if( !session.getTransaction().wasCommitted() ){
                session.getTransaction().commit();
            }
        } catch (HibernateException e) {
            System.err.println("Exception: "+e.getMessage());
            e.printStackTrace();
            if( session.isOpen() ){
                session.getTransaction().rollback();
            }
        }finally{ }
        return objeto;
    }

    public Object buscarPorCampo(String campo, String valor, Class<?> classe) {
        Session session = HibernateUtil.openSession().getSessionFactory().getCurrentSession();
        Object objeto = null;
        try {            
            session.beginTransaction();
            Criteria criteria = session.createCriteria(classe);
            criteria.add(Restrictions.eq(campo, valor));          
            objeto = criteria.uniqueResult();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally{}
        return objeto;
    }

    public Object buscarPorCampos(String[] campos, String[] valores, Class<?> classe) {
        Session session = HibernateUtil.openSession();        
        Object objeto = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(classe);
            
            for(int i=0; i < campos.length;i++){
                criteria.add(Restrictions.eq(campos[i], valores[i]));          
            }
            objeto = criteria.uniqueResult();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally{ }
        return objeto;
    }
 
    
    public ArrayList<?> buscarPorCampos(String[] campos, int[] valores, Class<?> classe) {
        Session session = HibernateUtil.openSession();        
        ArrayList<?> objetos = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(classe);      
            try{
                for(int i=0; i < campos.length;i++){
                    criteria.add(Restrictions.eq(campos[i], valores[i]));          
                }
                objetos = (ArrayList<?>) criteria.list();            
            }catch(ClassCastException e){
                System.err.println("Não foi possível fazer o cast com os resultados da classe: "+classe.getName()+" através da busca do Hibernate");
                return null;
            }
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally{ }
        return objetos;
    }
    
    /**
     * Retornar um conjunto de resultados
     * @param query
     * @param campos
     * @param valores
     * @param classe
     * @return 
     */
    public ArrayList<?> buscarQuery(String query, String[] campos, int[] valores, Class<?> classe) {
        Session session = HibernateUtil.openSession();
        ArrayList<?> retorno = null;
        try {            
            session.beginTransaction();
            SQLQuery sqlQuery = session.createSQLQuery(query);
            if(classe != null){
                sqlQuery.addEntity(classe);
            }
            for(int i=0; i < campos.length;i++){                
                //System.out.println("Query -> Campo: "+campos[i]+" | valor: "+valores[i]);
                sqlQuery.setParameter(campos[i], valores[i]);            
            }            
            try{
                retorno = (ArrayList<?>) sqlQuery.list();
            } catch (HibernateException e) {
                System.err.println("Não foi possível fazer o cast com os resultados da classe: "+classe.getName()+" através da busca do Hibernate");
            }            
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        return retorno;        
    }

    public Object buscarQuery(String query, Class<?> classe) {
        Session session = HibernateUtil.openSession();
        Object retorno = null;
        try {            
            session.beginTransaction();
            SQLQuery sqlQuery = session.createSQLQuery(query);
            if(classe != null){
                sqlQuery.addEntity(classe);
            }
            try{
                retorno = (Object) sqlQuery.uniqueResult();
            } catch (HibernateException e) {
                System.err.println("Não foi possível fazer o cast com o resultado da classe: "+classe.getName()+" através da busca do Hibernate");
            }            
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        return retorno;        
    }
    
    
    public ArrayList<?> buscarTodos(Class<?> classe) {
        Session session = HibernateUtil.openSession();        
        ArrayList<?> objetos = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(classe);      
            try{
                objetos = (ArrayList<?>) criteria.list();            
            }catch(ClassCastException e){
                System.err.println("Não foi possível fazer o cast com os resultados da classe: "+classe.getName()+" através da busca do Hibernate");
                return null;
            }
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally{  }
        return objetos;
    }

    public boolean excluir(int id, Class<?> classe) {        
        Session session = HibernateUtil.openSession();
        Object objeto = buscarPeloId(id, classe);        
        try {
            session.beginTransaction();		
            session.delete(objeto);
            session.getTransaction().commit();
            return true;	
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            return false;
        }finally{ }
    }
    
    /**
     * TRUE = NULO
     * FALSE = NÃO NULO
     * @param Object
     * @return boolean
     */
    public boolean verificaNulo(Object obj) {
        return obj == null;
    }

}
