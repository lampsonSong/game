# coding:utf-8
# Date : 2015 - 11 - 14
# Input: stock plate
# Output: active stock list

import tushare as ts 
import os
import json
from plate_list import plate_names


#
# step 1: get all industry data and save it to an excel file
#

fileExists = os.path.exists('plate.json')

if not fileExists:
	plate_info = ts.get_industry_classified()
	plate_info.to_json('plate.json',orient='records')

f = file('plate.json')
plate_info = json.load(f)
f.close

#
# step 2: pick out '金融行业' stocks, and compute the increasing rate
#

# number of all stocks
stock_num = len(plate_info)

###                        ###
###   change the target    ###
###                        ###
# target = plate_names[2]
target = "食品行业"


stock_list = list();
day_num = 30

for i in range(0,stock_num):
	if plate_info[i]['c_name'].encode('utf-8') == target:
	 	# try:
		 	#get increasing rate
	 	code = plate_info[i]['code']
	 	stock_hist_data = ts.get_hist_data(code).tail(day_num)

	 	start_price = stock_hist_data.close[0]
		end_price = stock_hist_data.close[day_num-1]

		increaseing_rate = (end_price - start_price) / start_price

	 	# structure is [code, name , increasing_rate]
	 	stock_list.append( (code,plate_info[i]['name'],increaseing_rate) )
	 	# except Exception,e:
			# print i
			

	 	# print ind
		# print plate_info[i]['code'].decode('utf-8').encode('gbk') + ' ' + plate_info[i]['name'].decode('utf-8').encode('gbk')

selected_num = len(stock_list)



#
# step 3: sort the stocks and display
#

out = sorted(stock_list,key=lambda s : s[2], reverse = 1)
for i in range(0,selected_num):
	print str(out[i][0]).encode('utf-8') + ' ' \
			+ ' ' + out[i][1].encode('utf-8') + ' ' \
			+ str(out[i][2]).encode('utf-8')
