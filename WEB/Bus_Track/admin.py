from flask import *
from database import *
import uuid
import qrcode
admin=Blueprint('admin',__name__)

@admin.route('adminhome')
def adminhome():
	return render_template('adminhome.html')




# @admin.route('/adminmanagestaff',methods=['get','post'])
# def adminmanagestaff():
# 	data={}
# 	if "register" in request.form:
# 		fna=request.form['f']
# 		lna=request.form['l']
# 		pla=request.form['pl']
# 		pho=request.form['ph']
# 		em=request.form['e']
# 		de=request.form['desg']
# 		uname=request.form['u']
# 		pwd=request.form['p']
# 		ql="insert into login values(null,'%s','%s','staff')"%(uname,pwd)
# 		rl=insert(ql)
# 		qs="insert into staff values(null,'%s','%s','%s','%s','%s','%s','%s')"%(rl,fna,lna,pla,pho,em,de)
# 		insert(qs)
# 		print(qs)
# 		flash("added successfully")
# 		return redirect(url_for('admin.adminmanagestaff'))
# 	if "action" in request.args:
# 		action=request.args['action']
# 		mid=request.args['mid']
# 		lid=request.args['lid']
# 	else:
# 		action=None
# 	if "update" in request.form:
# 		fna=request.form['f']
# 		lna=request.form['l']
# 		pla=request.form['pl']
# 		pho=request.form['ph']
# 		em=request.form['e']
# 		de=request.form['desg']

# 		q="update staff set  firstname='%s',lastname='%s',place='%s',phone='%s',email='%s',designation='%s' where staff_id='%s'"%(fna,lna,pla,pho,em,de,mid)
# 		r=update(q)
# 		flash("update successfully")
# 		return redirect(url_for('admin.adminmanagestaff'))
# 	if action=="update":
# 		q="select * from  staff where staff_id='%s'"%(mid)
# 		r=select(q)
# 		data['updateteacher']=r
# 	if action=="delete":
# 		q="delete from staff where login_id='%s'"%(lid)
# 		delete(q)
# 		q="delete from login where login_id='%s'"%(lid)
# 		delete(q)
# 		flash("delete successfully")
# 		return redirect(url_for('admin.adminmanagestaff'))
# 	q="select * from staff "
# 	r=select(q)
# 	data['teachers']=r
# 	return render_template('adminmanagestaff.html',data=data)








@admin.route('/adminviewattendance',methods=['get','post'])
def adminviewattendance():
	data={}
	student_id=request.args['mid']
	q="select * from attendance inner join student using(student_id)where student_id='%s'"%(student_id)
	r=select(q)
	data['attendance']=r
	return render_template('adminviewattendance.html',data=data)

@admin.route('/adminviewfeedback',methods=['get','post'])
def adminviewfeedback():
	data={}
	q="select * from feedback inner join student using(student_id)"
	r=select(q)
	data['view']=r
	return render_template('adminviewfeedback.html',data=data)



@admin.route('/adminviewstudent',methods=['get','post'])
def adminviewstudent():
	data={}
	q="select * from student "
	r=select(q)
	data['student']=r

	return render_template('adminviewstudent.html',data=data)




@admin.route('/adminmanageplace',methods=['get','post'])
def adminmanageplace():
	data={}
	q="select * from place"
	r=select(q)
	data['noti']=r
	if "add" in request.form:
		n=request.form['n']
		p=request.form['lati']
		l=request.form['longi']
		
		qs="insert into place values(null,'%s','%s','%s')"%(n,p,l)
		insert(qs)
		flash("added successfully")
		return redirect(url_for('admin.adminmanageplace'))
	if "action" in request.args:
		action=request.args['action']
		nid=request.args['nid']
	else:
		action=None
	if "update" in request.form:
		n=request.form['n']
		p=request.form['lati']
		l=request.form['longi']
		q="update place set place_name='%s',latitude='%s',longitude='%s' where place_id='%s'"%(n,p,l,nid)
		r=update(q)
		return redirect(url_for('admin.adminmanageplace'))
	if action=="update":
		q="select * from  place where place_id='%s'"%(nid)
		r=select(q)
		data['updatenoti']=r
	if action=="delete":
		q="delete from place where place_id='%s'"%(nid)
		delete(q)
		return redirect(url_for('admin.adminmanageplace'))
	return render_template('adminmanageplace.html',data=data)


@admin.route('/adminmanageroute',methods=['get','post'])
def adminmanageroute():
	data={}
	q="select * from routes"
	r=select(q)
	data['noti']=r
	q="select * from place"
	r=select(q)
	data['fromplace']=r
	q="select * from place"
	r=select(q)
	data['toplace']=r
	if "add" in request.form:
		n=request.form['n']
		fp=request.form['fp']
		tp=request.form['tp']
		# r=request.form['r']
			
		qs="insert into routes values(null,'%s','%s','%s','route')"%(fp,tp,n)
		insert(qs)
		flash("added successfully")
		return redirect(url_for('admin.adminmanageroute'))
	if "action" in request.args:
		action=request.args['action']
		nid=request.args['nid']
	else:
		action=None
	if "update" in request.form:
		n=request.form['n']
		fp=request.form['fp']
		tp=request.form['tp']
		q="update routes set from_place_id='%s',to_place_id='%s',route_name='%s' where route_id='%s'"%(fp,tp,n,nid)
		r=update(q)
		flash("update successfully")
		return redirect(url_for('admin.adminmanageroute'))
	if action=="update":
		q="select * from  routes where route_id='%s'"%(nid)
		r=select(q)
		data['updatenoti']=r
	if action=="delete":
		q="delete from routes where route_id='%s'"%(nid)
		delete(q)
		flash("delete successfully")
		return redirect(url_for('admin.adminmanageroute'))
	return render_template('adminmanageroute.html',data=data)



@admin.route('/adminviewroute',methods=['get','post'])
def adminviewroute():
	data={}
	q="select * from routes"
	r=select(q)
	data['noti']=r
	q="select * from place"
	r=select(q)
	data['fromplace']=r
	q="select * from place"
	r=select(q)
	data['toplace']=r
	if "action" in request.args:
		action=request.args['action']
		nid=request.args['nid']
	else:
		action=None
	if "update" in request.form:
		n=request.form['n']
		fp=request.form['fp']
		tp=request.form['tp']
		q="update routes set from_place_id='%s',to_place_id='%s',route_name='%s' where route_id='%s'"%(fp,tp,n,nid)
		r=update(q)
		flash("update successfully")
		return redirect(url_for('admin.adminviewroute'))
	if action=="update":
		q="select * from  routes where route_id='%s'"%(nid)
		r=select(q)
		data['updatenoti']=r
	if action=="delete":
		q="delete from routes where route_id='%s'"%(nid)
		delete(q)
		flash("delete successfully")
		return redirect(url_for('admin.adminviewroute'))
	return render_template('adminviewroute.html',data=data)


@admin.route('/adminviewstudents',methods=['get','post'])
def adminviewstudents():
	data={}

	qq="SELECT * FROM student  inner join place using(place_id) inner join login using(login_id) "
	res=select(qq)
	data['student']=res
	if "action" in request.args:
		action=request.args['action']
		mid=request.args['mid']
		lid=request.args['lid']
	else:
		action=None

	if "update" in request.form:
		pp=request.form['pp']
		r=request.form['r']
		ccid=request.form['cc_id']
		fna=request.form['f']
		lna=request.form['l']
		pla=request.form['pl']
		pho=request.form['ph']
		# em=request.form['e']
		# g=request.form['g']
		# i=request.files['i']
		# path="static/"+str(uuid.uuid4())+i.filename
		# i.save(path)		
		q="update student set route_id='%s',place_id='%s', fname='%s',lname='%s',course='%s',batch='%s',phone='%s' where student_id='%s'"%(r,pp,fna,lna,ccid,pla,pho,mid)
		r=update(q)
		flash("update successfully")

		return redirect(url_for('admin.adminviewstudents'))
	if action=="update":
		q1="select * from  student where student_id='%s'"%(mid)
		r1=select(q1)
		data['updatestudent']=r1
	if action=="delete":
		q="delete from login where login_id='%s'"%(lid)
		print(q)

		delete(q)
		q="delete from student where login_id='%s'"%(lid)
		delete(q)
		flash("delete successfully")
		return redirect(url_for('admin.adminviewstudents'))

	if "search" in request.form:
		sn=request.form['sname']+"%"
		print(sn)
		q="SELECT * FROM student inner join place using(place_id) inner join login using(login_id) where fname like '%s'"%(sn)
		print(q)
		r=select(q)
		print(r)
		data['search']=r


	return render_template('adminviewstudents.html',data=data)



@admin.route('/adminmanagestudent',methods=['get','post'])
def adminmanagestudent():
	data={}
	q="select * from routes"
	r=select(q)
	data['route']=r
	q="select * from place"
	r=select(q)
	data['place']=r
	qq="SELECT * FROM student inner join routes using(route_id) inner join place using(place_id) inner join login using(login_id) "
	res=select(qq)
	data['student']=res
	
	if "register" in request.form:
		ccid=request.form['cc_id']
		pp=request.form['pp']
		r=request.form['r']
		fna=request.form['f']
		lna=request.form['l']
		pla=request.form['pl']
		pho=request.form['ph']
		# em=request.form['e']
		# g=request.form['g']
		uname=request.form['u']
		pwd=request.form['p']
		# i=request.files['i']
		# path="static/"+str(uuid.uuid4())+i.filename
		# i.save(path)
		q="select * from login where username='%s'"%(uname)
		res=select(q)
		if res:
			flash('already registered')
		else:
			path="static/qr_code/"+str(uuid.uuid4())+".png"
			ql="insert into login values(null,'%s','%s','student')"%(uname,pwd)
			rl=insert(ql)
			qs="insert into student values(null,'%s','%s','%s','%s','%s','%s','%s','dob','gender','%s','%s','pending')"%(rl,r,pp,fna,lna,ccid,pla,pho,path)
			r=insert(qs)

			s=qrcode.make(str(r))
			s.save(path)

			print(qs)
			flash("Added successfully")
			return redirect(url_for('admin.adminmanagestudent'))
	if "action" in request.args:
		action=request.args['action']
		mid=request.args['mid']
		lid=request.args['lid']
	else:
		action=None
	if "update" in request.form:
		pp=request.form['pp']
		r=request.form['r']
		ccid=request.form['cc_id']
		fna=request.form['f']
		lna=request.form['l']
		pla=request.form['pl']
		pho=request.form['ph']
		em=request.form['e']
		g=request.form['g']
		# i=request.files['i']
		# path="static/"+str(uuid.uuid4())+i.filename
		# i.save(path)		
		q="update student set route_id='%s',place_id='%s', fname='%s',lname='%s',course='%s',batch='%s',phone='%s',dob='%s',gender='%s' where student_id='%s'"%(r,pp,fna,lna,ccid,pla,pho,em,g,mid)
		r=update(q)
		flash("update successfully")

		return redirect(url_for('admin.adminmanagestudent'))
	if action=="update":
		q1="select * from  student where student_id='%s'"%(mid)
		r1=select(q1)
		data['updatestudent']=r1
	if action=="delete":
		q="delete from login where login_id='%s'"%(lid)
		print(q)

		delete(q)
		q="delete from student where login_id='%s'"%(lid)
		delete(q)
		flash("delete successfully")
		return redirect(url_for('admin.adminmanagestudent'))
	return render_template('adminmanagestudent.html',data=data)


@admin.route('/adminviewbus',methods=['get','post'])
def adminviewbus():
	data={}
	qq="SELECT * FROM bus inner join routes using(route_id) "
	res=select(qq)
	data['view']=res
	
	if "action" in request.args:
		action=request.args['action']
		mid=request.args['mid']
	else:
		action=None
	if "update" in request.form:
		r=request.form['r']
		fna=request.form['f']
		lna=request.form['l']
			
		q="update bus set route_id='%s', register_number='%s',seat_capacity='%s' where bus_id='%s'"%(r,fna,lna,mid)
		r=update(q)
		flash("update successfully")

		return redirect(url_for('admin.adminviewbus'))
	if action=="update":
		q1="select * from  bus where bus_id='%s'"%(mid)
		r1=select(q1)
		data['updateBus']=r1
	if action=="delete":
		
		q="delete from bus where bus_id='%s'"%(mid)
		delete(q)
		flash("delete successfully")
		return redirect(url_for('admin.adminviewbus'))
	return render_template('adminviewbus.html',data=data)




@admin.route('/adminmanagebus',methods=['get','post'])
def adminmanagebus():
	data={}
	q="select * from routes"
	r=select(q)
	data['route']=r
	
	# qq="SELECT * FROM bus inner join routes using(route_id) "
	# res=select(qq)
	# data['view']=res
	
	if "register" in request.form:
		
		r=request.form['r']
		fna=request.form['f']
		lna=request.form['l']
		
		qs="insert into bus values(null,'%s','%s','%s')"%(r,fna,lna)
		insert(qs)
		print(qs)
		flash("Added successfully")
		return redirect(url_for('admin.adminmanagebus'))

	return render_template('adminmanagebus.html',data=data)


@admin.route('/adminmanagedriver',methods=['get','post'])
def adminmanagedriver():
	data={}
	bus_id=request.args['bus_id']	
	qq="SELECT * FROM driver where bus_id='%s'"%(bus_id)
	res=select(qq)
	data['Driver']=res
	
	if "register" in request.form:
		fna=request.form['f']
		lna=request.form['l']
		pho=request.form['ph']
		uname=request.form['u']
		pwd=request.form['p']
		i=request.files['i']
		path="static/"+str(uuid.uuid4())+i.filename
		i.save(path)		
		ql="insert into login values(null,'%s','%s','driver')"%(uname,pwd)
		rl=insert(ql)
		qs="insert into driver values(null,'%s','%s','%s','%s','%s','%s')"%(rl,bus_id,fna,lna,path,pho)
		insert(qs)
		print(qs)
		flash("Added successfully")
		return redirect(url_for('admin.adminmanagedriver',bus_id=bus_id))
	if "action" in request.args:
		action=request.args['action']
		mid=request.args['mid']
		lid=request.args['lid']
	else:
		action=None
	if "update" in request.form:
		fna=request.form['f']
		lna=request.form['l']
		pho=request.form['ph']
		i=request.files['i']
		path="static/"+str(uuid.uuid4())+i.filename
		i.save(path)		
		q="update driver set  firstname='%s',lastname='%s',phone='%s',photo='%s' where driver_id='%s'"%(fna,lna,pho,path,mid)
		r=update(q)
		flash("update successfully")

		return redirect(url_for('admin.adminmanagedriver',bus_id=bus_id))
	if action=="update":
		q1="select * from  driver where driver_id='%s'"%(mid)
		r1=select(q1)
		data['updateDriver']=r1
	if action=="delete":
		q="delete from login where login_id='%s'"%(lid)
		print(q)

		delete(q)
		q="delete from driver where login_id='%s'"%(lid)
		delete(q)
		flash("delete successfully")
		return redirect(url_for('admin.adminmanagedriver',bus_id=bus_id))
	return render_template('adminmanagedriver.html',data=data)


@admin.route('/admintrackbus',methods=['get','post'])
def admintrackbus():
	data={}
	q="select * from buslocation "
	r=select(q)
	data['view']=r

	return render_template('admintrackbus.html',data=data)


@admin.route('/assignbustostudents',methods=['get','post'])
def assignbustostudents():
	data={}
	q="select * from student"
	r=select(q)
	data['stud']=r
	q="select * from bus"
	r=select(q)
	data['bus']=r
	qq="SELECT * FROM assign_bus inner join bus using(bus_id) inner join student using(student_id) "
	print(qq)
	res=select(qq)
	data['student']=res
	
	if "register" in request.form:
		pp=request.form['pp']
		r=request.form['r']
		qs="insert into assign_bus values(null,'%s','%s')"%(pp,r)
		insert(qs)
		print(qs)
		flash("Added successfully")
		return redirect(url_for('admin.assignbustostudents'))
	
	return render_template('assignbustostudents.html',data=data)
@admin.route('/adminbusfee',methods=['get','post'])
def adminbusfee():
	data={}
	bus_id=request.args['bus_id']
	student_id=request.args['student_id']
	# q="select * from assign_amount where student_id='%s' and bus_id='%s'"%(student_id,bus_id)
	q="select * from assign_amount "
	r=select(q)
	data['fee']=r
	
	if "register" in request.form:
		f=request.form['f']
		
		qs="insert into assign_amount values(null,'%s','%s','%s',curdate())"%(student_id,bus_id,f)
		insert(qs)
		print(qs)
		flash("Added successfully")
		return redirect(url_for('admin.adminbusfee',bus_id=bus_id,student_id=student_id))
	
	return render_template('adminbusfee.html',data=data)


@admin.route('/admin_view_driver_noti',methods=['get','post'])
def admin_view_driver_noti():
	data={}

	q="SELECT * FROM `driver_notification` INNER JOIN `driver` USING(`driver_id`) order by date desc"
	r=select(q)
	data['driver_noti']=r
	
	
	return render_template('admin_view_driver_noti.html',data=data)


@admin.route('/admin_view_late_report',methods=['get','post'])
def admin_view_late_report():
	data={}

	q="SELECT * FROM `late_information` INNER JOIN `student` ON `login_id`=`sender_id` order by date desc"
	r=select(q)
	data['late']=r
	
	
	return render_template('admin_view_late_report.html',data=data)