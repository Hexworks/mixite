package org.codetome.hexameter.swtexample;

import org.codetome.hexameter.api.*;
import org.codetome.hexameter.api.Point;
import org.codetome.hexameter.api.exception.HexagonalGridCreationException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.codetome.hexameter.api.HexagonOrientation.POINTY_TOP;
import static org.codetome.hexameter.api.HexagonalGridLayout.RECTANGULAR;

public class DemoComposite extends Composite {

    private HexagonalGrid hexagonalGrid;
    private HexagonalGridCalculator hexagonalGridCalculator;
    private static final int DEFAULT_GRID_WIDTH = 15;
    private static final int DEFAULT_GRID_HEIGHT = 15;
    private static final int DEFAULT_RADIUS = 30;
    private static final HexagonOrientation DEFAULT_ORIENTATION = POINTY_TOP;
    private static final HexagonalGridLayout DEFAULT_GRID_LAYOUT = RECTANGULAR  ;
    private static final int CANVAS_WIDTH = 1000;

    private int gridWidth = DEFAULT_GRID_WIDTH;
    private int gridHeight = DEFAULT_GRID_HEIGHT;
    private int radius = DEFAULT_RADIUS;
    private HexagonOrientation orientation = DEFAULT_ORIENTATION;
    private HexagonalGridLayout hexagonGridLayout = DEFAULT_GRID_LAYOUT;
    private boolean showNeighbors = false;
    private boolean showMovementRange = false;
    private Hexagon prevSelected = null;
    private Hexagon currSelected = null;
    private int movementRange;
    private Font font;
    private int fontSize;
    private boolean drawCoordinates;

    // I used window builder to build this...>:)
    public DemoComposite(Shell parent, int style, final int shellWidth, final int shellHeight) {
        super(parent, style);
        GridLayout compositeLayout = new GridLayout(2, false);
        compositeLayout.horizontalSpacing = 0;
        compositeLayout.verticalSpacing = 0;
        compositeLayout.marginWidth = 0;
        compositeLayout.marginHeight = 0;
        setLayout(compositeLayout);
        setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        final Canvas canvas = new Canvas(this, SWT.DOUBLE_BUFFERED);
        canvas.addMouseMoveListener(new MouseMoveListener() {
            public void mouseMove(MouseEvent e) {
            }
        });
        GridData gd_canvas = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
        gd_canvas.minimumWidth = CANVAS_WIDTH;
        canvas.setLayoutData(gd_canvas);
        canvas.setLayout(new GridLayout(1, false));
        final Group grpControls = new Group(this, SWT.NONE);
        grpControls.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        GridLayout gl_grpControls = new GridLayout(2, false);
        gl_grpControls.marginHeight = 0;
        grpControls.setLayout(gl_grpControls);
        grpControls.setText("Controls:");

        // pointy radio
        final Button radioPointy = new Button(grpControls, SWT.RADIO);
        radioPointy.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent arg0) {
                if (radioPointy.getSelection()) {
                    orientation = POINTY_TOP;
                    regenerateHexagonGrid(canvas);
                }
            }
        });
        radioPointy.setSelection(true);
        radioPointy.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
        radioPointy.setText("Pointy");

        // flat radio
        final Button radioFlat = new Button(grpControls, SWT.RADIO);
        radioFlat.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
        radioFlat.setText("Flat");
        radioFlat.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent arg0) {
                if (radioFlat.getSelection()) {
                    orientation = HexagonOrientation.FLAT_TOP;
                    regenerateHexagonGrid(canvas);
                }
            }
        });

        // layout
        Label lblLayout = new Label(grpControls, SWT.NONE);
        lblLayout.setText("Layout");
        final Combo layoutCombo = new Combo(grpControls, SWT.NONE);
        for (HexagonalGridLayout layout : HexagonalGridLayout.values()) {
            layoutCombo.add(layout.name());
        }
        layoutCombo.setText(DEFAULT_GRID_LAYOUT.name());
        layoutCombo.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                hexagonGridLayout = HexagonalGridLayout.valueOf(layoutCombo.getText());
                regenerateHexagonGrid(canvas);
            }
        });
        layoutCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

        // grid width
        Label lblNewLabel = new Label(grpControls, SWT.NONE);
        lblNewLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
        lblNewLabel.setText("Grid width");
        final Spinner gridWidthSpinner = new Spinner(grpControls, SWT.BORDER);
        gridWidthSpinner.setSelection(gridWidth);
        gridWidthSpinner.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                gridWidth = gridWidthSpinner.getSelection();
                regenerateHexagonGrid(canvas);
            }

        });

        // grid height
        Label lblGridy = new Label(grpControls, SWT.NONE);
        lblGridy.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
        lblGridy.setText("Grid height");
        final Spinner gridHeightSpinner = new Spinner(grpControls, SWT.BORDER);
        gridHeightSpinner.setSelection(gridHeight);
        gridHeightSpinner.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                gridHeight = gridHeightSpinner.getSelection();
                regenerateHexagonGrid(canvas);
            }

        });

        // radius
        Label lblRadius = new Label(grpControls, SWT.NONE);
        lblRadius.setText("Radius");
        final Spinner radiusSpinner = new Spinner(grpControls, SWT.BORDER);
        radiusSpinner.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                radius = radiusSpinner.getSelection();
                regenerateHexagonGrid(canvas);
            }

        });
        radiusSpinner.setSelection(radius);

        // movement range
        Label lblMovementRange = new Label(grpControls, SWT.NONE);
        lblMovementRange.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblMovementRange.setText("Movement range");
        final Spinner movementRangeSpinner = new Spinner(grpControls, SWT.BORDER);
        movementRangeSpinner.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                movementRange = movementRangeSpinner.getSelection();
                canvas.redraw();
            }

        });

        // toggle neighbors
        final Button toggleNeighborsCheck = new Button(grpControls, SWT.CHECK);
        toggleNeighborsCheck.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
        toggleNeighborsCheck.setText("Toggle neighbors");
        final Button toggleMovementRangeCheck = new Button(grpControls, SWT.CHECK);
        toggleMovementRangeCheck.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
        toggleMovementRangeCheck.setText("Toggle movement range");
        toggleMovementRangeCheck.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseUp(MouseEvent e) {
                showMovementRange = toggleMovementRangeCheck.getSelection();
                showNeighbors = !showMovementRange;
                toggleNeighborsCheck.setSelection(showNeighbors);
                canvas.redraw();
            }
        });
        toggleNeighborsCheck.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseUp(MouseEvent e) {
                showNeighbors = toggleNeighborsCheck.getSelection();
                showMovementRange = !showNeighbors;
                toggleMovementRangeCheck.setSelection(showMovementRange);
                canvas.redraw();
            }
        });

        // toggle draw coordinates
        final Button toggleDrawCoordinatesCheck = new Button(grpControls, SWT.CHECK);
        toggleDrawCoordinatesCheck.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
        toggleDrawCoordinatesCheck.setText("Draw coordinates");
        toggleDrawCoordinatesCheck.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseUp(MouseEvent e) {
                drawCoordinates = toggleDrawCoordinatesCheck.getSelection();
                canvas.redraw();
            }
        });

        // reset button
        Button resetButton = new Button(grpControls, SWT.NONE);
        resetButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
        resetButton.setText("Reset");

        // position of mouse
        Label lblXPosition = new Label(grpControls, SWT.NONE);
        lblXPosition.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblXPosition.setText("X position:");

        final Text xPositionText = new Text(grpControls, SWT.BORDER);
        xPositionText.setEditable(false);
        xPositionText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

        Label lblYPosition = new Label(grpControls, SWT.NONE);
        lblYPosition.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblYPosition.setText("Y position:");

        final Text yPositionText = new Text(grpControls, SWT.BORDER);
        yPositionText.setEditable(false);
        yPositionText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

        // distance
        Label lblDistance = new Label(grpControls, SWT.NONE);
        lblDistance.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblDistance.setText("Distance");

        final Text distanceText = new Text(grpControls, SWT.BORDER);
        distanceText.setEditable(false);
        distanceText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

        Label lblNewLabel_1 = new Label(grpControls, SWT.NONE);
        lblNewLabel_1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
        lblNewLabel_1.setText("(between last 2 selected)");

        resetButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseUp(MouseEvent arg0) {
                resetFields();
                resetControls();
                regenerateHexagonGrid(canvas);
            }

            private void resetFields() {
                orientation = DEFAULT_ORIENTATION;
                gridHeight = DEFAULT_GRID_HEIGHT;
                gridWidth = DEFAULT_GRID_WIDTH;
                radius = DEFAULT_RADIUS;
                showNeighbors = false;
                showMovementRange = false;
                hexagonGridLayout = DEFAULT_GRID_LAYOUT;
                prevSelected = null;
                currSelected = null;
                movementRange = 0;
                drawCoordinates = false;
            }

            private void resetControls() {
                radioPointy.setSelection(true);
                radioFlat.setSelection(false);
                gridHeightSpinner.setSelection(DEFAULT_GRID_HEIGHT);
                gridWidthSpinner.setSelection(DEFAULT_GRID_WIDTH);
                radiusSpinner.setSelection(DEFAULT_RADIUS);
                toggleNeighborsCheck.setSelection(false);
                toggleMovementRangeCheck.setSelection(false);
                distanceText.setText("");
                movementRangeSpinner.setSelection(0);
                toggleDrawCoordinatesCheck.setSelection(false);
                layoutCombo.setText(DEFAULT_GRID_LAYOUT.name());

            }
        });

        canvas.addMouseMoveListener(new MouseMoveListener() {
            public void mouseMove(MouseEvent e) {
                xPositionText.setText(e.x + "");
                yPositionText.setText(e.y + "");
            }
        });

        // darawing
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseUp(MouseEvent e) {
                Hexagon hex = null;
                try {
                    hex = hexagonalGrid.getByPixelCoordinate(e.x, e.y).get();
                } catch (NoSuchElementException ex) {
                    ex.printStackTrace();
                }
                if (hex != null) {
                    prevSelected = currSelected;
                    currSelected = hex;
                    drawDistance();
                    Optional<SatelliteDataImpl> dataOptional = hex.<SatelliteDataImpl>getSatelliteData();
                    SatelliteDataImpl data = null;
                    if (dataOptional.isPresent()) {
                        data = dataOptional.get();
                    } else {
                        data = new SatelliteDataImpl();
                    }
                    data.setSelected(!data.isSelected());
                    hex.setSatelliteData(data);
                }
                canvas.redraw();
            }

            private void drawDistance() {
                if (prevSelected != null) {
                    distanceText.setText(hexagonalGridCalculator.calculateDistanceBetween(prevSelected, currSelected) + "");
                }
            }
        });
        canvas.addPaintListener(new PaintListener() {
            Color darkBlue = getDisplay().getSystemColor(SWT.COLOR_DARK_BLUE);
            Color white = getDisplay().getSystemColor(SWT.COLOR_WHITE);
            Color darkGray = getDisplay().getSystemColor(SWT.COLOR_DARK_GRAY);
            Color yellow = getDisplay().getSystemColor(SWT.COLOR_YELLOW);
            Color red = getDisplay().getSystemColor(SWT.COLOR_RED);

            public void paintControl(PaintEvent e) {
                e.gc.setLineWidth(2);
                e.gc.setForeground(darkBlue);
                e.gc.setBackground(white);
                e.gc.fillRectangle(new Rectangle(0, 0, shellWidth, shellHeight));

                for (Hexagon hexagon : hexagonalGrid.getHexagons()) {
                    Optional<SatelliteDataImpl> data = hexagon.<SatelliteDataImpl>getSatelliteData();
                    if (data.isPresent() && data.get().isSelected()) {
                        if (showNeighbors) {
                            for (Hexagon hex : hexagonalGrid.getNeighborsOf(hexagon)) {
                                drawNeighborHexagon(e.gc, hex);
                            }
                        }
                        if (showMovementRange) {
                            for (Hexagon hex : hexagonalGridCalculator.calculateMovementRangeFrom(hexagon, movementRange)) {
                                drawMovementRangeHexagon(e.gc, hex);
                            }
                        }
                    }
                    drawEmptyHexagon(e.gc, hexagon);
                }
                for (Hexagon hexagon : hexagonalGrid.getHexagons()) {
                    Optional<SatelliteDataImpl> data = hexagon.<SatelliteDataImpl>getSatelliteData();
                    if (data.isPresent() && data.get().isSelected()) {
                        drawFilledHexagon(e.gc, hexagon);
                    }
                    if (drawCoordinates) {
                        drawCoordinates(e.gc, hexagon);
                    }
                }
            }

            private void drawEmptyHexagon(GC gc, Hexagon hexagon) {
                gc.setForeground(darkBlue);
                gc.setBackground(white);
                gc.drawPolygon(convertToPointsArr(hexagon.getPoints()));
            }

            private void drawFilledHexagon(GC gc, Hexagon hexagon) {
                gc.setForeground(white);
                gc.setBackground(darkBlue);
                gc.fillPolygon(convertToPointsArr(hexagon.getPoints()));
                gc.setForeground(darkBlue);
                gc.drawPolygon(convertToPointsArr(hexagon.getPoints()));
            }

            private void drawNeighborHexagon(GC gc, Hexagon hexagon) {
                gc.setForeground(white);
                gc.setBackground(darkGray);
                gc.fillPolygon(convertToPointsArr(hexagon.getPoints()));
                gc.setForeground(darkBlue);
                gc.drawPolygon(convertToPointsArr(hexagon.getPoints()));
            }

            private void drawMovementRangeHexagon(GC gc, Hexagon hexagon) {
                gc.setForeground(darkBlue);
                gc.setBackground(yellow);
                gc.fillPolygon(convertToPointsArr(hexagon.getPoints()));
                gc.setForeground(darkBlue);
                gc.drawPolygon(convertToPointsArr(hexagon.getPoints()));
            }

            private void drawCoordinates(GC gc, Hexagon hexagon) {
                int x = hexagon.getGridX();
                int y = hexagon.getGridY();
                int z = -(x + y);
                gc.setFont(font);
                gc.setForeground(red);
                gc.drawString("x:" + x, (int) hexagon.getCenterX() - fontSize, (int) (hexagon.getCenterY() - fontSize * 2.5), true);
                gc.drawString("y:" + y, (int) (hexagon.getCenterX() - fontSize * 2.8), (int) hexagon.getCenterY() + fontSize / 3, true);
                gc.drawString("z:" + z, (int) (hexagon.getCenterX() + fontSize / 3), (int) hexagon.getCenterY() + fontSize / 3, true);
            }

            private int[] convertToPointsArr(List<Point> points) {
                int[] pointsArr = new int[12];
                int i = 0;
                for (Point point : points) {
                    pointsArr[i] = (int) Math.round(point.getX());
                    pointsArr[i + 1] = (int) Math.round(point.getY());
                    i += 2;
                }
                return pointsArr;
            }
        });

        // fire it up
        regenerateHexagonGrid(canvas);
    }

    private void regenerateHexagonGrid(Canvas canvas) {
        FontData fd = canvas.getDisplay().getSystemFont().getFontData()[0];
        fontSize = (int) (radius / 3.5);
        font = new Font(canvas.getDisplay(), fd.getName(), fontSize, SWT.NONE);
        try {
            HexagonalGridBuilder builder = new HexagonalGridBuilder().setGridWidth(gridWidth).setGridHeight(gridHeight).setRadius(radius).setOrientation(orientation).setGridLayout(hexagonGridLayout).setCustomStorage(new HashMap<String, Hexagon>());
            hexagonalGrid = builder.build();
            hexagonalGridCalculator = builder.buildCalculatorFor(hexagonalGrid);
        } catch (HexagonalGridCreationException e) {
            final Shell dialog = new Shell(canvas.getShell(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
            dialog.setLayout(new RowLayout());
            Label msg = new Label(dialog, SWT.NONE);
            msg.setText(e.getMessage());
            final Button ok = new Button(dialog, SWT.PUSH);
            ok.setText("Ok");
            Listener listener = new Listener() {
                public void handleEvent(Event event) {
                    dialog.close();
                }
            };
            ok.addListener(SWT.Selection, listener);
            dialog.pack();
            dialog.open();
        }
        canvas.redraw();
    }
}
