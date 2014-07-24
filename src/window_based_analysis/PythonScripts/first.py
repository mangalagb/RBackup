yourpath = '/home/gowri/Rfiles/data_dump/CoOccuringAnalysis/src/window_based_analysis/new_output'

import os
for root, dirs, files in os.walk(yourpath, topdown=False):
    for name in files:
	fo = open(name, "r+")
	for line in fo:
		print line[:-4] + '\n'
 
