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
package at.jku.xlwrap.map.transf;

import at.jku.xlwrap.common.XLWrapException;
import at.jku.xlwrap.exec.ExecutionContext;
import at.jku.xlwrap.exec.TransformationStage;
import at.jku.xlwrap.map.range.Range;

/**
 * @author dorgon
 *
 */
public class SheetShift extends Shift {
//	private static final Logger log = LoggerFactory.getLogger(MultiDimTests.class);

	/**
	 * 
	 * @param shift
	 * @param repeat
	 * @param restriction
	 * @param skipCondition
	 * @param breakCondition
	 * @throws XLWrapException 
	 */
	public SheetShift(int shift, int repeat, String restriction, String skipCondition, String breakCondition) throws XLWrapException {
		super(shift, repeat, restriction, skipCondition, breakCondition);
	}

	@Override
	public String getArgsAsString() {
		return "repeat " + repeat + " by shifting " + shift + " sheet(s)";
	}
	
	@Override
	public TransformationStage getExecutor(ExecutionContext context) {
		return new TransformationStage(context) {
			private int count;
			
			@Override
			public void init() {
				count = 0;
			}
			
			@Override
			public boolean hasMoreTransformations() throws XLWrapException {
				if (count < repeat) {
					count++;
					return true;
				} else
					return false;
			}

			@Override
			public Range transform(Range range, Range restriction) throws XLWrapException {
				if (count > 1)
					return range.shiftSheets(shift, restriction, context);
				else
					return range;
			}
			
			@Override
			public String getThisStatus() {
				return "SheetShift: " + count +  "/" + repeat;
			}

		};
	}


}
