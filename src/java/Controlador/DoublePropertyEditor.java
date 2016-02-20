/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.beans.PropertyEditorSupport;
import org.springframework.stereotype.Component;

/**
 *
 * @author ricado
 */
@Component
public class DoublePropertyEditor extends PropertyEditorSupport  {

    
    @Override
    public String getAsText() {
        Double d = (Double) getValue();
        return d.toString();
    }

    @Override
    public void setAsText(String str) {
        if (str == "" || str == null) {
            setValue(0);
        } else {
            setValue(Double.parseDouble(str));
        }
    }
}
