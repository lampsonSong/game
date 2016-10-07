#coding:utf-8

import json
import tushare as ts
from plate_list import plate_names

plate_f = file('stock_data/each_stock/600051.json')
plate_info = json.load(plate_f)
plate_f.close 

# stock_num = len(plate_info)

# for i in range(0,stock_num):
# 	print plate_info[i]['0'].encode('utf-8') + " " + plate_info[i]['1'].encode('utf-8')
# 	print plate_info[i]['2']
# print stock_num

# import pp
# import sys

# # job_server = pp.Server()
# ppservers = ()

# if len(sys.argv) > 1:
#     ncpus = int(sys.argv[1])
#     # Creates jobserver with ncpus workers
#     job_server = pp.Server(ncpus, ppservers=ppservers)
# else:
#     # Creates jobserver with automatically detected number of workers
#     job_server = pp.Server(ppservers=ppservers)

# print "Starting pp with", job_server.get_ncpus(), "workers"

print plate_info

# for i in range(0,stock_num):
# 	print i
# 	ts.get_hist_data(plate_info[i]['code'])
