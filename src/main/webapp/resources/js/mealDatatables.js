var ajaxUrl = 'ajax/profile/meals/';
var datatableApi;

function updateTable() {
    $.ajax({
        type: "POST",
        url: ajaxUrl + 'filter',
        data: $('#filter').serialize(),
        success: function (data) {
            updateTableByData(data);
        }
    });
    return false;
}

$(function () {
    datatableApi = $('#datatable').DataTable({
        "ajax": {
            "url": ajaxUrl,
            "dataSrc": ""
        },
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime",
                "render": function (date, type, row) {
                    if (type == 'display') {
                        var dateObject = new Date(date);
                        return '<span>' + dateObject.toISOString().substring(0, 10) + ' ' + dateObject.toISOString().substring(11, 16) + '</span>';
                    }
                    return date;
                }
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderEditBtn
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderDeleteBtn
            }
        ],
        "order": [
            [
                0,
                "desc"
            ]
        ],
        "createdRow": function (row, data, dataIndex) {
            if (data.exceed) {
                $(row).css("color", "red");
            } else {
                $(row).css("color", "green");
            }
        },
        "initComplete": function () {
            $('#filter').submit(function () {
                updateTable();
                return false;
            });

            $('#datepicker1').datetimepicker({
                timepicker: false,
                format: 'Y-m-d'
            });

            $('#datepicker2').datetimepicker({
                timepicker: false,
                format: 'Y-m-d'
            });

            $('#timepicker1').datetimepicker({
                datepicker: false,
                format: 'H:m'
            });

            $('#timepicker2').datetimepicker({
                datepicker: false,
                format: 'H:m'
            });

            $('#datetimepicker').datetimepicker({
                format: 'Y-m-d H:m'
            });

            makeEditable();
        }
    });
});