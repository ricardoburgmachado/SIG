
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

    <c:import url="mensagem.jsp"></c:import>

    <c:import url="acesso.jsp"></c:import>



    <h1>Listar Templates Padrão</h1>
    <c:choose>
        <c:when test="${not empty templates}">
            <table id="lista_templates_padrao">                    

                <button  type="button" id="btnNovoRegistro" title="Clique aqui para cadastrar novo template padrão"></button>

                <tr class="sub_titulo">
                    <td class="id">CÓDIGO</td>
                    <td class="normal">TEMPLATE</td>        
                    <td class="funcoes" colspan="1">AÇÕES</td>
                </tr>

                <c:forEach items="${templates}" var="template">

                    <tr class="linealt">
                    <a>
                        <td class="id">${template.id}</td>
                        <td class="normal">${template.nome}</td>           
                        <td class="edita"><button type="button" class="editar" title="Clique aqui para editar o template" value="${template.id}"></button></td>           
                         
                    </a>
                </tr>
            </c:forEach>

            </table>
        </c:when>    
        <c:otherwise>
            <table id="lista_classes">                    

                <button  type="button" id="btnNovoRegistro" title="Clique aqui para cadastrar novo template padrão"></button>

                <tr class="sub_titulo">
                    Nenhum registro encontrado
                </tr>

            </table>
        </c:otherwise>
    </c:choose>
<script>
    $('.editar').click(function() {
        var id = $(this).attr("value");
        $.get("<c:url value="/mostrarModalEditarTemplatePadrao"/>",
                {id: id}
        , function(data, status) {
            $('#conteudoModal').html(data);
        });
    });



    $("#btnNovoRegistro").click(function() {

        $.post("<c:url value="/mostrarModalCadastrarTemplatePadrao"/>", {
        }, function(data, status) {
            $('#conteudoModal').html(data);
        });
    });


    



</script>
