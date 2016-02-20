/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Repositorio;

import Modelo.GerenciamentoDadosGeotecnicos.Classe;
import Modelo.GerenciamentoExpressoes.Elemento;
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
public class DaoElemento extends DAO {

    
    public Integer salvar(Elemento elemento) {
        return super.salvar(elemento);
    }
    
    public Object buscarPorNome(String nome) {
        return super.buscarPorCampo("nome", nome, Elemento.class);
    }
   
    public Object buscarPeloId(int id) {
        return super.buscarPeloId(id, Elemento.class);
    }

    public ArrayList<Elemento> buscarTodos() {
        return (ArrayList<Elemento>) super.buscarTodos(Elemento.class);
    }

    public boolean excluir(int id) {
        return super.excluir(id, Elemento.class);
    }

    
    /*
    private Session session;
    private Transaction tx;    
    public Integer salvar(Elemento objeto){
        
        session = HibernateUtil.openSession();
        Transaction tx = null;
        Integer identificador;
        try {
            tx = session.getTransaction();
            tx.begin();
            session.saveOrUpdate(objeto);
            identificador = (Integer) session.getIdentifier(objeto);
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            identificador = -1;
        }
        return identificador;       
    }
    
    public Elemento buscar(int id){
        session = HibernateUtil.openSession();
        Elemento objeto = null;
        try {
            tx = session.getTransaction();
            tx.begin();
            Criteria criteria = session.createCriteria(Elemento.class);
            criteria.add(Restrictions.idEq(id));
            objeto = (Elemento) criteria.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        return objeto;
    }
    
    public ArrayList<Elemento> buscarTodos(){
        
        session = HibernateUtil.openSession();
        ArrayList<Elemento> objetos = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Elemento.class);
            ProjectionList projection = Projections.projectionList();
            objetos = (ArrayList<Elemento>) criteria.list();            
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        return objetos;
    }
    */
    
    
   
    
}
