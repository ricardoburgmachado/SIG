<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        
        
    </head>
    <body>
        
         <c:import url="mensagem.jsp"></c:import>
         
          <c:import url="acesso.jsp"></c:import>
        
        <h1>Editar Classe</h1>

        <button type="button" id="btnListarRegistros" title="Clique aqui para listar as classes"></button>
        
        <form:form modelAttribute="classe" commandName="classe" id="form_cadastro_classe">

            <form:input type="hidden" path="id" id="id" />     
            
            <fieldset> 
                <span class="bloco">
                    <label for="nome">Nome</label>
                    <form:input type="text" path="descricao" id="classe" value=""  />
                </span>
                <br/>
               
                </span>

                <span style="clear: both; display: block;"></span>
                <input id="btnSalvarRegistro" style="cursor: pointer" class="enviar_form" type="submit" value="Editar Classe"/>
                
                <a id="btnCancelar" href="#" rel="modal:close">Cancelar</a> 

            </fieldset>
        </form:form>


    </body>

    <script>
      
        $('#form_cadastro_classe').submit(function(event) {
            $.ajax({
                url: "<c:url value="/editarClasse"/>",
                data: new FormData(this),
                processData: false,
                contentType: false,
                type: "POST",
                success: function(data) {
                    $('#conteudoModal').html(data);
                }
            });
            event.preventDefault();
        });

        $("#btnListarRegistros").click(function() {
            $.post("/SIG/mostrarModalListarClasses", {
            }, function(data, status) {
                $('#conteudoModal').html(data);
            });
        });


    </script>
</html>
