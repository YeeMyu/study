// Solve Maze
// javac SolveMaze.java

import java.io.*;

class SolveMaze {
    private final int SIZE = 30;
    private char m[][] = new char[SIZE][SIZE];
    private DirPattern dp[][] = new DirPattern[4][4];

    public SolveMaze() {
	int i;
	String str;

	// read map
	try {
	    FileReader fr = new FileReader("map.txt");
	    BufferedReader br = new BufferedReader(fr);
	    i = 0;
	    while(true) {
		if((str = br.readLine()) == null || i >= SIZE) {
		    break;
		}
		m[i] = str.toCharArray();
		++i;
	    }
	    br.close();
	} catch(FileNotFoundException e) {
	    System.err.println(e.getMessage());
	} catch(IOException e) {
	    System.err.println(e.getMessage());
	}

	// diretion pattern
	dp[0][0] = new DirPattern( 0, -1);
	dp[0][1] = new DirPattern(-1,  0);
	dp[0][2] = new DirPattern( 0,  1);
	dp[0][3] = new DirPattern( 1,  0);
	dp[1][0] = new DirPattern(-1,  0);
	dp[1][1] = new DirPattern( 0,  1);
	dp[1][2] = new DirPattern( 1,  0);
	dp[1][3] = new DirPattern( 0, -1);
	dp[2][0] = new DirPattern( 0,  1);
	dp[2][1] = new DirPattern( 1,  0);
	dp[2][2] = new DirPattern( 0, -1);
	dp[2][3] = new DirPattern(-1,  0);
	dp[3][0] = new DirPattern( 1,  0);
	dp[3][1] = new DirPattern( 0, -1);
	dp[3][2] = new DirPattern(-1,  0);
	dp[3][3] = new DirPattern( 0,  1);
    }

    public void Solve() {
	int x = 0, y = 0, i, j;
	int dir; //direction 0:N 1:E 2:S 3:W
	boolean br; // brank flag
	
	// start point
	for(i = 0; i < SIZE; ++i) {
	    if(m[0][i] == ' ') {
		x = i; y = 0;
		break;
	    }
	}
	if(x == 0) {
	    for(i = 0; i < SIZE; ++i) {
		if(m[i][0] == ' ') {
		    x = 0; y = i;
		    break;
		}
	    }
	}
	m[y][x] = '+';
	if(x > 0) {
	    dir = 2;
	    ++y;
	} else {
	    dir = 1;
	    ++x;
	}
	m[y][x] = '+';
	
	do {
	    // check direction
	    br = false;
	    for(i = 0; i < 4; ++i) {
		if(m[y+dp[dir][i].row][x+dp[dir][i].col] == ' ') {
		    if(m[y][x] == '*') {
			m[y][x] = '+';
		    }
		    x += dp[dir][i].col;
		    y += dp[dir][i].row;
		    m[y][x] = '+';
		    dir += i - 1;
		    if(dir == -1) {
			dir = 3;
		    } else if(dir == 4) {
			dir = 0;
		    } else if(dir == 5) {
			dir = 1;
		    }
		    br = true;
		    break;
		}
	    }
	    
	    // dead-end
	    if(!br) {
		if(m[y][x] == '+') {
		    m[y][x] = '*';
		}
		for(i = 0; i < 4; ++i) {
		    if(m[y+dp[dir][i].row][x+dp[dir][i].col] == '+' ||
		       m[y+dp[dir][i].row][x+dp[dir][i].col] == '*') {
			x += dp[dir][i].col;
			y += dp[dir][i].row;
			m[y][x] = '*';
			dir += i - 1;
			if(dir == -1) {
			    dir = 3;
			} else if(dir == 4) {
			    dir = 0;
			} else if(dir == 5) {
			    dir = 1;
			}
			break;
		    }
		}
	    }
	} while(x < (SIZE - 1) && y < (SIZE - 1));

	// delete return route
	for(i = 0; i < SIZE; ++i) {
	    for(j = 0; j < SIZE; ++j) {
		if(m[i][j] == '*') {
		    m[i][j] = ' ';
		}
	    }
	}
    }

    public static void main(String args[]) {
	SolveMaze p = new SolveMaze();
	p.Solve();
	for(int i = 0; i < p.SIZE; ++i) {
	    System.out.println(p.m[i]);
	}
    }
}

class DirPattern {
    public int row, col;
    public DirPattern(int r, int c) {
	row = r;
	col = c;
    }
}

