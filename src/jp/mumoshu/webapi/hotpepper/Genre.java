package jp.mumoshu.webapi.hotpepper;

public class Genre {
	String code;
	String name;
	String description;
	public Genre(String code, String description, String name){
		this.code = code;
		this.name = name;
		this.description = description;
	}
	public String getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
}
