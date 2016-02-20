package Modelo.GerenciamentoDadosGeotecnicos;

import Modelo.Excessoes.Excessao;
import Modelo.GerenciamentoArquivos.LeitorDocumento;
import Modelo.GerenciamentoArquivos.ImplemLeitorDocumento;
import Modelo.GerenciamentoArquivos.LeitorDocumentoV2;
import Modelo.GerenciamentoExpressoes.Elemento;
import Repositorio.RepositorioParametro;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ricado
 */
@Component
public class ImplementaConstrutorEvento extends ConstrutorEvento {

    private Ilha ilha;
    
    private RepositorioParametro repositorioParametro;
    
    private ImplemLeitorDocumento leitor;
    
    @Autowired(required = true)
    public ImplementaConstrutorEvento(ImplemLeitorDocumento leitor, RepositorioParametro repositParam){
        this.leitor = leitor;
        this.repositorioParametro = repositParam;
    }
    
    @Override    
    public Ilha construirEvento() {
        
        System.out.println("********************************* construirIlha()");
        ilha = new Ilha();
        construirDadodsIdentificacao(leitor);
        construirEnsaios(leitor);
        return ilha;                    
    }

    /**
     * Obtém os parametros registrados no banco de dados
     */
    public void verificaParametrosRegistrados() {

        ArrayList<Parametro> paramBD;        
        paramBD = (ArrayList<Parametro>) repositorioParametro.buscarTodos();
    }

    @Override
    public boolean construirDadodsIdentificacao(LeitorDocumento leitor) {

        Excessao excessao = null;

        if (verificaVazio(leitor.obterNome())) {
            excessao = new Excessao(excessao, "[Nome da ilha não informado]</br>");         
        } else {
            ilha.setNome(leitor.obterNome());
        }

        if (!verificaVazio(leitor.obterAutor())) {
            ilha.setAutor(leitor.obterAutor());
        }

        if (!verificaNulo(leitor.obterDataExecucao())) {
            ilha.setDataExecucao(leitor.obterDataExecucao());
        }

        if (!verificaVazio(leitor.obterEndereco())) {
            ilha.setEndereco(leitor.obterEndereco());
        }

        if (verificaVazio(leitor.obterReferencia())) {
            excessao = new Excessao(excessao, "[Referência da ilha não informado]</br>");
        } else {
            ilha.setReferencia(leitor.obterReferencia());
        }

        if (verificaVazio(leitor.obterEnsaiosRealizados())) {
            excessao = new Excessao(excessao, "[Ensaios realizados da ilha não informado]</br>");
        } else {
            ilha.setEnsaiosRealizados(leitor.obterEnsaiosRealizados());
        }

        if (verificaNulo(leitor.obterLatitude())) {
            excessao = new Excessao(excessao, "[Latitude da ilha não informado]</br>");
        } else {
            ilha.setLatitude(leitor.obterLatitude());
        }

        if (verificaNulo(leitor.obterLongitude())) {
            excessao = new Excessao(excessao, "[Longitude da ilha não informado]</br>");
        } else {
            ilha.setLongitude(leitor.obterLongitude());
        }

        if (verificaVazio(leitor.obterLocal())) {
            excessao = new Excessao(excessao, "[Local da ilha não informado]</br>");
        } else {
            ilha.setLocal(leitor.obterLocal());
        }

        if (excessao == null) {
            return true;
        } else {

            throw excessao;
        }
    }

    @Override
    public void construirEnsaios(LeitorDocumento leitor) {

        Ensaio ensaio;
        for (int i = 0; i < leitor.obterQuantidadeEnsaios(); i++) {

            if (leitor.obterClasseEnsaio(i) != null) {
                System.out.println("Classe ensaio: [" + i + "] " + leitor.obterClasseEnsaio(i));
                ensaio = new Ensaio();

                /* INICIO RESOLVE CLASSE do PARAMETRO */
                Classe classe = null;
                switch (leitor.obterClasseEnsaio(i)) {
                    case "Palheta":
                        classe = new Classe();
                        classe.setDescricao(leitor.obterClasseEnsaio(i));
                        classe.setId(1);
                        break;
                    case "Laboratorio":
                        classe = new Classe();
                        classe.setDescricao(leitor.obterClasseEnsaio(i));
                        classe.setId(2);
                        break;
                    case "CPTU":
                        classe = new Classe();
                        classe.setDescricao(leitor.obterClasseEnsaio(i));
                        classe.setId(3);
                        break;
                    case "DPP-CPTU":
                        classe = new Classe();
                        classe.setDescricao(leitor.obterClasseEnsaio(i));
                        classe.setId(4);
                    default:
                }
                ensaio.setClasse(classe);
                /* FIM RESOLVE CLASSE PARAMETRO */

                construirParametros(leitor, leitor.obterClasseEnsaio(i), i, ensaio);

                if(possuiParametroChave(ensaio)){
                    verificaTamanhoParametro(ensaio);
                    this.ilha.setEnsaio(ensaio);
                }
                
                
            }
        }
    }

    /**
     * Método responsável por analisar um parametro lido no template e verificar
     * se o mesmo já está registrado no banco de dados RETORNA VALOR DO BD =
     * parametro já está cadastrado no BD RETORNA 0 = parametro não está
     * cadastrado no BD
     */
    public int comparaParametrosBD(String nomeParametro) {

        ArrayList<Parametro> parametrosBD = (ArrayList<Parametro>) repositorioParametro.buscarTodos();
        for (Parametro p : parametrosBD) {
            if (p.getNome().equalsIgnoreCase(nomeParametro)) {
                return p.getId();
            }
        }
        return 0;
    }

    
    private void verificaTamanhoParametro(Ensaio ensaio){
        
        int tamProfChave=0;
        for(Parametro p: ensaio.getParametros()){
            if(p.isProfundidadeChave()){
                tamProfChave = p.getValores().size();//obtem o tamanho da profundidade chave do ensaio
            }
        }
        
        //percorre todos os parametros para verificar se é necessário exluí-lo do ensaio
        //for(Parametro p: ensaio.getParametros()){
        for(int i=0; i < ensaio.getParametros().size();i++){
            if( !ensaio.getParametros().get(i).isProfundidadeChave() ){//verifica todos os parametros que não são parametro chave do ensaio     
                //System.out.println("Verifica tamanho do parâmetro: if p.getValores().size() ("+ensaio.getParametros().get(i).getValores().size()+") < tamProfChave("+tamProfChave+")");
                if( ensaio.getParametros().get(i).getValores().size() < tamProfChave ){//verifica se o parametro possui tamanho menor do que tamanho do parametro chave
                    //System.out.println("ensaio.getParametros().remove(p) "+ensaio.getParametros().get(i).getNome()+" | ensaio: id "+ensaio.getId()+" classe"+ensaio.getClasse().getDescricao());  
                    ensaio.getParametros().remove(ensaio.getParametros().get(i));//remove o parametro do ensaio
                    i--;                    
                }else{
                    //System.out.println("Este parametro foi mantido no ensaio: "+ensaio.getParametros().get(i).getNome()+" | ensaio: id "+ensaio.getId()+" classe"+ensaio.getClasse().getDescricao());  
                }                
            }
        }
    }
    
    
    /**
     * Responsável por criar os parâmatros relacionados ao ensaio corrente
     *
     * @param leitor
     * @param classe
     * @param indexEnsaio
     * @param ensaio
     * @return
     */
    @Override
    public void construirParametros(LeitorDocumento leitor, String classe, int indexEnsaio, Ensaio ensaio) {

        //percorre os parametros
        for (Iterator<String> it = leitor.obterParametros(classe).iterator(); it.hasNext();) {
            String nomeParam = it.next();
            
            Parametro parametro = new Parametro(nomeParam);           
            parametro.setNomeFantasia(nomeParam);
            parametro.setSigla(leitor.obterSiglaParametro(nomeParam, classe, indexEnsaio));
            parametro.setNomeFantasia(leitor.obterNomeFantasiaParametro(nomeParam, classe, indexEnsaio));
            parametro.setUnidade(leitor.obterUnidadeParametro(nomeParam, classe, indexEnsaio));
            parametro.setClasse(ensaio.getClasse());
            ArrayList<Valor> valores = null;
            
            if(construirValores(leitor, classe, nomeParam, indexEnsaio) != null){
                valores = construirValores(leitor, classe, nomeParam, indexEnsaio);
                //verifica se o parametro tem ao menos um valor, caso não tenha este parametro será ignorado
                if(verificaValorMinimo(valores.size())) {
                    ensaio.setParametro(parametro);                    
                    int idBD = comparaParametrosBD(nomeParam);
                    if(idBD != 0) {//verifica se retornou algum id de parametro cadastrado no BD 
                        parametro.setId(idBD);                                                                       
                    }                   
                    parametro.setValores(valores);
                }
            }

            
        }
    }

    /**
     * Verifica se o ensaio possui o parametro chave. Isso será usado para a decisão de criação do ensaio.
     * @param classe
     * @param leitor
     * @return 
     */
    private boolean possuiParametroChave(Ensaio ensaio) {
        List<String> parChave = new LinkedList<>();
        parChave.add("ProfLab");
        parChave.add("ProfPalheta");
        parChave.add("ProfunCPTU");
        parChave.add("ProfCoeficCPTU");                
        for (Parametro p: ensaio.getParametros()) {
            
            for(String n: parChave){
                if(p.getNome().equalsIgnoreCase(n)){
                    p.setProfundidadeChave(true);
                    return true;
                }    
            }            
        }
        return false;
        
    }

    /**
     * Verifica se o parametro possui ao menos um valor 
     * TRUE = possui um ou mais valores
     * FALSE = não possui nenhum valor
     */
    private boolean verificaValorMinimo(int qtdValores) {
        if (qtdValores > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public ArrayList<Valor> construirValores(LeitorDocumento leitor, String classe, String parametro, int indexEnsaio) {
        List<Double> dados = leitor.obterValoresParametro(classe, parametro, indexEnsaio);
        ArrayList<Valor> valores = new ArrayList<>();
        Valor valor;
        if(dados != null){
            for (Double d : dados) {
                valor = new Valor();
                valor.setDados(d);
                //valor.setEnsaio(null);
                //valor.setParametro(null);
                valores.add(valor);
            }
            return valores;
        }else{
            return null;
        }
        
    }

    /**
     * TRUE = NULO FALSE = NÃO NULO
     *
     *
     * @param obj
     * @return
     */
    private boolean verificaNulo(Object obj) {
        return obj == null;
    }

    /**
     * TRUE = vazio FALSE = não vazio
     *
     * @param campo
     * @return
     */
    private boolean verificaVazio(String campo) {
        return verificaNulo(campo) || campo.equals("");
    }

}
