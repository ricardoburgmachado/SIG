/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.GerenciamentoArquivos;

import Modelo.GerenciamentoDadosGeotecnicos.Ensaio;
import Modelo.GerenciamentoDadosGeotecnicos.Evento;
import Modelo.GerenciamentoDadosGeotecnicos.Ilha;
import Modelo.GerenciamentoDadosGeotecnicos.Parametro;
import Modelo.GerenciamentoDadosGeotecnicos.Valor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import org.json.JSONException;

/**
 *
 * @author Ricardo
 */
public class ImplementaConstrutorDocumentoIlha extends ConstrutorDocumentoIlha {

    
    public ImplementaConstrutorDocumentoIlha(EscritorDocumentoIlha escritor, Evento evento) {
        super(escritor, evento);
    }

    
    /**
     * @throws IOException
     * @throws WriteException
     * @throws RowsExceededException
     * @throws JSONException
     */
    @Override
    public void construirDocumentoIlha() throws IOException, WriteException, RowsExceededException, JSONException {
        
        super.escritor.setarIdentificacao(super.evento);
        construirEnsaios();
        super.escritor.finalizarEscrita();
    }

    @Override
    public void construirEnsaios() {
       Ilha ilha = (Ilha) super.evento;
       ArrayList<Ensaio> ensaios = (ArrayList<Ensaio>) ilha.getEnsaios();
        
        for(int i=0, cont=1; i < ensaios.size();i++, cont++){
            try {
                super.escritor.setarEnsaio(ensaios.get(i), cont);
            } catch (IOException ex) {
                Logger.getLogger(ImplementaConstrutorDocumentoIlha.class.getName()).log(Level.SEVERE, null, ex);
            } catch (WriteException ex) {
                Logger.getLogger(ImplementaConstrutorDocumentoIlha.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JSONException ex) {
                Logger.getLogger(ImplementaConstrutorDocumentoIlha.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    
}
