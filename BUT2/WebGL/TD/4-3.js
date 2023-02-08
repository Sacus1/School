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
let gridX = true;
let gridY = false;
let gridZ = false;
let axes = true;
let ground = true;


/**
 * Returns a THREE.Object3D cylinder and spheres going from top to bottom positions
 * @param material - THREE.Material
 * @param radius - the radius of the capsule's cylinder
 * @param top, bottom - THREE.Vector3, top and bottom positions of cone
 * @param segmentsWidth - tessellation around equator, like radiusSegments in CylinderGeometry
 * @param openTop, openBottom - whether the end is given a sphere; true means they are not
 */
function createCapsule( material, radius, top, bottom, segmentsWidth, openTop, openBottom )
{
    // defaults
    segmentsWidth = (segmentsWidth === undefined) ? 32 : segmentsWidth;
    openTop = (openTop === undefined) ? false : openTop;
    openBottom = (openBottom === undefined) ? false : openBottom;

    // get cylinder height
    const cylAxis = new THREE.Vector3();
    cylAxis.subVectors( top, bottom );
    const length = cylAxis.length();

    // get cylinder center for translation
    const center = new THREE.Vector3();
    center.addVectors( top, bottom );
    center.divideScalar( 2.0 );

    // always open-ended
    const cylGeom = new THREE.CylinderGeometry(radius, radius, length, segmentsWidth, 1, 1);
    const cyl = new THREE.Mesh(cylGeom, material);

    // pass in the cylinder itself, its desired axis, and the place to move the center.
    makeLengthAngleAxisTransform( cyl, cylAxis, center );

    // YOUR CODE HERE
    // Here's a sphere's geometry. Use it to cap the cylinder if
    // openTop and/or openBottom is false. Bonus points: use instancing!
    const sphGeom = new THREE.SphereGeometry(radius, segmentsWidth, segmentsWidth / 2);
    const sph = new THREE.Mesh(sphGeom, material);
    if (!openTop){
        cyl.add(sph);
        sph.position.set(0, length / 2, 0);
    }
    if (!openBottom){
        const sph2 = sph.clone(true);
        cyl.add(sph2);
        sph2.position.set(0, -length / 2, 0);
    }
    // You'll probably want to return something other than this...
    return cyl;

}

// Transform cylinder to align with given axis and then move to center
function makeLengthAngleAxisTransform( cyl, cylAxis, center )
{
    cyl.matrixAutoUpdate = false;

    // From left to right using frames: translate, then rotate; TR.
    // So translate is first.
    cyl.matrix.makeTranslation( center.x, center.y, center.z );

    // take cross product of cylAxis and up vector to get axis of rotation
    const yAxis = new THREE.Vector3(0, 1, 0);
    // Needed later for dot product, just do it now;
    // a little lazy, should really copy it to a local Vector3.
    cylAxis.normalize();
    const rotationAxis = new THREE.Vector3();
    rotationAxis.crossVectors( cylAxis, yAxis );
    if ( rotationAxis.length() < 0.000001 )
    {
        // Special case: if rotationAxis is just about zero, set to X axis,
        // so that the angle can be given as 0 or PI. This works ONLY
        // because we know one of the two axes is +Y.
        rotationAxis.set( 1, 0, 0 );
    }
    rotationAxis.normalize();

    // take dot product of cylAxis and up vector to get cosine of angle of rotation
    const theta = -Math.acos(cylAxis.dot(yAxis));
    //cyl.matrix.makeRotationAxis( rotationAxis, theta );
    const rotMatrix = new THREE.Matrix4();
    rotMatrix.makeRotationAxis( rotationAxis, theta );
    cyl.matrix.multiply( rotMatrix );
}

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

    // TEST MATERIALS AND OBJECTS
    const redMaterial = new THREE.MeshLambertMaterial({color: 0xFF0000});
    const greenMaterial = new THREE.MeshLambertMaterial({color: 0x00FF00});
    const blueMaterial = new THREE.MeshLambertMaterial({color: 0x0000FF});
    const grayMaterial = new THREE.MeshLambertMaterial({color: 0x808080});

    const yellowMaterial = new THREE.MeshLambertMaterial({color: 0xFFFF00});
    const cyanMaterial = new THREE.MeshLambertMaterial({color: 0x00FFFF});
    const magentaMaterial = new THREE.MeshLambertMaterial({color: 0xFF00FF});

    const radius = 20;
    const segmentsWidth = 32;
    let capsule;

    // along Y axis
    capsule = new createCapsule( greenMaterial,
        radius,
        new THREE.Vector3( 0, 300, 0 ),
        new THREE.Vector3( 0, 0, 0 ),
        segmentsWidth, false, false );
    window.scene.add( capsule );

    // along X axis
    capsule = new createCapsule( redMaterial,
        radius,
        new THREE.Vector3( 300, 0, 0 ),
        new THREE.Vector3( 0, 0, 0 ),
        segmentsWidth, false, true );
    window.scene.add( capsule );

    // along Z axis
    capsule = new createCapsule( blueMaterial,
        radius,
        new THREE.Vector3( 0, 0, 300 ),
        new THREE.Vector3( 0, 0, 0 ),
        segmentsWidth, false, true );
    window.scene.add( capsule );

    // along XYZ axis
    capsule = new createCapsule( grayMaterial,
        radius,
        new THREE.Vector3( 200, 200, 200 ),
        new THREE.Vector3( 0, 0, 0 ),
        segmentsWidth, false, true );
    window.scene.add( capsule );

    // along -Y axis, translated in XYZ
    capsule = new createCapsule( yellowMaterial,
        radius,
        new THREE.Vector3( 50, 100, -200 ),
        new THREE.Vector3( 50, 300, -200 ),
        segmentsWidth, false, true );
    window.scene.add( capsule );

    // along X axis, from top of previous capsule
    capsule = new createCapsule( cyanMaterial,
        radius,
        new THREE.Vector3( 50, 300, -200 ),
        new THREE.Vector3( 250, 300, -200 ),
        segmentsWidth, false, true );
    window.scene.add( capsule );

    // continue from bottom of previous capsule
    capsule = new createCapsule( magentaMaterial,
        radius,
        new THREE.Vector3( 250, 300, -200 ),
        new THREE.Vector3( -150, 100, 0 ),
        segmentsWidth, false, false );
    window.scene.add( capsule );
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
    renderer.gammaInput = true;
    renderer.gammaOutput = true;
    renderer.setSize(canvasWidth, canvasHeight);
    renderer.setClearColor( 0xAAAAAA, 1.0 );

    // CAMERA
    camera = new THREE.PerspectiveCamera( 40, canvasRatio, 1, 10000 );
    camera.position.set( -528, 513, 92 );
    // CONTROLS
    cameraControls = new OrbitControls(camera, renderer.domElement);
    cameraControls.target.set(0,200,0);

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
