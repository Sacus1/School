"use strict"; // good practice - see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Strict_mode
/*global THREE, Coordinates, $, document, window, dat*/
import * as THREE from "three";
import { OBJLoader } from "three/addons/loaders/OBJLoader.js";
import { OrbitControls } from "three/addons/controls/OrbitControls.js";
import {dat} from "/lib/dat.gui.min.js";
import {MeshPhongMaterial} from "three";

let camera, renderer;
let cameraControls;
let effectController ={
    lightIntensity: 1,
    lightDistance: 1000,
};
const clock = new THREE.Clock();

function Deg2Rad(number) {
    return number * Math.PI / 180;
}

function fillScene() {
    renderer.shadowMapEnabled = true;
    window.scene = new THREE.Scene();
    window.scene.fog = new THREE.Fog( 0xAAAAAA, 200, 2000 );
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
            reflectivity: 0.8,
            //map: new THREE.TextureLoader().load( 'texture/water.jpg'),
            // use normal as displacement map for wave effect
            displacementMap: new THREE.TextureLoader().load( 'texture/water_normal.png'),
            displacementScale: 1,
        } ) );
    waterMesh.rotation.x = - Math.PI / 2;
    waterMesh.position.y = -1;
    waterMesh.receiveShadow = true;
    window.scene.add( waterMesh );
    // GROUND
    let groundTexture = new THREE.TextureLoader().load( 'texture/ground.png' );
    groundTexture.wrapS = groundTexture.wrapT = THREE.RepeatWrapping;
    groundTexture.repeat.set( 2, 2 );
    groundTexture.anisotropy = 16;
    let groundMaterial = new THREE.MeshLambertMaterial( { map: groundTexture,
        side: THREE.DoubleSide,
        normalMap: new THREE.TextureLoader().load( 'texture/ground_normal.png' ),
        // exagerate normal vectors
        normalScale: new THREE.Vector2( 3, 3 ),
    } );
    let mesh = new THREE.Mesh( new THREE.PlaneGeometry( 200, 200 ), groundMaterial );
    mesh.position.y = 1;
    mesh.rotation.x = - Math.PI / 2;
    mesh.rotation.z = Deg2Rad(45);
    mesh.receiveShadow = true;
    window.scene.add( mesh );
    // LIGHTS
    // add light around the camera pointing in a half-circle around the center.
    let spotLight = [];
    let amount = 13;
    for (let i = 0; i < amount; i++) {
        spotLight[i] = new THREE.PointLight( 0xCEAE18, effectController.lightIntensity, effectController.lightDistance );
        let theta = Deg2Rad(90/amount*i) + Deg2Rad(90+45);
        let distance = 750;
        spotLight[i].castShadow = true;

        spotLight[i].position.set( distance*Math.cos(theta), 50, distance*Math.sin(theta) );
        spotLight[i].angle = Deg2Rad(30);
        spotLight[i].penumbra = 1;
        scene.add( spotLight[i] );
        let LightSphere = new THREE.SphereGeometry( 5, 16, 8 );
        let LightMesh = new THREE.Mesh( LightSphere, new THREE.MeshBasicMaterial( { color: 0xCEAE18 } ) );
        LightMesh.position.set( distance*Math.cos(theta), 50, distance*Math.sin(theta) );
        scene.add( LightMesh );
    }
    // EXTERIOR GROUND
    //import the obj file
    let objLoader = new OBJLoader();
    objLoader.load('model/hollow_circle.obj', function (obj) {
        obj.scale.set(70, 30, 70);
        obj.position.set(0, 0, 0);
        // set color to blue, and the ground texture
        let groundTexture = new THREE.TextureLoader().load( 'texture/ground.png' );
        groundTexture.wrapS = groundTexture.wrapT = THREE.RepeatWrapping;
        groundTexture.repeat.set( 20, 20 );
        groundTexture.anisotropy = 16;
        obj.children[0].material = new THREE.MeshPhongMaterial({ map: groundTexture });
        scene.add(obj);
    });
    // SHIP
    //import the obj file
    objLoader.load('model/Ship.obj', function (obj) {
        console.log(obj);
        obj.scale.set(10, 10, 10);
        obj.position.set(-220, 5, 0);
        obj.rotation.y = Deg2Rad(90);
        // set color to brown of the ship
        for (let i = 0; i < obj.children.length; i++) {
            // change color of the ship to brown , with a bit of variation for each part but still brown
            let color = new THREE.Color(0.5 + Math.random() * 0.25, 0.25 + Math.random() * 0.125, 0);
            obj.children[i].material = new THREE.MeshPhongMaterial({ color: color });
        }


        obj.castShadow = true;
        obj.receiveShadow = true;

        scene.add(obj);
    });

    // GUI
    let gui = new dat.GUI();
    gui.add(effectController, "lightIntensity", 0, 2, 0.025)
        .name("Light Intensity")
        .onChange(function (value) {
            for (let i = 0; i < 32; i++) {
                spotLight[i].intensity = value;
            }
            // update gui
            effectController.lightIntensity = value;
            console.log(gui)

        } );
    gui.add(effectController, "lightDistance", 0, 3000, 1)
        .name("Light Distance")
        .onChange(function (value) {
            for (let i = 0; i < 32; i++) {
                spotLight[i].distance = value;
            }
            // update gui
            effectController.lightDistance = value;
        });

    // GUYS (2 guys on the ground) (cylinder and sphere)
    let guy = new THREE.Object3D();
    let cylinder = new THREE.Mesh(new THREE.CylinderGeometry(5, 5, 20, 32), new THREE.MeshPhongMaterial({color: 0xAAAAAA}));
    cylinder.position.set(0, 10, 0);
    cylinder.castShadow = true;
    guy.add(cylinder);
    let sphere = new THREE.Mesh(new THREE.SphereGeometry(6, 32, 32), new THREE.MeshPhongMaterial({color: 0xCED09F}));
    sphere.position.set(0, 17, 0);
    sphere.castShadow = true;
    guy.add(sphere);
    guy.position.set(-6, 0, -22);
    guy.scale.set(.7, .7, .7);

    scene.add(guy);
    let guy2 = new THREE.Object3D();
    let cylinder2 = new THREE.Mesh(new THREE.CylinderGeometry(5, 5, 20, 32), new THREE.MeshPhongMaterial({color: 0xAAAAAA}));
    cylinder2.position.set(0, 10, 0);
    cylinder2.castShadow = true;
    guy2.add(cylinder2);
    let sphere2 = new THREE.Mesh(new THREE.SphereGeometry(6, 32, 32), new THREE.MeshPhongMaterial({color: 0xCED09F}));
    sphere2.position.set(0, 17, 0);
    sphere2.castShadow = true;
    guy2.add(sphere2);
    guy2.position.set(-6, 0, -30);
    guy2.scale.set(.7, .7, .7);
    scene.add(guy2);
    // add sprite in front of tバチhe guys
    let spriteMap = new THREE.TextureLoader().load( 'texture/Sprite.png' );
    let spriteMaterial = new THREE.SpriteMaterial( { map: spriteMap, color: 0xffffff } );
    let sprite = new THREE.Sprite( spriteMaterial );
    sprite.scale.set(15, 15, 15);
    sprite.position.set(0,8, -25);
    sprite.rotation.y = Deg2Rad(90);
    scene.add( sprite );


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
    camera.position.set( 170,50,0);

    // CONTROLS
    cameraControls = new OrbitControls(camera, renderer.domElement);
    cameraControls.target.set(0,50,0);

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
    addToDOM();
    animate();
} catch(e) {
    const errorReport = "Your program encountered an unrecoverable error, can not draw on canvas. Error was:<br/><br/>";
    $('#webGL').append(errorReport+e.stack);
}
