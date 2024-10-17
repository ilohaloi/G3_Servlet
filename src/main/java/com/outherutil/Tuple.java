package com.outherutil;

public class Tuple<K,V1,V2> {

	K k;
	V1 v1;
	V2 v2;

	public Tuple() {
		super();
	}

	public Tuple(K k, V1 v1, V2 v2) {
		super();
		this.k = k;
		this.v1 = v1;
		this.v2 = v2;
	}

	public K getK() {
		return k;
	}

	public V1 getV1() {
		return v1;
	}

	public V2 getV2() {
		return v2;
	}

	public void setK(K k) {
		this.k = k;
	}

	public void setV1(V1 v1) {
		this.v1 = v1;
	}

	public void setV2(V2 v2) {
		this.v2 = v2;
	}

}
