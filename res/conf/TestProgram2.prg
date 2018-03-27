34 0 0
0
0
0
0
32				# Paging
64				# Paging
96				# Paging
128				# Paging
160				# Paging
192				# Paging
224				# Paging
256				# Paging
288				# Paging
320				# Paging
352				# Paging
500				# Beginning of the paragraph
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Last Page
64				# Next Page
LDX 1, 31		# EntryPoint, #2
LDX 1, 6		# Initialize IR1
CHK 0, 2		# Check if card is ready, #4
JNE 0, 1, 4		# Check if card is ready
LDR 0, 0, 17	
STR 0, 1, 28	# Store where we are looking.
LDR 1, 1, 30	# Load count
JZ 1, 1, 23		# Loop, #9
IN 0, 2			# Read Character
JZ 0, 1, 23		# Unexpected End of File
OUT 0, 1		# Output the file.
STR 0, 1, 28, 1	# Save the Paragraph.
LDR 1, 1, 28	# Update the pointer
AIR 1, 1		# Update the pointer
STR 1, 1, 28	# Update the pointer
SMR 0, 1, 29	# Check if it's a sentence.
JNE 0, 1, 9		# Check if it's a sentence.
LDR 0, 1, 30	# Update sentence count
SIR 0, 1		# Update sentence count
STR 0, 1, 30	# Update sentence count
JMA 1, 9		# Jump, Also end of loop, #22
HLT				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Current Saving point, #28
46				# '.', #29
6				# Count of sentences to be loaded, #30
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
