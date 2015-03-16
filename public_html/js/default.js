$('.add-checklist').click(function() {
    window.location.replace("/add.html");
});


$(document).on('pagebeforeshow', '#home', function(event) {
    homepage();
});

$(document).on('pagebeforeshow', '#checklist', function(event) {
    checklist();
});

$(document).ready(function() {
    $('#plus2').click(function() {
        $(".checklijst-add").append('<p><input type="text" class="form-control extralink" name="check[]"><span id="plus2" class="glyphicon glyphicon-minus minus"></span></p>');
    });

    $(document).on('click', '.minus', function() {
        $(this).parent('p').remove();
    });

    $('.save-checklist').click(function() {
        add();
    });
   
    $('.edit-checklist').click(function() {
       alert('Deze feature komt in de android versie'); 
    });
    $(document.body).on('click','.trashcan', function(){
        var item_id = $(this).parent().attr('data-id');
        removeObjectById(item_id);
        $(this).parent().fadeOut();
    });
    
    $('.back').click(function() {
        window.location.replace("/main.html");
    });
});

function add(){
    // Retrieve the entered form data
    var title = $('[name="title"]').val();
    var participants = $('[name="participant"]').val();
    var checks = [];
        $('input[name^="check"]').each(function() {
            checks.push(($(this).val()));
        });
         
    // Fetch the existing objects
    objects = getObjects();
    var size = Object.keys(objects).length;
    var id = size;
    // Push the new item into the existing list
    objects.push({
        id: id,
        title: title,
        participants: participants,
        checks: checks
    });
    // Store the new list
    saveObjects(objects);
    // Reload the page to show the new objects
   document.location.href = '/main.html';
}

function getObjects(){
    // See if objects is inside localStorage
    if (localStorage.getItem("objects"))
    {
    // If yes, then load the objects
        objects = JSON.parse(localStorage.getItem("objects"));
    }
    else
    {
    // Make a new array of objects
        objects = new Array();
    }
    return objects;
}

function getObjectById(id) {
    objects = getObjects();
    var data = $.grep(objects, function(e) {
       return e.id == id; 
    });
    return data;
}

function removeObjectById(id) {
    objects = getObjects();
    var data = $.grep(objects, function(e){ 
     return e.id != id; 
    });
    saveObjects(data);
}
function saveObjects(objects){
    // Save the list into localStorage
    localStorage.setItem("objects", JSON.stringify(objects));
}

function homepage() {
    // Fetch the existing objects
    objects = getObjects();
    // Clear the list
    if (($('#overview-checklist').find('li'))) {
        $('#overview-checklist').find('li').remove();
    }
    // Add every object to the objects list
    $.each(objects, function(index, item)
    {
        element = '<li data-id="' + index + '"><div class="glyphicon glyphicon-trash trashcan"></div><a href="/checklist.html?id=' + index + '"><h1 class="title">' + item.title + '</h1><p class="participants">Deelnemers: ' + item.participants + '</p></a></li>';
        $('#overview-checklist').append(element);
    });
    $('#overview-checklist').listview();
    $('#overview-checklist').listview("refresh");
}

function checklist() {
    var id = getParameterByName('id');
    var object = getObjectById(id);
    $('#checks').find('li').remove();
    $.each(object, function(key, element) {
        $('#title').text(element.title);
        if (element.checks.length > 0) {
            $.each(element.checks, function(i, value){ 
                element = '<li><div class="glyphicon glyphicon-ok check-ok"></div>' + value + '</li>';
                $('#checks').append(element);
            });
        }
    });
}
    

 
function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}