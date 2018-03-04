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
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
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
0 # reserved for to_number
48 # reserved for '0'
LDX 1, 0 # PrintNumber: prints whatever in R0.
LDX 1, 31 # reserved
JZ 0, 1, 29 # Print '0' and return
LDA 2, 0, 10 # output digit.
DVD 0, 2 # reserved
AMR 1, 1, 12 # get char
OUT 1, 1 # output it.
JZ 0, 1, 31 # return
JMA 1, 17 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
LDR 0, 1, 12 # 
OUT 0, 1 # out 0.
RFS 0 # return
0 # reserved
400 # List_head
400 # List_tail
20 # Count
LDA 0, 0, 31 # PROGRAM_INIT
AIR 0, 1 # 96
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
JMA 2, 8 # loop ends
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved
0 # reserved