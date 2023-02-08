"use strict"; // good practice - see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Strict_mode
////////////////////////////////////////////////////////////////////////////////
// Vertex Order Exercise
// Your task is to determine the problem and fix the vertex drawing order.
// Check the function someObject()
// and correct the code that starts at line 17.
////////////////////////////////////////////////////////////////////////////////
/*global THREE, Coordinates, $, document*/
import * as THREE from "three";
import { OBJLoader } from "three/addons/loaders/OBJLoader.js";
import { OrbitControls } from "three/addons/controls/OrbitControls.js";
import {dat} from "/lib/dat.gui.min.js";
import {Coordinates} from "../lib/Coordinates.js";
let camera, renderer;
let windowScale;
window.scene = new THREE.Scene();
function someObject(material) {
	let geometry = new THREE.BufferGeometry();
	const vertices = new Float32Array( [
		3, 3, 0,
		7, 3, 0,
		7, 7, 0,

		3, 3, 0,
		7, 7, 0,
		3, 7, 0
	] );

	geometry.setAttribute( 'position', new THREE.BufferAttribute( vertices, 3 ) );

	// Student: some data below must be fixed
	// for both triangles to appear !
	//geometry.vertices.push( new THREE.Vector3( 3, 3, 0 ) );
	//geometry.vertices.push( new THREE.Vector3( 7, 3, 0 ) );
	//geometry.vertices.push( new THREE.Vector3( 7, 7, 0 ) );
	//geometry.vertices.push( new THREE.Vector3( 3, 7, 0 ) );

	//geometry.faces.push( new THREE.Face3( 0, 1, 2 ) );
	//geometry.faces.push( new THREE.Face3( 2, 0, 3 ) );

	let mesh = new THREE.Mesh( geometry, material );
	window.scene.add( mesh );
}

function init() {
	// Setting up some parameters
	let canvasWidth = 846;
	let canvasHeight = 494;
	// For grading the window is fixed in size; here's general code:
	//let canvasWidth = window.innerWidth;
	//let canvasHeight = window.innerHeight;
	let canvasRatio = canvasWidth / canvasHeight;

	// Camera: Y up, X right, Z up
	windowScale = 10;
	let windowWidth = windowScale * canvasRatio;
	let windowHeight = windowScale;

	camera = new THREE.OrthographicCamera( windowWidth / - 2, windowWidth / 2,
		windowHeight / 2, windowHeight / - 2, 0, 40 );

	let focus = new THREE.Vector3( 5,4,0 );
	camera.position.x = focus.x;
	camera.position.y = focus.y;
	camera.position.z = 10;
	camera.lookAt( focus );

	renderer = new THREE.WebGLRenderer({ antialias: true, preserveDrawingBuffer: true});
	renderer.setSize( canvasWidth, canvasHeight );
	renderer.setClearColor( 0xFFFFFF, 1.0 );

}

function addToDOM() {
	let container = document.getElementById('webGL');
	let canvas = container.getElementsByTagName('canvas');
	if (canvas.length>0) {
		container.removeChild(canvas[0]);
	}
	container.appendChild( renderer.domElement );
}

function showGrids() {
	// Background grid and axes. Grid step size is 1, axes cross at 0, 0
	Coordinates.drawGrid({size:100,scale:1,orientation:"z"});
	Coordinates.drawAxes({axisLength:11,axisOrientation:"x",axisRadius:0.04});
	Coordinates.drawAxes({axisLength:11,axisOrientation:"y",axisRadius:0.04});
}

function render() {
	renderer.render( window.scene, camera );
}


// Main body of the script
try {
	init();
	showGrids();
	let material = new THREE.MeshBasicMaterial( { color: 0xF6831E, side: THREE.FrontSide } );
	someObject(material);
	addToDOM();
	render();
} catch(e) {
	let errorReport = "Your program encountered an unrecoverable error, can not draw on canvas. Error was:<br/><br/>";
	$('#webGL').append(errorReport+e.stack);
}
