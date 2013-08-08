package com.kull.entity.sa;

import java.io.Serializable;
import java.util.Date;

import org.javaclub.jorm.annotation.Entity;
import org.javaclub.jorm.annotation.Id;
import org.javaclub.jorm.annotation.PK;

@PK({"_id"})
@Entity(table="sa_dict")
public class DictEntity implements Serializable {

	@Id
	 protected Integer _id ;
	 protected String _code ;
	 protected String _type ;
	 protected String _text ;
	 protected String _remark ;
	 protected Date _create_date ;
	 protected String _create_user_code ;
	 protected Integer _level ;
	 protected String _string_0 ;
	 protected String _string_1 ;
	 protected String _string_2 ;
	 protected Integer _int_0 ;
	 protected Integer _int_1 ;
	 protected Integer _int_2 ;
	 protected String dtype ;


	 public Integer get_id(){ return this._id; }
	 public void set_id(Integer _id){ this._id=_id; }
	 public String get_code(){ return this._code; }
	 public void set_code(String _code){ this._code=_code; }
	 public String get_type(){ return this._type; }
	 public void set_type(String _type){ this._type=_type; }
	 public String get_text(){ return this._text; }
	 public void set_text(String _text){ this._text=_text; }
	 public String get_remark(){ return this._remark; }
	 public void set_remark(String _remark){ this._remark=_remark; }
	 public Date get_create_date(){ return this._create_date; }
	 public void set_create_date(Date _create_date){ this._create_date=_create_date; }
	 public String get_create_user_code(){ return this._create_user_code; }
	 public void set_create_user_code(String _create_user_code){ this._create_user_code=_create_user_code; }
	 public Integer get_level(){ return this._level; }
	 public void set_level(Integer _level){ this._level=_level; }
	 public String get_string_0(){ return this._string_0; }
	 public void set_string_0(String _string_0){ this._string_0=_string_0; }
	 public String get_string_1(){ return this._string_1; }
	 public void set_string_1(String _string_1){ this._string_1=_string_1; }
	 public String get_string_2(){ return this._string_2; }
	 public void set_string_2(String _string_2){ this._string_2=_string_2; }
	 public Integer get_int_0(){ return this._int_0; }
	 public void set_int_0(Integer _int_0){ this._int_0=_int_0; }
	 public Integer get_int_1(){ return this._int_1; }
	 public void set_int_1(Integer _int_1){ this._int_1=_int_1; }
	 public Integer get_int_2(){ return this._int_2; }
	 public void set_int_2(Integer _int_2){ this._int_2=_int_2; }
	 public String getDtype(){ return this.dtype; }
	 public void setDtype(String dtype){ this.dtype=dtype; }
}
