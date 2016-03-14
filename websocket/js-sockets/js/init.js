function init() {

	canvas = document.getElementById( "c" );
	var template = document.getElementById( "template" ).text;
	var content = document.getElementById( "content" );

	// make Line scene

	lineScene = new THREE.Scene();

	// make a list item
	var element = document.createElement( "div" );
	element.className = "list-item";
	element.innerHTML = template.replace( '$', "Motion Vector");
	// Look up the element that represents the area
	// we want to render the scene
	lineScene.userData.element = element.querySelector( ".scene" );
	content.appendChild( element );

	var camera = new THREE.PerspectiveCamera( 50, 1, 1, 10 );
	camera.position.z = 15;
	lineScene.userData.camera = camera;

	var controls = new THREE.OrbitControls( lineScene.userData.camera, lineScene.userData.element );
	controls.minDistance = 2;
	controls.maxDistance = 5;
	controls.enablePan = false;
	controls.enableZoom = false;
	lineScene.userData.controls = controls;

	// new arrowHelper
	var origin = new THREE.Vector3(0, 0, 0);
	var targetPos1 = new THREE.Vector3(0, -1, 2);
	var direction = new THREE.Vector3().subVectors(targetPos1, origin);
	arrow = new THREE.ArrowHelper(direction.clone().normalize(), origin, direction.length(), 0x000000);
	lineScene.add(arrow);

	var axes = new THREE.AxisHelper(250);
	lineScene.add(axes);
	scenes.push( lineScene );



	// Make the sphere scene

	sphereScene = new THREE.Scene();
			// make a list item
	var element = document.createElement( "div" );
	element.className = "list-item";
	element.innerHTML = template.replace( '$', "Virtual Reality Sphere");

			// Look up the element that represents the area
			// we want to render the scene
	sphereScene.userData.element = element.querySelector( ".scene" );
	content.appendChild( element );

	var camera = new THREE.PerspectiveCamera( 50, 1, 1, 10 );
	camera.position.z = 2;
	sphereScene.userData.camera = camera;

	var controls = new THREE.OrbitControls(sphereScene.userData.camera, sphereScene.userData.element );
	controls.minDistance = 2;
	controls.maxDistance = 5;
	controls.enablePan = false;
	controls.enableZoom = false;
	sphereScene.userData.controls = controls;

			// add one random mesh to each scene
	var geometry = new THREE.SphereGeometry( 0.5, 100, 50 )

	var material = new THREE.MeshStandardMaterial( {
		color: new THREE.Color().setHSL( 0.9, 1, 0.75 ),
		roughness: 0.1,
		metalness: 0,
		shading: THREE.FlatShading
	} );

	sphereScene.add( new THREE.Mesh(geometry, material) );

	sphereScene.add(hemLight2);

	scenes.push(sphereScene);

	renderer = new THREE.WebGLRenderer( { canvas: canvas, antialias: true } );
	renderer.setClearColor( 0xffffff, 1 );
	renderer.setPixelRatio( window.devicePixelRatio );

}
