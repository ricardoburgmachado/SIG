
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


    <c:import url="acesso.jsp"></c:import>

    <c:import url="mensagem.jsp"></c:import>
    
     


    <h1>Listar Classes</h1>
    <c:choose>
        <c:when test="${not empty classes}">
            <table id="lista_classes">                    

                <button  type="button" id="btnNovoRegistro" title="Clique aqui para cadastrar nova classe"></button>

                <tr class="sub_titulo">
                    <td class="id">CÓDIGO</td>
                    <td class="normal">CLASSE</td>        
                    <td class="funcoes" colspan="3">AÇÕES</td>
                </tr>

                <c:forEach items="${classes}" var="classe">

                    <tr class="linealt">
                    <a>
                        <td class="id">${classe.id}</td>
                        <td class="normal">${classe.descricao}</td>           
                        <td class="edita"><button type="button" class="editar" title="Clique aqui para editar a classe" value="${classe.id}"></button></td>           
                        <td class="exclui"><button type="button" class="excluir" title="Clique aqui para excluir a classe" value="${classe.id}"></button></td>           
                    </a>
                </tr>
            </c:forEach>

            </table>
        </c:when>    
        <c:otherwise>
            <table id="lista_classes">                    

                <button  type="button" id="btnNovoRegistro" title="Clique aqui para cadastrar nova classe"></button>

                <tr class="sub_titulo">
                    Nenhum registro encontrado
                </tr>

            </table>
        </c:otherwise>
    </c:choose>
<script>
    $('.editar').click(function() {
        var id = $(this).attr("value");
        $.get("<c:url value="/mostrarModalEditarClasse"/>",
                {id: id}
        , function(data, status) {
            $('#conteudoModal').html(data);
        });
    });

    $('.excluir').click(function() {
        
        decisao = confirm("Tem certeza que deseja excluir este registro?");
        if(decisao){
        
            var id = $(this).attr("value");

             $.get("<c:url value="/excluirClasse"/>",
             {id: id}
             , function(data, status) {
             $('#conteudoModal').html(data);
             });         
        }
    });


    $("#btnNovoRegistro").click(function() {

        $.post("<c:url value="/mostrarModalCadastrarClasse"/>", {
        }, function(data, status) {
            $('#conteudoModal').html(data);
        });
    });


    



</script>
