"use strict"; // good practice - see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Strict_mode
////////////////////////////////////////////////////////////////////////////////
// Draw a Square Exercise
// Your task is to complete the function square (at line 28).
// The function takes 4 arguments - coordinates x1, y1, x2, y2
// for the square and returns a geometry object (THREE.Geometry())
// that defines a square at the provided coordinates.
////////////////////////////////////////////////////////////////////////////////
/*global THREE, Coordinates, document*/
import * as THREE from "three";
import { OBJLoader } from "three/addons/loaders/OBJLoader.js";
import { OrbitControls } from "three/addons/controls/OrbitControls.js";
import {dat} from "/lib/dat.gui.min.js";
import {Coordinates} from "../lib/Coordinates.js";

var camera, renderer;
var windowScale;
window.scene = new THREE.Scene();
var camera, renderer;
var windowScale;

function exampleTriangle() {
	// This code demonstrates how to draw a triangle
	const triangle = new THREE.BufferGeometry();

	const vertices = new Float32Array( [
		1.0, 1.0,  0.0,
		3.0, 1.0, 0.0,
		3.0, 3.0,  0.0,
	] );

	triangle.setAttribute( 'position', new THREE.BufferAttribute( vertices, 3 ) );

	return triangle;
}

function drawSquare(x1, y1, x2, y2) {

	const square = new THREE.BufferGeometry();
	// square = 2 triangles (6 vertices)
	const vertices = new Float32Array( [
		x1, y1,  0.0,
		x2, y1, 0.0,
		x2, y2,  0.0,
		x1, y1,  0.0,
		x1, y2,  0.0,
		x2, y2,  0.0,
	] );
	square.setAttribute( 'position', new THREE.BufferAttribute( vertices, 3) );
	// don't forget to return the geometry!	The following line is required!
	return square;
}

function init() {
	// Set up some parameters
	const canvasWidth = 846;
	const canvasHeight = 494;
	// For grading the window is fixed in size; here's general code:
	//var canvasWidth = window.innerWidth;
	//var canvasHeight = window.innerHeight;
	const canvasRatio = canvasWidth / canvasHeight;
	// Camera: Y up, X right, Z up
	windowScale = 12;
	const windowWidth = windowScale * canvasRatio;
	const windowHeight = windowScale;

	camera = new THREE.OrthographicCamera(windowWidth/-2, windowWidth/2, windowHeight/2, windowHeight/-2, 0, 40);

	const focus = new THREE.Vector3(5, 5, 0);
	camera.position.x = focus.x;
	camera.position.y = focus.y;
	camera.position.z = 20;
	camera.lookAt(focus);

	renderer = new THREE.WebGLRenderer({ antialias: true, preserveDrawingBuffer: true});
	renderer.gammaInput = true;
	renderer.gammaOutput = true;
	renderer.setSize( canvasWidth, canvasHeight );
	renderer.setClearColor( 0xFFFFFF, 1.0 );
}

function addToDOM() {
	const container = document.getElementById('webGL');
	const canvas = container.getElementsByTagName('canvas');
	if (canvas.length>0) {
		container.removeChild(canvas[0]);
	}
	container.appendChild( renderer.domElement );
}

function render() {
	renderer.render( window.scene, camera );
}

function showGrids() {
	// Background grid and axes. Grid step size is 1, axes cross at 0, 0
	Coordinates.drawGrid({size:100,scale:1,orientation:"z"});
	Coordinates.drawAxes({axisLength:11,axisOrientation:"x",axisRadius:0.04});
	Coordinates.drawAxes({axisLength:11,axisOrientation:"y",axisRadius:0.04});
}

try {
	$('#webGL').append("toto");
	init();
	showGrids();
	// creating and adding the triangle to the scene
	const triangleMaterial = new THREE.MeshBasicMaterial({color: 0x2685AA, side: THREE.DoubleSide});
	const triangleGeometry = exampleTriangle();
	const triangleMesh = new THREE.Mesh(triangleGeometry, triangleMaterial);
	window.scene.add(triangleMesh);
	// creating and adding your square to the scene !
	const square_material = new THREE.MeshBasicMaterial({color: 0xF6831E, side: THREE.DoubleSide});
	const square_geometry = drawSquare(3, 5, 7, 9);
	const square_mesh = new THREE.Mesh(square_geometry, square_material);
	window.scene.add(square_mesh);
	addToDOM();
	render();
} catch(e) {
	const errorReport = "Your program encountered an unrecoverable error, can not draw on canvas. Error was:<br/><br/>";
	$('#webGL').append(errorReport+e);
}
