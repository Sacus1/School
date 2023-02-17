"use strict"; // good practice - see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Strict_mode
/*global THREE, Coordinates, $, document, window, dat*/
import * as THREE from "three";
import {OBJLoader} from "three/addons/loaders/OBJLoader.js";
import {OrbitControls} from "three/addons/controls/OrbitControls.js";
import {dat} from "/lib/dat.gui.min.js";
import {Coordinates} from "../lib/Coordinates.js";

let camera, renderer;
let cameraControls, effectController;

const clock = new THREE.Clock();

function SquareGeometry() {

    const triangle = new THREE.BufferGeometry();

    // generate vertices
    const vertices = new Float32Array( [
        0.0, 0.0,  0.0,
        1.0, 0.0, 0.0,
        1.0, 1.0,  0.0,

        0.0, 0.0,  0.0,
        1.0, 1.0,  0.0,
        0.0, 1.0,  0.0,
    ] );
    let x = Math.round(Math.random()*3);
    let y =  Math.round(Math.random()*3);
    // Change this array to select the correct part of the texture
    const uv = new Float32Array( [
        .25*x, 0.25*y,
        0.25*x+0.25, 0.25*y,
        0.25*x+0.25, 0.25*y+0.25,

        0.25*x, 0.25*y,
        0.25*x+0.25, 0.25*y+0.25,
        0.25*x, 0.25*y+0.25,
    ] );

    triangle.setAttribute( 'position', new THREE.BufferAttribute( vertices, 3 ) );
    triangle.setAttribute( 'uv', new THREE.BufferAttribute( uv, 2 ) );

    // done: return it.
    return triangle;
}

function fillScene() {
    window.scene = new THREE.Scene();

    const myPolygon = new SquareGeometry();
    const myTexture = new THREE.TextureLoader().load('textures/lettergrid.png');
    const myPolygonMaterial = new THREE.MeshBasicMaterial({map: myTexture});
    const polygonObject = new THREE.Mesh(myPolygon, myPolygonMaterial);
    window.scene.add(polygonObject);
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
    renderer.setClearColor( 0xFFFFFF, 1.0 );

    // Camera: Y up, X right, Z up
    camera = new THREE.PerspectiveCamera( 1, canvasRatio, 50, 150 );
    camera.position.set( 0.5, 0.5, 100 );

    // CONTROLS
    cameraControls = new OrbitControls(camera, renderer.domElement);
    cameraControls.target.set(0.5,0.5,0);

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
    // Background grid and axes. Grid step size is 1, axes cross at 0, 0
    Coordinates.drawGrid({size:100,scale:1,orientation:"z",offset:-0.01});
    Coordinates.drawAxes({axisLength:2.1,axisOrientation:"x",axisRadius:0.004,offset:-0.01});
    Coordinates.drawAxes({axisLength:2.1,axisOrientation:"y",axisRadius:0.004,offset:-0.01});
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

function setupGui() {

    effectController = {

        alpha: 0.7,
        sred:   0xE5/255,
        sgreen: 0x33/255,
        sblue:  0x19/155,

        dred:   0xE5/255,
        dgreen: 0xE5/255,
        dblue:  0x66/255
    };

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