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
        
        
        
        <h1>Editar Usuário</h1>

        <form:form modelAttribute="usuario" commandName="usuario" id="form_cadastro_usuario">

            <fieldset> 


                <form:input type="hidden" path="id" id="id" />                    


                <span class="bloco">
                    <label for="nome">Nome</label>
                    <form:input type="text" path="nome" id="nome" />
                </span>

                <span class="bloco">
                    <label for="nome">Senha</label>
                    <form:input type="password" path="senha"  id="senha" />
                </span>

                <span class="bloco">
                    <label>Perfil:</label>                  



                    <form:select path="perfil">
                        <c:forEach items="${perfis}" var="p"> 
                            <c:choose>
                                <c:when test="${p.id == usuario.perfil.id}">
                                    <form:option value="${p.id}" selected="selected">${p.nome}</form:option>
                                </c:when>
                                <c:otherwise>
                                    <form:option value="${p.id}">${p.nome}</form:option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </form:select>

                </span>



                <span style="clear: both; display: block;"></span>
                <input alt="Editar usuário" title="Editar usuário" id="btnSalvarRegistro" class="enviar_form" type="submit" value="Editar Usuário"/>

                <a id="btnCancelar" href="#" rel="modal:close">Cancelar</a> 


            </fieldset>
        </form:form>


    </body>

    <script>
        
        $('#form_cadastro_usuario').submit(function(event) {
            $.ajax({
                url: "<c:url value="/editarUsuario"/>",
                //data: $('#editarUsuario').serialize(),
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


    </script>
</html>
