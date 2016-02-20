
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!-- INICIO MULTISELECT -->    
<link rel="stylesheet" href="<c:url value="/recursos/js/multiselect/jquery.multiselect.css"/>" />
<link rel="stylesheet" href="<c:url value="/recursos/js/multiselect/assets/prettify.css"/>" />
<link rel="stylesheet" href="<c:url value="/recursos/js/multiselect/assets/style.css"/>" />
<link rel="stylesheet" href="<c:url value="/recursos/js/multiselect/remote/jquery-ui.css"/>" />
<script  type="text/javascript" src="<c:url value="/recursos/js/multiselect/remote/jquery.js"/>" ></script>
<script  type="text/javascript" src="<c:url value="/recursos/js/multiselect/remote/jquery-ui.min.js"/>" ></script>
<script  type="text/javascript" src="<c:url value="/recursos/js/multiselect/src/jquery.multiselect.js"/>" ></script>
<!-- FIM MULTISELECT -->

<!--<script type="text/javascript" src="<c:url value="recursos/js/maskedinput/jquery.maskedinput.js"/>" ></script>
<script type="text/javascript" src="<c:url value="recursos/js/maskedinput/jquery.maskedinput.min.js"/>" ></script>-->


<h1>Gerar Gráfico</h1>

<script>
    
    var codigos = getMarcadoresSelecionados();

     if (codigos == "") {
        //alert("Para plotar um gráfico você deve selecionar uma ou mais ilhas!");
     }
    
</script>

<fieldset id="opcoes_selecao_parametros">
    
    
    <legend> <b>Opções para Geração do Gráfico</b> </legend>

    <table id="eixos" >

        <tr>
            <td class="subtitulo">
                <b>Eixo X:</b>
            </td>

            <td>
                <label>Caracterização</label>
                <select id="caracterizacaoX" multiple="multiple" name="caracterizacaoX">
                    <c:forEach items="${parametros}" var="p">                             
                        <c:if test="${p.tipoParametro.nome eq 'Caracterização'}">
                            <option value="${p.id}">${p.nome}</option>                        
                        </c:if>
                    </c:forEach>
                <select>
            </td>         


            
            <td>
                <label>Compressibilidade</label>
                <select id="compressibilidadeX" multiple="multiple" name="compressibilidadeX">
                    <c:forEach items="${parametros}" var="p">                             
                        <c:if test="${p.tipoParametro.nome eq 'Compressibilidade'}">
                            <option value="${p.id}">${p.nome}</option>                        
                        </c:if>
                    </c:forEach>
                <select>
            </td>         
            
            
            <td>
                <label>Resistência</label>
                <select id="resistenciaX" multiple="multiple" name="resistenciaX">
                    <c:forEach items="${parametros}" var="p">                             
                        <c:if test="${p.tipoParametro.nome eq 'Resistência'}">
                            <option value="${p.id}">${p.nome}</option>                        
                        </c:if>
                    </c:forEach>
                <select>
            </td>       
            
        </tr>
        
        <tr  style="border-left: 1px solid #666666;">
            <td class="subtitulo">
                <b>Eixo Y:</b>
            </td>
            
            <td>
                <label>Profundidade</label>
                <input id="profundidadeY" checked="checked" type="checkbox"/>
            </td>
            
            <td>
                <label>Caracterização</label>
                <select id="caracterizacaoY" multiple="multiple" name="caracterizacaoY">
                    <c:forEach items="${parametros}" var="p">                             
                        <c:if test="${p.tipoParametro.nome eq 'Caracterização'}">
                            <option value="${p.id}">${p.nome}</option>                        
                        </c:if>
                    </c:forEach>
                <select>
            </td>         
            
            <td>
                <label>Compressibilidade</label>
                <select id="compressibilidadeY" multiple="multiple" name="compressibilidadeY">
                    <c:forEach items="${parametros}" var="p">                             
                        <c:if test="${p.tipoParametro.nome eq 'Compressibilidade'}">
                            <option value="${p.id}">${p.nome}</option>                        
                        </c:if>
                    </c:forEach>
                <select>
            </td>         
            
            
            <td>
                <label>Resistência</label>
                <select id="resistenciaY" multiple="multiple" name="resistenciaY">
                    <c:forEach items="${parametros}" var="p">                             
                        <c:if test="${p.tipoParametro.nome eq 'Resistência'}">
                            <option value="${p.id}">${p.nome}</option>                        
                        </c:if>
                    </c:forEach>
                <select>
            </td>       
        </tr>

    </table>    
    
    <span style="clear: both; display: block;"></span>
        
    <input id="btnPlotarGrafico" style="cursor: pointer" class="enviar_form" type="submit" value="Plotar gráfico"/>
                
    <a id="btnCancelarPlotar" href="#" rel="modal:close">Cancelar</a> 

</fieldset>

<span id="fatores"></span>

<span id="parametros_selecionados">
    
    <!--<span id="titulo">Parâmetros selecionados para plotagem do gráfico:</span>-->
    
    <span id="blocoLeft">
        <span id="eixoXCaract"></span>
        <span id="eixoXCompress"></span>
        <span id="eixoXResist"></span>
    </span>
    
    <span id="blocoRight">
        <span id="eixos"></span>
        <span id="eixoY">Profundidade</span>
    </span>
    
</span>

<script>


    function isChecked(multiselect, value) {  
        var aChecked = multiselect.multiselect("getChecked");
        for (var i = 0; i < aChecked.length; i++)
          if (aChecked[i].value == value)
            return true;
        return false;
    }

    var eixoXGlobal = new Array(); 
    var idsSplitCaractX = new Array();
    var idsSplitCompressX = new Array();
    var idsSplitResistX = new Array();
    var idsCaractY = new Array();
    var idsCompressY = new Array();
    var idsResistY = new Array();
    var eixoXMultiple = true;
    
    function profundidadeChecked(){
        if($('#profundidadeY').is(':checked')){ 
            return true;
        }else{                      
            return false;
        }        
    }
    
    /**
     * Realiza a concatenação de das estrutura de dados dos parametros
     * @returns {undefined}
     */
    function addParametroEixoX(){     
        console.log("inicia addParametroEixoX()");
        eixoXGlobal = new Array();
        eixoXGlobal = eixoXGlobal.concat(idsSplitCaractX, idsSplitCompressX, idsSplitResistX,
            idsCaractY, idsCompressY, idsResistY);
    }
    
    /**
     * Acessa a estrutura global e envia para o seridor retornar os fatores relacionados aos parametros enviados
     * @returns {undefined}
     */
    function atualizaPrintFatores(){
        //console.log("atualizaPrintFatores() -> "+eixoXGlobal.valueOf());       
        if(eixoXGlobal.length > 0 && eixoXGlobal.valueOf() !== null && eixoXGlobal.valueOf() !== ''){
            $.ajax({
                    url: "<c:url value="/buscarParametrosFatores"/>",                
                    data: { 
                      eixoXGlobal: eixoXGlobal.valueOf()                
                    },  
                    async: true,        
                    type: "POST",
                    success: function(retorno) {                
                        if(retorno !== null || retorno !== undefined){
                            $('#fatores').html( processaRetorno(retorno) );
                        }                        
                    }
            });
            //event.preventDefault();
        }else{
            $('#fatores').css("border", "none");
            $('#fatores').html( "");
        }
    }
    
    /*
    * Método responsável por construir o html dos fatores a serem apresentados posteriormente
    * @returns {undefined}     
    */    
    function processaRetorno(r){
        console.log("processaRetorno() "+r.valueOf());
        var cont = new String();
        if(r.length > 0 && r !== null){            
            for(i=0; i < r.length;i++){
                cont += "<span class=\"conteudo\"><span class=\"label\">"+r[i].nome+"</span>&nbsp;<input class=\"valorFator\" value=\""+r[i].valor+"\" name=\""+r[i].nome+"\" type=\"text\" id=\""+r[i].id+"\"/></span><br/>";
                //cont += "<span class=\"conteudo\"><span class=\"label\">"+r[i].nome+"</span>&nbsp;<fmt\:formatNumber class=\"valorFator\" value=\""+r[i].valor+"\" name=\""+r[i].nome+"\" type=\"number\" maxIntegerDigits=\"3\" id=\""+r[i].id+"\"/></span><br/>";                
            }      
            $('#fatores').css("border", "1px dashed #444444");
        }else{
            $('#fatores').css("border", "none");
            $('#fatores').html( "");
        }
        return cont;
    }
    

    
    $('#caracterizacaoX').change(function(){  
        //var dados = $( "#caracterizacaoX option:selected" ).text();//concatena os textos dos itens selecionados         
        //var qtd = $(this).multiselect("widget").find("input:checked").length;//obtem a quantidade de itens selecionados
        //var dados = $( "#caracterizacaoX option:selected" ).html()+"<br/>";//obtem o nome do ultimo selecionado (baseado no evento do usuário)
        //var nome = $( "#caracterizacaoX option:selected" ).last();//obtem o nome do ultimo selecionado de acordo com a listagem
        //var nome = $( "#caracterizacaoX option:selected" ).index();//obtem o index da opção selecionada        
        //var texto = $('#caracterizacaoX option:eq(0)').text();//recupera o texto através do index
        //var texto = $('#caracterizacaoX option[value="SEL1"]').text();//recupera o texto através do value
        var ids = $("#caracterizacaoX").val();
        idsSplitCaractX = new Array();        
        if(ids !== null){
            idsSplitCaractX = ids.toString().split(',');       
            resetaCompressX();
            resetaResistenciaX();
        }        
        addParametroEixoX();
        atualizaPrintFatores();                 
        escreveCaractX();                
     });


     /**
     * Método responsável por analisar o status de "eixoXMultiple", caso seja = false este reseta as opções selecionadas do combo compressibilidade do eixo X
      * @returns {undefined}      */
    function resetaCompressX(){
        if( !eixoXMultiple ){//caso não seja multiple tiver um elemento selecionado neste combo, serão resetados os demais combos do eixoX                
            idsSplitCompressX = new Array();                
            addParametroEixoX();
            atualizaPrintFatores();                
            escreveCompressX();                
        }         
     }

    /**
     * Método responsável por analisar o status de "eixoXMultiple", caso seja = false este reseta as opções selecionadas do combo resistência do eixo X
      * @returns {undefined}      */
    function resetaResistenciaX(){
        if( !eixoXMultiple ){//caso não seja multiple tiver um elemento selecionado neste combo, serão resetados os demais combos do eixoX                
            idsSplitResistX = new Array();                
            addParametroEixoX();
            atualizaPrintFatores();                
            escreveResistX();                
        }         
     }

    /**
    * Método responsável por analisar o status de "eixoXMultiple", caso seja = false este reseta as opções selecionadas do combo caracterizacao do eixo X
    * @returns {undefined}      */
    function resetaCaracterizacaoX(){
        if( !eixoXMultiple ){//caso não seja multiple tiver um elemento selecionado neste combo, serão resetados os demais combos do eixoX                
            idsSplitCaractX = new Array();                
            addParametroEixoX();
            atualizaPrintFatores();                
            escreveCaractX();                
        }         
     }

     function escreveCaractX(){
        var dados = new String();
        console.log("idsSplitCaractX.length: "+idsSplitCaractX.length);
        for(i=0;i < idsSplitCaractX.length; i++){            
            var value = idsSplitCaractX[i].valueOf();                
            console.log("Nome: "+$('#caracterizacaoX option[value="'+value+'"]').text());
            dados += $('#caracterizacaoX option[value="'+value+'"]').text()+"<br/>";            
        }       
        $('#eixoXCaract').html( dados );  
     }


    $('#compressibilidadeX').change(function(){        
        var ids = $("#compressibilidadeX").val();
        idsSplitCompressX = new Array();
        if(ids !== null){
            idsSplitCompressX = ids.toString().split(',');
            resetaCaracterizacaoX();
            resetaResistenciaX();
        }
        addParametroEixoX();
        atualizaPrintFatores();
        escreveCompressX();       
     });
    
    function escreveCompressX(){
        var dados = new String();
        for(i=0;i < idsSplitCompressX.length; i++){
            var value = idsSplitCompressX[i].valueOf();
            dados += $('#compressibilidadeX option[value="'+value+'"]').text()+"<br/>";
        }
        $('#eixoXCompress').html( dados );  
    }
    
    
    $('#resistenciaX').change(function(){        
        var ids = $("#resistenciaX").val();
        idsSplitResistX = new Array();
        if(ids !== null){
            idsSplitResistX = ids.toString().split(',');    
            resetaCaracterizacaoX();
            resetaCompressX();
        }
        addParametroEixoX();
        atualizaPrintFatores();        
        escreveResistX();
     });

     function escreveResistX(){
        var dados = new String();
        for(i=0;i < idsSplitResistX.length; i++){
            var value = idsSplitResistX[i].valueOf();    
            dados += $('#resistenciaX option[value="'+value+'"]').text()+"<br/>";
        }
        $('#eixoXResist').html( dados );        
     }


    $('#profundidadeY').change(function(){      
        var v = $('#profundidadeY').is(':checked');        
        if(v){
            $('#eixoY').html("Profundidade");                                                 
            idsCaractY = new Array();
            idsCompressY = new Array();
            idsResistY = new Array();
            addParametroEixoX();
            atualizaPrintFatores();
            $('#profundidadeY').prop('checked', true);
            multipleEixoX();     
        }else{            
            naoMultipleEixoX();
            $('#eixoY').html("");                        
        }   
    });

    function multipleEixoX(){
        eixoXMultiple = true;
        $('#caracterizacaoX').prop('selectedIndex', -1);//reseta o componente select
        $('#compressibilidadeX').prop('selectedIndex', -1);//reseta o componente select
        $('#resistenciaX').prop('selectedIndex', -1);//reseta o componente select
        startSelectMultipleCaracX();//altera a propriedade do componente select para "multiple: true"  
        startSelectMultipleCompresX();
        startSelectMultipleResistX();
        idsSplitCaractX = new Array();
        idsSplitCompressX = new Array();
        idsSplitResistX = new Array();
        addParametroEixoX();
        atualizaPrintFatores();
        escreveCaractX();
        escreveCompressX();
        escreveResistX();
    }

    function naoMultipleEixoX(){
        eixoXMultiple = false;
        $('#caracterizacaoX').prop('selectedIndex', -1);//reseta o componente select
        $('#compressibilidadeX').prop('selectedIndex', -1);//reseta o componente select
        $('#resistenciaX').prop('selectedIndex', -1);//reseta o componente select
        startSelectNaoMultipleCaracX();//altera a propriedade do componente select para "multiple: false"     
        startSelectNaoMultipleCompresX();
        startSelectNaoMultipleResistX();       
        idsSplitCaractX = new Array();
        idsSplitCompressX = new Array();
        idsSplitResistX = new Array();
        addParametroEixoX();
        atualizaPrintFatores();
        escreveCaractX();
        escreveCompressX();
        escreveResistX();
    }


    $('#caracterizacaoY').change(function(){        
 
       document.getElementById("profundidadeY").checked = false;
       //$("#compressibilidadeY").multiselect("uncheckAll");
       //$("#resistenciaY").multiselect("uncheckAll");              
       var dados = $( "#caracterizacaoY option:selected" ).html();//obtem o nome do ultimo selecionado (baseado no evento do usuário)
       $('#eixoY').html(dados);
       idsCaractY = new Array();
       idsCompressY = new Array();
       idsResistY = new Array();       
       idsCaractY = $("#caracterizacaoY").val();
       
       if(idsCaractY !== null && idsCaractY.length > 0 && eixoXMultiple){
           naoMultipleEixoX();
       }
       
       //console.log("idsCaractY.valueOf(): "+idsCaractY.valueOf());
       addParametroEixoX();     
       atualizaPrintFatores();       
    });
    
   
    
    $('#compressibilidadeY').change(function(){        
       document.getElementById("profundidadeY").checked = false;
       //$("#caracterizacaoY").multiselect("uncheckAll");
       //$("#resistenciaY").multiselect("uncheckAll");
        
       var dados = $( "#compressibilidadeY option:selected" ).html();//obtem o nome do ultimo selecionado (baseado no evento do usuário)
       $('#eixoY').html(dados);  
       idsCaractY = new Array();
       idsCompressY = new Array();
       idsResistY = new Array();
       if(idsCompressY !== null){
            idsCompressY = $("#compressibilidadeY").val();
       }
       
       if(idsCompressY !== null && idsCompressY.length > 0 && eixoXMultiple){
           naoMultipleEixoX();
       }
       
       addParametroEixoX();
       atualizaPrintFatores();
       
    });

    $('#resistenciaY').change(function(){        
       document.getElementById("profundidadeY").checked = false;
       //$("#caracterizacaoY").multiselect("uncheckAll");
       //$("#compressibilidadeY").multiselect("uncheckAll");  
        
       var dados = $( "#resistenciaY option:selected" ).html();//obtem o nome do ultimo selecionado (baseado no evento do usuário)
       $('#eixoY').html(dados);
       idsCaractY = new Array();
       idsCompressY = new Array();
       idsResistY = new Array();
       if(idsResistY !== null){
            idsResistY = $("#resistenciaY").val();
       }
       
       if(idsResistY !== null && idsResistY.length > 0 && eixoXMultiple){
           naoMultipleEixoX();
       }
       
       addParametroEixoX();
       atualizaPrintFatores();
    });


    $("#caracterizacaoX").multiselect({
        selectedText: "# selecionado(s) de #",
        checkAllText: 'Selecionar Todos',
        uncheckAllText: 'Desselecionar Todos',
        noneSelectedText: 'Selecione uma opção',
        multiple: true
    });
    
    $("#compressibilidadeX").multiselect({
        selectedText: "# selecionado(s) de #",
        checkAllText: 'Selecionar Todos',
        uncheckAllText: 'Desselecionar Todos',
        noneSelectedText: 'Selecione uma opção'
    });
    
    $("#resistenciaX").multiselect({
        selectedText: "# selecionado(s) de #",
        checkAllText: 'Selecionar Todos',
        uncheckAllText: 'Desselecionar Todos',
        noneSelectedText: 'Selecione uma opção'
    });
    
    $("#caracterizacaoY").multiselect({
        selectedText: "Selecione uma opção",
        checkAllText: 'Selecionar Todos',
        uncheckAllText: 'Desselecionar Todos',
        noneSelectedText: 'Selecione uma opção',
        multiple: false
    });
    
    $("#compressibilidadeY").multiselect({
        selectedText: "Selecione uma opção",
        checkAllText: 'Selecionar Todos',
        uncheckAllText: 'Desselecionar Todos',
        noneSelectedText: 'Selecione uma opção',
        multiple: false    
    });
    
    $("#resistenciaY").multiselect({
        selectedText: "Selecione uma opção",
        checkAllText: 'Selecionar Todos',
        uncheckAllText: 'Desselecionar Todos',
        noneSelectedText: 'Selecione uma opção',
        multiple: false
    });
    
    
    function startSelectMultipleCaracX(){
        $("#caracterizacaoX").multiselect({
            selectedText: "# selecionado(s) de #",
            checkAllText: 'Selecionar Todos',
            uncheckAllText: 'Desselecionar Todos',
            noneSelectedText: 'Selecione uma opção',
            multiple: true
        });
    }
    
    function startSelectNaoMultipleCaracX(){
        $("#caracterizacaoX").multiselect({
            selectedText: "# selecionado(s) de #",
            checkAllText: 'Selecionar Todos',
            uncheckAllText: 'Desselecionar Todos',
            noneSelectedText: 'Selecione uma opção',
            multiple: false
        });
    }
    
    function startSelectMultipleCompresX(){
        $("#compressibilidadeX").multiselect({
            selectedText: "# selecionado(s) de #",
            checkAllText: 'Selecionar Todos',
            uncheckAllText: 'Desselecionar Todos',
            noneSelectedText: 'Selecione uma opção',
            multiple: true
        });
    }
    
    function startSelectNaoMultipleCompresX(){
        $("#compressibilidadeX").multiselect({
            selectedText: "# selecionado(s) de #",
            checkAllText: 'Selecionar Todos',
            uncheckAllText: 'Desselecionar Todos',
            noneSelectedText: 'Selecione uma opção',
            multiple: false
        });
    }
    
    function startSelectMultipleResistX(){
        $("#resistenciaX").multiselect({
            selectedText: "# selecionado(s) de #",
            checkAllText: 'Selecionar Todos',
            uncheckAllText: 'Desselecionar Todos',
            noneSelectedText: 'Selecione uma opção',
            multiple: true
        });
    }
    
    function startSelectNaoMultipleResistX(){
        $("#resistenciaX").multiselect({
            selectedText: "# selecionado(s) de #",
            checkAllText: 'Selecionar Todos',
            uncheckAllText: 'Desselecionar Todos',
            noneSelectedText: 'Selecione uma opção',
            multiple: false
        });
    }
    
    $("#btnPlotarGrafico").click(function() {        
        
        
        if(getMarcadoresSelecionados().length > 0 && (idsSplitCaractX.length > 0 || idsSplitCompressX.length > 0 || idsSplitResistX.length > 0) &&
                (idsCaractY.length > 0 || idsCompressY.length > 0 || idsResistY.length > 0 || $('#profundidadeY').is(':checked') === true )){            
            
            var fatoresValue = constroiArrayValue(document.getElementsByClassName("valorFator"));
            var fatoresId = constroiArrayId(document.getElementsByClassName("valorFator"));
            var fatoresText = constroiArrayText(document.getElementsByClassName("valorFator"));

            var marcadores = getMarcadoresSelecionados();
            
           
            var idsEixoXConcat = new Array();
            idsEixoXConcat = idsSplitCaractX.concat(idsSplitCompressX, idsSplitResistX);
            
            $.ajax({               
                url: "<c:url value="/plotarGrafico"/>",
                data: { 
                    codigos: marcadores,
                    fatoresValor:  fatoresValue,
                    fatoresId:  fatoresId,
                    fatoresText:  fatoresText,
                    
                    idsSplitCaractX: idsSplitCaractX.valueOf(),
                    idsSplitCompressX: idsSplitCompressX.valueOf(),
                    idsSplitResistX: idsSplitResistX.valueOf(),
                    
                    idsEixoX: idsEixoXConcat.valueOf(), 
                    
                    idsCaractY: idsCaractY.valueOf(),
                    idsCompressY: idsCompressY.valueOf(),
                    idsResistY:idsResistY.valueOf(),
                    profundidadeYChecked: $('#profundidadeY').is(':checked')                    
                },      
                beforeSend : function(){                    
                    $('#conteudoModal').html('<span id=\"carregando\" \"></span>');                    
                },                     
                type: "POST", 
                success: function(data) {            
 
                   document.getElementById("conteudoModal").style.width = "1100px";            
                   document.getElementById("conteudoModal").style.height = "650px";           
                   document.getElementById("conteudoModal").style.border = "1px solid black";                              
                   
                   document.getElementById("modalPrincipal").style.width = "950px";                       
                   document.getElementById("modalPrincipal").style.marginLeft = "-550.5px"; //-444.5px é o que está por padrão   
                   document.getElementById("modalPrincipal").style.height = "650px";                   
                    $('.close-modal').css("visibility","hidden");                                               
                   $('#conteudoModal').html(data);
                   
                }
            });
            //event.preventDefault();        
        }else{
            alert("Você deve selecionar ao menos uma ilha e as opções mínimas para o eixos X e Y");
        }    
    });

    /**
    * Constroi um novo Array inerindo apenas a propriedade value de cada elemento
     * @param {type} dados
     * @returns {Array|constroiArrayValue.retornoArray} */
    function constroiArrayValue(dados){    
        if(dados !== null){
            var retornoArray = new Array();               
            for(i = 0; i < dados.length;i++){
                retornoArray.push(dados[i].value);
            }
            return retornoArray;
        }else{
            return null;
        }
    }
    
    /**
    * Constroi um novo Array inerindo apenas a propriedade id de cada elemento
     * @param {type} dados
     * @returns {Array|constroiArrayValue.retornoArray} */
    function constroiArrayId(dados){    
        if(dados !== null){
            var retornoArray = new Array();               
            for(i = 0; i < dados.length;i++){
                retornoArray.push(dados[i].id);
            }
            return retornoArray;
        }else{
            return null;
        }
    }
    
    /**
    * Constroi um novo Array inerindo apenas a propriedade name de cada elemento
     * @param {type} dados
     * @returns {Array|constroiArrayValue.retornoArray} */
    function constroiArrayText(dados){    
        if(dados !== null){
            var retornoArray = new Array();               
            for(i = 0; i < dados.length;i++){
                retornoArray.push(dados[i].name);
            }
            return retornoArray;
        }else{
            return null;
        }
    }
    
    
    
</script>