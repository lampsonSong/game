
import json
import threading
import math
import tushare as ts
import pandas as pd

stock_list = []
plate_info = []

# download
def get_stocks_increasing(start,end):
	day_num = 3
	for i in range(start,end):
		code = plate_info[i]['code']
		
		try:
		 	stock_hist_data = ts.get_hist_data(code).tail(day_num)
		 	start_price = stock_hist_data.close[0]
			end_price = stock_hist_data.close[2]

			increaseing_rate = (end_price - start_price) / start_price
			# structure of stock_list is [code, c_name , increasing_rate]
		 	stock_list.append( (code,plate_info[i]['c_name'],increaseing_rate) )
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
  
def sum_primes(start,end):  
    # """Calculates sum of all primes below given integer n"""  
    return sum([x for x in xrange(start,end) if isprime(x)])  
##############################################################################



if __name__ == '__main__':

	#open plate information
	plate_f = file('plate.json')
	plate_info = json.load(plate_f)
	plate_f.close 

	stock_num = len(plate_info)

	each_stock_num = 100

	[thread_num,division] = divmod(stock_num,each_stock_num)
	if not division == 0:
		thread_num = thread_num + 1


	# initialize threads
	threads = []
	for i in range(0,thread_num):
		# range
		start = i*each_stock_num
		end = (i+1) * each_stock_num

		if end > stock_num:
			end = stock_num

		t = threading.Thread( target=get_stocks_increasing,args=(start,end) )
		# para = 100000
		# t = threading.Thread( target=sum_primes,args=(i*50000,(i+1)*50000) )
		threads.append(t)

	# start threads
	for t in threads:
		t.setDaemon(True)
		t.start()

	for t in threads:
		t.join()

	print len(stock_list)
	stock_DataFrame = pd.DataFrame(stock_list)
	stock_DataFrame.to_json('stock_increaseing.json',orient='records')
