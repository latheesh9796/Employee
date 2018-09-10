
$(document).ready(function(){
  $("#myInput").on("keyup", function() {
    var value = $(this).val().toLowerCase();
    $("#myTable tr").filter(function() {
      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
  });
});

$(document).ready(function () {
    $('.projects tr').click(function (event) {
        if (event.target.type !== 'checkbox') {
            $(':checkbox', this).trigger('click');
        }
    });
});

$(".highlight td").on("click", function() {
    var tr = $(this).parent();
    if(tr.hasClass("selected")) {
        tr.removeClass("selected");
    } else {
        tr.addClass("selected");
    }
    
});
 $(document).ready(function(){
        $("#employeeInput").on("keyup", function() {
          var value = $(this).val().toLowerCase();
          $("#employeeTable tr").filter(function() {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
          });
        });
      });


function downloadProjects() {
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
        pdf.save('Projects.pdf');
    }, margins);
}
$(document).ready(function () {
    $('.employees tr').click(function (event) {
        if (event.target.type !== 'checkbox') {
            $(':checkbox', this).trigger('click');
        }
    });
});
