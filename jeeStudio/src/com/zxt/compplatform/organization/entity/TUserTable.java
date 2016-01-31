package com.zxt.compplatform.organization.entity;

import com.zxt.framework.common.entity.BasicEntity;

public class TUserTable extends BasicEntity{

	private static final long serialVersionUID = 1L;
	private Long userid;
	 private String username;
	 private String userpassword;
	 private String uname;
	 private String sex;
	 private String birthday;
	 private String age;
	 private String photo;
	 private String npla;
	 private String nation;		
	 private String marry;
	 private String health;
	 private String edu;
	 private String byschool;
	 private String bydate;
	 private String subject;
	 private String id;
	 private String phone;
	 private String mtel;
	 private String bp;
	 private String email;
	 private String address;
	 private String postcode;
	 private String comdate;
	 private String transdate;
	 private String bio;
	 private String unote;
	 private String oracode;
	 private String yhtype;
	 private Long levelnumber;
	 private String msn;
	 private String is_pseudo_deleted;
	 
	public TUserTable(){};
	 
	public TUserTable(Long userid, String username, String userpassword,
			String uname, String sex, String birthday, String age,
			String photo, String npla, String nation, String marry,
			String health, String edu, String byschool, String bydate,
			String subject, String id, String phone, String mtel, String bp,
			String email, String address, String postcode, String comdate,
			String transdate, String bio, String unote, String oracode,
			String yhtype, Long levelnumber, String msn,String is_pseudo_deleted) {
		super();
		this.userid = userid;
		this.username = username;
		this.userpassword = userpassword;
		this.uname = uname;
		this.sex = sex;
		this.birthday = birthday;
		this.age = age;
		this.photo = photo;
		this.npla = npla;
		this.nation = nation;
		this.marry = marry;
		this.health = health;
		this.edu = edu;
		this.byschool = byschool;
		this.bydate = bydate;
		this.subject = subject;
		this.id = id;
		this.phone = phone;
		this.mtel = mtel;
		this.bp = bp;
		this.email = email;
		this.address = address;
		this.postcode = postcode;
		this.comdate = comdate;
		this.transdate = transdate;
		this.bio = bio;
		this.unote = unote;
		this.oracode = oracode;
		this.yhtype = yhtype;
		this.levelnumber = levelnumber;
		this.msn = msn;
		this.is_pseudo_deleted = is_pseudo_deleted;
	}
	
	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserpassword() {
		return userpassword;
	}
	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getNpla() {
		return npla;
	}
	public void setNpla(String npla) {
		this.npla = npla;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getMarry() {
		return marry;
	}
	public void setMarry(String marry) {
		this.marry = marry;
	}
	public String getHealth() {
		return health;
	}
	public void setHealth(String health) {
		this.health = health;
	}
	public String getEdu() {
		return edu;
	}
	public void setEdu(String edu) {
		this.edu = edu;
	}
	public String getByschool() {
		return byschool;
	}
	public void setByschool(String byschool) {
		this.byschool = byschool;
	}
	public String getBydate() {
		return bydate;
	}
	public void setBydate(String bydate) {
		this.bydate = bydate;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMtel() {
		return mtel;
	}
	public void setMtel(String mtel) {
		this.mtel = mtel;
	}
	public String getBp() {
		return bp;
	}
	public void setBp(String bp) {
		this.bp = bp;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getComdate() {
		return comdate;
	}
	public void setComdate(String comdate) {
		this.comdate = comdate;
	}
	public String getTransdate() {
		return transdate;
	}
	public void setTransdate(String transdate) {
		this.transdate = transdate;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	public String getUnote() {
		return unote;
	}
	public void setUnote(String unote) {
		this.unote = unote;
	}
	public String getOracode() {
		return oracode;
	}
	public void setOracode(String oracode) {
		this.oracode = oracode;
	}
	public String getYhtype() {
		return yhtype;
	}
	public void setYhtype(String yhtype) {
		this.yhtype = yhtype;
	}
	
	public Long getLevelnumber() {
		return levelnumber;
	}

	public void setLevelnumber(Long levelnumber) {
		this.levelnumber = levelnumber;
	}

	public String getMsn() {
		return msn;
	}
	public void setMsn(String msn) {
		this.msn = msn;
	}

	public String getIs_pseudo_deleted() {
		return is_pseudo_deleted;
	}

	public void setIs_pseudo_deleted(String is_pseudo_deleted) {
		this.is_pseudo_deleted = is_pseudo_deleted;
	}   
	 
}
