"use strict"; // good practice - see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Strict_mode
////////////////////////////////////////////////////////////////////////////////
// Clock hand rotation: rotate the hand into the proper orientation
////////////////////////////////////////////////////////////////////////////////
/*global THREE, Coordinates, document, window, dat, $*/
import * as THREE from "three";
import { OBJLoader } from "three/addons/loaders/OBJLoader.js";
import { OrbitControls } from "three/addons/controls/OrbitControls.js";
import {dat} from "/lib/dat.gui.min.js";
window.window.window.scene = new THREE.Scene();
import {Coordinates} from "../lib/Coordinates.js";

let camera, renderer;
let cameraControls, effectController;
const clock = new THREE.Clock();
let gridX = false;
let gridY = false;
let gridZ = false;
let axes = true;
let ground = true;
let arm, forearm, body,handLeft, handRight;

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

    // Robot definitions
    const robotHandLeftMaterial = new THREE.MeshPhongMaterial({color: 0xCC3399, specular: 0xCC3399, shininess: 20});
    const robotHandRightMaterial = new THREE.MeshPhongMaterial({color: 0xDD3388, specular: 0xDD3388, shininess: 20});
    const robotBaseMaterial = new THREE.MeshPhongMaterial({color: 0x6E23BB, specular: 0x6E23BB, shininess: 20});
    const robotForearmMaterial = new THREE.MeshPhongMaterial({color: 0xF4C154, specular: 0xF4C154, shininess: 100});
    const robotUpperArmMaterial = new THREE.MeshPhongMaterial({color: 0x95E4FB, specular: 0x95E4FB, shininess: 100});

    const torus = new THREE.Mesh(
        new THREE.TorusGeometry(22, 15, 32, 32), robotBaseMaterial);
    torus.rotation.x = 90 * Math.PI/180;
    window.scene.add( torus );

    forearm = new THREE.Object3D();
    const faLength = 80;

    createRobotExtender( forearm, faLength, robotForearmMaterial );

    arm = new THREE.Object3D();
    const uaLength = 120;

    createRobotCrane( arm, uaLength, robotUpperArmMaterial );

    // Move the forearm itself to the end of the upper arm.
    forearm.position.y = uaLength;
    arm.add( forearm );

    window.scene.add( arm );

    const handLength = 38;

    handLeft = new THREE.Object3D();
    createRobotGrabber( handLeft, handLength, robotHandLeftMaterial );
    // Move the hand part to the end of the forearm.
    handLeft.position.y = faLength;
    forearm.add( handLeft );

    // YOUR CODE HERE
    // Add the second grabber handRight. Note that it uses a different color, defined above
    // ALSO EDIT render() TO ENABLE CONTROLS FOR GRABBER

    handRight = new THREE.Object3D();
    createRobotGrabber( handRight, handLength, robotHandRightMaterial );
    // Move the hand part to the end of the forearm.
    handRight.position.y = faLength;
    forearm.add( handRight );


}

function createRobotGrabber( part, length, material )
{
    const box = new THREE.Mesh(
        new THREE.BoxGeometry(30, length, 4), material);
    box.position.y = length/2;
    part.add( box );
}

function createRobotExtender( part, length, material )
{
    let cylinder = new THREE.Mesh(
        new THREE.CylinderGeometry(22, 22, 6, 32), material);
    part.add( cylinder );

    let i;
    for ( i = 0; i < 4; i++ )
    {
        const box = new THREE.Mesh(
            new THREE.BoxGeometry(4, length, 4), material);
        box.position.x = (i < 2) ? -8 : 8;
        box.position.y = length/2;
        box.position.z = (i%2) ? -8 : 8;
        part.add( box );
    }

    cylinder = new THREE.Mesh(
        new THREE.CylinderGeometry( 15, 15, 40, 32 ), material );
    cylinder.rotation.x = 90 * Math.PI/180;
    cylinder.position.y = length;
    part.add( cylinder );
}

function createRobotCrane( part, length, material )
{
    const box = new THREE.Mesh(
        new THREE.BoxGeometry(18, length, 18), material);
    box.position.y = length/2;
    part.add( box );

    const sphere = new THREE.Mesh(
        new THREE.SphereGeometry(20, 32, 16), material);
    // place sphere at end of arm
    sphere.position.y = length;
    part.add( sphere );
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
    camera = new THREE.PerspectiveCamera( 38, canvasRatio, 1, 10000 );
    // CONTROLS
    cameraControls = new OrbitControls(camera, renderer.domElement);
    camera.position.set(-49, 242,54);
    cameraControls.target.set(54, 106, 33);
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

    arm.rotation.y = effectController.uy * Math.PI/180;	// yaw
    arm.rotation.z = effectController.uz * Math.PI/180;	// roll

    forearm.rotation.y = effectController.fy * Math.PI/180;	// yaw
    forearm.rotation.z = effectController.fz * Math.PI/180;	// roll

    // ADD handRight yaw AND translate HERE
    handLeft.rotation.z = effectController.hz * Math.PI/180;	// yaw
    handLeft.position.z = effectController.htz;	// translate

    handRight.rotation.z = effectController.hz * Math.PI/180;	// yaw
    handRight.position.z = -effectController.htz;	// translate
    renderer.render(window.scene, camera);
}

function setupGui() {

    effectController = {

        newGridX: gridX,
        newGridY: gridY,
        newGridZ: gridZ,
        newGround: ground,
        newAxes: axes,

        uy: 70.0,
        uz: -15.0,

        fy: 10.0,
        fz: 60.0,

        hz: 30.0,
        htz: 12.0
    };

    const gui = new dat.GUI();
    let h = gui.addFolder("Grid display");
    h.add( effectController, "newGridX").name("Show XZ grid");
    h.add( effectController, "newGridY" ).name("Show YZ grid");
    h.add( effectController, "newGridZ" ).name("Show XY grid");
    h.add( effectController, "newGround" ).name("Show ground");
    h.add( effectController, "newAxes" ).name("Show axes");
    h = gui.addFolder("Arm angles");
    h.add(effectController, "uy", -180.0, 180.0, 0.025).name("Upper arm y");
    h.add(effectController, "uz", -45.0, 45.0, 0.025).name("Upper arm z");
    h.add(effectController, "fy", -180.0, 180.0, 0.025).name("Forearm y");
    h.add(effectController, "fz", -120.0, 120.0, 0.025).name("Forearm z");
    h.add(effectController, "hz", -45.0, 45.0, 0.025).name("Hand z");
    h.add(effectController, "htz", 2.0, 17.0, 0.025).name("Hand spread");
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
