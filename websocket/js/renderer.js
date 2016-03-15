function render() {

  updateSize();

	// redraw arrow
	lineScene.remove(arrow);
	var origin = new THREE.Vector3(0, 0, 0);
	if (flag) {
		var direction = new THREE.Vector3().subVectors(targetPos, origin);
	} else {
		var direction = new THREE.Vector3().subVectors((0,0,0), origin);
	}
	arrow = new THREE.ArrowHelper(direction.clone().normalize(), origin, direction.length(), 0x000000);
	lineScene.add(arrow);


	// move sphere's light
	hemLight2.position.set(x, y, z);

	renderer.setClearColor( 0xffffff );
	renderer.setScissorTest( false );
	renderer.clear();
	renderer.setClearColor( 0xe0e0e0 );
	renderer.setScissorTest( true );

	scenes.forEach(function(scene) {

			// spins the sphere
	 		//scene.children[0].rotation.y = Date.now() * 0.0001;

			// draw the scene
			var element = scene.userData.element;

			// get its position relative to the page's viewport
			var rect = element.getBoundingClientRect();

			// check if it's offscreen. If so skip it
			if ( rect.bottom < 0 || rect.top  > renderer.domElement.clientHeight || rect.right  < 0 || rect.left > renderer.domElement.clientWidth ) {
				return;  // it's off screen
			}

			// set the viewport
			var width  = rect.right - rect.left;
			var height = rect.bottom - rect.top;
			var left   = rect.left;
			var bottom = renderer.domElement.clientHeight - rect.bottom;

			renderer.setViewport( left, bottom, width, height );
			renderer.setScissor( left, bottom, width, height );

			var camera = scene.userData.camera;

			renderer.render(scene, camera );
		} );
}
