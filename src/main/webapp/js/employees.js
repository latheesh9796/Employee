
$(document).ready(function(){
        $("#myInput").on("keyup", function() {
          var value = $(this).val().toLowerCase();
          $("#myTable tr").filter(function() {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
          });
        });
});

function downloadEmployees() {
    var pdf = new jsPDF('landscape', 'pt', 'a4');
    source = $('#customers')[0];
    specialElementHandlers = {
        '#bypassme': function (element, renderer) {
            return true
        }
    };
    margins = {
    top: 50,
    bottom: 50,
    left: 100,
    width: 800
    };

    pdf.fromHTML(
    source, 
    margins.left, 
    margins.top, { 
        'width': margins.width,
        'elementHandlers': specialElementHandlers
    },

    function (dispose) {
        pdf.save('Employees.pdf');
    }, margins);
}

function calculate_age(dob) { 
    var diff_ms = Date.now() - dob.getTime();
    var age_dt = new Date(diff_ms); 
    console.log(Math.abs(age_dt.getUTCFullYear() - 1970));
    return Math.abs(age_dt.getUTCFullYear() - 1970);
}
