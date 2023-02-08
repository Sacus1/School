"use strict"; // good practice - see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Strict_mode
////////////////////////////////////////////////////////////////////////////////
// Clock hand rotation: rotate the hand into the proper orientation
////////////////////////////////////////////////////////////////////////////////
/*global THREE, Coordinates, document, window, dat, $*/
import * as THREE from "three";
import { OBJLoader } from "three/addons/loaders/OBJLoader.js";
import { OrbitControls } from "three/addons/controls/OrbitControls.js";
import {dat} from "/lib/dat.gui.min.js";
window.window.window.scene = new THREE.Scene();
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
 * Returns a THREE.Object3D helix going from top to bottom positions
 * @param material - THREE.Material
 * @param radius - radius of helix itself
 * @param tube - radius of tube
 * @param radialSegments - number of capsules around a full circle
 * @param tubularSegments - tessellation around equator of each tube
 * @param height - height to extend, from *center* of tube ends along Y axis
 * @param arc - how many times to go around the Y axis; currently just an integer
 * @param clockwise - if true, go counterclockwise up the axis
 */
function createHelix( material, radius, tube, radialSegments, tubularSegments, height, arc, clockwise )
{
    // defaults
    tubularSegments = (tubularSegments === undefined) ? 32 : tubularSegments;
    arc = (arc === undefined) ? 1 : arc;
    clockwise = (clockwise === undefined) ? true : clockwise;

    const helix = new THREE.Object3D();

    const top = new THREE.Vector3();

    const sine_sign = clockwise ? 1 : -1;

    ///////////////
    // YOUR CODE HERE: remove spheres, use capsules instead, going from point to point.
    //
    for (let i = 0; i <= arc*radialSegments ; i++ )
    {
        // going from X to Z axis
        top.set( radius * Math.cos( i * 2*Math.PI / radialSegments ),
            height * (i/(arc*radialSegments)) - height/2,
            sine_sign * radius * Math.sin( i * 2*Math.PI / radialSegments ) );
        const next = new THREE.Vector3();
        next.set( radius * Math.cos( (i+1) * 2*Math.PI / radialSegments ),
            height * ((i+1)/(arc*radialSegments)) - height/2,
            sine_sign * radius * Math.sin( (i+1) * 2*Math.PI / radialSegments ) );
        const sphGeom = new THREE.CapsuleGeometry(tube,next.distanceTo(top),tubularSegments,radialSegments);
        const sphere = new THREE.Mesh(sphGeom, material);
        sphere.position.copy( top );
        // align the capsule to the next point
        const cylAxis = new THREE.Vector3();
        cylAxis.subVectors( next, top );
        makeLengthAngleAxisTransform( sphere, cylAxis, top );
        helix.add( sphere );
    }
    ///////////////

    return helix;
}

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

    const capsule = new THREE.Object3D();
    capsule.add( cyl );
    if ( !openTop || !openBottom ) {
        // instance geometry
        const sphGeom = new THREE.SphereGeometry(radius, segmentsWidth, segmentsWidth / 2);
        if ( !openTop ) {
            const sphTop = new THREE.Mesh(sphGeom, material);
            sphTop.position.set( top.x, top.y, top.z );
            capsule.add( sphTop );
        }
        if ( !openBottom ) {
            const sphBottom = new THREE.Mesh(sphGeom, material);
            sphBottom.position.set( bottom.x, bottom.y, bottom.z );
            capsule.add( sphBottom );
        }
    }

    return capsule;

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

    const radius = 60;
    const tube = 10;
    const radialSegments = 24;
    const height = 300;
    const segmentsWidth = 12;
    const arc = 2;

    let helix;
    helix = createHelix( redMaterial, radius, tube, radialSegments, segmentsWidth, height, arc, true );
    helix.position.y = height/2;
    window.scene.add( helix );

    helix = createHelix( greenMaterial, radius/2, tube, radialSegments, segmentsWidth, height, arc, false );
    helix.position.y = height/2;
    window.scene.add( helix );

    // DNA
    helix = createHelix( blueMaterial, radius, tube/2, radialSegments, segmentsWidth, height, arc, false );
    helix.position.y = height/2;
    helix.position.z = 2.5 * radius;
    window.scene.add( helix );

    helix = createHelix( blueMaterial, radius, tube/2, radialSegments, segmentsWidth, height, arc, false );
    helix.rotation.y = 120 * Math.PI / 180;
    helix.position.y = height/2;
    helix.position.z = 2.5 * radius;
    window.scene.add( helix );

    helix = createHelix( grayMaterial, radius, tube/2, radialSegments, segmentsWidth, height/2, arc, true );
    helix.position.y = height/2;
    helix.position.x = 2.5 * radius;
    window.scene.add( helix );

    helix = createHelix( yellowMaterial, 0.75*radius, tube/2, radialSegments, segmentsWidth, height, 4*arc, false );
    helix.position.y = height/2;
    helix.position.x = 2.5 * radius;
    helix.position.z = -2.5 * radius;
    window.scene.add( helix );

    helix = createHelix( cyanMaterial, 0.75*radius, 4*tube, radialSegments, segmentsWidth, height, 2*arc, false );
    helix.position.y = height/2;
    helix.position.x = 2.5 * radius;
    helix.position.z = 2.5 * radius;
    window.scene.add( helix );

    helix = createHelix( magentaMaterial, radius, tube, radialSegments, segmentsWidth, height, arc, true );
    helix.rotation.x = 45 * Math.PI / 180;
    helix.position.y = height/2;
    helix.position.z = -2.5 * radius;
    window.scene.add( helix );
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
