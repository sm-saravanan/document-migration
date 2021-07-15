package com.veeva.document.migration.model;
/**
 * WonderDrugDocument class holds metadata related to WonderDrug company
 * 
 * @author Saran
 *
 */
public class WonderDrugDocument {
	private String documentID;
	private String documentName;
	private String filePath;
	private String type;
	private String subtype;
	private String approvedDate;
	
	
	public String getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(String approvedDate) {
		this.approvedDate = approvedDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSubtype() {
		return subtype;
	}
	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}
	public WonderDrugDocument(String documentID, String documentName, String filePath) {
		this.documentID = documentID;
		this.documentName = documentName;
		this.filePath = filePath;
	}
	public WonderDrugDocument() {
		
	}
	
	public String getDocumentID() {
		return documentID;
	}
	public void setDocumentID(String documentID) {
		this.documentID = documentID;
	}
	public String getDocumentName() {
		return documentName;
	}
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	
	
	@Override
	public String toString() {
		return "documentID: "+ documentID + ",documentName"  + documentName + ", filePath" + filePath;
	}
}
