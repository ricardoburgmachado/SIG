package Modelo.GerenciamentoUsuarios;



import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.stereotype.Component;

/**
 * @author Ricardo
 */
@Component
public class Criptografia {
    
    
    private static MessageDigest md = null;

    static {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
    }

    
    private static char[] hexCodes(byte[] text) {
        char[] hexOutput = new char[text.length * 2];
        String hexString;
        for (int i = 0; i < text.length; i++) {
            hexString = "00" + Integer.toHexString(text[i]);
            hexString.toUpperCase().getChars(hexString.length() - 2,hexString.length(), hexOutput, i * 2);
        }
        return hexOutput;
    }
    
    public static String criptografar(String pwd) {
        if (md != null) {
            return new String(hexCodes(md.digest(pwd.getBytes())));
        }
        return null;
    }

    public static boolean descriptografar(String pwdCriptografado, String pwdBruta){    
        if(criptografar(pwdBruta).equalsIgnoreCase(pwdCriptografado)){            
            return true;
        }else{
            return false;
        }
    }
    
    
    /*
    public static void main(String[] args){

        String senha = "admin";

        System.out.println(Criptografia.criptografar(senha));
        
        
        System.out.println("RETORNO : "+Criptografia.descriptografar(Criptografia.criptografar(senha), "senhadeteste"));

    }
*/









    
    
}
