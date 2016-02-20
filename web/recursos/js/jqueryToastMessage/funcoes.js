
/**
 * http://akquinet.github.io/jquery-toastmessage-plugin/demo/demo.html
 * @returns {undefined}
 */

/*
 function showSuccessToast() {
 $().toastmessage('showSuccessToast', "Success Dialog which is fading away ...");
 }
 */

function showSuccessToast(texto) {
    
    var textoGlobal = texto;
    
    var specialChars = "!@#$^&%*()+=-[]\{}|:?,.";
    for (var i = 0; i < specialChars.length; i++) {
        textoGlobal = textoGlobal.replace(new RegExp("\\" + specialChars[i], 'gi'), '');
    }

    $().toastmessage('showToast', {
        text: textoGlobal,
        sticky: true,
        position: 'top-center',
        type: 'success',
        closeText: '',
        close: function() {
           // console.log("toast is closed ...");
        }
    });
}


function showStickySuccessToast() {
    $().toastmessage('showToast', {
        text: 'cadastrado com sucesso!',
        sticky: true,
        //position: 'top-right',
        position: 'top-center',
        type: 'success',
        closeText: '',
        close: function() {
           // console.log("toast is closed ...");
        }
    });

}
/**
 function showNoticeToast() {
 $().toastmessage('showNoticeToast', "Notice  Dialog which is fading away ...");
 }
 */
function showNoticeToast() {
    $().toastmessage('showNoticeToast', "Notice  Dialog which is fading away ...");
}
function showStickyNoticeToast() {
    $().toastmessage('showToast', {
        text: 'Notice Dialog which is sticky',
        sticky: true,
        position: 'top-right',
        type: 'notice',
        closeText: '',
        close: function() {
           // console.log("toast is closed ...");
        }
    });
}
function showWarningToast() {
    $().toastmessage('showWarningToast', "Warning Dialog which is fading away ...");
}
function showStickyWarningToast(texto) {
    var textoGlobal = texto;
    
    var specialChars = "!@#$^&%*()+=-[]\{}|:?,.";
    for (var i = 0; i < specialChars.length; i++) {
        textoGlobal = textoGlobal.replace(new RegExp("\\" + specialChars[i], 'gi'), '');
    }
    $().toastmessage('showToast', {
        text: textoGlobal,
        sticky: true,
        position: 'top-center',
        type: 'warning',
        closeText: '',
        close: function() {
            //console.log("toast is closed ...");
        }
    });
}
function showErrorToast(texto) {
    
    var textoGlobal = texto;
    
    var specialChars = "!@#$^&%*()+=-[]\{}|:?,.";
    for (var i = 0; i < specialChars.length; i++) {
        textoGlobal = textoGlobal.replace(new RegExp("\\" + specialChars[i], 'gi'), '');
    }

    $().toastmessage('showToast', {
        text: textoGlobal,
        sticky: true,
        position: 'top-center',
        type: 'error',
        closeText: '',
        close: function() {
            //console.log("toast is closed ...");
        }
    });

}

function showStickyErrorToast() {
    $().toastmessage('showToast', {
        text: 'Error Dialog which is sticky',
        sticky: true,
        position: 'top-right',
        type: 'error',
        closeText: '',
        close: function() {
           // console.log("toast is closed ...");
        }
    });
}

function showStickyErrorToast(texto) {
    var textoGlobal = texto;
    
    var specialChars = "!@#$^&%*()+=-[]\{}|:?,.";
    for (var i = 0; i < specialChars.length; i++) {
        textoGlobal = textoGlobal.replace(new RegExp("\\" + specialChars[i], 'gi'), '');
    }
    
    $().toastmessage('showToast', {
        //text: 'Error Dialog which is sticky',
        text: textoGlobal,
        sticky: true,
        position: 'top-right',
        type: 'error',
        closeText: '',
        close: function() {
            //console.log("toast is closed ...");
        }
    });
}