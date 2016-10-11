#
#
#
#		Date 		: 		2016-10-11
#		Author 		: 		lampson
#		Input 		:  		1-100000,300001-400000, 600001-700000
#		Output 		: 		all existed stocks
#		Objective 	: 		test the difference between coroutine and linear running
#
#

from getDate import getToday
import tushare as ts
import pandas as pd
import pdb
import time


def crawl_data_00():
	today = getToday()

	for f_code in xrange(1,100):
		num_str = "%06d" % f_code
		code_str = str(num_str)

		data_t = ts.get_hist_data(code_str,start=today,end=today)
		
		if( data_t is not None):
			yield code_str
			ts.get_hist_data(code_str)
	# pdb.set_trace()
	# print data_t


if __name__ == "__main__":
	

	# crawlData(code)
	time1 = time.time()

	# crawl_data_00()

	res = crawl_data_00()
	for i in res:
		print i
	# pdb.set_trace()

	time2 = time.time()

	print time2 - time1
	# print ts.get_hist_data(code,start=start_date,end=end_date)




#######################################################################
#Conclusion:
#	when the operation relies on network transformation,
#	the coroutine and linear are the same
#
#######################################################################