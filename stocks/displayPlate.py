# coding:utf-8
# Date  : 2015 - 11 - 15
# Input : a stock code
# Output: display plate and concept information

import tushare as ts
import os
import json


# def showPlates():
# 	plates = list()

# 	print plates




# get all stocks data and classify the industry
def updatePlates():

	plate_info = ts.get_industry_classified()
	plate_info.to_json('plate.json',orient='records')




# get all stocks data and classify the concept
def updateConcepts():

	concept_info = ts.get_concept_classified()
	concept_info.to_json('concept.json',orient='records')

	


# concept_list = updateConcepts()

# concept_num = len(concept_list)
# for i in range(0,concept_num):
# 	print concept_list[i].decode('utf-8').encode('gbk')
# print concept_num

def whichPlate(stock_code):
	# # get plate of all stocks
	plate_f = file('plate.json')
	plate_info = json.load(plate_f)
	plate_f.close

	stock_num = len(plate_info)

	for i in range(0,stock_num):
		plate_code = plate_info[i]['code']
		if stock_code == plate_code:
			return plate_info[i]['c_name']
# stock_code = '002673'

# # get plate of all stocks
# plate_f = file('plate.json')
# plate_info = json.load(plate_f)
# plate_f.close


# # get concept of all stocks
# concept_f = file('concept.json')
# concept_info = json.load(concept_f)
# concept_f.close

# stock_num = len(concept_info)

# print "The concept of target stock are: "
# for i in range(0,stock_num):
# 	concept_code = concept_info[i]['code']
# 	if stock_code == concept_code:
# 		print concept_info[i]['c_name'].decode('utf-8').encode('gbk')
		
# print ""
# print "The industry of target stock is:"

# stock_num = len(plate_info)

# for i in range(0,stock_num):
# 	plate_code = plate_info[i]['code']
# 	if stock_code == plate_code:
# 		print plate_info[i]['c_name'].decode('utf-8').encode('gbk')