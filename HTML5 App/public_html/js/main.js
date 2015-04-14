$( document ).ready(function() {
    
      // Retrieve the object from storage
        var retrievedObject =  JSON.parse(localStorage.getItem('checklist'));
        
        if (retrievedObject !== null) {
            $('.overview-checklist').append('<li><a href=""><div class="glyphicon glyphicon-trash trashcan"></div><h1 class="title"></h1><p class="participants">Deelnemers: </p></a></li>');
            jQuery.each( retrievedObject, function( i, field ) {
                console.log(field.name);
                switch(field.name) {
                    case 'title': 
                      $('.overview-checklist li').find('.title').text(field.value);
                      break;
                    case 'faculty':
                      $('.overview-checklist li').find('.participants').text('Deelnemers: ' + field.value);
                      break;
                  case 'check[]':
                      break;
                }
                
            });
        }
//      jQuery.each( array, function( i, ield ) {
//        console.log(field.value);
//      });

});
//        
//        <li>
//					
//                                            <a href="#smart-phone"> <div class="glyphicon glyphicon-trash trashcan"></div>	<h1>Wintersport Mayrhofen 2014</h1>
//						<p>
//							Deelnemers: Jerry, Jimmy en jijzelf.
//						</p> </a>
//                                            
//					</li>