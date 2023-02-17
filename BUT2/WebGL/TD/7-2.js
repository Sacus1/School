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

function fillScene() {
    window.scene = new THREE.Scene();


    // Student: rewrite the following vertex generation code so that
    // vertices are generated every 100 units:
    // -1000,-1000,-1000 to 1000,1000,1000, e.g.
    // at -1000,-1000,-1000, -900,-1000,-1000,
    // and so on, for the 21*21*21 = 9261 points.

    const disk = new THREE.TextureLoader().load('disc.png');
    const material = new THREE.SpriteMaterial({map: disk});
    material.color.setHSL(0.9, 0.2, 0.6)
    for (let x = 0; x < 21; x++) {
        for (let y = 0; y < 21; y++) {
            for (let z = 0; z < 21; z++) {
                const particles = new THREE.Sprite(material);
                // accept the point only if it's in the sphere
                particles.scale.set(35, 35, 35);
                particles.position.x = x*100;
                particles.position.y = y*100;
                particles.position.z = z*100;
                window.scene.add(particles);
            }
        }
    }
}

function init() {
    const canvasWidth = 846;
    const canvasHeight = 494;
    // For grading the window is fixed in size; here's general code:
    //var canvasWidth = window.innerWidth;
    //var canvasHeight = window.innerHeight;
    const canvasRatio = canvasWidth / canvasHeight;

    // RENDERER
    renderer = new THREE.WebGLRenderer({antialias: true});
    renderer = new THREE.WebGLRenderer({clearAlpha: 1});
    renderer.setSize(canvasWidth, canvasHeight);
    renderer.setClearColor(0xAAAAAA, 1.0);

    // CAMERA
    camera = new THREE.PerspectiveCamera(55, canvasRatio, 2, 8000);
    camera.position.set(10, 5, 15);
    // CONTROLS
    cameraControls = new OrbitControls(camera, renderer.domElement);
    cameraControls.target.set(0, 0, 0);

}

function addToDOM() {
    const container = document.getElementById('webGL');
    const canvas = container.getElementsByTagName('canvas');
    if (canvas.length > 0) {
        container.removeChild(canvas[0]);
    }
    container.appendChild(renderer.domElement);
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
    addToDOM();
    animate();
} catch (e) {
    const errorReport = "Your program encountered an unrecoverable error, can not draw on canvas. Error was:<br/><br/>";
    $('#webGL').append(errorReport + e.stack);
}