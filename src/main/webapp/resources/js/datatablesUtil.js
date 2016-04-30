function makeEditable() {
    $('#add').click(function () {
        $('#id').val(0);
        $('#editRow').modal();
    });

    $('.delete').click(function () {
        deleteRow($(this).attr("id"));
    });

    $('#detailsForm').submit(function () {
        save();
        return false;
    });

    $('.edit').click(function () {
        $('#id').val($(this).attr("id"));
        $('#name').val($(this).attr("name"));
        $('#email').val($(this).attr("email"));
        $('#editRow').modal();
    });

    $('.changeStatus').click(function () {
        changeActiveStatus($(this).attr("id"), $(this).attr("checked"));
    });

    $('#filter').submit(function () {
        mealFilter();
    });

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(event, jqXHR, options, jsExc);
    });
}

function mealFilter() {
    var form = $('#filter');
    $.ajax({
        type: "POST",
        url: ajaxUrl + "filter",
        data: form.serialize(),
        success: function () {
                datatableApi.clear();
                $.each(data, function (key, item) {
                    datatableApi.rows.add(item);
                });
                datatableApi.draw();
            successNoty('filtered');
        }
    });
}

function deleteRow(id) {
    $.ajax({
        url: ajaxUrl + id,
        type: 'DELETE',
        success: function () {
            updateTable();
            successNoty('Deleted');
        }
    });
}

function updateTable() {
    $.get(ajaxUrl, function (data) {
        datatableApi.clear();
        $.each(data, function (key, item) {
            datatableApi.row.add(item);
        });
        datatableApi.draw();
    });
}

function save() {
    var form = $('#detailsForm');
    debugger;
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: form.serialize(),
        success: function () {
            $('#editRow').modal('hide');
            updateTable();
            successNoty('Saved');
        }
    });
}

function changeActiveStatus(id, activeStatus) {
    $.ajax({
        url: ajaxUrl + id + "/" + activeStatus,
        type: 'PUT',
        success: function () {
            updateTable();
            successNoty('Change Active Status to user ' + id);
        }
    });
}

var failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(text) {
    closeNoty();
    noty({
        text: text,
        type: 'success',
        layout: 'bottomRight',
        timeout: true
    });
}

function failNoty(event, jqXHR, options, jsExc) {
    closeNoty();
    failedNote = noty({
        text: 'Failed: ' + jqXHR.statusText + "<br>",
        type: 'error',
        layout: 'bottomRight'
    });
}
