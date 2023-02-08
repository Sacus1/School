"use strict"; // good practice - see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Strict_mode
////////////////////////////////////////////////////////////////////////////////
// Clock hand rotation: rotate the hand into the proper orientation
////////////////////////////////////////////////////////////////////////////////
/*global THREE, Coordinates, document, window, dat, $*/
import * as THREE from "three";
import { OBJLoader } from "three/addons/loaders/OBJLoader.js";
import { OrbitControls } from "three/addons/controls/OrbitControls.js";
import {dat} from "/lib/dat.gui.min.js";
window.window.scene = new THREE.Scene();
import {Coordinates} from "../lib/Coordinates.js";

let camera, renderer;
let cameraControls, effectController;
const clock = new THREE.Clock();
let gridX = false;
let gridY = false;
let gridZ = false;
let axes = true;
let ground = true;
let arm, forearm, body;

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

    // Robot definitions
    const robotBaseMaterial = new THREE.MeshPhongMaterial({color: 0x6E23BB, specular: 0x6E23BB, shininess: 20});
    const robotForearmMaterial = new THREE.MeshPhongMaterial({color: 0xF4C154, specular: 0xF4C154, shininess: 100});
    const robotUpperArmMaterial = new THREE.MeshPhongMaterial({color: 0x95E4FB, specular: 0x95E4FB, shininess: 100});
    const robotBodyMaterial = new THREE.MeshPhongMaterial({color: 0x279933, specular: 0x279933, shininess: 100});

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
    // YOUR CODE HERE
    body = new THREE.Object3D();
    const bodyLength = 60;
    // Add robot body here, put arm at top.
    // Note that "body" is already declared at top of this code.
    // Here's the call to create the body itself:
    createRobotBody( body, bodyLength, robotBodyMaterial );
    // ALSO CHECK OUT GUI CONTROLS FOR BODY
    // IN THE FUNCTIONS setupGUI() and render()
    // Note you'll have to add the body to the window.scene to get it to display.
    arm.add(body);
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

function createRobotBody( part, length, material )
{
    let cylinder = new THREE.Mesh(
        new THREE.CylinderGeometry(50, 12, length / 2, 18), material);
    cylinder.position.y = length/4;
    part.add( cylinder );

    cylinder = new THREE.Mesh(
        new THREE.CylinderGeometry( 12, 50, length/2, 18 ), material );
    cylinder.position.y = 3*length/4;
    part.add( cylinder );

    const box = new THREE.Mesh(
        new THREE.BoxGeometry(12, length / 4, 110), material);
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
    camera.position.set( -510, 240, 100 );
    // CONTROLS
    cameraControls = new OrbitControls(camera, renderer.domElement);
    cameraControls.target.set(0,120,0);
    camera.position.set(-102, 177, 20);
    cameraControls.target.set(-13, 60, 2);
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

    // UNCOMMENT FOLLOWING LINES TO ENABLE CONTROLS FOR BODY:

    body.rotation.y = effectController.by * Math.PI/180;	// yaw

    arm.rotation.y = effectController.uy * Math.PI/180;	// yaw
    arm.rotation.z = effectController.uz * Math.PI/180;	// roll

    forearm.rotation.y = effectController.fy * Math.PI/180;	// yaw
    forearm.rotation.z = effectController.fz * Math.PI/180;	// roll

    renderer.render(window.scene, camera);
}

function setupGui() {

    effectController = {

        newGridX: gridX,
        newGridY: gridY,
        newGridZ: gridZ,
        newGround: ground,
        newAxes: axes,

        // UNCOMMENT FOLLOWING LINE TO SET DEFAULT VALUE OF CONTROLS FOR BODY:
        by: 0.0,

        uy: 70.0,
        uz: -15.0,

        fy: 10.0,
        fz: 60.0
    };

    const gui = new dat.GUI();
    let h = gui.addFolder("Grid display");
    h.add( effectController, "newGridX").name("Show XZ grid");
    h.add( effectController, "newGridY" ).name("Show YZ grid");
    h.add( effectController, "newGridZ" ).name("Show XY grid");
    h.add( effectController, "newGround" ).name("Show ground");
    h.add( effectController, "newAxes" ).name("Show axes");
    h = gui.addFolder("Arm angles");
    // student, uncomment: h.add(effectController, "by", -180.0, 180.0, 0.025).name("Body y");
    h.add(effectController, "uy", -180.0, 180.0, 0.025).name("Upper arm y");
    h.add(effectController, "uz", -45.0, 45.0, 0.025).name("Upper arm z");
    h.add(effectController, "fy", -180.0, 180.0, 0.025).name("Forearm y");
    h.add(effectController, "fz", -120.0, 120.0, 0.025).name("Forearm z");
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
