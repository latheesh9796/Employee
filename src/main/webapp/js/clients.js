function downloadClients() {
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
    margins.left, // x coord
    margins.top, { // y coord
        'width': margins.width, // max width of content on PDF
        'elementHandlers': specialElementHandlers
    },

    function (dispose) {
        // dispose: object with X, Y of the last line add to the PDF 
        //          this allow the insertion of new lines after html
        pdf.save('Clients.pdf');
    }, margins);
}
$(document).ready(function () {
    $('.projects tr').click(function (event) {
        if (event.target.type !== 'checkbox') {
            $(':checkbox', this).trigger('click');
        }
    });
});
$('.demo').on('click', function (e) {
    $('.demo').toggleClass("colored");
});

 $(document).ready(function(){
        $("#projectInput").on("keyup", function() {
          var value = $(this).val().toLowerCase();
          $("#projectTable tr").filter(function() {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
          });
        });
      });

