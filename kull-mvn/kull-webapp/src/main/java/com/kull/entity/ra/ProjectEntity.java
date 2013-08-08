package com.kull.entity.ra;

import java.io.Serializable;
import java.util.Date;

import org.javaclub.jorm.annotation.Entity;
import org.javaclub.jorm.annotation.Id;
import org.javaclub.jorm.annotation.PK;

@PK("id")
@Entity(table="ra_project")
public class ProjectEntity implements Serializable {

	@Id
	protected Integer id ;
	 protected String code ;
	 protected String text ;
	 protected String remark ;
	 protected Date create_date ;
	 protected String create_user_code ;
	 protected Integer level ;


	 public Integer getId(){ return this.id; }
	 public void setId(Integer id){ this.id=id; }
	 public String getCode(){ return this.code; }
	 public void setCode(String code){ this.code=code; }
	 public String getText(){ return this.text; }
	 public void setText(String text){ this.text=text; }
	 public String getRemark(){ return this.remark; }
	 public void setRemark(String remark){ this.remark=remark; }
	 public Date getCreate_date(){ return this.create_date; }
	 public void setCreate_date(Date create_date){ this.create_date=create_date; }
	 public String getCreate_user_code(){ return this.create_user_code; }
	 public void setCreate_user_code(String create_user_code){ this.create_user_code=create_user_code; }
	 public Integer getLevel(){ return this.level; }
	 public void setLevel(Integer level){ this.level=level; }
}
