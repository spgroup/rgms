import os
basename="form.gsp"
newname="_form.gsp"
for dirpath, dirs, files in os.walk(os.getcwd()):
    for filename in files:
    	if filename==basename:
			filepath = os.path.join(dirpath, filename)
			new_filepath = os.path.join(dirpath, newname)
			try:
				os.rename(filepath, new_filepath)
			except OSError, ex:
				print >>sys.stderr, "Error renaming '%s': %s"  % (filepath, ex.strerror)