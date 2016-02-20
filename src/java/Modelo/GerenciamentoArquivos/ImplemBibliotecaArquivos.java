/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modelo.GerenciamentoArquivos;
import Modelo.GerenciamentoDadosGeotecnicos.Ensaio;
import Modelo.GerenciamentoDadosGeotecnicos.Evento;
import Modelo.GerenciamentoDadosGeotecnicos.Ilha;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.Cell;
import jxl.CellView;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.CellFormat;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
/**
 *
 * @author Ricardo
 */
@Component
public class ImplemBibliotecaArquivos implements BibliotecaArquivos {

    private WritableCellFormat timesBoldUnderline;
    private WritableCellFormat times;
    private WritableWorkbook writableWorkbook;    

    @Override
    public void definirFontes() throws WriteException, JSONException {
        NumberFormat typeDouble = new NumberFormat("##.##");
        WritableCellFormat wTypeDouble = new WritableCellFormat(typeDouble);
        // Cria o tipo de fonte como TIMES e tamanho
        WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
        // Define o formato da célula
        times = new WritableCellFormat(times10pt);
        // Efetua a quebra automática das células
        times.setWrap(true);
        
        // Cria a fonte em negrito com underlines
        WritableFont times10ptBoldUnderline = new WritableFont(
        WritableFont.ARIAL, 10, WritableFont.BOLD, false);
        //UnderlineStyle.SINGLE);
        timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
        // Efetua a quebra automática das células
        timesBoldUnderline.setWrap(true);
    }

    @Override
    public void inserirArquivo(Object arquivo) {
        this.writableWorkbook = (WritableWorkbook) arquivo;
    }

    @Override
    public void escreverArquivo() {
        try {
            writableWorkbook.write();
        } catch (IOException ex) {
            Logger.getLogger(ImplemBibliotecaArquivos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void fecharArquivo() {
        try {    
            writableWorkbook.close();
        } catch (IOException ex) {
            Logger.getLogger(ImplemBibliotecaArquivos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (WriteException ex) {
            Logger.getLogger(ImplemBibliotecaArquivos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void adicionarConteudo(Object planilha, int linha, int coluna, String conteudo) {
        WritableSheet writableSheet = (WritableSheet) planilha;
        Label label;
        label = new Label(coluna, linha, conteudo, times);
        try {
            writableSheet.addCell(label);
        } catch (WriteException ex) {
            Logger.getLogger(ImplemBibliotecaArquivos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void adicionarCabecalho(Object planilha, int linha, int coluna, String conteudo) {
        WritableSheet writableSheet = (WritableSheet) planilha;
        Label label;
        label = new Label(coluna, linha, conteudo, timesBoldUnderline);
        try {
            writableSheet.addCell(label);
        } catch (WriteException ex) {
            Logger.getLogger(ImplemBibliotecaArquivos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Object obterConteudo(int indexPlanilha, int linha, int coluna) {
        Cell cell;
        WritableSheet sheet = writableWorkbook.getSheet(indexPlanilha);
        cell = sheet.getCell(coluna, linha);//coluna, linha
        return cell;
    }

    @Override
    public Object obterConteudo(Object ensaio, int linha, int coluna) {
        Cell cell;
        WritableSheet sheet = (WritableSheet)ensaio;
        cell = sheet.getCell(coluna, linha);//coluna, linha
        return cell;
    }
    
    
    @Override
    public int obterQuantidadeEnsaios() {
        return this.writableWorkbook.getNumberOfSheets();
    }

    @Override
    public Object acessarEnsaio(int indexEnsaio) {
        Sheet sheet = this.writableWorkbook.getSheet(indexEnsaio);
        return sheet;
    }

    @Override
    public int obterQuantidadeLinhasEnsaio(Object ensaio) {
        Sheet sheet = (Sheet) ensaio; 
        return sheet.getRows();
    }

    
    
   

}

