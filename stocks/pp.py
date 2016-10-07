

import json
import pandas as pd
from multiprocessing import Pool
import pp
import sys

stock_list = []
plate_info = []

# download
def get_stocks_increasing(index):
	day_num = 3

	code = plate_info[index]['code']
	
	try:
	 	stock_hist_data = tushare.get_hist_data(code).tail(day_num)
	 	start_price = stock_hist_data.close[0]
		end_price = stock_hist_data.close[2]

		increaseing_rate = (end_price - start_price) / start_price
		
		# structure of stock_list is [code, c_name , increasing_rate]
	 	stock_list.append( (code,plate_info[index]['c_name'],increaseing_rate) )
	 	return stock_list
	except Exception,e:
		# print i
		return None
#
# entrance of the program
#
if __name__ == '__main__':

	#open plate information
	plate_f = file('plate.json')
	plate_info = json.load(plate_f)
	plate_f.close 

	stock_num = len(plate_info)


	ppservers = ()
	if len(sys.argv) > 1:
	    ncpus = int(sys.argv[1])
	    # Creates jobserver with ncpus workers
	    job_server = pp.Server(ncpus, ppservers=ppservers)
	else:
	    # Creates jobserver with automatically detected number of workers
	    job_server = pp.Server(ppservers=ppservers)

	print "Starting pp with", job_server.get_ncpus(), "workers"

	
	# stock_DataFrame = pd.DataFrame(out)
	# stock_DataFrame.to_json('stock_increaseing.json',orient='records')