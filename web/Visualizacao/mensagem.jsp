
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

 <!-- INICIO JQUERY TOAST MESSAGE-->
    <script type="text/javascript" src="<c:url value="/recursos/js/jqueryToastMessage/jquery.toastmessage-min.js"/>" ></script>
    <script type="text/javascript" src="<c:url value="/recursos/js/jqueryToastMessage/funcoes.js"/>" ></script> 
    <link rel="stylesheet" href="<c:url value="/recursos/js/jqueryToastMessage/css/jquery.toastmessage-min.css"/>" />        
<!-- FIM JQUERY TOAST MESSAGE-->  


    <c:if test="${not empty mensagem}">
        <script>            
            $( document ).ready(function() {                
                switch ('${tipoMensagem}'){
                  case 'erro': showErrorToast('${mensagem}'); break;
                  case 'sucesso': showSuccessToast('${mensagem}'); break;
                  case 'aviso': showStickyWarningToast('${mensagem}'); break;
                  default:  
                }
            });
        </script>
    </c:if>