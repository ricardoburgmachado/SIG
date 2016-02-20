function js_inicializarAbas() {

  $(".tab_content").hide();
  $(".tab_content:first").show(); 

  $("ul.tabs li").click(function() {
    $("ul.tabs li").removeClass("active");
    $(this).addClass("active");
    $(".tab_content").hide();
    var activeTab = $(this).attr("rel"); 
    $("#"+activeTab).fadeIn(); 
  });
  
}
//dsfsdf