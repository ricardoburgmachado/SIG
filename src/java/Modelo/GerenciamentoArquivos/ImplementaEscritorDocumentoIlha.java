/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.GerenciamentoArquivos;

import Modelo.GerenciamentoDadosGeotecnicos.Ensaio;
import Modelo.GerenciamentoDadosGeotecnicos.Evento;
import Modelo.GerenciamentoDadosGeotecnicos.Ilha;
import java.io.IOException;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Ricardo
 */
//public class ImplementaEscritorDocumentoIlha implements EscritorDocumentoIlha {
public class ImplementaEscritorDocumentoIlha extends EscritorDocumentoIlha {
    
    private WritableCellFormat timesBoldUnderline;
    private WritableCellFormat times;
    private WritableWorkbook writableWorkbook;
    private WritableSheet excelSheet;

    public ImplementaEscritorDocumentoIlha(Object arquivo, BibliotecaArquivos biblioteca) throws WriteException, JSONException {
        super(arquivo, biblioteca);
        super.biblioteca.inserirArquivo(arquivo);
        super.biblioteca.definirFontes();
        writableWorkbook = (WritableWorkbook)arquivo;
    }
    
    @Override
    public void setarIdentificacao(Evento evento) {
 
        
        Ilha ilha = (Ilha) evento;       
        writableWorkbook.createSheet("Dados de Identificação", 0);
        WritableSheet sheet = writableWorkbook.getSheet(0);
        
        biblioteca.adicionarCabecalho(sheet, 1, 2, "Dados de Identificação:");//linha, coluna
        biblioteca.adicionarCabecalho(sheet, 2, 2, "Local:");//coluna, linha
        biblioteca.adicionarCabecalho(sheet, 3, 2, "Nome da ilha:");//coluna, linha
        biblioteca.adicionarCabecalho(sheet, 4, 2, "Endereço:");//coluna, linha
        biblioteca.adicionarCabecalho(sheet, 5, 2, "Autor dos ensaios:");//coluna, linha
        biblioteca.adicionarCabecalho(sheet, 6, 2, "Referência");//coluna, linha
        biblioteca.adicionarCabecalho(sheet, 7, 2, "Ensaios realizados:");//coluna, linha
        biblioteca.adicionarCabecalho(sheet, 8, 2, "Latitude:");//coluna, linha
        biblioteca.adicionarCabecalho(sheet, 9, 2, "Longitude:");//coluna, linha
        if(ilha.getDataExecucao() != null){
            biblioteca.adicionarCabecalho(sheet,10, 2, "Data de execução dos ensaios:");//coluna, linha
        }
        biblioteca.adicionarConteudo(sheet, 2, 3, ilha.getLocal()  );        
        biblioteca.adicionarConteudo(sheet, 3, 3, ilha.getNome()  );        
        biblioteca.adicionarConteudo(sheet, 4, 3, ilha.getEndereco()  );        
        biblioteca.adicionarConteudo(sheet, 5, 3, ilha.getAutor()  );        
        biblioteca.adicionarConteudo(sheet, 6, 3, ilha.getReferencia()  );        
        biblioteca.adicionarConteudo(sheet, 7, 3, ilha.getEnsaiosRealizados()  );        
        biblioteca.adicionarConteudo(sheet, 8, 3, ilha.getLatitude().toString()  );        
        biblioteca.adicionarConteudo(sheet, 9, 3, ilha.getLongitude().toString()  );
        if(ilha.getDataExecucao() != null){
            biblioteca.adicionarConteudo(sheet,10, 3, ilha.getDataExecucao().toString()  );
        }
    }

    @Override
    public void setarEnsaio(Ensaio ensaio, int index) throws IOException, WriteException, RowsExceededException, JSONException {

        writableWorkbook.createSheet("("+index+")"+ensaio.getClasse().getDescricao(), index);
        excelSheet = writableWorkbook.getSheet(index);        
        biblioteca.adicionarCabecalho(excelSheet, 0, 0, "Ensaio "+ensaio.getClasse().getDescricao());//linha, coluna

        for(int i = 0; i < ensaio.getParametros().size();i++){        
            biblioteca.adicionarCabecalho(excelSheet, 1, i, ensaio.getParametros().get(i).getNomeFantasia() );//linha, coluna
        }       
        for(int i=0, coluna=0; i < ensaio.getParametros().size();i++, coluna++){
            for(int j=0, linha=0; j < ensaio.getParametros().get(i).getValores().size();j++, linha++){                
                biblioteca.adicionarConteudo(excelSheet, linha+2, coluna, ensaio.getParametros().get(i).getValores().get(j).getDados().toString());
            }            
        }
    }

    @Override
    public void finalizarEscrita() {
        biblioteca.escreverArquivo();        
        biblioteca.fecharArquivo();
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /*
    private JExcel jExcel;
    private Ilha ilha;
    
    public ImplementaEscritorDocumentoIlha(JExcel jExcel, Ilha ilha){
        this.jExcel = jExcel;
        this.ilha = ilha;
    }
    
    @Override
    public void setarAutor() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setarNome() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setarDataColeta() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setarInstituicao() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setarEndereco() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setarEnsaios(WritableWorkbook workbook) throws IOException, WriteException, RowsExceededException, JSONException {
        jExcel.setEstrutura(workbook);
        jExcel.preencheEstruturaEvento(ilha);        
    }
    */


    
}
