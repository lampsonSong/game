# coding: utf-8
# Date: 2015-11-23
# Author: lampson
# Output: stock data

import tushare as ts
from getDate import getToday
import os
import json
from multiprocessing import Pool
import pandas as pd

basic_file = 'stock_data/basic_info.json'
plate_file = 'stock_data/plate_info.json'
concept_file = 'stock_data/concept_info.json'
path = 'stock_data/each_stock/'

# global variation
plate_info = []
concept_info = []
basic_info = []
global code

# multi-processing function
def mapConcepts(concept_index):
	if code == concept_info[concept_index]['code']:
		basic_info.append(concept_info[concept_index]['c_name'].encode('utf-8'))
		print basic_info[0]


if not os.path.exists(plate_file) and os.path.exists(concept_file):
	#plate
	plate = ts.get_industry_classified()
	plate.to_json(plate_file,orient='records')
	#concept
	concept = ts.get_concept_classified()
	concept.to_json(concept_file,orient='records')

f = file(plate_file)
plate_info = json.load(f)
f.close()

f = file(concept_file)
concept_info = json.load(f)
f.close()


# map code[plate & concept]

for i in range(0,len(plate_info)):
	code = plate_info[i]['code']

	temp_info = ts.get_hist_data(code)

	file_path = path + code  + '.json'
	temp_info.to_json(file_path,orient='records')

	# concept_index = range( 0,len(concept_info) )

	# pool = Pool(4)

	# pool.map(mapConcepts,concept_index)
	# pool.close()
	# pool.join()

	time = [getToday()]
	u_date = pd.DataFrame(time)
	u_date.to_json('update_date.json')



