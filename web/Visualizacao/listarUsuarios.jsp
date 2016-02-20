
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


    <c:import url="mensagem.jsp"></c:import>
        
    <c:import url="acesso.jsp"></c:import>

    <h1>Listar Usuários</h1>
<c:choose>
    <c:when test="${not empty usuarios}">
        <table id="lista_usuarios">                    


            <button type="button" id="btnNovoRegistro" title="Clique aqui para cadastrar novo usuário"></button>

            <tr class="sub_titulo">
                <td class="id">CÓDIGO</td>
                <td class="normal">NOME</td>
                <td class="normal">PERFIL</td>
                <td class="funcoes" colspan="3">AÇÕES</td>
            </tr>

            <c:forEach items="${usuarios}" var="usuario">

                <tr class="linealt">
                <a>
                    <td class="id">${usuario.id}</td>
                    <td class="normal">${usuario.nome}</td>
                    <td class="normal">${usuario.perfil.nome}</td>
                    <td class="edita"><button type="button" class="editar" title="Clique aqui para editar o usuário" value="${usuario.id}"></button></td>                        
                </a>
            </tr>
        </c:forEach>

    </table>


</c:when>    
<c:otherwise>
    <table id="lista_usuarios">                    

         <button type="button" id="btnNovoRegistro" title="Clique aqui para cadastrar novo usuário"></button>

        <tr class="sub_titulo">
            Nenhum registro encontrado.
        </tr>

    </table>
</c:otherwise>
</c:choose>


<script>
    $('.editar').click(function() {
        var id = $(this).attr("value");
        $.get("<c:url value="/mostrarModalEditarUsuario"/>",
                {id: id}
        , function(data, status) {
            $('#conteudoModal').html(data);
        });
    });


    $("#btnNovoRegistro").click(function() {
        //$.post("/SIG/mostrarModalCadastrarUsuario", {
        $.post("<c:url value="/mostrarModalCadastrarUsuario"/>", {
        }, function(data, status) {
            $('#conteudoModal').html(data);
        });
    });


</script>