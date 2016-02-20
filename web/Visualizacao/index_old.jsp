<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>

        <link rel="shortcut icon" href="imagens/Letter-S-dg-icon.png" >
            <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
            <meta http-equiv="content-type" content="text/html; charset=utf-8"/>

            <link rel="stylesheet" href="<c:url value="/recursos/css/style.css"/>" />
            <script type="text/javascript" src="<c:url value="/recursos/js/funcoes_mapa.js"/>" ></script>
            <script type="text/javascript" src="<c:url value="/recursos/js/funcoes_mapa.js"/>" ></script>

            <script type="text/javascript" src="https://maps.google.com/maps/api/js?sensor=false"></script> 
            
            <!--
            <link rel="stylesheet" href="<c:url value="/recursos/js/jquery/jquery-ui.css"/>" />
            <script type="text/javascript" src="<c:url value="/recursos/js/jquery/jquery.js"/>" ></script>
            <script type="text/javascript" src="<c:url value="/recursos/js/jquery/jquery-ui.min.js"/>" ></script>
            -->
            <script type="text/javascript" src="<c:url value="/recursos/js/jquery/jquery-1.11.0.js"/>" ></script>
            <!--<script type="text/javascript" src="<c:url value="/recursos/js/jquery/jquery-1.11.1.min.js"/>" ></script>-->

            <title>JSP Page</title>    
    </head>


    <body  onload="js_mapInitialize();">
                
        <div id="principal" >
            
            <div id="barrasuperior"></div>
            
            <div id="sub-barrasuperior"></div>
            
            
                <div id="menuvertical">  
                    <ul class="vert-one">
                      <li><a id="menu_btn_principal" href="#" title="CSS Menus">Principal</a></li>
                       <li><a id="menu_btn_cadatrarilha" href="#" title="CSS Menus">Cadastrar ilha</a></li>
                    </ul>
                </div>
                
                <div id="conteudo">       
                    <div id="map_canvas" class="mapa"><!-- MAPA --></div>
                </div>            
            
        </div>
        
        <script>
            
             $('#menu_btn_principal').click(function(event) {           
               $.ajax({       
                url: "<c:url value="/"/>",                           
                type: "GET",
                dataType: "html",
                success: function(data) {
                    location.reload();
                }
                });
                event.preventDefault();                
             });
             
             $('#menu_btn_cadatrarilha').click(function(event) {
                $.ajax({       
                    url: "<c:url value="/form_cadatrarilha"/>",                           
                    type: "GET",
                    success: function(data) {
                    $('#conteudo').html(data);                
                }
                });
                event.preventDefault();
             });
             

        </script>

    </body>
</html>

