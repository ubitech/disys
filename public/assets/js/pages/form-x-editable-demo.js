$(function(){
    //ajax mocks
    $.mockjaxSettings.responseTime = 500; 
    
    $.mockjax({
        url: '/post',
        response: function(settings) {
            log(settings, this);
        }
    });

    $.mockjax({
        url: '/error',
        status: 400,
        statusText: 'Bad Request',
        response: function(settings) {
            this.responseText = 'Please input correct value'; 
            log(settings, this);
        }        
    });
    
    $.mockjax({
        url: '/status',
        status: 500,
        response: function(settings) {
            this.responseText = 'Internal Server Error';
            log(settings, this);
        }        
    });
  
    $.mockjax({
        url: '/groups',
        response: function(settings) {
            this.responseText = [ 
             {value: 0, text: 'Guest'},
             {value: 1, text: 'Service'},
             {value: 2, text: 'Customer'},
             {value: 3, text: 'Operator'},
             {value: 4, text: 'Support'},
             {value: 5, text: 'Admin'}
           ];
           log(settings, this);
        }        
    });
    
	$.mockjax({
        url: '/RestaurantCousine',
        response: function(settings) {
            this.responseText = [ 
			 {value: 0, text: 'Α�?γεντίνικη'},
             {value: 1, text: 'Ελληνική'},
             {value: 2, text: 'Γαλλική'},
             {value: 3, text: 'Ιταλική'},
             {value: 4, text: 'Κινέζικη'},
             {value: 5, text: 'Κο�?εάτικη'},
             {value: 6, text: 'Μεξικάνικη'},
             {value: 7, text: 'Μπυ�?α�?ία'}
           ];
           log(settings, this);
        }        
    });

	$.mockjax({
        url: '/Merida',
        response: function(settings) {
            this.responseText = [ 
			 {value: 0, text: '1/2 (μισή)'},
             {value: 1, text: 'Κανονική'},
             {value: 2, text: '2 ατόμων'},
             {value: 3, text: '4 ατόμων'}
           ];
           log(settings, this);
        }        
    });
	
		$.mockjax({
        url: '/UserCategory',
        response: function(settings) {
            this.responseText = [ 
			 {value: 0, text: 'Διαχει�?ιστής'},
             {value: 1, text: 'Καταστηματά�?χης'},
             {value: 2, text: 'Πελάτης'}
           ];
           log(settings, this);
        }        
    });

	
    function log(settings, response) {
            var s = [], str;
            s.push(settings.type.toUpperCase() + ' url = "' + settings.url + '"');
            for(var a in settings.data) {
                if(settings.data[a] && typeof settings.data[a] === 'object') {
                    str = [];
                    for(var j in settings.data[a]) {str.push(j+': "'+settings.data[a][j]+'"');}
                    str = '{ '+str.join(', ')+' }';
                } else {
                    str = '"'+settings.data[a]+'"';
                }
                s.push(a + ' = ' + str);
            }
            s.push('RESPONSE: status = ' + response.status);

            if(response.responseText) {
                if($.isArray(response.responseText)) {
                    s.push('[');
                    $.each(response.responseText, function(i, v){
                       s.push('{value: ' + v.value+', text: "'+v.text+'"}');
                    }); 
                    s.push(']');
                } else {
                   s.push($.trim(response.responseText));
                }
            }
            s.push('--------------------------------------\n');
            $('#console').val(s.join('\n') + $('#console').val());
    }                 
    
});