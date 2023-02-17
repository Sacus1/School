"use strict"; // good practice - see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Strict_mode
////////////////////////////////////////////////////////////////////////////////
// Adding a spotlight
////////////////////////////////////////////////////////////////////////////////
/*global THREE, Coordinates, document, window, $*/
import * as THREE from "three";
import { OBJLoader } from "three/addons/loaders/OBJLoader.js";
import { OrbitControls } from "three/addons/controls/OrbitControls.js";
import {dat} from "/lib/dat.gui.min.js";
import {Coordinates} from "../lib/Coordinates.js";

let camera, renderer;
let cameraControls;

const clock = new THREE.Clock();

let cylinder, sphere, cube;

const bevelRadius = 1.9;	// TODO: 2.0 causes some geometry bug.

let headlight;

function fillScene() {
    window.scene = new THREE.Scene();
    window.scene.fog = new THREE.Fog( 0x0, 2000, 4000 );

    // LIGHTS
    window.scene.add( new THREE.AmbientLight( 0x222222 ) );

    headlight = new THREE.PointLight( 0x606060, 1.0 );
    window.scene.add( headlight );

    ///////////////////////////////////
    // Student: change the following directional light to a spotlight,
    // color full white, intensity 1.5
    // location -400, 1200, 300
    // angle 20 degrees
    // exponent 1
    // target position 0, 200, 0

    const light = new THREE.SpotLight(0xFFFFFF, 1.5);
    light.position.set( -400, 1200, 300 );
    light.angle = 20 * Math.PI / 180;
    light.exponent = 1;
    light.target.position.set( 0, 200, 0 );
    window.scene.add( light );

    const solidGround = new THREE.Mesh(
        new THREE.PlaneGeometry(10000, 10000),
        new THREE.MeshPhongMaterial({
            color: 0xFFFFFF,
            // polygonOffset moves the plane back from the eye a bit, so that the lines on top of
            // the grid do not have z-fighting with the grid:
            // Factor == 1 moves it back relative to the slope (more on-edge means move back farther)
            // Units == 4 is a fixed amount to move back, and 4 is usually a good value
            polygonOffset: true, polygonOffsetFactor: 1.0, polygonOffsetUnits: 4.0
        }));
    solidGround.rotation.x = -Math.PI / 2;

    window.scene.add( solidGround );

    //////////////////////////////
    // Bird
    const bird = new THREE.Object3D();
    createDrinkingBird( bird );
    window.scene.add( bird );
}

// Supporting frame for the bird - base + legs + feet
function createSupport( bsupport ) {
    const legMaterial = new THREE.MeshPhongMaterial({shininess: 4});
    legMaterial.color.setHex( 0xAdA79b );
    legMaterial.specular.setRGB( 0.5, 0.5, 0.5 );

    const footMaterial = new THREE.MeshPhongMaterial({color: 0x960f0b, shininess: 30});
    footMaterial.specular.setRGB( 0.5, 0.5, 0.5 );

    // base
    cube = new THREE.Mesh(
        new THREE.BoxGeometry( 20+64+110, 4, 2*77+12  ), footMaterial );
    cube.position.x = -45;	// (20+32) - half of width (20+64+110)/2
    cube.position.y = 4/2;	// half of height
    cube.position.z = 0;	// centered at origin
    bsupport.add( cube );

    // feet
    cube = new THREE.Mesh(
        new THREE.BoxGeometry( 20+64+110, 52, 6  ), footMaterial );
    cube.position.x = -45;	// (20+32) - half of width (20+64+110)/2
    cube.position.y = 52/2;	// half of height
    cube.position.z = 77 + 6/2;	// offset 77 + half of depth 6/2
    bsupport.add( cube );

    cube = new THREE.Mesh(
        new THREE.BoxGeometry( 20+64+110, 52, 6  ), footMaterial );
    cube.position.x = -45;	// (20+32) - half of width (20+64+110)/2
    cube.position.y = 52/2;	// half of height
    cube.position.z = -(77 + 6/2);	// negative offset 77 + half of depth 6/2
    bsupport.add( cube );

    cube = new THREE.Mesh(
        new THREE.BoxGeometry( 64, 104, 6  ), footMaterial );
    cube.position.x = 0;	// centered on origin along X
    cube.position.y = 104/2;
    cube.position.z = 77 + 6/2;	// negative offset 77 + half of depth 6/2
    bsupport.add( cube );

    cube = new THREE.Mesh(
        new THREE.BoxGeometry( 64, 104, 6  ), footMaterial );
    cube.position.x = 0;	// centered on origin along X
    cube.position.y = 104/2;
    cube.position.z = -(77 + 6/2);	// negative offset 77 + half of depth 6/2
    bsupport.add( cube );

    // legs
    cube = new THREE.Mesh(
        new THREE.BoxGeometry( 60, 282+4, 4  ), legMaterial );
    cube.position.x = 0;	// centered on origin along X
    cube.position.y = 104 + 282/2 - 2;
    cube.position.z = 77 + 6/2;	// negative offset 77 + half of depth 6/2
    bsupport.add( cube );

    cube = new THREE.Mesh(
        new THREE.BoxGeometry( 60, 282+4, 4  ), legMaterial );
    cube.position.x = 0;	// centered on origin along X
    cube.position.y = 104 + 282/2 - 2;
    cube.position.z = -(77 + 6/2);	// negative offset 77 + half of depth 6/2
    bsupport.add( cube );
}

// Body of the bird - body and the connector of body and head
function createBody(bbody) {
    const bodyMaterial = new THREE.MeshPhongMaterial({shininess: 100});
    bodyMaterial.color.setRGB( 31/255, 86/255, 169/255 );
    bodyMaterial.specular.setRGB( 0.5, 0.5, 0.5 );

    const glassMaterial = new THREE.MeshPhongMaterial({
        color: 0x0,
        specular: 0xFFFFFF,
        shininess: 100,
        opacity: 0.3,
        transparent: true
    });

    const crossbarMaterial = new THREE.MeshPhongMaterial({color: 0x808080, specular: 0xFFFFFF, shininess: 400});

    // body
    sphere = new THREE.Mesh(
        new THREE.SphereGeometry( 104/2, 32, 16, 0, Math.PI * 2, Math.PI/2, Math.PI ), bodyMaterial );
    sphere.position.x = 0;
    sphere.position.y = 160;
    sphere.position.z = 0;
    bbody.add( sphere );

    // cap for top of hemisphere
    cylinder = new THREE.Mesh(
        new THREE.CylinderGeometry( 104/2, 104/2, 0, 32 ), bodyMaterial );
    cylinder.position.x = 0;
    cylinder.position.y = 160;
    cylinder.position.z = 0;
    bbody.add( cylinder );

    cylinder = new THREE.Mesh(
        new THREE.CylinderGeometry( 12/2, 12/2, 390 - 100, 32 ), bodyMaterial );
    cylinder.position.x = 0;
    cylinder.position.y = 160 + 390/2 - 100;
    cylinder.position.z = 0;
    bbody.add( cylinder );

    // glass stem
    sphere = new THREE.Mesh(
        new THREE.SphereGeometry( 116/2, 32, 16 ), glassMaterial );
    sphere.position.x = 0;
    sphere.position.y = 160;
    sphere.position.z = 0;
    bbody.add( sphere );

    cylinder = new THREE.Mesh(
        new THREE.CylinderGeometry( 24/2, 24/2, 390, 32 ), glassMaterial );
    cylinder.position.x = 0;
    cylinder.position.y = 160 + 390/2;
    cylinder.position.z = 0;
    bbody.add( cylinder );

    // crossbar
    cylinder = new THREE.Mesh(
        new THREE.CylinderGeometry( 5, 5, 200, 32 ), crossbarMaterial );
    cylinder.position.set( 0, 360, 0 );
    cylinder.rotation.x = 90 * Math.PI / 180.0;
    bbody.add( cylinder );
}

// Head of the bird - head + hat
function createHead(bhead) {
    const headMaterial = new THREE.MeshLambertMaterial();
    headMaterial.color.r = 104/255;
    headMaterial.color.g = 1/255;
    headMaterial.color.b = 5/255;

    const hatMaterial = new THREE.MeshPhongMaterial({shininess: 100});
    hatMaterial.color.r = 24/255;
    hatMaterial.color.g = 38/255;
    hatMaterial.color.b = 77/255;
    hatMaterial.specular.setRGB( 0.5, 0.5, 0.5 );

    const eyeMaterial = new THREE.MeshPhongMaterial({color: 0x000000, specular: 0x303030, shininess: 4});

    // head
    sphere = new THREE.Mesh(
        new THREE.SphereGeometry( 104/2, 32, 16 ), headMaterial );
    sphere.position.x = 0;
    sphere.position.y = 160 + 390;
    sphere.position.z = 0;
    bhead.add( sphere );

    // hat
    cylinder = new THREE.Mesh(
        new THREE.CylinderGeometry( 142/2, 142/2, 10, 32 ), hatMaterial );
    cylinder.position.x = 0;
    cylinder.position.y = 160 + 390 + 40 + 10/2;
    cylinder.position.z = 0;
    bhead.add( cylinder );

    cylinder = new THREE.Mesh(
        new THREE.CylinderGeometry( 80/2, 80/2, 70, 32 ), hatMaterial );
    cylinder.position.x = 0;
    cylinder.position.y = 160 + 390 + 40 + 10 + 70/2;
    cylinder.position.z = 0;
    bhead.add( cylinder );

    // nose
    cylinder = new THREE.Mesh(
        new THREE.CylinderGeometry( 6, 14, 70, 32 ), headMaterial );
    cylinder.position.set( -70, 530, 0 );
    cylinder.rotation.z = 90 * Math.PI / 180.0;
    bhead.add( cylinder );

    // eyes
    const sphGeom = new THREE.SphereGeometry(10, 32, 16);

    // left eye
    sphere = new THREE.Mesh( sphGeom, eyeMaterial );
    sphere.position.set( -48, 560, 0 );
    let eye = new THREE.Object3D();
    eye.add( sphere );
    eye.rotation.y = 20 * Math.PI / 180.0;
    bhead.add( eye );

    // right eye
    sphere = new THREE.Mesh( sphGeom, eyeMaterial );
    sphere.position.set( -48, 560, 0 );
    eye = new THREE.Object3D();
    eye.add( sphere );
    eye.rotation.y = -20 * Math.PI / 180.0;
    bhead.add( eye );
}

function createDrinkingBird(bbird) {
    const support = new THREE.Object3D();
    const body = new THREE.Object3D();
    const head = new THREE.Object3D();

    // MODELS
    // base + legs + feet
    createSupport(support);

    // body + body/head connector
    createBody(body);

    // head + hat
    createHead(head);

    bbird.add(support);
    bbird.add(body);
    bbird.add(head);
}

function init() {
    const canvasWidth = 846;
    const canvasHeight = 494;
    // For grading the window is fixed in size; here's general code:
    //var canvasWidth = window.innerWidth;
    //var canvasHeight = window.innerHeight;

    // RENDERER
    renderer = new THREE.WebGLRenderer( { antialias: true } );
    renderer.gammaInput = true;
    renderer.gammaOutput = true;
    renderer.setSize(canvasWidth, canvasHeight);
    renderer.setClearColor( 0x0, 1.0 );

    // CAMERA
    camera = new THREE.PerspectiveCamera( 35, canvasWidth/ canvasHeight, 1, 4000 );
    camera.position.set( -1160, 350, -600 );

    // CONTROLS
    cameraControls = new OrbitControls(camera, renderer.domElement);
    cameraControls.target.set(0,310,0);

}
function drawHelpers() {
    //Coordinates.drawGround({size:10000});
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

    headlight.position.copy( camera.position );
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