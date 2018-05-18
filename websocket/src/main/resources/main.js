var webSocket = null;
var scene;
var camera;
var renderer;
var controls = void 0;
var data = void 0;
var sphereMesh = [];
var numberOfVertices = 6;
var materialLine;
var materiaBoldlLine;

initGraphics();
initSockets();

function initSockets() {
    webSocket = new WebSocket("ws://" + window.location.hostname + ":" + window.location.port + "/connect/");
    webSocket.onerror = onError.bind(this);
    webSocket.onopen = onOpen.bind(this);
    webSocket.onmessage = onMessage.bind(this);
}

function initGraphics() {
    scene = new THREE.Scene();

    camera = new THREE.PerspectiveCamera(45, window.innerWidth / window.innerHeight, 1, 100);
    camera.position.set(10, 5, 60);
    camera.lookAt(scene.position);
    scene.add(camera);

    renderer = new THREE.WebGLRenderer();
    renderer.setSize(window.innerWidth - 200, window.innerHeight);
    renderer.setClearColor(0x145409);
    renderer.shadowMap.enabled = true;
    renderer.toneMapping = THREE.ReinhardToneMapping;
    document.body.appendChild(renderer.domElement);

    var geometrySphere = new THREE.SphereGeometry(1, 16, 8);
    var materialSphere = new THREE.MeshStandardMaterial({color: 0xE5DF11});
    for (var i = 0; i < numberOfVertices; i++) {
        sphereMesh.push(new THREE.Mesh(geometrySphere, materialSphere));
    }

    scene.add(new THREE.HemisphereLight(0xffffbb, 0x080820, 1));

    materialLine = new THREE.LineBasicMaterial( { color: 0x003300 } );
    materialBoldLine = new THREE.LineBasicMaterial( { color: 0xffffb3 } );

    controls = new THREE.OrbitControls(camera, renderer.domElement);
    controls.update();
}

function animate() {
    requestAnimationFrame(animate.bind(this));
    renderer.render(scene, camera);
}

function onError(evt) {
    console.log(evt.data);
}

function onOpen() {
    console.log("connected");
}

function onMessage(evt) {
    var obj = JSON.parse(evt.data);
    switch (obj.type) {
        case "response":
            data = JSON.parse(obj.data);
            console.log(data);
            for (var i = 0; i < numberOfVertices; i++) {
                sphereMesh[i].position.x = data.coord[i][0];
                sphereMesh[i].position.y = data.coord[i][1];
                sphereMesh[i].position.z = data.coord[i][2];
                scene.add(sphereMesh[i]);
            }
            for (var i = 0; i < data.edges.length; i++) {
                var geometry = new THREE.Geometry();
                geometry.vertices.push(new THREE.Vector3(data.coord[data.edges[i][0]][0], data.coord[data.edges[i][0]][1], data.coord[data.edges[i][0]][2]));
                geometry.vertices.push(new THREE.Vector3(data.coord[data.edges[i][1]][0], data.coord[data.edges[i][1]][1], data.coord[data.edges[i][1]][2]));
                var line = new THREE.Line(geometry, materialLine);
                scene.add( line );
            }
            var geometry = new THREE.Geometry();
            for (var i = 0; i < data.path.length; i++) {
                geometry.vertices.push(new THREE.Vector3(data.coord[data.path[i]][0], data.coord[data.path[i]][1], data.coord[data.path[i]][2]));
            }
            var line = new THREE.Line(geometry, materialBoldLine);
            scene.add( line );
            break;
    }
}

function onClick() {
    var startData = {
        coordinates: [
            [document.getElementById("vertex0_x").value, document.getElementById("vertex0_y").value, document.getElementById("vertex0_z").value],
            [document.getElementById("vertex1_x").value, document.getElementById("vertex1_y").value, document.getElementById("vertex1_z").value],
            [document.getElementById("vertex2_x").value, document.getElementById("vertex2_y").value, document.getElementById("vertex2_z").value],
            [document.getElementById("vertex3_x").value, document.getElementById("vertex3_y").value, document.getElementById("vertex3_z").value],
            [document.getElementById("vertex4_x").value, document.getElementById("vertex4_y").value, document.getElementById("vertex4_z").value],
            [document.getElementById("vertex5_x").value, document.getElementById("vertex5_y").value, document.getElementById("vertex5_z").value]
        ]
    };

    var message = {
        type: "request",
        data: JSON.stringify(startData)
    };
    webSocket.send(JSON.stringify(message));
    animate();
}
