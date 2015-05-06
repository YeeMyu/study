// MakeMaze
// javac MakeMaze.java

import java.io.*;
import java.util.*;

class MakeMaze {
    private final int SIZE = 50;
    private Stack<Location> stk = new Stack<Location>();
    private Direction dir[] = new Direction[4];
    private Vector<Integer> vctr = new Vector<Integer>();
    private int m[][] = new int[SIZE][SIZE];

    public MakeMaze() {
	int i, j;

	// initialize
	for(i = 0; i < SIZE; ++i) {
	    for(j = 0; j < SIZE; ++j) {
		m[j][i] = 1;
	    }
	}

	dir[0] = new Direction( 0, -1); // N
	dir[1] = new Direction( 1,  0); // E
	dir[2] = new Direction( 0,  1); // S
	dir[3] = new Direction(-1,  0); // W
    }

    public void Dig() {
	int i, idx, spc_count, x, y;
	boolean goal = false, dig_flag, oblique_flag;
	Location loc = new Location(0, 0);

	// start point
	x = 0;
	y = (int)(Math.random() * (SIZE - 2.0) + 1.0);
	m[y][x] = 0;
	++x;
	m[y][x] = 0;
	stk.push(new Location(x, y));

	do {
	    dig_flag = false;

	    // initialize vector
	    vctr.clear();
	    for(i = 0; i < 4; ++i) {
		vctr.add(i);
	    }

	    do {
		oblique_flag = false;

		// decide direction
		idx = (int)(Math.random() * vctr.size());
		i = vctr.get(idx);
		vctr.remove(idx);

		// check
		if(x + dir[i].x == (SIZE - 1)) {
		    if(!goal) {
			dig_flag = true;
			goal = true;
			m[y + dir[i].y][x + dir[i].x] = 0;
			stk.push(new Location(x, y));
			break;
		    }
		} else {
		    if(x + dir[i].x >= 1 && x + dir[i].x < (SIZE - 1) &&
		       y + dir[i].y >= 1 && y + dir[i].y < (SIZE - 1)) {
			if(m[y + dir[i].y][x + dir[i].x] == 1) {
			    spc_count = m[y + dir[i].y - 1][x + dir[i].x] +
			        m[y + dir[i].y][x + dir[i].x - 1] +
			        m[y + dir[i].y][x + dir[i].x + 1] +
				m[y + dir[i].y + 1][x + dir[i].x];
			    if(spc_count == 3) {
				// check oblique
				if(i == 0 &&
				   (m[y + dir[i].y - 1][x + dir[i].x - 1] == 0 ||
				    m[y + dir[i].y - 1][x + dir[i].x + 1] == 0)) {
				    oblique_flag = true;
				}
				if(i == 1 &&
				   (m[y + dir[i].y - 1][x + dir[i].x + 1] == 0 ||
				    m[y + dir[i].y + 1][x + dir[i].x + 1] == 0)) {
				    oblique_flag = true;
				}
				if(i == 2 &&
				   (m[y + dir[i].y + 1][x + dir[i].x - 1] == 0 ||
				    m[y + dir[i].y + 1][x + dir[i].x + 1] == 0)) {
				    oblique_flag = true;
				}
				if(i == 3 &&
				   (m[y + dir[i].y - 1][x + dir[i].x - 1] == 0 ||
				    m[y + dir[i].y + 1][x + dir[i].x - 1] == 0)) {
				    oblique_flag = true;
				}
				if(!oblique_flag) {
				    dig_flag = true;
				    m[y + dir[i].y][x + dir[i].x] = 0;
				    stk.push(new Location(x, y));
				    x += dir[i].x;
				    y += dir[i].y;
				    break;
				}
			    }
			}
		    }
		}
	    } while(!vctr.isEmpty());

	    if(!dig_flag) {
		loc = stk.pop();
		x = loc.col;
		y = loc.row;
	    }
	} while(!stk.empty());
    }

    public static void main(String args[]) {
	MakeMaze mm = new MakeMaze();

	mm.Dig();
	for(int i = 0; i < mm.SIZE; ++i) {
	    for(int j = 0; j < mm.SIZE; ++j) {
		if(mm.m[i][j] == 1) {
		    System.out.print('#');
		} else {
		    System.out.print(' ');
		}
	    }
	    System.out.println();
	}
        System.out.println();
    }
}

class Direction {
    public int x, y;
    public Direction(int diff_x, int diff_y) {
	x = diff_x;
	y = diff_y;
    }
}

class Location {
    public int row, col;
    public Location(int x, int y) {
	col = x;
	row = y;
    }
}

    
				
