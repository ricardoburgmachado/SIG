<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>

        <link rel="shortcut icon" href="imagens/Letter-S-dg-icon.png" >
            <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
            <meta http-equiv="content-type" content="text/html; charset=utf-8"/>


            <!--<script type="text/javascript" src="<c:url value="/recursos/js/funcoes.js"/>" ></script>-->


            <link rel="stylesheet" href="<c:url value="/recursos/css/style.css"/>" />

            <link rel="stylesheet" href="<c:url value="/recursos/js/jquery/jquery-ui.css"/>" />
            <script type="text/javascript" src="<c:url value="/recursos/js/jquery/jquery.js"/>" ></script>
            <script type="text/javascript" src="<c:url value="/recursos/js/jquery/jquery-ui.min.js"/>" ></script>
            <script type="text/javascript" src="<c:url value="recursos/js/jquery/js/jquery-1.7.2.min.js"/>" ></script>
            
            <!-- INICIO JQUERY TOAST MESSAGE-->
            <script type="text/javascript" src="<c:url value="/recursos/js/jqueryToastMessage/jquery.toastmessage-min.js"/>" ></script>
            <script type="text/javascript" src="<c:url value="/recursos/js/jqueryToastMessage/funcoes.js"/>" ></script> 
            <link rel="stylesheet" href="<c:url value="/recursos/js/jqueryToastMessage/css/jquery.toastmessage-min.css"/>" />        
            <!-- FIM JQUERY TOAST MESSAGE-->  


            <title>JSP Page</title>    
    </head>

    <body>

        <c:import url="mensagem.jsp"></c:import>
        
        <div id="page">


            <div id="login">
                
                <span class="titulo">Login de acesso ao sistema</span>


                <form:form action="/SIG/autentica" method="POST" modelAttribute="usuario" commandName="usuario" id="form_login">

                    
                    <fieldset> 
                        <span class="bloco">
                            <label for="nome">Usuário:</label>
                            <form:input type="text" path="nome" id="nome" value=""  />
                        </span>
                        <br/>
                        <span class="bloco">
                            <label for="nome">Senha:</label>
                            <form:input type="password" path="senha"  id="senha" value=""/>
                        </span>
                        <br/>
                        

                        <span style="clear: both; display: block;"></span>
                        <input id="btnAcessarSistema" style="cursor: pointer" class="enviar_form" type="submit" value="Acessar sistema"/>


                    </fieldset>
                </form:form>

            </div>    





        </div>    







    </body>

</html>

