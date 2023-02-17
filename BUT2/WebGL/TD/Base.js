"use strict"; // good practice - see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Strict_mode
/*global THREE, Coordinates, $, document, window, dat*/
import * as THREE from "three";
import { OBJLoader } from "three/addons/loaders/OBJLoader.js";
import { OrbitControls } from "three/addons/controls/OrbitControls.js";
import {dat} from "/lib/dat.gui.min.js";
import {Coordinates} from "../lib/Coordinates.js";

let camera, renderer;
let cameraControls;

const clock = new THREE.Clock();

function fillScene() {
    window.scene = new THREE.Scene();
    window.scene.fog = new THREE.Fog( 0xAAAAAA, 2000, 4000 );

    // LIGHTS
    window.scene.add( new THREE.AmbientLight( 0x222222 ) );
    let light = new THREE.DirectionalLight( 0xFFFFFF, 1.5 );
    light.position.set( -200, 200, -400 );
    window.scene.add( light );
}

function init() {
    const canvasWidth = 846;
    const canvasHeight = 494;

    // RENDERER
    renderer = new THREE.WebGLRenderer( { antialias: true } );
    renderer.setSize(canvasWidth, canvasHeight);
    renderer.setClearColor( 0xAAAAAA, 1.0 );

    // CAMERA
    camera = new THREE.PerspectiveCamera( 35, canvasWidth/ canvasHeight, 1, 4000 );
    camera.position.set( 100,0,0);

    // CONTROLS
    cameraControls = new OrbitControls(camera, renderer.domElement);
    cameraControls.target.set(0,0,0);

}

function drawHelpers() {
    Coordinates.drawGround({size:10000});
    Coordinates.drawGrid({size:10000,scale:0.01});
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

    renderer.render(window.scene, camera);
}
try {
    init();
    fillScene();
    drawHelpers();
    addToDOM();
    animate();
} catch(e) {
    const errorReport = "Your program encountered an unrecoverable error, can not draw on canvas. Error was:<br/><br/>";
    $('#webGL').append(errorReport+e.stack);
}