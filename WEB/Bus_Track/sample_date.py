# Python program to get
# current date


# Import date class from datetime module
from datetime import datetime 
now = datetime.now()


# Returns the current local date
date_time = now.strftime("%Y-%m")
print("date and time:",date_time)
