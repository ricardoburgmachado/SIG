/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Repositorio;

import Modelo.GerenciamentoDadosGeotecnicos.Valor;
import Modelo.GerenciamentoExpressoes.ValorElemento;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

/**
 *
 * @author ricado
 */
@Component
public class DaoValor extends DAO {

    
    public Integer salvar(Valor valor) {
        return super.salvar(valor);
    }

    @Override
    public boolean salvar(List list) {
        return super.salvar(list);
    }
    
    @Override
    public void roolbackSalvarList(){
        super.roolbackSalvarList();
    }
    public ArrayList<Valor> buscar(int idParametro, int idEnsaio){
        String query = "SELECT * FROM valor WHERE valor.fk_id_parametro = :idParametro  AND valor.fk_id_ensaio = :idEnsaio ";
        return (ArrayList<Valor>) super.buscarQuery(query, new String[]{ "idParametro","idEnsaio"}, new int[]{ idParametro, idEnsaio}, Valor.class );       
    }
    
    public ArrayList<Valor> buscarTodos() {
        return (ArrayList<Valor>) super.buscarTodos(Valor.class);
    }
    
    public ArrayList<Valor> buscarProfundidadeChave(int idEnsaio){
        String query = "SELECT * FROM valor WHERE valor.fk_id_ensaio = :idEnsaio AND valor.fk_id_parametro "
                + "IN (SELECT elemento.id_elemento FROM elemento WHERE elemento.profundidade_chave = 1)";
        return (ArrayList<Valor>) super.buscarQuery(query, new String[]{ "idEnsaio",}, new int[]{ idEnsaio}, Valor.class );
    }
    
    /**
     * Retorna valores vinculados ao ensaio e ao parametro informado
     * @param idEnsaio
     * @param idParametro
     * @return 
     */
    public ArrayList<Valor> buscarPorEnsaioParametro(int idEnsaio, int idParametro){
        String query = "SELECT * FROM valor WHERE valor.fk_id_ensaio = :idEnsaio AND valor.fk_id_parametro = :idParametro";
        return (ArrayList<Valor>) super.buscarQuery(query, new String[]{ "idEnsaio","idParametro"}, new int[]{ idEnsaio, idParametro}, Valor.class );
    }

    
    
    /*
    private Session session;
    private Transaction tx;
    public int salvarValor(Valor v){
        session = HibernateUtil.openSession();
        Transaction tx = null;
        Integer identificador;
        try {
            tx = session.getTransaction();
            tx.begin();
            session.saveOrUpdate(v);
            identificador = (Integer) session.getIdentifier(v);
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            identificador = -1;
        }
        return identificador;       
    }

    public int salvarValor(Valor v, Session session){                
        Integer identificador;
        try {
            //tx = session.getTransaction();
            //tx.begin();
            //session.getTransaction().begin();
            session.saveOrUpdate(v);
            identificador = (Integer) session.getIdentifier(v);
            //tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            identificador = -1;
        }
        return identificador;       
    }
    */
    
    /**
     * Realiza uma busca por valores no BD baseado no id da ilha e id do parametro
     * @param idIlha
     * @param idParametro
     * @return 
     */
    /*
    public ArrayList<Valor> buscar(int idIlha, int idParametro, int idEnsaio){
        
        System.out.println("Parametros buscar valor -> idIlha: "+idIlha+" | idParametro: "+idParametro+" | idEnsaio: "+idEnsaio);
               
        
        session = HibernateUtil.openSession();
        ArrayList<Valor> retorno = null;
        try {
            retorno = new ArrayList<Valor>();
            tx = session.getTransaction();
            tx.begin();            
            String sql = "SELECT * FROM valor WHERE valor.fk_id_parametro = :idParametro  AND valor.fk_id_ensaio = :idEnsaio ";
            
            SQLQuery query = session.createSQLQuery(sql);
            query.addEntity(Valor.class);
            //query.setParameter("idIlha", idIlha);
            query.setParameter("idParametro", idParametro);
            query.setParameter("idEnsaio", idEnsaio);
            retorno = (ArrayList<Valor>) query.list();
            tx.commit();            
            
            System.out.println("QTD resultados da busca: "+retorno.size());
            
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        return retorno;
    }
    */
    /*
    public ArrayList<Valor> buscarProfundidadeChave(int idIlha, int idClasse, int idEnsaio){
        
        System.out.println("DADOS PARAMETRO buscarProfundidadeChave()-> idIlha: "+idIlha+ " | idClasse: "+idClasse+" | idEnsaio: "+idEnsaio);
        
        session = HibernateUtil.openSession();
        ArrayList<Valor> retorno = null;
        try {
            retorno = new ArrayList<Valor>();
            tx = session.getTransaction();
            tx.begin();            
            String sql = "SELECT * FROM valor WHERE valor.fk_id_ensaio = :idEnsaio AND valor.fk_id_parametro IN "
                    + " (SELECT elemento.id_elemento FROM elemento WHERE elemento.profundidade_chave = 1)";
            
            SQLQuery query = session.createSQLQuery(sql);
            query.addEntity(Valor.class);
            //query.setParameter("idIlha", idIlha);
            //query.setParameter("idClasse", idClasse);
            query.setParameter("idEnsaio", idEnsaio);            
            retorno = (ArrayList<Valor>) query.list();
            tx.commit();            
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        return retorno;
    }
    */
    
}
