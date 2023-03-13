$(function () {
    //main carousel
    let count = 2
    $('input[type=radio]').click(function () {
        count = parseInt($(this).attr('id').substr(-1, 1));
    })
    const mainCarousel_timer = setInterval(function () {
        $('#radio' + count).prop('checked', true);
        count = count % 5 + 1;
    }, 3000);

    $('.fa-chevron-left').click(function () {
        $('input[type=radio]').each(function () {
            if ($(this).is(':checked')) {
                count = parseInt($(this).attr('id').substr(-1, 1)) - 1;
                if (count === 0) {
                    count = 5
                }
                return false;
            }
        })
        $('#radio' + count).prop('checked', true);
    })

    $('.fa-chevron-right').click(function () {
        $('input[type=radio]').each(function () {
            if ($(this).is(':checked')) {
                count = parseInt($(this).attr('id').substr(-1, 1)) + 1;
                if (count === 6) {
                    count = 1
                }
                return false;
            }
        })
        $('#radio' + count).prop('checked', true);
    })


    //weeklybest carousel
    const wbc = $('.weeklybest_carousel').get();
    const wbc_length = wbc.length;
    const menu = $('.weeklybest_menu span').get();
    let wbc_idx = 0;
    let menu_idx = 0
    $('.weeklybest_menu span').each(function () {
        $(this).attr('menu_num', menu_idx);
        menu_idx += 1
    })


    $('.weeklybest_menu span').on('click', function () {
        wbc_idx = parseInt($(this).attr('menu_num'));

        for (let i = 0; i < wbc_length; i++) {
            if (i === wbc_idx) {
                $(this).addClass('active');
                $(wbc[i]).show();
                $(wbc[i]).css('opacity', '1');
            } else {
                $(menu[i]).removeClass('active');
                $(wbc[i]).hide();
            }
        }
    })

    let wbc_timer = setInterval(function () {
        for (let i = 0; i < wbc_length; i++) {
            if (i === wbc_idx) {
                $(menu[i]).addClass('active');
                $(wbc[i]).show();
                $(wbc[i]).css('opacity', '1');
            } else {
                $(menu[i]).removeClass('active');
                $(wbc[i]).hide();
                $(wbc[i]).css('opacity', '0');
            }
        }
        wbc_idx = (wbc_idx + 1) % wbc_length;
    }, 3000);

    $('.hoverbtn').on('click', function (e) {
        console.log(e);
    })
})