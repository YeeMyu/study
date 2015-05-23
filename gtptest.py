#!/usr/bin/env python
# -*- coding: utf-8 -*-
# This program needs GNU Go

import os, sys

def run_gtp():
    os.execlp('gnugo', 'gnugo', '--mode', 'gtp')

def operate(size, komi):
    ope_fh = open('operate.log', 'w')
    black_res = ''
    white_res = ''

    print 'boardsize ' + str(size) 
    sys.stdout.flush()
    temp = raw_input()
    temp = raw_input()
    print 'komi ' + str(komi)
    sys.stdout.flush()
    temp = raw_input()
    temp = raw_input()
    
    while 1:
        print 'genmove black'
        sys.stdout.flush()
        black_res = raw_input()
        ope_fh.write('black_res '+ black_res + '\n')
        if black_res == '= resign' \
           or (black_res == '= PASS' and white_res == '= PASS'):
            temp = raw_input()
            break
        temp = raw_input()

        print 'genmove white'
        sys.stdout.flush()
        white_res = raw_input()
        ope_fh.write('white_res ' + white_res + '\n')
        if white_res == '= resign' \
           or (black_res == '= PASS' and white_res == '= PASS'):
            temp = raw_input()
            break
        temp = raw_input()

    print 'showboard'
    sys.stdout.flush()
    temp = raw_input()
    while temp != '':
        ope_fh.write(temp + '\n')
        temp = raw_input()
    ope_fh.write('\n')

    print 'final_score'
    sys.stdout.flush()
    temp = raw_input()
    ope_fh.write('score' + temp + '\n')

    print 'quit'
    sys.stdout.flush()
    ope_fh.close()

while 1:
    print 'boardsize = ',
    try:
        size = int(raw_input())
    except ValueError:
        continue
    else:
        if size > 1 and size <= 19:
            break

while 1:
    print 'komi = ',
    try:
        komi = float(raw_input())
    except ValueError:
        continue
    else:
        if komi >= 0 and komi < 100:
            break;

stdin = sys.stdin.fileno()
stdout = sys.stdout.fileno()
p_stdin, c_stdout = os.pipe()
c_stdin, p_stdout = os.pipe()
pid = os.fork()
if pid:
    # parent
    os.close(c_stdout)
    os.close(c_stdin)
    os.dup2(p_stdin, stdin)
    os.dup2(p_stdout, stdout)
    operate(size, komi)
else:
    # child
    os.close(p_stdin)
    os.close(p_stdout)
    os.dup2(c_stdin, stdin)
    os.dup2(c_stdout, stdout)
    run_gtp()
