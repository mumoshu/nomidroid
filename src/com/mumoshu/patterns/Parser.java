package com.mumoshu.patterns;


public interface Parser<Output> {
	public abstract Output parse(String data) throws Exception;
}
