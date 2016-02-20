<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
<body>
    
    
    <a id="btnExportarResultados" title="Exportar resultados para arquivo Excel" href="#"></a>    
    
    <span style="visibility: hidden" id="span_carregando_grafico"><span id="loading"></span></span>
    
    <a id="btnSelecionarOpcoes" title="Selecionar opções novamente" href="#"></a>    
    
    <div id="grafico" style="height: 465px; width: 100%; clear: both "></div>
     
</body>

</html>

<script>
    
    
    $(document).ready(function() {     
        js_plotarGrafico(organizaDadosGrafico('${resultados}'));        
    });
    
    /*
    Highcharts.setOptions({
        global:{
            useUTC: true
        },
        lang:{
            contextButtonTitle: "Opções para impressão do gráfico",
            downloadJPEG: "Download do gráfico em imagem formato JPEG",
            downloadPDF: "Download do gráfico em documento formato PDF",
            downloadPNG: "Download do gráfico em imagem formato PNG",
            downloadSVG: "Download do gráfico em imagem formato SVG", 
            loading: "Carregando dados ...",
            printChart: "Imprimir gráfico",
            resetZoom: "Resetar zoom"
        }         
    });
    */

    Highcharts.setOptions({
        global:{
            useUTC: true
        }         
    });

    
    function js_plotarGrafico(entrada) {                 
         chart = new Highcharts.Chart({
         
         
         
         chart: {
         layout: 'vertical',
         renderTo: 'grafico',
         type: 'line',
         inverted: false,
         width: 1100,
         height: 575,//altura do gráfico
         zoomType: 'xy',          
         style: {
         margin: '0 auto'
         }
         },        
         title: {
         text: 'Gráfico de resultados'
         //text: 'tipo ensaio: '+aEnsaios[0].tipo_ensaio.toString()+ ' linha: '+esp_linha 
         },
         subtitle: {
         //text: 'Autor: X'
         },
         xAxis: {
         
         labels: {
         style: {
         }
         },
         
         reversed: false,
         title: {
         enabled: true,
         text: ""
         //text: "NOME DO PARÂMETRO"
         },
         maxPadding: 0.05,
         minorGridLineWidth: 0,
         minorTickInterval: 'auto',
         minorTickLength: 10,
         minorTickWidth: 1
         
         },
         yAxis: {
             //min: 0,
         title: {
         //text: "Profundidade (m)"
         text: entrada[0].eixoY
         },
         //lineWidth: (marker ? 0.8 : 0.1),
         lineWidth: 0.8,
         minorGridLineWidth: 0,
         minorTickInterval: 'auto',
         minorTickLength: 10,
         minorTickWidth: 1
         },
         legend: {
         layout: 'vertical',
         align: 'top',
         verticalAlign: 'bottom',
         x: 0,
         y: 0,
         borderWidth: 1,
         maxHeight: 100,//altura máxima da legenda
         maxWidth: 1050,
            navigation:{

            }         
         },         
         tooltip: {
            enabled: true,
            formatter: function() {
               //return '<b>'+ this.entrada.name +'</b><br/>'+
               return ''+
               this.x +': '+ this.y +'(m)';
            }
         },         
         plotOptions: {
            series: {
                marker: {
                radius: 7
                },
                pointStart: 0
            },
            line:{
               lineWidth: 4 ,
               radius: 8      
            }
         },
         data:{
            startRow: 0,
            startColumn: 0
         },
         series: entrada 
         
        
         
         });                  
         chart.hideLoading();                  
    }
    
    
    /**
     * Método responsável por organizar os dados na mesma estrutura esperada para ser processada pela biblioteca geradora do gráfico
     * @Param entrada
     * @returns {Array():retornoArray}
     */
    function organizaDadosGrafico(entrada){
        
        
        var entradaJSON;        
        if(entrada !== null ){
            entradaJSON = JSON.parse(entrada);                
        }
        var retornoArray = new Array();
        var coordenadasArray;     
        var contCoorden;
        var lineWidth;
        var radius;
        var marker;
        //console.log("tamaho raiz: "+entradaJSON.length);
        for(w = 0; w < entradaJSON[0].length; w++){        
            coordenadasArray = new Array();
            contCoorden = 0;
            for(i= 0; i < entradaJSON[0][w].coordenadas.length; i++){                                                            
                //console.log("nome: "+entradaJSON[0][w].nome+" | x: "+entradaJSON[0][w].coordenadas[i].x+" | y: "+entradaJSON[0][w].coordenadas[i].y);                
                coordenadasArray.push( [  
                    parseFloat(entradaJSON[0][w].coordenadas[i].x) ,
                    parseFloat( entradaJSON[0][w].coordenadas[i].y )]  ) ;    
                
                contCoorden++;
            }
            
            if(contCoorden > 50){
                lineWidth = 1; radius = 0;marker = {radius: 0}; 
            }else{
                lineWidth = 0; radius = 5;marker = {radius: 5}; 
            }            
                       
            /*var name = ""+entradaJSON[0][w].nomeIlha+"("+entradaJSON[0][w].idIlha+") \n\
                    | Ensaio(s): "+entradaJSON[0][w].idEnsaio+"("+entradaJSON[0][w].classeEnsaio+") | X: "+entradaJSON[0][w].eixoX+" | Y: "+entradaJSON[0][w].eixoY;*/
            
            var name = ""+entradaJSON[0][w].nomeIlha+"\n\
                    | Ensaio(s): "+entradaJSON[0][w].classeEnsaio+" | X: "+entradaJSON[0][w].eixoX+" | Y: "+entradaJSON[0][w].eixoY;
           
            
            retornoArray.push({name: name, data: coordenadasArray, lineWidth:lineWidth, radius:radius, marker: marker, eixoY: entradaJSON[0][w].eixoY});            
            
        }
        console.log(retornoArray);
        return retornoArray; 
    }
    
    function removeCarctEspeciais(value){
        //return value.replace(/[-[\]{}()*+?%&amp;amp;amp;amp;amp;@!?¨_:;'"<>/=\\^$|#\b]/g, "");
        //return value.replace(/[¨'"]/g, "");
        return value.replace("'", "");
    }
    
    $("#btnExportarResultados").click(function() {        
        
        $.ajax({           
            url: "/SIG/consultaPermissao",
            data: {permissao : 'ExportarResultados' },
            type: "GET", 
            success: function(data) {            
                if(data === true){
                    document.getElementById("span_carregando_grafico").style.visibility = "visible";
                    window.location = "exportar_dados";
                    document.getElementById("span_carregando_grafico").style.visibility = "hidden";       
                }else{
                    alert("Este usuário não tem permissão para acessar esta funcionalidade");
                }   
            }
        });        
    });
    
    $("#btnSelecionarOpcoes").click(function() {                   
        $.ajax({           
            url: "/SIG/mostrarModalOpcoesParametro",
            data: { },      
            beforeSend : function(){                    
                $('#conteudoModal').html('<span id=\"carregando\" \"></span>');                    
            },
            type: "POST", 
            success: function(data) {            
               $('#conteudoModal').html(data);
               $('.close-modal').css("visibility","hidden");                     
            }
        });
        document.getElementById("conteudoModal").style.width = "850px";            
        document.getElementById("conteudoModal").style.height = "600px";
    });    
    
    
</script>



