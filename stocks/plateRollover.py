# coding:gbk
# Date: 2015-11-15
# Input: Null
# Output: list of increasing rate of plates

import tushare as ts
import sys
import json
from displayPlate import whichPlate
import pandas as pd

reload(sys)
sys.setdefaultencoding('utf-8')


def get_stocks_increasing():
	plate_f = file('plate.json')
	plate_info = json.load(plate_f)
	plate_f.close

	stock_num = len(plate_info)

	# get a list to store plate category name
	stock_list = list()

	day_num = 3

	print "start..."

	for i in range(0,stock_num):
		a = divmod(i,100)
		if a[1] == 0:
			print i
		#get increasing rate
	 	code = plate_info[i]['code']
	 	stock_hist_data = ts.get_hist_data(code).tail(day_num)

	 	start_price = stock_hist_data.close[0]
		end_price = stock_hist_data.close[2]

		increaseing_rate = (end_price - start_price) / start_price

	 	# structure of stock_list is [code, c_name , increasing_rate]
	 	stock_list.append( (code,plate_info[i]['c_name'],increaseing_rate) )

	print "End downloading..."

	stock_DataFrame = pd.DataFrame(stock_list)
	stock_DataFrame.to_json('stock_increaseing.json',orient='records')


stock_increasing_f = file('stock_increaseing.json')
# increasing_info : (code, c_name, increasing_rate)
increasing_info = json.load(stock_increasing_f)
stock_increasing_f.close

stock_num = len(increasing_info)
# store the plate_information as the following structure:
# plate_roll_info : [plate_name, increasing_rate_total, num_plate_stocks, incresing_rate_average]


plate_list = list()
plate_roll_info = list()

for i in range(0,stock_num):
	if increasing_info[i]['1'] not in plate_list:
		plate_list.append( increasing_info[i]['1'] )
		plate_roll_info.append( (increasing_info[i]['1'],0,0,0) )

plate_num = len(plate_list)

#compute the increasing rate of plates
# print ""
# print "Start plate increasing rate computing..."

for i in range(0,plate_num):
	for j in range(0,stock_num):
		if increasing_info[j]['1'] == plate_list[i]:
			increasing_rate_total = plate_roll_info[i][1] + increasing_info[j]['2']
			num_plate_stocks = plate_roll_info[i][2] + 1
			plate_roll_info[i] = (increasing_info[j]['1'],increasing_rate_total,num_plate_stocks,0)

print "plate_name" + " " + "increasing_rate"
for i in range(0,plate_num):
	increasing_rate_average = plate_roll_info[i][1] / plate_roll_info[i][2]
	plate_roll_info[i] = (plate_roll_info[i][0], plate_roll_info[i][1], plate_roll_info[i][2], increasing_rate_average)

	print plate_roll_info[i][0].decode('utf-8').encode('gbk') + ' ' \
			+ str(plate_roll_info[i][3]).decode('utf-8').encode('gbk')
	#print plate_list[i].decode('utf-8').encode('gbk')
