package Controlador;

import Modelo.GerenciamentoExpressoes.FatorAjuste;
import Modelo.GerenciamentoUsuarios.Permissao;
import Modelo.GerenciamentoUsuarios.Usuario;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jxl.Workbook;
import jxl.write.WritableWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 *
 * @author Ricardo
 */
public abstract class ControladorGenerico {
    
    @InitBinder 
    protected abstract void converterTipos(HttpServletRequest request, ServletRequestDataBinder binder)throws Exception;
    
    public boolean isInteger(String s){    
        try{ Integer.parseInt(s); return true;
        }catch(NumberFormatException e){return false;}      
    }
    
    public boolean possuiPermissao(String permissao, HttpServletRequest request){
        Usuario user = (Usuario) request.getSession().getAttribute("usuario");
        if(user != null){
            for(Permissao perm: user.getPerfil().getPermissoes()){//percorre todas as permissões do perfil do usuário
                if(perm.getNome().equalsIgnoreCase(permissao)){
                    System.out.println("*********** POSSUI PERMISSAO");
                    return true;
                }   
            }
        }    
        System.out.println("********** NÃO POSSUI a PERMISSAO: "+permissao);
        return false;
    }
    
    /**
     * return true caso o parametro(identificador) esteja presente na estrutura de dados
     */
    public abstract boolean estaContido(int identificador, List a);
    
    public Object gerarEventoResposta(HttpServletResponse response) throws IOException{
        WritableWorkbook workbook;        
        ServletOutputStream outputStream = response.getOutputStream();        
        response.reset();   
        response.setHeader("Content-Disposition", "attachment;filename=ilhaSAPPGAM.xls");  
        response.setContentType("Application/xls");          
        workbook = Workbook.createWorkbook(outputStream);    
        return workbook;
    }
    
}
