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
0				# Word count
1				# Sentence count
0				# Current searching in word
0				# Current output in word
0				# Reserved
0				# Reserved
46				# char '.'
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
JMA 1, 2		# Jump to next page.
0				# Reserved for page ending 0
64				# Last page
128				# Next page
LDX 2, 1		# Page shift
LDX 2, 1		# Page shift, IR1 = This page, IR2 = Next page.
LDX 3, 31
LDX 3, 10		# IR3 = 160
LDR 1, 0, 17, 1	# Beginning of word search: Find the beginning of a word, #6
JSR 3, 2		# Func Call.
JNE 0, 1, 6		# loop
LDR 2, 0, 20 	# Initialize pointer, #9
STR 2, 0, 24	# Initialize pointer
SMR 1, 0, 24, 1	# Check if the character match, #11
JNE	0, 2, 7		# Check if the character match.
LDR 0, 0, 17	# word matches, move pointers ahead.
AIR 0, 1		# Paragraph pointer + 1
STR 0, 0, 17	# Paragraph pointer + 1
SMR 0, 0, 19	# Check if endable
JZ 0, 3, 26		# Reach the end of paragraph
LDR 0, 0, 24	# Word progress + 1
AIR 0, 1		# Word progress + 1
STR 0, 0, 24	# Word progress + 1
SMR 0, 0, 21	# Check if new word ends
JZ 0, 2, 4		# 
LDR 1, 0, 17, 1	# Check if paragraph word ends
JSR 3, 2		# Check if paragraph word ends
JNE 0, 1, 27	# 
JMA 1, 11		# Both not ends, continue comparing.
LDR 0, 0, 17	# Paragraph pointer + 1, #27
AIR 0, 1		# Paragraph pointer + 1
STR 0, 0, 17	# Paragraph pointer + 1
JMA 2, 2		# Word continues, while paragraph ends, not an answer. 
0				# Reserved for page ending 0
96				# Last page
160				# Next page
SMR 0, 0, 19	# Check if endable
JZ 0, 3, 26		# Reach the end of paragraph
JMA 1, 6		# Word continues, while paragraph ends, not an answer. 
LDR	1, 0, 17, 1	# Check if paragraph word ends, #5
JSR 3, 2		# Check if paragraph word ends
JNE 0, 2, 12	# Reserved
LDR 1, 0, 17, 1	# Paragraph Word continues, while word ends, not an answer, skip word, #8
JSR 3, 2		# Reserved
JZ 0, 2, 14		# Reserved
JMA 1, 9		# Back to compare
JSR 3, 24		# Both word ends together, output info, #12
JMA 1, 6		# Reserved
LDR 0, 0, 17	# Reserved, #14
AIR 0, 1		# Reserved
STR 0, 0, 17	# Reserved
JMA 2, 8		# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
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
0
196
SMR 1, 0, 30	# Check if end, Skip ' ', #2
JNE 1, 3, 13	# Skip ' '
LDR 0, 0, 22	# Word count + 1
AIR 0, 1		# Word count + 1
STR 0, 0, 22	# Word count + 1
LDR 0, 0, 17	# Pointer + 1, #7
AIR 0, 1		# Pointer + 1
STR 0, 0, 17	# Pointer + 1
SMR 0, 0, 19	# Check if endable
JZ 0, 3, 26		# Reach the end of paragraph
RFS 1			# Word ends
AMR 1, 0, 30	# Not ' ', #13
SMR 1, 0, 28	# Skip '.'
JNE 1, 3, 22	# Skip '.'
LDR 0, 0, 23	# Sentence count + 1
AIR 0, 1		# Sentence count + 1
STR 0, 0, 23	# Sentence count + 1
LDA 0, 0, 0		# Reset word count
STR 0, 0, 22	# Reset word count
JMA 3, 7		# To pointer + 1
AMR 1, 0, 28	# Not '.', #22
RFS 0			# Is word
LDX 3, 1		# Output information, #24
JMA 3, 2
HLT				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved
0				# Reserved for page ending 0
160				# Paging
0				# Reserved
LDR 2, 0, 20	# Output information, #2
STR 2, 0, 25	# Reserved
SMR 2, 0, 21	# Reserved
JZ 2, 3, 11		# Reserved
LDR 2, 0, 25, 1	# Reserved
OUT 2, 1		# Reserved
LDR 2, 0, 25	# Reserved
AIR 2, 1		# Reserved
JMA 3, 3		# Reserved
LDR 2, 0, 23	# Print sentence number, #11
AIR 2, 24		# Reserved
AIR 2, 24		# Reserved
OUT 2, 1		# Reserved
LDR 2, 0, 22	# Print word number, #11
AIR 2, 24		# Reserved
AIR 2, 24		# Reserved
OUT 2, 1		# Reserved
LDX 3, 0		# Revert changes on IRF
RFS 0			# Return
