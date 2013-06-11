package biz.pavonis.hexameter.example;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import biz.pavonis.hexameter.Hexagon;
import biz.pavonis.hexameter.HexagonOrientation;
import biz.pavonis.hexameter.HexagonalGrid;
import biz.pavonis.hexameter.HexagonalGridBuilder;
import biz.pavonis.hexameter.Point;

public class Main {

	/**
	 * Simple and ugly sample usage of the hexameter framework.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);

		// params for grid
		final int width = 1280;
		final int height = 900;
		int gridWidth = 25;
		int gridHeight = 15;
		int radius = 25;

		shell.setSize(width, height);
		final Canvas canvas = new Canvas(shell, SWT.NONE);
		canvas.setBounds(0, 0, width, height);

		final HexagonalGrid hexagonGrid = new HexagonalGridBuilder().setGridWidth(gridWidth).setGridHeight(gridHeight)
				.setRadius(radius).setOrientation(HexagonOrientation.POINTY).build();

		canvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {

				e.gc.setLineWidth(2);
				e.gc.setForeground(canvas.getDisplay().getSystemColor(SWT.COLOR_DARK_BLUE));
				e.gc.setBackground(canvas.getDisplay().getSystemColor(SWT.COLOR_YELLOW));
				e.gc.fillRectangle(new Rectangle(0, 0, width, height));

				for (List<Hexagon> hexagons : hexagonGrid.getHexagons()) {
					for (Hexagon hexagon : hexagons) {
						int[] points = new int[12];
						int i = 0;
						if (hexagon != null) {
							for (Point point : hexagon.getPoints()) {
								points[i] = (int) Math.round(point.x);
								points[i + 1] = (int) Math.round(point.y);
								i += 2;
							}
							e.gc.drawPolygon(points);
							e.gc.drawText(hexagon.getGridX() + "," + hexagon.getGridY(),
									(int) hexagon.getCenterX() - 11, (int) hexagon.getCenterY() - 10);
						}
					}
				}

			}
		});

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
