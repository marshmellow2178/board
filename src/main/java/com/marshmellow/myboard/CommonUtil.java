package com.marshmellow.myboard;

import org.springframework.stereotype.Component;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.commonmark.node.Node;

@Component
public class CommonUtil { //마크다운 텍스트 -> HTML 문서
	public String markdown(String md) {
		Parser p = Parser.builder().build();
		Node doc = p.parse(md);
		HtmlRenderer renderer = HtmlRenderer.builder().build();
		return renderer.render(doc);
	}
}
