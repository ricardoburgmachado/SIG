<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<c:import url="mensagem.jsp"></c:import>

<script>    
    //if('${tipoMensagem}' === 'sucesso'){
    //    location.reload();
    //}     
</script> 

<!-- Resetar valor de variavel -->
<%
    //String tipoMensagem = (String) pageContext.getAttribute("tipoMensagem");
    //tipoMensagem = null;
%>
    
<c:import url="acesso.jsp"></c:import>

    <h1>Cadastrar ilha</h1>

    <button type="button" id="btnListarRegistros" title="Clique aqui para listar as ilhas"></button>
    
    <form id="form_cad_ilha" enctype="multipart/form-data" method="POST">

        <fieldset> 

            <span class="bloco">
                <label for="nome"></label>
                <input id="arquivo" type="file" name="arquivo"/>
            </span>

            <span style="clear: both; display: block; height: 25px;"></span>
            <input id="btnSalvarRegistro" type="submit" value="Cadastrar Ilha"/>


            <a id="btnCancelar" href="#" rel="modal:close">Cancelar</a> 

            <span style="visibility: hidden" id="span_carregando">
                <span id="loading"></span>
                Aguarde, carregando.
            </span>

        </fieldset>

    </form>    


    <script>

        $('#form_cad_ilha').submit(function(event) {
            mostrarCarregando();  
            $.ajax({
                url: "<c:url value="/cadatrarilha"/>",
                data: new FormData(this),
                processData: false,
                contentType: false,
                type: "POST",                
                success: function(data) {
                    $('#conteudoModal').html(data);
                    esconderCarregando();
                                  
                }
            });            
            event.preventDefault();
        });
        
        function mostrarCarregando(){
            document.getElementById("span_carregando").style.visibility = "visible";
        }
        
        function esconderCarregando(){
            document.getElementById("span_carregando").style.visibility = "hidden";
        }


        $("#btnListarRegistros").click(function() {
            $.post("<c:url value="/mostrarModalListarIlhas"/>", {
            }, function(data) {
                $('#conteudoModal').html(data);
            });
        });


        modalEventoListener(this);

    </script>
</html>
