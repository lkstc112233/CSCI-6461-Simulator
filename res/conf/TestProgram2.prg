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
255				# Mask for lower 8 bits.
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
LDX 2, 31		
LDX 2, 7		# Initialize IR2
LDR 2, 0, 18	# Load mask.
CHK 0, 2		# Check if card is ready, #7
JNE 0, 1, 7		# Check if card is ready
LDR 0, 0, 17	
STR 0, 1, 28	# Store where we are saving.
LDR 1, 1, 30	# Load sentence count
JZ 1, 1, 22		# Loop, #12
IN 0, 2			# Read Character
JZ 0, 1, 22		# Unexpected End of File
STR 0, 1, 27	# Save input for later use.
SRC 0, 8, 0, 1	# Get higher 8 bits.
JSR 2, 2
LDR 0, 1, 27	# Load back, output, and store lower 8 bits.
AND 0, 2
JSR 2, 2
JMA 1, 12		# Jump, Also end of loop, #21
LDR 0, 1, 28	# Save array ending point.
STR 0, 0, 19	# Save array ending point.
HLT				# Stop for now. Later JMA goes from here.
NOP
NOP
0				# Reserved for input. #27
0				# Current Saving point, #28
46				# '.', #29
6				# Count of sentences to be loaded, #30
0				# Reserved for page ending 0
32				# Last Page
96				# Next Page
OUT 0, 1		# Output the content to screen, #2
STR 0, 1, 28, 1	# Save the Paragraph.
LDR 1, 1, 28	# Update the pointer
AIR 1, 1		# Update the pointer
STR 1, 1, 28	# Update the pointer
SMR 0, 1, 29	# Check if it's a sentence.
JNE 0, 2, 14	# Check if it's a sentence.
AIR 0, 13		# Output a '\n'
OUT 0, 1				
LDR 0, 1, 30	# Update sentence count
SIR 0, 1		# Update sentence count
STR 0, 1, 30	# Update sentence count
RFS 1			# Is a sentence, sentence count updated, #14
RFS 0			# Not a sentence, #15
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
0				# Reserved for page ending 0
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
