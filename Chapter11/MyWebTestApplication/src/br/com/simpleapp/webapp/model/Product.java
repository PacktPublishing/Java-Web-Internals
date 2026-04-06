package br.com.isiflix.webtestapp.model;

public class Produto {
	private int id;
	private String name;
	private double price;
	private String linkPhoto;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getLinkPhoto() {
		return linkPhoto;
	}
	public void setLinkPhto(String linkPhoto) {
		this.linkPhoto = linkPhoto;
	}
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", price=" + price + ", linkPhoto=" + linkPhoto + "]";
	}
	
	

}
