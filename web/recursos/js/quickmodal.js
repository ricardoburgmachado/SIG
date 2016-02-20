// leanModal v1.1 by Ray Stone - http://finelysliced.com.au
// Dual licensed under the MIT and GPL

(function ($){
	$.fn.extend({
		quickModal:function (options){
			var defaults = {
				closeButton: ".modal-close",
				overlayOpacity : 0.5,
				scrollBehind : false,
				overlayZIndex: 100,
				modalZIndex: 1000,
				overlayColor: "#000",
				modalWidth: "500",
				boxShadow: "0px 0px 10px rgba(0,0,0,0.5)",
				borderCss: "1px solid #444",
				borderRadius: "4px",
				modalBgColor: "#fff",
				headerBgColor: "rgb(185, 185, 185)",
				footerBgColor: "rgb(219, 219, 219)"
			};
			
			var overlay = $("<div id='bg_overlay'></div>");
			$("body").append(overlay);
			options = $.extend(defaults,options);
			
			return this.each(function (){			
				var o = options;
				$(this).click(function (e){
					var modal_id = $(this).attr("href");
					
					$("#bg_overlay").click(function (){
						close_modal(modal_id)
					});
					$(o.closeButton).click(function (){
						close_modal(modal_id)
					});

					if (o.scrollBehind == false) {
						$("body").css("position", "fixed")
					}

					$("#bg_overlay").css({
						"display" : "block",
						"opacity" : "0",
						"position": "absolute",
						"z-index": o.overlayZIndex,
						"background-color": o.overlayColor,
						"top": "0",
						"left": "0",
						"height": "100%",
						"width": "100%"						
					});
					$("#bg_overlay").fadeTo(200, o.overlayOpacity); //duration, opacity

					$(modal_id).css({
						"opacity": 0,						
						"display": "block",
						"position": "fixed",
						"background-color": o.modalBgColor,						
						"z-index": o.modalZIndex,					
						"-moz-border-radius": o.borderRadius,
						"-webkit-border-radius": o.borderRadius,
						"border-radius": o.borderRadius, 						
						"-webkit-box-shadow": o.boxShadow,
						"-moz-box-shadow": o.boxShadow,
						"box-shadow": o.boxShadow,
						"border": o.borderCss,
						"width": o.modalWidth
					});
					
					//need to set the width before calculating the top position
					var left = setLeftPosition(o.modalWidth);
					var top = setTopMargin(modal_id);					
					$(modal_id).css({
						"left": left,
						"top": top
					});
					
					var headerElement = modal_id + " .modal-header";
					$(headerElement).css({
						"background-color": o.headerBgColor
					});
					var footerElement = modal_id + " .modal-footer";
					$(footerElement).css({
						"background-color": o.footerBgColor
					});

					$(modal_id).fadeTo(200,1);
					e.preventDefault()
				})
			});
			function close_modal(modal_id){
				$("#bg_overlay").fadeOut(200);
				$(modal_id).css({ "display" : "none" })
				$("body").css("position", "static")
			}
			function setTopMargin(modal_id) {
				var windowHeight = $(window).height(); 
				var halfWindowHeight = windowHeight / 2; 				
				var modal_height = $(modal_id).outerHeight(false);
				var halfModalHeight = modal_height / 2;				
				var mTop = halfWindowHeight - halfModalHeight;
				return mTop;
			}
			function setLeftPosition(modalWidth){	
				var windowWidth = $(window).width();
				var halfWinWidth = windowWidth / 2;
				var halfModalWidth = modalWidth / 2;
				var mLeft = halfWinWidth - halfModalWidth;
				return mLeft;
			}
		}
	})
})(jQuery);
