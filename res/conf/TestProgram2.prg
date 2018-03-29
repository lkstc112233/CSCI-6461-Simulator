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
0				# Where the paragraph ends.
400				# Beginning of the word taken
400				# Where the user input word ends.
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
13				# char '\n'
32				# char ' '
0				# Reserved for page ending 0
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
LDX 1, 1		# Page shift
LDX 1, 1		# Page shift
JMA 2, 16		# To next page
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
IN 0, 0			# Cont. of main, loop begins, take a word from user
JZ 0, 2, 16		# Input
SMR 0, 0, 29	# Check if it's a '\n'
JZ 0, 2, 30		# Check if it's a '\n'
AMR 0, 0, 29	# Check if it's a '\n'
SMR 0, 0, 30	# Check if it's a ' '
JZ 0, 2, 30		# Check if it's a ' '
AMR 0, 0, 30	# Check if it's a ' '
STR 0, 0, 21, 1	# Store the character
OUT 0, 1		# Output the character
LDR 0, 0, 21	# Update the pointer
AIR 0, 1		# Update the pointer
STR 0, 0, 21	# Update the pointer
JMA 2, 16		# Loop
HLT				# Reserved
0				# Reserved for page ending 0
64				# Last page
128				# Next page
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
