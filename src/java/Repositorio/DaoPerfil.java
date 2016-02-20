/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repositorio;

import Modelo.GerenciamentoDadosGeotecnicos.Classe;
import Modelo.GerenciamentoUsuarios.Perfil;
import java.util.ArrayList;
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
public class DaoPerfil extends DAO {

    public Object buscarPeloId(int id) {
        return super.buscarPeloId(id, Perfil.class);
    }

    public ArrayList<Perfil> buscarTodos() {
        ArrayList<Perfil> tp = (ArrayList<Perfil>) super.buscarTodos(Perfil.class);
        if (!verificaNulo(tp)) {
            return tp;
        } else {
            return null;
        }
    }
    /*
     private Session session;
     private Transaction tx;
    
     public DaoPerfil(){ }
    
     public Perfil buscarPerfil(int codigo) {

     session = HibernateUtil.openSession();
     Perfil objeto = null;
     try {
     tx = session.getTransaction();
     tx.begin();
     Criteria criteria = session.createCriteria(Perfil.class);
     criteria.add(Restrictions.idEq(codigo));
     objeto = (Perfil) criteria.uniqueResult();
     tx.commit();
     } catch (HibernateException e) {
     e.printStackTrace();
     session.getTransaction().rollback();
     }
     return objeto;
     }*/

}
