# Date : 2015-11-14
# Input: no Input
# Output: today date and the day one month ago

import datetime

def getDate():
	today = datetime.date.today();

	start =  str(today.year) + '-' + str(today.month) + '-' + str(today.day)
	if today.month > 1:
		end = str(today.year) + '-' + str(today.month-1) + '-' + str(today.day)
	else:
		end = str(today.year-1) + '-' + str(12) + '-' + str(today.day)

	duration = [start,end]
	return duration

def getToday():
	today = datetime.date.today();

	today_date =  str(today.year) + '-' + str(today.month) + '-' + str(today.day)
	return today_date