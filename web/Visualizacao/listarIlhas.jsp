
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


    <c:import url="acesso.jsp"></c:import>

    <c:import url="mensagem.jsp"></c:import>
    
     


    <h1>Listar Ilhas</h1>
    <c:choose>
        <c:when test="${not empty ilhas}">
            <table id="lista_ilhas">                    

                <button  type="button" id="btnNovoRegistro" title="Clique aqui para cadastrar nova ilha"></button>

                <tr class="sub_titulo">
                    <td class="id">CÓDIGO</td>
                    <td class="normal">ILHA</td>    
                    <td class="normal" style="width: 250px;">USUÁRIO</td>                            
                    <td class="funcoes" colspan="3">AÇÕES</td>
                </tr>

                <c:forEach items="${ilhas}" var="ilha">

                    <tr class="linealt">
                    <a>
                        <td class="id">${ilha.id}</td>
                        <td class="normal">${ilha.local}</td>
                        <td class="normal" style="width: 250px;">${ilha.usuario.nome}</td>
                        <td class="edita"><button type="button" class="editar" title="Clique aqui para editar a ilha" value="${ilha.id}"></button></td>           
                        <td class="exclui"><button type="button" class="excluir" title="Clique aqui para excluir a ilha" value="${ilha.id}"></button></td>           
                    </a>
                </tr>
            </c:forEach>

            </table>
        </c:when>    
        <c:otherwise>
            <table id="lista_classes">                    

                <button  type="button" id="btnNovoRegistro" title="Clique aqui para cadastrar nova ilha"></button>

                <tr class="sub_titulo">
                    Nenhum registro encontrado
                </tr>

            </table>
        </c:otherwise>
    </c:choose>
<script>
    $('.editar').click(function() {
        var id = $(this).attr("value");
        
        alert("Funcionalidade invativa no momento!");
        /*$.ajax({           
            url: "<c:url value="/mostrarModalEditarIlha"/>",
            data: { id: id },      
            beforeSend : function(){                    
                $('#conteudoModal').html('<span id=\"carregando\" \"></span>');                    
            },
            type: "GET", 
            success: function(data) {            
                $('#conteudoModal').html(data);
                $('.close-modal').css("visibility","hidden");            
            }
        });*/
    });

    $('.excluir').click(function() {
        
        decisao = confirm("Tem certeza que deseja excluir este registro?");
        if(decisao){
            var id = $(this).attr("value");
            $.ajax({           
                url: "<c:url value="/excluirIlha"/>",
                data: {id: id},      
                beforeSend : function(){                    
                    $('#conteudoModal').html('<span id=\"carregando\" \"></span>');                    
                },
                type: "GET", 
                success: function(data) {            
                    $('#conteudoModal').html(data);
                    $('.close-modal').css("visibility","hidden");            
                }
            });
        }
    });

    $("#btnNovoRegistro").click(function() {

        $.post("<c:url value="/mostrarModalCadastrarIlha"/>", {
        }, function(data, status) {
            $('#conteudoModal').html(data);
        });
    });


    



</script>
