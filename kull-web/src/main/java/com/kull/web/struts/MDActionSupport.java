/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kull.web.struts;

import com.kull.ObjectHelper;
import com.kull.StringHelper;
import com.kull.able.Resultable;
import com.kull.web.Utils;
import com.opensymphony.xwork2.ModelDriven;
import java.io.IOException;

/**
 *
 * @author lin
 */
public abstract class MDActionSupport<M> extends AwareActionSupport implements ModelDriven<M> {

    private M m;

    protected String pk;

    public void setPk(String pk) {
        this.pk = pk;
    }

    public boolean hasPk() {
        return StringHelper.isNotBlank(pk);
    }

    protected abstract Class<M> classM();

    protected abstract M readByPk(String pk) throws Exception;

    protected abstract M newModel();

    protected abstract void _create(M m) throws Exception;

    protected void onCreate(M m, Resultable re) throws Exception {
    }

    protected void onCreateError(M m, Resultable re, Exception ex) {
    }

    protected void onCreateSuccess(M m, Resultable re) throws Exception {
    }

    protected abstract void _update(M m) throws Exception;

    protected void onUpdate(M m, M post, Resultable re) throws Exception {
    }

    protected void onUpdateError(M m, M post, Resultable re, Exception ex) {
    }

    protected void onUpdateSuccess(M m, M post, Resultable re) throws Exception {
    }

    protected abstract void _delete(M m) throws Exception;

    protected void onDelete(M m, Resultable re) throws Exception {
    }

    protected void onDeleteError(M m, Resultable re, Exception ex) {
    }

    protected void onDeleteSuccess(M m, Resultable re) throws Exception {
    }

    public void create() throws IOException {
        Resultable result = new Resultable();
        M m = null;

        try {
            m = newModel();

            m = Utils.evalParameterModel(this.request, m, "", "");
            onCreate(m, result);
            _create(m);
            onCreateSuccess(m, result);

        } catch (Exception ex) {
            result.setCode(Resultable.CODE_EXCEPTION);
            result.setMsg("insert exception:" + ex.getMessage());
            onCreateError(m, result, ex);
        }
        Utils.writeJson(this.response, result);
    }

    public void update() throws IOException {
        Resultable result = new Resultable();
        M source = null, post = null;
        try {
            source = readByPk(pk);

            post = Utils.evalParameterModel(this.request, newModel(), "", "");
            ObjectHelper.cp(post, source);
            onUpdate(source, post, result);
            _update(source);
            onUpdateSuccess(m, post, result);

        } catch (Exception ex) {
            result.setCode(Resultable.CODE_EXCEPTION);
            result.setMsg("update exception:" + ex.getMessage());
            onUpdateError(m, post, result, ex);
        }
        Utils.writeJson(this.response, result);
    }

    public void delete() throws IOException {
        Resultable result = new Resultable();
        M m = null;

        try {
            m = readByPk(pk);
            m = Utils.evalParameterModel(this.request, m, "", "");
            onDelete(m, result);
            _delete(m);
            onDeleteSuccess(m, result);

        } catch (Exception ex) {
            result.setCode(Resultable.CODE_EXCEPTION);
            result.setMsg("delete exception:" + ex.getMessage());
            onDeleteError(m, result, ex);
        }
        Utils.writeJson(this.response, result);
    }

    public void save() throws IOException {
        if (hasPk()) {
            this.update();
        } else {

            this.create();
        }
    }

    public void read() throws Exception {
        M m = hasPk() ? readByPk(pk) : newModel();
        Utils.writeJson(this.response, m);
    }

    public M getModel() {

        if (m == null) {
            m = newModel();
        }
        return m;
    }

}
