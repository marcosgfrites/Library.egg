(function(){

    $(document).ready(function(){

        $('#btnTop').click(function(){
            $('body, html').animate({
                scrollTop: '0px'
            }, 300);
        });

        $(window).scroll(function(){
            if( $(this).scrollTop() > 0 ){
                $('#btnTop').slideDown(300);
            } else {
                $('#btnTop').slideUp(300);
            }
        });

    });

}())