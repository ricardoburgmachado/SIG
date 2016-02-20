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
        
        <h1>Cadastrar Usuário</h1>

        <button type="button" id="btnListarRegistros" title="Clique aqui para listar os usuários"></button>
        
        <form:form modelAttribute="usuario" commandName="usuario" id="form_cadastro_usuario">

            <fieldset> 
                <span class="bloco">
                    <label for="nome">Nome</label>
                    <form:input type="text" path="nome" id="nome" value=""  />
                </span>
                <br/>
                <span class="bloco">
                    <label for="nome">Senha</label>
                    <form:input type="password" path="senha"  id="senha" value=""/>
                </span>
                <br/>
                <span class="bloco">
                    <label>Perfil:</label>        
                    <form:select path="perfil">
                        <c:forEach items="${perfis}" var="perfil">                               
                            <form:option value="${perfil.id}">${perfil.nome}</form:option>
                        </c:forEach>
                    </form:select>

                </span>

                <span style="clear: both; display: block;"></span>
                <input id="btnSalvarRegistro" style="cursor: pointer" class="enviar_form" type="submit" value="Cadastrar Usuário"/>
                
                <a id="btnCancelar" href="#" rel="modal:close">Cancelar</a> 

            </fieldset>
        </form:form>


    </body>

    <script>
    


        $('#form_cadastro_usuario').submit(function(event) {
            $.ajax({
                url: "<c:url value="/cadastrarUsuario"/>",
                //data: $('#cadastrarUsuario').serialize(),
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
            $.post("/SIG/mostrarModalListarUsuarios", {
            }, function(data, status) {
                $('#conteudoModal').html(data);
            });
        });

        modalEventoListener(this);

    </script>
</html>
