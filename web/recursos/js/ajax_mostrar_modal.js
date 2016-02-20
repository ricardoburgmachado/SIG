    
    
    
    $(".modalCadastrarUsuario").click(function() {
        $.ajax({           
            url: "/SIG/mostrarModalCadastrarUsuario",
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
    });
    
    $(".modalOpcoesParametro").click(function() {        
        $.ajax({           
            url: "/SIG/mostrarModalOpcoesParametro",
            data: { },      
            beforeSend : function(){                    
                $('#conteudoModal').html('<span id=\"carregando\" \"></span>');                    
            },
            type: "POST", 
            success: function(data) {            
               $('#conteudoModal').html('<span id=\"carregando\" \"></span>');         
               $('#conteudoModal').html(data);
               $('.close-modal').css("visibility","hidden");                      
            }
        });        
    });
    
    $(".modalCadastrarIlha").click(function() {

        $.ajax({           
            url: "/SIG/mostrarModalCadastrarIlha",
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
        
    });
    
    $(".modalListarIlhas").click(function() {
        $.ajax({           
            url: "/SIG/mostrarModalListarIlhas",
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

        
    });
    
    $(".modalListarUsuarios").click(function() {        
        $.ajax({           
            url: "/SIG/mostrarModalListarUsuarios",
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
    });
    
    
    $(".modalCadastrarClasse").click(function() {        
        $.ajax({           
            url: "/SIG/mostrarModalCadastrarClasse",
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
    });
    
    $(".modalListarClasse").click(function() {
        
        $.ajax({           
            url: "/SIG/mostrarModalListarClasses",
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
    });    
    
    $(".modalListarFatorAjuste").click(function() {
        $.ajax({           
            url: "/SIG/mostrarModalListarFatorAjuste",
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
    });
    
    $(".modalCadastrarFatorAjuste").click(function() {        
        $.ajax({           
            url: "/SIG/mostrarModalCadastrarFatorAjuste",
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
    });
    
    $(".modalCadastrarTemplatePadrao").click(function() {        
        $.ajax({           
            url: "/SIG/mostrarModalCadastrarTemplatePadrao",
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
    });
    
    
    $(".modalListarTemplatePadrao").click(function() {        
         $.ajax({           
            url: "/SIG/mostrarModalListarTemplatePadrao",
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
    });
    
    $(".modalCadastrarParametro").click(function() {        
        $.ajax({           
            url: "/SIG/mostrarModalCadastroParametro",
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
    });
    
    $(".modalListarParametros").click(function() {    
        $.ajax({           
            url: "/SIG/mostrarModalListarParametros",
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
    });
    
    $(".modalCadastrarParametroPlotagem").click(function() {        
        $.ajax({           
            url: "/SIG/mostrarModalCadastroParametroPlotagem",
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
    });
    
    
    $(".modalListarParametroPlotagem").click(function() {        
        $.ajax({           
            url: "/SIG/mostrarModalListarParametroPlotagem",
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
    });        
    
    
    $(".download_template").click(function() {        
        $.ajax({           
            url: "/SIG/consultaPermissao",
            data: {permissao : 'DownloadTemplate' },
            type: "GET", 
            success: function(data) {            

                if(data === true){
                    window.location = "download_template";                
                }else{
                    alert("Este usuário não tem permissão para acessar esta funcionalidade");
                }   
            }
        });        
    });        
    
    $(".download_ilha").click(function() {    
       $.ajax({           
            url: "/SIG/consultaPermissao",
            data: {permissao : 'ExportarIlha' },
            type: "GET", 
            success: function(data) {            
                console.log("RESPOSTA PERMISSÃO: "+data.toString());
                if(data === true){
                    if(getMarcadoresSelecionados().length == 1){
                        window.location = "exportar_ilha?id="+getMarcadoresSelecionados();
                    }else{
                        alert("Você deve selecionar tão somente uma ilha no mapa para exportá-la");
                    }
                }else{
                    alert("Este usuário não tem permissão para acessar esta funcionalidade");
                }   
            }
        });
    });  
    

    
