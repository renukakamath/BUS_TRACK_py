from flask import *
from database import *
import demjson
import datetime 
import uuid
import qrcode

api=Blueprint('api',__name__)

@api.route('/login')
def login():
	data={}
	username=request.args['username']
	password=request.args['password']
	q="select * from login where username='%s' and password='%s'"%(username,password)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	return str(data)


@api.route('/driverviestudentdetails')
def driverviestudentdetails():
	data={}
	lid=request.args['lid']
	q="SELECT * FROM `student` INNER JOIN `place` USING(place_id) INNER JOIN `routes` USING(route_id) INNER JOIN `bus` ON(`routes`.`route_id`=`bus`.`route_id`) INNER JOIN `driver` USING(bus_id) WHERE driver_id=(select driver_id from driver where login_id='%s')"%(lid)
	print(q)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	return str(data)


@api.route('/studentviewbus')
def studentviewbus():
	data={}
	lid=request.args['lid']
	# q="SELECT * FROM `student` INNER JOIN `bus` USING (route_id)  WHERE student_id=(SELECT student_id FROM student WHERE login_id='%s')"%(lid)
	q="SELECT * FROM `assign_bus` INNER JOIN `bus` USING (bus_id) INNER JOIN `buslocation` USING(bus_id) WHERE student_id=(select student_id from student where login_id='%s')"%(lid)
	print(q)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	return str(data)


@api.route('/studentviewamount')
def studentviewamount():
	data={}
	lid=request.args['lid']
	q="SELECT * FROM `assign_amount`  WHERE student_id=(select student_id from student where login_id='%s')"%(lid)
	print(q)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	return str(data)


@api.route('/usermakepayment')
def usermakepayment():
	data={}
	data['method']="usermakepayment"
	lid=request.args['lid']
	amount=request.args['amount']
	aid=request.args['aid']
	q="select * from assign_amount inner join payment USING(assign_id) where assign_id='%s'"%(aid)
	print(q)
	res=select(q)
	if res:
		data['status']="na"
	else:
		current_time = datetime.datetime.now() 
		print ("Year : ", end = "") 
		print (current_time.year) 
			
		print ("Month : ", end = "") 
		print (current_time.month) 

		dt="%"+str(current_time.year)+"-"+str(current_time.month)+"%"
		print("DT : ",dt)
		q="SELECT * FROM `payment` WHERE `assign_id`='%s' AND `date` LIKE '%s'"%(aid,dt)
		res=select(q)
		print(q)
		if res:
			data['status']="na"
		else:
			q="insert into payment values(null,'%s','%s',curdate())"%(aid,amount)
			insert(q)
			data['status']="success"
	return str(data)



@api.route('/studentviewseats')
def studentviewseats():
	data={}
	lid=request.args['lid']
	q="SELECT * FROM `student` INNER JOIN `bus` USING (route_id) INNER JOIN `buslocation` USING(bus_id) WHERE student_id=(select student_id from student where login_id='%s')"%(lid)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	return str(data)


@api.route('/updatepasslocation',methods=['get','post'])
def updatepasslocation():
	data={}

	latti=request.args['latti']
	longi=request.args['longi']
	logid=request.args['logid']
	q="select * from driver inner join bus USING(bus_id) where login_id='%s'"%(logid)
	res=select(q)
	if res:
		q="SELECT * FROM `buslocation` WHERE `bus_id`='%s'"%(res[0]['bus_id'])
		rs=select(q)
		if rs:
			q="update `buslocation` set `latitude`='%s',`longitude`='%s',location_date=curdate(),location_time=curtime() where `bus_id`='%s'"%(latti,longi,res[0]['bus_id'])
			
			update(q)
		else:
			q="INSERT INTO `buslocation` VALUES(NULL,'%s','%s','%s',CURDATE(),CURTIME())"%(res[0]['bus_id'],latti,longi)
			insert(q)
		data['status'] = 'success'
	else:
		data['status'] = 'failed'
	data['method'] = 'updatepasslocation'
	return str(data)




@api.route('/AndroidBarcodeQrExample')
def AndroidBarcodeQr():
	data={}
	lid=request.args['contents']
	from datetime import datetime 
	now = datetime.now()

	date_time = now.strftime("%Y-%m")
	print("date and time:",date_time)
	

	dt="%"+date_time+"%"
	print("DT : ",dt)

	q="SELECT * FROM `payment` WHERE `assign_id`=(SELECT `assign_id` FROM `assign_amount` WHERE `student_id`='%s' AND `date` LIKE '%s') AND `date` LIKE '%s'"%(lid,dt,dt)
	print(q)
	res=select(q)
	if res:
		q="SELECT * FROM `attendance` WHERE `student_id`='%s' AND `date`=CURDATE()"%(lid)
		res1=select(q)
		if res1:
			q="UPDATE `attendance` SET `out`=curtime() WHERE `attendance_id`='%s'"%(res1[0]['attendance_id'])
			update(q)
		else:
			q="INSERT INTO `attendance` VALUES(NULL,'%s',CURDATE(),curtime(),'NA')"%(lid)
			insert(q)
		data['status']="success"
	else:
		data['status']='Exp'
	return str(data)



@api.route('/View_my_qr')
def View_my_qr():
	data={}
	lid=request.args['lid']
	q="SELECT * FROM `student` WHERE `login_id`='%s'"%(lid)
	res=select(q)
	data['data']=res[0]['qr_code']
	data['status']="success"
	return str(data)



@api.route('/View_app_rating',methods=['get','post'])
def View_app_rating():
	data = {}

	loginid=request.args['loginid']
	
	
	q="SELECT * FROM `app_rating` WHERE `sender_id`='%s'"%(loginid)
	print(q)
	result=select(q)
	if result:
		data['status'] = 'success'
		data['data'] = result[0]['rate']
		data['data1'] = result[0]['review']
		
	else:
		data['status'] = 'failed'
	data['method'] = 'View_app_rating'
	return str(data)





@api.route('/App_rating',methods=['get','post'])
def App_rating():

	data={}

	ratings=request.args['rating']
	review=request.args['review']
	loginid=request.args['loginid']

	q="SELECT * FROM `app_rating` WHERE `sender_id`='%s'"%(loginid)
	res=select(q)
	if res:

		q="UPDATE `app_rating` SET `rate`='%s',`review`='%s',`date_time`=CURDATE() WHERE `sender_id`='%s'"%(review,ratings,loginid)
		update(q)
		data['method'] = 'App_rating'
	else:
		q="INSERT INTO `app_rating` VALUES(NULL,'%s','%s','%s',CURDATE())"%(loginid,ratings,review)
		print(q)
		id=insert(q)
		if id>0:
			data['status'] = 'success'
			
		else:
			data['status'] = 'failed'
		data['method'] = 'App_rating'
	return str(data)


@api.route('/Student_view_driver_details',methods=['get','post'])
def Student_view_driver_details():
	data = {}

	loginid=request.args['lid']
	
	
	q="SELECT *,CONCAT(`driver`.`firstname`,' ',`driver`.`lastname`) AS dname FROM `assign_bus` INNER JOIN `driver` USING(`bus_id`) WHERE `student_id`=(SELECT student_id FROM student WHERE login_id='%s')"%(loginid)
	print(q)
	result=select(q)
	if result:
		data['status'] = 'success'
		data['data'] = result[0]['dname']
		data['data1'] = result[0]['photo']
		data['data2'] = result[0]['phone']
		data['data3'] = result[0]['driver_id']
		
	else:
		data['status'] = 'failed'
	data['method'] = 'Student_view_driver_details'
	return str(data)


@api.route('/Student_view_late_information/',methods=['get','post'])
def Student_view_late_information():
	data = {}
	loginid=request.args['log_id']
	driver_id=request.args['driver_id']
	
	q="SELECT * FROM `late_information` WHERE `sender_id`='%s' AND `receiver_id`=(SELECT `login_id` FROM `driver` WHERE `driver_id`='%s') ORDER BY `date` DESC"%(loginid,driver_id)
	print(q)
	result=select(q)
	if result:
		data['status'] = 'success'
		data['data'] = result	
	else:
		data['status'] = 'failed'
	data['method'] = 'view'
	return str(data)


@api.route('/Student_late_information/',methods=['get','post'])
def Student_late_information():
	data = {}
	loginid=request.args['log_id']
	driver_id=request.args['driver_id']
	details=request.args['complaint']
	
	q="INSERT INTO `late_information` VALUES(NULL,'%s',(SELECT `login_id` FROM `driver` WHERE `driver_id`='%s'),'%s','NA',CURDATE())"%(loginid,driver_id,details)
	print(q)
	insert(q)
	data['status'] = 'success'
	
	data['method'] = 'send'
	return str(data)




@api.route('/Driver_view_late_request/',methods=['get','post'])
def Driver_view_late_request():
	data = {}
	loginid=request.args['log_id']
	
	q="SELECT *,CONCAT(`student`.`fname`,' ',`student`.`lname`) AS student_name FROM `late_information` INNER JOIN `student` ON `late_information`.`sender_id`=`student`.`login_id` WHERE  `receiver_id`='%s' ORDER BY `date` DESC"%(loginid)
	print(q)
	result=select(q)
	if result:
		data['status'] = 'success'
		data['data'] = result	
	else:
		data['status'] = 'failed'
	data['method'] = 'view'
	return str(data)


@api.route('/Driver_send_reply/',methods=['get','post'])
def Driver_send_reply():
	data = {}
	late_ids=request.args['late_ids']
	complaint=request.args['complaint']
	
	
	q="UPDATE `late_information` SET `reply`='%s' WHERE `late_id`='%s'"%(complaint,late_ids)
	print(q)
	update(q)
	data['status'] = 'success'
	
	data['method'] = 'send'
	return str(data)


@api.route('/Driver_view_attendance/',methods=['get','post'])
def Driver_view_attendance():
	data = {}
	loginid=request.args['log_id']
	
	q="SELECT *,CONCAT(`student`.`fname`,' ',`student`.`lname`) AS student_name FROM `student` INNER JOIN `place` USING(place_id) INNER JOIN `routes` USING(route_id) INNER JOIN `bus` ON(`routes`.`route_id`=`bus`.`route_id`) INNER JOIN `driver` USING(bus_id) INNER JOIN `attendance` USING(`student_id`) WHERE driver_id=(SELECT driver_id FROM driver WHERE login_id='%s') ORDER BY `date` DESC"%(loginid)
	print(q)
	result=select(q)
	if result:
		data['status'] = 'success'
		data['data'] = result	
	else:
		data['status'] = 'failed'
	data['method'] = 'view'
	return str(data)


@api.route('/Driver_view_notification/',methods=['get','post'])
def Driver_view_notification():
	data = {}
	loginid=request.args['log_id']
	
	q="SELECT * FROM `driver_notification` WHERE `driver_id`=(SELECT `driver_id` FROM `driver` WHERE `login_id`='%s') ORDER BY `date` DESC"%(loginid)
	print(q)
	result=select(q)
	if result:
		data['status'] = 'success'
		data['data'] = result	
	else:
		data['status'] = 'failed'
	data['method'] = 'view'
	return str(data)


@api.route('/Driver_send_notification/',methods=['get','post'])
def Driver_send_notification():
	data = {}
	log_id=request.args['log_id']
	complaint=request.args['complaint']

	q="INSERT INTO `driver_notification` VALUES(NULL,(SELECT `driver_id` FROM `driver` WHERE `login_id`='%s'),'%s',CURDATE())"%(log_id,complaint)
	print(q)
	insert(q)
	data['status'] = 'success'
	
	data['method'] = 'send'
	return str(data)


@api.route('/Student_view_notification/',methods=['get','post'])
def Student_view_notification():
	data = {}
	log_id=request.args['log_id']

	q="SELECT * FROM `driver_notification` INNER JOIN `driver` USING(`driver_id`) INNER JOIN `assign_bus` USING(`bus_id`) WHERE `student_id`=(SELECT student_id FROM student WHERE login_id='%s') order by date desc"%(log_id)
	print(q)
	res=select(q)
	data['status'] = 'success'
	data['data']=res
	
	data['method'] = 'view'
	return str(data)



@api.route('/view_place/',methods=['get','post'])
def view_place():
	data = {}

	q="select * from place"
	r=select(q)
	if r:
		data['status']="success"
		data['data']=r
	
	data['method'] = 'view_place'
	return str(data)

@api.route('/view_route/',methods=['get','post'])
def view_route():
	data = {}

	q="select * from routes"
	r=select(q)
	if r:
		data['status']="success"
		data['data']=r
	
	data['method'] = 'view_route'
	return str(data)

@api.route('/register/',methods=['get','post'])
def register():
	data = {}
	fname=request.args['fname']
	lname=request.args['lname']
	batch=request.args['batch']
	course=request.args['course']
	username=request.args['username']
	password=request.args['password']
	phone=request.args['phone']
	place_ids=request.args['place_ids']
	route_ids=request.args['route_ids']
	parent_number=request.args['parent_number']
	image1=request.files['image1']
	path1="static/students/"+str(uuid.uuid4())+".jpg"
	image1.save(path1)

	q="select * from login where username='%s'"%(username)
	res=select(q)
	if res:
		data['status']="duplicate"
	else:
		path="static/qr_code/"+str(uuid.uuid4())+".png"
		ql="insert into login values(null,'%s','%s','student')"%(username,password)
		id=insert(ql)
		qs="insert into student values(null,'%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','pending','%s')"%(id,route_ids,place_ids,fname,lname,course,batch,phone,parent_number,path,path1)
		r=insert(qs)

		s=qrcode.make(str(r))
		s.save(path)

		data['status']="Success"

	data['method'] = 'send'
	return str(data)


@api.route('/Parent_view_attendance/',methods=['get','post'])
def Parent_view_attendance():
	data = {}
	log_id=request.args['log_id']

	q="SELECT * FROM `attendance` WHERE `student_id`=(SELECT `student_id` FROM `student` WHERE `login_id`='%s') order by date desc"%(log_id)
	r=select(q)
	if r:
		data['status']="success"
		data['data']=r
	
	data['method'] = 'Parent_view_attendance'
	return str(data)



@api.route('/user_get_parent_number/',methods=['get','post'])
def user_get_parent_number():
	data = {}
	log_id=request.args['lid']

	q="SELECT * FROM `student` WHERE `login_id`='%s'"%(log_id)
	r=select(q)
	if r:
		data['status']="success"
		data['data']=r[0]['parent_number']
		data['data1']=r[0]['fname']+" "+r[0]['lname']
	
	data['method'] = 'user_get_parent_number'
	return str(data)



@api.route('/Student_profile/',methods=['get','post'])
def Student_profile():
	data = {}
	log_id=request.args['lid']

	q="SELECT * FROM `student` WHERE `login_id`='%s'"%(log_id)
	r=select(q)
	if r:
		data['status']="success"
		data['data']=r
	
	data['method'] = 'Student_profile'
	return str(data)


@api.route('/Student_qr_profile/',methods=['get','post'])
def Student_qr_profile():
	data = {}
	log_id=request.args['lid']
	contents=request.args['contents']

	q="SELECT * FROM `student` WHERE `student_id`='%s'"%(contents)
	r=select(q)
	if r:
		data['status']="success"
		data['data']=r
	
	data['method'] = 'Student_profile'
	return str(data)