/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.GerenciamentoExpressoes;

import Modelo.Excessoes.MalformedExpressionException;
import Modelo.GerenciamentoDadosGeotecnicos.Ensaio;
import Modelo.GerenciamentoDadosGeotecnicos.Ilha;
import Modelo.GerenciamentoDadosGeotecnicos.Parametro;
import Modelo.GerenciamentoDadosGeotecnicos.Valor;
import Repositorio.RepositorioElemento;
import Repositorio.RepositorioEnsaio;
import Repositorio.RepositorioIlha;
import Repositorio.RepositorioParametroPlotagem;
import Repositorio.RepositorioValor;
import com.towel.math.Expression;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Ricardo
 */
@Component
public class ImplementaProcessadorExpressoes extends ProcessadorExpressoes {
    
    @Autowired
    private RepositorioIlha repositorioIlha;
    @Autowired
    private RepositorioParametroPlotagem repositorioParametroPlotagem;
    @Autowired
    private RepositorioElemento repositorioElemento;
    @Autowired
    private RepositorioValor repositorioValor;
    @Autowired
    private RepositorioEnsaio repositorioEnsaio;
    
    private ArrayList<Parametro> parametrosEixoX;
    private JSONObject resultadoObjectJson;
    private JSONObject coordenadaObjectJson;
    private JSONArray coordenadaArrayJson;
    private JSONArray resultadosEixoXArrayJSON;   
    private JSONArray resultadosArrayJSON_RETORNO;       
    private ArrayList<Integer> idsEnsaios;        
    private ArrayList<Valor> valProfChaveEixoX;
    private String[] expSplit;
    private ArrayList<Parametro> parametrosEixoY;
    //private Expression expressionEixoY;
    //private Expression expressionEixoX;
    private ArvoreExpressao expressionEixoY;
    private ArvoreExpressao expressionEixoX;
    private ArvoreExpressao arvore;
    private String expBD;
    private ParametroPlotagem ppEixoY;
    private Ensaio ensaio;
    private Ilha ilha;
    private ParametroPlotagem ppEixoX;
    
    
    @Override
    public JSONArray processarExpressoes(Object[] idsEixoX, Integer[] idsIlhas, ArrayList<FatorAjuste> fatoresAjuste, boolean profundidadeChecked, int idEixoY) throws JSONException, MalformedExpressionException {
        
        ppEixoY = null;        
        resultadosEixoXArrayJSON = new JSONArray();
        resultadosArrayJSON_RETORNO = new JSONArray();
        
        if (idsEixoX != null && idsEixoX.length > 0) {

            for (Integer codIlha : idsIlhas) {//percorre todas as ilhas selecionadas                
                
                ilha = (Ilha) repositorioIlha.buscarIdentificacaoIlha(codIlha);                
                
                if( !profundidadeChecked){
                    resultadosEixoXArrayJSON = new JSONArray();
                }
                idsEnsaios = repositorioIlha.buscarIdsEnsaios(codIlha);
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");               
                System.out.println("@@@@@@@@@@@@@@@@@@@ INICIO LEITURA ILHA @@@@@@@@@@@@@@@@@@@@@@@@@@@");               
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");               
                
                System.out.println("INICIA A LEITURA DA ILHA "+codIlha);

                if(idsEnsaios != null && idsEnsaios.size() > 0){
                    System.out.println("IDS DOS ENSAIOS "+idsEnsaios.toString()+" DA ILHA: "+codIlha);
                }else{
                    System.out.println("NÃO FOI ENCONTRADO NENHUM ENSAIO PARA A ILHA: "+codIlha);
                }
                
                if(idsEnsaios != null && idsEnsaios.size() > 0){
                    
                    parametrosEixoX = new ArrayList<>();
                    resultadoObjectJson = new JSONObject();

                        for (Object idParamPlot : idsEixoX) {
                            
                            ppEixoX = (ParametroPlotagem) repositorioParametroPlotagem.buscarPeloId((int) idParamPlot);
                            String expressao = ppEixoX.getExpressao();
                            
                            System.out.println("PARAMETRO DE PLOTAGEM: "+ppEixoX.getNome()+" | id: "+ppEixoX.getId());

                            expSplit = expressao.split("@");
                           
                            valProfChaveEixoX = null;
                            
                            for(Integer idEnsaio: idsEnsaios){

                              ensaio = (Ensaio) repositorioEnsaio.buscarPeloId(idEnsaio);  
                              coordenadaArrayJson = new JSONArray();
                              parametrosEixoX = new ArrayList<>();                              
                              expBD = new String();
                                
                              System.out.println("######################################");
                              System.out.println("############### INÍCIO PRIMEIRA ITERAÇÃO ENSAIOS ###############");
                              System.out.println("######################################");        
                                //expressionEixoX = new Expression(); 
                                expressionEixoX = new ImplementaArvoreExpressao();
                                for (String elementoIndex : expSplit) {
                                    
                                    if (isInteger(elementoIndex)) {
                                        Elemento e = (Elemento) repositorioElemento.buscarPeloId(Integer.parseInt(elementoIndex));
                                        //System.out.println("ELEMENTO ENCONTRADO NO BD: "+e.getNome()+"  | tipo: "+e.getTipo()+" | valor: "+e.getValor());

                                        if (e.getTipo().equalsIgnoreCase("F")) {
                                            if (substituiValorEditadoFator(fatoresAjuste, e.getId()) != null) {//realiza a substituição do fator caso não seja retornado um valor nulo. Por exemplo, caso o usuário tenha editado o valor e informado um valor nulo para o fator
                                                expressionEixoX.setarVariavel(e.getNome(), substituiValorEditadoFator(fatoresAjuste, e.getId()));
                                                //System.out.println("setVariable()-> nome: "+e.getNome()+" | valor: "+substituiValorEditadoFator(fatoresAjuste, e.getId()));
                                            } else {//seta na formula o valor padrão do fator (oriundo do BD)
                                                expressionEixoX.setarVariavel(e.getNome(), e.getValor());
                                                //System.out.println("setVariable()-> nome: "+e.getNome()+" | valor: "+e.getValor());                                
                                            }
                                        }
                                        
                                        if (e.getTipo().equalsIgnoreCase("P")) {
                                            Parametro param = (Parametro) e;
                                            param.setValores(repositorioValor.buscar( e.getId(), idEnsaio));
                                            parametrosEixoX.add(param);
                                        }
                                        expBD += e.getNome();
                                    } else {
                                        expBD += elementoIndex;
                                    }
                                }
                            
                                System.out.println("TAMANHO PARAMETROS eixo X: "+parametrosEixoX.size());
                                expressionEixoX.setarExpressao(expBD);        
                                
                                System.out.println("EXPRESSION: "+expBD+" | id Ensaio: "+idEnsaio+" | ilha: "+ilha.getNome()+"("+ilha.getId()+")");
                                valProfChaveEixoX = repositorioValor.buscarProfundidadeChave(codIlha, ensaio.getClasse().getId(), idEnsaio);
                                
                                //if(valProfChaveEixoX == null || valProfChaveEixoX.isEmpty()){
                                    //System.out.println("A profundidade chave está nula ou vazia");
                                //}
                                
                                System.out.println("profundidadeChecked: "+profundidadeChecked);
                                
                                        if( !verificaParametrosInvalidos(parametrosEixoX) ){     
                                            
                                           
                                            resultadoObjectJson = constroirResultadoJSon(ilha, idEnsaio, ensaio.getClasse().getDescricao(), ppEixoX);
                                            
                                            for (int j = 0; j < valProfChaveEixoX.size(); j++) {//serve de profundidade padrão para todos os demais parametros envolvidos                                                                                                                                                                                                                                                                                                                              
                                                    for (Parametro parametro : parametrosEixoX) {                                           
                                                        expressionEixoX.setarVariavel(parametro.getNome(), parametro.getValores().get(j).getDados());                                                                    
                                                    }

                                                    coordenadaObjectJson = new JSONObject();
                                                    if( !expressionEixoX.resolver().isNaN() && !expressionEixoX.resolver().isInfinite()){
                                                        //System.out.println("Resultado resolve: "+expressionEixoX.resolve());
                                                        coordenadaObjectJson.append("x", expressionEixoX.resolver());                                                    
                                                    }else{                                                     
                                                        continue;
                                                    }
                                                    if(profundidadeChecked) {                                                        
                                                        coordenadaObjectJson.append("y", (valProfChaveEixoX.get(j).getDados() * -1 ));
                                                    }else{
                                                        coordenadaObjectJson.append("y", valProfChaveEixoX.get(j).getDados());
                                                    }
                                                    coordenadaArrayJson.put(coordenadaObjectJson);
                                            }                                           
                                                
                                            if( coordenadaArrayJson.length() > 0 ){
                                                
                                                resultadoObjectJson.put("coordenadas", coordenadaArrayJson);
                                                resultadosEixoXArrayJSON.put(resultadoObjectJson);
                                            }
                                        }
                                        
                                        System.out.println("######################################");
                                        System.out.println("############### FIM PRIMEIRA ITERAÇÃO ENSAIOS ###################");
                                        System.out.println("######################################");                   
                                
                                if (profundidadeChecked) {
                                   System.out.println("PROFUNDIDADE CHECKED");
                                   resultadosArrayJSON_RETORNO = resultadosEixoXArrayJSON;
                                } //FECHA VERIFICAÇÃO CHECKED
                            
                            }//FECHA PRIMEIRA ITERAÇÃO DOS ENSAIOS      
                            
                        for(Integer idEnsaio: idsEnsaios){//INCIA SEGUNDA ITERAÇÃO PELOS ENSAIOS
                            
                            System.out.println("######################################");
                            System.out.println("############### INÍCIO SEGUNDA ITERAÇÃO ENSAIOS ###################");
                            System.out.println("######################################");          
                            
                            ensaio = (Ensaio) repositorioEnsaio.buscarPeloId(idEnsaio); 
                            
                            if ( !profundidadeChecked ) {// O EIXO Y É UM PARÂMETRO SELECIONADO

                                
                                System.out.println("PROFUNDIDADE NÃO CHECKED");

                                //INÍCIO CRIAÇÃO DOS VALORES DE Y
                                parametrosEixoY = new ArrayList<>();
                                //expressionEixoY = new Expression();
                                expressionEixoY = new ImplementaArvoreExpressao();
                                ppEixoY = (ParametroPlotagem) repositorioParametroPlotagem.buscarPeloId(idEixoY);
                                String expressaoEixoY = ppEixoY.getExpressao();
                                String[] expSplitEixoY = expressaoEixoY.split("@");
                                String expBDEixoY = new String();
                                for (String elementoIndex : expSplitEixoY) {

                                    if (isInteger(elementoIndex)) {
                                        Elemento e = (Elemento) repositorioElemento.buscarPeloId(Integer.parseInt(elementoIndex));

                                        if (e.getTipo().equalsIgnoreCase("F")) {
                                            if (substituiValorEditadoFator(fatoresAjuste, e.getId()) != null) {//realiza a substituição do fator caso não seja retornado um valor nulo. Por exemplo, caso o usuário tenha editado o valor e informado um valor nulo para o fator
                                                expressionEixoY.setarVariavel(e.getNome(), substituiValorEditadoFator(fatoresAjuste, e.getId()));

                                            } else {//seta na formula o valor padrão do fator (oriundo do BD)
                                                expressionEixoY.setarVariavel(e.getNome(), e.getValor());
                                            }
                                        }
                                        if (e.getTipo().equalsIgnoreCase("P")) {
                                            Parametro param = (Parametro) e;
                                            param.setValores(repositorioValor.buscar(e.getId(), idEnsaio));
                                            parametrosEixoY.add(param);
                                        }
                                        expBDEixoY += e.getNome();
                                    } else {
                                        expBDEixoY += elementoIndex;
                                    }
                                }
                                
                                if( !verificaParametrosInvalidos(parametrosEixoY) ){  
                                    System.out.println("RESULTADO VERIFICAÇÃO PARAMETROS EIXO Y: NÃO POSSUI PARAMETROS INVÁLIDOS");
                                }else{
                                    System.out.println("RESULTADO VERIFICAÇÃO PARAMETROS EIXO Y: POSSUI PARAMETROS INVÁLIDOS");
                                }    
                            
                                expressionEixoY.setarExpressao(expBDEixoY);

                                System.out.println("EXPRESSION EIXO Y: " + expBDEixoY.toString());

                                ArrayList<Valor> valProfChaveEixoY = repositorioValor.buscarProfundidadeChave(codIlha, ensaio.getClasse().getId(), idEnsaio);
                                
                    if( !verificaParametrosInvalidos(parametrosEixoY) ){  
                                ArrayList<Double> valoresY = new ArrayList<>();

                                for (int j = 0; j < valProfChaveEixoY.size(); j++) {
                                    for (Parametro parametrosEixoY1 : parametrosEixoY) {
                                        //System.out.println("setVariable() NOME: " + parametrosEixoY1.getNome() + " | VALOR: " + parametrosEixoY1.getValores().get(j).getDados());
                                        expressionEixoY.setarVariavel(parametrosEixoY1.getNome(), parametrosEixoY1.getValores().get(j).getDados());
                                    }
                                    if( !expressionEixoY.resolver().isNaN() ){
                                        valoresY.add(expressionEixoY.resolver());
                                    }else{                                       
                                        continue;
                                    }
                                }
                                //FIM CRIAÇÃO DOS VALORES DE Y
                      
                                System.out.println("TAMANHO RESULTADOS EIXO Y: "+valoresY.size());
                                
                                for(int ii=0; ii < resultadosEixoXArrayJSON.length(); ii++){
                                    
                                    System.out.println("COMPARAR PROFUNDIDADES: X: "+resultadosEixoXArrayJSON.getJSONObject(ii).getJSONArray("coordenadas").length() +" | Y: "
                                            + ""+valProfChaveEixoY.size());
                                    
                                    if(isProfundidadeIgual(resultadosEixoXArrayJSON.getJSONObject(ii).getJSONArray("coordenadas"), valProfChaveEixoY)) {//as duas profundidades possuem tamanhos iguais}
                                        
                                        System.out.println("+++++++++ AS DUAS PROFUNDIDADES POSSUEM TAMANHOS IGUAIS | coordenadas x: "
                                                +resultadosEixoXArrayJSON.getJSONObject(ii).getJSONArray("coordenadas").length() +" | y: "+valProfChaveEixoY.size());
                                                 
                                            
                                            if(resultadosEixoXArrayJSON.getJSONObject(ii).getJSONArray("idEnsaio").getString(0).equals(idEnsaio.toString())){    
                                                resultadoObjectJson = constroirResultadoJSon(ilha, resultadosEixoXArrayJSON.getJSONObject(ii).getJSONArray("idEnsaio").getString(0),idEnsaio, resultadosEixoXArrayJSON.getJSONObject(ii).getJSONArray("classeEnsaio").getString(0),ensaio.getClasse().getDescricao(), resultadosEixoXArrayJSON.getJSONObject(ii).getJSONArray("eixoX").getString(0),resultadosEixoXArrayJSON.getJSONObject(ii).getJSONArray("eixoXID").getString(0), ppEixoY);
                                                coordenadaArrayJson = new JSONArray();
                                                for(int j=0; j < resultadosEixoXArrayJSON.getJSONObject(ii).getJSONArray("coordenadas").length();j++){

                                                    coordenadaObjectJson = new JSONObject();
                                                    coordenadaObjectJson.append("x",resultadosEixoXArrayJSON.getJSONObject(ii).getJSONArray("coordenadas").getJSONObject(j).getJSONArray("x").getDouble(0));                                                    
                                                    coordenadaObjectJson.append("y", valoresY.get(j));
                                                    coordenadaArrayJson.put(coordenadaObjectJson);
                                                }    
                                                resultadoObjectJson.put("coordenadas", coordenadaArrayJson);
                                                resultadosArrayJSON_RETORNO.put(resultadoObjectJson);
                                            }   
                                    }else{
                                        System.out.println("+++++++++ AS DUAS PROFUNDIDADES NÃO POSSUEM TAMANHOS IGUAIS | coordenadas x: "
                                                +resultadosEixoXArrayJSON.getJSONObject(ii).getJSONArray("coordenadas").length()+" | y: "+valProfChaveEixoY.size());
                                        
                                        if(resultadosEixoXArrayJSON.getJSONObject(ii).getJSONArray("coordenadas").length() > 0){//verifica se tem ao menos um resultado
                                        
                                            if(isProfundidadeMenor(resultadosEixoXArrayJSON.getJSONObject(ii).getJSONArray("coordenadas"), valProfChaveEixoY)) {//a profundidade de X é menor do que Y

                                                System.out.println("A PROFUNDIDADE DE X É MENOR DO QUE A PROFUNDIDADE DE Y");

                                                ArrayList<Integer> indices = new ArrayList<>();
                                                indices = obtemIndicesProfundidade(resultadosEixoXArrayJSON.getJSONObject(ii).getJSONArray("coordenadas") , valProfChaveEixoY);                                            
                                                System.out.println("INDICES: "+indices.toString());                                            
                                                ArrayList<Double> novosValores = obtemValoresIndices(valoresY, indices);                                                                                        
                                                System.out.println("TAMANHO NOVOS VALORES: "+novosValores.size());

                                                 
                                                if(resultadosEixoXArrayJSON.getJSONObject(ii).getJSONArray("idEnsaio").getString(0).equals(idEnsaio.toString())){
                                                    coordenadaArrayJson = new JSONArray();
                                                    resultadoObjectJson = constroirResultadoJSon(ilha, resultadosEixoXArrayJSON.getJSONObject(ii).getJSONArray("idEnsaio").getString(0), idEnsaio, resultadosEixoXArrayJSON.getJSONObject(ii).getJSONArray("classeEnsaio").getString(0), ensaio.getClasse().getDescricao(),resultadosEixoXArrayJSON.getJSONObject(ii).getJSONArray("eixoX").getString(0), resultadosEixoXArrayJSON.getJSONObject(ii).getJSONArray("eixoXID").getString(0), ppEixoY);

                                                    for(int x=0; x < resultadosEixoXArrayJSON.getJSONObject(ii).getJSONArray("coordenadas").length();x++){

                                                        coordenadaObjectJson = new JSONObject();                                                    
                                                        coordenadaObjectJson.append("x", resultadosEixoXArrayJSON.getJSONObject(ii).getJSONArray("coordenadas").getJSONObject(x).getJSONArray("x").getDouble(0) );
                                                        coordenadaObjectJson.append("y", novosValores.get(x));
                                                        coordenadaArrayJson.put(coordenadaObjectJson);
                                                    }    
                                                    resultadoObjectJson.put("coordenadas", coordenadaArrayJson);
                                                    resultadosArrayJSON_RETORNO.put(resultadoObjectJson);
                                                }    

                                            }else{//a profundidade de Y é menor do que X

                                                System.out.println("A PROFUNDIDADE DE Y É MENOR DO QUE A PROFUNDIDADE DE X");      

                                                ArrayList<Integer> indices = new ArrayList<>();
                                                indices = obtemIndicesProfundidade(valProfChaveEixoY, resultadosEixoXArrayJSON.getJSONObject(ii).getJSONArray("coordenadas") );       

                                                System.out.println("INDICES: "+indices.toString());      


                                                ArrayList<Double> novosValores = obtemValoresIndices(resultadosEixoXArrayJSON.getJSONObject(ii).getJSONArray("coordenadas"), indices);                                                                                        
                                                System.out.println("TAMANHO NOVOS VALORES: "+novosValores.size());
                                                
                                                if(resultadosEixoXArrayJSON.getJSONObject(ii).getJSONArray("idEnsaio").getString(0).equals(idEnsaio.toString())){
                                                    coordenadaArrayJson = new JSONArray();
                                                    resultadoObjectJson = constroirResultadoJSon(ilha, resultadosEixoXArrayJSON.getJSONObject(ii).getJSONArray("idEnsaio").getString(0), idEnsaio, resultadosEixoXArrayJSON.getJSONObject(ii).getJSONArray("classeEnsaio").getString(0), ensaio.getClasse().getDescricao(), resultadosEixoXArrayJSON.getJSONObject(ii).getJSONArray("eixoX").getString(0), resultadosEixoXArrayJSON.getJSONObject(ii).getJSONArray("eixoXID").getString(0), ppEixoY);

                                                    for(int x=0; x < valoresY.size();x++){

                                                        coordenadaObjectJson = new JSONObject();
                                                        coordenadaObjectJson.append("x", novosValores.get(x));                                                    
                                                        coordenadaObjectJson.append("y", valoresY.get(x));
                                                        coordenadaArrayJson.put(coordenadaObjectJson);
                                                        //System.out.println("NOVOS-> "+ "X: "+novosValores.get(x)+" | "+ "Y: "+valoresY.get(x) );                                                
                                                    }    
                                                    resultadoObjectJson.put("coordenadas", coordenadaArrayJson);
                                                    resultadosArrayJSON_RETORNO.put(resultadoObjectJson);
                                                }
                                                
                                            }//FECHA VERIFICAÇÃO DE PROFUNDIDADE                                      
                                        }//fecha verificação de tamanho de resultados > 0    
                                    }
                                }  
                    }//FECHA VERIFICA PARAMETROS INVALIDOS
                            }//fim profundidade checked
                        }//finaliza leitura parametros de plotagem          
                }//fecha if de verificação (size() > 0) de idsEnsaios
               System.out.println("######################################");
               System.out.println("############### FIM SEGUNDA ITERAÇÃO ENSAIOS ###################");
               System.out.println("######################################");          
                
               }//FECHA A SEGUNDA ITERAÇÃO PELOS ENSAIOS 
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");               
            System.out.println("@@@@@@@@@@@@@@@@@@@ FIM LEITURA ILHA @@@@@@@@@@@@@@@@@@@@@@@@@@@");               
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"); 
               
            }//finaliza leitura ids ilhas
        }
        System.out.println("Chegou ao final do método processarExpressoes()");
        return resultadosArrayJSON_RETORNO;
    }

    @Override
    public boolean validarExpressao(String s) {
        arvore = new ImplementaArvoreExpressao();
        arvore.setarExpressao(s);
        return arvore.avaliar();
    }
    

    
}
