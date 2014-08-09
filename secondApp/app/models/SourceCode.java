package models;

public class SourceCode
{
	public String snippet;
	public String sourceCode;
	
	public SourceCode(SourceCode clusterHead) {
		this.snippet = clusterHead.snippet;
		this.sourceCode = clusterHead.sourceCode;
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
	
	public void setSnippet(String name) {
        this.snippet = name;
    }
	
	public void setSourceCode(String name) {
        this.sourceCode = name;
    }
}