package cn.gojava.app.common.http;

import cn.gojava.app.common.utils.XssUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class HttpResult {

    private int statusCode = 200;
    private String msg;
	private Object data;

	public static HttpResult error() {
		return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器出错，请联系管理员");
	}

	public static HttpResult error(String msg) {
		return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg);
	}

	public static HttpResult error(int code, String msg) {
		HttpResult r = new HttpResult();
		r.setStatusCode(code);
		r.setMsg(msg);
		return r;
	}

	public static HttpResult ok(String msg) {
		HttpResult r = new HttpResult();
		r.setMsg(msg);
		return r;
	}

	public static HttpResult ok(Object data) {
		HttpResult r = new HttpResult();
		r.setData(XssUtils.filterData(data));
		return r;
	}

	public static HttpResult ok(String msg,Object data) {
		HttpResult r = new HttpResult();
		r.setMsg(msg);
		r.setData(XssUtils.filterData(data));
		return r;
	}

	public static HttpResult ok() {
		return new HttpResult();
	}

    public void setData(Object data) {
		this.data = XssUtils.filterData(data);
	}

}
