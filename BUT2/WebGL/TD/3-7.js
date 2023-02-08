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
let gridX = true;
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

    // FLOWER
    const petalMaterial = new THREE.MeshLambertMaterial({color: 0xCC5920});
    const flowerHeight = 200;
    const petalLength = 120;
    const cylGeom = new THREE.CylinderGeometry(15, 0, petalLength, 32);
    const flower = new THREE.Object3D();

    /////////
    // YOUR CODE HERE
    // add code here to make 24 petals, radiating around the sphere
    // Just rotates and positions on the cylinder and petals are needed.
    // Rest of the flower
    const stamenMaterial = new THREE.MeshLambertMaterial({color: 0x333310});
    const stamen = new THREE.Mesh(
        new THREE.SphereGeometry(20, 32, 16), stamenMaterial);
    stamen.position.y = flowerHeight;	// move to flower center
    flower.add( stamen );
    for (let i = 0; i < 24; i++) {
        const cylinder = new THREE.Mesh(cylGeom, petalMaterial);
        const petal = new THREE.Object3D();
        petal.add(cylinder);
        stamen.add(petal);
        petal.rotation.z = Math.PI/2;
        petal.rotation.y = i * Math.PI/12;
        cylinder.position.y = petalLength/2;
        flower.add(stamen);
    }
    const stemMaterial = new THREE.MeshLambertMaterial({color: 0x339424});
    const stem = new THREE.Mesh(
        new THREE.CylinderGeometry(10, 10, flowerHeight, 32), stemMaterial);
    stem.position.y = flowerHeight/2;	// move from ground to stamen
    flower.add( stem );

    window.scene.add( flower );

}


function init() {
    const canvasWidth = 846;
    const canvasHeight = 494;
    // For grading the window is fixed in size; here's general code:
    //var canvasWidth = window.innerWidth;
    //var canvasHeight = window.innerHeight;
    const canvasRatio = canvasWidth / canvasHeight;

    // RENDERER
    renderer = new THREE.WebGLRenderer( { antialias: false } );
    renderer.setSize(canvasWidth, canvasHeight);
    renderer.setClearColor( 0xAAAAAA, 1.0 );

    // CAMERA
    camera = new THREE.PerspectiveCamera( 38, canvasRatio, 1, 10000 );
    // CONTROLS
    cameraControls = new OrbitControls(camera, renderer.domElement);
    camera.position.set(-200, 400, 20);
    cameraControls.target.set(0,150,0);
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
    const h = gui.addFolder("Grid display");
    h.add( effectController, "newGridX").name("Show XZ grid");
    h.add( effectController, "newGridY" ).name("Show YZ grid");
    h.add( effectController, "newGridZ" ).name("Show XY grid");
    h.add( effectController, "newGround" ).name("Show ground");
    h.add( effectController, "newAxes" ).name("Show axes");

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
