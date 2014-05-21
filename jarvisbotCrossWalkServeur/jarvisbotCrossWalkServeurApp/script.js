

function echo(text){
	 //$('#log').append(text+"<br/>");
  	//alert(text);
    console.log(text);
}

var $client, $session;
var self = this;

function initVline(){

	//create client

	var serviceId='serverjarvisbot';

	var options = { 
		serviceId: serviceId,
		ui : false,                 
    	uiVideoPanel : 'video-wrapper'
	};

	$client = vline.Client.create(options);

	this.calls_ = [];
  	this.term_ = undefined;

  	if ($client.isLoggedIn()) {
  		/*echo('Already logged in'); 
      console.log('logged');
	    $session = $client.getDefaultSession();
	    afterLogin();
	    return;*/
      $client.logout();
	 }

  console.log('notlogged');
  	$client.login('serverjarvisbot').
    done(function(session) {
      	$session = session;
      	afterLogin();
      	echo('Success logged in');      
    }, this).
    fail(function(err) {
      	echo('Unable to log in. Did you set your service ID?');
    }, this);	

    autoLogin(user,pass);
}

function afterLogin(){
	echo("afterLogin");
  stopAutoLogin();

	$client.
        on('recv:im', onIm, self).
        on('change:connectionState', onChangeConnectionState, self); // Try disabling/enabling your network
    
  $client.
          on('add:mediaSession', onAddMediaSession, self).
          on('remove:mediaSession', onRemoveMediaSession, self);

	//$session.postMessage("serverjarvisbot:6yzm2etpz2nra94w", "test2");
}

  function onIm(event) {
   /* var msg = event.message,
        sender = msg.getSender();
    // format message in the style of the unix write command
    this.term_.echo('\n[[;#FFF;]Message from ' + sender.getDisplayName() + ' at ' +
        new Date(msg.getCreationTime()).toLocaleTimeString() + ' ...]' +
        '\n[[;#0F0;]'+  msg.getBody(false) + ']' +
        '\n[[;#FFF;]EOF]');*/
  }

   function onChangeConnectionState(event) {
    echo(formatConnectionMessage_());
  }

   function formatConnectionMessage_() {
    return 'vLine Cloud connection state: ' + $client.getConnectionState();
  }

function onAddMediaSession(event) {
    //alert('add');
    var mediaSession = event.target;
    addMediaSession_(mediaSession);
}

function onRemoveMediaSession(event) {
	//alert('remove');

  var mediaSession = event.target;
	removeMediaSession_(mediaSession);
}

function addMediaSession_(mediaSession) {
	// add event handler for add stream events
  //mediaSession.on('mediaSession:addLocalStream mediaSession:addRemoteStream', function(event) {
	mediaSession.on('mediaSession:addRemoteStream', function(event) {
		var stream = event.stream;
		// guard against adding a local video stream twice if it is attached to two media sessions
		/*if ($('#' + stream.getId()).length) {
			return;
		}*/
		// create video or audio element
		var elem = $(event.stream.createMediaElement());
		elem.prop('id', stream.getId());
		// You can style the HTML element that was created however you want.
		// This example shows how to flip the remote video upside-down.
		// Uncomment to see it in action
		
		/*if (stream.isRemote()) {
		//elem.css('transform', 'scaleY(-1)');         // Firefox
		//elem.css('-webkit-transform', 'scaleY(-1)'); // Chrome
		}*/
		
		
    $('#video-wrapper').append(elem);
    console.log('add');

	});
	// add event handler for remove stream events
	mediaSession.on('mediaSession:removeLocalStream mediaSession:removeRemoteStream', function(event) {
  		$('#' + event.stream.getId()).remove();
	});

	// The Call object tracks the lifecycle of the mediaSession
	this.calls_.push(new Call(this.term_, mediaSession));
}

function removeMediaSession_(mediaSession) {
	// Clean up call list when call ends
	this.calls_.splice(this.calls_.indexOf(mediaSession), 1);
}

function Call(term, mediaSession) {
    this.mediaSession_ = mediaSession;
    this.term_ = term;

    mediaSession.
        on('enterState:pending', onEnterPending, this).
        on('enterState:incoming', onEnterIncoming, this).
        on('exitState:incoming', onExitIncoming, this).
        on('enterState:outgoing', onEnterOutgoing, this).
        on('enterState:connecting', onEnterConnecting, this).
        on('enterState:active', onEnterActive, this).
        on('enterState:closed', onEnterClosed, this);

    function onEnterPending() {
      startLogoAnim();      
      echo("Click 'Allow' to start call ^^^");
    }
    function onEnterIncoming() {
      echo('Incoming call from ' + mediaSession.getDisplayName());
      //his.showAcceptPrompt(mediaSession);  
      mediaSession.start();    
      echo('[Call ACCEPTED]');
    }
    function onExitIncoming() {
      //this.hideAcceptPrompt();
      //mediaSession.stop();  
      stopLogoAnim();
    }
    function onEnterOutgoing() {
      echo('Calling ' + mediaSession.getDisplayName() + '...');
    }
    function onEnterConnecting() {
      echo('[Call CONNECTING]');      
    }
    function onEnterActive() {
      echo('[Call STARTED]');
      stopLogoAnim();
      affichageVideo();
    }
    function onEnterClosed() {
      echo('[Call ENDED]');
      affichageVeille();
    }
}

Call.prototype.end = function() {
	this.mediaSession_.stop();
};

/*Call.prototype.showAcceptPrompt = function(mediaSession) {
	var term = this.term_;
	term.push(function(command) {
	  if (command.match(/y|yes|^$/i)) {
	    mediaSession.start();
	    echo('[Call ACCEPTED]');
	  } else if (command.match(/n|no/i)) {
	    mediaSession.stop();
	    echo('[Call REJECTED]');
	  }
	}, {
	  prompt: '[[;#FFF;]Accept? (YES/no):] '
	});
};*/

/*Call.prototype.hideAcceptPrompt = function() {
	this.term_.pop();
};*/