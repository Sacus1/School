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
let bCube = true;
let gridX = false;
let gridY = false;
let gridZ = false;
let axes = true;
let ground = false;

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

    const cylinderMaterial = new THREE.MeshPhongMaterial({color: 0xD1F5FD, specular: 0xD1F5FD, shininess: 100});

    // get two diagonally-opposite corners of the cube and compute the
    // cylinder axis direction and length
    const maxCorner = new THREE.Vector3(1, 1, 1);
    const minCorner = new THREE.Vector3(-1, -1, -1);
    // note how you can chain one operation on to another:
    const cylAxis = new THREE.Vector3().subVectors(maxCorner, minCorner);
    const cylLength = cylAxis.length();

    // take dot product of cylAxis and up vector to get cosine of angle
    cylAxis.normalize();
    const theta = Math.acos(cylAxis.dot(new THREE.Vector3(0, 1, 0)));
    // or just simply theta = Math.acos( cylAxis.y );

    // YOUR CODE HERE
    const cylinder = new THREE.Mesh(
        new THREE.CylinderGeometry(0.2, 0.2, cylLength, 32), cylinderMaterial);
    const rotationAxis = new THREE.Vector3(1, 0, -1);
    // makeRotationAxis wants its axis normalized
    rotationAxis.normalize();
    // don't use position, rotation, scale
    cylinder.matrixAutoUpdate = false;
    cylinder.matrix.makeRotationAxis( rotationAxis, theta );
    window.scene.add( cylinder );

    const cylinder2 = new THREE.Mesh(
        new THREE.CylinderGeometry(0.2, 0.2, cylLength, 32), cylinderMaterial);
    const rotationAxis2 = new THREE.Vector3(1, 0, 1);
    // makeRotationAxis wants its axis normalized
    rotationAxis2.normalize();
    // don't use position, rotation, scale
    cylinder2.matrixAutoUpdate = false;
    cylinder2.matrix.makeRotationAxis( rotationAxis2, theta );
    window.scene.add( cylinder2 );

    const cylinder3 = new THREE.Mesh(
        new THREE.CylinderGeometry(0.2, 0.2, cylLength, 32), cylinderMaterial);
    const rotationAxis3 = new THREE.Vector3(-1, 0, 1);
    // makeRotationAxis wants its axis normalized
    rotationAxis3.normalize();
    // don't use position, rotation, scale
    cylinder3.matrixAutoUpdate = false;
    cylinder3.matrix.makeRotationAxis( rotationAxis3, theta );
    window.scene.add( cylinder3 );
    const cylinder4 = new THREE.Mesh(
        new THREE.CylinderGeometry(0.2, 0.2, cylLength, 32), cylinderMaterial);
    const rotationAxis4 = new THREE.Vector3(-1, 0, -1);
    // makeRotationAxis wants its axis normalized
    rotationAxis4.normalize();
    // don't use position, rotation, scale
    cylinder4.matrixAutoUpdate = false;
    cylinder4.matrix.makeRotationAxis( rotationAxis4, theta );
    window.scene.add( cylinder4 );
}

function drawHelpers() {
    if (ground) {
        Coordinates.drawGround({size:100});
    }
    if (gridX) {
        Coordinates.drawGrid({size:100,scale:1});
    }
    if (gridY) {
        Coordinates.drawGrid({size:100,scale:1, orientation:"y"});
    }
    if (gridZ) {
        Coordinates.drawGrid({size:100,scale:1, orientation:"z"});
    }
    if (axes) {
        Coordinates.drawAllAxes({axisLength:5,axisRadius:0.01,axisTess:50});
    }

    if (bCube) {
        const cubeMaterial = new THREE.MeshLambertMaterial(
            {color: 0xFFFFFF, opacity: 0.7, transparent: true});
        const cube = new THREE.Mesh(
            new THREE.BoxGeometry(2, 2, 2), cubeMaterial);
        window.scene.add( cube );
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
    renderer = new THREE.WebGLRenderer( { antialias: true } );
    renderer.gammaInput = true;
    renderer.gammaOutput = true;
    renderer.setSize(canvasWidth, canvasHeight);
    renderer.setClearColor( 0xAAAAAA, 1.0 );


    // CAMERA
    camera = new THREE.PerspectiveCamera( 30, canvasRatio, 1, 10000 );
    // CONTROLS
    cameraControls = new OrbitControls(camera, renderer.domElement);
    camera.position.set( -7, 7, 2 );
    cameraControls.target.set(0,0,0);

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

    if ( effectController.newCube !== bCube || effectController.newGridX !== gridX || effectController.newGridY !== gridY || effectController.newGridZ !== gridZ || effectController.newGround !== ground || effectController.newAxes !== axes)
    {
        bCube = effectController.newCube;
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

        newCube: bCube,
        newGridX: gridX,
        newGridY: gridY,
        newGridZ: gridZ,
        newGround: ground,
        newAxes: axes
    };

    const gui = new dat.GUI();
    gui.add( effectController, "newCube").name("Show cube");
    gui.add( effectController, "newGridX").name("Show XZ grid");
    gui.add( effectController, "newGridY" ).name("Show YZ grid");
    gui.add( effectController, "newGridZ" ).name("Show XY grid");
    gui.add( effectController, "newGround" ).name("Show ground");
    gui.add( effectController, "newAxes" ).name("Show axes");
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
