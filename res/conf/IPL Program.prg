LDA 0, 0, 31 # Load IRF-1 with 1024.
AIR 0, 1
LDA 2, 0, 31
AIR 2, 1
MLT 0, 2
STR 1, 0, 0
LDX 1, 0
LDA 1, 0, 0
STR 1, 0, 0
CHK 0, 2 # Load program loop, check status
JNE 0, 1, 10
IN 0, 2 # load program entry point
STR 0, 1, 22
CHK 0, 2 # load program
JNE 0, 1, 22, 1
IN 0, 2
STR 0, 1, 23, 1 # Store program
LDR 0, 1, 23
AIR 0, 1
STR 0, 1, 23 # update pointer
JMA 1, 14