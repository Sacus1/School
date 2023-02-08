"use strict"; // good practice - see https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Strict_mode
////////////////////////////////////////////////////////////////////////////////
// Drinking Bird Model exercise                                               //
// Your task is to complete the model for the drinking bird                   //
// Please work from the formal blueprint dimensions and positions shown at    //
// https://www.udacity.com/wiki/cs291/notes                                   //
//                                                                            //
// The following materials should be used:                                    //
// Hat and spine: cylinderMaterial (blue)                                     //
// Head and bottom body: sphereMaterial (red)                                 //
// Rest of body: cubeMaterial (orange)                                        //
//                                                                            //
// So that the exercise passes, and the spheres and cylinders look good,      //
// all SphereGeometry calls should be of the form:                            //
//     SphereGeometry( radius, 32, 16 );                                      //
// and CylinderGeometry calls should be of the form:                          //
//     CylinderGeometry( radiusTop, radiusBottom, height, 32 );               //
////////////////////////////////////////////////////////////////////////////////
/*global THREE, Coordinates, $, document, window, dat*/
import * as THREE from "three";
import { OBJLoader } from "three/addons/loaders/OBJLoader.js";
import { OrbitControls } from "three/addons/controls/OrbitControls.js";
import { dat } from "/lib/dat.gui.min.js";
import { Coordinates } from "../lib/Coordinates.js";
var camera, renderer;
var cameraControls, effectController;
var clock = new THREE.Clock();
var gridX = false;
var gridY = false;
var gridZ = false;
var axes = false;
var ground = true;

function init() {
  var canvasWidth = 846;
  var canvasHeight = 494;
  // For grading the window is fixed in size; here's general code:
  //var canvasWidth = window.innerWidth;
  //var canvasHeight = window.innerHeight;
  var canvasRatio = canvasWidth / canvasHeight;

  // RENDERER
  renderer = new THREE.WebGLRenderer({ antialias: true });
  renderer.gammaInput = true;
  renderer.gammaOutput = true;
  renderer.setSize(canvasWidth, canvasHeight);
  renderer.setClearColor(0xffffff, 1.0);

  // CAMERA
  camera = new THREE.PerspectiveCamera(45, canvasRatio, 1, 40000);
  // CONTROLS
  cameraControls = new OrbitControls(camera, renderer.domElement);

  camera.position.set(5,5,0)
  cameraControls.target.set(0, 0, 0);

  fillScene();
  // add button to change ambient light to red
  var ambientLightButton = document.getElementById("ambientLightButton");
  ambientLightButton.addEventListener("click", function () {
    var ambientLight = new THREE.AmbientLight(0xff0000);
    window.scene.add(ambientLight);
  });
}

function fillScene() {
  // SCENE
  window.scene = new THREE.Scene();
  window.scene.fog = new THREE.Fog(0x808080, 3000, 6000);
  // LIGHTS
  var ambientLight = new THREE.AmbientLight(0x222222);
  var light = new THREE.DirectionalLight(0x0000ff, 1.0);
  light.position.set(200, 400, 500);

  var light2 = new THREE.DirectionalLight(0xffffff, 1.0);
  light2.position.set(-400, 200, -300);

  window.scene.add(ambientLight);
  window.scene.add(light);
  window.scene.add(light2);

  if (gridX) {
    Coordinates.drawGrid({ size: 1000, scale: 0.01 });
  }
  if (gridY) {
    Coordinates.drawGrid({ size: 1000, scale: 0.01, orientation: "y" });
  }
  if (gridZ) {
    Coordinates.drawGrid({ size: 1000, scale: 0.01, orientation: "z" });
  }
  if (axes) {
    Coordinates.drawAllAxes({ axisLength: 300, axisRadius: 2, axisTess: 50 });
  }
  let loader = new OBJLoader();
  // Charger le fichier
  loader.load("suzanne.obj", function (object) {
    window.scene.add(object);
  });
  // add a cube
  var cubeMaterial = new THREE.MeshLambertMaterial({
    color: 0x00ff00,
    shading: THREE.FlatShading,
  });
  var cube = new THREE.Mesh(new THREE.BoxGeometry(1, 1, 1), cubeMaterial);
  cube.position.set(0, 1.5, 0);
  window.scene.add(cube);
}
//
function addToDOM() {
  var container = document.getElementById("webGL");
  var canvas = container.getElementsByTagName("canvas");
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
  var delta = clock.getDelta();
  cameraControls.update(delta);
  if (
    effectController.newGridX !== gridX ||
    effectController.newGridY !== gridY ||
    effectController.newGridZ !== gridZ ||
    effectController.newGround !== ground ||
    effectController.newAxes !== axes
  ) {
    gridX = effectController.newGridX;
    gridY = effectController.newGridY;
    gridZ = effectController.newGridZ;
    ground = effectController.newGround;
    axes = effectController.newAxes;

    fillScene();
  }
  renderer.render(window.scene, camera);
}

function setupGui() {
  effectController = {
    newGridX: gridX,
    newGridY: gridY,
    newGridZ: gridZ,
    newGround: ground,
    newAxes: axes,
  };

  var gui = new dat.GUI();
  gui.add(effectController, "newGridX").name("Show XZ grid");
  gui.add(effectController, "newGridY").name("Show YZ grid");
  gui.add(effectController, "newGridZ").name("Show XY grid");
  gui.add(effectController, "newGround").name("Show ground");
  gui.add(effectController, "newAxes").name("Show axes");
}

try {
  init();
  setupGui();
  addToDOM();
  animate();
} catch (e) {
  var errorReport =
    "Your program encountered an unrecoverable error, can not draw on canvas. Error was:<br/><br/>";
  $("#webGL").append(errorReport + e.stack);
}
