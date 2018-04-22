from firebase import firebase
from pyfcm import FCMNotification
import pytz
from datetime import datetime
from subprocess import call
import os
import time
import serial
import math

temp = -1000
while True:

	print("\033[1;34;40mReading Data through the Sensor...")
	ser = serial.Serial('/dev/ttyACM0', 9600)
	s = [0]

	read_serial = ser.readline()
	read_serial = ser.readline()
	data = read_serial.split('.')
	read_serial_int = int(data[0])
	if(read_serial_int > -40 and read_serial_int < 100):
		print("\033[1;32;40mData recieved successfully through the sensor.")
		print('{}{}'.format("Temperature Value is ", read_serial_int))

		if (read_serial_int != temp):

			print("\033[1;33;40mTemperature changed since last measurement.")

			temp = read_serial_int


			f = open('DHTData.dat', 'w')

			f.write(data[0])

			f.close()


			push_service = FCMNotification(api_key="AAAAHQD3d0A:APA91bExIuKtmo93naG2qULEt8VK4i5AG-Bq-hSsDzwQOrvu7q8kVKwBsYF8lNlu69DraSdlUtT09cr6Oa1PL97FERpBhYO5vDa1VUuKSadJ2BCo_GrxgXazBg68_kd0KN_tKiK30O5c")
			firebase = firebase.FirebaseApplication('https://raspnotifier1311.firebaseio.com',None)
			
			print("\033[1;34;40mEncrypting Data...")

			aesOutput = call(["./main", "args", "to", "main"])
			ft = open('message.dat', 'r')
			file_data = ft.read()
			ft.close()
			os.remove('message.dat')

			print("\033[1;33;40mEncryption completed.")
			print("\033[1;37;40mEncrypted Data is " + file_data)
			print("\033[1;34;40mSending encrypted data.")
			if(read_serial_int < 20):
				key = "12312"
				message = "Cold"
				date = datetime.now()
				utc = pytz.timezone('UTC')
				aware_date = utc.localize(date)
				india = pytz.timezone('Asia/Kolkata')
				india_date = aware_date.astimezone(india)
				date = str(india_date.strftime("%a %b %d %H:%M:%S %Y"))
			elif(read_serial_int > 40):
				key = "12312"
				message = "Hot"
				date = datetime.now()
				utc = pytz.timezone('UTC')
				aware_date = utc.localize(date)
				india = pytz.timezone('Asia/Kolkata')
				india_date = aware_date.astimezone(india)
				date = str(india_date.strftime("%a %b %d %H:%M:%S %Y"))
			else:
				key = "12312"
				message = "Normal"
				date = datetime.now()
				utc = pytz.timezone('UTC')
				aware_date = utc.localize(date)
				india = pytz.timezone('Asia/Kolkata')
				india_date = aware_date.astimezone(india)
				date = str(india_date.strftime("%a %b %d %H:%M:%S %Y"))
		#print(date) # Wed Nov 11 08:00:00 2015

	#fields that are in database table - temperature,message,date,key
			data = {'temperature':file_data,'message':message,'date':date,'key':key}
	##Below command post the data on the firebase database.
			postdata = firebase.post('/data',data)
		#print(postdata)
			message_title = "Temperature Change"
			message_body = "Have a look"
			data_message = {
		    	"Nick" : "Mario",
	 	   		"body" : "great match!",
	 	   		"Room" : "PortugalVSDenmark"
			}

	#Command is used to call a c or c++ program. Before this compile the c or c++ program like this "g++ -Wall aes.cpp -o aes" folder should be same.


	##below commands sends a notification on android device
			if((read_serial_int < 20) or (read_serial_int > 40)):
				
				print("\033[1;31;40mTemperature out of stable range. Sending Notification Alert !!!")

				notification = push_service.notify_topic_subscribers(topic_name="news", message_title=message_title,message_body=message_body,data_message=data_message,sound="True")


			print("\033[1;32;40mData successfully sent.")
			print("\033[1;34;40mSensor put on hold.")
			time.sleep(100)
			print("\033[1;32;40mActivating Sensor Again.")

		else:
			print("\033[1;36;40mNo change in temperature.")
			print("\033[1;34;40mSensor put on hold.")
			time.sleep(100)
			print("\033[1;32;40mActivating Sensor Again.")	
	
	else:
		print("\033[1;31;40mError in recieveing data. Trying Again...")	

	
	 
