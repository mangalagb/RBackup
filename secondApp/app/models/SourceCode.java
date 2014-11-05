package models;

public class SourceCode
{
	public String snippet;
	public String sourceCode;
	public String startHighlight;
	public String stopHighlight;
	
	public SourceCode(SourceCode clusterHead) {
		this.snippet = clusterHead.snippet;
		this.sourceCode = clusterHead.sourceCode;
		this.startHighlight = clusterHead.startHighlight;
		this.stopHighlight = clusterHead.stopHighlight;
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
	public String getStartHighlight() {
        return startHighlight;
    }
	public String getStopHighlight() {
        return stopHighlight;
    }
	
	public void setSnippet(String name) {
        this.snippet = name;
    }
	
	public void setSourceCode(String name) {
        this.sourceCode = name;
    }
	
	public void setStartHighlight(String startHighlight) {
        this.startHighlight = startHighlight;
    }
	public void setStopHighlight(String stopHighlight) {
        this.stopHighlight = stopHighlight;
    }
}