package com.brisco.BridgeCalculatorPro;

import com.brisco.BridgeCalculatorPro.persistence.File;

public class HTMLRenderer {
	public void Render(StringBuilder html) {
		RenderHeader(html);
		RenderBody(html);
		RenderFooter(html);
	}

	protected void RenderHeader(StringBuilder html) {
		html.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
		html.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
		html.append("<head>");
		html.append("<meta http-equiv=\"Content-Type\" content=\"text/html;charset="
				+ File.Encoding + "\" />");
		RenderTitle(html);
		RenderIncludes(html);
		html.append("</head>");
		RenderBodyTag(html);
	}

	protected void RenderBodyTag(StringBuilder html) {
		html.append("<body style=\"color: #e0e0e0; background: #000000;\">");
	}

	protected void RenderIncludes(StringBuilder html) {
	}

	protected void RenderTitle(StringBuilder html) {
		html.append("<title>Bridge Result</title>");

	}

	protected void RenderBody(StringBuilder html) {
	}

	protected void RenderFooter(StringBuilder html) {
		html.append("</body></html>");
	}
}
