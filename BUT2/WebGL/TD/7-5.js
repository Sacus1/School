"use strict"; // good practice - see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Strict_mode
/*global THREE, Coordinates, $, document, window, dat*/
import * as THREE from "three";
import {OBJLoader} from "three/addons/loaders/OBJLoader.js";
import {OrbitControls} from "three/addons/controls/OrbitControls.js";
import {dat} from "/lib/dat.gui.min.js";
import {Coordinates} from "../lib/Coordinates.js";


let camera, renderer;
let cameraControls;
const clock = new THREE.Clock();
const teapotSize = 50;

import { TeapotGeometry } from './TeapotGeometry.js';

function createMaterial() {
    // MATERIALS
    // Student: use the texture 'textures/water.jpg'
    const material = new THREE.MeshPhongMaterial({shininess: 50});
    material.color.setHSL( 0.09, 0.46, 0.2 );
    material.specular.setHSL( 0.09, 0.46, 1.0 );
    material.specularMap = new THREE.TextureLoader().load('textures/cracked_n.png');
    material.normalMap = new THREE.TextureLoader().load('textures/cracked_n.png');
    return material;
}

function fillScene() {
    window.scene = new THREE.Scene();
    // LIGHTS
    window.scene.add( new THREE.AmbientLight( 0x333333 ) );
    let light = new THREE.DirectionalLight(0xFFFFFF, 0.9);
    light.position.set( 200, 300, 500 );
    window.scene.add( light );
    light = new THREE.DirectionalLight( 0xFFFFFF, 0.7 );
    light.position.set( -200, -100, -400 );
    window.scene.add( light );

    const material = createMaterial();
    const teapot = new THREE.Mesh(
        new TeapotGeometry(teapotSize,10,true,true,true,false,true),
        material);
    window.scene.add( teapot );
}

function init() {
    const canvasWidth = 846;
    const canvasHeight = 494;
    // For grading the window is fixed in size; here's general code:
    //var canvasWidth = window.innerWidth;
    //var canvasHeight = window.innerHeight;

    // CAMERA

    camera = new THREE.PerspectiveCamera( 45, canvasWidth/ canvasHeight, 100, 20000 );
    camera.position.set( -222, 494, 1746 );

    // RENDERER

    renderer = new THREE.WebGLRenderer( { antialias: true } );
    renderer.setSize( canvasWidth, canvasHeight );
    renderer.setClearColor( 0xAAAAAA, 1.0 );
    renderer.gammaInput = true;
    renderer.gammaOutput = true;

    // CONTROLS
    cameraControls = new OrbitControls( camera, renderer.domElement );
    cameraControls.target.set(0, -160, 0);

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

    requestAnimationFrame( animate );
    render();

}

function render() {
    const delta = clock.getDelta();
    cameraControls.update( delta );

    renderer.render( window.scene, camera );
}

try {
    init();
    fillScene();
    addToDOM();
    animate();
} catch (e) {
    const errorReport = "Your program encountered an unrecoverable error, can not draw on canvas. Error was:<br/><br/>";
    $('#webGL').append(errorReport + e.stack);
}
