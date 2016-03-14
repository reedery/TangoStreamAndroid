var targetPos;
var targetHemisphereLocation;
var flag = false;
var notFirst = false;
var hemLight2 = new THREE.HemisphereLight( 0x00FFFF, 0xFF8C00 );

// vars from gyroscope for hemisphere light
var x;
var y;
var z;

var lineScene = new THREE.Scene();
var sphereScene = new THREE.Scene();
var arrow;

// websocket functions

// called when socket connection established
ws.onopen = function() {
	console.log("open!");
    appendLog("Connected")
};

// called when a message received from server
ws.onmessage = function (evt) {
		var str = evt.data;
		var data = str.split("\t");
		targetPos = new THREE.Vector3(data[0], data[1], data[2]);
	//	targetHemisphereLocation = new THREE.Vector3(data[3], data[4], data[5]);
		x = data[3];
		y = data[4];
		z = data[5];
		flag = true;
  	appendLog("sending")
};

// called when socket connection closed
ws.onclose = function() {
    appendLog("Disconnected")
};

// called in case of an error
ws.onerror = function(err) {
    console.log("ERROR!", err )
};

// appends logText to log text area
function appendLog(logText) {
    var log = document.getElementById("log");
    log.value = log.value + logText + "\n"
}

// sends msg to the server over websocket
function sendToServer(msg) {
    ws.send(msg);
}


function updateSize() {
	var width = canvas.clientWidth;
	var height = canvas.clientHeight;
	if ( canvas.width !== width || canvas.height != height ) {
		renderer.setSize( width, height, false );
	}
}

function animate() {
	render();
	requestAnimationFrame( animate );
}
