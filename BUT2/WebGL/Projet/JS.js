"use strict"; // good practice - see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Strict_mode
/*global THREE, Coordinates, $, document, window, dat*/
import * as THREE from "three";
import { OBJLoader } from "three/addons/loaders/OBJLoader.js";
import { OrbitControls } from "three/addons/controls/OrbitControls.js";
import {dat} from "/lib/dat.gui.min.js";
import {Coordinates} from "../lib/Coordinates.js";
import {MeshPhongMaterial} from "three";

let camera, renderer;
let cameraControls;

const clock = new THREE.Clock();

function Deg2Rad(number) {
    return number * Math.PI / 180;
}

function fillScene() {
    window.scene = new THREE.Scene();
    window.scene.fog = new THREE.Fog( 0xAAAAAA, 500, 4000 );
    // SKYBOX
    scene.background = new THREE.CubeTextureLoader()
        .setPath('texture/skybox/')
        .load( [
            'StarSkybox041.png',
            'StarSkybox042.png',
            'StarSkybox043.png',
            'StarSkybox044.png',
            'StarSkybox045.png',
            'StarSkybox046.png'
        ] );

    // WATER (reflections of skybox)
    let waterGeometry = new THREE.PlaneGeometry( 10000, 10000 );
    let waterMesh = new THREE.Mesh( waterGeometry,
        new MeshPhongMaterial( {
            side: THREE.DoubleSide,
            envMap: scene.background,
            reflectivity: 0.5,
            normal: new THREE.TextureLoader().load( 'texture/water_normal.png'),
            map: new THREE.TextureLoader().load( 'texture/water.jpg'),
            // use normal as displacement map for wave effect
            displacementMap: new THREE.TextureLoader().load( 'texture/water_normal.png'),
            displacementScale: 1,
        } ) );
    waterMesh.rotation.x = - Math.PI / 2;
    window.scene.add( waterMesh );
    // GROUND
    let groundTexture = new THREE.TextureLoader().load( 'texture/ground.png' );
    groundTexture.wrapS = groundTexture.wrapT = THREE.RepeatWrapping;
    groundTexture.repeat.set( 2, 2 );
    groundTexture.anisotropy = 16;
    let groundMaterial = new THREE.MeshLambertMaterial( { map: groundTexture,
        side: THREE.DoubleSide,
        normal: new THREE.TextureLoader().load( 'texture/ground_normals.png' ),
        // exagerate normal vectors
        normalScale: new THREE.Vector2( 3, 3 ),
    } );
    let mesh = new THREE.Mesh( new THREE.PlaneGeometry( 200, 200 ), groundMaterial );
    mesh.position.y = 1;
    mesh.rotation.x = - Math.PI / 2;
    mesh.rotation.z = Deg2Rad(45);
    window.scene.add( mesh );

    // LIGHTS
    let ambientLight = new THREE.AmbientLight( 0x222222 );
    scene.add( ambientLight );
    // add 32 light around the camera pointing in a circle around the center.
    let spotLight = [];
    for (let i = 0; i < 32; i++) {
        spotLight[i] = new THREE.PointLight( 0xCEAE18, 1, 1000 );
        let theta = Deg2Rad(360/32*i);
        spotLight[i].position.set( 1000*Math.cos(theta), 50, 1000*Math.sin(theta) );
        spotLight[i].angle = Deg2Rad(30);
        spotLight[i].penumbra = 0.05;
        scene.add( spotLight[i] );
        let LightSphere = new THREE.SphereGeometry( 5, 16, 8 );
        let LightMesh = new THREE.Mesh( LightSphere, new THREE.MeshBasicMaterial( { color: 0xCEAE18 } ) );
        LightMesh.position.set( 1000*Math.cos(theta), 50, 1000*Math.sin(theta) );
        scene.add( LightMesh );
    }
    // EXTERIOR GROUND
    //import the obj file
    let objLoader = new OBJLoader();
    objLoader.load('hollow_circle.obj', function (obj) {
        obj.scale.set(100, 30, 100);
        obj.position.set(0, 0, 0);
        // set color to blue and the ground texture
        let groundTexture = new THREE.TextureLoader().load( 'texture/ground.png' );
        groundTexture.wrapS = groundTexture.wrapT = THREE.RepeatWrapping;
        groundTexture.repeat.set( 20, 20 );
        groundTexture.anisotropy = 16;
        obj.children[0].material = new THREE.MeshPhongMaterial({ map: groundTexture });
        scene.add(obj);
    });
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
    camera.position.set( 100,50,0);

    // CONTROLS
    cameraControls = new OrbitControls(camera, renderer.domElement);
    cameraControls.target.set(0,20,0);

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
