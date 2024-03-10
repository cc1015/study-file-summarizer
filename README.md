This program traverses a given root directory and reads the .md files in the directory. It extracts only the headers and double-bracketed phrases from the .md files and writes the extracted text into a single destination file. The extracted information can be sorted by file creation time, modification time, or alphabetically by file name on the destination file.

The program takes in three command-line arguments:

1. A relative or absolute path to a folder (directory) of markdown files containing the notes you want to summarize. The directory may contain both markdown files as well as other folders.  And these other folders might contain more markdown files and/or other folders.
2. Ordering Flag - A flag to indicate how the summary document should be organized
    1. `filename` - organize the content in the output summary file in order based on the alphabetically sorted source file names. 
    2. `created` - organize the content in the output summary file in order based on the create-date time stamp of the source file. 
    3. `modified` - organize the content in the output summary file in order based on the the last modified time stamp of the source file. 
4. An output path (relative or absolute) and filename of where to write the study guide your program generates. Based on the input file processing order dictated by command-line argument #2 above, the output file will contain:
    1. all headings in the order they appear in the file (properly nested). 
        1. Except for the very first line of the study guide file, all headings should be preceded with a blank line. 
    2. all important phrases identified with the `[[]]` properly nested under the heading in which it appears in the original input file. 
        1. In the output file, do not output the brackets themselves. 
        2. Each bracketed phrase should be output as a single bullet point (`-`) in the output file. 
        3. Bracketed phrases or sentences may span multiple lines of the input file. 
        4. A single line of the input file may contain multiple bracketed important phrases; each should be output as a separate bullet point in the study guide.
