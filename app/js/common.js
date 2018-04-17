$(function( ) {
    var $menu = $("#menu").mmenu({
            "extensions": {
                "all": [
                    "pagedim-black",
                    "position-right",
                    "theme-white"],
                "(max-width: 400px)": ["fullscreen"],
                "(min-height: 768px)": ["listview-large"]
            },
            "navbars": [
                {
                    "position": "top"
                }
            ],
            "navbar" : {
                "title": "UniService"
            }
        },
        {
            // configuration
            clone: true
        }
    );

    var $icon = $("#btn-menu");
    var API = $menu.data( "mmenu" );
    $icon.on( "click", function() {
        API.open();
        API.bind( "open:finish", function() {
            setTimeout(function() {
                $icon.addClass( "is-active" );
            }, 100);
        });
        API.bind( "close:finish", function() {
            setTimeout(function () {
                $icon.removeClass("is-active");
            }, 100);
        });
    });


});