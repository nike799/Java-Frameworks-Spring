const $btnViruses = $('#radioButton-viruses');
const $btnCapitals = $('#radioButton-capitals');

$btnViruses.on('change', getViruses);
$btnCapitals.on('change', getCapitals);

function getViruses() {
    //Clear result div
    $("#resultArea").html("");

    //Crate table html tag
    let table = $(" <table class=\"table table-hover\"></table>").appendTo("#resultArea");

    //Create table header row
    let rowHeader = $("<tr></tr>").appendTo(table);
    $("<td></td>").text("#").appendTo(rowHeader).css('font-weight', 'bold');
    $("<td></td>").text("Name").appendTo(rowHeader).css('font-weight', 'bold');
    $("<td></td>").text("Magnitude").appendTo(rowHeader).css('font-weight', 'bold');
    $("<td></td>").text("Released On").appendTo(rowHeader).css('font-weight', 'bold');
    $("<td></td>").text("Actions").appendTo(rowHeader).css('font-weight', 'bold');

    //Get JSON data by calling action method in controller
    fetch('http://localhost:8000/viruses/fetch-viruses')
        .then(response => response.json())
        .then(
            $('#sentence-select').remove())
        .then(
            $('#your-choice').text(function (i, oldText) {
                return oldText === 'Your choice' || 'All Capitals' ? 'All Viruses' : oldText;
            }))
        .then(data => {
            $.each(data, function (i, value) {
                //Create new row for each record
                let row = $("<tr></tr>").appendTo(table);
                $("<td></td>").text(i + 1).appendTo(row);
                $("<td></td>").text(value.name).appendTo(row);
                $("<td></td>").text(value.magnitude).appendTo(row);
                $("<td></td>").text(value.releasedOn).appendTo(row);
                $("<td></td>")
                    .append(
                        '<form action="/viruses/delete/ID" method="post">'.replace('ID', value.id) +
                        '<button type="submit" class="btn-danger">Delete</button>' +
                        '</form>')
                    .appendTo(row);
                $("<td></td>")
                    .append(
                        '<form action="/viruses/edit/ID" method="get">'.replace('ID', value.id) +
                        '<button type="submit" class="btn-danger">Edit</button>' +
                        '</form>')
                    .appendTo(row);
            })
        });
}

function getCapitals() {
    //Clear result div
    $("#resultArea").html("");

    //Crate table html tag
    let table = $(" <table class=\"table table-hover\"></table>").appendTo("#resultArea");

    //Create table header row
    let rowHeader = $("<tr></tr>").appendTo(table);
    $("<td></td>").text("#").appendTo(rowHeader).css('font-weight', 'bold');
    $("<td></td>").text("Name").appendTo(rowHeader).css('font-weight', 'bold');
    $("<td></td>").text("Latitude").appendTo(rowHeader).css('font-weight', 'bold');
    $("<td></td>").text("Longitude").appendTo(rowHeader).css('font-weight', 'bold');

    //Get JSON data by calling action method in controller
    fetch('http://localhost:8000/viruses/fetch-capitals')
        .then(response => response.json())
        .then(
            $('#sentence-select').remove())
        .then(
            $('#your-choice').text(function (i, oldText) {
                return oldText === 'Your choice' || 'All Viruses' ? 'All Capitals' : oldText;
            })
        )
        .then(data => {
            $.each(data, function (i, value) {
                //Create new row for each record
                let row = $("<tr></tr>").appendTo(table);
                $("<td></td>").text(i + 1).appendTo(row);
                $("<td></td>").text(value.name).appendTo(row);
                $("<td></td>").text(value.latitude).appendTo(row);
                $("<td></td>").text(value.longitude).appendTo(row);
            })
        });
}

