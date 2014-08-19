package models;

public class SourceCode
{
	public String snippet;
	public String sourceCode;
	public String toHighlight;
	
	public SourceCode(SourceCode clusterHead) {
		this.snippet = clusterHead.snippet;
		this.sourceCode = clusterHead.sourceCode;
		this.toHighlight = clusterHead.toHighlight;
	}

	public SourceCode() {
		// TODO Auto-generated constructor stub
	}

	public String getSnippet() {
        return snippet;
    }
	
	public String getSourceCode() {
        return sourceCode;
    }
	public String getToHighlight() {
        return toHighlight;
    }
	
	public void setSnippet(String name) {
        this.snippet = name;
    }
	
	public void setSourceCode(String name) {
        this.sourceCode = name;
    }
	
	public void setToHighlight(String toHighlight) {
        this.toHighlight = toHighlight;
    }
}