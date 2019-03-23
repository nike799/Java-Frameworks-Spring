
const $btnViruses = $('#radioButton-viruses');
const $btnCapitals = $('#radioButton-capitals');

$btnViruses.on('change', getViruses)
$btnCapitals.on('change', getCapitals)

function getViruses() {
    $(".viruses-container").css('visibility', 'visible');
    $(".capitals-container").css('visibility', 'hidden');
    $('#sentence-select').remove();
    $('#your-choice').text(function (i, oldText) {
        return oldText === 'Your choice' || 'All Capitals' ? 'All Viruses' : oldText;
    });
}

function getCapitals() {
    $(".capitals-container").css('visibility', 'visible').css('margin-top','-200px');
    $(".viruses-container").css('visibility', 'hidden');
    $('#sentence-select').remove();
    $('#your-choice').text(function (i, oldText) {
        return oldText === 'Your choice' || 'All Viruses'? 'All Capitals' : oldText;
    });
}
