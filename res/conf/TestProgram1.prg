100
0
0
0
0
0
0 # line 5
IN 1, 0 # SUB_ROUTINE: input() -> R1, char inputed.
JZ 1, 0, 6 # reserved
RFS 0
0 # reserved for input_number, return value
0 # reserved for input_number, iterator
STR 3, 0, 9 # SUB_ROUTINE: input_number() -> return value in R1, a number inputed.
JSR 0, 6 # Take input
JSR 0, 27 # to_number
JZ 2, 0, 22
LDA 0, 0, 10 # load 10
LDR 2, 0, 10 # load previous answer
MLT 2, 0 # calc answer
STR 3, 0, 10
AMR 1, 0, 10
STR 1, 0, 10 # save digit.
JMA 0, 12 # loop.
LDR 1, 0, 10
LDA 0, 0, 0
STR 0, 0, 10
LDR 3, 0, 9
RFS 0
LDX 1, 0 # to_number(Ascii in 1) -> ascii to number. number in R1, if not number, R2 is 0, else is 1.
LDX 1, 31 # load 64
JMA 1, 1 # Jump to 65 to continue
0 # reserved for calc I
64 # paging
96 # paging
128 # paging
160 # paging
192 # paging
224 # paging
256 # paging
0 # reserved
LDR 1, 1, 11, 1 # reserved
OUT 1, 1 # reserved
LDR 1, 1, 11 # output element in list 
SIR 1, 1 # reserved
STR 1, 1, 11 # save it back
JMA 1, 20 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved for list
0 # reserved for list
0 # reserved for list
0 # reserved for list
0 # reserved for list
LDA 2, 0, 10 # reserved
DVD 0, 2 # reserved
AMR 1, 1, 12 # reserved
STR 1, 1, 11, 1 # reserved
LDR 2, 1, 11 # reserved
AIR 2, 1 # reserved
JZ 0, 1, 18 # reserved
JMA 1, 16 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
SMR 1, 1, 12 # I1 = 64. calc, -48
JGE 1, 1, 5 # is_num?
LDA 2, 0, 0 # no
RFS 0
STR 1, 1, 11 # Store answer
SIR 1, 10 # calc, -10
JGE 1, 1, 3 # is_num?
LDR 1, 1, 11 # yes
LDA 2, 0, 1 # 
RFS 0 # reserved
0 # reserved for to_number and Print Number, list tail
48 # reserved for '0' #12
LDX 1, 0 # Print Number
LDX 1, 31 # 
LDR 2, 1, 12 # 
STR 2, 1, 11 # manage list
JMA 1, 31, 1 # arrange output
SMR 2, 1, 12 # 
AIR 2, 1 # 
SOB 2, 1, 30, 1 # loop
LDA 0, 0, 13 # reserved
OUT 0, 1 # reserved
RFS 0 # return
JGE 1, 1, 28 # abs(R1) #24
STR 1, 1, 11 # 
SMR 1, 1, 11 # -> 0
SMR 1, 1, 11 # -> -R1
RFS 0 # reserved
0 # reserved
39 # reserved
53 # reserved
0 # reserved
400 # List_head
400 # List_tail
20 # Count, temp
LDA 0, 0, 31 # PROGRAM_INIT , 96 + #4
AIR 0, 1 # get 96
STR 0, 0, 30 # calc
LDX 2, 30, 1 # Initialize self.
LDR 0, 2, 3 # loop begins
SOB 0, 2, 11 # loop condition
JMA 2, 18
STR 0, 2, 3 # 11
JSR 0, 11 # load list number
STR 1, 2, 2, 1# store list
LDR 1, 2, 2 # 
AIR 1, 1 # 
STR 1, 2, 2 # update list tail
JMA 2, 8 # loop ends #17
LDR 0, 2, 1 # reserved
STR 0, 2, 3 # loop begins #19
SMR 0, 2, 2 # #20
JZ 0, 2, 27 # reserved
LDR 0, 2, 3, 1 # 
JSR 1, 13 # output
LDR 0, 2, 3
AIR 0, 1
JMA 2, 19 # loop ends
LDR 2, 2, 31 # reads 33 #27
STR 2, 0, 30 # reserved
LDX 3, 30, 1 # reserved
JMA 3, 4 # reserved
33 # reserved
0 # reserved
0 # the input from user, #1
0 # the min diff value, #2
32767 # the min difference(inited with INT MAX)
JSR 0, 11 # Main continues: Reads an input from user, #4
STR 1, 3, 1 # 
LDR 0, 3, 1 # 
JSR 1, 13 # output that number
LDR 1, 2, 1, 1 # load first value from list
STR 1, 0, 30
SMR 1, 3, 1 # get difference
JSR 1, 24 # abs
SMR 1, 3, 3
JGE 1, 3, 18 # no change.
AMR 1, 3, 3
STR 1, 3, 3
LDR 1, 0, 30
STR 1, 3, 2 
LDR 1, 2, 1 # +1 # 18
AIR 1, 1 
STR 1, 2, 1
SMR 1, 2, 2 # isEnd?
JNE 1, 3, 8 # loop
LDR 0, 3, 2 # OUTPUT
JSR 1, 13 # 
HLT # reserved