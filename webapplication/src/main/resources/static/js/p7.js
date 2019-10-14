function lendcopy() {
    var id = $("#reservationModal").find("#recipient-name").val();
    var duration = $("#inlineFormCustomSelect").children("option:selected").val();
    console.log("id: " + id + ", duration: " + duration);
    $.ajax({
        url: 'resa',
        contentType: 'application/json',
        dataType: 'text',
        data: '{"id":' + id + ',"duration":' + duration + '}',
        type: 'POST',
        error: function () {
            console.log("erreur....")
        },
        success: function (result) {
            $("#reservationModal").modal("hide");
            console.log("reservation effectuee...." + result);
            location.reload();
        }
    });
}