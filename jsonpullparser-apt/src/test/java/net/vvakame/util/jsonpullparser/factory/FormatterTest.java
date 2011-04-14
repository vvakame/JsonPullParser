/*
 * Copyright 2011 vvakame <vvakame@gmail.com>
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

package net.vvakame.util.jsonpullparser.factory;

import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jdt.internal.formatter.DefaultCodeFormatter;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;
import org.junit.Test;
import org.seasar.aptina.unit.AptinaTestCase;

/**
 * フォーマッタ用例
 * @author vvakame
 */
public class FormatterTest extends AptinaTestCase {

	/**
	 * フォーマッタの利用例.
	 * @throws MalformedTreeException
	 * @throws BadLocationException
	 * @author vvakame
	 */
	@Test
	public void test() throws MalformedTreeException, BadLocationException {
		String code =
				"package        net.vvakame;  import net.vvakame.Test;public class geo{\n\n\n\n\tpublic static void main(String[] args){Object obj=null;if(obj!=null){System.out.println(\"true\");}else\n{System.out.println(\"false\");}}}";
		CodeFormatter cf = new DefaultCodeFormatter();

		TextEdit te = cf.format(CodeFormatter.K_UNKNOWN, code, 0, code.length(), 0, null);
		IDocument dc = new Document(code);

		te.apply(dc);
		System.out.println(dc.get());
	}
}
