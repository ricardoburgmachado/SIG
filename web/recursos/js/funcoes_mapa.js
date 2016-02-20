

    
    /* Variável do tipo googleMaps - Acesso ao mapa */
    var map;
    /* Markers - ilhas que são apresentados no mapa como ícones */
    var markers = [];


    function loadScript() {
        var script = document.createElement('script');
        $.get("/SIG/buscarMapa", {
            }, function(data, status) {
                script.src = data.urlServico;
        });
        script.type = 'text/javascript';
        document.body.appendChild(script);        
    }

    function js_mapInitialize() {
        
        var localizacaoInicial=null;                
        $.ajax( {
          type: 'GET',
          url: '/SIG/buscarMapa',
          dataType: 'json',
          success: function (dados) {       
                localizacaoInicial = new google.maps.LatLng(dados.latitudeInicial, dados.longitudeInicial);
                var myOptions = {
                zoom: 10,
                center: localizacaoInicial,
                mapTypeId: castTipos(dados.tipo)
                };
                map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);  
          },
          error: function(XMLHttpRequest, textStatus, errorThrown) {              
              return null;
          }
        });
        buscaMarcadoresBancoDados();
    }

    //window.onload = loadScript;

    function castTipos(tipo){        
        switch(tipo) {
            case 'SATELLITE':
                return google.maps.MapTypeId.SATELLITE
                break;
            case 'ROADMAP':
                return google.maps.MapTypeId.ROADMAP                
                break;
            case 'HYBRID':
                return google.maps.MapTypeId.HYBRID                
                break;
            default:
                return null;
        }
        
    }


    function inserirMarcadores(marcadores) {
        var infowindow = new google.maps.InfoWindow();
        var marker;
        var sContent = [];
        var latlng = [];
        var ll;
        markers = [];
        $('#txt_codigoEnsaios').val("");
        if(marcadores !== null && marcadores.length > 0){
            for (var x = 0; x < marcadores.length; x++) {

                ll = new google.maps.LatLng(marcadores[x].latitude, marcadores[x].longitude);
                latlng.push(ll);

                marker = new google.maps.Marker({
                    position: ll,
                    map: map,
                    title: marcadores[x].titulo,
                    clickable: true,
                    icon: 'recursos/imagens/mountains.png'
                });

                marker.set("id", "googleMarker");

                markers.push(fabricaMarcadores(marker, marcadores[x].id));
                sContent[x] = '<b>Código:</b> ' + marcadores[x].id + '<br> ';
                sContent[x] += '<b>Endereço:</b> ' + marcadores[x].titulo + ' <br>';

                google.maps.event.addListener(marker, 'dblclick', (function(marker, x) {
                    return function() {
                        infowindow.setContent(sContent[x]);
                        markers[x].selected = false;
                        if (marker.getIcon() == 'recursos/imagens/information.png') {

                            marker.setIcon('recursos/imagens/mountains.png');
                            infowindow.close();

                        } else {

                            marker.setIcon('recursos/imagens/information.png');
                            infowindow.open(map, marker);
                        }
                    }
                })(marker, x));

                google.maps.event.addListener(marker, 'click', (function(marker, x) {
                    return function() {


                        infowindow.close();
                        if (marker.getIcon() != 'recursos/imagens/mountains.png') {
                            marker.setIcon('recursos/imagens/mountains.png');

                            markers[x].selected = false;

                        } else {

                            marker.setIcon('recursos/imagens/star-3.png');
                            markers[x].selected = true;
                        }
                    }
                })(marker, x));
            }
        }else{
            //alert("Não existe nenhuma ilha cadastrada no sistema");
        }
        var latlngbounds = new google.maps.LatLngBounds();
        for (var iJ = 0; iJ < latlng.length; iJ++)
            latlngbounds.extend(latlng[iJ]);
        map.setCenter(latlngbounds.getCenter());
        map.fitBounds(latlngbounds);

    }
    
    
    function buscaMarcadoresBancoDados(){
        console.log("inicia buscaMarcadoresBancoDados()");
        $.ajax( {
          type: 'GET',
          url: '/SIG/buscarMarcadores',
          dataType: 'json',
          success: function (retorno) {                              
              inserirMarcadores(retorno);              
          },
          error: function(XMLHttpRequest, textStatus, errorThrown) {
              console.log("textStatus: "+textStatus+" | errorThrown: "+errorThrown);
          }
        });
    }
    
    function buscaMapa(){

        $.ajax( {
          type: 'GET',
          url: '/SIG/buscarMapa',
          dataType: 'json',
          success: function (dados) {       
              return dados;              
          },
          error: function(XMLHttpRequest, textStatus, errorThrown) {
              return null;
          }
        });
    }

    function getMarcadoresSelecionados() {

        var sCodigos = new Array();
        console.log("qtd markers: "+markers.length);
        
        for (var iI = 0; iI < markers.length; iI++)
          if (markers[iI].selected){            
            
            //sCodigos += (sCodigos === "" ? markers[iI].id : "," + markers[iI].id);
            sCodigos.push( markers[iI].id );
        }                   
        return sCodigos; 
    }


    function fabricaMarcadores(oGoogleMarker, iCodigo) {

        var oMarker          = new Object();
        oMarker.selected     = false;
        oMarker.id       = iCodigo;
        oMarker.googleMarker = oGoogleMarker;
        return oMarker;
    }


    function modalEventoListener(modal){
        
        $(modal).ready(function() {                 
            var btnFecharModal = document.getElementById('btnFecharModal');
            //btnFecharModal.addEventListener("click", sessaoIniciaRoolback, true);

            var btnCancelar = document.getElementById('btnCancelar');
            //btnCancelar.addEventListener("click", sessaoIniciaRoolback, true);
            
            var mmblocker = document.getElementById('efeito_blocker');//mm-blocker  mapedo no arquivo jquery.modal.js          
            //mmblocker.addEventListener("click", sessaoIniciaRoolback, true);
            
            var btnSalvarRegistro = document.getElementById('btnSalvarRegistro');
            //btnSalvarRegistro.addEventListener("click", sessaoSalvarRegistro, true);
            
        });        
    }
    
    function sessaoSalvarRegistro() {  
        console.log('sessaoSalvarRegistro');               
        $.get("/SIG/sessaoSalvarRegistro",
        {}
        ,function(data, status) {
            if(data){
                console.log("Sessão salvarRegistro foi criada");
            }else{
                console.log("A sessão salvarRegistro NÃO foi criada");
            }
        });                    
    }  
    
    function sessaoCancelarSalvarRegistro() {  
        console.log('sessaoCancelarSalvarRegistro');               
        $.get("/SIG/sessaoCancelarSalvarRegistro",
        {}
        ,function(data, status) {
            if(data){
                console.log("Sessão salvarRegistro foi cancelada");
            }else{
                console.log("A sessão cancelarSalvarRegistro NÃO foi criada");
            }
        });                    
    }  
    
    function sessaoIniciaRoolback() {  
        console.log('sessaoIniciaRoolback');               
        $.get("/SIG/sessaoIniciaRoolback",
        {}
        ,function(data, status) {
            if(data){
            console.log("Sessão roolback foi criada");
            }else{
                console.log("A sessão roolback NÃO foi criada");
            }
        });            
        showStickyWarningToast('A operação foi cancelada');                       
    }      

    /*function eventoRespostaPerguntar() {  
        console.log('iniciaRoolback');       
        var decisao = false;
        decisao = confirm("Tem certeza que deseja cancelar a operação?");
        if(decisao){//ok
            $.get("/SIG/iniciaRoolback",
             {}
             ,function(data, status) {
                if(data){
                    console.log("Sessão roolback criada");
                }else{
                    console.log("A sessão roolback NÃO foi criada");
                }
            });            
            showStickyWarningToast('A operação foi cancelada');        
        }else {//cancelar
            console.log('A operação não foi cancelada');
        }        
    } */


    