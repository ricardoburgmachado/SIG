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

            <h1>Editar Parâmetro de Plotagem</h1>

            <!--<button type="button" id="btnListarRegistros" title="Clique aqui para listar os usuários"></button>-->

        <form:form modelAttribute="parametroPlotagem" commandName="parametroPlotagem" id="form_edicao_parametro_plotagem">

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

                    </select>
                    <span id="addSimbolo" class="add"></span>
                </span>  
                 

                <!--FATOR DE AJUSTE-->
                <span class="bloco">
                    <label>Fatores:</label>        
                    <select id="fator" name="fator" >
                        <c:forEach items="${fatores}" var="fator">                               
                            <option value="${fator.id}">${fator.valor}</option>
                        </c:forEach>
                    </select>
                    <span id="addFator" class="add"></span>
                </span>      
                
                
                <!-- PARÂMETROS -->
                <span class="bloco" style="width: 350px;">
                    <label style="width: 100px;">Parâmetros:</label>        
                    <select id="parametro" name="parametro" style="width: 180px;">
                        
                        <c:forEach items="${parametros}" var="px">                               
                            <option value="${px.id}">${px.nome}</option>
                        </c:forEach>
                    </select>
                    <span id="addParametro" class="add"></span>
                </span>      
                
                <span style="clear: both; display: block; height: 10px"></span>

                <span class="bloco" style="clear: both; width: 505px; height: 85px">
                    <label for="nome">Fórmula:</label>
                    
                    <span id="statusInvalido"> inválida
                        <a class="limparFormula" href="#">&nbsp descartar esta e criar nova fórmula &nbsp</a>
                    </span>
                    
                    <span id="statusValido"> válida 
                        <a class="limparFormula" href="#">&nbsp descartar esta e criar nova fórmula &nbsp</a>
                    </span>

                    
                    <span id="statusInicial">  
                        <a class="limparFormula" href="#">Descartar esta e criar nova fórmula</a>
                    </span>
                    
                    <form:textarea  disabled="true" path="expressao" id="expressao" value="" style="width: 700px; font-size: 15px" />
                    
                    
                    
                </span>

                <span id="resumoCadastro"> </span>    
                    
                <span style="clear: both; display: block; float: left; width: 650px;">

                <input id="btnSalvarRegistro" style="cursor: pointer; visibility: hidden; display: none" class="enviar_form" type="submit" value="Editar"/>     

                <input id="btnValidar" style="cursor: pointer" class="enviar_form" value="Validar"/>
                        
                <a id="btnCancelar" href="#" rel="modal:close">Cancelar</a> 
                
                </span>

            </fieldset>
        </form:form>

    </body>

    <script>        
        var itensFormula= new Array();
        var idParametroPlotagem;
        var itensValidacao = new Array();
        
        $(document).ready(function() {     
            document.getElementById("addOperando").style.visibility = "hidden"; 
            document.getElementById("addSimbolo").style.visibility = "hidden"; 
            document.getElementById("addFator").style.visibility = "hidden"; 
            document.getElementById("addParametro").style.visibility = "hidden";
            
            document.getElementById("btnSalvarRegistro").style.visibility = "visible";
            document.getElementById("btnSalvarRegistro").style.display = "block";
            
            document.getElementById("btnValidar").style.visibility = "hidden";                       
            document.getElementById("btnValidar").style.display = "none";          
            setExpressaoBD('${expressaoBD}}');
            setIdBD('${parametroPlotagem.id}');
        });
        
        function addElementoValid(elemento) {
            itensValidacao.push(elemento);
        }
        
        function setIdBD(idBD){  
            idParametroPlotagem = idBD;
        }          
        
        function setExpressaoBD(expressaoBD){            
            var expSplit = expressaoBD.split("@");
            console.log("TAMANHO DO SPLIT DA EXPRESSÃO BD: "+expSplit.length);            
            for(i=0; i < expSplit.length;i++){
                if( expSplit[i] !== "}" && expSplit[i] !== "{" ){
                    itensFormula.push(expSplit[i]);
                }                
            }
        }
    
        function addElemento(elemento){            
            itensFormula.push(elemento);
        }        

        $("#addOperando").click(function() {

            var selecionado = new String();            
            var conteudoAtual = new String();            
            selecionado = $("#operando option:selected").text().toString();
            var selecionadoItem = $("#operando option:selected").val();                        
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
            conteudoAtual =document.getElementById("expressao").value;
            $("#expressao").val(conteudoAtual + selecionado);

            addElemento(selecionadoItem);
            addElementoValid("1");
            document.getElementById("addOperando").style.visibility = "visible";
            document.getElementById("addSimbolo").style.visibility = "visible";
            document.getElementById("addFator").style.visibility = "hidden";
            document.getElementById("addParametro").style.visibility = "hidden";
        });

        $("#btnSalvarRegistro").click(function() {      
            console.log( itensFormula );
            var nome = document.getElementById("nome").value;
            var unidade = document.getElementById("unidade").value;        
            var tipoParametro = document.getElementById("tipoParametro").value;                                
            console.log("ID PARAMETRO DE PLOTAGEM: "+idParametroPlotagem);
          $.ajax({
                url: "<c:url value="/editarParametroPlotagem"/>",
                data: { 
                    itensFormula: itensFormula,                    
                    nome: nome,
                    unidade: unidade,
                    tipoParametro: tipoParametro,
                    id: idParametroPlotagem
                },              
                type: "POST", 
                beforeSend : function(){                    
                    $('#conteudoModal').html('<span id=\"carregando\" \"></span>');                    
                },
                success: function(data) {
                    $('#conteudoModal').html(data);
                }
            });
            //event.preventDefault();
            
        });
        
        $("#btnValidar").click(function() {                                    
            $.ajax({
                url: "<c:url value="/validarExpressao"/>",
                data: { 
                    expressao: constroiExpressaoAvaliacao(itensValidacao)
                },              
                type: "POST", 
                success: function(data) {            
                    console.log("RETORNO VALIDAÇÃO: "+data);
                    if(data){
                        statusValido();
                    }else{
                        statusInvalido();
                    }          
                }
            });
            //event.preventDefault();        
        });
        
        $(".limparFormula").click(function() {
            itensFormula = new Array();
            itensValidacao = new Array();
            document.getElementById("expressao").value = "";
            statusInvalido();
            document.getElementById("addOperando").style.visibility = "visible"; 
            document.getElementById("addSimbolo").style.visibility = "visible"; 
            document.getElementById("addFator").style.visibility = "visible"; 
            document.getElementById("addParametro").style.visibility = "visible";      
            escondeStatusInicial();
        });
        
        function escondeStatusInicial(){
            document.getElementById("statusInicial").style.visibility = "hidden";                       
            document.getElementById("statusInicial").style.display = "none";                                   
        }
        
        function statusValido(){
            document.getElementById("btnSalvarRegistro").style.visibility = "visible";
            document.getElementById("btnSalvarRegistro").style.display = "block";
            document.getElementById("btnValidar").style.visibility = "hidden";                       
            document.getElementById("btnValidar").style.display = "none";                       
            document.getElementById("statusValido").style.display = "block";
            document.getElementById("statusInvalido").style.display = "none";        
            document.getElementById("addOperando").style.visibility = "hidden"; 
            document.getElementById("addSimbolo").style.visibility = "hidden"; 
            document.getElementById("addFator").style.visibility = "hidden"; 
            document.getElementById("addParametro").style.visibility = "hidden";
            
            construirResumoFormula();
        }
        
        function construirResumoFormula(){            
            var dados = new String();
            var expressao = document.getElementById("expressao").value;          
            var nome = document.getElementById("nome").value;
            var unidade = document.getElementById("unidade").value;        
            var tipoParametro = document.getElementById('tipoParametro').options[document.getElementById('tipoParametro').selectedIndex].text;
            dados += "<i>Pré-visualização: </i><br />";
            if(nome.valueOf() === ''){
               dados += "Nome: <strong>nulo</strong>&nbsp;|&nbsp;";                
            }else{
                dados += "Nome: <strong>"+nome+"</strong>&nbsp;|&nbsp;";
            }
            if(unidade.valueOf() === ''){
               dados += "Unidade: <strong>nulo"+"</strong><br />";               
            }else{
                dados += "Unidade: <strong>"+unidade+"</strong><br />";
            }
            if(tipoParametro.valueOf() === ''){
               dados += "Tipo: <strong>nulo"+"</strong><br />";               
            }else{
                dados += "Tipo: <strong>"+tipoParametro+"</strong><br />";
            }        
            if(expressao.valueOf() === ''){
               dados += "Expressão: <strong>nulo"+"</strong><br />";               
            }else{
                dados += "Expressão: <strong>"+expressao+"</strong><br />";
            }       
            document.getElementById("resumoCadastro").style.display = "block";
            $('#resumoCadastro').html(dados);            
        }
        
        function statusInvalido(){
            document.getElementById("btnSalvarRegistro").style.visibility = "hidden";
            document.getElementById("btnSalvarRegistro").style.display = "none";
            document.getElementById("btnValidar").style.visibility = "visible";                       
            document.getElementById("btnValidar").style.display = "block";
            document.getElementById("statusInvalido").style.display = "block";
            document.getElementById("statusValido").style.display = "none";
            document.getElementById("resumoCadastro").style.display = "none";
        }


        $("#btnListarRegistros").click(function() {
            $.post("/SIG/mostrarModalListarParametroPlotagem", {
            }, function(data) {
                $('#conteudoModal').html(data);
            });
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

    </script>
</html>
