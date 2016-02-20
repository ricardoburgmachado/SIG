/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.GerenciamentoArquivos;

import Modelo.Excessoes.Excessao;
import com.sun.tools.jxc.gen.config.Classes;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.Cell;
import jxl.CellType;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.NumberFormats;
import jxl.write.WritableWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ricado
 */
@Component
public class LeitorDocumentoV2 implements LeitorDocumento {

    
    private static Workbook arquivo;
    
    private static final int linhaInicialPalheta = 7;//utilizada no momento da leitura do primeiro valor de um parâmetro da classe Palheta
    private static final int linhaInicialCPTU = 7;//utilizada no momento da leitura do primeiro valor de um parâmetro da classe CPTU
    private static final int linhaInicialLab = 7;//utilizada no momento da leitura do primeiro valor de um parâmetro da classe Laboratório
    private static final int linhaInicialCoefAdensamentoCPTU = 7;//utilizada no momento da leitura do primeiro valor de um parâmetro da classe Coef.AdensamentoCPTU
    private static final int linhaUnidade = 6;
    private static final int linhaSigla = 5;
    private static final int linhaNomeFantasia = 3;
    
    private Map<String, Integer> colParametrosPalheta;//possui o mapeamento das colunas de cada parametro da classe Palheta do template
    private Map<String, Integer> colParametrosCPTU;//possui o mapeamento das colunas de cada parametro da classe CPTU do template
    private Map<String, Integer> colParametrosLab;//possui o mapeamento das colunas de cada parametro da classe Lab do template
    private Map<String, Integer> colParametrosCoefAdensamentoCPTU;//possui o mapeamento das colunas de cada parametro da classe coefAdensamentoCPTU do template
    private List<String> classes;//armazena as possíveis classes dos ensaios
    
    private Sheet sheet;

    public ImplemBibliotecaArquivos biblioteca;  

    /**
     *
     * @param arquivo
     * @param excessao
     * @param arq
     * @return
     * @throws Exception
     */
    @Override
    public boolean iniciaLeitura(Object arquivo, Excessao excessao) {
        
        System.out.println("************************************* iniciaLeitura()");
        
        System.out.println("Arquivo leitorV2: "+arquivo.getClass().toString());
        System.out.println("Arquivo leitorV2: "+arquivo.toString());
        
        
        MultipartFile multipartFile = (MultipartFile) arquivo;
        
        configurarColunasParametros();
        
        if(arquivo == null){
            excessao = new Excessao(excessao, "[Arquivo não encontrado]</br>");
        }
       
        try {        
            this.arquivo = Workbook.getWorkbook(multipartFile.getInputStream());
            biblioteca = new ImplemBibliotecaArquivos();
            
            System.out.println("Arquivo this leitorV2: "+this.arquivo.getClass().toString());
            System.out.println("Arquivo this leitorV2: "+this.arquivo.toString());
            
            biblioteca.inserirArquivo(this.arquivo);
            return true;
        } catch (IOException ex) {
            excessao = new Excessao(excessao, "[Problemas na leitura do arquivo]</br>");
            Logger.getLogger(LeitorDocumentoV2.class.getName()).log(Level.SEVERE, null, ex);
            throw excessao;
        } catch (BiffException ex) {
            excessao = new Excessao(excessao, "[Problemas na leitura do arquivo]</br>");
            Logger.getLogger(LeitorDocumentoV2.class.getName()).log(Level.SEVERE, null, ex);
            throw excessao;
        }
           
    }

    
    @Override
    public String obterNome() {        
        return obterCelulaString(4, 3, 0);        
    }

    @Override
    public String obterLocal() {
        return obterCelulaString(3, 3, 0);        
    }

    @Override
    public String obterEndereco() {
        return obterCelulaString(5, 3, 0);
    }

    @Override
    public String obterAutor() {
        return obterCelulaString(6, 3, 0);
    }

    @Override
    public String obterReferencia() {
        return obterCelulaString(7, 3, 0);
    }

    @Override
    public String obterEnsaiosRealizados() {
        return obterCelulaString(8, 3, 0);
    }

    @Override
    public Double obterLatitude() {
        return obterCelulaDouble(9, 3, 0);
    }

    @Override
    public Double obterLongitude() {
        return obterCelulaDouble(10, 3, 0);
    }

    @Override
    public Date obterDataExecucao() {
        return obterCelulaDate(11, 3, 0);
    }

    @Override
    public int obterQuantidadeEnsaios() {
        return biblioteca.obterQuantidadeEnsaios();
    }

    

    /**
     *
     * @param classe
     * @param parametro
     * @param indexEnsaio
     * @return
     */
    @Override
    public List obterValoresParametro(String classe, String parametro, int indexEnsaio) {

        //sheet = arquivo.getSheet(indexEnsaio);
        Sheet sheet = (Sheet) biblioteca.acessarEnsaio(indexEnsaio);
        //int qtdLinhas = sheet.getRows();//estimativa de quantidade de linhas
        int qtdLinhas = biblioteca.obterQuantidadeLinhasEnsaio(sheet);
        int colParametro = obtemColunaParametro(parametro, classe);
        List<Double> r = new ArrayList<Double>();        
        for (int k = obtemLinhaInicial(classe); k < qtdLinhas; k++) {//começa por 9 por causa das linhas
            //Cell celula = sheet.getCell(colParametro, k);//coluna , linha
            Cell celula = (Cell) biblioteca.obterConteudo(sheet, k, colParametro);
            //verifica se a primeira linha a ser lida possui um valor válido. Caso seja válido finaliza método.
            if(k == obtemLinhaInicial(classe) && !(celula.getType() == CellType.NUMBER_FORMULA || celula.getType() == CellType.NUMBER)){            
                return null;
            }
            
            if(celula.getType() == CellType.NUMBER_FORMULA || celula.getType() == CellType.NUMBER) {
                
                NumberCell nc = (NumberCell) celula;
                r.add(nc.getValue());
                //System.out.println("valor: " + nc.getValue()+"["+k+"]["+colParametro+"]"+" | Parâmetro: "+parametro);
            }
        }
        return r;
    }

    
    /**
     * 
     * @param index do ensaio a ser lido
     * @return 
     */
    @Override
    public String obterClasseEnsaio(int index) {
        System.out.println("obterClasseEnsaio()-> index: "+index);
        System.out.println("obterClasseEnsaio()-> "+obterCelulaString(1, 2, index));
        if(obterCelulaString(1, 2, index) != null && (
                obterCelulaString(1, 2, index).equalsIgnoreCase("Palheta")
                || obterCelulaString(1, 2, index).equalsIgnoreCase("Laboratorio")
                || obterCelulaString(1, 2, index).equalsIgnoreCase("CPTU")
                || obterCelulaString(1, 2, index).equalsIgnoreCase("DPP-CPTU") ) ){
            return obterCelulaString(1, 2, index);
        }else{
            return null;
        }
    }

    private String obterCelulaString(int linha, int coluna, int planilha) {
        Cell ts;
        ts = (Cell) biblioteca.obterConteudo(planilha, linha, coluna);
        if(ts!= null && ts.getContents() != null && !"".equals(ts.getContents())){
          return ts.getContents();  
        }else{
          return null;
        }
    }

    private Double obterCelulaDouble(int linha, int coluna, int planilha) {
        Cell ts;
        ts = (Cell) biblioteca.obterConteudo(planilha, linha, coluna);
        if(ts.getContents() == "") {
            return null;
        }else{
            return Double.valueOf(replace(ts.getContents(), ",", "."));
        }        
    }

    private String replace(String palavra, String atual, String novo){    
        return palavra.replace(atual, novo);
    }
    
    private Date obterCelulaDate(int linha, int coluna, int planilha) {
        try {
            Cell ts;
            ts = (Cell) biblioteca.obterConteudo(planilha, linha, coluna);
            DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
            
            if(ts!= null && ts.getContents() != null && ts.getContents() != "" && ts.getType() == CellType.DATE){
                Date date = (Date) formatter.parse(ts.getContents());            
                return date;
            }
        } catch (ParseException ex) {
            //Logger.getLogger(LeitorDocumentoV1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private int obtemLinhaInicial(String classe) {

        if (classe.equalsIgnoreCase("laboratorio")) {
            return this.linhaInicialLab;
        } else if (classe.equalsIgnoreCase("palheta")) {
            return this.linhaInicialPalheta;
        } else if (classe.equalsIgnoreCase("cptu")) {
            return this.linhaInicialCPTU;
        } else if (classe.equalsIgnoreCase("DPP-CPTU")) {
            return this.linhaInicialCoefAdensamentoCPTU;
        } else {
            return -1;
        }
    }

    private void configurarColunasParametros() {

        colParametrosLab = new HashMap<String, Integer>();
        colParametrosLab.put("ProfLab", 1);
        colParametrosLab.put("UmidNatSolo", 2);
        colParametrosLab.put("DensidRealGraos", 3);
        colParametrosLab.put("PesoEspNatLab", 5);
        colParametrosLab.put("PesoEspecAgua", 6);
        colParametrosLab.put("TensaoSobreAdensamento", 7);
        colParametrosLab.put("TensaoVertTotalLab", 8);
        colParametrosLab.put("PressaoHidroLAB", 9);
        colParametrosLab.put("IndiceCompressao", 12);
        colParametrosLab.put("IndiceExpansao", 13);
        colParametrosLab.put("IndiceVaziosInicial", 15);
        colParametrosLab.put("LimiteLiquidez", 17);
        colParametrosLab.put("LimitePlasticidade", 18);
        colParametrosLab.put("PorcentMatOrganica", 20);

        
        colParametrosPalheta = new HashMap<String, Integer>();
        colParametrosPalheta.put("ProfPalheta", 1);
        colParametrosPalheta.put("ResistNaoDrenada", 2);
        colParametrosPalheta.put("ResistNaoDrenadaAlmogada", 3);

        colParametrosCPTU = new HashMap<String, Integer>();
        colParametrosCPTU.put("ProfunCPTU", 1);        
        colParametrosCPTU.put("ResistPontaCorrigida", 2);
        colParametrosCPTU.put("AtritoLateral", 3);
        colParametrosCPTU.put("Poropressaou1", 4);
        colParametrosCPTU.put("Poropressaou2", 5);        
        colParametrosCPTU.put("PesoEspecNatSolo", 7);
        colParametrosCPTU.put("PressaoHidroCPTU", 8);
        colParametrosCPTU.put("TensaoVerticalTotal", 9);      
        
        colParametrosCoefAdensamentoCPTU = new HashMap<String, Integer>();
        colParametrosCoefAdensamentoCPTU.put("ProfCoeficCPTU", 1);
        colParametrosCoefAdensamentoCPTU.put("CoefAdensHorizontPreAden_u1", 2);
        colParametrosCoefAdensamentoCPTU.put("CoefAdensHorizontPreAden_u2", 3);                
    }
    
    private void configurarClasses(){
    
        classes.add("palheta");
        classes.add("laboratorio");
        classes.add("cptu");
        classes.add("DPP-CPTU");        
    }
   
    private int obtemColunaParametro(String parametro, String classe) {        
        if(classe.equalsIgnoreCase("palheta")){
            return this.colParametrosPalheta.get(parametro);
        }else if(classe.equalsIgnoreCase("laboratorio")){
            return this.colParametrosLab.get(parametro);
        }else if(classe.equalsIgnoreCase("cptu")){
            return this.colParametrosCPTU.get(parametro);
        }else if(classe.equalsIgnoreCase("DPP-CPTU")){
            return this.colParametrosCoefAdensamentoCPTU.get(parametro);
        }else{
            return -1;
        }
    }

    
    @Override
    public Set<String> obterParametros(String classe){    
        if(classe.equalsIgnoreCase("palheta")){
            return this.colParametrosPalheta.keySet();
        }else if(classe.equalsIgnoreCase("laboratorio")){    
            return this.colParametrosLab.keySet();
        }else if(classe.equalsIgnoreCase("cptu")){    
            return this.colParametrosCPTU.keySet();
        }else if(classe.equalsIgnoreCase("DPP-CPTU")){    
            return this.colParametrosCoefAdensamentoCPTU.keySet();
        }else{
            return null;
        }        
    }

    @Override
    public String obterUnidadeParametro(String parametro, String classe, int planilha) {
        int col = obtemColunaParametro(parametro, classe);
        return obterCelulaString(linhaUnidade, col, planilha);
    }

    @Override
    public String obterSiglaParametro(String parametro, String classe, int planilha) {
        int col = obtemColunaParametro(parametro, classe);
        return obterCelulaString(linhaSigla, col, planilha);
    }

    
    /**
     * TRUE = vazio
     * FALSE = não vazio
     * @param campo
     * @return 
     */
    private boolean verificaVazio(String campo) {

        return verificaNulo(campo) || campo.equals("");
    }

    /**
     * TRUE = NULO
     * FALSE = NÃO NULO
     * @param obj
     * @return 
     */
    private boolean verificaNulo(Object obj) {

        return obj == null;
    }

    @Override
    public String obterNomeFantasiaParametro(String parametro, String classe, int planilha) {
        int col = obtemColunaParametro(parametro, classe);
        return obterCelulaString(linhaNomeFantasia, col, planilha);
    }
    
    
    
}
