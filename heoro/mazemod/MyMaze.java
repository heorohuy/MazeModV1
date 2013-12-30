package heoro.mazemod;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;

public class MyMaze {
	private int dimensionX, dimensionZ; // dimension of maze
	private int gridDimensionX, gridDimensionZ; // dimension of output grid
	private char[][] grid; // output grid
	private Cell[][] cells; // 2d array of Cells
	private Random random = new Random(); // The random object
	private final int SIZE = 3;
	private char[][] actualGrid;
	private String newGrid = "";


	// initialize with x and y the same
	public MyMaze(int aDimension, World world, int x, int y, int z) {
		// Initialize
		this(aDimension, aDimension, world, x, y, z);
	}
	// constructor
	public MyMaze(int xDimension, int yDimension, World world, int x, int y, int z) {
		dimensionX = xDimension;
		dimensionZ = yDimension;
		gridDimensionX = xDimension * 4 + 1;
		gridDimensionZ = yDimension * 2 + 1;
		grid = new char[gridDimensionX][gridDimensionZ];
		actualGrid = new char[gridDimensionX*SIZE][gridDimensionZ*SIZE];
		init();
		generateMaze();
		buildMaze(world,x,y,z);
	}

	private void init() {
		// create cells
		cells = new Cell[dimensionX][dimensionZ];
		for (int x = 0; x < dimensionX; x++) {
			for (int y = 0; y < dimensionZ; y++) {
				cells[x][y] = new Cell(x, y, false); // create cell (see Cell constructor)
			}
		}
	}

	// inner class to represent a cell
	private class Cell {
		int x, y; // coordinates
		// cells this cell is connected to
		ArrayList<Cell> neighbors = new ArrayList<Cell>();
		// solver: if already used
		boolean visited = false;
		// solver: the Cell before this one in the path
		Cell parent = null;
		// solver: if used in last attempt to solve path
		boolean inPath = false;
		// solver: distance travelled this far
		double travelled;
		// solver: projected distance to end
		double projectedDist;
		// impassable cell
		boolean wall = true;
		// if true, has yet to be used in generation
		boolean open = true;
		// construct Cell at x, y
		Cell(int x, int y) {
			this(x, y, true);
		}
		// construct Cell at x, y and with whether it isWall
		Cell(int x, int y, boolean isWall) {
			this.x = x;
			this.y = y;
			this.wall = isWall;
		}
		// add a neighbor to this cell, and this cell as a neighbor to the other
		void addNeighbor(Cell other) {
			if (!this.neighbors.contains(other)) { // avoid duplicates
				this.neighbors.add(other);
			}
			if (!other.neighbors.contains(this)) { // avoid duplicates
				other.neighbors.add(this);
			}
		}
		// used in updateGrid()
		boolean isCellBelowNeighbor() {
			return this.neighbors.contains(new Cell(this.x, this.y + 1));
		}
		// used in updateGrid()
		boolean isCellRightNeighbor() {
			return this.neighbors.contains(new Cell(this.x + 1, this.y));
		}
		// useful Cell representation
		@Override
		public String toString() {
			return String.format("Cell(%s, %s)", x, y);
		}
		// useful Cell equivalence
		@Override
		public boolean equals(Object other) {
			if (!(other instanceof Cell)) return false;
			Cell otherCell = (Cell) other;
			return (this.x == otherCell.x && this.y == otherCell.y);
		}
		// should be overridden with equals
		@Override
		public int hashCode() {
			// random hash code method designed to be usually unique
			return this.x + this.y * 256;
		}

		//sets the cell to be a wall
		public void wallIt(){
			this.wall=true;
		}
	}
	// generate from upper left (In computing the y increases down often)
	private void generateMaze() {
		generateMaze(0, 0);
	}
	// generate the maze from coordinates x, y
	private void generateMaze(int x, int y) {
		generateMaze(getCell(x, y)); // generate from Cell
	}
	private void generateMaze(Cell startAt) {
		// don't generate from cell not there
		if (startAt == null) return;
		startAt.open = false; // indicate cell closed for generation
		ArrayList<Cell> cells = new ArrayList<Cell>();
		cells.add(startAt);

		while (!cells.isEmpty()) {
			Cell cell;
			// this is to reduce but not completely eliminate the number
			//   of long twisting halls with short easy to detect branches
			//   which results in easy mazes
			if (random.nextInt(10)==0)
				cell = cells.remove(random.nextInt(cells.size()));
			else cell = cells.remove(cells.size() - 1);
			// for collection
			ArrayList<Cell> neighbors = new ArrayList<Cell>();
			// cells that could potentially be neighbors
			Cell[] potentialNeighbors = new Cell[]{
					getCell(cell.x + 1, cell.y),
					getCell(cell.x, cell.y + 1),
					getCell(cell.x - 1, cell.y),
					getCell(cell.x, cell.y - 1)
			};
			for (Cell other : potentialNeighbors) {
				// skip if outside, is a wall or is not opened
				if (other==null || other.wall || !other.open) continue;
				neighbors.add(other);
			}
			if (neighbors.isEmpty()) continue;
			// get random cell
			Cell selected = neighbors.get(random.nextInt(neighbors.size()));
			// add as neighbor
			selected.open = false; // indicate cell closed for generation
			cell.addNeighbor(selected);
			cells.add(cell);
			cells.add(selected);
		}
	}
	// used to get a Cell at x, y; returns null out of bounds
	public Cell getCell(int x, int y) {
		try {
			return cells[x][y];
		} catch (ArrayIndexOutOfBoundsException e) { // catch out of bounds
			return null;
		}
	}
	//
	//	public void solve() {
	//		// default solve top left to bottom right
	//		this.solve(0, 0, dimensionX/2, dimensionZ/2);
	//	}
	//	// solve the maze starting from the start state (A-star algorithm)
	//	public void solve(int startX, int startY, int endX, int endY) {
	//		// re initialize cells for path finding
	//		for (Cell[] cellrow : this.cells) {
	//			for (Cell cell : cellrow) {
	//				cell.parent = null;
	//				cell.visited = false;
	//				cell.inPath = false;
	//				cell.travelled = 0;
	//				cell.projectedDist = -1;
	//			}
	//		}
	//		// cells still being considered
	//		ArrayList<Cell> openCells = new ArrayList<Cell>();
	//		// cell being considered
	//		Cell endCell = getCell(endX, endY);
	//		if (endCell == null) return; // quit if end out of bounds
	//		{ // anonymous block to delete start, because not used later
	//			Cell start = getCell(startX, startY);
	//			if (start == null) return; // quit if start out of bounds
	//			start.projectedDist = getProjectedDistance(start, 0, endCell);
	//			start.visited = true;
	//			openCells.add(start);
	//		}
	//		boolean solving = true;
	//		while (solving) {
	//			if (openCells.isEmpty()) return; // quit, no path
	//			// sort openCells according to least projected distance
	//			Collections.sort(openCells, new Comparator<Cell>(){
	//				@Override
	//				public int compare(Cell cell1, Cell cell2) {
	//					double diff = cell1.projectedDist - cell2.projectedDist;
	//					if (diff > 0) return 1;
	//					else if (diff < 0) return -1;
	//					else return 0;
	//				}
	//			});
	//			Cell current = openCells.remove(0); // pop cell least projectedDist
	//			if (current == endCell) break; // at end
	//			for (Cell neighbor : current.neighbors) {
	//				double projDist = getProjectedDistance(neighbor,
	//						current.travelled + 1, endCell);
	//				if (!neighbor.visited || // not visited yet
	//						projDist < neighbor.projectedDist) { // better path
	//					neighbor.parent = current;
	//					neighbor.visited = true;
	//					neighbor.projectedDist = projDist;
	//					neighbor.travelled = current.travelled + 1;
	//					if (!openCells.contains(neighbor))
	//						openCells.add(neighbor);
	//				}
	//			}
	//		}
	//		// create path from end to beginning
	//		Cell backtracking = endCell;
	//		backtracking.inPath = true;
	//		while (backtracking.parent != null) {
	//			backtracking = backtracking.parent;
	//			backtracking.inPath = true;
	//		}
	//	}
	//	// get the projected distance
	//	// (A star algorithm consistent)
	//	public double getProjectedDistance(Cell current, double travelled, Cell end) {
	//		return travelled + Math.abs(current.x - end.x) + 
	//				Math.abs(current.y - current.x);
	//	}

	// draw the maze
	public void updateGrid() {
		char backChar = ' ', wallChar = 'X', cellChar = ' ', pathChar = '*';
		// fill background
		for (int x = 0; x < gridDimensionX; x ++) {
			for (int y = 0; y < gridDimensionZ; y ++) {
				grid[x][y] = backChar;
			}
		}
		// build walls
		for (int x = 0; x < gridDimensionX; x ++) {
			for (int y = 0; y < gridDimensionZ; y ++) {
				if (x % 4 == 0 || y % 2 == 0){
					grid[x][y] = wallChar;
				}
			}
		}
		// make meaningful representation
		for (int x = 0; x < dimensionX; x++) {
			for (int y = 0; y < dimensionZ; y++) {
				Cell current = getCell(x, y);
				int gridX = x * 4 + 2, gridY = y * 2 + 1;
				if (current.inPath) {
					grid[gridX][gridY] = pathChar;
					if (current.isCellBelowNeighbor())
						if (getCell(x, y + 1).inPath) {
							grid[gridX][gridY + 1] = pathChar;
							grid[gridX + 1][gridY + 1] = backChar;
							grid[gridX - 1][gridY + 1] = backChar;
						} else {
							grid[gridX][gridY + 1] = cellChar;
							grid[gridX + 1][gridY + 1] = backChar;
							grid[gridX - 1][gridY + 1] = backChar;
						}
					if (current.isCellRightNeighbor())
						if (getCell(x + 1, y).inPath) {
							grid[gridX + 2][gridY] = pathChar;
							grid[gridX + 1][gridY] = pathChar;
							grid[gridX + 3][gridY] = pathChar;
						} else {
							grid[gridX + 2][gridY] = cellChar;
							grid[gridX + 1][gridY] = cellChar;
							grid[gridX + 3][gridY] = cellChar;
						}
				} else {
					grid[gridX][gridY] = cellChar;
					if (current.isCellBelowNeighbor()) {
						grid[gridX][gridY + 1] = cellChar;
						grid[gridX + 1][gridY + 1] = backChar;
						grid[gridX - 1][gridY + 1] = backChar;
					}
					if (current.isCellRightNeighbor()) {
						grid[gridX + 2][gridY] = cellChar;
						grid[gridX + 1][gridY] = cellChar;
						grid[gridX + 3][gridY] = cellChar;
					}
				}
			}
		}
	}

	// simply prints the map
	public void draw() {
		System.out.print(this);
	}
	// forms a meaningful representation
	@Override
	public String toString() {
		updateGrid();
		String output = "";
		for (int y = 0; y < gridDimensionZ; y++) {
			for (int x = 0; x < gridDimensionX; x++) {
				output += grid[x][y];
			}
			output += "\n";
		}
		return output;
	}

	//	public void buildMaze(World world, int x, int y, int z){
	//		
	//		try{
	//			newGrid = toString();
	//		}catch (ArrayIndexOutOfBoundsException e) {
	//			System.err.println("Caught ArrayIndexOutOfBoundsException: "
	//					+  e.getMessage());
	//
	//		}
	//
	//		int yy = 0;
	//		for(int xx = 0; xx < newGrid.length(); xx++){
	//			if(newGrid.charAt(xx) != '\n'){
	//				actualGrid[xx][yy] = newGrid.charAt(xx);
	//			}else{
	//				yy++;
	//			}
	//		}
	//		//grid is likly unpopulated.
	//		for (int a = 0; a < gridDimensionZ; a++) {
	//			for (int b = 0; b < gridDimensionX; b++) {
	//				if(grid[b][a] == 'X'){
	//					//Build walls.
	//					//7 blocks, floor, roof, and 5 layers.
	//					for(int c = 0; c < 7; c++){
	//						//Maps each cell in the grid to a 3x3 area.
	//						for(int d = 0; d < SIZE; d++)
	//							for(int e = 0; e < SIZE; e++)
	//								world.setBlock(x+b*SIZE+d,y+c,z+a*SIZE+e,Block.cobblestoneMossy.blockID);
	//					}
	//				}
	//				else if(grid[b][a] == ' '){
	//					//Empty the space.
	//					//1 Floor block
	//					for(int d = 0; d < SIZE; d++)
	//						for(int e = 0; e < SIZE; e++)
	//							world.setBlock(x+b*SIZE+d,y,z+a*SIZE+e,Block.cobblestoneMossy.blockID);
	//					//5 Air blocks
	//					for(int c = 1; c < 6; c++){
	//						for(int d = 0; d < SIZE; d++)
	//							for(int e = 0; e < SIZE; e++)
	//								world.setBlockToAir(x+b*SIZE+d, y+c, z+a*SIZE+e);
	//					}
	//					//1 Roof block
	//					for(int d = 0; d < SIZE; d++)
	//						for(int e = 0; e < SIZE; e++)
	//							world.setBlock(x+b*SIZE+d,y+6,z+a*SIZE+e,Block.cobblestoneMossy.blockID);
	//				}
	//			}
	//		}
	//
	//		// insert chest here : grid[gridDimensionX/2][gridDimensionY/2]
	//		world.setBlock(x+SIZE*gridDimensionX/2,y+1,z+SIZE*gridDimensionZ/2,BlockChest.chest.blockID);
	//		TileEntityChest tileEntity = (TileEntityChest) world.getBlockTileEntity(x+SIZE*gridDimensionX/2,y+1,z+SIZE*gridDimensionZ/2);
	//		tileEntity.setInventorySlotContents(27/2, new ItemStack(Item.arrow, 64));
	//	}

	public void buildMaze(World world, int x, int y, int z){
		updateGrid();

		//grid is likly unpopulated.
		for (int a = 0; a < gridDimensionZ; a++) {
			for (int b = 0; b < gridDimensionX; b++) {
				if(grid[b][a] == 'X'){
					//Build walls.
					//7 blocks, floor, roof, and 5 layers.
					for(int c = 0; c < 7; c++){
						//Maps each cell in the grid to a 3x3 area.
						for(int d = 0; d < SIZE; d++){
							for(int e = 0; e < SIZE; e++){
								if(e==SIZE/2 && d==SIZE/2 && c==1 && random.nextInt(4)==0){
									world.setBlock(x+b*SIZE+d,y+c,z+a*SIZE+e,Block.mobSpawner.blockID);
								}
								else{
									world.setBlock(x+b*SIZE+d,y+c,z+a*SIZE+e,Block.cobblestoneMossy.blockID);
								}
							}
						}
					}
				}
				else if(grid[b][a] == ' '){
					//Empty the space.
					//1 Floor block
					for(int d = 0; d < SIZE; d++)
						for(int e = 0; e < SIZE; e++)
							world.setBlock(x+b*SIZE+d,y,z+a*SIZE+e,Block.cobblestoneMossy.blockID);
					//5 Air blocks
					for(int c = 1; c < 6; c++){
						for(int d = 0; d < SIZE; d++)
							for(int e = 0; e < SIZE; e++)
								world.setBlockToAir(x+b*SIZE+d, y+c, z+a*SIZE+e);
					}
					//1 Roof block
					for(int d = 0; d < SIZE; d++)
						for(int e = 0; e < SIZE; e++)
							world.setBlock(x+b*SIZE+d,y+6,z+a*SIZE+e,Block.cobblestoneMossy.blockID);
				}
			}
		}

		// insert chest here : grid[dimensionX/2][dimensionY/2]
		world.setBlock(x+SIZE*dimensionX/2,y+1,z+SIZE*dimensionZ/2,Block.anvil.blockID);//Pedestal
		world.setBlock(x+SIZE*dimensionX/2,y+2,z+SIZE*dimensionZ/2,BlockChest.chest.blockID);
		TileEntityChest tileEntity = (TileEntityChest) world.getBlockTileEntity(x+SIZE*dimensionX/2,y+2,z+SIZE*dimensionZ/2);
		tileEntity.setInventorySlotContents(27/2, new ItemStack(Item.arrow, 64));

		//Generate the entrence shaft.
		int ex = random.nextInt(gridDimensionX);
		int ez = random.nextInt(gridDimensionZ);
		int[] heights = new int[9];
		int i = 0;
		for(int a = 0; a < 3; a++){
			for(int b = 0; b < 3; b++){
				for(int c = y + 6; c < world.getHeightValue(a+x+ex, b+z+ez); c++){
					if(a==1 && b==1){
						world.setBlockToAir(a+x+ex, c, b+z+ez);
					}else{
						world.setBlock(a+x+ex, c, b+z+ez,Block.cobblestoneMossy.blockID);
					}
				}
			}
		}
	}
}