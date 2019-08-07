const mealAjaxUrl = "ajax/meals/";

// $(document).ready(function () {
$(function () {
    makeEditable({
            ajaxUrl: mealAjaxUrl,
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "dateTime"
                    },
                    {
                        "data": "description"
                    },
                    {
                        "data": "calories"
                    },
                    {
                        "defaultContent": "",
                        "orderable": false
                    },
                    {
                        "defaultContent": "",
                        "orderable": false
                    }
                ],
                "order": [
                    [
                        0,
                        "asc"
                    ]
                ]
            })
        }
    );
});

function filterMeal() {
    $.ajax({
        type: "GET",
        url: mealAjaxUrl + "filter",
        data: $("#mealsFilter").serialize()
    }).done(updateTableByData);
}

function updateTableByData(data) {
    context.datatableApi.clear().rows.add(data).draw();
}

function clearFilter() {
    $("#mealsFilter")[0].reset();
    $.get(mealAjaxUrl, updateTableByData);
}