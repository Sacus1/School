"use strict"; // good practice - see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Strict_mode
////////////////////////////////////////////////////////////////////////////////
// Drinking Bird Model exercise                                               //
// Your task is to complete the model for the drinking bird                   //
// Please work from the formal blueprint dimensions and positions shown at    //
// https://www.udacity.com/wiki/cs291/notes                                   //
//                                                                            //
// The following materials should be used:                                    //
// Hat and spine: cylinderMaterial (blue)                                     //
// Head and bottom body: sphereMaterial (red)                                 //
// Rest of body: cubeMaterial (orange)                                        //
//                                                                            //
// So that the exercise passes, and the spheres and cylinders look good,      //
// all SphereGeometry calls should be of the form:                            //
//     SphereGeometry( radius, 32, 16 );                                      //
// and CylinderGeometry calls should be of the form:                          //
//     CylinderGeometry( radiusTop, radiusBottom, height, 32 );               //
////////////////////////////////////////////////////////////////////////////////
/*global THREE, Coordinates, $, document, window, dat*/
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
let axes = false;
let ground = true;

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
	camera = new THREE.PerspectiveCamera( 45, canvasRatio, 1, 40000 );
	// CONTROLS
	cameraControls = new OrbitControls(camera, renderer.domElement);

	camera.position.set( -480, 659, -619 );
	cameraControls.target.set(4,301,92);

	fillScene();
}

// Supporting frame for the bird - base + legs + feet
function createSupport() {

	const legMaterial = new THREE.MeshPhongMaterial({color: 0xF07020, specular: 4});
	legMaterial.specular.setRGB( 0.5, 0.5, 0.5 );
	const footMaterial = new THREE.MeshPhongMaterial({color: 0xFF0000, specular: 30});
	footMaterial.specular.setRGB( 0.5, 0.5, 0.5 );
	// base
	let cube;
	cube = new THREE.Mesh(
		new THREE.BoxGeometry( 20+64+110, 4, 2*77 ), legMaterial );
	cube.position.x = -45;	// (20+32) - half of width (20+64+110)/2
	cube.position.y = 4/2;	// half of height
	cube.position.z = 0;	// centered at origin
	window.scene.add( cube );

	// left foot
	cube = new THREE.Mesh(
		new THREE.BoxGeometry( 20+64+110, 52, 6 ), footMaterial );
	cube.position.x = -45;	// (20+32) - half of width (20+64+110)/2
	cube.position.y = 52/2;	// half of height
	cube.position.z = 77 + 6/2;	// offset 77 + half of depth 6/2
	window.scene.add( cube );

	// left leg
	cube = new THREE.Mesh(
		new THREE.BoxGeometry( 64, 334+52, 6 ), legMaterial );
	cube.position.x = 0;	// centered on origin along X
	cube.position.y = (334+52)/2;
	cube.position.z = 77 + 6/2;	// offset 77 + half of depth 6/2
	window.scene.add( cube );
	// right foot
    cube = new THREE.Mesh(
        new THREE.BoxGeometry( 20+64+110, 52, 6 ), footMaterial );
    cube.position.x = -45;	// (20+32) - half of width (20+64+110)/2
    cube.position.y = 52/2;	// half of height
    cube.position.z = -77 - 6/2;	// offset 77 + half of depth 6/2
    window.scene.add( cube );
	// right leg
    cube = new THREE.Mesh(
        new THREE.BoxGeometry( 64, 334+52, 6 ), legMaterial );
    cube.position.x = 0;	// centered on origin along X
    cube.position.y = (334+52)/2;
    cube.position.z = -77 - 6/2;	// offset 77 + half of depth 6/2
    window.scene.add( cube );
}

// Body of the bird - body and the connector of body and head
function createBody() {
	const material = new THREE.MeshPhongMaterial({shininess: 100, color: 0xffffff});
	material.specular.setRGB( 0.5, 0.5, 0.5 );
	material.transparent = true;
	material.opacity = 0.3;
    // body
	let sphere;
	sphere = new THREE.Mesh(
        new THREE.SphereGeometry( 116/2 ), material );
    sphere.position.x = 0;	// centered on origin along X
    sphere.position.y = 160;	// offset 160 along Y
    sphere.position.z = 0;	// centered on origin along Z
    window.scene.add( sphere );
	let cylinder;
	cylinder = new THREE.Mesh(
        new THREE.CylinderGeometry( 24/2,24/2,390), material );
    cylinder.position.x = 0;	// centered on origin along X
    cylinder.position.y = 334+52
    cylinder.position.z = 0;	// centered on origin along Z
    window.scene.add( cylinder );
	const bodyMaterial = new THREE.MeshPhongMaterial({shininess: 100});
	bodyMaterial.color.setRGB( 31/255, 86/255, 169/255 );
	bodyMaterial.specular.setRGB( 0.5, 0.5, 0.5 );
	const liquid = new THREE.Mesh(
		new THREE.SphereGeometry(104 / 2, 32, 16, 0, Math.PI * 2, Math.PI / 2, Math.PI),
		bodyMaterial);
	liquid.position.x = 0;	// centered on origin along X
	liquid.position.y = 160;	// offset 160 along Y
	liquid.position.z = 0;	// centered on origin along Z
	window.scene.add( liquid );

}

// Head of the bird - head + hat
function createHead() {
	const sphereMaterial = new THREE.MeshLambertMaterial({color: 0xA00000});
	const cylinderMaterial = new THREE.MeshPhongMaterial({shininess: 100, color: 0x0000D0});
	cylinderMaterial.specular.setRGB( 0.5, 0.5, 0.5 );
	const head = new THREE.Mesh(
		new THREE.SphereGeometry(116 / 2), sphereMaterial);
	head.position.x = 0;	// centered on origin along X
    head.position.y =390+160;
    head.position.z = 0;	// centered on origin along Z
    window.scene.add( head );
	const hatBase = new THREE.Mesh(
		new THREE.CylinderGeometry(142 / 2, 142 / 2, 10), cylinderMaterial);
	hatBase.position.x = 0;	// centered on origin along X
    hatBase.position.y = 390+160+40;
    hatBase.position.z = 0;	// centered on origin along Z
    window.scene.add( hatBase );
	const hatTop = new THREE.Mesh(
		new THREE.CylinderGeometry(80 / 2, 80 / 2, 70), cylinderMaterial);
	hatTop.position.x = 0;	// centered on origin along X
    hatTop.position.y = 390+160+40+10;
    hatTop.position.z = 0;	// centered on origin along Z
    window.scene.add( hatTop );
	// eye relative to head
	const eyeMaterial = new THREE.MeshLambertMaterial({color: 0x000000});
	let eye = new THREE.Mesh(
		new THREE.SphereGeometry( 20/2 ), eyeMaterial );
	eye.position.x = -116/2;
	eye.position.y = 0;
	eye.position.z = 20;
	head.add( eye );
	eye = new THREE.Mesh(
		new THREE.SphereGeometry( 20/2 ), eyeMaterial );
	eye.position.x = -116/2;
	eye.position.y = 0;
	eye.position.z = -20;
	head.add( eye );
	// beak relative to head
	const beakMaterial = new THREE.MeshLambertMaterial({color: 0xFFD700});
	const beakHolder = new THREE.Object3D();
	beakHolder.position.x = -116/2 - 20/2;
	beakHolder.position.y = -20;
	const beak = new THREE.Mesh(
		new THREE.CylinderGeometry( 5, 20/2, 116/2 ), beakMaterial );
	beak.rotation.z = Math.PI / 2;
	beakHolder.add( beak );
	head.add( beakHolder );
}

function createDrinkingBird() {

	// MODELS
	// base + legs + feet
	createSupport();

	// body + body/head connector
	createBody();

	// head + hat
	createHead();
}

function fillScene() {
	// SCENE
	window.scene = new THREE.Scene();
	window.scene.fog = new THREE.Fog( 0x808080, 3000, 6000 );
	// LIGHTS
	const ambientLight = new THREE.AmbientLight(0x222222);
	const light = new THREE.DirectionalLight(0xFFFFFF, 1.0);
	light.position.set( 200, 400, 500 );

	window.scene.add(ambientLight);
	window.scene.add(light);

	if (ground) {
		Coordinates.drawGround({size:1000});
	}
	if (gridX) {
		Coordinates.drawGrid({size:1000,scale:0.01});
	}
	if (gridY) {
		Coordinates.drawGrid({size:1000,scale:0.01, orientation:"y"});
	}
	if (gridZ) {
		Coordinates.drawGrid({size:1000,scale:0.01, orientation:"z"});
	}
	if (axes) {
		Coordinates.drawAllAxes({axisLength:300,axisRadius:2,axisTess:50});
	}
	createDrinkingBird();
}
//
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
	gui.add(effectController, "newGridX").name("Show XZ grid");
	gui.add( effectController, "newGridY" ).name("Show YZ grid");
	gui.add( effectController, "newGridZ" ).name("Show XY grid");
	gui.add( effectController, "newGround" ).name("Show ground");
	gui.add( effectController, "newAxes" ).name("Show axes");
}

try {
	init();
	setupGui();
	addToDOM();
	animate();
} catch(e) {
	const errorReport = "Your program encountered an unrecoverable error, can not draw on canvas. Error was:<br/><br/>";
	$('#webGL').append(errorReport+e.stack);
}