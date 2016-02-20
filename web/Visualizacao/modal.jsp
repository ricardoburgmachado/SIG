
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


 <script type="text/javascript" src="<c:url value="/recursos/js/ajax_mostrar_modal.js"/>" ></script>


<div id="modalPrincipal" style="display: none;   " >
    <a id="btnFecharModal" title="Fechar"  href="#" rel="modal:close"></a> 
    
    <div id="conteudoModal" class="scroll-pane">
              
    </div>
</div>

 
 <script>
     
    //Método para resetar informações definidas no CSS. Isso existe pois no modal para plotagem de gráficos é feita uma redefinição do tamanho do modal.
    $("#btnFecharModal").click(function() {  
      
      document.getElementById("conteudoModal").style.width = "850px";            
      document.getElementById("conteudoModal").style.height = "600px";        
      
      document.getElementById("modalPrincipal").style.width = "850px";                       
      document.getElementById("modalPrincipal").style.marginLeft = "-444.5px"; 
      document.getElementById("modalPrincipal").style.height = "600px";
    });
 </script>
 