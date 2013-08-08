package com.kull.action.sa;

import org.javaclub.jorm.Jorm;
import org.javaclub.jorm.Session;

import com.google.gson.Gson;
import com.kull.StringHelper;
import com.kull.easyui.ETreeActionSupport;
import com.kull.entity.sa.MenuEntity;
import com.kull.struts.CrudActionSupport;

public class ET_MenuAction extends ETreeActionSupport {

	@Override
	protected Node _createNode(String id,String parentId) {
		// TODO Auto-generated method stub
		Session session=Jorm.getSession(true);
		MenuEntity menu=new MenuEntity();
		parentId=StringHelper.isBlank(parentId)?"root":parentId;
		menu.set_text("new node");
		menu.setParent_code(parentId);
		menu.set_code(id);
		session.save(menu);
		Node node=new Node();
		node.text=menu.get_text();
		node.id=id;
		return node;
	}

	@Override
	protected Node _updateNode(String id, String text) {
		// TODO Auto-generated method stub
		Session session=Jorm.getSession(true);
		MenuEntity menu=session.loadFirst(MenuEntity.class, "select * from sa_menu where _code=?",id);
		menu.set_text(text);
		session.saveOrUpdate(menu);
		
		Node node=new Node();
		node.text=menu.get_text();
		node.id=id;
		return node;
	}

	@Override
	protected void _destroyNode(String id) {
		// TODO Auto-generated method stub
		Session session=Jorm.getSession(true);
		MenuEntity menu=session.loadFirst(MenuEntity.class, "select * from sa_menu where _code=?",id);
		session.delete(menu);
		
	}

	@Override
	protected void _dndNode(String id, String targetId, Point point) {
		// TODO Auto-generated method stub
		Session session=Jorm.getSession(true);
		MenuEntity menu=session.loadFirst(MenuEntity.class, "select * from sa_menu where _code=?",id)
				,targetMenu=session.loadFirst(MenuEntity.class, "select * from sa_menu where _code=?",targetId)
				;
		
		switch(point){
		  case append:{
			  menu.setParent_code(targetId);
			  break;
	      }
		  case bottom:{
			  menu.setParent_code(targetMenu.getParent_code());
			  break;
		  }
		  case top:{
			  menu.setParent_code(targetMenu.getParent_code());
			  break;
			  }
		}
		session.saveOrUpdate(menu);
		
	}

	@Override
	protected String toJson(Object obj) {
		// TODO Auto-generated method stub
		return new Gson().toJson(obj);
	}

	

	

}
