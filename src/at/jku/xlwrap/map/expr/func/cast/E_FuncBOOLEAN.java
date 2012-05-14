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
package at.jku.xlwrap.map.expr.func.cast;

import at.jku.xlwrap.common.XLWrapException;
import at.jku.xlwrap.exec.ExecutionContext;
import at.jku.xlwrap.map.expr.TypeCast;
import at.jku.xlwrap.map.expr.XLExpr;
import at.jku.xlwrap.map.expr.val.E_Boolean;
import at.jku.xlwrap.map.expr.val.XLExprValue;
import at.jku.xlwrap.spreadsheet.XLWrapEOFException;



/**
 * @author dorgon
 *
 */
public class E_FuncBOOLEAN extends XLFuncTypeCast {

	/**
	 * default constructor
	 */
	public E_FuncBOOLEAN() {
	}
	
	public E_FuncBOOLEAN(XLExpr arg) {
		args.add(arg);
	}

	@Override
	public XLExprValue<Boolean> eval(ExecutionContext context) throws XLWrapException, XLWrapEOFException {
		XLExprValue<?> v1 = args.get(0).eval(context);
		if (v1 == null)
			return null;
		
		E_Boolean b = TypeCast.toBooleanExpr(v1, context);
		b.setCastedFlag();
		return b;
	}

	@Override
	public String toString() {
		return "(BOOLEAN) " + args.get(0);
	}
}
