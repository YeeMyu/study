/* distribution of primes */

#include <iostream>
using namespace std;

#define INTERVAL 10
#define S 1
#define M 2
#define L 3
#define LINESIZE 50

char pnum_count(int x);

int main(void)
{
  int  cell, flag, cnt = 0, char_cnt = 0;
  long i, j, from, to, interval_cnt = 0;

  cout << "Input 'from' number: ";
  cin >> from;
  if(from < 2) {
    cout << "bad number.\n";
    return 1;
  }
  cout << "Input 'to' number: ";
  cin >> to;
  if(to <= from) {
    cout << "bad number.\n";
    return 1;
  }

  cout << '|';
  for(i = from; i <= to; i++) {
    flag = 0;
    for(j = 2; j <= i / 2; j++)
      if(i % j == 0) {
	flag = 1;
	break;
      }
    if(flag == 0)
      cnt++;
    interval_cnt++;
    if(interval_cnt % INTERVAL == 0) {
      cout << pnum_count(cnt);
      char_cnt++;
      cnt = 0;
      interval_cnt = 0;
    }
    if(char_cnt >= LINESIZE) {
      cout << '|' << endl;
      if(i < to)
	cout << '|';
      char_cnt = 0;
    }
  }
  cout << pnum_count(cnt) << endl;

  return 0;
}

char pnum_count(int x) 
{
  if(x <= S)
    return ' ';
  else if(x > S && x <= M)
    return '.';
  else if(x > M && x <= L)
    return 'o';
  else
    return 'O';
}
