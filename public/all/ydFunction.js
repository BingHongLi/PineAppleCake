var curdata = {"vote":0,"cake1Sum":0,"cake2Sum":0,"totalTickets":11,"leftTickets":11,"winner": 0};


$(document).ready(function(){

$("#click-me-button" ).click(function(){
  curdata.vote = 1
  $.ajax({
      url: '/pineApple1',
      type: 'POST',
      data: JSON.stringify(curdata),
      contentType: 'application/json; charset=utf-8',
      dataType: 'json',
      async: false,
      success: function(msg) {
          alert(msg.leftTickets);
          curdata = msg;
      }
});
});


$("#click-me-button2" ).click(function() {

  });

$("#result" ).click(function() {
      $.ajax({
            url: '/pineAppleSum',
            type: 'POST',
            data: JSON.stringify(curdata),
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            async: false,
            success: function(msg) {
                alert("目前贏家: "+msg.winner);
            }
  });

});
});

