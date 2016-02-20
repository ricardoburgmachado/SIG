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
public abstract class ProcessadorExpressoes {

    
    public abstract JSONArray processarExpressoes(Object[] idsEixoX, Integer[] idsIlhas, ArrayList<FatorAjuste> fatoresAjuste, boolean profundidadeChecked, int idEixoY) throws JSONException, MalformedExpressionException ;
    
    protected boolean isInteger(String s){    
        try{ Integer.parseInt(s); return true;
        }catch(NumberFormatException e){return false;}      
    }
    
    /**
     * Retorna o valor do fator baseado no id, porém é feita a verificação se o
     * valor não é nulo
     *
     * @param fatores
     * @param id
     * @return
     */
    protected Double substituiValorEditadoFator(ArrayList<FatorAjuste> fatores, int id) {
        if(fatores == null){
            System.out.println("FATORES PARA EDIÇÃO NÃO ESTÁ NULL");
        }else{
            for (FatorAjuste f : fatores) {
                if (id == f.getId() && f.getValor() != null) {
                    return f.getValor();
                }
            }
            System.out.println("FATORES PARA EDIÇÃO ESTÁ NULL");        
        }
        return null;
    }
    
    /**
     * Verifica todos os valores contidos dentro dos parâmetros. Caso tenha algum parâmetro tenha quantidade igual a zero.
     * Possui parametro inválido: retorna TRUE
     * Não possui parametro inválido: retorna FALSE
     */
    protected boolean verificaParametrosInvalidos(ArrayList<Parametro> parametros){    
        for(Parametro p: parametros){            
            System.out.println("Parametro verificado: "+p.getNome());
            if(p.getValores() == null || p.getValores().size() == 0){
                System.out.println("PARAMETRO ZERADO: "+p.getNome()+" | id: "+p.getId());
                return true;
            }            
        }
        return false;
    }
    
        /**
     * Método responsável por analisar duas profundidades para retornar TRUE
     * caso os dois parametros tenha tamanhos iguais
     *
     * @param profBase
     * @param prof2
     * @return
     */
    protected boolean isProfundidadeIgual(JSONArray profBase, ArrayList<Valor> prof2) {
        if (profBase.length() == prof2.size()) {
            return true;
        } else {
            return false;
        }
    }    
    
    
        /**
     * Método responsável por analisar duas profundidade para retornar TRUE caso
     * o primeiro parametro(profundidade base) seja menor
     *
     * @param profBase
     * @param prof2 para ser comparada com a profundidade base
     * @return
     */
    protected boolean isProfundidadeMenor(JSONArray profBase, ArrayList<Valor> prof2) {
        if (profBase.length() < prof2.size()) {
            return true;
        } else {
            return false;
        }
    }
    
     /**
     * Método utilizado para construir um array de índices baseado no Eixo X
     * @param prof1
     * @param prof2
     * @return
     * @throws JSONException 
     */
    protected ArrayList<Integer> obtemIndicesProfundidade(JSONArray prof1, ArrayList<Valor> prof2) throws JSONException {
        ArrayList<Integer> retorno = new ArrayList<>();
        System.out.println("obtemIndicesProfundidade(): ");
        System.out.println("Primeiro parametro.length(): "+prof1.length()+" | segundo parametro.size(): "+prof2.size());
        if(prof1.length() < prof2.size()) {//o prof1 será o base
            for (int i=0; i < prof1.length();i++) {               
                retorno.add(obtemIndiceMaisProximo(prof2, prof1.getJSONObject(i).getJSONArray("y").getDouble(0)  ));
            }
        }
        return retorno;
    } 
    
    /**
     * Responsável por analisar e contruir um novo array a partir da comparação
     * de indices (parâmetros)
     *
     * @param valores
     * @param indices
     * @return
     */
    protected ArrayList<Double> obtemValoresIndices(ArrayList<Double> valores, ArrayList<Integer> indices) {
        ArrayList<Double> valRetorno = new ArrayList<>();
        for (int i = 0; i < valores.size(); i++) {
            for (int j = 0; j < indices.size(); j++) {
                if (i == indices.get(j)) {
                    System.out.println("indice: (" + i + ") == indiceValor(" + j + ") | valor :" + valores.get(i));
                    valRetorno.add(valores.get(i));
                }
            }
        }
        return valRetorno;
    }
    
    protected ArrayList<Double> obtemValoresIndices(JSONArray valores, ArrayList<Integer> indices) throws JSONException {
        ArrayList<Double> valRetorno = new ArrayList<>();
        for (int i = 0; i < valores.length(); i++) {
            for (int j = 0; j < indices.size(); j++) {
                if (i == indices.get(j)) {
                    System.out.println("indice: (" + i + ") == indiceValor(" + j + ") | valor :" + valores.get(i));                    
                    valRetorno.add(valores.getJSONObject(i).getJSONArray("y").getDouble(0));
                }
            }
        }
        return valRetorno;
    }
       
    
    /**
     * Método utilizado para construir um array de índices baseado no Eixo Y
     * @param prof1
     * @param prof2
     * @return
     * @throws JSONException 
     */
    protected ArrayList<Integer> obtemIndicesProfundidade(ArrayList<Valor> prof1, JSONArray prof2) throws JSONException {
        ArrayList<Integer> retorno = new ArrayList<>();
        System.out.println("obtemIndicesProfundidade(): ");
        System.out.println("Primeiro parametro.length(): "+prof1.size()+" | segundo parametro.size(): "+prof2.length());
        if(prof1.size() < prof2.length()) {//o prof2 será o base
            for(int i=0; i < prof1.size() ;i++) {               
                retorno.add(obtemIndiceMaisProximo(prof2, prof1.get(i).getDados()  ));
            }
        }
        return  retorno;        
    }
    
    protected int obtemIndiceMaisProximo(JSONArray array, Double value) throws JSONException {
        System.out.println("início obtemIndiceMaisProximo(): ");        
        if (array.length() > 0) {
            int nearestMatchIndex = 0;
            int retorno = -1;
           for (int i = 1; i < array.length(); i++) {           
               if(Math.abs(value - array.getJSONObject(nearestMatchIndex).getJSONArray("y").getDouble(0) ) > Math.abs(value - array.getJSONObject(i).getJSONArray("y").getDouble(0) )) {
                    nearestMatchIndex = i;
                    retorno = i;
                }else{
                    retorno = nearestMatchIndex;
                }
            }
            return retorno;
        } else {
            return -1;
        }        
    }
    
        /**
     * Utilizado exclusivamente no método balancearResultados
     *
     * @param array
     * @param value
     * @return
     */
    protected int obtemIndiceMaisProximo(ArrayList<Valor> array, Double value) {
        System.out.println("início obtemIndiceMaisProximo(): ");
        if (array.size() > 0) {
            int nearestMatchIndex = 0;
            int retorno = -1;
           for (int i = 1; i < array.size(); i++) {           
                if (Math.abs(value - array.get(nearestMatchIndex).getDados()) > Math.abs(value - array.get(i).getDados())) {
                    nearestMatchIndex = i;
                    retorno = i;
                }else{
                    retorno = nearestMatchIndex;
                }
            }
            return retorno;
        } else {
            return -1;
        }        
    }

    protected JSONObject constroirResultadoJSon(Ilha ilha, int idEnsaio,  String classeEns, ParametroPlotagem ppEixoX) throws JSONException{
        JSONObject resultadoObjectJson = new JSONObject();        
        resultadoObjectJson.append("idIlha",""+ilha.getId());
        resultadoObjectJson.append("nomeIlha",""+ilha.getLocal());
        resultadoObjectJson.append("referenciaIlha",""+ilha.getReferencia());                                                                                            
        resultadoObjectJson.append("idEnsaio",""+idEnsaio);
        resultadoObjectJson.append("classeEnsaio", ""+classeEns);
        resultadoObjectJson.append("eixoX",""+ppEixoX.getNome()+"("+ppEixoX.getUnidade()+")");
        resultadoObjectJson.append("eixoXID",""+ppEixoX.getId());
        resultadoObjectJson.append("eixoY",""+"Profundidade (m)");
        resultadoObjectJson.append("eixoYID","0");
        resultadoObjectJson.append("aviso","");
        return resultadoObjectJson;                                    
    }                                        
    
    protected JSONObject constroirResultadoJSon(Ilha ilha, String idEnsaio1, int idEnsaio2, String classeEns1, String classeEns2, String eixoX,String eixoXID, ParametroPlotagem ppEixoY) throws JSONException{
        JSONObject resultadoObjectJson = new JSONObject();
        resultadoObjectJson.append("idIlha",""+ilha.getId());
        resultadoObjectJson.append("nomeIlha",""+ilha.getLocal());
        resultadoObjectJson.append("referenciaIlha",""+ilha.getReferencia());
        resultadoObjectJson.append("idEnsaio", idEnsaio1+"/"+idEnsaio2);
        resultadoObjectJson.append("classeEnsaio", classeEns1+"/"+classeEns2);
        resultadoObjectJson.append("eixoX",""+eixoX);
        resultadoObjectJson.append("eixoXID",""+eixoXID);                                            
        resultadoObjectJson.append("eixoY",""+ppEixoY.getNome()+"("+ppEixoY.getUnidade()+")");
        resultadoObjectJson.append("eixoYID",""+ppEixoY.getId());
        resultadoObjectJson.append("aviso","");					
        return resultadoObjectJson;
    }    
    
    public abstract boolean validarExpressao(String s);
}
