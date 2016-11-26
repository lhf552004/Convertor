package com.convertor;

public class Translation {
	 private String searchText;
	 private String oldTranslationText;
	 private String newTranslationText;
	 private boolean needToTranslate;
	 
	 public Translation(String searchText,String translationText){
		 this.searchText = searchText;
		 this.oldTranslationText = this.newTranslationText = translationText;
		 this.needToTranslate = true;
	 }
	/**
	 * @return the searchText
	 */
	public String getSearchText() {
		return searchText;
	}
	/**
	 * @param searchText the searchText to set
	 */
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	/**
	 * @return the oldTranslationText
	 */
	public String getOldTranslationText() {
		return oldTranslationText;
	}
	/**
	 * @param oldTranslationText the oldTranslationText to set
	 */
	public void setOldTranslationText(String oldTranslationText) {
		this.oldTranslationText = oldTranslationText;
	}
	/**
	 * @return the newTranslationText
	 */
	public String getNewTranslationText() {
		return newTranslationText;
	}
	/**
	 * @param newTranslationText the newTranslationText to set
	 */
	public void setNewTranslationText(String newTranslationText) {
		this.newTranslationText = newTranslationText;
	}
	/**
	 * @return the needToTranslate
	 */
	public boolean isNeedToTranslate() {
		return needToTranslate;
	}
	/**
	 * @param needToTranslate the needToTranslate to set
	 */
	public void setNeedToTranslate(boolean needToTranslate) {
		this.needToTranslate = needToTranslate;
	}
}
