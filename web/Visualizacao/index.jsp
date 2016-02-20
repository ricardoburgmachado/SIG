<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<%@page import="Modelo.GerenciamentoUsuarios.Usuario"%>

<%
    Usuario user = (Usuario) request.getSession().getAttribute("usuario");
%>
<c:set value="usuario" var="user"></c:set>


    <!DOCTYPE html>
    <html xmlns="http://www.w3.org/1999/xhtml">
        <head>

            <link rel="shortcut icon" href="imagens/Letter-S-dg-icon.png" >
                <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
                <meta http-equiv="content-type" content="text/html; charset=utf-8"/>

            <script type="text/javascript" src="<c:url value="/recursos/js/funcoes_mapa.js"/>" ></script>

            <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?v=3.exp&key=AIzaSyAqFpSbY2aUXW9ngG3E9h2JwprbzLpjHT8"></script>            
            <!--<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?v=3.exp"></script>-->            
            <!--<script type="text/javascript" src="https://maps.google.com/maps/api/js?sensor=false"></script>-->            
            <!--<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?v=3.20"></script>-->
            
            
            <link rel="stylesheet" href="<c:url value="/recursos/css/style.css"/>" />

            <link rel="stylesheet" href="<c:url value="/recursos/js/jquery/jquery-ui.css"/>" />
            <script type="text/javascript" src="<c:url value="/recursos/js/jquery/jquery.js"/>" ></script>
            <script type="text/javascript" src="<c:url value="/recursos/js/jquery/jquery-ui.min.js"/>" ></script>
            <script type="text/javascript" src="<c:url value="recursos/js/jquery/js/jquery-1.7.2.min.js"/>" ></script>

            <script  type="text/javascript" src="<c:url value="/recursos/js/ui.dialog.js"/>" ></script>

            <!-- INICIO MENU -->
            <link rel="stylesheet" href="<c:url value="/recursos/css/jquery.mmenu.all.css"/>" />
            <link rel="stylesheet" href="<c:url value="/recursos/css/demo.css"/>" />
            <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
            <script src="<c:url value="/recursos/js/jquery.min.js"/>" ></script>
            <script src="<c:url value="/recursos/js/jquery.mmenu.min.all.js"/>" ></script>
            <!-- FIM MENU -->            


            <script type="text/javascript" src="<c:url value="/recursos/js/jquery.modal.js"/>" ></script>

            <!-- INICIO JQUERY TOAST MESSAGE-->
            <script type="text/javascript" src="<c:url value="/recursos/js/jqueryToastMessage/jquery.toastmessage-min.js"/>" ></script>
            <script type="text/javascript" src="<c:url value="/recursos/js/jqueryToastMessage/funcoes.js"/>" ></script> 
            <link rel="stylesheet" href="<c:url value="/recursos/js/jqueryToastMessage/css/jquery.toastmessage-min.css"/>" />        
            <!-- FIM JQUERY TOAST MESSAGE-->  
            
            <!-- INICIO LIB PARA GRÁFICOS -->  
            <script type="text/javascript" src="<c:url value="/recursos/js/Highcharts-3.0.10/js/highcharts.js"/>" ></script>
            <script type="text/javascript" src="<c:url value="/recursos/js/Highcharts-3.0.10/js/modules/exporting.js"/>" ></script>
            <!-- FIM LIB PARA GRÁFICOS -->  
            
            
            <script type="text/javascript">
                $(function() {
                    $('nav#menu').mmenu();
                });                
            </script>
            
            <!-- INICIO JQUERY MASK MONEY -->  
            <script type="text/javascript" src="<c:url value="/recursos/js/jqueryMaskMoney/jquery.maskMoney.js"/>" ></script>            
            
            <!-- FIM JQUERY MASK MONEY -->  
            
            <title>JSP Page</title>    
    </head>

    <body onload="js_mapInitialize();">

          <c:import url="mensagem.jsp"></c:import>
        
        <div id="page">

            <div class="header">
                <div id="topo">

                    <button type="button" id="btnSairSistema" title="Clique aqui para sair do sistema">Sair do sistema</button>
                    <div id="usuario">
                        Usuário: ${usuario.nome} (${usuario.perfil.nome})                        
                    </div>


                </div>
                <a href="#menu"></a>Menu               
            </div>

            <!--<div class="conteudo">-->

            <div id="map_canvas" class="mapa"><!-- MAPA --></div>

            <!--</div>-->

            <nav id="menu">
                <div class="xx">
                    <c:import url="menu.jsp"></c:import>
                    </div>
                </nav>


            <c:import url="modal.jsp"></c:import>




            </div>    

        </body>
        <script>
            $('#btnSairSistema').click(function() {
               
                $.get("<c:url value="/sairSistema"/>",
                        
                function(data, status) {
                    window.location = "";
                });
            });
            
            
            
        </script>        
        
    
            
            
            
</html>

