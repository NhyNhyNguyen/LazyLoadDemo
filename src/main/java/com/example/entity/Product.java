package com.example.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "products")
public class Product 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer pId;
	private String pName;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "product_id")
	private List<Variant> variant = new ArrayList<Variant>();

	public int getpId() {
		return pId;
	}

	public void setpId(int pId) {
		this.pId = pId;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public List<Variant> getVariant() {
		return variant;
	}

	public void setVariant(List<Variant> variant) {
		this.variant = variant;
	}



	@Override
	public String toString() {
		return "Product{" +
				"pId=" + pId +
				", pName='" + pName + '\'' +
				", variant=" + variant +
				'}';
	}
}
