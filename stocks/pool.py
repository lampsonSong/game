

import json
import pandas as pd
from multiprocessing import Pool
import tushare as ts
import math

stock_list = []
plate_info = []

# download
def get_stocks_increasing(index):
	day_num = 3

	code = plate_info[index]['code']
	
	try:
	 	stock_hist_data = ts.get_hist_data(code).tail(day_num)
	 	start_price = stock_hist_data.close[0]
		end_price = stock_hist_data.close[2]

		increaseing_rate = (end_price - start_price) / start_price
		
		# structure of stock_list is [code, c_name , increasing_rate]
	 	stock_list.append( (code,plate_info[index]['c_name'],increaseing_rate) )
	 	# return stock_list
	except Exception,e:
		# print i
		return None



########################## Test pool and thread  ##########################
def isprime(n):  
    """Returns True if n is prime and False otherwise"""  
    if not isinstance(n, int):  
        raise TypeError("argument passed to is_prime is not of 'int' type")  
    if n < 2:  
        return False  
    if n == 2:  
        return True  
    max = int(math.ceil(math.sqrt(n)))  
    i = 2  
    while i <= max:  
        if n % i == 0:  
            return False  
        i += 1  
    return True  
  
def sum_primes(l):  
    # """Calculates sum of all primes below given integer n"""
    if isprime(l):
    	l + 100 
##############################################################################



#
# entrance of the program
#
if __name__ == '__main__':

	#open plate information
	plate_f = file('plate.json')
	plate_info = json.load(plate_f)
	plate_f.close 

	stock_num = len(plate_info)

	l = range(0,stock_num)
	# l = xrange(0,28*50000)

	pool = Pool(2)
	pool.map(get_stocks_increasing,l)
	# pool.map(sum_primes,l)
	pool.close()
	pool.join()	

	
	# stock_DataFrame = pd.DataFrame(out)
	# stock_DataFrame.to_json('stock_increaseing.json',orient='records')