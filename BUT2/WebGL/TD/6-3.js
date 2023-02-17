"use strict"; // good practice - see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Strict_mode
////////////////////////////////////////////////////////////////////////////////
// Make 4 viewports
////////////////////////////////////////////////////////////////////////////////
/*global THREE, Coordinates, $ */
import * as THREE from "three";
import { OBJLoader } from "three/addons/loaders/OBJLoader.js";
import { OrbitControls } from "three/addons/controls/OrbitControls.js";
import {dat} from "/lib/dat.gui.min.js";
import {Coordinates} from "../lib/Coordinates.js";

let camera, topCam, sideCam, frontCam;
let renderer;
let cameraControls;

const clock = new THREE.Clock();

let canvasWidth;
let canvasHeight;

function fillScene() {
    window.scene = new THREE.Scene();
    window.scene.fog = new THREE.Fog( 0xDDDDDD, 2000, 4000 );

    // LIGHTS

    window.scene.add( new THREE.AmbientLight( 0x222222 ) );

    let light = new THREE.DirectionalLight(0xFFFFFF, 0.7);
    light.position.set( 200, 500, 500 );

    window.scene.add( light );

    light = new THREE.DirectionalLight( 0xFFFFFF, 0.9 );
    light.position.set( -200, -100, -400 );

    window.scene.add( light );

    // MATERIALS
    const headMaterial = new THREE.MeshLambertMaterial();
    headMaterial.color.r = 104/255;
    headMaterial.color.g = 1/255;
    headMaterial.color.b = 5/255;

    const hatMaterial = new THREE.MeshPhongMaterial({shininess: 100});
    hatMaterial.color.r = 24/255;
    hatMaterial.color.g = 38/255;
    hatMaterial.color.b = 77/255;
    hatMaterial.specular.setRGB( 0.5, 0.5, 0.5 );

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

    const legMaterial = new THREE.MeshPhongMaterial({shininess: 4});
    legMaterial.color.setHex( 0xAdA79b );
    legMaterial.specular.setRGB( 0.5, 0.5, 0.5 );

    const footMaterial = new THREE.MeshPhongMaterial({color: 0x960f0b, shininess: 30});
    footMaterial.specular.setRGB( 0.5, 0.5, 0.5 );

    let sphere, cylinder, cube;

    const bevelRadius = 1.9;	// TODO: 2.0 causes some geometry bug.

    // MODELS
    // base
    cube = new THREE.Mesh(
        new THREE.BoxGeometry( 20+64+110, 4, 2*77+12  ), footMaterial );
    cube.position.x = -45;	// (20+32) - half of width (20+64+110)/2
    cube.position.y = 4/2;	// half of height
    cube.position.z = 0;	// centered at origin
    window.scene.add( cube );

    // feet
    cube = new THREE.Mesh(
        new THREE.BoxGeometry( 20+64+110, 52, 6  ), footMaterial );
    cube.position.x = -45;	// (20+32) - half of width (20+64+110)/2
    cube.position.y = 52/2;	// half of height
    cube.position.z = 77 + 6/2;	// offset 77 + half of depth 6/2
    window.scene.add( cube );

    cube = new THREE.Mesh(
        new THREE.BoxGeometry( 20+64+110, 52, 6  ), footMaterial );
    cube.position.x = -45;	// (20+32) - half of width (20+64+110)/2
    cube.position.y = 52/2;	// half of height
    cube.position.z = -(77 + 6/2);	// negative offset 77 + half of depth 6/2
    window.scene.add( cube );

    cube = new THREE.Mesh(
        new THREE.BoxGeometry( 64, 104, 6  ), footMaterial );
    cube.position.x = 0;	// centered on origin along X
    cube.position.y = 104/2;
    cube.position.z = 77 + 6/2;	// negative offset 77 + half of depth 6/2
    window.scene.add( cube );

    cube = new THREE.Mesh(
        new THREE.BoxGeometry( 64, 104, 6  ), footMaterial );
    cube.position.x = 0;	// centered on origin along X
    cube.position.y = 104/2;
    cube.position.z = -(77 + 6/2);	// negative offset 77 + half of depth 6/2
    window.scene.add( cube );

    // legs
    cube = new THREE.Mesh(
        new THREE.BoxGeometry( 60, 282+4, 4  ), legMaterial );
    cube.position.x = 0;	// centered on origin along X
    cube.position.y = 104 + 282/2 - 2;
    cube.position.z = 77 + 6/2;	// negative offset 77 + half of depth 6/2
    window.scene.add( cube );

    cube = new THREE.Mesh(
        new THREE.BoxGeometry( 60, 282+4, 4  ), legMaterial );
    cube.position.x = 0;	// centered on origin along X
    cube.position.y = 104 + 282/2 - 2;
    cube.position.z = -(77 + 6/2);	// negative offset 77 + half of depth 6/2
    window.scene.add( cube );

    // body
    sphere = new THREE.Mesh(
        new THREE.SphereGeometry( 104/2, 32, 16, 0, Math.PI * 2, Math.PI/2, Math.PI ), bodyMaterial );
    sphere.position.x = 0;
    sphere.position.y = 160;
    sphere.position.z = 0;
    window.scene.add( sphere );

    // cap for top of hemisphere
    cylinder = new THREE.Mesh(
        new THREE.CylinderGeometry( 104/2, 104/2, 0, 32 ), bodyMaterial );
    cylinder.position.x = 0;
    cylinder.position.y = 160;
    cylinder.position.z = 0;
    window.scene.add( cylinder );

    cylinder = new THREE.Mesh(
        new THREE.CylinderGeometry( 12/2, 12/2, 390 - 100, 32 ), bodyMaterial );
    cylinder.position.x = 0;
    cylinder.position.y = 160 + 390/2 - 100;
    cylinder.position.z = 0;
    window.scene.add( cylinder );

    // glass stem
    sphere = new THREE.Mesh(
        new THREE.SphereGeometry( 116/2, 32, 16 ), glassMaterial );
    sphere.position.x = 0;
    sphere.position.y = 160;
    sphere.position.z = 0;
    window.scene.add( sphere );

    cylinder = new THREE.Mesh(
        new THREE.CylinderGeometry( 24/2, 24/2, 390, 32 ), glassMaterial );
    cylinder.position.x = 0;
    cylinder.position.y = 160 + 390/2;
    cylinder.position.z = 0;
    window.scene.add( cylinder );

    // head
    sphere = new THREE.Mesh(
        new THREE.SphereGeometry( 104/2, 32, 16 ), headMaterial );
    sphere.position.x = 0;
    sphere.position.y = 160 + 390;
    sphere.position.z = 0;
    window.scene.add( sphere );

    // hat
    cylinder = new THREE.Mesh(
        new THREE.CylinderGeometry( 142/2, 142/2, 10, 32 ), hatMaterial );
    cylinder.position.x = 0;
    cylinder.position.y = 160 + 390 + 40 + 10/2;
    cylinder.position.z = 0;
    window.scene.add( cylinder );

    cylinder = new THREE.Mesh(
        new THREE.CylinderGeometry( 80/2, 80/2, 70, 32 ), hatMaterial );
    cylinder.position.x = 0;
    cylinder.position.y = 160 + 390 + 40 + 10 + 70/2;
    cylinder.position.z = 0;
    window.scene.add( cylinder );

    const crossbarMaterial = new THREE.MeshPhongMaterial({color: 0x808080, specular: 0xFFFFFF, shininess: 400});
    const eyeMaterial = new THREE.MeshPhongMaterial({color: 0x000000, specular: 0x303030, shininess: 4});

    // crossbar
    cylinder = new THREE.Mesh(
        new THREE.CylinderGeometry( 5, 5, 200, 32 ), crossbarMaterial );
    cylinder.position.set( 0, 360, 0 );
    cylinder.rotation.x = 90 * Math.PI / 180.0;
    window.scene.add( cylinder );

    // nose
    cylinder = new THREE.Mesh(
        new THREE.CylinderGeometry( 6, 14, 70, 32 ), headMaterial );
    cylinder.position.set( -70, 530, 0 );
    cylinder.rotation.z = 90 * Math.PI / 180.0;
    window.scene.add( cylinder );

    // eyes
    const sphGeom = new THREE.SphereGeometry(10, 32, 16);

    // left eye
    sphere = new THREE.Mesh( sphGeom, eyeMaterial );
    sphere.position.set( -48, 560, 0 );
    let eye = new THREE.Object3D();
    eye.add( sphere );
    eye.rotation.y = 20 * Math.PI / 180.0;
    window.scene.add( eye );

    // right eye
    sphere = new THREE.Mesh( sphGeom, eyeMaterial );
    sphere.position.set( -48, 560, 0 );
    eye = new THREE.Object3D();
    eye.add( sphere );
    eye.rotation.y = -20 * Math.PI / 180.0;
    window.scene.add( eye );
}

function init() {
    canvasWidth = 846;
    canvasHeight = 494;
    // For grading the window is fixed in size; here's general code:
    //var canvasWidth = window.innerWidth;
    //var canvasHeight = window.innerHeight;
    const aspectRatio = canvasWidth / canvasHeight;

    // RENDERER
    renderer = new THREE.WebGLRenderer( { antialias: true } );
    renderer.gammaInput = true;
    renderer.gammaOutput = true;
    renderer.setSize(canvasWidth, canvasHeight);
    renderer.setClearColor( 0xDDDDDD, 1.0 );
    // don't clear when multiple viewports are drawn
    renderer.autoClear = false;

    const container = document.getElementById('webGL');
    container.appendChild( renderer.domElement );

    // CAMERA
    camera = new THREE.PerspectiveCamera( 45, canvasWidth / canvasHeight, 1, 4000 );
    camera.position.set( -1160, 310, -600 );

    // OrthographicCamera( left, right, top, bottom, near, far )
    const viewSize = 1100;
    topCam = new THREE.OrthographicCamera(
        -aspectRatio*viewSize / 2, aspectRatio*viewSize / 2,
        viewSize / 2, -viewSize / 2,
        -1000, 1000 );
    // set X to be the up axis
    topCam.up.set( 1, 0, 0 );
    sideCam = new THREE.OrthographicCamera(
        -aspectRatio*viewSize / 2, aspectRatio*viewSize / 2,
        viewSize / 2, -viewSize / 2,
        -1000, 1000 );
    // set Y to be the up axis
    sideCam.up.set( 0, 1, 0 );
    frontCam = new THREE.OrthographicCamera(
        -aspectRatio*viewSize / 2, aspectRatio*viewSize / 2,
        viewSize / 2, -viewSize / 2,
        -1000, 1000 );
    // set X to be the up axis
    frontCam.up.set( 0, 1, 0 );
    // CONTROLS
    cameraControls = new OrbitControls(camera, renderer.domElement);
    cameraControls.target.set(0,310,0);

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

    // clear whole screen with proper clear color
    renderer.clear();

    // perspective camera
    renderer.setViewport( 0, 0, 0.5*canvasWidth, 0.5*canvasHeight );
    renderer.render( window.scene, camera );

    // top view
    topCam.position.copy( cameraControls.target );
    // move up a unit and look down at bird
    topCam.position.y +=1 ;
    topCam.lookAt( cameraControls.target );
    renderer.setViewport( 0.5*canvasWidth, 0.5*canvasHeight, 0.5*canvasWidth, 0.5*canvasHeight );
    renderer.render( window.scene, topCam );
    // side view
    sideCam.position.copy( cameraControls.target );
    // move right a unit and look at bird
    sideCam.position.z +=1 ;
    sideCam.lookAt( cameraControls.target );
    renderer.setViewport( 0, 0.5*canvasHeight, 0.5*canvasWidth, 0.5*canvasHeight );
    renderer.render( window.scene, sideCam );
    // front view
    frontCam.position.copy( cameraControls.target );
    // move forward a unit and look at bird
    frontCam.position.x -=1 ;
    frontCam.lookAt( cameraControls.target );
    renderer.setViewport( 0.5*canvasWidth, 0, 0.5*canvasWidth, 0.5*canvasHeight );
    renderer.render( window.scene, frontCam );

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