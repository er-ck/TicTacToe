package TicTacToe;

import java.awt.*;
import java.util.LinkedList;

public class TicTacGrid {
	private TicTacTile[][] ticTacTiles;
	private int width, height;
	private int blockWidth;
	private int xBlockNumCount, yBlockNumCount, totalBlocks;

	public TicTacGrid(int width, int height, int blockWidth) {
		this.width = width;
		this.height = height;
		this.blockWidth = blockWidth;
		this.xBlockNumCount = width/blockWidth;
		this.yBlockNumCount = height/blockWidth;
		this.totalBlocks = xBlockNumCount * yBlockNumCount;

		ticTacTiles = new TicTacTile[xBlockNumCount][yBlockNumCount];

		// width = 500; blockwidth = 10; ans = 500/10=50
		for (int x = 0; x < (width / blockWidth); x++) {
			for (int y = 0; y < (height / blockWidth); y++) {
				ticTacTiles[x][y] = new TicTacTile(x, y, blockWidth);
			}
		}
	}

	public void draw(Graphics g) {
		Color outline = Color.black;

		for (int x = 0; x < (getWidth() / blockWidth); x++) {
			for (int y = 0; y < (getHeight()/ blockWidth); y++) {
				TicTacTile ticTacTile = getTicTacTiles()[x][y];
				g.setColor(outline);
				g.drawRect(ticTacTile.x * blockWidth, ticTacTile.y * blockWidth, blockWidth, blockWidth);
				g.setColor(ticTacTile.getColor());
				g.setFont(new Font("Verdana", Font.BOLD, 20));


				g.fillRect(ticTacTile.x * blockWidth, ticTacTile.y * blockWidth, blockWidth, blockWidth);

				FontMetrics m = g.getFontMetrics();
				m.getStringBounds(ticTacTile.xo, g);
				g.setColor(Color.BLACK);
				g.drawString(ticTacTile.xo, ticTacTile.x * blockWidth, ticTacTile.y * blockWidth + m.getHeight());

			}
		}
	}

	/***
	 *  Converts a x and y coordinate to a corresponding box object at that position.
	 *  x represents a point P on the graph with x-coordinate on the grid and y the corresponding y-coordinate.
	 *  This function takes that point P(x,y) and calculates what Box object has been clicked on.
	 * @param x represents a x coordinate on the grid ∴  x lies within 0 and width (x <= width && x >= 0)
	 * @param y represents a y coordinate on the grid ∴  x lies within 0 and width (x <= width && x >= 0)
	 * @return Box object at given P(x,y) or NULL if not found.
	 */
	public TicTacTile coordinatesToBox(int x, int y) {
		TicTacTile ret = null;

		for(int bx = 0; bx < ticTacTiles.length; bx++) {
			for(int by = 0; by < ticTacTiles.length; by++) {
				if (ticTacTiles[bx][by].contains(x,y)) {
					ret = ticTacTiles[bx][by]; // this is the box that we've clicked on
					System.out.println("Box[" + bx + "," + by + "] selected.");
					return ret;
				}
			}
		}
		return ret;
	}

	public TicTacTile[][] getTicTacTiles() {
		return ticTacTiles;
	}

	public void setBlockWidth(int newBlockWidth) {

		// calculate new number of blocks on both axes
		TicTacTile[][] oldboxes = this.ticTacTiles;
		int oldBlockWidth = this.blockWidth;
		int oldxBlocks = xBlockNumCount;
		int oldyBlocks = yBlockNumCount;

		System.out.println("Old block = " + oldBlockWidth);
		System.out.println("NEW block = " + newBlockWidth);

		xBlockNumCount = width / newBlockWidth;
		yBlockNumCount = height / newBlockWidth;
		ticTacTiles = new TicTacTile[xBlockNumCount][yBlockNumCount];

		/*
			Preserve the blocks that have been clicked onto the new blockList.
			this will make is so when i've had stuff drawn on the old block list, it'll be preserved and only the parts
			that need to be cut will be cut according to the new
		 */

		// todo: implement





		for (int x = 0; x < xBlockNumCount; x++) {
			for (int y = 0; y < xBlockNumCount; y++) {
				ticTacTiles[x][y] = new TicTacTile(x, y, blockWidth);
			}
		}

		//set it
		this.blockWidth = newBlockWidth;

	}

	public LinkedList getSurroundingBoxes(TicTacTile ticTacTile) {
		LinkedList list = new LinkedList();
		int x = ticTacTile.x, y = ticTacTile.y;
		TicTacTile neighbourTicTacTile;

		// ensures that when we're on the edge blocks of the grid, the program wont crash from an out of bounds exception
		if ((x > 0 && y > 0)) {
			boolean checkNW = true, checkN = true, checkNE = true,
					checkW = true,                  checkE = true,
					checkSW = true, checkS = true, checkSE = true;

			if (x == xBlockNumCount-1  && y == yBlockNumCount-1) {
				checkNE = false; checkE = false; checkSE = false; checkSW = false; checkS = false;
			} else if (x == 0) {
				checkNW = false; checkW = false; checkSW = false;
			} else if(x == xBlockNumCount-1) {
				checkNE = false; checkE = false; checkSE = false;
			} else if(y == 0) {
				checkNW = false; checkN = false; checkNE = false;
			}else if (y == yBlockNumCount-1){
				checkSW = false; checkS = false; checkSE = false;
			}

			// optimum method but i have no idea how to check for bounds exception. we always hit it and it buggin me
//            for(int m = -1; m <= 1; m++) {
//                for (int j = -1; j<= 1; j++) {
//                    System.out.println(m + " " +  j);
//                    //neighbourBox = boxes[xBlockNumCount-1 + m][yBlockNumCount-1 + j];
//                    if ((neighbourBox = boxes[x+m][y+j]).getColor() != Color.white) {
//                        neighbourBox.neighbourCount++;
//                        list.add(neighbourBox);
//
//                    }
//
//                }
//            }

			// top left - NW
			if ((checkNW) && Color.white != (neighbourTicTacTile = ticTacTiles[x - 1][y - 1]).getColor()) {
				list.add(neighbourTicTacTile);
			}
			// top center - N
			if ((checkN) && Color.white != (neighbourTicTacTile = ticTacTiles[x][y - 1]).getColor()) {
				list.add(neighbourTicTacTile);
			}
			// top right - NE
			if ((checkNE) && Color.white != (neighbourTicTacTile = ticTacTiles[x + 1][y - 1]).getColor()) {
				list.add(neighbourTicTacTile);
			}
			// middle left - W
			if ((checkW) && Color.white != (neighbourTicTacTile = ticTacTiles[x - 1][y]).getColor()) {
				list.add(neighbourTicTacTile);
			}
			// middle right - E
			if ((checkE) && Color.white != (neighbourTicTacTile = ticTacTiles[x + 1][y]).getColor()) {
				list.add(neighbourTicTacTile);
			}
			// bottom left - SW
			if ((checkSW) && Color.white != (neighbourTicTacTile = ticTacTiles[x - 1][y + 1]).getColor()) {
				list.add(neighbourTicTacTile);
			}
			// bottom middle - S
			if ((checkS) && Color.white != (neighbourTicTacTile = ticTacTiles[x][y + 1]).getColor()) {
				list.add(neighbourTicTacTile);
			}
			// bottom right - SE
			if ((checkSE) && Color.white != (neighbourTicTacTile = ticTacTiles[x + 1][y + 1]).getColor()) {
				list.add(neighbourTicTacTile);
			}
		}
//        // top row <-.↑.1->.2->.3->.
//        for (int i=-1; i <= 1; i++) {
//            if ((neighbourBox = boxes[x-i][y+1]).getColor() != Color.white) {
//                neighbourBox.setColor(Color.pink);
//            }
//        }


		return list;
	}

	public int getBlockWidth() {
		return blockWidth;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getxBlockNumCount() {
		return xBlockNumCount;
	}

	public int getyBlockNumCount() {
		return yBlockNumCount;
	}

	public int getTotalBlocks() {
		return totalBlocks;
	}

	/***
	 *  Re-initializes all of the elements of the grid, wiping any previously values assigned permanently.
	 */
	public void reInitialize() {
		for (int x = 0; x < (width / blockWidth); x++) {
			for (int y = 0; y < (height / blockWidth); y++) {
				ticTacTiles[x][y] = new TicTacTile(x, y, blockWidth);
			}
		}
	}
}
