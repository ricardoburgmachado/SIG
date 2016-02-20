
package Repositorio;

import Modelo.Excessoes.Excessao;
import Modelo.GerenciamentoDadosGeotecnicos.Classe;
import Modelo.GerenciamentoDadosGeotecnicos.Ensaio;
import Modelo.GerenciamentoDadosGeotecnicos.Ilha;
import Modelo.GerenciamentoDadosGeotecnicos.Parametro;
import Modelo.GerenciamentoDadosGeotecnicos.Valor;
import Modelo.GerenciamentoUsuarios.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ricado
 */
@Repository
public class RepositorioIlha extends Repositorio{
    
    private DaoIlha daoIlha;
    private DaoEnsaio daoEnsaio;
    private DaoParametro daoParametro;
    private DaoValor daoValor;
    
    @Autowired(required = true)
    public RepositorioIlha (DaoEnsaio daoEnsaio, DaoParametro daoParametro, DaoValor daoValor, DaoIlha daoIlha){        
        this.daoEnsaio = daoEnsaio;
        this.daoParametro = daoParametro;        
        this.daoIlha = daoIlha;
        this.daoValor = daoValor;
    }

    public ArrayList<Ilha> buscarPeloUsuario(Usuario u) {
        return daoIlha.buscarPeloUsuario(u);                
    }
    
    @Override
    public ArrayList<Ilha> buscarTodos() {
        return daoIlha.buscarTodos();
    }
 
    public ArrayList<Integer> buscarIdsEnsaios(int idIlha){    
        return daoIlha.buscarIdsEnsaios(idIlha);
    }
    
    @Override
    public Integer salvar(Object o) throws Excessao {
        return -1;
    }
    
    @Override
    public Integer salvar(Object o, HttpServletRequest request) throws Excessao {       
        Excessao excessao = null;
        Ilha ilha = null;
        List<Valor> valores = new ArrayList();
        int idIlha = 0;
        
        if(o == null){              
            excessao = new Excessao(excessao,"[Ilha inválida]</br>");
        }
        
        if(excessao == null ){
                        if( !castValido(o) ){                                
                return -1;
            }else{
                ilha = (Ilha)o;
            }                
            idIlha = daoIlha.salvar(ilha);        
            ilha.setId(idIlha);        
            for(Ensaio e: ilha.getEnsaios()){
                e.setIlha(ilha);
                e.setId(daoEnsaio.salvar(e));

                for(Parametro p: e.getParametros()){
                    p.setEnsaio(e);

                    //essa verificação abaixo é feita para contornar a situação do parametro no cadastro de template. Pois diretamente na 
                    //construção da ilha e consequentemente dos seus parametros, não é possível verificar se um parâmetro com o mesmo nome está sendo criado. Isso implicava na criação 
                    //duplicada de parâmetros com o mesmo nome 
                    Parametro pTemp = (Parametro) daoParametro.buscarPeloNome(p.getNome());                
                    if(pTemp != null){ 
                        //System.out.println("###################### FOI ENCONTRADO O PARAMETRO REPETIDO NO BD: "+pTemp.getId()+" | nome: "+pTemp.getNome());
                        p.setId( pTemp.getId());
                    }else{
                        p.setId( daoParametro.salvar(p));
                    }

                    for(Valor v: p.getValores()){
                        v.setParametro(p);
                        v.setEnsaio(e);
                        //daoValor.salvar(v);
                        valores.add(v);
                    }
                }
            }
            daoValor.salvar(valores);
            if(desfazerCadastro(request, idIlha)){
                return -1;
            }else{
                return idIlha;
            }
        }else{
            throw excessao;
        }
    }
    
    @Override
    public boolean desfazerCadastro(HttpServletRequest request, int id) {
        boolean roolback = (boolean) request.getSession().getAttribute("roolback");
        System.out.println("roolback: "+roolback);
        if(roolback){
            request.getSession().setAttribute("roolback", false); 
            daoIlha.excluir(id);
            System.out.println("O roolback (cadastro ilha) foi executado");
            return true;
        }else{
            request.getSession().setAttribute("roolback", false); 
            return false;
        }  
    }

    @Override
    public Integer editar(Object o) throws Excessao {
        System.out.println("Funcionalidade desativada.");
        return -1;
    }

    /**
     * Retornar a ilha completa
     * @param idIlha
     * @return
     * @throws Excessao 
     */
    @Override
    public Object buscarPeloId(int idIlha) throws Excessao {        
        Ilha ilha =  (Ilha) daoIlha.buscarPeloId(idIlha);
        ArrayList<Integer> idsEnsaios = daoIlha.buscarIdsEnsaios(idIlha);          
        for(Integer idEns: idsEnsaios){            
            ArrayList<Integer> idsParametros = daoParametro.buscarPorEnsaio(idEns);
            Ensaio ensaio = (Ensaio) daoEnsaio.buscarPeloId(idEns);
            for(Integer idParam: idsParametros){
                Parametro parametro = (Parametro) daoParametro.buscarPeloId(idParam);
                ArrayList<Valor> valores = daoValor.buscarPorEnsaioParametro(idEns, idParam);
                parametro.setValores(valores);                
                ensaio.setParametro(parametro);
            }
            ilha.setEnsaio(ensaio);
        }
        return ilha;
    }

    /**
     * Retorna a ilha apenas com os dados de identificação
     * @param idIlha
     * @return
     * @throws Excessao 
     */
    public Object buscarIdentificacaoIlha(int idIlha) throws Excessao {
        return (Ilha) daoIlha.buscarPeloId(idIlha);
    }
    
    @Override
    public boolean excluir(int id) throws Excessao {
        return daoIlha.excluir(id);
    }

    @Override
    public boolean castValido(Object o) throws Excessao {
        try{
            Ilha c = (Ilha) o;                       
            return true;
        }catch(ClassCastException e){
            System.err.println("Cast de Ilha inválido");
            return false;
        }
    }

    @Override
    public boolean verificaRegistroDuplicado(Object o) throws Excessao {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean verificaRegistroDuplicadoEdicao(Object o) throws Excessao {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /*
    public boolean salvar(Ilha ilha){
        
        int idIlha = daoIlha.salvarIlha(ilha);
        
        ilha.setId(idIlha);        
        for(Ensaio e: ilha.getEnsaios()){
            e.setIlha(ilha);
            e.setId(daoEnsaio.salvar(e));
            
            for(Parametro p: e.getParametros()){
                p.setEnsaio(e);
                
                //essa verificação abaixo é feita para contornar a situação do parametro no cadastro de template. Pois diretamente na 
                //construção da ilha e consequentemente dos seus parametros, não é possível verificar se um parâmetro com o mesmo nome está sendo criado. Isso implicava na criação 
                //duplicada de parâmetros com o mesmo nome 
                Parametro pTemp = (Parametro) daoParametro.buscarPeloNome(p.getNome());                
                if(pTemp != null){ 
                    //System.out.println("###################### FOI ENCONTRADO O PARAMETRO REPETIDO NO BD: "+pTemp.getId()+" | nome: "+pTemp.getNome());
                    p.setId( pTemp.getId());
                }else{
                    p.setId( daoParametro.salvar(p));
                }
                
                for(Valor v: p.getValores()){
                    v.setParametro(p);
                    v.setEnsaio(e);
                    daoValor.salvar(v);
                }
            }
        }
        return true;
    }
    */
    
    /*
    public boolean salvar(Ilha ilha, HttpServletRequest request){
        
        Session sessionIlha = HibernateUtil.openSession();        
        sessionIlha.getTransaction().begin();
        
        Session sessionEnsaio = HibernateUtil.openSession();        
        sessionEnsaio.getTransaction().begin();
        
        Session sessionParam = HibernateUtil.openSession();        
        sessionParam.getTransaction().begin();
        
        Session sessionValor = HibernateUtil.openSession();        
        sessionValor.getTransaction().begin();
        
        int idIlha = daoIlha.salvar(ilha);
        sessionIlha.getTransaction().commit();
        ilha.setId(idIlha);        
        for(Ensaio e: ilha.getEnsaios()){
            e.setIlha(ilha);
            e.setId(daoEnsaio.salvar(e));            
            sessionEnsaio.getTransaction().commit();
            for(Parametro p: e.getParametros()){
                p.setEnsaio(e);                
               
        
                Parametro pTemp = (Parametro) daoParametro.buscarPeloNome(p.getNome());                
                if(pTemp != null){ 
                    //System.out.println("###################### FOI ENCONTRADO O PARAMETRO REPETIDO NO BD: "+pTemp.getId()+" | nome: "+pTemp.getNome());
                    p.setId( pTemp.getId());
                }else{
                    p.setId( daoParametro.salvar(p));
                    sessionParam.getTransaction().commit();
                }
                for(Valor v: p.getValores()){
                    v.setParametro(p);
                    v.setEnsaio(e);
                    daoValor.salvar(v);
                    sessionValor.getTransaction().commit();
                }                
            }            
        }
        boolean roolback = (boolean) request.getSession().getAttribute("roolback");
        if(roolback){
            System.out.println("O Rookback será executado");
            sessionIlha.getTransaction().rollback();     
            sessionEnsaio.getTransaction().rollback();     
            sessionParam.getTransaction().rollback();     
            sessionValor.getTransaction().rollback();     
            request.getSession().setAttribute("roolback", false);
            return false;
        }else{
            System.out.println("O Rookback NÃO será executado");
            request.getSession().setAttribute("roolback", false);
            return true;
        }        
    }
    */
    
    
}
