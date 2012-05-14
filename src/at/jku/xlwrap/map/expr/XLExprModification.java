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
package at.jku.xlwrap.map.expr;

import at.jku.xlwrap.common.XLWrapException;

/**
 * @author dorgon
 *
 */
public interface XLExprModification {
	
	public XLExpr visiting0(XLExpr expr) throws XLWrapException ;
	public XLExpr visiting1(XLExpr expr) throws XLWrapException ;
	public XLExpr visiting2(XLExpr expr) throws XLWrapException ;
	public XLExpr visitingFunction(XLExpr expr) throws XLWrapException ;
	
}
