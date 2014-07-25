import tempfile
import sys

def modify_file(filename):

      #Create temporary file read/write
      t = tempfile.NamedTemporaryFile(mode="r+")

      #Open input file read-only
      i = open(filename, 'r')

      #Copy input file to temporary file, modifying as we go
      for line in i:
           t.write(line[:-4]+"\n")

      i.close() #Close input file

      t.seek(0) #Rewind temporary file to beginning

      o = open(filename, "w")  #Reopen input file writable

      #Overwriting original file with temporary file contents          
      for line in t:
           o.write(line)  

      t.close() #Close temporary file, will cause it to be deleted


yourpath = '/home/gowri/Rfiles/data_dump/CoOccuringAnalysis/src/window_based_analysis/new_output'

import os
for root, dirs, files in os.walk(yourpath, topdown=False):
    for name in files:
	modify_file(name)
