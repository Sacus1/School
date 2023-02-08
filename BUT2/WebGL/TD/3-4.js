"use strict"; // good practice - see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Strict_mode
////////////////////////////////////////////////////////////////////////////////
// Clock hand rotation: rotate the hand into the proper orientation
////////////////////////////////////////////////////////////////////////////////
/*global THREE, Coordinates, document, window, dat, $*/
import * as THREE from "three";
import { OBJLoader } from "three/addons/loaders/OBJLoader.js";
import { OrbitControls } from "three/addons/controls/OrbitControls.js";
import {dat} from "/lib/dat.gui.min.js";
window.scene = new THREE.Scene();
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

	const snowMaterial = new THREE.MeshLambertMaterial({color: 0xFFFFFF});
	const woodMaterial = new THREE.MeshLambertMaterial({color: 0x75691B});

	let sphere = new THREE.Mesh(
		new THREE.SphereGeometry(20, 32, 16), snowMaterial);
	sphere.position.y = 20;
	window.scene.add( sphere );

	let sphere2 = new THREE.Mesh(
		new THREE.SphereGeometry( 15, 32, 16 ), snowMaterial );
	sphere2.position.y = 50;
	window.scene.add( sphere2 );

	sphere = new THREE.Mesh(
		new THREE.SphereGeometry( 10, 32, 16 ), snowMaterial );
	sphere.position.y = 70;
	window.scene.add( sphere );

	const cylinder = new THREE.Mesh(
		new THREE.CylinderGeometry(2, 2, 60, 32), woodMaterial);

	// YOUR CHANGES HERE
	// These positions are given just so you can see the stick.
	// You will need to reposition, etc.
	cylinder.rotation.x = Math.PI / 2;
	sphere2.add( cylinder );
	window.scene.add( sphere2 );
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

	//var container = document.getElementById('webGL');
	//container.appendChild( renderer.domElement );

	// CAMERA
	camera = new THREE.PerspectiveCamera( 30, canvasRatio, 1, 10000 );
	//camera.position.set( -170, 170, 40 );
	// CONTROLS
	cameraControls = new OrbitControls(camera, renderer.domElement);
	//cameraControls.target.set(0,50,0);
	camera.position.set(-120, 66, 23);
	cameraControls.target.set(0, 43, -8);

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
