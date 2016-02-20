  
  /* Variável do tipo googleMaps - Acesso ao mapa */
  var map;
  /* Markers - ensaios que são apresentados no mapa como ícones */
  var markers = [];
  
  /*

   *@example
   *  js_setView('incluirEnsaio.view.php', 'Incluir Ensaio', 1000, 520, { codigoEnsaio: 1}, 'figura.png');
   **/
  function js_setView(sViewName, sTitle, iWidth, iHeight, oParameters, sNomeImagem) {
    
    jQuery.ajaxSetup({
      cache: false
    });
    
    $("#view").dialog({ title: sTitle, width: iWidth, height: iHeight });
    $("#view").load("Visualizacao/" + sViewName, oParameters);
   
    //$("#view").hide();
    
    if (typeof(nomeImagem) != 'undefined') {
      
      $("#ui-dialog-title-view").html('<img src="imagens/' + sNomeImagem + '" height="18px"/> ' +  $("#ui-dialog-title-view").html());
      $("#ui-dialog-title-view").css("vertical-align", "top");    
    }
    
  }
  
   function setDialog(sViewName, sTitle){
        $('#cadastrar').dialog('destroy').remove(); 
             $('<div id="demo3">').load("Visualizacao/" + sViewName).dialog({ 
                 title: sTitle, width: 450, height: 220 
         }); 
    }
   /*
   *@abstract
   *Função responsável por inicializar o mapa e chama a função que inclui os ensaios
   *com as respectivas referências geográficas.
   *
   *@param void
   *
   *@returns void
   *
   *@example
   *  js_mapInitialize();
   **/
  function js_mapInitialize() {
  
    //alert("Inicializou a função js_mapInitialize");  
    
    var latlng   = new google.maps.LatLng(-22.965585, -43.370004);
    var myOptions = {
      zoom: 10,
      center: latlng,
      mapTypeId: google.maps.MapTypeId.HYBRID
    };
    map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);    
    //$("#map_canvas").height((screen.height - 290) + 'px');   
    
    //js_insertEnsaios();
    
  }
  
   /*
   *@abstract
   *Função responsável por inserir os ensaio no mapa. Ela busca eles na base de dados
   *e depois associa aos respetivos markers. Utilizada na função js_mapInitialize.
   *@author <a href="mailto:adrianoquiliao@gmail.com">Adriano Quilião de Oliveira</a>
   *
   *@param void
   *
   *@returns void
   *
   *@example
   *  js_insertEnsaios();
   **/
  function js_insertEnsaios() {
    
    $.ajax( {
      type: 'GET',
      //url: 'RPC/geral.RPC.php',
      url: '/Solos/busca_ilhas_ajax',
      dataType: 'json',

      success: function (data) {       
        
          js_setMarkers(data);
      },
      error: function(XMLHttpRequest, textStatus, errorThrown) {
        
        //alert(textStatus + " : " + errorThrown);
        
      }
    });
    
  }
  
   /*
   *@abstract
   *Função responsável por incluir os Markers (ensaios) no mapa e que também atribui as 
   *respectivas funcionalidades destes.
   *@param aEnsaios - Array de ensaios com as informações oriundas da base de dados (mesma nomenclatura de variáveis).
   *@returns void
   **/
  //function js_setMarkers(aEnsaios) {
  function js_setMarkers(ilha) {
    
    var infowindow = new google.maps.InfoWindow();
    var marker;
    var sContent = [];
    var latlng   = [];
    markers      = [];
    $('#txt_codigoEnsaios').val("");
    
    
    
    for (var x = 0; x < ilha.length; x++) { 
        
        /*alert("endereço: ["+x+"] "+ilha[x].localizacao.endereco+" \n\
            lat: "+ilha[x].localizacao.latitude+" long: "+ilha[x].localizacao.longitude+" \n\
            id ilha: "+ilha[x].id+" endereço: "+ilha[x].localizacao.endereco);
        */
       

    
        //for (var iI = 0; iI < aEnsaios.length; iI++) { 
        //for (var iI = 0; iI < ilha[x].length; iI++) { 

            //var ll = new google.maps.LatLng(parseFloat(aEnsaios[iI].es_latitude), parseFloat(aEnsaios[iI].es_longitude));
            var ll = new google.maps.LatLng(ilha[x].localizacao.latitude, ilha[x].localizacao.longitude);
            //var ll = new google.maps.LatLng(-22.969754, -43.365251);
            
            
            latlng.push(ll);
            
            marker = new google.maps.Marker({
              position: ll, 
              map: map,
              //title: aEnsaios[iI].es_endereco,
              title: ilha[x].localizacao.endereco,
              clickable: true,
              //icon: 'localhost:8080/Solos/recursos/imagens/mountains.png'
              icon: 'recursos/imagens/mountains.png'
              //<c:url value="recursos/imagens/information.png"/>
            });
            
           
            
            marker.set("id", "googleMarker");
            //markers.push(js_factoryMarker(marker, aEnsaios[iI].es_codigo ));
            markers.push(js_factoryMarker(marker, ilha[x].id ));
            //sContent[iI]  = '<b>Código:</b> ' + aEnsaios[iI].es_codigo + '<br> ';
            //sContent[iI] += '<b>Endereço:</b> ' + aEnsaios[iI].es_endereco + ' <br>';
            //sContent[iI] += '<b>Autor:</b> ' + aEnsaios[iI].es_autor;
            sContent[x]  = '<b>Código:</b> ' + ilha[x].id + '<br> ';
            sContent[x] += '<b>Endereço:</b> ' + ilha[x].localizacao.endereco + ' <br>';
            sContent[x] += '<b>Autor:</b> ' + ilha[x].autor.nome;

            //google.maps.event.addListener(marker, 'dblclick', (function(marker, iI) {
            google.maps.event.addListener(marker, 'dblclick', (function(marker, x) {
              return function() {

                //infowindow.setContent(sContent[iI]);
                infowindow.setContent(sContent[x]);
                //markers[iI].selected = false;
                markers[x].selected = false;
                if (marker.getIcon() == 'recursos/imagens/information.png') {

                  marker.setIcon('recursos/imagens/mountains.png'); 
                  infowindow.close();

                } else {

                  marker.setIcon('recursos/imagens/information.png');
                  infowindow.open(map, marker);
                }

              }
            //})(marker, iI));
            })(marker, x));

            //google.maps.event.addListener(marker, 'click', (function(marker, iI) {
            google.maps.event.addListener(marker, 'click', (function(marker, x) {
              return function() {


                infowindow.close();
                if (marker.getIcon() != 'recursos/imagens/mountains.png'){

                  marker.setIcon('recursos/imagens/mountains.png'); 
                  //markers[iI].selected = false;
                  markers[x].selected = false;

                } else {

                  marker.setIcon('recursos/imagens/star-3.png');
                  //markers[iI].selected = true;
                  markers[x].selected = true;

                }


              }
            //})(marker, iI));
            })(marker, x));
           
        //}
    }    
        var latlngbounds = new google.maps.LatLngBounds();
        for (var iJ = 0; iJ < latlng.length; iJ++)
          latlngbounds.extend(latlng[iJ]);
        map.setCenter(latlngbounds.getCenter());
        map.fitBounds(latlngbounds); 
    
  }
  
   /*
   *@abstract
   *Função responsável por criar objeto "Marker" para dominio interno de informações e
   *este será inserido no array de markers "markers".
   *@author <a href="mailto:adrianoquiliao@gmail.com">Adriano Quilião de Oliveira</a>
   *
   *@param oGoogleMarker - Objeto do tipo "google.maps.Marker", que é plotado no mapa.
   *@param iCodigo - Código de referência do ensaio (es_codigo).
   *
   *@returns oMarker - Atributos {selected: boolean, codigo: int, googleMarker: google.maps.Marker}
   *
   *@example
   * myMarker = js_factoryMarker(oGoogleMarker, 1);
   **/
  function js_factoryMarker(oGoogleMarker, iCodigo) {
  
    var oMarker          = new Object();
    oMarker.selected     = false;
    oMarker.codigo       = iCodigo;
    oMarker.googleMarker = oGoogleMarker;
    return oMarker;
  
  }
  
   /*
   *@abstract
   *Função que retorna uma string formatada com os ensaios (markers) que estão na condição de
   *selecionados. O separador é "," (exemplo: "1,3,4").
   *@author <a href="mailto:adrianoquiliao@gmail.com">Adriano Quilião de Oliveira</a>
   *
   *@param void
   *
   *@returns sCodigos - String formatada com códigos separados por virgula.
   *
   *@example
   * sCodigos = js_getSelectedMarkers();
   **/
  function js_getSelectedMarkers() {
    
    var sCodigos = "";
    for (var iI = 0; iI < markers.length; iI++)
      if (markers[iI].selected)
        sCodigos += (sCodigos == "" ? markers[iI].codigo : "," + markers[iI].codigo);
    return sCodigos; 
    
  }
  
   /*
   *@abstract
   *Função que abre a DIV carregando, e deve ser utilizda em requisições demoradas.
   *@author <a href="mailto:adrianoquiliao@gmail.com">Adriano Quilião de Oliveira</a>
   *
   *@param oElement - Elemento HTML onde ela será exibida.
   *
   *@returns void
   *
   *@example
   * js_abrirDivCarregando($('#view'));
   **/
  function js_abrirDivCarregando(oElement) {
    
    if ($("#dv_carregando").length == 0) {

      var pos = (parseInt($('#view').css('width').replace('px',''))/2) - (230/2);
      pos     += "px";
      var oDiv = $('<div id="dv_carregando" align="center"></div>');
      oDiv.css('background-color', '#FFFFFF');
      oDiv.css('position', 'absolute');
      oDiv.css('left', pos);
      oDiv.css('top', '40%');
      oDiv.css('display', 'table-cell');
      oDiv.css('vertical-align', 'middle');
      $('<img src="recursos/imagens/carregando.gif" />').appendTo(oDiv);
      oDiv.appendTo(oElement);
      
    } else {
      $("#dv_carregando").show();
      
    }
    
  }
  
   /*
   *@abstract
   *Função responsável por fechar DIV carregando.
   *@author <a href="mailto:adrianoquiliao@gmail.com">Adriano Quilião de Oliveira</a>
   *
   *@param void
   *
   *@returns void
   *
   *@example
   * js_fecharDivCarregando();
   **/
  function js_fecharDivCarregando() {
    
    if ($("#dv_carregando") != null) {
      $("#dv_carregando").hide();
    }
  }

  function limpaBD(){
      
      var r = confirm("Você tem certeza que deseja excluir todos os ensaios do banco de dados?");
      if (r == true){
          x = "SIM";
          window.location = "limpar_banco";
      }else{
          x = "CANCELAR";
      }
      
      
  }  


  /**
  * Direciona para a aba do gráfico após o usuário clicar em gerar gráfico
  * @returns {undefined}
  */  
  function manipulaAbas(){ 

    $( "#tabs" ).tabs({ active: [ 1 ] });
    $( "#tabs" ).tabs( "selected", 1 );
  }
 