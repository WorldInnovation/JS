$('document').ready(function(){

        $("#test").click(function(){
            $.get("/ajaxtest",function(data,status){
                $('#content').append('<br/>' + data + ' : ' + status);
                //alert("Data: " + data + "\nStatus: " + status);
            });
        });

        //---get departments
        $("#getDeps").click(function () {
            $.get("/deps",function (data,status) {//
                $('#content').append('<br/>' + 'Status: '+ status);
                $.each(data, function(k, v) {
                    $.each(v, function(key, value) {
                        if (key == 'id') $('#content').append('<br/>'  + value);
                            if(key == 'name') $('#content').append(' '  + value);
                    })
                })
            })
        })
    //---

    $("#butSaveDep").click( function(){
        $.ajax({
            url: '/depSave',
            type : "POST",
            dataType : 'json',
            data : $("#depSave").serialize(), // post data || get data
            success : function(result) {
                // you can see the result from the console
                // tab of the developer tools
                console.log(result);
            },
            error: function(xhr, resp, text) {
                console.log(xhr, resp, text);
            }
        })
    });

    function arrayToTable(tableData) {
        var table = $('<table></table>');

        $(tableData).each(function (i, rowData) {
            var row = $('<tr></tr>');
            $(rowData).each(function (j, cellData) {
                row.append($('<td>'+cellData+'</td>'));
            });
            table.append(row);
        });
        return table;
    }

    ///---table
  $("#getDepTable").click( function () {
      $.ajax({
          type: "GET",
          url: "/deps",
          dataType: 'json',
          success: function (data) {
              if(data){
                  var table = $('<table>' +'<caption>' + '<h2>' + 'Departments' + '</h2>' +'</caption>' + '</table>');
                  var row = $('<tr></tr>');
                  row.append('<td>' + "id" + '</td>');
                  row.append('<td>' + "name" + '</td>');
                  row.append('<td>' + "select" + '</td>');
                  row.append('<td>' + "edit" + '</td>');
                  row.append('<td>' + "delete" + '</td>');
                  table.append(row);
                  $.each(data, function(k, v) {
                      row = $('<tr></tr>');
                  $.each(v, function(key, value) {
                      var idDep;
                      if (key == 'id') {idDep = value;
                          row.append('<td>' + value + '</td>' + '<input type="hidden" name="depID" value="${idDep}">');
                      }
                      if (key == 'name') {
                          row.append('<td>' + value + '</td>');
                          row.append('<td>' + '<input type="submit" value="Select">' + '</td>' + '<input type="hidden" name="depID" value="${idDep}">');
                          row.append('<td>' + '<input type="submit" value="Edit">' + '</td>' + '<input type="hidden" name="depID" value="${idDep}">');
                          row.append('<td>' + '<input type="submit" value="Delete">' + '</td>' + '<input type="hidden" name="depID" value="${idDep}">');
                      }
                  })
                  table.append(row);
              })
                  $('#content').append(table);
            }
          }
      });
        });

        var DepartmetList = (function () {
           $.get("")

        })
    /*$('body').append('<a href ="http://google.com">Jump to google< />');
    var markers = 1;*/
});


