/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Repositorio;

import Modelo.GerenciamentoDadosGeotecnicos.Classe;
import Modelo.GerenciamentoDadosGeotecnicos.Ilha;
import Modelo.GerenciamentoUsuarios.Perfil;
import Modelo.GerenciamentoUsuarios.Usuario;
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
public class DaoUsuario extends DAO {
    
    public Integer salvar(Usuario usuario) {
        return super.salvar(usuario);
    }
    
    public ArrayList<Usuario> buscarTodos() {
        ArrayList<Usuario> tp = (ArrayList<Usuario>) super.buscarTodos(Usuario.class);
        if (!verificaNulo(tp)) {
            return tp;
        } else {
            return null;
        }
    }
    
    public Object buscarPeloId(int id) {
        return super.buscarPeloId(id, Usuario.class);
    }

    public Object buscarPeloNome(String nome) {
        return super.buscarPorCampo("nome", nome, Usuario.class);
    }
    
    public Object buscarPeloNomeSenha(String nome, String senha) {
        return super.buscarPorCampos(new String[]{ "nome","senha"}, new String[]{ nome, senha}, Usuario.class);
    }

    public boolean excluir(int id) {
        return super.excluir(id, Usuario.class);
    }
    
    
    /*
    private Session session;
    private Transaction tx;    
    public DaoUsuario(){}
    
    public Integer salvarUsuario(Usuario usuario){
        
        System.out.println("perfil USUARIO DAO: "+usuario.getPerfil().getNome());
        
        //session = HibernateUtil.openSession();
        session = HibernateUtil.openSession();
        Transaction tx = null;
        Integer identificador;
        try {
            tx = session.getTransaction();
            tx.begin();
            session.saveOrUpdate(usuario);
            identificador = (Integer) session.getIdentifier(usuario);
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            identificador = -1;
        }
        return identificador;
       
    }
    
    public ArrayList<Perfil> buscarTodosPerfis(){
        
        //session = HibernateUtil.openSession();
        session = HibernateUtil.openSession();
        ArrayList<Perfil> objetos = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Perfil.class);
            ProjectionList projection = Projections.projectionList();
            objetos = (ArrayList<Perfil>) criteria.list();            
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        return objetos;
    }
    
    public ArrayList<Usuario> buscarTodosUsuarios(){

        //session = HibernateUtil.openSession();
        session = HibernateUtil.openSession();
        ArrayList<Usuario> objetos = null;
        try {
            session.beginTransaction();
            Criteria criteria = session.createCriteria(Usuario.class);
            ProjectionList projection = Projections.projectionList();
            objetos = (ArrayList<Usuario>) criteria.list();            
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        return objetos;
    }
    
    public Usuario buscarUsuario(int id){
        //session = HibernateUtil.openSession();
        session = HibernateUtil.openSession();
        Usuario objeto = null;
        try {
            tx = session.getTransaction();
            tx.begin();
            Criteria criteria = session.createCriteria(Usuario.class);
            criteria.add(Restrictions.idEq(id));
            objeto = (Usuario) criteria.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        return objeto;
    }
    
    public Usuario buscarUsuario(String nome, String senha){
        //session = HibernateUtil.openSession();
        session = HibernateUtil.openSession();
        Usuario objeto = null;
        try {
            tx = session.getTransaction();
            tx.begin();
            Criteria criteria = session.createCriteria(Usuario.class);
            criteria.add(Restrictions.eq("nome", nome));
            criteria.add(Restrictions.eq("senha", senha));
            objeto = (Usuario) criteria.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        return objeto;
    }
    
    public Usuario buscarUsuario(String nome){
        //session = HibernateUtil.openSession();
        session = HibernateUtil.openSession();
        Usuario objeto = null;
        try {
            tx = session.getTransaction();
            tx.begin();
            Criteria criteria = session.createCriteria(Usuario.class);
            criteria.add(Restrictions.eq("nome", nome));
            objeto = (Usuario) criteria.uniqueResult();
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        return objeto;
    }
    */
    
}
