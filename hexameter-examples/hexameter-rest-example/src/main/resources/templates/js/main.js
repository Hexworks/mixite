var hexagonData = null;
var gridId = null;

document.addEventListener('DOMContentLoaded', function () {
    initPixi();
    $.ajax({
        url: 'http://localhost:' + HEROKU_ASSIGNED_PORT + '/grids',
        type: 'POST',
        dataType: 'json',
        data: JSON.stringify({
            "gridWidth": 15,
            "gridHeight": 15,
            "radius": 20
        }),
        success: function (result) {
            gridId = result;
            console.log("Successfully created grid: " + gridId);
            fetchGrid(gridId);
        }
    });
});

document.querySelector("#redraw").addEventListener("click", function (event) {
    event.preventDefault();
    var radius = document.querySelector("#radius").value;
    var columns = document.querySelector("#columns").value;
    var rows = document.querySelector("#rows").value;
    var gridLayout = document.querySelector("#layout").value;
    var orientation = document.querySelector('input[name="orientation"]:checked').value;

    $.ajax({
        url: 'http://localhost:' + HEROKU_ASSIGNED_PORT + '/grids',
        type: 'PUT',
        dataType: 'json',
        data: JSON.stringify({
            "id": gridId,
            "gridWidth": columns,
            "gridHeight": rows,
            "radius": radius,
            "gridLayout": gridLayout,
            "orientation": orientation
        }),
        success: function (result) {
            gridId = result;
            console.log("Successfully replaced grid: " + gridId);
            fetchGrid(gridId);
        }
    });
}, false);


function fetchGrid(gridId) {
    $.ajax({
        url: 'http://localhost:' + HEROKU_ASSIGNED_PORT + '/grids/getGridForDrawing/' + gridId,
        type: 'GET',
        success: function (result) {
            hexagonData = result.cellData;
            console.log("Successfully fetched grid. Number of cells: " + hexagonData.length);
            drawHexagons();
        }
    });
}

var renderer = null;
var graphics = null;
var stage = null;

function initPixi() {
    console.log("Pixi init...")
    var pixiContainer = document.querySelector("#pixi-container");
    renderer = PIXI.autoDetectRenderer(pixiContainer.offsetWidth, 600, {antialias: true, transparent: true});
    pixiContainer.appendChild(renderer.view);

    stage = new PIXI.Container();
    stage.interactive = true;

    graphics = new PIXI.Graphics();
    stage.addChild(graphics);
    animate();
}

function drawHexagons() {
    graphics.clear();
    graphics.beginFill(0xffffff);
    graphics.lineStyle(1, 0x000000, 1);

    hexagonData.forEach(function (hex) {
        var lastPoint = hex.points[5];
        graphics.moveTo(lastPoint[0], lastPoint[1]);
        hex.points.forEach(function (point) {
            graphics.lineTo(point[0], point[1]);
        });
    });

    graphics.endFill();
}

function animate() {
    renderer.render(stage);
    requestAnimationFrame(animate);
}
