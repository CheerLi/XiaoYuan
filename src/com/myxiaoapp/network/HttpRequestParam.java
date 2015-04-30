/**
 * 2015年4月11日
 * ken
 */
package com.myxiaoapp.network;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ken
 *
 */
public class HttpRequestParam {
	private String url;
    private Map<String, String> params;
    private Class resultModelClass;

    public HttpRequestParam(String url, Class clazz) {
        params = new HashMap<String, String>();
        this.url = url;
        this.resultModelClass = clazz;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    public void addParam(String key, String value) {
        if (params == null) {
            params = new HashMap<String, String>();
        }
        params.put(key, value);
    }

    public String getParamByKey(String key) {
        if (params == null) {
            return null;
        }
        return params.get(key);
    }

    public Map<String, String> getAllParams() {
        return params;
    }

    public void setResultModel(Class clazz) {
        this.resultModelClass = clazz;
    }

    public Class getResultModel() {
        return this.resultModelClass;
    }

    @Override
    public String toString() {
        if (params == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String temp = entry.getKey() + "=" + entry.getValue();
            sb.append(temp);
        }
        return sb.toString();
    }
}
