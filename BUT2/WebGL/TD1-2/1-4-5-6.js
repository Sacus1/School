"use strict"; // good practice - see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Strict_mode
////////////////////////////////////////////////////////////////////////////////
// Polygon Creation Exercise
// Your task is to complete the function PolygonGeometry(sides)
// which takes 1 argument:
//   sides - how many edges the polygon has.
// Return the mesh that defines the minimum number of triangles necessary
// to draw the polygon.
// Radius of the polygon is 1. Center of the polygon is at 0, 0.
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
function PolygonGeometry(sides) {
	let geo = new THREE.BufferGeometry();

	// generate vertices
	let vertices= [];
	for ( let pt = 0 ; pt < sides; pt++ )
	{
		// Add 90 degrees so we start at +Y axis, rotate counterclockwise around
		let angle = (Math.PI/2) + (pt / sides) * 2 * Math.PI;

		let x = Math.cos( angle );
		let y = Math.sin( angle );


		//Save the vertex location - fill in the code
		// Use something like vertices.push( new THREE.Vector3( something  ) ); to add a vertex
        vertices.push(new THREE.Vector3(x,y,0));
	}
	// Write the code to generate minimum number of faces for the polygon.
	let faces = [];
    for ( let pt = 1 ; pt < vertices.length-1; pt++ )
    {
        faces.push(vertices[0].x,vertices[0].y,vertices[0].z);
        faces.push(vertices[pt].x,vertices[pt].y,vertices[pt].z);
        faces.push(vertices[pt+1].x,vertices[pt+1].y,vertices[pt+1].z);
    }
	const fv=new Float32Array(faces);
	geo.setAttribute( 'position', new THREE.BufferAttribute( fv, 3 ) );
	// Return the geometry object
	return geo;
}
function PolygonGeometryL(sides,location){
	let geo = new THREE.BufferGeometry();

	// generate vertices
	let vertices= [];
	for ( let pt = 0 ; pt < sides; pt++ )
	{
		// Add 90 degrees so we start at +Y axis, rotate counterclockwise around
		let angle = (Math.PI/2) + (pt / sides) * 2 * Math.PI;

		let x = Math.cos( angle );
		let y = Math.sin( angle );


		//Save the vertex location - fill in the code
		// Use something like vertices.push( new THREE.Vector3( something  ) ); to add a vertex
        vertices.push(new THREE.Vector3(x+location.x,y+location.y,0));
	}
	// Write the code to generate minimum number of faces for the polygon.
	let faces = [];
    for ( let pt = 1 ; pt < vertices.length-1; pt++ )
    {
        faces.push(vertices[0].x,vertices[0].y,vertices[0].z);
        faces.push(vertices[pt].x,vertices[pt].y,vertices[pt].z);
        faces.push(vertices[pt+1].x,vertices[pt+1].y,vertices[pt+1].z);
    }
	const fv=new Float32Array(faces);
	geo.setAttribute( 'position', new THREE.BufferAttribute( fv, 3 ) );
	// Return the geometry object
	return geo;
}
function PolygonGeometryR(sides,location,radius){
	let geo = new THREE.BufferGeometry();

	// generate vertices
	let vertices= [];
	for ( let pt = 0 ; pt < sides; pt++ )
	{
		// Add 90 degrees so we start at +Y axis, rotate counterclockwise around
		let angle = (Math.PI/2) + (pt / sides) * 2 * Math.PI;

		let x = Math.cos( angle );
		let y = Math.sin( angle );

		//Save the vertex location - fill in the code
		// Use something like vertices.push( new THREE.Vector3( something  ) ); to add a vertex
		vertices.push(new THREE.Vector3(x*radius+location.x,y*radius+location.y,0));
	}
	// Write the code to generate minimum number of faces for the polygon.
	let faces = [];
	for ( let pt = 1 ; pt < vertices.length-1; pt++ )
	{
		faces.push(vertices[0].x,vertices[0].y,vertices[0].z);
		faces.push(vertices[pt].x,vertices[pt].y,vertices[pt].z);
		faces.push(vertices[pt+1].x,vertices[pt+1].y,vertices[pt+1].z);
	}
	const fv=new Float32Array(faces);
	geo.setAttribute( 'position', new THREE.BufferAttribute( fv, 3 ) );
	// Return the geometry object
	return geo;
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
	windowScale = 4;
	let windowWidth = windowScale * canvasRatio;
	let windowHeight = windowScale;

	camera = new THREE.OrthographicCamera( windowWidth / - 2, windowWidth / 2, windowHeight / 2, windowHeight / - 2, 0, 40 );

	let focus = new THREE.Vector3( 0,1,0 );
	camera.position.x = focus.x;
	camera.position.y = focus.y;
	camera.position.z = 10;
	camera.lookAt(focus);

	renderer = new THREE.WebGLRenderer({ antialias: false, preserveDrawingBuffer: true});
	renderer.gammaInput = true;
	renderer.gammaOutput = true;
	renderer.setSize( canvasWidth, canvasHeight );
	renderer.setClearColor( 0xFFFFFF, 1.0 );

}
function showGrids() {
	// Background grid and axes. Grid step size is 1, axes cross at 0, 0
	Coordinates.drawGrid({size:100,scale:1,orientation:"z"});
	Coordinates.drawAxes({axisLength:4,axisOrientation:"x",axisRadius:0.02});
	Coordinates.drawAxes({axisLength:3,axisOrientation:"y",axisRadius:0.02});
}
function addToDOM() {
	let container = document.getElementById('webGL');
	let canvas = container.getElementsByTagName('canvas');
	if (canvas.length>0) {
		container.removeChild(canvas[0]);
	}
	container.appendChild( renderer.domElement );
}
function render() {
	renderer.render( window.scene, camera );
}

// Main body of the script


try {
	init();
	showGrids();
	let geo = PolygonGeometryR(100,new THREE.Vector3(1.5,1.5,0),1.5);
	let material = new THREE.MeshBasicMaterial( { color: 0xff0000, side: THREE.FrontSide } );
	material.wireframe = false;
	let mesh = new THREE.Mesh( geo, material );
	window.scene.add( mesh );
	addToDOM();
	render();
} catch(e) {
	let errorReport = "Your program encountered an unrecoverable error, can not draw on canvas. Error was:<br/><br/>";
	$('#webGL').append(errorReport+e.stack);
}