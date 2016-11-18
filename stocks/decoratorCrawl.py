#
#
#
#		Date 				: 		2016-11-18
#		Author 				: 		lampson
#		Objective 			: 		Test the function of decorator in python
#		Implementation		: 		Input a date, and then crawl a stock data
#
#
#

# So our objective is not implementable

def inputCode(fn):

	c = 12
	print c

	def wrapper():
		# input_code = raw_input("Please input the code:\n")
		# return input_code
		print 'test'

	return fn
	

def inputDate(fn):
	b = 23
	print b
	def wrapper2():
		# input_date = raw_input("Please input the date:\n")
		print fn.__name__
		
	return fn

@inputCode
@inputDate
def crawData():
	a = 1
	print a
	print "test"


crawData()
# if __name__ == "__main__":
	# crawData()




# Some tips about decorator:
#
#		1. there must be a callable return in decorator
#		2. the return function in decorator determines which function to run
#		3. when there are multiple decorators, call them from bottom to up level
#		4. among the multiple wrapper functions and the original function, only one of them would be run 
#		5. it is not possible to get values back from decorators
# 		6. even the original function is not called, the decorator would still be run