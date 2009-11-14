/**
 * 
 */
package com.mumoshu.parsers;

import org.json.JSONObject;

import com.mumoshu.patterns.Parser;


/**
 * @author mumoshu
 *
 */
public class JSONParser implements Parser<JSONObject> {
	@Override
	public JSONObject parse(String data) throws Exception {
		return new JSONObject(data);
	}
}
