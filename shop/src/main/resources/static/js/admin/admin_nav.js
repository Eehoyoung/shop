$(function () {
    $('.menu-dropdown').click(function () {
        $(this).children('.dropdown').slideToggle();
    })
    $('.dropdown').click(function (e) {
        e.stopPropagation();
    })
});
