/**
 * Copyright 2009 Andreas Langegger, andreas@langegger.at, Austria
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package at.jku.xlwrap.map.expr.func.text;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import at.jku.xlwrap.common.XLWrapException;
import at.jku.xlwrap.exec.ExecutionContext;
import at.jku.xlwrap.map.expr.TypeCast;
import at.jku.xlwrap.map.expr.XLExpr;
import at.jku.xlwrap.map.expr.func.XLExprFunction;
import at.jku.xlwrap.map.expr.val.E_String;
import at.jku.xlwrap.map.expr.val.XLExprValue;
import at.jku.xlwrap.spreadsheet.XLWrapEOFException;

/**
 * @author dorgon
 *
 */
public class E_FuncURLENCODE extends XLExprFunction {
	
	/**
	 * default constructor
	 */
	public E_FuncURLENCODE() {
	}
	
	/**
	 * constructor
	 * 
	 * @param arg a string
	 */
	public E_FuncURLENCODE(XLExpr arg) {
		addArg(arg);
	}

	@Override
	public XLExprValue<String> eval(ExecutionContext context) throws XLWrapException, XLWrapEOFException {
		XLExprValue<?> v1 = args.get(0).eval(context);
		if (v1 == null)
			return null;
		
		try {
			return new E_String(URLEncoder.encode(TypeCast.toString(v1), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new XLWrapException(e.getMessage(), e);
		}
	}

}
