/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Repositorio;

import Modelo.GerenciamentoDadosGeotecnicos.Classe;
import Modelo.GerenciamentoDadosGeotecnicos.Ensaio;
import Modelo.GerenciamentoDadosGeotecnicos.Ilha;
import Modelo.GerenciamentoUsuarios.Perfil;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

/**
 *
 * @author ricado
 */
@Component
public class DaoEnsaio extends DAO {
    
    public Integer salvar(Ensaio ensaio) {
        return super.salvar(ensaio);
    }
   
    public Object buscarPeloId(int id) {
        return super.buscarPeloId(id, Ensaio.class);
    }
    
    
    /*
    private Session session;
    private Transaction tx;
    public int salvarEnsaio(Ensaio ensaio){
        session = HibernateUtil.openSession();        
        Transaction tx = null;
        Integer identificador;
        try {
            tx = session.getTransaction();
            tx.begin();
            session.saveOrUpdate(ensaio);
            identificador = (Integer) session.getIdentifier(ensaio);
            tx.commit();            
        } catch (HibernateException ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
            identificador = -1;
        }
        return identificador;       
    }

    public int salvarEnsaio(Ensaio ensaio, Session session){
        Integer identificador;
        try {
            //tx = session.getTransaction();
            //session.getTransaction().begin();
            session.saveOrUpdate(ensaio);
            identificador = (Integer) session.getIdentifier(ensaio);
            //tx.commit();            
        } catch (HibernateException ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
            identificador = -1;
        }
        return identificador;       
    }

    
     public Ensaio buscarEnsaio(int codigo) {

        session = HibernateUtil.openSession();
        Ensaio objeto = null;
        try {
            tx = session.getTransaction();
            tx.begin();
            Criteria criteria = session.createCriteria(Ensaio.class);
            criteria.add(Restrictions.idEq(codigo));
            objeto = (Ensaio) criteria.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        return objeto;
    }*/

    
}
