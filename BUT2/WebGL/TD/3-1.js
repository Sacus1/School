"use strict"; // good practice - see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Strict_mode
////////////////////////////////////////////////////////////////////////////////
// Clock hand rotation: rotate the hand into the proper orientation
////////////////////////////////////////////////////////////////////////////////
/*global THREE, Coordinates, document, window, dat, $*/
import * as THREE from "three";
import { OBJLoader } from "three/addons/loaders/OBJLoader.js";
import { OrbitControls } from "three/addons/controls/OrbitControls.js";
import {dat} from "/lib/dat.gui.min.js";
import {Coordinates} from "../lib/Coordinates.js";

let camera, renderer;
let cameraControls, effectController;
const clock = new THREE.Clock();
let gridX = false;
let gridY = false;
let gridZ = false;
let axes = true;
let ground = true;

function fillScene() {
	window.scene = new THREE.Scene();
	window.scene.fog = new THREE.Fog( 0x808080, 2000, 4000 );

	// LIGHTS
	const ambientLight = new THREE.AmbientLight(0x222222);

	const light = new THREE.DirectionalLight(0xFFFFFF, 1.0);
	light.position.set( 200, 400, 500 );

	const light2 = new THREE.DirectionalLight(0xFFFFFF, 1.0);
	light2.position.set( -500, 250, -200 );

	window.scene.add(ambientLight);
	window.scene.add(light);
	window.scene.add(light2);

	const faceMaterial = new THREE.MeshLambertMaterial({color: 0xFFECA9});
	const markMaterial = new THREE.MeshLambertMaterial({color: 0x89581F});
	const mark12Material = new THREE.MeshLambertMaterial({color: 0xE6880E});
	const handMaterial = new THREE.MeshLambertMaterial({color: 0x226894});

	// clock
	const clock = new THREE.Mesh(
		new THREE.CylinderGeometry(75, 75, 10, 32), faceMaterial);
	//new THREE.CubeGeometry( 150, 5, 150 ), faceMaterial );
	clock.position.y = 5;
	window.scene.add( clock );

	// marks
	let cube = new THREE.Mesh(
		new THREE.BoxGeometry(20, 4, 15), mark12Material);
	cube.position.x = 60;
	cube.position.y = 9;
	window.scene.add( cube );

	cube = new THREE.Mesh(
		new THREE.BoxGeometry( 10, 4, 10 ), markMaterial );
	cube.position.x = -60;
	cube.position.y = 9;
	window.scene.add( cube );

	cube = new THREE.Mesh(
		new THREE.BoxGeometry( 10, 4, 10 ), markMaterial );
	cube.position.z = 60;
	cube.position.y = 9;
	window.scene.add( cube );

	cube = new THREE.Mesh(
		new THREE.BoxGeometry( 10, 4, 10 ), markMaterial );
	cube.position.z = -60;
	cube.position.y = 9;
	window.scene.add( cube );

	// CODE FOR THE CLOCK HAND
	cube = new THREE.Mesh(
		new THREE.BoxGeometry( 110, 4, 4 ), handMaterial );
	cube.position.y = 14;

	// YOUR CODE HERE
	cube.rotation.y = 2*Math.PI/3;
	window.scene.add( cube );
}

function drawHelpers() {
	if (ground) {
		Coordinates.drawGround({size:10000});
	}
	if (gridX) {
		Coordinates.drawGrid({size:10000,scale:0.01});
	}
	if (gridY) {
		Coordinates.drawGrid({size:10000,scale:0.01, orientation:"y"});
	}
	if (gridZ) {
		Coordinates.drawGrid({size:10000,scale:0.01, orientation:"z"});
	}
	if (axes) {
		Coordinates.drawAllAxes({axisLength:200,axisRadius:1,axisTess:50});
	}
}


function init() {
	const canvasWidth = 846;
	const canvasHeight = 494;
	// For grading the window is fixed in size; here's general code:
	//var canvasWidth = window.innerWidth;
	//var canvasHeight = window.innerHeight;
	const canvasRatio = canvasWidth / canvasHeight;

	// RENDERER
	renderer = new THREE.WebGLRenderer( { antialias: true } );
	renderer.gammaInput = true;
	renderer.gammaOutput = true;
	renderer.setSize(canvasWidth, canvasHeight);
	renderer.setClearColor( 0xAAAAAA, 1.0 );

	// CAMERA
	camera = new THREE.PerspectiveCamera( 30, canvasRatio, 1, 10000 );
	camera.position.set( -370, 420, 190 );
	// CONTROLS
	cameraControls = new OrbitControls(camera, renderer.domElement);
	cameraControls.target.set(0,0,0);

	fillScene();

}

function addToDOM() {
	const container = document.getElementById('webGL');
	const canvas = container.getElementsByTagName('canvas');
	if (canvas.length>0) {
		container.removeChild(canvas[0]);
	}
	container.appendChild( renderer.domElement );
}

function animate() {
	window.requestAnimationFrame(animate);
	render();
}

function render() {
	const delta = clock.getDelta();
	cameraControls.update(delta);

	if ( effectController.newGridX !== gridX || effectController.newGridY !== gridY || effectController.newGridZ !== gridZ || effectController.newGround !== ground || effectController.newAxes !== axes)
	{
		gridX = effectController.newGridX;
		gridY = effectController.newGridY;
		gridZ = effectController.newGridZ;
		ground = effectController.newGround;
		axes = effectController.newAxes;

		fillScene();
		drawHelpers();
	}
	renderer.render(window.scene, camera);
}

function setupGui() {

	effectController = {

		newGridX: gridX,
		newGridY: gridY,
		newGridZ: gridZ,
		newGround: ground,
		newAxes: axes
	};

	const gui = new dat.GUI();
	gui.add( effectController, "newGridX").name("Show XZ grid");
	gui.add( effectController, "newGridY" ).name("Show YZ grid");
	gui.add( effectController, "newGridZ" ).name("Show XY grid");
	gui.add( effectController, "newGround" ).name("Show ground");
	gui.add( effectController, "newAxes" ).name("Show axes");
}


try {
	init();
	setupGui();
	drawHelpers();
	addToDOM();
	animate();
} catch(e) {
	const errorReport = "Your program encountered an unrecoverable error, can not draw on canvas. Error was:<br/><br/>";
	$('#webGL').append(errorReport+e.stack);
}
