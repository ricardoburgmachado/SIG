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

            <h1>Cadastrar Parâmetro de Plotagem</h1>

            <!--<button type="button" id="btnListarRegistros" title="Clique aqui para listar os usuários"></button>-->

        <form:form modelAttribute="parametroPlotagem" commandName="parametroPlotagem" id="form_cadastro_parametro_plotagem">

            <fieldset> 
                <span class="bloco">
                    <label for="nome">Nome:</label>
                    <form:input type="text" path="nome" id="nome" value=""  />
                </span>


                <span class="bloco">
                    <label for="nome">Unidade:</label>
                    <form:input type="text" path="unidade" id="unidade" value=""  />
                </span>


                <span class="bloco">
                    <label>Tipo:</label>        
                    <c:choose>
                        <c:when test="${not empty tiposParametro}">                    
                            <form:select id="tipoParametro" path="tipoParametro">
                                <c:forEach items="${tiposParametro}" var="tipo">                               
                                    <c:choose>
                                        <c:when test="${parametroPlotagem.tipoParametro.id == tipo.id}">                                        
                                            <form:option value="${tipo.id}" selected="selected">${tipo.nome}</form:option>
                                        </c:when>
                                        <c:otherwise>
                                            <form:option value="${tipo.id}">${tipo.nome}</form:option>
                                        </c:otherwise>
                                    </c:choose>                            
                                </c:forEach>
                            </form:select>
                        </c:when>
                        <c:otherwise>                            
                            Não existe tipos de parâmetro registrado no sistema.
                        </c:otherwise>
                    </c:choose>    
                </span>

                <span style="display: block; float: left; width: 600px; margin-bottom: 15px ">
                    Elementos para criação da fórmula
                </span>

                <!--OPERANDOS-->
                <span class="bloco">
                    <label style="width: 95px">Operadores:</label>        
                    <select id="operando" name="operando">                                                  
                        <option value="+">+</option>
                        <option value="-">-</option>
                        <option value="*">*</option>
                        <option value="/">/</option>                        
                    </select>
                    <span id="addOperando" class="add"></span>
                </span>       

                <!--SIMBOLOS/SINAIS-->
                <span class="bloco">
                    <label>Símbolos:</label>        
                    <select id="simbolo" name="simbolo">

                        <option value="(">(</option>
                        <option value=")">)</option>
                        <!--<option value="sin">Seno</option>
                        <option value="con">Cosseno</option>
                        <option value="tan">Tangente</option>-->


                    </select>
                    <span id="addSimbolo" class="add"></span>
                </span>       


                <!--FATOR DE AJUSTE-->
                <span class="bloco">
                    <label>Fatores:</label>        
                    <c:choose>
                        <c:when test="${not empty fatores}">
                            <select id="fator" name="fator" >
                                <c:forEach items="${fatores}" var="fator">                               
                                    <option value="${fator.id}">${fator.nome} | ${fator.valor}</option>
                                </c:forEach>
                            </select>
                            <span id="addFator" class="add"></span>
                        </c:when>
                        <c:otherwise>
                            <span id="addFator"  style="visibility: hidden"></span><!-- este span é mostrado para não acontecer um erro ao tentar recuperar o id=addFator -->
                            Não existe fator de ajuste registrado no sistema.
                        </c:otherwise>
                    </c:choose>
                </span>      


                <!-- PARÂMETROS -->
                <span class="bloco" style="width: 350px;">
                    <label style="width: 100px;">Parâmetros:</label>        
                    <c:choose>
                        <c:when test="${not empty parametros}">
                            <select id="parametro" name="parametro" style="width: 180px;">
                                <c:forEach items="${parametros}" var="px">                               
                                    <option value="${px.id}">${px.nome}</option>
                                </c:forEach>
                            </select>
                            <span id="addParametro" class="add"></span>
                        </c:when>
                        <c:otherwise>
                            <span id="addParametro" style="visibility: hidden"></span><!-- este span é mostrado para não acontecer um erro ao tentar recuperar o id=addParametro -->
                            Não existe parâmetro de ajuste registrado no sistema.
                        </c:otherwise>
                    </c:choose>
                </span>      
                
                
                
                
                
                
                
                
                
                
                <span style="clear: both; display: block; height: 10px"></span>

                <span class="bloco" style="clear: both; width: 505px; height: 85px">
                    <label for="nome">Fórmula:</label>

                    <span id="statusInvalidoInicial"> 
                        <a class="limparFormula" href="#">Limpar esta e criar nova fórmula &nbsp</a>
                    </span>
                    
                    <span id="statusInvalido"> Inválida
                        <a class="limparFormula" href="#">&nbsp Limpar esta e criar nova fórmula &nbsp</a>
                    </span>

                    <span id="statusValido"> Válida 
                        <a class="limparFormula" href="#">&nbsp Limpar esta e criar nova fórmula &nbsp</a>
                    </span>

                    <form:textarea disabled="true" path="expressao" id="expressao" value="" style="width: 700px; font-size: 15px" />



                </span>

                <span id="resumoCadastro"> </span>    

                <span style="clear: both; display: block; float: left; width: 650px;">

                    <input id="btnSalvarRegistro" style="cursor: pointer; visibility: hidden; display: none" class="enviar_form" type="submit" value="Cadastrar"/>     

                    <input id="btnValidar" style="cursor: pointer" class="enviar_form" value="Validar"/>

                    <a id="btnCancelar" href="#" rel="modal:close">Cancelar</a> 

                </span>

            </fieldset>
        </form:form>

    </body>

    <script>
        var itensFormula = new Array();
        var itensValidacao = new Array();
        

        function addElemento(elemento) {
            itensFormula.push(elemento);
        }

        function addElementoValid(elemento) {
            itensValidacao.push(elemento);
        }
        
        $("#addOperando").click(function() {

            var selecionado = new String();            
            var conteudoAtual = new String();            
            selecionado = $("#operando option:selected").text().toString();
            var selecionadoItem = $("#operando option:selected").val();            
            //conteudoAtual = $("#expressao").attr("value").toString();
            conteudoAtual =document.getElementById("expressao").value;

            $("#expressao").val(conteudoAtual + selecionado);


            addElemento(selecionadoItem);
            addElementoValid(selecionadoItem);
            document.getElementById("addOperando").style.visibility = "hidden";
            document.getElementById("addSimbolo").style.visibility = "visible";
            document.getElementById("addFator").style.visibility = "visible";
            document.getElementById("addParametro").style.visibility = "visible";
        });

        $("#addSimbolo").click(function() {
            var selecionado = new String();            
            var conteudoAtual = new String();
            selecionado = $("#simbolo option:selected").text().toString();
            var selecionadoItem = $("#simbolo option:selected").val();            
            //conteudoAtual = $("#expressao").attr("value").toString();
            conteudoAtual =document.getElementById("expressao").value;
            $("#expressao").val(conteudoAtual + selecionado);
            

            addElemento(selecionadoItem);
            addElementoValid(selecionadoItem);
            document.getElementById("addOperando").style.visibility = "visible";
            //document.getElementById("addSimbolo").style.visibility = "hidden";                 
            document.getElementById("addFator").style.visibility = "visible";
            document.getElementById("addParametro").style.visibility = "visible";
        });

        $("#addFator").click(function() {
            var selecionado = new String();            
            var conteudoAtual = new String();            
            selecionado = $("#fator option:selected").text().toString();
            var selecionadoItem = $("#fator option:selected").val();
            //conteudoAtual = $("#expressao").attr("value").toString();
            conteudoAtual =document.getElementById("expressao").value;
            $("#expressao").val(conteudoAtual + selecionado);
 
            addElemento(selecionadoItem);
            addElementoValid("1");
            document.getElementById("addOperando").style.visibility = "visible";
            document.getElementById("addSimbolo").style.visibility = "visible";
            document.getElementById("addFator").style.visibility = "hidden";
            document.getElementById("addParametro").style.visibility = "hidden";
        });

        $("#addParametro").click(function() {
            var selecionado = new String();            
            var conteudoAtual = new String();            
            selecionado = $("#parametro option:selected").text().toString();
            var selecionadoItem = $("#parametro option:selected").val();
            //conteudoAtual = $("#expressao").attr("value").toString();
            conteudoAtual =document.getElementById("expressao").value;
            $("#expressao").val(conteudoAtual + selecionado);

            addElemento(selecionadoItem);
            addElementoValid("1");
            document.getElementById("addOperando").style.visibility = "visible";
            document.getElementById("addSimbolo").style.visibility = "visible";
            document.getElementById("addFator").style.visibility = "hidden";
            document.getElementById("addParametro").style.visibility = "hidden";
        });

        $('#form_cadastro_parametro_plotagem').submit(function(event) {

            console.log(itensFormula);
           
            var nome = document.getElementById("nome").value;
            var unidade = document.getElementById("unidade").value;
            var tipoParametro = document.getElementById("tipoParametro").value; 
            $.ajax({
                url: "<c:url value="/cadastrarParametroPlotagem"/>",
                data: {
                    itensFormula: itensFormula,
                    //parametroPlotagem: $('#form_cadastro_parametro_plotagem').serialize(),   
                    nome: nome,
                    unidade: unidade,
                    tipoParametro: tipoParametro
                },
                beforeSend : function(){                    
                    $('#conteudoModal').html('<span id=\"carregando\" \"></span>');                    
                },  
                type: "POST",
                success: function(data) {
                    $('#conteudoModal').html(data);
                }
            });
            event.preventDefault();
        });

        $("#btnValidar").click(function() {           
            console.log("Itens validação: "+itensValidacao);            
            $.ajax({
                url: "<c:url value="/validarExpressao"/>",
                data: {
                    expressao: constroiExpressaoAvaliacao(itensValidacao)
                },                         
                type: "POST",
                success: function(data) {
                    console.log("RETORNO VALIDAÇÃO: "+data);
                    if (data) {
                        statusValido();
                    } else {
                        statusInvalido();
                    }                                      
                }
            });
            //event.preventDefault();
        });

        function constroiExpressaoAvaliacao(itens){        
            var entrada = itens.toString();
            var retorno = new String();            
            for(i=0; i < entrada.length;i++ ){
                console.log("a ser tratado: "+entrada[i]);
                retorno += entrada[i];
            }
            retorno = retorno.replace(/,/gi, "");            
            return retorno;
        }


        $(".limparFormula").click(function() {
            itensFormula = new Array();
            itensValidacao = new Array();
            document.getElementById("expressao").value = "";
            statusInvalido();
            document.getElementById("addOperando").style.visibility = "visible";
            document.getElementById("addSimbolo").style.visibility = "visible";
            document.getElementById("addFator").style.visibility = "visible";
            document.getElementById("addParametro").style.visibility = "visible";

        });

        function statusValido() {            
           
            document.getElementById("btnSalvarRegistro").style.display = "block";
            document.getElementById("btnSalvarRegistro").style.visibility = "visible";
            document.getElementById("btnValidar").style.visibility = "hidden";
            document.getElementById("btnValidar").style.display = "none";
            document.getElementById("statusValido").style.display = "block";
            document.getElementById("statusInvalido").style.display = "none";
            document.getElementById("statusInvalidoInicial").style.display = "none";            
            document.getElementById("addOperando").style.visibility = "hidden";
            document.getElementById("addSimbolo").style.visibility = "hidden";
            document.getElementById("addFator").style.visibility = "hidden";
            document.getElementById("addParametro").style.visibility = "hidden";
            
            construirResumoFormula();            
        }

        function construirResumoFormula() {            
            var dados = new String();
            var expressao = document.getElementById("expressao").value;
            var nome = document.getElementById("nome").value;
            var unidade = document.getElementById("unidade").value;
            var tipoParametro = document.getElementById('tipoParametro').options[document.getElementById('tipoParametro').selectedIndex].text;
            
            dados += "<i>Pré-visualização: </i><br />";
            if (nome.valueOf() === '') {
                dados += "Nome: <strong>nulo</strong>&nbsp;|&nbsp;";
            } else {
                dados += "Nome: <strong>" + nome + "</strong>&nbsp;|&nbsp;";
            }
            if (unidade.valueOf() === '') {
                dados += "Unidade: <strong>nulo" + "</strong><br />";
            } else {
                dados += "Unidade: <strong>" + unidade + "</strong><br />";
            }
            if (tipoParametro.valueOf() === '') {
                dados += "Tipo: <strong>nulo" + "</strong><br />";
            } else {
                dados += "Tipo: <strong>" + tipoParametro + "</strong><br />";
            }
            if (expressao.valueOf() === '') {
                dados += "Expressão: <strong>nulo" + "</strong><br />";
            } else {
                dados += "Expressão: <strong>" + expressao + "</strong><br />";
            }
            document.getElementById("resumoCadastro").style.display = "block";            
            $('#resumoCadastro').html(dados);
        }

        function statusInvalido() {
            document.getElementById("btnSalvarRegistro").style.visibility = "hidden";
            document.getElementById("btnSalvarRegistro").style.display = "none";
            document.getElementById("btnValidar").style.visibility = "visible";
            document.getElementById("btnValidar").style.display = "block";
            document.getElementById("statusInvalido").style.display = "block";
            document.getElementById("statusValido").style.display = "none";
            document.getElementById("statusInvalidoInicial").style.display = "none";            
            document.getElementById("resumoCadastro").style.display = "none";
        }


        $("#btnListarRegistros").click(function() {
            $.post("/SIG/mostrarModalListarParametroPlotagem", {
            }, function(data) {
                $('#conteudoModal').html(data);
            });
        });
        
        modalEventoListener(this);

    </script>
</html>
